package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a set of attributes and the value associated to its attributes
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Attributes 
{
	private final Map<String, String> attributes;

	/**
	 * Constructs a set of attributes with the key/value pair that is in the given associative table
	 * @param attributes
	 * 			Given map (associative table) of pair of key/value
	 */
	public Attributes(Map<String, String> attributes)
	{
		this.attributes = Collections.unmodifiableMap(new HashMap<>(attributes));
	}

	/**
	 * Verifies if the attributes list is empty
	 * @return true iff the set of attributes is empty
	 */
	public boolean isEmpty()
	{
		return attributes.isEmpty();
	}

	/**
	 * Returns true if the set includes the given key
	 * @param key
	 * 			Given key
	 * @return true iff the set includes the given key
	 */
	public boolean contains(String key)
	{
		return attributes.containsKey(key);
	}

	/**
	 * Returns the value associated to the given key, or null if the key doesn't exist
	 * @param key
	 * 			Given key
	 * @return the value associated to the given key or null
	 */
	public String get(String key)
	{
		return attributes.get(key);
	}

	/**
	 * Returns the value associated to the given key, or the given default value if no value is associated to it
	 * @param key
	 * 			Given key
	 * @param defaultValue
	 * 			Given default value
	 * @return the value associated to the given key or the given default value (of type String)
	 */
	public String get(String key, String defaultValue)
	{
		return attributes.getOrDefault(key, defaultValue);
	}

	/**
	 * Returns the integer associated to the given key
	 * or the given default value if no value is associated to it or if the given value is not a valid integer
	 * @param key
	 * 			Given key
	 * @param defaultValue
	 * 			Given default value
	 * @return the integer associated to the given key or the given default value (of type integer)
	 */
	public int get(String key, int defaultValue)
	{
		String valeur = get(key);
		if(valeur == null)
		{
			return defaultValue;
		}

		try
		{
			return Integer.parseInt(valeur);
		}catch(NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	 * Returns a filtrated version of the attributes containing only those from which the name appears in the given set
	 * @param keysToKeep
	 * 			Set (of strings) of the keys to keep
	 * @return a filtrated version of the attributes, keeping only the attributes corresponding to the given set of keys
	 */
	public Attributes keepOnlyKeys(Set<String> keysToKeep)
	{
		Map<String, String> mapFiltre = new HashMap<>();
		for(String s: keysToKeep)
		{
			if( get(s) != null)
			{
				mapFiltre.put(s, get(s));
			}	
		}

		return new Attributes(mapFiltre);
	}

	public final static class Builder 
	{
		private final Map<String, String> attributes = new HashMap<>();

		/**
		 * Adds the given pair (key/value) to the attributes set that is in construction
		 * If an attribute of same name is already in the set, its value is replaced by the given value
		 * @param key
		 * 			Given key
		 * @param value
		 * 			Given value
		 */
		public void put(String key, String value)
		{
			attributes.put(key, value);
		}

		/**
		 * Constructs a set of attributes containing the key/value pairs added until now
		 * @return Attributes
		 * 			A set of attributes containing the key/value pairs added in it until now
		 */
		public Attributes build()
		{
			return new Attributes(attributes);
		}
	}
}