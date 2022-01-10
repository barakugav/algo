package com.ugav.algo.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.Edge;
import com.ugav.algo.Graph.WeightFunctionInt;
import com.ugav.algo.Graphs;
import com.ugav.algo.SSSP;
import com.ugav.algo.SSSPDijkstra;
import com.ugav.algo.test.GraphsTestUtils.RandomGraphBuilder;

public class GraphsTest {

	@Test
	public static boolean bfsConnected() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16, 8 }, { 128, 32, 64 }, { 4, 2048, 8192 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = args[2];
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(true).selfEdges(true)
					.cycles(true).connected(true).build();
			int source = rand.nextInt(n);

			boolean[] visited = new boolean[n];
			List<Integer> invalidVertices = new ArrayList<>();
			Graphs.runBFS(g, source, (v, e) -> {
				if (visited[v] || (v != source && e.v() != v))
					invalidVertices.add(v);
				visited[v] = true;
				return true;
			});
			return invalidVertices.isEmpty();
		});
	}

	@Test
	public static boolean dfsConnected() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16, 8 }, { 128, 32, 64 }, { 4, 2048, 8192 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = args[2];
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(true).selfEdges(true)
					.cycles(true).connected(true).build();
			int source = rand.nextInt(n);

			boolean[] visited = new boolean[n];
			List<Integer> invalidVertices = new ArrayList<>();
			Graphs.runDFS(g, source, (v, pathFromSource) -> {
				if (visited[v] || (v != source && pathFromSource.get(pathFromSource.size() - 1).v() != v))
					invalidVertices.add(v);
				visited[v] = true;
				return true;
			});
			return invalidVertices.isEmpty();
		});
	}

	@Test
	public static boolean isTreeUnrootedPositive() {
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();

			return Graphs.isTree(g);
		});
	}

	@Test
	public static boolean isTreeUnrootedNegativeUnconnected() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();
			@SuppressWarnings("unchecked")
			Edge<Void> e = g.edges().toArray(new Edge[n])[rand.nextInt(m)];
			g.removeEdge(e);

			return !Graphs.isTree(g);
		});
	}

	@Test
	public static boolean isTreeUnrootedNegativeCycle() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();
			int u, v;
			do {
				u = rand.nextInt(n);
				v = rand.nextInt(n);
			} while (u == v);
			g.addEdge(u, v);

			return !Graphs.isTree(g);
		});
	}

	@Test
	public static boolean isTreeRootedPositive() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();
			int root = rand.nextInt(n);

			return Graphs.isTree(g, root);
		});
	}

	@Test
	public static boolean isTreeRootedNegativeUnconnected() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();
			int root = rand.nextInt(n);
			@SuppressWarnings("unchecked")
			Edge<Void> e = g.edges().toArray(new Edge[n])[rand.nextInt(m)];
			g.removeEdge(e);

			return !Graphs.isTree(g, root);
		});
	}

	@Test
	public static boolean isTreeRootedNegativeCycle() {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 256, 16 }, { 128, 32 }, { 4, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = n - 1;
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(false).doubleEdges(false).selfEdges(false)
					.cycles(false).connected(true).build();
			int root = rand.nextInt(n);
			int u, v;
			do {
				u = rand.nextInt(n);
				v = rand.nextInt(n);
			} while (u == v);
			g.addEdge(u, v);

			return !Graphs.isTree(g, root);
		});
	}

//		public static <E> Pair<Integer, int[]> findConnectivityComponents(Graph<E> g) {;

//		public static <E> Pair<Integer, int[]> findStrongConnectivityComponents(Graph<E> g) {;

	@Test
	public static boolean topologicalSortUnconnected() {
		return topologicalSort(false);
	}

	@Test
	public static boolean topologicalSortConnected() {
		return topologicalSort(true);
	}

	private static boolean topologicalSort(boolean connected) {
		int[][] phases = { { 256, 16, 16 }, { 128, 32, 64 }, { 16, 1024, 2048 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = args[2];
			Graph<Void> g = new RandomGraphBuilder().n(n).m(m).directed(true).doubleEdges(true).selfEdges(false)
					.cycles(false).connected(connected).build();

			int[] topolSort = Graphs.calcTopologicalSortingDAG(g);

			Set<Integer> seenVertices = new HashSet<>(n);
			for (int i = 0; i < n; i++) {
				int u = topolSort[i];
				for (Iterator<Edge<Void>> it = g.edges(u); it.hasNext();)
					if (seenVertices.contains(it.next().v()))
						return false;
				seenVertices.add(u);
			}
			return true;
		});
	}

	@Test
	public static boolean distancesDAGUnconnected() {
		return distancesDAG(false);
	}

	@Test
	public static boolean distancesDAGConnected() {
		return distancesDAG(true);
	}

	private static boolean distancesDAG(boolean connected) {
		int[][] phases = { { 256, 16, 16 }, { 128, 32, 64 }, { 16, 512, 1024 } };
		return TestUtils.runTestMultiple(phases, args -> {
			int n = args[1];
			int m = args[2];
			Graph<Integer> g = new RandomGraphBuilder().n(n).m(m).directed(true).doubleEdges(true).selfEdges(false)
					.cycles(false).connected(connected).build();
			GraphsTestUtils.assignRandWeightsIntPos(g);
			WeightFunctionInt<Integer> w = Graphs.WEIGHT_INT_FUNC_DEFAULT;
			int source = 0;

			SSSP.Result<Integer> result = Graphs.calcDistancesDAG(g, w, source);

			return SSSPTestUtils.validateResult(g, w, source, result, SSSPDijkstra.getInstace());
		});
	}

}