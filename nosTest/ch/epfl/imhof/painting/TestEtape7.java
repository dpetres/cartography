package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.Polygon;

public class TestEtape7 
{
	private static ClosedPolyLine s = new ClosedPolyLine(
            Arrays.asList(
                    new Point(36, 54),
                    new Point(-634, 96),
                    new Point(36, 54)));
	private static Polygon p =  new Polygon(s);
	private static Map<String, String> m = new HashMap<>();

	@Test
	public void alignedCoordinateChangeTest()
	{
		Function<Point, Point> blueToRed =
			    Point.alignedCoordinateChange(new Point(1, -1),
			                                  new Point(5, 4),
			                                  new Point(-1.5, 1),
			                                  new Point(0, 0));
		Point p = blueToRed.apply(new Point(0, 0));
		assertEquals(3.0, p.x(), 0);
		assertEquals(2.0, p.y(), 0);
	}
	
	@Test
	public void hey()
	{
		m.put("natural", "water");
		m.put("name", "Lac Léman");
		m.put("place", "Suisse");
		m.put("layer", "2");
		Attributes s1 = new Attributes(m);
		Attributed<Polygon> attPoly = new Attributed<>(p, s1);
		Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water", "10000");
		Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
		Predicate<Attributed<?>> onLayer2 = Filters.onLayer(2);
		
		assertFalse(isBuilding.test(attPoly));
		assertTrue(isLake.test(attPoly));
		assertTrue(onLayer2.test(attPoly));
	}

}
