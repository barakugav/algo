package com.ugav.algo.test;

import com.ugav.algo.Graph;
import com.ugav.algo.Graph.DirectedType;
import com.ugav.algo.GraphTable;
import com.ugav.algo.test.GraphImplTestUtils.GraphImpl;

public class GraphTableTest extends TestUtils {

	private static GraphImpl tableImpl() {
		return new GraphImpl() {

			@Override
			public <E> Graph<E> newGraph(DirectedType directedType, int... vertices) {
				if (vertices.length == 1) {
					return new GraphTable<>(directedType, vertices[0]);
				} else {
					throw new UnsupportedOperationException();
				}
			}
		};
	}

	@Test
	public static boolean testUndirectedMST() {
		return GraphImplTestUtils.testUndirectedMST(tableImpl());
	}

	@Test
	public static boolean testDirectedMDST() {
		return GraphImplTestUtils.testDirectedMDST(tableImpl());
	}

	@Test
	public static boolean testDirectedMaxFlow() {
		return GraphImplTestUtils.testDirectedMaxFlow(tableImpl());
	}

}