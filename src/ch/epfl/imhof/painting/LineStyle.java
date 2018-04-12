package ch.epfl.imhof.painting;

/**
 * Class defining the style of line on the graphic map. Regroups 
 * all useful style parameters in order to draw a line.
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class LineStyle 
{
	/**
	 * Lists the three types of members a relation can include: Node, Way, Relation
	 */
	public enum LineCap
	{
		BUTT, ROUND, SQUARE
	}
	
	/**
	 * Lists the three types of members a relation can include: Node, Way, Relation
	 */
	public enum LineJoin
	{
		BEVEL, MITER, ROUND
	}
	
	private final LineCap lineCap;
	private final LineJoin lineJoin;
	private final Color color;
	private final float width;
	private final float[] alternatingSequence;
	
	/**
	 * Principal constructor creates the line style given the five following parameters.
	 * Throws an IllaegalArgumentException if the values are out of the range [0;1].
	 * 
	 * @param lineCap
	 * 			the given cap of the line
	 * @param lineJoin
	 * 			the given type of joint between the lines
	 * @param color
	 * 			the given color of the line
	 * @param width
	 * 			the given width of the line
	 * @param alternatingSequence
	 * 			represents the size of the gap between each sequence if the line is an alternative sequence
	 * @throws IllegalArgumentException
	 */
	public LineStyle(LineCap lineCap, LineJoin lineJoin, Color color, float width, float[] alternatingSequence) throws IllegalArgumentException
	{
		if(width < 0)
			throw new IllegalArgumentException("The thickness must be positive");
		for(Float f: alternatingSequence)
		{
			if(f <= 0)
				throw new IllegalArgumentException("The alternating sequence must contain only positive numbers");
		}
		this.lineCap = lineCap;
		this.lineJoin = lineJoin;
		this.color = color;
		this.width = width;
		this.alternatingSequence = alternatingSequence;
	}
	
	/**
	 * Secondary constructor taking the default values, butt, miter and alternating sequence
	 * @param color
	 * 			given color of the line
	 * @param width
	 * 			given width of the line
	 * @throws IllegalArgumentException
	 */
	public LineStyle(Color color, float width) throws IllegalArgumentException
	{
		this(LineCap.BUTT, LineJoin.MITER, color, width, new float[0]);
	}
	
	/**
	 * Gives the cap style
	 * @return lineCap
	 */
	public LineCap lineCap()
	{
		return this.lineCap;
	}
	
	/**
	 * Give the join style
	 * @return lineJoin
	 */
	public LineJoin linejoin()
	{
		return this.lineJoin;
	}
	
	/**
	 * Gives the color
	 * @return color
	 */
	public Color color()
	{
		return this.color;
	}
	
	/**
	 * Gives the width
	 * @return width
	 */
	public float width()
	{
		return this.width;
	}
	
	/**
	 * Gives the alternating sequence type
	 * @return alternatingSequence
	 */
	public float[] alternatingSequence()
	{
		return this.alternatingSequence;
	}
	
	/**
	 * Creates a line style with the given cap style
	 * @param lineCap
	 * @return new line style with the given cap style
	 */
	public LineStyle withLineCap(LineCap lineCap)
	{
		return new LineStyle(lineCap, this.lineJoin, this.color, this.width, this.alternatingSequence); //même question this. ?
	}
	
	/**
	 * Creates a line style with the given join style
	 * @param lineJoin
	 * @return new line style with the given join style
	 */
	public LineStyle withLineJoin(LineJoin lineJoin)
	{
		return new LineStyle(this.lineCap, lineJoin, this.color, this.width, this.alternatingSequence);
	}
	
	/**
	 * Creates a line style with the given color
	 * @param color
	 * @return LineStyle with the given color
	 */
	public LineStyle withColor(Color color)
	{
		return new LineStyle(this.lineCap, this.lineJoin, color, this.width, this.alternatingSequence);
	}
	
	/**
	 * Creates a line style with the given width
	 * @param width
	 * @return LineStyle with the given width
	 */
	public LineStyle withWidth(float width)
	{
		return new LineStyle(this.lineCap, this.lineJoin, this.color, width, this.alternatingSequence);
	}
	
	/**
	 * Creates a line style with the given alternating sequence style
	 * @param alternatingSequence
	 * @return LineStyle with a (given) alternative sequence
	 */
	public LineStyle withalternatingSequence( float[] alternatingSequence)
	{
		return new LineStyle(this.lineCap, this.lineJoin, this.color, this.width, alternatingSequence);
	}
}