package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Represents an OSM relation
 * Inherites OSMEntity
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMRelation extends OSMEntity
{
	private final List<Member> members;

	/**
	 * Constructs an relation given its unique identifier, and its members and attributes
	 * @param id
	 * 			given unique identifier linked to the relation
	 * @param members
	 * 			given members of the relation
	 * @param attributes
	 * 			given attributes of the relation
	 */
	public OSMRelation(long id, List<Member> members, Attributes attributes)
	{
		super(id, attributes);
		this.members = Collections.unmodifiableList(new ArrayList<>(members));
	}

	/**
	 * Returns the list of member from the relation
	 * @return List<Member> list of members linked to the relation
	 */
	public List<Member> members()
	{
		return members;
	}

	/**
	 * Represents a Member of the relation in construction
	 * 
	 * @author Diana Petrescu (250971)
	 * @author Clarisse Fleurimont (246866)
	 */
	public final static class Member
	{
		private final Type type;
		private final String role;
		private final OSMEntity member;

		/**
		 * Constructs a member with the given type, role and value
		 * @param type
		 * 			given type of member
		 * @param role
		 * 			given role assimilated to the member in construction
		 * @param member
		 * 			member in question (OSMEntity)
		 */
		public Member(Type type, String role, OSMEntity member)
		{
			this.type = type;
			this.role = role;
			this.member = member;
		}

		/**
		 * Returns the member's type
		 * @return Type
		 * 			member's type
		 */
		public Type type()
		{
			return type;
		}

		/**
		 * Returns the member's role
		 * @return role of the member (String)
		 */
		public String role()
		{
			return role;
		}

		/**
		 * Returns the actual member
		 * @return OSMEntity
		 * 			the member as an OSMEntity
		 */
		public OSMEntity member()
		{
			return member;
		}

		/**
		 * Lists the three types of members a relation can include: Node, Way, Relation
		 */
		public enum Type
		{
			NODE, WAY, RELATION
		}
	}

	public final static class Builder extends OSMEntity.Builder
	{
		private List<Member> members = new ArrayList<Member>();

		/**
		 * Constructs a builder for a relation possessing the given identifier
		 * @param id
		 * 			given unique identifier
		 */
		public Builder(long id)
		{
			super(id);
		}

		/**
		 * Adds a new member of the given type and role in the relation
		 * @param type
		 * 			given type of member
		 * @param role
		 * 			given role of the member in the relation
		 * @param newMember
		 * 			given new member added in the relation
		 */
		public void addMember(Member.Type type, String role, OSMEntity newMember)
		{
			members.add(new Member(type, role, newMember));
		}

		/**
		 * Constructs and returns the relation including the given identifier as well as the added members and relations
		 * @return OSMRelation
		 * 			the relation constructed with the added identifiers, members and relations
		 * @throws IllegalStateException if the relation in construction is incomplete
		 */
		public OSMRelation build() throws IllegalStateException
		{
			if(isIncomplete())
			{
				throw new IllegalStateException("The relation in contruction is incomplete.");
			}
			return new OSMRelation(id, members, attributesBuilder.build());
		}
	}
}