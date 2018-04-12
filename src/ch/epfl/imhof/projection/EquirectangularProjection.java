package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;

/**
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 * 
 */
import ch.epfl.imhof.geometry.Point;

/**
 * Equirectangular and inverse equirectangular projection
 * Implements Projection
 *
 *  @author Diana Petrescu (250971)
 *  @author Clarisse Fleurimont (246866)
 */
public final class EquirectangularProjection implements Projection 
{
	/**
	 * Equirectangular projection of a PointGeo (spherical coordinates) in a Point (Cartesian coordinates)
	 * @param PointGeo
	 * 			given pointGeo in shperical coordinates
	 * @return Point
	 * 			equirectangular projection of the given point in Cartesian coordinates
	 */
	@Override
	public Point project(PointGeo point) 
	{
		return new Point(point.longitude(), point.latitude());
	}

	/**
	 * Inverse equirectangular projection of a point in the plane in a PointGeo (in spherical coordinates in radians)
	 * @param Point
	 * 			given point of the plane  
	 * @return PointGeo
	 * 			inverse equirectangular projection of the given point of the plane as a pointGeo 
	 */
	@Override
	public PointGeo inverse(Point point) 
	{
		return new PointGeo(point.x(), point.y());
	}
}