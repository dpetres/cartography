package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an OpenStreetMap
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMMap 
{
	private final List<OSMWay> ways;
	private final List<OSMRelation> relations;

	/**
	 * Constructs an OSM map with the given ways and relations
	 * @param ways
	 * 			given ways of the map
	 * @param relations
	 * 			given relation
	 */
	public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations)
	{
		this.ways = Collections.unmodifiableList(new ArrayList<>(ways));
		this.relations = Collections.unmodifiableList(new ArrayList<>(relations));
	}

	/**
	 * Returns the list of ways of the map
	 * @return list of ways
	 */
	public List<OSMWay> ways()
	{
		return ways;
	}

	/**
	 * Returns the list of attributes of the map
	 * @return list of attributes
	 */
	public List<OSMRelation> relations()
	{
		return relations;
	}

	public final static class Builder
	{
		private Map<Long, OSMNode> nodesMap = new HashMap<>();
		private  Map<Long, OSMWay> ways = new HashMap<>();
		private Map<Long, OSMRelation> relations = new HashMap<>();

		/**
		 * Adds the node to the builder
		 * @param newNode
		 * 			node added to the builder
		 */
		public void addNode(OSMNode newNode) 
		{
			nodesMap.put(newNode.id(), newNode);
		}

		/**
		 * Returns the node whose unique identifier is equal to that given,
		 * or null if the node has not been added to the builder
		 * @param id
		 * 			given tested identifier
		 * @return OSMNode attributed to the unique identifier 
		 * 			or null iff the given identifier doesn't exist
		 */
		public OSMNode nodeForId(long id) 
		{
			return nodesMap.get(id);
		}

		/**
		 * Adds the given way to the map in construction
		 * @param newWay
		 * 			given way added to the map in construction
		 */
		public void addWay(OSMWay newWay)
		{
			ways.put(newWay.id(), newWay);
		}

		/**
		 * Returns the way whose unique identifier is equal to that given,
		 * or null if the way has not been added to the builder
		 * @param id
		 * 			given tested identifier
		 * @return OSMWay attributed to the unique identifier 
		 * 			or null iff the given identifier doesn't exist
		 */
		public OSMWay wayForId(long id)
		{
			return ways.get(id);
		}

		/**
		 * Adds the given relation to the map in construction
		 * @param newRelation
		 * 			given relation added to the map in construction
		 */
		public void addRelation(OSMRelation newRelation)
		{
			relations.put(newRelation.id(), newRelation);
		}

		/**
		 * Returns the relation whose unique identifier is equal to that given,
		 * or null if the relation has not been added to the builder
		 * @param id
		 * 			given tested identifier
		 * @return OSMRelation attributed to the unique identifier 
		 * 			or null iff the given identifier doesn't exist
		 */
		public OSMRelation relationForId(long id)
		{
			return relations.get(id);
		}

		/**
		 * Constructs an OSMMap with the ways and relation added up until now
		 * @return OSMMap
		 * 			map constructed with the ways and relations added until now
		 */
		public OSMMap build()
		{
			return new OSMMap(ways.values(), relations.values());
		}
	}
}