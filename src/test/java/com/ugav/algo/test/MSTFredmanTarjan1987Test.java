package com.ugav.algo.test;

import com.ugav.algo.MSTFredmanTarjan1987;

public class MSTFredmanTarjan1987Test extends TestUtils {

	@Test
	public static void randGraph() {
		MSTTestUtils.testRandGraph(MSTFredmanTarjan1987::new);
	}

}
