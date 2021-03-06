package com.ugav.algo.test;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.Edge;
import com.ugav.algo.Graph.WeightFunction;
import com.ugav.algo.Graph.WeightFunctionInt;
import com.ugav.algo.GraphArrayUndirected;
import com.ugav.algo.Graphs;
import com.ugav.algo.MST;
import com.ugav.algo.MSTKruskal1956;
import com.ugav.algo.TPM;
import com.ugav.algo.test.GraphsTestUtils.RandomGraphBuilder;

@SuppressWarnings("boxing")
class TPMTestUtils extends TestUtils {

	private TPMTestUtils() {
		throw new InternalError();
	}

	static <E> Edge<E>[] calcExpectedTPM(Graph<E> t, WeightFunction<E> w, int[] queries) {
		int queriesNum = queries.length / 2;
		@SuppressWarnings("unchecked")
		Edge<E>[] res = new Edge[queriesNum];
		for (int q = 0; q < queriesNum; q++) {
			int u = queries[q * 2], v = queries[q * 2 + 1];

			List<Edge<E>> path = Graphs.findPath(t, u, v);

			Edge<E> maxEdge = null;
			double maxEdgeWeight = 0;
			for (Edge<E> e : path) {
				if (maxEdge == null || w.weight(e) > maxEdgeWeight) {
					maxEdge = e;
					maxEdgeWeight = w.weight(e);
				}
			}
			res[q] = maxEdge;
		}
		return res;
	}

	static int[] generateAllPossibleQueries(int n) {
		int[] queries = new int[(n + 1) * n];
		for (int q = 0, i = 0; i < n; i++) {
			for (int j = i; j < n; j++, q++) {
				queries[q * 2] = i;
				queries[q * 2 + 1] = j;
			}
		}
		return queries;
	}

	static int[] generateRandQueries(int n, int m) {
		Random rand = new Random(nextRandSeed());
		int[] queries = new int[m * 2];
		for (int q = 0; q < m; q++) {
			queries[q * 2] = rand.nextInt(n);
			queries[q * 2 + 1] = rand.nextInt(n);
		}
		return queries;
	}

	static <E> void compareActualToExpectedResults(int[] queries, Edge<E>[] actual, Edge<E>[] expected,
			WeightFunction<E> w) {
		assertEq(expected.length, actual.length, "Unexpected result size");
		for (int i = 0; i < actual.length; i++) {
			double aw = actual[i] != null ? w.weight(actual[i]) : Double.MIN_VALUE;
			double ew = expected[i] != null ? w.weight(expected[i]) : Double.MIN_VALUE;
			assertEq(ew, aw, "Unexpected result for query (", queries[i * 2], ", ", queries[i * 2 + 1], "): ",
					actual[i], " != ", expected[i], "\n");
		}
	}

	static void testTPM(Supplier<? extends TPM> builder) {
		List<Phase> phases = List.of(phase(64, 16), phase(32, 32), phase(16, 64), phase(8, 128), phase(4, 256),
				phase(2, 512), phase(1, 2485), phase(1, 3254));
		runTestMultiple(phases, (testIter, args) -> {
			int n = args[0];
			TPM algo = builder.get();
			testTPM(algo, n);
		});
	}

	private static void testTPM(TPM algo, int n) {
		Graph<Integer> t = GraphsTestUtils.randTree(n);
		GraphsTestUtils.assignRandWeightsIntPos(t);
		WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;

		int[] queries = n <= 64 ? generateAllPossibleQueries(n) : generateRandQueries(n, Math.min(n * 64, 8192));
		Edge<Integer>[] actual = algo.calcTPM(t, w, queries, queries.length / 2);
		Edge<Integer>[] expected = calcExpectedTPM(t, w, queries);
		compareActualToExpectedResults(queries, actual, expected, w);
	}

	static void verifyMSTPositive(Supplier<? extends TPM> builder) {
		List<Phase> phases = List.of(phase(256, 8, 16), phase(128, 16, 32), phase(64, 64, 128), phase(32, 128, 256),
				phase(8, 2048, 4096), phase(2, 8192, 16384));
		runTestMultiple(phases, (testIter, args) -> {
			int n = args[0];
			int m = args[1];
			Graph<Integer> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(true).selfEdges(false)
					.cycles(true).connected(true).build();
			GraphsTestUtils.assignRandWeightsIntPos(g);
			WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;
			Collection<Edge<Integer>> mstEdges = new MSTKruskal1956().calcMST(g, w);

			TPM algo = builder.get();
			assertTrue(MST.verifyMST(g, w, mstEdges, algo));
		});
	}

	static void verifyMSTNegative(Supplier<? extends TPM> builder) {
		List<Phase> phases = List.of(phase(256, 8, 16), phase(128, 16, 32), phase(64, 64, 128), phase(32, 128, 256),
				phase(8, 2048, 4096), phase(2, 8192, 16384));
		runTestMultiple(phases, (testIter, args) -> {
			int n = args[0];
			int m = args[1];

			Graph<Integer> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(true).selfEdges(false)
					.cycles(true).connected(true).build();
			GraphsTestUtils.assignRandWeightsIntPos(g);
			WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;

			Collection<Edge<Integer>> mstEdges = new MSTKruskal1956().calcMST(g, w);
			Graph<Integer> mst = GraphArrayUndirected.valueOf(g.vertices(), mstEdges);

			@SuppressWarnings("unchecked")
			Edge<Integer>[] edges = g.edges().toArray(new Edge[g.edges().size()]);

			Random rand = new Random(nextRandSeed());
			Edge<Integer> e;
			do {
				e = edges[rand.nextInt(edges.length)];
			} while (mstEdges.contains(e));

			List<Edge<Integer>> mstPath = Graphs.findPath(mst, e.u(), e.v());
			mst.removeEdge(mstPath.get(rand.nextInt(mstPath.size())));
			mst.addEdge(e);

			TPM algo = builder.get();

			assertFalse(MST.verifyMST(g, w, mst, algo), "MST validation failed");
		});
	}

}
