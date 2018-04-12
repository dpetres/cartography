package ch.epfl.imhof.painting;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * 
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)q
 */
public final class Filters
{
	private Filters() {}

	/**
	 * Returns a predicate that is true iff the value for which it is applied has an attribute with this name
	 * regardless of its value
	 * @param attributeName
	 * 			given name of the attribute
	 * @return a predicate true iff the value has an attribute of the given name
	 */
	public static Predicate<Attributed<?>> tagged(String attributeName)
	{
		return x -> x.hasAttribute(attributeName);
	}

	/**
	 * Returns a predicate that is true if the value for which it is applied has an attribute with the given name
	 * and if the value associated with this attribute is one of those data
	 * @param attributeName
	 * 			given name of the attribute
	 * @param value1
	 * 			given tested value
	 * @param values
	 * 			given String of multiple values
	 * @return a predicate true iff the conditions above are respected
	 */
	public static Predicate<Attributed<?>> tagged(String attributeName, String value1, String...values)
	{
		return x -> 
		{
			Set<String> set = new HashSet<>();
			set.add(value1);
			for(String s: values)
			{
				set.add(s);
			}
			return x.hasAttribute(attributeName) && set.contains(x.attributeValue(attributeName));
		};
	}

	/**
	 * Returns a predicate that is true only when applied to an attributed entity on this given layer
	 * @param layer
	 * 			given layer (integer between -5 and +5)
	 * @return a predicate true only if the condition above is respected
	 */
	public static Predicate<Attributed<?>> onLayer(int layer)
	{
		return x -> x.attributeValue("layer", 0) == layer;
	}
}
