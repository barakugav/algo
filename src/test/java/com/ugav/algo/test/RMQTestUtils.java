package com.ugav.algo.test;

import java.util.Random;
import java.util.function.Supplier;

import com.ugav.algo.RMQ;
import com.ugav.algo.RMQ.ArrayIntComparator;

@SuppressWarnings("boxing")
class RMQTestUtils extends TestUtils {

	private RMQTestUtils() {
		throw new InternalError();
	}

	static void testRMQ65536(Supplier<? extends RMQ> builder) {
		testRMQ(builder, 65536, 4096);
	}

	static void testRMQ(Supplier<? extends RMQ> builder, int n, int queriesNum) {
		int[] a = new int[n];
		int[][] queries = new int[queriesNum][];
		randRMQDataAndQueries(a, queries);

		testRMQ(builder, a, queries);
	}

	static void testRMQ(Supplier<? extends RMQ> builder, int a[], int[][] queries) {
		RMQ rmq = builder.get();
		rmq.preprocessRMQ(new ArrayIntComparator(a), a.length);

		for (int idx = 0; idx < queries.length; idx++) {
			int i = queries[idx][0];
			int j = queries[idx][1];
			int expectedIdx = queries[idx][2];
			int expected = a[expectedIdx];
			int actualIdx = rmq.calcRMQ(i, j);
			int actual = a[actualIdx];

			if (actual != expected) {
				printTestStr(" [", i, ",", j, "] -> expected[", expectedIdx, "]=", expected, " actual[", actualIdx,
						"]=", actual, "\n");
				printTestStr("data size: ", a.length, "\n");
				printTestStr("queries num: ", queries.length, "\n");
				printTestStr(formatRMQDataAndQueries(a, queries));
				testFail();
			}
		}
	}

	static void randRMQDataPlusMinusOne(int a[]) {
		Random rand = new Random(nextRandSeed());
		a[0] = 0;
		for (int i = 1; i < a.length; i++)
			a[i] = a[i - 1] + rand.nextInt(2) * 2 - 1;
	}

	static void randRMQQueries(int a[], int[][] queries, int blockSize) {
		Random rand = new Random(nextRandSeed());
		for (int q = 0; q < queries.length;) {
			int i = rand.nextInt(a.length);
			if (i % blockSize == blockSize - 1)
				continue;
			int blockBase = (i / blockSize) * blockSize;
			int blockEnd = blockBase + blockSize;
			int j = rand.nextInt(blockEnd - i) + i + 1;

			int m = i;
			for (int k = i; k < j; k++)
				if (a[k] < a[m])
					m = k;
			queries[q++] = new int[] { i, j, m };
		}

	}

	static void randRMQDataAndQueries(int a[], int[][] queries) {
		randRMQDataAndQueries(a, queries, a.length);
	}

	static void randRMQDataAndQueries(int a[], int[][] queries, int blockSize) {
		Utils.randArray(a, 0, 64, nextRandSeed());
		randRMQQueries(a, queries, blockSize);
	}

	static CharSequence formatRMQDataAndQueries(int a[], int[][] queries) {
		StringBuilder s = new StringBuilder();

		final int dataPerLine = 32;
		final int queriesPerLine = 12;

		if (a.length == 0)
			s.append("{}\n");
		else {
			s.append("{");
			for (int i = 0; i < a.length - 1; i++) {
				s.append(a[i]);
				s.append(((i + 1) % dataPerLine) == 0 ? ",\n" : ", ");
			}
			s.append(a[a.length - 1]);
			s.append("}\n");
		}

		if (queries.length == 0)
			s.append("{}\n");
		else {
			s.append("{");
			for (int i = 0; i < queries.length - 1; i++) {
				int[] q = queries[i];
				s.append("{" + q[0] + "," + q[1] + "," + q[2] + "},");
				s.append(((i + 1) % queriesPerLine) == 0 ? "\n" : " ");
			}
			int[] q = queries[queries.length - 1];
			s.append("{" + q[0] + "," + q[1] + "," + q[2] + "}");
			s.append("}\n");
		}

		return s;
	}
}
