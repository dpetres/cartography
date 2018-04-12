package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Represents an open PolyLine
 * Inherits PolyLine
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OpenPolyLine extends PolyLine 
{
	/**
	 * Constructs an open polyLine with the given points list
	 * @param points
	 * 			given list of points
	 */
	public OpenPolyLine(List<Point> points) 
	{
		super(points);
	}

	/**
	 * Overrides the isClosed() methode inherited from the mother class
	 * @return false
	 * 			because the polyline is open
	 */
	@Override
	public boolean isClosed() 
	{
		return false;
	}
}