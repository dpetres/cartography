package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Interface allowing to draw polygons and ploylines on a canvas
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public interface Canvas 
{
	/**
	 * Draws polylines on the canvas with the given line style
	 * @param polyline
	 * 			given polyline to be drawn
	 * @param lineStyle
	 * 			given line style
	 */
	public void drawPolyLine(PolyLine polyline, LineStyle lineStyle);
	
	/**
	 * Draws polygons on the canvas with the given color
	 * @param polygon
	 * 			given polygon to be drawn
	 * @param color
	 * 			given to color to be used to draw the polygon
	 */
	public void drawPolygon(Polygon polygon, Color color);
}
