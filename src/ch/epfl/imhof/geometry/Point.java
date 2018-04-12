package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * Represents a point in the plan in cartesian coordinates
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Point 
{
	private final double x;
	private final double y;

	/**
	 * Constructs a point with the given coordinates
	 * @param x
	 * 			horizontal value of the points coordinates
	 * @param y
	 * 			vertical value of the points coordinates
	 */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the value of x (horizontal)
	 * @return x
	 * 			horizontal value of the points coordinates
	 */
	public double x() 
	{
		return x;
	}

	/**
	 * Returns the value of y (vertical)
	 * @return y
	 * 			vertical value of the points coordinates
	 */
	public double y() 
	{
		return y;
	}
	
	/**
	 * Changes the landmark given two points with old and new coordinates
	 * 
	 * @param p1Old
	 * 			first point of the "old" landmark
	 * @param p1New
	 * 			first point of the "new" landmark
	 * @param p2Old
	 * 			second point of the "old" landmark
	 * @param p2New
	 * 			second point of the "new" landmark
	 * @return a new Function of points
	 * @throws IllegalArgumentException if the given points are aligned
	 */
	public static Function<Point, Point> alignedCoordinateChange(Point p1Old, Point p1New, Point p2Old, Point p2New) throws IllegalArgumentException
	{
		double x1Old = p1Old.x();
		double y1Old = p1Old.y();
		double x1New = p1New.x();
		double y1New = p1New.y();
		double x2Old = p2Old.x();
		double y2Old = p2Old.y();
		double x2New = p2New.x();
		double y2New = p2New.y();
		double v, h, w, l;
		
		if(x1Old == x2Old || y1Old == y2Old)
		{
			throw new IllegalArgumentException("Les points se trouvent sur la même ligne horizontale et/ou verticale.");
		}
		
		v = (x1New - x2New) / (x1Old - x2Old);
		h = x1New - v * x1Old;
		
		w = (y1New - y2New) / (y1Old - y2Old);
		l = y1New - w * y1Old;
		
		return p -> new Point(p.x() * v + h, p.y() * w + l);
	}
}