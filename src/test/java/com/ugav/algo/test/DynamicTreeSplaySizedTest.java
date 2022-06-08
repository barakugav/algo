package com.ugav.algo.test;

import java.util.List;

import com.ugav.algo.DynamicTreeSplaySized;
import com.ugav.algo.test.DynamicTreeSplayTest.Op;

public class DynamicTreeSplaySizedTest extends TestUtils {

	@Test
	public static boolean randOps() {
		List<Op> ops = List.of(Op.MakeTree, Op.FindRoot, Op.FindMinEdge, Op.AddWeight, Op.Link, Op.Cut, Op.Size);
		return DynamicTreeSplayTest.testRandOps(DynamicTreeSplaySized::new, ops);
	}
}
