package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class Notre_OSMNodeTest extends Notre_OSMEntityTest 
{

	private static PointGeo p;
	private static OSMNode n;
	
	
	static void initialiser()
	{
		Notre_OSMEntityTest.initialiser();
		p = new PointGeo(0, 0);
		n = new OSMNode(23, p, s);
	}
	
	@Override
	public OSMEntity newOSMEntity(long id, Attributes attributes)
	{
		return new OSMNode(id, new PointGeo(0, 0), attributes);
	}
	
	@Test
	public void positionReturnPosition() 
	{
		initialiser();
		assertEquals(p, n.position());
	}
	
	@Override
	public OSMEntity.Builder newOSMEntityBuilder(long id)
	{
		return new OSMNode.Builder(id, new PointGeo(0, 0));
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void buildCorrectlyThrowsException()
	{
		OSMNode.Builder n = new OSMNode.Builder(23, new PointGeo(0, 0));
		n.setAttribute("natural", "wood");
		n.setIncomplete();
		n.setAttribute("name", "Lac Léman");
		n.build();
	}
	
	@Test
	public void builderCorrectlyBuildsOSMNode()
	{
		OSMNode.Builder n = new OSMNode.Builder(23, new PointGeo(0, 0));
		n.setAttribute("natural", "wood");
		n.setAttribute("name", "Lac Léman");
		OSMNode test = n.build();
		
		Attributes attributes = test.attributes();
		String clefs[] = {"natural", "name"};
		for(int i = 0; i < clefs.length; ++i)
		{
			assertTrue(attributes.contains(clefs[i]));
		}
	}

}
