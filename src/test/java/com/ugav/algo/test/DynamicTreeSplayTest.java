package com.ugav.algo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.function.Function;

import com.ugav.algo.DebugPrintsManager;
import com.ugav.algo.DynamicTree;
import com.ugav.algo.DynamicTreeSplay;

public class DynamicTreeSplayTest extends TestUtils {

	@Test
	public static void randOps() {
		testRandOps(DynamicTreeSplay::new);
	}

	static void testRandOps(DoubleFunction<? extends DynamicTree<TrackerNode, Void>> builder) {
		testRandOps(builder, List.of(Op.MakeTree, Op.FindRoot, Op.FindMinEdge, Op.AddWeight, Op.Link, Op.Cut));
	}

	static void testRandOps(DoubleFunction<? extends DynamicTree<TrackerNode, Void>> builder, List<Op> ops) {
		List<Phase> phases = List.of(phase(1024, 16), phase(256, 32), phase(256, 64), phase(128, 128), phase(64, 512),
				phase(64, 2048), phase(64, 4096), phase(32, 16384));
		runTestMultiple(phases, (testIter, args) -> {
			int m = args[0];
			testRandOps(builder, m, ops);
		});
	}

	static enum Op {
		MakeTree, FindRoot, FindMinEdge, AddWeight, Link, Cut, Evert, Size
	}

	static class TrackerNode {
		final int id;
		DynamicTree.Node<TrackerNode, Void> dtNode;
		TrackerNode parent;
		final List<TrackerNode> children = new ArrayList<>();
		int edgeWeight;

		TrackerNode(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "<" + id + ">";
		}
	}

	@SuppressWarnings("boxing")
	private static void testRandOps(DoubleFunction<? extends DynamicTree<TrackerNode, Void>> builder, final int m,
			List<Op> ops) {
		DebugPrintsManager debug = new DebugPrintsManager(false);
		debug.println("\tnew iteration");
		Random rand = new Random(nextRandSeed());

		final int MAX_WEIGHT = 10000000;
		final int MAX_WEIGHT_LINK = 1000;
		final int MAX_WEIGHT_ADD = 100;
		List<TrackerNode> nodes = new ArrayList<>();
		List<TrackerNode> roots = new ArrayList<>();
		DynamicTree<TrackerNode, Void> tree = builder.apply(MAX_WEIGHT);

		Function<TrackerNode, TrackerNode> findRoot = node -> {
			TrackerNode root;
			for (root = node; root.parent != null;)
				root = root.parent;
			return root;
		};

		for (int i = 0; i < m;) {
			Op op = ops.get(rand.nextInt(ops.size()));
			switch (op) {
			case MakeTree: {
				TrackerNode node = new TrackerNode(nodes.size());
				DynamicTree.Node<TrackerNode, Void> dtNode = tree.makeTree(node);
				node.dtNode = dtNode;
				debug.println(op, "() -> ", dtNode);
				nodes.add(node);
				roots.add(node);
				break;
			}
			case FindRoot: {
				if (nodes.isEmpty())
					continue;
				TrackerNode node = nodes.get(rand.nextInt(nodes.size()));
				debug.println(op, "(", node, ")");

				TrackerNode root = findRoot.apply(node);
				DynamicTree.Node<TrackerNode, Void> expected = root.dtNode;
				DynamicTree.Node<TrackerNode, Void> actual = tree.findRoot(node.dtNode);
				assertEq(expected, actual, "FindRoot failure");
				break;
			}
			case FindMinEdge: {
				if (nodes.isEmpty())
					continue;
				TrackerNode node = nodes.get(rand.nextInt(nodes.size()));
				debug.println(op, "(", node, ")");

				TrackerNode min = null;
				for (TrackerNode p = node; p.parent != null; p = p.parent)
					if (min == null || p.edgeWeight <= min.edgeWeight)
						min = p;
				Object[] expected = min != null ? new Object[] { min.dtNode, min.edgeWeight } : null;

				DynamicTree.MinEdge<TrackerNode, Void> actual0 = tree.findMinEdge(node.dtNode);
				Object[] actual = actual0 != null ? new Object[] { actual0.u(), (int) Math.round(actual0.weight()) }
						: null;

				assertTrue(Arrays.equals(expected, actual),
						"FindMinEdge failure: " + Arrays.toString(expected) + " != " + Arrays.toString(actual) + "\n");
				break;
			}
			case AddWeight: {
				if (nodes.isEmpty())
					continue;
				TrackerNode node = nodes.get(rand.nextInt(nodes.size()));
				int weight = rand.nextInt(MAX_WEIGHT_ADD * 2 + 1) - MAX_WEIGHT_ADD;
				debug.println(op, "(", node, ", ", weight, ")");

				tree.addWeight(node.dtNode, weight);

				for (TrackerNode p = node; p.parent != null; p = p.parent)
					p.edgeWeight += weight;

				break;
			}
			case Link: {
				if (roots.size() < 2)
					continue;
				final int RETRY_MAX = 100;
				TrackerNode a = null, b = null;
				boolean found = false;
				for (int r = 0; r < RETRY_MAX; r++) {
					a = roots.get(rand.nextInt(roots.size()));
					b = nodes.get(rand.nextInt(nodes.size()));
					if (findRoot.apply(b) != a) {
						found = true;
						break;
					}
				}
				if (!found)
					continue;
				assert a.parent == null;
				int weight = rand.nextInt(MAX_WEIGHT_LINK);
				debug.println(op, "(", a, ", ", b, ", ", weight, ")");

				tree.link(a.dtNode, b.dtNode, weight, null);

				a.parent = b;
				a.edgeWeight = weight;
				b.children.add(a);
				roots.remove(a);

				break;
			}
			case Cut: {
				if (nodes.size() <= roots.size() || rand.nextInt(3) != 0)
					continue;
				final int RETRY_MAX = 100;
				TrackerNode node = null;
				boolean found = false;
				for (int r = 0; r < RETRY_MAX; r++) {
					node = nodes.get(rand.nextInt(nodes.size()));
					if (node.parent != null) {
						found = true;
						break;
					}
				}
				if (!found)
					continue;
				debug.println(op, "(", node, ")");

				node.parent.children.remove(node);
				node.parent = null;
				node.edgeWeight = 0;
				roots.add(node);

				tree.cut(node.dtNode);
				break;
			}
			case Evert: {
				throw new UnsupportedOperationException();
			}
			case Size: {
				if (nodes.isEmpty())
					continue;
				TrackerNode node = nodes.get(rand.nextInt(nodes.size()));

				int expected = 0;
				List<TrackerNode> stack = new ArrayList<>();
				stack.add(findRoot.apply(node));
				while (!stack.isEmpty()) {
					expected++;
					TrackerNode n = stack.get(stack.size() - 1);
					stack.remove(stack.size() - 1);
					for (TrackerNode c : n.children)
						stack.add(c);
				}

				int actual = tree.size(node.dtNode);
				assertEq(expected, actual, "Wrong size");
				break;
			}
			default:
				throw new InternalError();
			}
			i++;
		}
	}

}
