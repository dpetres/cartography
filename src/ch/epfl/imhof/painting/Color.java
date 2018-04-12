package ch.epfl.imhof.painting;

/**
 * A color represented by it's components red, greeen and blue.
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)q
 */
public final class Color 
{
	private final double r, g, b;

	/**
	 * Color « red » (pur).
	 */
	public final static Color RED = new Color(1,0,0);

	/**
	 * Color « green » (pur).
	 */
	public final static Color GREEN = new Color(0,1,0);

	/**
	 * La couleur « bleu » (pur).
	 */
	public final static Color BLUE = new Color(0,0,1);

	/**
	 * Color « black ».
	 */
	public final static Color BLACK = new Color(0,0,0);

	/**
	 * Color « white ».
	 */
	public final static Color WHITE = new Color(1,1,1);

	/**
	 * Creates the color given the value of red green and blue
	 * @param r
	 * 			value of red
	 * @param g
	 * 			value of green
	 * @param b
	 * 			value of blue
	 * @throws IllegalArgumentException iff the given values of the colors are out of bound
	 */
	private Color(double r, double g, double b) throws IllegalArgumentException
	{
		if (! (0.0 <= r && r <= 1.0))
			throw new IllegalArgumentException("invalid red component: " + r);
		if (! (0.0 <= g && g <= 1.0))
			throw new IllegalArgumentException("invalid green component: " + g);
		if (! (0.0 <= b && b <= 1.0))
			throw new IllegalArgumentException("invalid blue component: " + b);

		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Defines the color grey
	 * @param c
	 * 			the given value of the color red green and blue
	 * @return a grey color
	 * @throws IllegalArgumentException if c is out of range
	 */
	public static Color gray(double c) throws IllegalArgumentException
	{
		return new Color(c, c, c);
	}

	/**
	 * Creates the color given the value of the color red green and blue
	 * @param r
	 * 			value of the color red
	 * @param g
	 * 			value of the color green
	 * @param b
	 * 			value of the color blue
	 * @return a new color given the proportion of red green and blue
	 * @throws IllegalArgumentException if the given colors are out of bound
	 */
	public static Color rgb(double r, double g, double b) throws IllegalArgumentException
	{
		return new Color(r, g, b);
	}

	/**
	 * Constructs a color by "unwrapping" the three component RGB each stocked on 8 bits. The R component is supposed to occupy
	 * the bits from 23 to 16, the G component occupies the bits from 15 to 8 and B occupies the bits from 7 to 0. The components 
	 * are supposed to be gamma-encoded following the sRGB standard
	 * @param packedRGB
	 *            the encoded color, on RGB format
	 */
	public static Color rgb(int packedRGB) 
	{
		double r = (double)((packedRGB >> 16) & 0xFF) / 255d;
		double g = (double)((packedRGB >> 8) & 0xFF) / 255d; 
		double b = (double)((packedRGB >> 0) & 0xFF) / 255d;
		return new Color(r, g, b);
	}

	/**
	 * Returns the red component of the color
	 * @return the red component of the color (double)
	 */
	public double r() { return r; }

	/**
	 * Returns the green component of the color
	 * @return the green component of the color (double)
	 */
	public double g() { return g; }

	/**
	 * Returns the blue component of the color
	 * @return the blue component of the color (double)
	 */
	public double b() { return b; }

	public Color multiplyColors(Color c)
	{
		return new Color(this.r * c.r, this.g * c.g, this.b * c.b);
	}

	/**
	 * Converts the color in an AWT color
	 * @return the AWT color corresponding to the given color
	 */
	public java.awt.Color toAWTColor() 
	{
		return new java.awt.Color((float)r,(float)g,(float)b);
	}

	/**
	 * Gives the packed value of the color given the value of red green and blue
	 * @param r
	 * 			red component of the color
	 * @param g
	 * 			green component of the color
	 * @param b
	 * 			blue component of the color
	 * @return an integer representing the color given the value of red green and blue
	 */
	public static int packedColor(double r, double g, double b)
	{
		int r0 = (int)(r * 255.9999);
		int g0 = (int)(g * 255.9999);
		int b0 = (int)(b * 255.9999);
		return ((r0 << 16) | (g0 << 8) | b0);
	}
}