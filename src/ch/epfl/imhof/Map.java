package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Represents a projected map composed of attributed geometric entities
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Map 
{
	private final List<Attributed<PolyLine>> polyLines;
	private final List<Attributed<Polygon>> polygons;

	/**
	 * Constructs a map with the list of given attributed polylines and the list of given polygons
	 * @param polyLines
	 * 			Given list of polylines
	 * @param polygons
	 * 			Given list of polygons
	 */
	public Map(List<Attributed<PolyLine>> polyLines, List<Attributed<Polygon>> polygons)
	{
		this.polyLines = Collections.unmodifiableList(new ArrayList<>(polyLines));
		this.polygons = Collections.unmodifiableList(new ArrayList<>(polygons)); 
	}

	/**
	 * Returns the list of polyline attributed to the map
	 * @return list of polyline
	 */
	public List<Attributed<PolyLine>> polyLines()
	{
		return polyLines;
	}

	/**
	 * Returns the list of attributed polygons to the map
	 * @return list of polygons
	 */
	public List<Attributed<Polygon>> polygons()
	{
		return polygons;
	}

	public static class Builder
	{
		private List<Attributed<PolyLine>> polyLines = new ArrayList<>();
		private List<Attributed<Polygon>> polygons = new ArrayList<>();

		/**
		 * Adds an attributed polyline to the map in construction
		 * @param newPolyLine
		 * 			New given polyline to be added to the map
		 */
		public void addPolyLine(Attributed<PolyLine> newPolyLine)
		{
			polyLines.add(newPolyLine);
		}

		/**
		 * Adds an attributed polygon to the map in construction
		 * @param newPolygon
		 * 			New given polygon to be added to map
		 */
		public void addPolygon(Attributed<Polygon> newPolygon)
		{
			polygons.add(newPolygon);
		}

		/**
		 * Constructs a map with the polylines and polygons added until now
		 * @return map
		 * 			Map constructed with the polygons and polylines added until now
		 */
		public Map build()
		{
			return new Map(polyLines, polygons);
		}
	}
}