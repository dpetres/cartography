package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a non oriented graph
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class Graph<N>
{
	private final Map<N, Set<N>> neighbors;

	/**
	 * Contstructs a non oriented graph with the given adjacent table
	 * @param neighbors
	 * 			Given map of neighbors
	 */
	public Graph(Map<N, Set<N>> neighbors)
	{
		Map<N, Set<N>> neighbors2 = new HashMap<>();
		for(N n: neighbors.keySet())
		{
			neighbors2.put(n, Collections.unmodifiableSet(new HashSet<>(neighbors.get(n))));
		}
		this.neighbors = Collections.unmodifiableMap(new HashMap<>(neighbors2));
	}

	/**
	 * Returns the set of nodes from the graph
	 * @return set of nodes from the graph
	 */
	public Set<N> nodes()
	{
		return neighbors.keySet();
	}

	/**
	 * Returns a set of the neighboring nodes of type N from the given node
	 * @param node
	 * 			Given nodes of type N
	 * @return set of neighboring nodes
	 * @throws an IllegalArgumentException if the given node doesn't belong to the graph
	 */
	public Set<N> neighborsOf(N node) throws IllegalArgumentException
	{
		Set<N> set = neighbors.get(node);
		if(set == null)
		{
			throw new IllegalArgumentException("The given node doesn't belong to the graph.");
		}
		return set;
	}

	public final static class Builder<N>
	{
		private Map<N, Set<N>> neighbors = new HashMap<>();

		/**
		 * Adds the given node to the graph in construction, if it didn't already belong to it
		 * @param n
		 * 			Given node to be added to the graph in construction
		 */
		public void addNode(N n)
		{
			if(!neighbors.containsKey(n))
			{
				neighbors.put(n, new HashSet<>());
			}
		}

		/**
		 * Adds the edge between the two given nodes
		 * (adds the first to the neighboring set from the second and vice versa)
		 * @param n1
		 * 			First given node
		 * @param n2
		 * 			Second given node
		 * @throws an IllegalArgumentException if one of the nodes does not belong to the graph in construction 
		 */
		public void addEdge(N n1, N n2) throws IllegalArgumentException
		{
			Set<N> set1 = neighbors.get(n1);
			Set<N> set2 = neighbors.get(n2);
			if(set1 == null || set2 == null)
			{
				throw new IllegalArgumentException("The given node doesn't belong to the graph.");
			}
			set1.add(n2);
			set2.add(n1);
		}

		/**
		 * Constructs the graph composed of the nodes and edges added until now
		 * @return graph
		 * 			Graph compsed of the nodes added until now
		 */
		public Graph<N> build()
		{
			return new Graph<>(neighbors);
		}
	}
}