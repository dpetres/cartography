package ch.epfl.imhof;

import static org.junit.Assert.*;

import ch.epfl.imhof.Attributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class Notre_AttributesTest 
{
	private static Map<String, String > m;
	private static Attributes s;
	
	private static void initialiser()
	{
		m = new HashMap<>();
		m.put("natural", "wood");
		m.put("name", "Lac Léman");
		m.put("entier", "456");
		s = new Attributes(m);
	}
	
	@Test
	public void mapIsClonedBeforeBeeingStored() 
	{	
		initialiser();
		m.clear();
		assertEquals("wood", s.get("natural"));
	}
	
	@Test
	public void isEmptyReturnsBoolean()
	{
		Map<String, String> m = new HashMap<>();
		Attributes s = new Attributes(m);
		assertTrue(s.isEmpty());
	}
	
	@Test
	public void containsKeyReturnsBoolean()
	{
		initialiser();
		assertTrue(s.contains("natural"));
	}
	
	@Test
	public void getReturnsRightValue()
	{
		initialiser();
		assertEquals("Lac Léman", s.get("name"));
	}
	
	@Test
	public void getOrDefaultValueReturnsRightValue()
	{
		initialiser();
		assertEquals("Lac Léman", s.get("name","coucou"));
		assertEquals("coucou", s.get("salut","coucou"));
	}
	
	@Test
	public void getReturnsValueToInt()
	{
		initialiser();
		assertEquals(456, s.get("entier", 2));
		assertEquals(2, s.get("salut", 2));
		assertEquals(23, s.get("name", 23));
	}
	
	@Test
	public void keepOnlyKeysReturnsRightAttributes()
	{
		Set<String> set = new HashSet<>();
		set.add("natural");
		set.add("name");
		set.add("place");
		set.add("lieu");
		
		Map<String, String> m = new HashMap<>();
		m.put("natural", "456");
		m.put("name", "Lac Léman");
		m.put("place", "Suisse");
		m.put("Nom", "salut");
		Attributes s = new Attributes(m);
		
		Attributes test = s.keepOnlyKeys(set);
		assertFalse(test.contains("Nom"));
		assertFalse(test.contains("lieu"));
		assertTrue(test.contains("natural"));
		assertEquals("Lac Léman", test.get("name"));
	}
	
	@Test
	public void builderCorrectlyBuildsAttributes()
	{
		Attributes.Builder b = new Attributes.Builder();
		Attributes s = b.build();
		assertTrue(s.isEmpty());
		
		String clefs[] = {"clef", "key", "ele"};
		String valeurs[] = {"valeur", "value", "372"};
		for(int i = 0; i < clefs.length; ++i)
		{
			b.put(clefs[i], valeurs[i]);
		}
		
		s = b.build();
		for(int i = 0; i < clefs.length; ++i)
		{
			assertTrue(s.contains(clefs[i]));
		}
		assertFalse(s.contains("name"));
		assertFalse(s.isEmpty());
	}
}
