package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class Notre_OSMWayTest extends Notre_OSMEntityTest
{

	private static OSMNode n, noeud, nTest;
	private static long x = 5; 
	private static List<OSMNode> nodes;
	private static OSMWay t;
	
	static void initialiser()
	{
		Notre_OSMEntityTest.initialiser();
		n = new OSMNode(23, new PointGeo(0, 0), s);
		noeud = new OSMNode(22, new PointGeo(0, 1), s);
		nTest = new OSMNode(20, new PointGeo(-1, 0), s);
		nodes = new ArrayList<>();
		nodes.add(0, n);
		nodes.add(1, noeud);
		nodes.add(2, nTest);
		t = new OSMWay(x, nodes, s);
	}
	
	@Override
	public OSMEntity newOSMEntity(long id, Attributes attributes)
	{
		nodes = new ArrayList<>();
		nodes.add(0, n);
		nodes.add(1, noeud);
		nodes.add(2, nTest);
		return new OSMWay(id, nodes, attributes);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructorThrowsException() 
	{
		initialiser();
		List<OSMNode> node = new ArrayList<>();
		node.add(0, n);
		new OSMWay(x, node, s);
	}
	
	@Test
    public void nodeListIsClonedBeforeBeingStored() 
	{
		initialiser();
		nodes.clear();
		assertEquals(3, t.nodes().size());
		assertFalse(t.nodes().isEmpty());
    }
	
	@Test
	public void nodesCountReturnsNodesSize()
	{
		initialiser();
		assertEquals(nodes.size(), t.nodesCount());
	}
	
	@Test
	public void nonRepeatingNodesReturnsCorrectNewList()
	{
		initialiser();
		nodes.add(3, n);
		OSMWay w = new OSMWay(x, nodes, s);
		List<OSMNode> nouveauTab = w.nonRepeatingNodes();
		int tailleNouveauTab = nouveauTab.size();
		assertEquals(3, tailleNouveauTab);
		assertEquals(nTest, nouveauTab.get(tailleNouveauTab-1));
	}
	
	@Test
	public void nonRepeatingNodesReturnsCorrectList()
	{
		initialiser();
		List<OSMNode> memeTab = t.nonRepeatingNodes();
		int tailleNouveauTab = memeTab.size();
		assertEquals(nodes, memeTab);
		assertEquals(3, tailleNouveauTab);
		assertEquals(nTest, memeTab.get(tailleNouveauTab-1));
	}

	@Test
	public void firstNodeReturnsFirstNode()
	{
		initialiser();
		assertEquals(nodes.get(0), t.firstNode());
	}
	
	@Test
	public void lastNodeReturnsLastNode()
	{
		assertEquals(nodes.get(nodes.size()-1), t.lastNode());
	}
	
	@Test
	public void isClosedTest()
	{
		initialiser();
		assertFalse(t.isClosed());
	}
	
	@Override
	public OSMEntity.Builder newOSMEntityBuilder(long id)
	{
		return new OSMWay.Builder(id);
	}
	
	@Test(expected = IllegalStateException.class)
	public void buildCorrectlyThrowsException()
	{
		OSMWay.Builder w = new OSMWay.Builder(23);
		w.addNode(new OSMNode(23, new PointGeo(0, 0), s));
		w.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void buildCorrectlyThrowsExceptionBis()
	{
		OSMWay.Builder w = new OSMWay.Builder(23);
		w.addNode(new OSMNode(23, new PointGeo(0, 0), s));
		w.addNode(new OSMNode(23, new PointGeo(0, 0), s));
		w.addNode(new OSMNode(20, new PointGeo(-1, 0), s));
		w.setIncomplete();
		w.build();
	}
	
	@Test
	public void builderCorrectlyBuildsOSMWay()
	{
		OSMWay.Builder w = new OSMWay.Builder(23);
		OSMNode noeud = new OSMNode(23, new PointGeo(0, 0), s);
		w.addNode(noeud);
		w.addNode(new OSMNode(20, new PointGeo(-1, 0), s));
		OSMWay test = w.build();
		
		List<OSMNode> listNodes = test.nodes();
		assertTrue(listNodes.contains(noeud));
	}

}
