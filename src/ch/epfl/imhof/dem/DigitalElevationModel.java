package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Represents a numerical model of the ground of the map
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public interface DigitalElevationModel extends AutoCloseable
{
	/**
	 * Takes a point in WGS 84 as argument and returns the normal vector to the Earth at this point
	 * @param p
	 * 			given point in WGS 84
	 * @return a three dimensional vector normal to the Earth at the given point
	 * @throws IllegalArgumentException iff the point is not part of the area covered by the MNT
	 */
	public Vector3 normalAt(PointGeo p) throws IllegalArgumentException;
}
