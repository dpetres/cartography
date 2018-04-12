package ch.epfl.imhof;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.Polygon;

public class Notre_AttributedTest 
{
	private static ClosedPolyLine s;
	private static Polygon p;
	private static Map<String, String> m;
	private static Attributes s1;
	private static Attributed<Polygon> attPoly;
	
	private static void initialiser()
	{
		s = new ClosedPolyLine(
	            Arrays.asList(
	                    new Point(36, 54),
	                    new Point(-634, 96),
	                    new Point(36, 54)));
		p = new Polygon(s);
		m = new HashMap<>();
		m.put("natural", "456");
		m.put("name", "Lac Léman");
		m.put("place", "Suisse");
		s1 = new Attributes(m);
		attPoly = new Attributed<>(p, s1);
	}
    
	@Test
	public void valueAndAttributesGetterReturnsValueAndAttributes() 
	{
		initialiser();
		assertEquals(p, attPoly.value());
		assertEquals(s1, attPoly.attributes());
	}
	
	@Test
	public void hasAttributeReturnsBoolean()
	{
		initialiser();
		assertTrue(attPoly.hasAttribute("natural"));
		assertFalse(attPoly.hasAttribute("Fred"));	
	}
	
	@Test
	public void attributeValueReturnsRightValue()
	{
		initialiser();
		assertEquals("Lac Léman", attPoly.attributeValue("name"));
		assertEquals("Lac Léman", attPoly.attributeValue("name","coucou"));
		assertEquals("coucou", attPoly.attributeValue("salut","coucou"));
		assertEquals(456, attPoly.attributeValue("natural", 2));
		assertEquals(2, attPoly.attributeValue("salut", 2));
		assertEquals(23, attPoly.attributeValue("name", 23));
	}
	
	@Test
	public void attributeValueWithArgsStringStringReturnsRightValue()
	{
		initialiser();
		assertEquals("Lac Léman", attPoly.attributeValue("name","coucou"));
		assertEquals("coucou", attPoly.attributeValue("salut","coucou"));
	}
	
	@Test
	public void attributeValueWithArgsStringIntReturnsRightValue()
	{
		initialiser();
		assertEquals(456, attPoly.attributeValue("natural", 2));
		assertEquals(2, attPoly.attributeValue("salut", 2));
		assertEquals(23, attPoly.attributeValue("name", 23));
	}


}
