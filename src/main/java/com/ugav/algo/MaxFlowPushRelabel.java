package com.ugav.algo;

import java.util.function.ObjDoubleConsumer;

import com.ugav.algo.Graph.DirectedType;
import com.ugav.algo.Graph.Edge;
import com.ugav.algo.Graph.EdgeIterator;
import com.ugav.algo.Utils.QueueIntFixSize;

public class MaxFlowPushRelabel implements MaxFlow {

	private final DebugPrintsManager debug;
	private static final double EPS = 0.0001;

	public MaxFlowPushRelabel() {
		debug = new DebugPrintsManager(false);
	}

	@Override
	public <E> double calcMaxFlow(Graph<E> g0, FlowNetwork<E> net, int source, int target) {
		if (!g0.isDirected())
			throw new IllegalArgumentException("only directed graphs are supported");
		if (source == target)
			throw new IllegalArgumentException("Source and target can't be the same vertices");
		debug.println("\t" + getClass().getSimpleName());

		Graph<Ref<E>> g = referenceGraph(g0, net);
		int n = g.vertices();

		@SuppressWarnings("unchecked")
		EdgeIterator<Ref<E>>[] edges = new EdgeIterator[n];
		double[] access = new double[n];
		boolean[] isActive = new boolean[n];
		QueueIntFixSize active = new QueueIntFixSize(n);
		int[] d = new int[n];

		ObjDoubleConsumer<Edge<Ref<E>>> pushFlow = (e0, f) -> {
			Ref<E> e = e0.val();
			int u = e0.u(), v = e0.v();
			if (e0.u() == e.orig.u())
				debug.println("F(" + e.orig + ") += " + f);

			e.flow += f;
			e.rev.flow -= f;
			assert e.flow <= e.cap + EPS;
			assert e.rev.flow <= e.rev.cap + EPS;

			if (u != source && u != target) {
				access[u] -= f;
				if (access[u] > EPS && !isActive[u]) {
					isActive[u] = true;
					active.push(u);
				}
			}
			if (v != source && v != target) {
				access[v] += f;
				if (access[v] > EPS && !isActive[v]) {
					isActive[v] = true;
					active.push(v);
				}
			}
		};

		/* Push as much as possible from the source vertex */
		for (Edge<Ref<E>> e : Utils.iterable(g.edges(source))) {
			double f = e.val().cap - e.val().flow;
			if (Math.abs(f) < EPS)
				continue;
			pushFlow.accept(e, f);
		}

		/* Init all vertices distances */
		for (int u = 0; u < n; u++)
			d[u] = 0;
		d[source] = n;

		/* Init all vertices iterators */
		for (int u = 0; u < n; u++)
			edges[u] = g.edges(u);

		while (!active.isEmpty()) {
			int u = active.pop();
			EdgeIterator<Ref<E>> it = edges[u];

			while (access[u] > EPS && it.hasNext()) {
				Edge<Ref<E>> e = it.pickNext();
				double eAccess = e.val().cap - e.val().flow;
				if (eAccess > EPS && d[u] == d[e.v()] + 1) {
					double f = Math.min(access[u], eAccess);
					pushFlow.accept(e, f);
				} else {
					it.next();
				}
			}

			/* Finished iterating over all vertex edges, relabel and reset iterator */
			if (!it.hasNext()) {
				d[u]++;
				edges[u] = g.edges(u);
			}

			/* Update isActive and add to queue if active */
			if (isActive[u] = access[u] > EPS)
				active.push(u);
		}

		/* Construct result */
		for (Edge<Ref<E>> e : g.edges())
			if (e.u() == e.val().orig.u())
				net.setFlow(e.val().orig, e.val().flow);
		double totalFlow = 0;
		for (Edge<Ref<E>> e : Utils.iterable(g.edges(source)))
			totalFlow += e.val().flow;
		return totalFlow;
	}

	private static <E> Graph<Ref<E>> referenceGraph(Graph<E> g0, FlowNetwork<E> net) {
		Graph<Ref<E>> g = new GraphArray<>(DirectedType.Directed, g0.vertices());
		for (Edge<E> e : g0.edges()) {
			Ref<E> ref = new Ref<>(e, net.getCapacity(e), 0), refRev = new Ref<>(e, 0, 0);
			g.addEdge(e.u(), e.v()).val(ref);
			g.addEdge(e.v(), e.u()).val(refRev);
			refRev.rev = ref;
			ref.rev = refRev;
		}
		return g;
	}

	private static class Ref<E> {

		final Edge<E> orig;
		Ref<E> rev;
		final double cap;
		double flow;

		Ref(Edge<E> e, double cap, double flow) {
			orig = e;
			rev = null;
			this.cap = cap;
			this.flow = flow;
		}

		@Override
		public int hashCode() {
			return orig.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (other == this)
				return true;
			if (!(other instanceof Ref))
				return false;

			Ref<?> o = (Ref<?>) other;
			return orig.equals(o.orig);
		}

		@Override
		public String toString() {
			return "R(" + orig + ")";
		}

	}

}