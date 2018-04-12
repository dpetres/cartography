package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member;

public class Notre_OSMRelationTest extends Notre_OSMEntityTest
{
	private static List<Member> member;
	private static OSMRelation osmRelation;
	
	static void initialiser()
	{
		Notre_OSMEntityTest.initialiser();
		member = new ArrayList<>(Arrays.asList(
                new Member(OSMRelation.Member.Type.NODE, "salut", new OSMNode(23, new PointGeo(1,0), s)),
                new Member(OSMRelation.Member.Type.WAY, "salut", new OSMNode(23, new PointGeo(1,0), s)),
                new Member(OSMRelation.Member.Type.RELATION, "salut", new OSMNode(23, new PointGeo(1,0), s))));
		osmRelation = new OSMRelation(3, member, s);
	}
	
	@Override
	public OSMEntity newOSMEntity(long id, Attributes attributes)
	{
		return new OSMRelation(id, member = new ArrayList<>(Arrays.asList(
                new Member(OSMRelation.Member.Type.NODE, "salut", new OSMNode(23, new PointGeo(1,0), s)),
                new Member(OSMRelation.Member.Type.WAY, "salut", new OSMNode(23, new PointGeo(1,0), s)),
                new Member(OSMRelation.Member.Type.RELATION, "salut", new OSMNode(23, new PointGeo(1,0), s)))), attributes);
	}

	@Test
    public void memberListIsClonedBeforeBeingStored() 
	{
		initialiser();
		member.clear();
		assertEquals(3, osmRelation.members().size());
		assertFalse(osmRelation.members().isEmpty());
    }
	
	@Override
	public OSMEntity.Builder newOSMEntityBuilder(long id)
	{
		return new OSMRelation.Builder(id);
	}

	@Test(expected = IllegalStateException.class)
	public void buildCorrectlyThrowsException()
	{
		OSMRelation.Builder r = new OSMRelation.Builder(23);
		r.addMember(OSMRelation.Member.Type.NODE, "salut", new OSMNode(23, new PointGeo(1,0), s));
		r.setIncomplete();
		r.build();;
	}
	
	@Test
	public void builderCorrectlyBuildsOSMRelation()
	{
		OSMRelation.Builder r = new OSMRelation.Builder(23);
		r.addMember(OSMRelation.Member.Type.NODE, "salut", new OSMNode(23, new PointGeo(1,0), s));
		r.addMember(OSMRelation.Member.Type.WAY, "salut", new OSMNode(23, new PointGeo(1,0), s));
		OSMRelation test = r.build();
		assertEquals(2, test.members().size());
		assertEquals(OSMRelation.Member.Type.NODE, test.members().get(0).type());
		assertEquals("salut", test.members().get(0).role());
	}

}