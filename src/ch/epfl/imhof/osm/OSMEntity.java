package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * Mother class to all classes representing OSM entities
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public abstract class OSMEntity 
{
	private final long id;
	private final Attributes attributes;

	/**
	 * Constructs an OSM entity provided with the given unique identifier and attributes
	 * @param id
	 * 			given identifier
	 * @param attributes
	 * 			given attributes used to create an OSMEntity
	 */
	public OSMEntity(long id, Attributes attributes)
	{
		this.id = id;
		this.attributes = attributes;
	}

	/**
	 * Returns the unique identifier of it's entity
	 * @return identifier
	 * 			the unique identifier of the entity
	 */
	public long id()
	{
		return id;
	}

	/**
	 * Returns the entity's attributes
	 * @return attributes
	 * 			the entity's attributes
	 */
	public Attributes attributes()
	{
		return attributes;
	}

	/**
	 * Returns true iff the entity possesses the given attribute
	 * @param key
	 * 			given atribute
	 * @return boolean value true iff the entity contains the given String key
	 */
	public boolean hasAttribute(String key)
	{
		return attributes.contains(key);
	}

	/**
	 * Returns the value of the given attribute, or null if it doesn't exist
	 * @param key
	 * 			given attribute
	 * @return attributed value or null iff the given key does not exist
	 */
	public String attributeValue(String key)
	{
		return attributes.get(key);
	}

	public abstract static class Builder
	{
		protected long id;
		protected Attributes.Builder attributesBuilder = new Attributes.Builder();
		private boolean builderIncomplete = false;

		/**
		 * Constructs a builder for an OSM entity identified by the given integer
		 * @param id
		 * 			unique given identifier
		 */
		public Builder(long id)
		{
			this.id = id;
		}

		/** 
		 * Adds the given key/value association to the set of attributes of the entity in construction 
		 * @param key
		 * 			given key added to the set of attributes
		 * @param value
		 * 			given value attributed to the key and added to the set of attributes
		 */
		public void setAttribute(String key, String value)
		{
			attributesBuilder.put(key, value);
		}

		/**
		 * Defines the entity in construction as incomplete
		 */
		public void setIncomplete()
		{
			builderIncomplete = true;
		}

		/**
		 * Returns true iff the entity in construction is incomplete
		 * @return boolean value true iff the entity in construction is incomplete
		 */
		public boolean isIncomplete()
		{
			return builderIncomplete;
		}
	}
}