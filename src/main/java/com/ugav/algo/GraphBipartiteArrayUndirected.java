package com.ugav.algo;

import java.util.Arrays;

public class GraphBipartiteArrayUndirected<E> extends GraphArrayUndirected<E> implements GraphBipartite.Undirected<E> {

	private boolean[] side;
	private int sSize;

	private static final boolean S_SIDE = true;
	private static final boolean T_SIDE = !S_SIDE;
	private static final boolean[] EMPTY_ARR = new boolean[0];

	public GraphBipartiteArrayUndirected() {
		this(0, 0);
	}

	public GraphBipartiteArrayUndirected(int sn, int tn) {
		super(sn + tn);
		if (sn < 0 || tn < 0)
			throw new IllegalArgumentException();
		int n = sn + tn;
		side = n == 0 ? EMPTY_ARR : new boolean[n];
		sSize = sn;
		if (sn > 0)
			Arrays.fill(side, 0, sn, S_SIDE);
	}

	@Override
	public int svertices() {
		return sSize;
	}

	@Override
	public int tvertices() {
		return vertices() - sSize;
	}

	@Override
	public boolean isVertexInS(int v) {
		return side[v] == S_SIDE;
	}

	@Override
	public int newVertexS() {
		return newVertex(S_SIDE);
	}

	@Override
	public int newVertexT() {
		return newVertex(T_SIDE);
	}

	private int newVertex(boolean side) {
		ensureSize(vertices() + 1);
		int u = super.newVertex();
		this.side[u] = side;
		return u;

	}

	private void ensureSize(int n) {
		if (side.length >= n)
			return;
		int aLen = Math.max(side.length * 2, 2);
		side = Arrays.copyOf(side, aLen);
	}

	@Override
	public int newVertex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Edge<E> addEdge(int u, int v) {
		if (side[u] == side[v])
			throw new IllegalArgumentException("The vertices (" + u + ", " + v + ") are from the same side");
		return super.addEdge(u, v);
	}

	@Override
	public void addEdge(Edge<E> e) {
		if (side[e.u()] == side[e.v()])
			throw new IllegalArgumentException("The vertices (" + e.u() + ", " + e.v() + ") are from the same side");
		super.addEdge(e);
	}

}