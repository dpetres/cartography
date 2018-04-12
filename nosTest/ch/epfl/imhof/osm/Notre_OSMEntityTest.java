package ch.epfl.imhof.osm;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import ch.epfl.imhof.Attributes;

public abstract class Notre_OSMEntityTest 
{
	
	protected static Map<String, String > m;
	protected static Attributes s;
	
	static void initialiser()
	{
		m = new HashMap<>();
		m.put("natural", "wood");
		m.put("name", "Lac Léman");
		m.put("entier", "456");
		s = new Attributes(m);
	}

	abstract OSMEntity newOSMEntity(long id, Attributes attributes);
	
	
	@Test
	public void idReturnsCorrectId()
	{
		initialiser();
		OSMEntity test = newOSMEntity(3, s);
		assertEquals(3, test.id());
	}

	@Test
	public void attributesReturnsCorrectAttributes()
	{
		initialiser();
		OSMEntity test = newOSMEntity(3, s);
		assertEquals(s, test.attributes());
	}
	
	@Test
	public void hasAttributeTest()
	{
		initialiser();
		OSMEntity test = newOSMEntity(3, s);
		assertTrue(test.hasAttribute("natural"));
		assertTrue(test.hasAttribute("entier"));
		assertFalse(test.hasAttribute("salut"));
		
	}
	
	@Test
	public void attributeValueTest()
	{
		initialiser();
		OSMEntity test = newOSMEntity(3, s);
		assertEquals("wood", test.attributeValue("natural"));
		assertEquals(null, test.attributeValue("salut"));
		
	}
	
	abstract OSMEntity.Builder newOSMEntityBuilder(long id);
	
	@Test
	public void builderIsIncompleteTest()
	{
		OSMEntity.Builder o = newOSMEntityBuilder(3);
		o.setAttribute("natural", "wood");
		o.setIncomplete();
		assertTrue(o.isIncomplete());
	}

}
