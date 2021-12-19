package com.ugav.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RMQGabowBentleyTarjan1984 extends RMQLinearAbstract {

	private RMQGabowBentleyTarjan1984() {
	}

	private static final RMQGabowBentleyTarjan1984 INSTANCE = new RMQGabowBentleyTarjan1984();

	public static RMQGabowBentleyTarjan1984 getInstace() {
		return INSTANCE;
	}

	@Override
	public RMQ.Result preprocessRMQ(RMQ.Comparator c, int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		Objects.requireNonNull(c);

		RMQGabowBentleyTarjan1984.DataStructure ds = new RMQGabowBentleyTarjan1984.DataStructure(n, c);

		preprocessRMQ(ds);

		return ds;
	}

	int[] calcDemoBlock(int key, int blockSize) {
		int demoBlock[] = new int[blockSize];

		int nodes[] = new int[blockSize];
		int nodesCount = 0;

		int keyIdx = 0;

		for (int i = 0; i < demoBlock.length; i++) {
			int x = nodesCount > 0 ? nodes[nodesCount - 1] + blockSize : 0;
			while ((key & (1 << keyIdx)) != 0) {
				x = nodes[nodesCount-- - 1] - 1;
				keyIdx++;
			}
			nodes[nodesCount++] = x;
			keyIdx++;

			demoBlock[i] = x;
		}

		return demoBlock;
	}

	int calcBlockKey(RMQGabowBentleyTarjan1984.DataStructure ds, int b) {
		int nodes[] = new int[ds.blockSize];
		int nodesCount = 0;

		int key = 0;
		int keyIdx = 0;

		int base = b * ds.blockSize;
		for (int i = 0; i < ds.blockSize; i++) {
			int x = base + i;
			while (nodesCount > 0 && ds.c.compare(x, nodes[nodesCount - 1]) < 0) {
				nodesCount--;
				key |= 1 << (keyIdx++);
			}
			nodes[nodesCount++] = x;
			keyIdx++;
		}

		return key;
	}

	@Override
	void initInterBlock(RMQLinearAbstract.DataStructure ds0) {
		RMQGabowBentleyTarjan1984.DataStructure ds = (RMQGabowBentleyTarjan1984.DataStructure) ds0;

		Map<Integer, RMQ.Result> tables = new HashMap<>();

		for (int b = 0; b < ds.blockNum; b++) {
			int key = calcBlockKey(ds, b);

			ds.interBlocksDs[b] = tables.computeIfAbsent(key, k -> {
				int demoBlock[] = calcDemoBlock(k, ds.blockSize);
				return RMQLookupTable.getInstace().preprocessRMQ(new IntArrayComparator(demoBlock), demoBlock.length);
			});
		}
	}

	static class DataStructure extends RMQLinearAbstract.DataStructure {

		final RMQ.Result interBlocksDs[];

		DataStructure(int n, Comparator c) {
			super(n, c);
			interBlocksDs = new RMQ.Result[blockNum];
		}

		@Override
		int getBlockSize(int n) {
			return (int) Math.ceil(Utils.log2((double) n) / 4);
		}

		@Override
		int queryInterBlock(int block, int i, int j) {
			return block * blockSize + interBlocksDs[block].query(i, j + 1);
		}

	}

}