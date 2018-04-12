package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mother class representing the closed or open polyLines 
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public abstract class PolyLine 
{
	private final List<Point> points;

	/**
	 * Constructs a polyLine with the given list of points
	 * @param points 
	 * 			given list of points in order to construct the closed or open polyline
	 * @throws IllegalArgumentException if the list of tops is empty
	 */
	public PolyLine(List<Point> points) throws IllegalArgumentException
	{
		if(points.isEmpty())
		{
			throw new IllegalArgumentException("Error: the list of tops is empty.");
		}
		this.points = Collections.unmodifiableList(new ArrayList<>(points));
	}

	/**
	 * Abstract method defining if the polyLine is open or closed (must be override)
	 */
	public abstract boolean isClosed();

	/**
	 * Returns the list of tops of the polyLine
	 * @return the list of tops of the polyline (points)
	 */
	public List<Point> points()
	{
		return points;
	}

	/**
	 * Returns the first point of the polyLine
	 * @return the first point
	 */
	public Point firstPoint()
	{
		return points.get(0);
	}

	public final static class Builder
	{
		private List<Point> points = new ArrayList<Point>();

		/**
		 * Adds the given point at the end of the list of points of the polyLine in construction
		 * @param newPoint
		 * 			given point added to the list of points
		 */
		public void addPoint(Point newPoint)
		{
			points.add(newPoint);
		}

		/**
		 * Constructs and returns an open polyLine with the added points
		 * @return OpenPolyLine
		 * 			OpenPolyline constructed with the added points
		 */
		public OpenPolyLine buildOpen()
		{
			return new OpenPolyLine(points);
		}

		/**
		 * Constructs and returns a closed polyLine with the added points
		 * @return ClosedPolyLine
		 * 			ClosedPolyline constructed with the added points
		 */
		public ClosedPolyLine buildClosed()
		{
			return new ClosedPolyLine(points);
		}
	}
}