package com.ugav.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.ugav.algo.Graph.Edge;
import com.ugav.algo.Graph.WeightFunction;
import com.ugav.algo.Graphs.EdgeWeightComparator;

public class MSTFredmanTarjan1987 implements MST {

	/*
	 * O(m log* n)
	 */

	public MSTFredmanTarjan1987() {
	}

	@Override
	public <E> Collection<Edge<E>> calcMST(Graph<E> g, WeightFunction<E> w) {
		int n = g.vertices(), m = g.edges().size();
		if (n == 0)
			return Collections.emptyList();

		// following variables are used to maintain the super vertices

		int[] V = new int[n]; // (vertex -> super vertex) of the current iteration
		int[] VNext = new int[n]; // (super vertex -> super vertex next) new labels for next iteration
		int ni = n; // number of super vertices in the current iteration

		// to be able to iterate over all edges of a super vertex, we maintain for each
		// super vertex a list of the vertices that were contracted to create it. The
		// list is stored as 3 arrays:
		int[] vListBegin = new int[n]; // (super vertex -> first vertex in the list) of the current iteration
		int[] vListEnd = new int[n]; // (super vertex -> last vertex in the list) of the current iteration
		int[] vListNext = new int[n]; // (vertex -> next vertex in the list)
		int[] vListBeginNext = new int[n]; // vListBegin of the next iteration
		int[] vListEndNext = new int[n];// vListEnd of the next iteration

		// init each vertex to be its own super vertex
		for (int v = 0; v < n; v++) {
			V[v] = vListBegin[v] = vListEnd[v] = v;
			vListNext[v] = -1;
		}

		// following variables are used for the Prim algorithm on the super vertices

		int[] vTree = new int[n]; // (super vertex -> tree index)
		int[] treeVertices = new int[n]; // stack of super vertices in current built tree

		Comparator<Edge<E>> c = new EdgeWeightComparator<>(w);
		// heap of edges going out of the current tree, one edge in per super vertex
		HeapDirectAccessed<Edge<E>> heap = new HeapFibonacci<>(c);
		// (super vertex -> heap element) for fast decreaseKey
		@SuppressWarnings("unchecked")
		HeapDirectAccessed.Handle<Edge<E>>[] vHeapElm = new HeapDirectAccessed.Handle[n];

		Collection<Edge<E>> mst = new ArrayList<>(n - 1);
		while (true) {
			int kExp = 2 * m / ni;
			int k = kExp < Integer.SIZE ? 1 << kExp : Integer.MAX_VALUE;
			int niNext = 0;

			Arrays.fill(vTree, 0, ni, -1);
			for (int r = 0; r < ni; r++) {
				if (vTree[r] != -1)
					continue;

				int treeSize = 0;
				int connectedTree = -1;

				treeLoop: for (int U = r;;) {
					vTree[U] = r;
					treeVertices[treeSize++] = U;
					vHeapElm[U] = null;

					// decrease edges keys if a better one is found
					for (int u = vListBegin[U]; u != -1; u = vListNext[u]) {
						// for each vertex in the super vertex, iterate over all edges
						for (Edge<E> e : Utils.iterable(g.edges(u))) {
							int v = V[e.v()];

							// edge from current tree to itself
							if (vTree[v] == r)
								continue;

							HeapDirectAccessed.Handle<Edge<E>> heapElm = vHeapElm[v];
							if (heapElm == null) {
								heapElm = vHeapElm[v] = heap.insert(e);
								if (heap.size() > k)
									break treeLoop;
							} else if (c.compare(e, heapElm.get()) < 0)
								heap.decreaseKey(heapElm, e);
						}
					}

					// find next lightest edge
					Edge<E> e = null;
					int v, vt;
					while (true) {
						if (heap.isEmpty())
							// reached all vertices from current root, continue to next tree
							break treeLoop;
						e = heap.extractMin();
						v = V[e.v()];
						if ((vt = vTree[v]) != r)
							break;
					}

					// add lightest edge to MST
					mst.add(e);
					U = v;
					if (vt != -1) {
						// connected to an existing tree, take it's super vertex label for next iter
						connectedTree = VNext[vt];
						break;
					}
				}

				int newV; // super vertex label for next iteration for all vertices in tree
				int listEnd; // last vertex in super vertex vertices list
				if (connectedTree != -1) {
					newV = connectedTree;
					listEnd = vListEndNext[connectedTree];
				} else {
					newV = niNext++;
					listEnd = -1;
				}

				// contract tree to super vertex, assign new label and concatenate vertices list
				for (int v; treeSize-- > 0; listEnd = vListEnd[v]) {
					v = treeVertices[treeSize];
					VNext[v] = newV;

					if (listEnd == -1)
						// set new super vertex vertices list begin
						vListBeginNext[newV] = vListBegin[v];
					else
						// concatenate current vertex vertices list with the prev one
						vListNext[listEnd] = vListBegin[v];
				}
				// set new super vertex vertices list end
				vListEndNext[newV] = listEnd;

				Arrays.fill(vHeapElm, 0, ni, null);
				heap.clear();
			}

			// assign new super vertices' vertices list
			int[] temp = vListBegin;
			vListBegin = vListBeginNext;
			vListBeginNext = temp;
			temp = vListEnd;
			vListEnd = vListEndNext;
			vListEndNext = temp;

			// If we failed to contract the graph, we are done
			if (ni == niNext)
				break;
			ni = niNext;

			// update super vertex labels for all vertices
			for (int v = 0; v < n; v++)
				V[v] = VNext[V[v]];

		}

		return mst;
	}

}
