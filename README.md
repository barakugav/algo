# Algo

Algo is a collection of algorithms implemented in Java. It contains mostly algorithms for various graphs problems and some utilities data structures and array algorithms.

## Supported algorithms

- Heaps
   - Binary heap
   - Binomial heap
   - Fibonacci heap
   - Red-Black binary search tree
 - UnionFind
 - SplitFindMin
 - SSSP (Single source shortest path)
   - Dijkstra positive weights, O(m + nlogn)
   - Dial1969 positive integer weights, O(m + D) where D is the maximum distance
   - BellmanFord general weights, O(m * n)
   - Goldberg1995 positive and negative integer weights, O(m * n^0.5 * logN) where N is the minimum negative weight
 - RMQ (range query minimum)
   - PlusMinusOneBenderFarachColton2000, only for +/-1 arrays, O(n) preprocessing, O(1) query
   - GabowBentleyTarjan1984, O(n) preprocessing, O(1) query
 - LCA BenderFarachColton2000 (lowest common ancestor) static, using RMQ, O(n)
 - Max flow EdmondsKarp O(m * n^2)
 - MST (minimum spanning tree)
   - Boruvka1926 O(mlogn)
   - Kruskal1956 O(mlogn)
   - Prim1957 O(m + nlogn)
   - Yao1976 O(mloglogn + nlogn)
   - FredmanTarjan1987 O(mlog*n)
   - KargerKleinTarjan1995 randomized algorithm, expected time O(m + n)
   - MDST Tarjan1977 directed graphs, O(mlogn)
 - TMP (tree path maxima)
   - Komlos1985King1997Hagerup2009 O(m + n) where m is the number of queries
 - Maximum matching
   - HopcroftKarp1973 for bipartite unweighted graphs, O(m * n^0.5)
   - Gabow1976 for general unweighted graphs, O(m * n * alpha(m,n)) (alpha is inverse Ackermann's function)
   - SSSP bipartite weighted matching, O(mn + n^2logn)
   - Hungarian method for bipartite weighted matching, O(mn + n^2logn)
   - MatchingWeightedGabow2018 for general weighted graphs, O(m * n * logn), (WIP for O(m*n + n^2 logn))

Additional utils:
 - BFS / DFS
 - Connectivity components calculation, undirected O(m + n), strongly connected, directed O(m + n)
 - Topological sort calculation (DAG) O(m + n)
 - SSSP DAG (directed acyclic graph) O(m + n)
 - Arrays utils, K'th element O(n), bucket partition O(nlogk) where k is the bucket size
