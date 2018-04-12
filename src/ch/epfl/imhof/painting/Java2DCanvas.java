package ch.epfl.imhof.painting;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Function;

import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Represents a 2Dimensional canvas on which the map is to be drawn
 * Implements the interface Canvas
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Java2DCanvas implements Canvas
{
	private final Function<Point, Point> projection;
	private final BufferedImage image;
	private final Graphics2D graphic;

	/**
	 * Represents the canvas with the given height, width, resolution and background
	 * @param pBL
	 * 			point bottom left of the canvas
	 * @param pTR
	 * 			point top right of the canvas
	 * @param widthPixel
	 * 			width of the canvas in pixel
	 * @param heightPixel
	 * 			height of the canvas in pixel
	 * @param resolution
	 * 			given resolution of the canvas
	 * @param backgroundColor
	 * 			given background color of the canvas
	 */
	public Java2DCanvas(Point pBL, Point pTR, int widthPixel, int heightPixel, int resolution, Color backgroundColor)
	{
		double factor = resolution/72.0;
		
		this.image = new BufferedImage(widthPixel, heightPixel, BufferedImage.TYPE_INT_RGB);
		this.graphic = image.createGraphics();
		
		graphic.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		
		graphic.scale(factor, factor);
		
		this.projection = Point.alignedCoordinateChange(pBL, new Point(0,heightPixel / factor), pTR, new Point(widthPixel / factor, 0));
		
		graphic.setColor(backgroundColor.toAWTColor());
		
		graphic.fillRect(0, 0, widthPixel, heightPixel);
	}

	@Override
	public void drawPolyLine(PolyLine polyline, LineStyle lineStyle)
	{
		BasicStroke bs;
		if (lineStyle.alternatingSequence().length == 0)
			bs = new BasicStroke(lineStyle.width(), lineStyle.lineCap().ordinal(), lineStyle.linejoin().ordinal(), 
				10.0f, null, 0.0f);
		else 
				bs = new BasicStroke(lineStyle.width(), lineStyle.lineCap().ordinal(), lineStyle.linejoin().ordinal(), 
					10.0f, lineStyle.alternatingSequence(), 0.0f);
		graphic.setStroke(bs);
		graphic.setColor(lineStyle.color().toAWTColor());
		Path2D path = getShapeFromPolyLine(polyline);
		graphic.draw(path);
	}

	@Override
	public void drawPolygon(Polygon polygon, Color color)
	{
		graphic.setColor(color.toAWTColor());
		Path2D path = getShapeFromPolyLine(polygon.shell());
		path.closePath();
		Area area = new Area(path);
		for(ClosedPolyLine c: polygon.holes())
		{
			area.subtract(new Area(getShapeFromPolyLine(c)));
		}
		graphic.fill(area);
	}

	/**
	 * Returns the path shape formed with the given polyline
	 * @param polyline
	 * 			given polyline forming a path
	 * @return a 2Dimentional path
	 */
	private Path2D getShapeFromPolyLine(PolyLine polyline)
	{
		Path2D path = new Path2D.Double();
		Point firstPoint = projection.apply(polyline.firstPoint());
		path.moveTo(firstPoint.x(), firstPoint.y());
		Point pp;
		for(Point p: polyline.points())
		{
			pp = projection.apply(p);
			path.lineTo(pp.x(), pp.y());
		}
		if(polyline.isClosed())
			path.closePath();
		return path;
	}

	/**
	 * Gets the image on the canvas
	 * @return the buffered image of the canvas
	 * @throws IOException
	 */
	public BufferedImage image() throws IOException
	{		
		return this.image;
	}
}