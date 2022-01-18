package com.ugav.algo.test;

import com.ugav.algo.MatchingWeightedBipartiteHungarianMethod;

public class MatchingWeightedBipartiteHungarianMethodTest {

	@Test
	public static boolean randBipartiteGraphsWeight1() {
		return MatchingBipartiteTestUtils.randBipartiteGraphs(MatchingWeightedBipartiteHungarianMethod.getInstance());
	}

	@Test
	public static boolean randBipartiteGraphsWeighted() {
		return MatchingWeightedTestUtils
				.randBipartiteGraphsWeighted(MatchingWeightedBipartiteHungarianMethod.getInstance());
	}

}