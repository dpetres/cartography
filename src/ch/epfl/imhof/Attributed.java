package ch.epfl.imhof;

/**
 * Represents an attributed T type entity 
 *
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 *
 * @param <T>
 * 		Type of the attributed entity
 */
public final class Attributed<T> 
{
	private final T value;
	private final Attributes attributes;

	/**
	 * Constructs an attributed value. The value and attributes of which are given
	 * @param value
	 * 			attributed value
	 * @param attributes
	 * 			attributes linked to the given value
	 */
	public Attributed(T value, Attributes attributes)
	{
		this.value = value;
		this.attributes = attributes;
	}

	/**
	 * Returns the value to which the attributes are attached
	 * @return value
	 * 			value (of type T) to which the attributes are attached
	 */
	public T value()
	{
		return value;
	}

	/**
	 * Returns the attributes attached to the value
	 * @return attributes
	 * 			Attributes attached to the attributed value
	 */
	public Attributes attributes()
	{
		return attributes;
	}

	/**
	 * Returns true iff the attributes include the one from which the name is passed as an argument 
	 * @param attributeName
	 * 			Name of the tested attribute
	 * @return true iff the attributes include the on attribute from which the name is passed as an argument
	 */
	public boolean hasAttribute(String attributeName)
	{
		return attributes.contains(attributeName);
	}

	/**
	 * Returns the value attributed to the given attribute, or null if the attribute doesn't exist
	 * @param attributeName
	 * 			Name of the attribute
	 * @return the value attributed to the given attribute or null
	 */
	public String attributeValue(String attributeName)
	{
		return attributes.get(attributeName);
	}

	/**
	 * Returns the value associated to the given attribute, or the given default value if the attribute doesn't exist
	 * @param attributeName
	 * 			Name of the attribute
	 * @param defaultValue
	 * 			Given default value
	 * @return the value associated to the given attribute or the given default value
	 */
	public String attributeValue(String attributeName, String defaultValue)
	{
		return attributes.get(attributeName, defaultValue);
	}

	/**
	 * Returns the integer value associated to the given attribute,
	 * or the default value if it doesn't exist or if its associated value isn't a valid integer
	 * @param attributeName
	 * 			Name of the attribute
	 * @param defaultValue
	 * 			Given default value
	 * @return the integer value associated to the given attribute or the given default value
	 */
	public int attributeValue(String attributeName, int defaultValue)
	{
		return attributes.get(attributeName, defaultValue);
	}
}