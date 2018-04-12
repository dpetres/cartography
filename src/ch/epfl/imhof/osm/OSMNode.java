package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

/**
 * Represents an OSMNode
 * Inherites OSMEntity
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMNode extends OSMEntity
{
	private final PointGeo position;

	/**
	 * Constructs an OSM node with the identifier, the position and the given attributes
	 * @param id
	 * 			the given identifier attributed to the node
	 * @param position
	 * 			position of the node on the map (coordinates)
	 * @param attributes
	 * 			attributes linked to the node
	 */
	public OSMNode(long id, PointGeo position, Attributes attributes)
	{
		super(id, attributes);
		this.position = position;
	}

	/**
	 * Returns the position of the node
	 * @return PointGeo
	 * 			coordinates indicating the position of the node
	 */
	public PointGeo position()
	{
		return position;
	}

	public final static class Builder extends OSMEntity.Builder
	{
		private PointGeo position;

		/**
		 * Constructs a builder for a node possessing the given identifier and position
		 * @param id
		 * 			given unique identifier linked to the node in construction
		 * @param position
		 * 			given position of the node in construction
		 */
		public Builder(long id, PointGeo position)
		{
			super(id);
			this.position = position;
		}

		/**
		 * Constructs an OSM node with the identifier and position given in the constructor, and the eventual added attributes
		 * @return OSMNode
		 * 			the node constructed with the given identifiers and positions and the eventual attributes
		 * @throws an IllegalStateException if the node in construction is incomplete
		 */
		public OSMNode build() throws IllegalStateException
		{
			if(isIncomplete())
			{
				throw new IllegalStateException("The node in consruction is incomplete."); 
			}
			return new OSMNode(id, position,  attributesBuilder.build());
		}
	}
}