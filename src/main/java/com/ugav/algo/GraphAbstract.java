package com.ugav.algo;

import java.util.Iterator;

public abstract class GraphAbstract<E> implements Graph<E> {

	public boolean hasEdge(int u, int v) {
		return getEdge(u, v) != null;
	}

	@Override
	public int getEdgesArr(int u, Edge<E>[] edges, int begin) {
		int count = 0;
		for (Iterator<Edge<E>> it = edges(u); it.hasNext();)
			edges[begin + count++] = it.next();
		return count;
	}

	@Override
	public int getEdgesArrVs(int u, int[] edges, int begin) {
		int count = 0;
		for (Iterator<Edge<E>> it = edges(u); it.hasNext();)
			edges[begin + count++] = it.next().v();
		return count;
	}

	@Override
	public void addEdge(Edge<E> e) {
		edges().add(e);
	}

	@Override
	public void removeEdge(Edge<E> e) {
		edges().remove(e);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (!(other instanceof Graph<?>))
			return false;
		Graph<?> o = (Graph<?>) other;

		return isDirected() == o.isDirected() && vertices() == o.vertices() && edges().equals(o.edges());
	}

	@Override
	public int hashCode() {
		int h = vertices();
		for (Edge<E> e : edges())
			h = h * 31 + e.hashCode();
		return h;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		int n = vertices();
		for (int u = 0; u < n; u++) {
			s.append("[" + u + "]->{");

			boolean first = true;
			for (Iterator<Edge<E>> it = edges(u); it.hasNext();) {
				if (first)
					first = false;
				else
					s.append(", ");
				s.append(it.next());
			}
			s.append("}, ");
		}
		s.append('}');
		return s.toString();
	}

}