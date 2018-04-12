package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Projection interface
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public interface Projection 
{
	/**
	 * Public abstract projection methode, will be override
	 * @param point
	 * 			given pointGeo
	 * @return a projection of the given point
	 */
	Point project(PointGeo point);

	/**
	 * Public abstract inverse projection methode, will be override
	 * @param point
	 * 			given point of the plane
	 * @return a projection of the given point
	 */
	PointGeo inverse(Point point);
}