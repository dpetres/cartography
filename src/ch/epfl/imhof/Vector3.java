package ch.epfl.imhof;

/**
 * Represents a three dimensional vector
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Vector3 
{
	private final double x;
	private final double y;
	private final double z;
	
	public Vector3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates and returns the norm of the vector
	 * @return the norm of the vector
	 */
	public double norm()
	{
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
	}
	
	/**
	 * Gives the normalized version of the vector 
	 * (i.e., a vector parallel and of the same direction but of length one)
	 * @return a three dimensional vector of length one, parallel to the given vector and of same direction
	 */
	public Vector3 normalized()
	{
		double norm = this.norm();
		return new Vector3(this.x/norm, this.y/norm, this.z/norm);
	}

	/**
	 * Returns the dot product between the receiver and a second given vector
	 * @param vector
	 * 			the given second vector
	 * @return (double) the dot product between the given vector and the receiver
	 */
	public double scalarProduct(Vector3 vector)
	{
		return this.x*vector.x + this.y*vector.y + this.z*vector.z;
	}
}
