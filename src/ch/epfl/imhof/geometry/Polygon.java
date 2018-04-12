package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Polygon
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Polygon 
{
	private final ClosedPolyLine shell;
	private final List<ClosedPolyLine> holes;

	/**
	 * Constructs a polygon with the given shell and holes
	 * @param shell
	 * 			a given closed polyline forming the shell
	 * @param holes
	 * 			a given list of closed polylines forming holes (in the given shell)
	 */
	public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes)
	{
		this.shell = shell;
		this.holes = Collections.unmodifiableList(new ArrayList<>(holes));
	}

	/**
	 * Constructs a polygone with the given shell, without the holes
	 * @param shell
	 * 			given "empty of holes" shell
	 */
	public Polygon(ClosedPolyLine shell)
	{
		this.shell = shell;
		holes = Collections.emptyList();
	}

	/**
	 * Returns the shell as a closed polyLine
	 * @return shell
	 * 			the closed polyline forming the shell
	 */
	public ClosedPolyLine shell()
	{
		return shell;
	}

	/**
	 * Returns the list of holes
	 * @return holes
	 * 			list of closed polylines forming holes
	 */
	public List<ClosedPolyLine> holes()
	{
		return holes;
	}
}
