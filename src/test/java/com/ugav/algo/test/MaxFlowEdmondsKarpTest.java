package com.ugav.algo.test;

import java.util.LinkedList;
import java.util.Random;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.Edge;
import com.ugav.algo.MaxFlow;
import com.ugav.algo.MaxFlow.FlowNetwork;
import com.ugav.algo.MaxFlowEdmondsKarp;
import com.ugav.algo.Pair;
import com.ugav.algo.test.GraphsTestUtils.RandomGraphBuilder;

public class MaxFlowEdmondsKarpTest {

	private static Pair<Graph<Pair<Double, Double>>, FlowNetwork<Pair<Double, Double>>> randNetword(int n, int m) {
		Graph<Pair<Double, Double>> g = new RandomGraphBuilder().n(n).m(m).directed(true).doubleEdges(false)
				.selfEdges(false).cycles(true).connected(false).build();

		RandomIntUnique rand = new RandomIntUnique(m, m * 2, TestUtils.nextRandSeed());
//		Random rand = new Random(TestUtils.nextRandSeed());
		for (Edge<Pair<Double, Double>> e : g.edges()) {
//			e.val(Pair.valueOf(rand.nextDouble() * 100, 0.0));
			e.val(Pair.valueOf((double) rand.next(), 0.0));
		}

		return Pair.valueOf(g, new FlowNetwork<>() {

			@Override
			public double getCapacity(Edge<Pair<Double, Double>> e) {
				return e.val().e1;
			}

			@Override
			public double getFlow(Edge<Pair<Double, Double>> e) {
				return e.val().e2;
			}

			@Override
			public void setFlow(Edge<Pair<Double, Double>> e, double flow) {
				if (flow < 0 || flow > e.val().e1)
					throw new IllegalArgumentException();
				e.val().e2 = flow;
			}
		});
	}

	@Test
	public static boolean randGraphs() {
		TestUtils.initTestRand(TestUtils.getTestFullname(), 6609301001716443851L);
		return randGraphs(MaxFlowEdmondsKarp.getInstance());
	}

	private static boolean randGraphs(MaxFlow algo) {
		Random rand = new Random(TestUtils.nextRandSeed());
		int[][] phases = { { 128, 16, 16 }, { 128, 16, 32 }, { 64, 64, 64 }, { 64, 64, 128 }, { 8, 512, 512 },
				{ 8, 512, 2048 }, { 1, 4096, 4096 }, { 1, 4096, 16384 } };
		for (int phase = 0; phase < phases.length; phase++) {
			int repeat = phases[phase][0];
			int n = phases[phase][1];
			int m = phases[phase][2];
			for (int i = 0; i < repeat; i++) {

				Pair<Graph<Pair<Double, Double>>, FlowNetwork<Pair<Double, Double>>> p = randNetword(n, m);
				Graph<Pair<Double, Double>> g = p.e1;
				FlowNetwork<Pair<Double, Double>> net = p.e2;
				int source, target;
				do {
					source = rand.nextInt(g.vertices());
					target = rand.nextInt(g.vertices());
				} while (source == target);

				if (!testNetwork(g, net, source, target, algo))
					return false;
			}
		}
		return true;
	}

	private static <E> boolean testNetwork(Graph<E> g, FlowNetwork<E> net, int source, int target, MaxFlow algo) {
		double actualMaxFlow = algo.calcMaxFlow(g, net, source, target);

		int n = g.vertices();
		double[] vertexFlowOut = new double[n];
		for (Edge<E> e : g.edges()) {
			vertexFlowOut[e.u()] += net.getFlow(e);
			vertexFlowOut[e.v()] -= net.getFlow(e);
		}
		for (int v = 0; v < n; v++) {
			double expected = v == source ? actualMaxFlow : v == target ? -actualMaxFlow : 0;
			if (vertexFlowOut[v] != expected) {
				TestUtils.printTestStr("Invalid vertex(" + v + ") flow: " + vertexFlowOut[v] + "\n");
				return false;
			}
		}

		double expectedMaxFlow = calcExpectedFlow(g, net, source, target);
		if (expectedMaxFlow != actualMaxFlow) {
			TestUtils.printTestStr("Unexpected max flow: " + expectedMaxFlow + " != " + actualMaxFlow + "\n");
			return false;
		}

		return true;
	}

	/* implementation taken from the Internet */

	private static <E> double calcExpectedFlow(Graph<E> g, FlowNetwork<E> net, int source, int target) {
		int n = g.vertices();
		double[][] capacities = new double[n][n];
		for (Edge<E> e : g.edges())
			capacities[e.u()][e.v()] += net.getCapacity(e);

		return fordFulkerson(capacities, source, target);
	}

	private static boolean bfs(double rGraph[][], int s, int t, int parent[]) {
		int n = rGraph.length;
		boolean visited[] = new boolean[n];
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(s);
		visited[s] = true;
		parent[s] = -1;
		while (queue.size() != 0) {
			int u = queue.poll();
			for (int v = 0; v < n; v++) {
				if (visited[v] == false && rGraph[u][v] > 0) {
					queue.add(v);
					parent[v] = u;
					visited[v] = true;
				}
			}
		}
		return (visited[t] == true);
	}

	private static double fordFulkerson(double graph[][], int s, int t) {
		int n = graph.length;
		int u, v;
		double rGraph[][] = new double[n][n];
		for (u = 0; u < n; u++)
			for (v = 0; v < n; v++)
				rGraph[u][v] = graph[u][v];
		int parent[] = new int[n];
		int max_flow = 0;
		while (bfs(rGraph, s, t, parent)) {
			double pathFlow = Double.MAX_VALUE;
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				pathFlow = Math.min(pathFlow, rGraph[u][v]);
			}
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				rGraph[u][v] -= pathFlow;
				rGraph[v][u] += pathFlow;
			}
			max_flow += pathFlow;
		}
		return max_flow;
	}

}