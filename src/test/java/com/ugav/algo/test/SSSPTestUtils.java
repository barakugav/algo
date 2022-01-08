package com.ugav.algo.test;

import java.util.Random;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.WeightFunction;
import com.ugav.algo.Graph.WeightFunctionInt;
import com.ugav.algo.Graphs;
import com.ugav.algo.SSSP;
import com.ugav.algo.SSSPDial1969;
import com.ugav.algo.SSSPDijkstra;
import com.ugav.algo.test.GraphsTestUtils.RandomGraphBuilder;

class SSSPTestUtils {

	private SSSPTestUtils() {
		throw new InternalError();
	}

	static boolean testSSSPDirectedPositiveInt(SSSP algo) {
		return testSSSPPositiveInt(algo, true);
	}

	static boolean testSSSPUndirectedPositiveInt(SSSP algo) {
		return testSSSPPositiveInt(algo, false);
	}

	private static boolean testSSSPPositiveInt(SSSP algo, boolean directed) {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 128, 16, 32 }, { 64, 64, 256 }, { 8, 512, 4096 }, { 1, 4096, 16384 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = args[2];
			Graph<Integer> g = new RandomGraphBuilder().n(n).m(m).directed(directed).doubleEdges(true).selfEdges(true)
					.cycles(true).connected(false).build();
			GraphsTestUtils.assignRandWeightsInt(g);
			WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;
			int source = rand.nextInt(g.vertices());

			SSSP.Result<Integer> actualRes = algo.calcDistances(g, w, source);

			SSSP validationAlgo = algo instanceof SSSPDijkstra ? SSSPDial1969.getInstace() : SSSPDijkstra.getInstace();
			return validateResult(g, w, source, actualRes, validationAlgo);
		});
	}

	static <E> boolean validateResult(Graph<E> g, WeightFunction<E> w, int source, SSSP.Result<E> result,
			SSSP validationAlgo) {
		SSSP.Result<E> expectedRes = validationAlgo.calcDistances(g, w, source);

		int n = g.vertices();
		for (int v = 0; v < n; v++) {
			double expeced = expectedRes.distance(v);
			double actual = result.distance(v);
			if (expeced != actual) {
				TestUtils.printTestStr("Distance to vertex " + v + " is wrong: " + expeced + " != " + actual + "\n");
				return false;
			}
		}
		return true;
	}

}
