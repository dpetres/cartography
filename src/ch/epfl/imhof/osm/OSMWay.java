package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Represents a way
 * Inherites OSMEntity
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMWay extends OSMEntity
{
	private final List<OSMNode> nodes;

	/**
	 * Constructs a way given its unique identifier, nodes and attributes
	 * @param id
	 * 			given unique identifier
	 * @param nodes
	 * 			given list of nodes
	 * @param attributes
	 * 			given attributes
	 * @throws an IllegalArgumentException if the nodes list has less than two elements
	 */
	public OSMWay(long id, List<OSMNode> nodes, Attributes attributes) throws IllegalArgumentException
	{
		super(id, attributes);
		if(nodes == null || nodes.size() < 2)
		{
			throw new IllegalArgumentException("The list contains at less than two nodes.");
		}
		this.nodes = Collections.unmodifiableList(new ArrayList<>(nodes));
	}

	/**
	 * Returns the number of nodes in the way
	 * @return number of nodes in the way (int)
	 */
	public int nodesCount()
	{
		return nodes.size();
	}

	/**
	 * Returns the list of nodes of the way
	 * @return List<OSMNode> list of nodes of the way
	 */
	public List<OSMNode> nodes()
	{
		return nodes;
	}

	/**
	 * Returns the list of nodes of the way without the last node if it is the same as the first
	 * @return List<OSMNode> list of nodes of the way without the last node if it is the same as the first
	 */
	public List<OSMNode> nonRepeatingNodes()
	{
		if(isClosed())
		{
			return nodes.subList(0, nodesCount()-1);
		}
		return nodes;
	}

	/**
	 * Returns the first node of the way
	 * @return OSMNode
	 * 			first node of the way
	 */
	public OSMNode firstNode()
	{
		return nodes.get(0);
	}

	/**
	 * Returns the last node of the way
	 * @return OSMNode
	 * 			last node of the way
	 */
	public OSMNode lastNode()
	{
		return nodes.get(nodesCount()-1);
	}

	/**
	 * Returns true iff the way is closed
	 * @return true iff the way is closed
	 */
	public boolean isClosed()
	{
		return (firstNode().equals(lastNode()));
	}

	public final static class Builder extends OSMEntity.Builder
	{
		private List<OSMNode> nodes = new ArrayList<>();

		/**
		 * Constructs a builder for the way possessing the given identifier
		 * @param id
		 * 			given unique identifier
		 */
		public Builder(long id)
		{
			super(id);
		}

		/**
		 * Adds a node at the end of the nodes of the way in construction
		 * @param newNode
		 * 			new node added to the list to create the way
		 */
		public void addNode(OSMNode newNode)
		{
			nodes.add(newNode);
		}

		/**
		 * Overrides the methode isIncomplete()
		 * @return true also if the list contains less than two nodes
		 */
		public boolean isIncomplete() 
		{
			return (nodes.size() < 2) || super.isIncomplete();
		}

		/**
		 * Constructs and returns the way including the nodes and attributes added up until now
		 * Throws the IllegalStateException if the way is incomplete
		 * @return OSMWay
		 * 			newly created way including the nodes and attributes added up until now
		 * @throws an IllegalStateException if the way is incomplete
		 */
		public OSMWay build() throws IllegalStateException
		{
			if(isIncomplete())
			{
				throw new IllegalStateException("The way in construction is incomplete."); 
			}
			return new OSMWay(id, nodes, attributesBuilder.build());
		}
	}
}