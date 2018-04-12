package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * CH1903 projection and inverse projection
 * Implements Projection
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class CH1903Projection implements Projection 
{
	/**
	 * CH1903projection of a PointGeo in a Point in the plane
	 * @param PointGeo
	 * 			pointGeo to be projected in a point on the plane
	 * @return Point
	 * 			projection of the given pointGeo on the plane
	 */
	@Override
	public Point project(PointGeo point) 
	{
		double longitude = (1.0/10000) * (Math.toDegrees(point.longitude()) * 3600 - 26782.5);
		double latitude = (1.0/10000) * (Math.toDegrees(point.latitude()) * 3600 - 169028.66);
		double x = 600072.37 + 211455.93 * longitude - 10938.51 * longitude * latitude 
				- 0.36 * longitude * Math.pow(latitude, 2) - 44.54 * Math.pow(longitude, 3);
		double y = 200147.07 + 308807.95 * latitude + 3745.25 * Math.pow(longitude, 2) 
				+ 76.63 * Math.pow(latitude, 2) - 194.56 * Math.pow(longitude, 2) * latitude 
				+ 119.79 * Math.pow(latitude, 3);

		return new Point(x, y);
	}

	/**
	 * Inverse CH1903projection of a Point in the plane in a PointGeo (in spherical coordinates in radians)
	 * @param Point
	 * 			given point of the plane to be projected as a pointGeo
	 * @return PointGeo
	 * 			projection of a point of the plane as a pointGeo
	 */
	@Override
	public PointGeo inverse(Point point) 
	{
		double x1 = (point.x() - 600000) / 1000000;
		double y1 = (point.y() - 200000) / 1000000;
		double longitude0 = 2.6779094 + 4.728982 * x1 + 0.791484 * x1 * y1 
				+ 0.1306 * x1 * Math.pow(y1, 2) - 0.0436 * Math.pow(x1, 3);
		double latitude0 = 16.9023892 + 3.238272 * y1 - 0.270978 * Math.pow(x1, 2) 
				- 0.002528 * Math.pow(y1, 2) - 0.0447 * Math.pow(x1, 2) * y1 
				- 0.0140 * Math.pow(y1,3);
		double longitude = Math.toRadians(longitude0 * (100.0/36));
		double latitude = Math.toRadians(latitude0 * (100.0/36));

		return new PointGeo(longitude, latitude);
	}
}