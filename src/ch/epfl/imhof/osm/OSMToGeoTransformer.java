package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.projection.Projection;

/**
 * Represents an OSM data converter in attributed geometric entities
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMToGeoTransformer
{
	private final Projection projection;

	/**
	 * Constructs an OSM data converter which uses the given projection
	 * @param projection
	 * 			given projection
	 */
	public OSMToGeoTransformer(Projection projection)
	{
		this.projection = projection;
	}

	/**
	 * Converts an OSM "map" in a projected geometric map
	 * @param OSMMap map
	 * 			given map to be converted
	 * @return projected geometric map corresponding to the given OSM map
	 */
	public Map transform(OSMMap map)
	{
		Map.Builder mapIC = new Map.Builder();

		Set<String> setForClosedWays = new HashSet<>(Arrays.asList("aeroway", "amenity", "building", "harbour", "historic",
				"landuse", "leisure", "man_made", "military", "natural",
				"office", "place", "power", "public_transport", "shop",
				"sport", "tourism", "water", "waterway", "wetland"));
		Set<String> setFilterPolyLine =  new HashSet<>(Arrays.asList("bridge", "highway", "layer", "man_made", "railway",
				"tunnel", "waterway"));
		Set<String> setFilterPolygon =  new HashSet<>(Arrays.asList("building", "landuse", "layer", "leisure", "natural",
				"waterway"));

		Attributes attsWay;
		Attributes attsFilter;
		PolyLine.Builder polylineIC = new PolyLine.Builder();
		for(OSMWay w: map.ways())
		{
			attsWay = w.attributes();
			if(w.isClosed())
			{
				{
					if(!attsWay.keepOnlyKeys(setForClosedWays).isEmpty() || (attsWay.contains("area") && (attsWay.get("area").equals("yes") 
							|| attsWay.get("area").equals("1") || attsWay.get("area").equals("true"))))
					{
						attsFilter = attsWay.keepOnlyKeys(setFilterPolygon);
						if(!attsFilter.isEmpty())
						{
							for(OSMNode n: w.nonRepeatingNodes())
							{
								polylineIC.addPoint(projection.project(n.position()));
							}
							mapIC.addPolygon(new Attributed<>(new Polygon(polylineIC.buildClosed()), attsFilter));
							polylineIC = new PolyLine.Builder();
						}
					}else
					{
						attsFilter = attsWay.keepOnlyKeys(setFilterPolyLine);
						if(!attsFilter.isEmpty())
						{
							for(OSMNode n: w.nonRepeatingNodes())
							{
								polylineIC.addPoint(projection.project(n.position()));
							}
							mapIC.addPolyLine(new Attributed<PolyLine>(polylineIC.buildClosed(), attsFilter));
							polylineIC = new PolyLine.Builder();
						}
					}
				}
			}else
			{
				attsFilter = attsWay.keepOnlyKeys(setFilterPolyLine);
				if(!attsFilter.isEmpty())
				{
					for(OSMNode n: w.nodes())
					{
						polylineIC.addPoint(projection.project(n.position()));
					}
					mapIC.addPolyLine(new Attributed<PolyLine>(polylineIC.buildOpen(), attsFilter));
					polylineIC = new PolyLine.Builder();
				}
			}
		}

		Attributes attsRelation;
		Attributes attsRelationFilter;
		for(OSMRelation r: map.relations())
		{
			attsRelation = r.attributes();
			if(attsRelation.contains("type") && attsRelation.get("type").equals("multipolygon"))
			{
				attsRelationFilter = attsRelation.keepOnlyKeys(setFilterPolygon);;
				if(!attsRelationFilter.isEmpty())
				{
					for(Attributed<Polygon> p: assemblePolygon(r, attsRelationFilter))
					{
						mapIC.addPolygon(p);
					}
				}
			}
		}
		return mapIC.build();
	}

	/**
	 * Calculates and returns the set of rings in the given relation that possesses the specified role
	 * Returns an empty list if the calculation of the rings fails
	 * @param relation
	 * 			given relation
	 * @param role
	 * 			given role
	 * @return List<ClosedPolyLine> list of rings in the given relation and having the given role
	 */
	private List<ClosedPolyLine> ringsForRole(OSMRelation relation, String role)
	{
		Graph.Builder<OSMNode> graphIC = new Graph.Builder<>();
		OSMWay newWay;
		List<OSMNode> nodesList;
		List<ClosedPolyLine> polyLineList = new ArrayList<>();
		for(Member m: relation.members())
		{
			if(m.type().equals(Member.Type.WAY))
			{
				if(m.role().equals(role))
				{
					newWay = (OSMWay)m.member();
					nodesList = newWay.nodes();
					graphIC.addNode(nodesList.get(0));
					for(int i = 1; i < nodesList.size(); ++i)
					{
						graphIC.addNode(nodesList.get(i));
						graphIC.addEdge(nodesList.get(i-1), nodesList.get(i));
					}
				}
			}
		}

		Graph<OSMNode> graph = graphIC.build();

		OSMNode node;
		boolean ok;
		LinkedList<OSMNode> nonVisitedNodes = new LinkedList<>(graph.nodes());
		Set<OSMNode> neighboringNodesSet;
		PolyLine.Builder polylineIC;
		while(!nonVisitedNodes.isEmpty())
		{
			node = nonVisitedNodes.getFirst();
			polylineIC =  new PolyLine.Builder();
			polylineIC.addPoint(projection.project(node.position()));
			ok = true;
			while(ok)
			{
				neighboringNodesSet = new HashSet<>(graph.neighborsOf(node));
				if(neighboringNodesSet.size() != 2)
				{
					return new ArrayList<ClosedPolyLine>();
				}
				nonVisitedNodes.remove(node);
				neighboringNodesSet.retainAll(nonVisitedNodes);
				if(neighboringNodesSet.isEmpty())
				{
					polyLineList.add(polylineIC.buildClosed());
					ok = false;
				}else
				{
					node = neighboringNodesSet.iterator().next();
					polylineIC.addPoint(projection.project(node.position()));
				}
			}	
		}
		return polyLineList;
	}

	/**
	 * Calculates and returns the list of attributed polygons from the given relation, by attaching the given attributes
	 * @param relation
	 * 			given relation
	 * @param attributes
	 * 			given attributes to be attached to the new list of attributed polygon
	 * @return list of attributed polygons from the given relation
	 */
	private List<Attributed<Polygon>> assemblePolygon(OSMRelation relation, Attributes attributes)
	{
		List<Attributed<Polygon>> polygonsList = new ArrayList<>();
		List<ClosedPolyLine> innerPolyLineList = ringsForRole(relation, "inner");
		List<ClosedPolyLine> outerPolyLineList = ringsForRole(relation, "outer");
		if(outerPolyLineList.isEmpty())
		{
			return polygonsList;
		}

		Collections.sort(outerPolyLineList, (o1, o2) -> { return Double.valueOf(o1.area()).compareTo(Double.valueOf(o2.area()));});

		Collections.sort(innerPolyLineList, (o1, o2) -> { return Double.valueOf(o1.area()).compareTo(Double.valueOf(o2.area()));});

		List<ClosedPolyLine> holes;
		ClosedPolyLine c2;
		Iterator<ClosedPolyLine> iterator;

		for(ClosedPolyLine c1: outerPolyLineList)
		{
			holes = new ArrayList<>();
			iterator = innerPolyLineList.iterator();
			while(iterator.hasNext())
			{
				c2 = iterator.next();
				if(c2.area() >= c1.area())
				{
					break;
				}
				if(c1.containsPoint(c2.firstPoint()))
				{
					holes.add(c2);
					iterator.remove();
				}
			}
			polygonsList.add(new Attributed<Polygon>(new Polygon(c1, holes), attributes));
		}
		return polygonsList;
	}
}