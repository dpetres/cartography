package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * Represents all the specifications in order to draw roads
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class RoadSpec 
{
	private final Predicate<Attributed<?>> filter;
	private final float wi;
	private final Color ci;
	private final float wc;
	private final Color cc;
	private final static Predicate<Attributed<?>> FBRIDGE = Filters.tagged("bridge");
	private final static Predicate<Attributed<?>> FTUNNEL = Filters.tagged("tunnel");

	/**
	 * Sets the characteristics of the road
	 * @param filter
	 * 			predicate that selects the type of road to be painted
	 * @param wi
	 * 			width of the inner line
	 * @param ci
	 * 			color of the inner line
	 * @param wc
	 * 			width of the outer line
	 * @param cc
	 * 			color of the outer line
	 */
	public RoadSpec(Predicate<Attributed<?>> filter, float wi, Color ci, float wc, Color cc)
	{
		this.filter = filter;
		this.wi = wi;
		this.ci = ci;
		this.wc = wc;
		this.cc = cc;
	}

	/**
	 * Generates a painter drawing all inner bridges of the map on the canvas
	 * @return painter drawing the inner bridges
	 */
	public Painter pBridgeInner()
	{
		Predicate<Attributed<?>> f = filter.and(FBRIDGE);
		return Painter.line(LineStyle.LineCap.ROUND, LineStyle.LineJoin.ROUND, ci, wi).when(f);
	}

	/**
	 * Generates a painter drawing all outer bridges of the map on the canvas
	 * @return painter drawing the outer bridges
	 */
	public Painter pBridgeOuter()
	{
		Predicate<Attributed<?>> f = filter.and(FBRIDGE);
		return Painter.line(LineStyle.LineCap.BUTT, LineStyle.LineJoin.ROUND, cc, (wi + 2*wc)).when(f);
	}

	/**
	 * Generates a painter drawing all inner roads of the map on the canvas
	 * @return painter drawing the inner roads
	 */
	public Painter pRoadInner()
	{
		Predicate<Attributed<?>> f = filter.and(FBRIDGE.negate().and(FTUNNEL.negate()));
		return Painter.line(LineStyle.LineCap.ROUND, LineStyle.LineJoin.ROUND, ci, wi).when(f);
	}

	/**
	 * Generates a painter drawing all outer roads of the map on the canvas
	 * @return painter drawing the outer roads
	 */
	public Painter pRoadOuter()
	{
		Predicate<Attributed<?>> f = filter.and(FBRIDGE.negate().and(FTUNNEL.negate()));
		return Painter.line(LineStyle.LineCap.ROUND, LineStyle.LineJoin.ROUND, cc, (wi + 2*wc)).when(f);
	}

	/**
	 * Generates a painter drawing all tunnels of the map on the canvas
	 * @return painter drawing the tunnels
	 */
	public Painter pTunnel()
	{
		Predicate<Attributed<?>> f = filter.and(FTUNNEL);
		return Painter.line(LineStyle.LineCap.BUTT, LineStyle.LineJoin.ROUND, cc, (wi/2.0f), 2*wi, 2*wi).when(f);
	}
}
