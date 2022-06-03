package com.ugav.algo.test;

import com.ugav.algo.RMQPowerOf2Table;

public class RMQPowerOf2TableTest extends TestUtils {

	@Test
	public static boolean regularNRange64to256() {
		for (int n = 64; n <= 256; n++)
			if (RMQTestUtils.testRMQ(RMQPowerOf2Table::new, n, 1024) != true)
				return false;
		return true;
	}

	@Test
	public static boolean regular65536() {
		return RMQTestUtils.testRMQ65536(RMQPowerOf2Table::new);
	}

}
