package com.ugav.algo.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.DirectedType;
import com.ugav.algo.Graph.Edge;
import com.ugav.algo.Graph.WeightFunction;
import com.ugav.algo.Graph.WeightFunctionInt;
import com.ugav.algo.GraphArray;
import com.ugav.algo.Graphs;
import com.ugav.algo.MDST;
import com.ugav.algo.MDSTTarjan1977;
import com.ugav.algo.MST;
import com.ugav.algo.test.GraphsTestUtils.RandomGraphBuilder;

public class MDSTTarjan1977Test extends TestUtils {

	private static class MDSTUndirectedWrapper implements MST {

		private final MDST algo;

		MDSTUndirectedWrapper(MDST algo) {
			this.algo = algo;
		}

		@Override
		public <E> Collection<Edge<E>> calcMST(Graph<E> g, WeightFunction<E> w) {
			if (g.isDirected())
				return algo.calcMST(g, w);
			int n = g.vertices();
			Graph<Edge<E>> dg = new GraphArray<>(DirectedType.Directed, n);
			for (int u = 0; u < n; u++) {
				for (Edge<E> e : Utils.iterable(g.edges(u))) {
					dg.addEdge(e.u(), e.v()).val(e);
					dg.addEdge(e.v(), e.u()).val(e);
				}
			}
			Collection<Edge<Edge<E>>> mst0 = algo.calcMST(dg, e -> w.weight(e.val()));
			Collection<Edge<E>> mst = new ArrayList<>(mst0.size());
			for (Edge<Edge<E>> e : mst0)
				mst.add(e.val());
			return mst;
		}

	}

	@Test
	public static boolean randGraphUndirected() {
		return MSTTestUtils.testRandGraph(() -> new MDSTUndirectedWrapper(new MDSTTarjan1977()));
	}

	@Test
	public static boolean randGraphDirected() {
		return testRandGraph(new MDSTTarjan1977());
	}

	private static boolean testRandGraph(MDST algo) {
		List<Phase> phases = List.of(phase(1, 0, 0), phase(256, 6, 5), phase(128, 16, 32), phase(64, 64, 128),
				phase(32, 128, 256), phase(8, 1024, 4096), phase(2, 4096, 16384));
		return runTestMultiple(phases, args -> {
			int n = args[0];
			int m = args[1];
			return testRandGraph(algo, n, m);
		});
	}

	private static boolean testRandGraph(MDST algo, int n, int m) {
		Graph<Integer> g = new RandomGraphBuilder().n(n).m(m).directed(true).doubleEdges(false).selfEdges(false)
				.cycles(true).connected(false).build();
		GraphsTestUtils.assignRandWeightsIntPos(g);

		WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;
		@SuppressWarnings("unused")
		Collection<Edge<Integer>> mst = algo.calcMST(g, w, 0);
		// TODO verify the result
		return true;
	}

}
