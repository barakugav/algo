package com.ugav.algo.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestList {

	private static final Collection<Class<?>> RMQ_TESTS = List.of(
			RMQLookupTableTest.class,
			RMQPowerOf2TableTest.class,
			RMQPlusMinusOneBenderFarachColton2000Test.class,
			RMQGabowBentleyTarjan1984Test.class);

	private static final Collection<Class<?>> HEAP_TESTS = List.of(
			HeapBinaryTest.class,
			HeapBinomialTest.class,
			HeapFibonacciTest.class);

	private static final Collection<Class<?>> MISC_TESTS = List.of(
			UnionFindPtrTest.class,
			UnionFindArrayTest.class,
			SplitFindMinArrayTest.class,
			LCARMQBenderFarachColton2000Test.class,
			TPMKomlos1985King1997Hagerup2009Test.class,
			MaxFlowEdmondsKarpTest.class);

	private static final Collection<Class<?>> GRAPHS_DISTANCE_TESTS = List.of(
			SSSPDijkstraTest.class,
			SSSPDial1969Test.class,
			SSSPBellmanFordTest.class);

	private static final Collection<Class<?>> MST_TESTS = List.of(
			MSTBoruvka1926Test.class,
			MSTKruskal1956Test.class,
			MSTPrim1957Test.class,
			MSTFredmanTarjan1987Test.class,
			MSTKargerKleinTarjan1995Test.class);

	private static final Collection<Class<?>> MATCHING_TESTS = List.of(
			MatchingBipartiteHopcroftKarp1973Test.class,
			MatchingGabow1976Test.class);

	static final Collection<Class<?>> TEST_CLASSES;
	static {
		Collection<Class<?>> l = new ArrayList<>();
		l.addAll(RMQ_TESTS);
		l.addAll(HEAP_TESTS);
		l.addAll(MISC_TESTS);
		l.addAll(GRAPHS_DISTANCE_TESTS);
		l.addAll(MST_TESTS);
		l.addAll(MATCHING_TESTS);
		TEST_CLASSES = Collections.unmodifiableCollection(l);
	}

}
