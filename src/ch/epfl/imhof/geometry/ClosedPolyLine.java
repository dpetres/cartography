package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Represents a closed PolyLine
 * Inherits PolyLine
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class ClosedPolyLine extends PolyLine 
{
	/**
	 * Constructs a closed polyLine with a list of points
	 * @param points
	 * 			given list of points
	 */
	public ClosedPolyLine(List<Point> points) 
	{
		super(points);
	}

	/**
	 * Overrides the isClosed() methode inherited from the mother class
	 * @return true
	 * 			because the polyline is closed in order to create a ClosedPolyline
	 */
	@Override
	public boolean isClosed() 
	{
		return true;
	}

	/**
	 * Returns the (positive) area of the polyLine
	 * @return area
	 * 			area of the polyline (double)
	 */
	public double area()
	{
		double area = 0.0;

		for(int i = 0; i < points().size(); i++)
		{
			area += (1.0/2) * points().get(generalizedPoint(i)).x() * 
					(points().get(generalizedPoint(i+1)).y() - points().get(generalizedPoint(i-1)).y()); 
		}
		return Math.abs(area);
	}

	/**
	 * Returns true iff a given point p is included in the closed polyLine
	 * @param p
	 * 			given point
	 * @return true iff the polyLine contains the given point p
	 */
	public boolean containsPoint(Point p)
	{
		int index = 0;
		double y = p.y();
		Point firstPoint;
		Point secondPoint;
		double y1, y2;

		for(int i = 0; i < points().size(); i++)
		{
			firstPoint = points().get(generalizedPoint(i));
			secondPoint = points().get(generalizedPoint(i+1));
			y1 = firstPoint.y();
			y2 = secondPoint.y();

			if(y1 <= y)
			{
				if(y2 > y && isLeftPoint(p, firstPoint, secondPoint))
				{
					index += 1;
				}
			}else
			{
				if(y2 <= y && isLeftPoint(p, secondPoint, firstPoint))
				{
					index -= 1;
				}
			}
		}
		return (index != 0);
	}

	/**
	 * Returns true iff the given point p is strictly on the left of the line formed by p1 and p2
	 * @param p
	 * 			tested point
	 * @param p1
	 * 			first point of the line
	 * @param p2
	 * 			second point of the line
	 * @return true iff the given point p is strictly on the left of the line formed by p1 and p2
	 */
	private boolean isLeftPoint(Point p, Point p1, Point p2) 
	{
		double x = p.x(), y = p.y();
		double x1 = p1.x(), y1 = p1.y();
		double x2 = p2.x(), y2 = p2.y();
		return ((x1 - x) * (y2-y) > (x2-x) * (y1-y));
	}

	/**
	 * Provides a generalized index top
	 * @param index
	 * 			given generalized index
	 * @return generalized index
	 */
	private int generalizedPoint(int index)
	{
		return Math.floorMod(index, points().size());
	}
}
