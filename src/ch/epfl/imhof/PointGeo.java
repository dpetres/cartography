package ch.epfl.imhof;

import java.lang.Math;

/**
 * Represents a point on the surface of the Earth in spherical coordinates (in the WSG 84 system)
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class PointGeo 
{
	private final double longitude;
	private final double latitude;

	/**
	 * Constructs a point with the given longitude and latitude (in radians)
	 * @param longitude
	 * 			Given longitude of the point to be constructed
	 * @param latitude
	 * 			Given latitude of the point to be constructed
	 * @throws an IllegalArgumentException if the longitude is out of the bound [−π;π]
	 * @throws an IllegalArgumentException if the latitude is out of the bound [−π/2;π/2]
	 */
	public PointGeo(double longitude, double latitude) throws IllegalArgumentException 
	{
		// Tests if the longitude is out of the given bound
		if(longitude < -Math.PI || longitude > Math.PI)
		{
			throw new IllegalArgumentException("Longitude invalid: " + longitude + "(out of bound [−π;π].)");
		}

		this.longitude = longitude;

		// Tests if the latitude is out of the given bound
		if(latitude < -Math.PI/2 || latitude > Math.PI/2)
		{
			throw new IllegalArgumentException("Latitude invalid: " + longitude + "(out of bound [−π/2;π/2].)");
		}

		this.latitude = latitude;
	}

	/**
	 * Returns the longitude's point value in radians
	 * @return longitude
	 * 			Longitude of the point
	 */
	public double longitude() 
	{
		return longitude;
	}

	/**
	 * Returns the latitude's point value in radians
	 * @return latitude
	 * 			Latitude of the point
	 */
	public double latitude() 
	{
		return latitude;
	}
}