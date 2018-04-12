package ch.epfl.imhof.painting;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * Painter interface
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public interface Painter 
{
	/**
	 * Draws the given map on the given canvas.
	 * @param map
	 * 			given map to be drawn on the given canvas
	 * @param canvas
	 * 			canvas on which the map is drawn
	 */
	public void drawMap(Map map, Canvas canvas);
	
	/**
	 * Colors a polygon with the given color
	 * @param color
	 * 			given color of the polygon
	 * @return painter that colors the polygon in the given color
	 */
	public static Painter polygon(Color color)
	{
		return (map, canvas) ->
		{
			for(Attributed<Polygon> p : map.polygons())
			{
				canvas.drawPolygon(p.value(), color);
			}
		};
	}
	
	/**
	 * Draws all given lines of the map with the given line style.
	 * @param lineStyle
	 * 			the line style used to draw on the map
	 * @return painter that draws the lines as described above
	 */
	public static Painter line(LineStyle lineStyle)
	{
		return (map, canvas) ->
		{
			for (Attributed<PolyLine> polylines : map.polyLines()) 
			{
				canvas.drawPolyLine(polylines.value(), lineStyle);
			}
		};
	}
	
	/**
	 * Draws all given lines of the map with the given line style arguments.
	 * @param lineCap
	 * @param lineJoin
	 * @param color
	 * 			given color of the lines to be drawn on the map
	 * @param width
	 * 			given width of the lines to be drawn on the map
	 * @param alternatingSequence
	 * 			represents the size of the gap between each sequence if the line is an alternative sequence
	 * @return painter that draws the lines as described above
	 */
	public static Painter line(LineCap lineCap, LineJoin lineJoin, Color color, float width, float... alternatingSequence)
	{
		return Painter.line(new LineStyle(lineCap, lineJoin, color, width, alternatingSequence));
	}
	
	/**
	 * Draws all given lines of the map with the given color and width and the default other line styles arguments.
	 * @param width
	 * 			given width of the line
	 * @param color
	 * 			given color of the line
	 * @return painter that draws the lines as described above
	 */
	public static Painter line(float width, Color color)
	{
		return Painter.line(new LineStyle(color, width));
	}
	
	/**
	 * Draws all outlines from the given map on the given canvas
	 * @param lineStyle 
	 * 				given line style used to draw the line on the canvas
	 * @return painter that draws the outlines on given line style on the map
	 */
	public static Painter outline(LineStyle lineStyle)
	{
		return (map, canvas) ->
		{
			for (Attributed<Polygon> attPolygon : map.polygons()) 
			{
				canvas.drawPolyLine(attPolygon.value().shell(), lineStyle);
				for (ClosedPolyLine hole : attPolygon.value().holes()) 
				{
					canvas.drawPolyLine(hole, lineStyle);
				}
			}
		};
	}

	/**
	 * Paints the outline on the canvas given the line style
	 * @param lineCap
	 * @param lineJoin
	 * @param color
	 * 			given color of the line
	 * @param width
	 * 			given width of the line
	 * @param alternatingSequence
	 * 			given size of the gap between each sequence if the line is an alternative sequence
	 * @return painter that draws the lines of the map on the canvas given the line style
	 */
	public static Painter outline(LineCap lineCap, LineJoin lineJoin, Color color, float width, float... alternatingSequence)
	{
		return Painter.outline(new LineStyle(lineCap, lineJoin, color, width, alternatingSequence));
	}
	
	/**
	 * Creates a painter that draws the line of the map on the canvas given the color and the width
	 * @param width
	 * 			given width of the line
	 * @param color
	 * 			given color of the line
	 * @return painter that draws the lines on the canvas
	 */
	public static Painter outline(float width, Color color)
	{
		return Painter.outline(new LineStyle(color, width));
	}
	
	/**
	 * Returns a painter acting as the given one but that satisfies the given predicate
	 * @param predicate
	 * 			given predicate
	 * @return painter that satisfies the given predicate
	 */
	public default Painter when(Predicate<Attributed<?>> predicate)
	{
		return (map, canvas) ->
		{
			List<Attributed<PolyLine>> lPolyLines = map.polyLines().stream().filter(predicate).collect(Collectors.toList());
			List<Attributed<Polygon>> lPolygons = map.polygons().stream().filter(predicate).collect(Collectors.toList());
			this.drawMap(new Map(lPolyLines, lPolygons), canvas);
		};
	}
	
	/**
	 * Draws the given painter under the used painter
	 * @param painter
	 * 			given painter
	 * @return painter that combines the two given painters
	 */
	public default Painter above(Painter painter)
	{
		return (map, canvas) ->
		{
			painter.drawMap(map, canvas);
			this.drawMap(map, canvas);
		};
	}
	
	/**
	 * Creates a painter using the layer attribute attached to the entity of the map and drawing them by layers 
	 * drawing the layers from -5 to +5
	 * @return painter drawing the layers one by one
	 */
	public default Painter layered()
	{
		Painter painter = this.when(Filters.onLayer(-5));
		for (int i = -4; i <= 5; i++) 
		{
			painter = this.when(Filters.onLayer(i)).above(painter);
		}
		return painter;
	}
}
