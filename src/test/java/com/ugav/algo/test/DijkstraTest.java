package com.ugav.algo.test;

import com.ugav.algo.Dijkstra;
import com.ugav.algo.Graph;
import com.ugav.algo.Graphs;

public class DijkstraTest {

	@Test
	public static boolean basic() {
		int[][][] adjacencyMatrices = new int[][][] {
				{ { 0, 0, 35, 91, 0, 0, 0, 0, 76, 19, 49, 0, 9, 62, 77, 43 },
						{ 0, 0, 0, 32, 0, 0, 0, 0, 0, 1, 0, 0, 19, 87, 37, 0 },
						{ 35, 0, 0, 0, 0, 0, 74, 0, 24, 83, 55, 19, 0, 0, 37, 0 },
						{ 91, 32, 0, 0, 45, 0, 0, 29, 0, 66, 21, 41, 98, 60, 0, 0 },
						{ 0, 0, 0, 45, 0, 60, 61, 7, 0, 62, 95, 0, 97, 14, 95, 0 },
						{ 0, 0, 0, 0, 60, 0, 0, 0, 16, 69, 0, 34, 0, 0, 0, 1 },
						{ 0, 0, 74, 0, 61, 0, 0, 41, 0, 0, 13, 90, 0, 28, 0, 0 },
						{ 0, 0, 0, 29, 7, 0, 41, 0, 21, 31, 0, 11, 0, 32, 88, 40 },
						{ 76, 0, 24, 0, 0, 16, 0, 21, 54, 29, 49, 23, 0, 0, 56, 27 },
						{ 19, 1, 83, 66, 62, 69, 0, 31, 29, 0, 0, 66, 0, 0, 0, 38 },
						{ 49, 0, 55, 21, 95, 0, 13, 0, 49, 0, 0, 0, 0, 0, 66, 46 },
						{ 0, 0, 19, 41, 0, 34, 90, 11, 23, 66, 0, 0, 0, 0, 0, 80 },
						{ 9, 19, 0, 98, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 95 },
						{ 62, 87, 0, 60, 14, 0, 28, 32, 0, 0, 0, 0, 0, 33, 0, 0 },
						{ 77, 37, 37, 0, 95, 0, 0, 88, 56, 0, 66, 0, 73, 0, 44, 0 },
						{ 43, 0, 0, 0, 0, 1, 0, 40, 27, 38, 46, 80, 95, 0, 0, 0 } },

				{ { 0, 0, 47, 83, 36, 0, 0, 24, 77, 13, 48, 0, 0, 0, 0, 33 },
						{ 0, 0, 64, 0, 0, 0, 88, 0, 0, 0, 0, 0, 74, 57, 0, 0 },
						{ 47, 64, 0, 67, 84, 0, 0, 42, 32, 18, 64, 50, 0, 0, 0, 4 },
						{ 83, 0, 67, 83, 0, 5, 0, 39, 0, 0, 25, 98, 31, 10, 0, 0 },
						{ 36, 0, 84, 0, 0, 0, 0, 0, 97, 0, 22, 0, 73, 0, 0, 0 },
						{ 0, 0, 0, 5, 0, 0, 78, 92, 28, 0, 52, 77, 80, 88, 47, 73 },
						{ 0, 88, 0, 0, 0, 78, 0, 56, 0, 0, 52, 0, 82, 8, 74, 0 },
						{ 24, 0, 42, 39, 0, 92, 56, 0, 27, 28, 29, 31, 0, 54, 98, 0 },
						{ 77, 0, 32, 0, 97, 28, 0, 27, 74, 0, 90, 56, 0, 0, 0, 34 },
						{ 13, 0, 18, 0, 0, 0, 0, 28, 0, 0, 2, 0, 86, 0, 0, 0 },
						{ 48, 0, 64, 25, 22, 52, 52, 29, 90, 2, 19, 0, 0, 58, 68, 0 },
						{ 0, 0, 50, 98, 0, 77, 0, 31, 56, 0, 0, 0, 0, 0, 0, 70 },
						{ 0, 74, 0, 31, 73, 80, 82, 0, 0, 86, 0, 0, 72, 0, 0, 0 },
						{ 0, 57, 0, 10, 0, 88, 8, 54, 0, 0, 58, 0, 0, 0, 0, 94 },
						{ 0, 0, 0, 0, 0, 47, 74, 98, 0, 0, 68, 0, 0, 0, 51, 0 },
						{ 33, 0, 4, 0, 0, 73, 0, 0, 34, 0, 0, 70, 0, 94, 0, 25 } },

				{ { 35, 0, 29, 0, 20, 38, 0, 47, 0, 0, 0, 0, 29, 5, 0, 0 },
						{ 0, 0, 61, 45, 0, 0, 0, 0, 85, 0, 0, 92, 93, 0, 0, 24 },
						{ 29, 61, 0, 0, 60, 10, 0, 8, 28, 87, 0, 0, 0, 65, 19, 15 },
						{ 0, 45, 0, 0, 59, 1, 84, 0, 50, 0, 0, 91, 97, 50, 21, 68 },
						{ 20, 0, 60, 59, 0, 28, 0, 17, 0, 36, 0, 0, 48, 0, 43, 32 },
						{ 38, 0, 10, 1, 28, 62, 36, 0, 39, 0, 0, 65, 95, 0, 42, 0 },
						{ 0, 0, 0, 84, 0, 36, 37, 0, 0, 0, 73, 0, 0, 28, 0, 81 },
						{ 47, 0, 8, 0, 17, 0, 0, 17, 0, 0, 0, 0, 65, 0, 20, 0 },
						{ 0, 85, 28, 50, 0, 39, 0, 0, 0, 25, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 87, 0, 36, 0, 0, 0, 25, 69, 18, 13, 10, 0, 0, 1 },
						{ 0, 0, 0, 0, 0, 0, 73, 0, 0, 18, 4, 0, 0, 28, 8, 0 },
						{ 0, 92, 0, 91, 0, 65, 0, 0, 0, 13, 0, 0, 0, 0, 0, 12 },
						{ 29, 93, 0, 97, 48, 95, 0, 65, 0, 10, 0, 0, 0, 17, 76, 0 },
						{ 5, 0, 65, 50, 0, 0, 28, 0, 0, 0, 28, 0, 17, 0, 70, 80 },
						{ 0, 0, 19, 21, 43, 42, 0, 20, 0, 0, 8, 0, 76, 70, 0, 69 },
						{ 0, 24, 15, 68, 32, 0, 81, 0, 0, 1, 0, 12, 0, 80, 69, 0 } },

				{ { 44, 0, 0, 92, 0, 0, 0, 0, 0, 13, 0, 45, 0, 7, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 57, 0, 34, 0, 0, 0, 0, 76, 0 },
						{ 0, 0, 46, 15, 40, 14, 51, 0, 0, 31, 90, 50, 90, 0, 7, 56 },
						{ 92, 0, 15, 92, 18, 0, 0, 0, 82, 0, 92, 0, 49, 0, 0, 77 },
						{ 0, 0, 40, 18, 0, 0, 0, 0, 0, 63, 37, 0, 25, 97, 0, 0 },
						{ 0, 0, 14, 0, 0, 0, 0, 73, 90, 0, 46, 3, 0, 0, 35, 0 },
						{ 0, 0, 51, 0, 0, 0, 33, 79, 37, 0, 0, 0, 0, 30, 71, 55 },
						{ 0, 57, 0, 0, 0, 73, 79, 66, 10, 23, 80, 0, 17, 0, 0, 25 },
						{ 0, 0, 0, 82, 0, 90, 37, 10, 0, 99, 64, 0, 62, 0, 0, 0 },
						{ 13, 34, 31, 0, 63, 0, 0, 23, 99, 50, 0, 33, 40, 0, 0, 52 },
						{ 0, 0, 90, 92, 37, 46, 0, 80, 64, 0, 0, 6, 48, 69, 0, 0 },
						{ 45, 0, 50, 0, 0, 3, 0, 0, 0, 33, 6, 71, 98, 82, 87, 0 },
						{ 0, 0, 90, 49, 25, 0, 0, 17, 62, 40, 48, 98, 0, 0, 51, 0 },
						{ 7, 0, 0, 0, 97, 0, 30, 0, 0, 0, 69, 82, 0, 0, 89, 0 },
						{ 0, 76, 7, 0, 0, 35, 71, 0, 0, 0, 0, 87, 51, 89, 0, 4 },
						{ 0, 0, 56, 77, 0, 0, 55, 25, 0, 52, 0, 0, 0, 0, 4, 46 } },

				{ { 16, 0, 10, 27, 0, 0, 50, 74, 65, 13, 76, 3, 56, 83, 0, 0 },
						{ 0, 0, 0, 30, 0, 96, 56, 33, 0, 0, 0, 26, 94, 44, 20, 0 },
						{ 10, 0, 0, 99, 0, 31, 96, 0, 76, 47, 59, 53, 9, 65, 52, 20 },
						{ 27, 30, 99, 0, 59, 86, 0, 8, 12, 0, 24, 0, 0, 50, 0, 35 },
						{ 0, 0, 0, 59, 0, 0, 0, 0, 64, 0, 0, 57, 98, 0, 0, 92 },
						{ 0, 96, 31, 86, 0, 0, 64, 0, 0, 0, 90, 62, 86, 0, 0, 0 },
						{ 50, 56, 96, 0, 0, 64, 0, 75, 21, 0, 0, 0, 0, 5, 0, 0 },
						{ 74, 33, 0, 8, 0, 0, 75, 0, 27, 0, 0, 53, 88, 0, 37, 23 },
						{ 65, 0, 76, 12, 64, 0, 21, 27, 0, 0, 5, 0, 0, 0, 27, 0 },
						{ 13, 0, 47, 0, 0, 0, 0, 0, 0, 0, 11, 0, 42, 0, 0, 84 },
						{ 76, 0, 59, 24, 0, 90, 0, 0, 5, 11, 0, 49, 29, 0, 0, 0 },
						{ 3, 26, 53, 0, 57, 62, 0, 53, 0, 0, 49, 0, 0, 0, 0, 67 },
						{ 56, 94, 9, 0, 98, 86, 0, 88, 0, 42, 29, 0, 21, 0, 0, 0 },
						{ 83, 44, 65, 50, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 48 },
						{ 0, 20, 52, 0, 0, 0, 0, 37, 27, 0, 0, 0, 0, 0, 8, 0 },
						{ 0, 0, 20, 35, 92, 0, 0, 23, 0, 84, 0, 67, 0, 48, 0, 0 } },

				{ { 0, 0, 55, 0, 22, 0, 0, 85, 29, 23, 0, 14, 29, 86, 0, 11 },
						{ 0, 0, 0, 0, 42, 0, 10, 0, 31, 5, 58, 0, 0, 8, 92, 0 },
						{ 55, 0, 57, 0, 60, 0, 31, 53, 0, 0, 49, 20, 0, 60, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 88, 17, 90, 0, 80, 9, 76, 0, 48 },
						{ 22, 42, 60, 0, 0, 9, 97, 98, 0, 0, 62, 0, 74, 0, 0, 77 },
						{ 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 1, 13, 31, 0, 0 },
						{ 0, 10, 31, 0, 97, 0, 0, 0, 0, 0, 0, 0, 47, 88, 41, 10 },
						{ 85, 0, 53, 88, 98, 0, 0, 0, 86, 21, 31, 0, 0, 10, 0, 28 },
						{ 29, 31, 0, 17, 0, 0, 0, 86, 0, 85, 0, 54, 0, 2, 7, 73 },
						{ 23, 5, 0, 90, 0, 0, 0, 21, 85, 67, 46, 0, 0, 20, 0, 0 },
						{ 0, 58, 49, 0, 62, 0, 0, 31, 0, 46, 0, 24, 0, 0, 4, 64 },
						{ 14, 0, 20, 80, 0, 1, 0, 0, 54, 0, 24, 0, 18, 49, 0, 71 },
						{ 29, 0, 0, 9, 74, 13, 47, 0, 0, 0, 0, 18, 0, 0, 0, 0 },
						{ 86, 8, 60, 76, 0, 31, 88, 10, 2, 20, 0, 49, 0, 55, 0, 58 },
						{ 0, 92, 0, 0, 0, 0, 41, 0, 7, 0, 4, 0, 0, 0, 0, 0 },
						{ 11, 0, 0, 48, 77, 0, 10, 28, 73, 0, 64, 71, 0, 58, 0, 0 } },

				{ { 0, 0, 70, 35, 78, 74, 13, 0, 56, 99, 0, 0, 83, 0, 28, 66 },
						{ 0, 0, 0, 65, 1, 48, 81, 0, 35, 0, 91, 11, 0, 53, 93, 54 },
						{ 70, 0, 38, 0, 99, 17, 59, 0, 15, 94, 0, 0, 58, 31, 0, 0 },
						{ 35, 65, 0, 0, 0, 0, 74, 84, 0, 13, 0, 0, 8, 0, 53, 0 },
						{ 78, 1, 99, 0, 0, 0, 0, 0, 0, 0, 16, 20, 0, 0, 18, 0 },
						{ 74, 48, 17, 0, 0, 0, 14, 0, 0, 0, 58, 18, 0, 77, 80, 49 },
						{ 13, 81, 59, 74, 0, 14, 73, 0, 59, 53, 81, 6, 93, 0, 63, 0 },
						{ 0, 0, 0, 84, 0, 0, 0, 0, 77, 0, 0, 0, 0, 0, 0, 22 },
						{ 56, 35, 15, 0, 0, 0, 59, 77, 0, 0, 0, 57, 25, 26, 70, 64 },
						{ 99, 0, 94, 13, 0, 0, 53, 0, 0, 89, 0, 0, 0, 0, 0, 0 },
						{ 0, 91, 0, 0, 16, 58, 81, 0, 0, 0, 14, 0, 0, 0, 0, 0 },
						{ 0, 11, 0, 0, 20, 18, 6, 0, 57, 0, 0, 0, 0, 45, 99, 0 },
						{ 83, 0, 58, 8, 0, 0, 93, 0, 25, 0, 0, 0, 0, 0, 73, 97 },
						{ 0, 53, 31, 0, 0, 77, 0, 0, 26, 0, 0, 45, 0, 0, 91, 0 },
						{ 28, 93, 0, 53, 18, 80, 63, 0, 70, 0, 0, 99, 73, 91, 0, 80 },
						{ 66, 54, 0, 0, 0, 49, 0, 22, 64, 0, 0, 0, 97, 0, 80, 0 } },

				{ { 0, 0, 68, 59, 50, 41, 0, 0, 70, 0, 0, 0, 46, 0, 68, 0 },
						{ 0, 0, 35, 25, 0, 0, 24, 0, 0, 12, 0, 0, 35, 4, 21, 0 },
						{ 68, 35, 0, 0, 0, 0, 59, 70, 0, 3, 55, 40, 0, 0, 0, 81 },
						{ 59, 25, 0, 0, 46, 0, 0, 0, 0, 78, 20, 0, 61, 87, 0, 95 },
						{ 50, 0, 0, 46, 0, 0, 0, 0, 0, 8, 0, 30, 94, 0, 68, 11 },
						{ 41, 0, 0, 0, 0, 43, 91, 0, 35, 0, 9, 0, 68, 7, 0, 75 },
						{ 0, 24, 59, 0, 0, 91, 0, 0, 13, 52, 52, 0, 0, 0, 12, 0 },
						{ 0, 0, 70, 0, 0, 0, 0, 0, 81, 85, 0, 31, 28, 0, 0, 31 },
						{ 70, 0, 0, 0, 0, 35, 13, 81, 84, 0, 0, 22, 91, 97, 66, 77 },
						{ 0, 12, 3, 78, 8, 0, 52, 85, 0, 53, 0, 0, 6, 0, 0, 43 },
						{ 0, 0, 55, 20, 0, 9, 52, 0, 0, 0, 0, 29, 0, 0, 86, 0 },
						{ 0, 0, 40, 0, 30, 0, 0, 31, 22, 0, 29, 0, 0, 9, 59, 89 },
						{ 46, 35, 0, 61, 94, 68, 0, 28, 91, 6, 0, 0, 66, 0, 0, 0 },
						{ 0, 4, 0, 87, 0, 7, 0, 0, 97, 0, 0, 9, 0, 0, 0, 0 },
						{ 68, 21, 0, 0, 68, 0, 12, 0, 66, 0, 86, 59, 0, 0, 0, 37 },
						{ 0, 0, 81, 95, 11, 75, 0, 31, 77, 43, 0, 89, 0, 0, 37, 90 } },

				{ { 20, 0, 0, 0, 0, 60, 28, 39, 9, 42, 44, 0, 82, 0, 14, 77 },
						{ 0, 15, 0, 45, 3, 0, 51, 22, 0, 0, 54, 0, 0, 0, 0, 0 },
						{ 0, 0, 82, 66, 0, 0, 0, 0, 87, 0, 0, 42, 0, 0, 58, 93 },
						{ 0, 45, 66, 38, 83, 0, 0, 71, 0, 44, 0, 0, 0, 0, 0, 34 },
						{ 0, 3, 0, 83, 0, 0, 40, 36, 0, 0, 0, 39, 0, 56, 0, 0 },
						{ 60, 0, 0, 0, 0, 75, 0, 65, 37, 87, 0, 85, 0, 0, 7, 93 },
						{ 28, 51, 0, 0, 40, 0, 0, 67, 30, 10, 0, 97, 8, 39, 0, 48 },
						{ 39, 22, 0, 71, 36, 65, 67, 54, 0, 64, 0, 8, 98, 0, 0, 0 },
						{ 9, 0, 87, 0, 0, 37, 30, 0, 0, 29, 0, 0, 0, 0, 0, 34 },
						{ 42, 0, 0, 44, 0, 87, 10, 64, 29, 0, 0, 57, 0, 2, 76, 78 },
						{ 44, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 38, 0, 89, 33, 0 },
						{ 0, 0, 42, 0, 39, 85, 97, 8, 0, 57, 38, 0, 47, 85, 78, 0 },
						{ 82, 0, 0, 0, 0, 0, 8, 98, 0, 0, 0, 47, 0, 23, 2, 0 },
						{ 0, 0, 0, 0, 56, 0, 39, 0, 0, 2, 89, 85, 23, 0, 0, 0 },
						{ 14, 0, 58, 0, 0, 7, 0, 0, 0, 76, 33, 78, 2, 0, 0, 0 },
						{ 77, 0, 93, 34, 0, 93, 48, 0, 34, 78, 0, 0, 0, 0, 0, 44 } },

				{ { 0, 92, 0, 14, 47, 43, 0, 89, 0, 0, 85, 0, 0, 3, 2, 0 },
						{ 92, 0, 49, 64, 0, 0, 0, 0, 0, 63, 0, 0, 0, 0, 0, 42 },
						{ 0, 49, 0, 78, 27, 13, 0, 0, 77, 0, 20, 29, 29, 0, 89, 85 },
						{ 14, 64, 78, 0, 0, 0, 0, 32, 0, 29, 0, 22, 1, 65, 97, 62 },
						{ 47, 0, 27, 0, 39, 33, 50, 0, 94, 0, 6, 0, 96, 0, 0, 44 },
						{ 43, 0, 13, 0, 33, 0, 24, 20, 0, 25, 0, 3, 0, 0, 76, 0 },
						{ 0, 0, 0, 0, 50, 24, 0, 0, 97, 57, 97, 0, 12, 4, 0, 0 },
						{ 89, 0, 0, 32, 0, 20, 0, 0, 39, 0, 0, 51, 23, 0, 0, 0 },
						{ 0, 0, 77, 0, 94, 0, 97, 39, 47, 0, 44, 0, 0, 50, 0, 29 },
						{ 0, 63, 0, 29, 0, 25, 57, 0, 0, 0, 39, 0, 28, 0, 0, 0 },
						{ 85, 0, 20, 0, 6, 0, 97, 0, 44, 39, 0, 0, 28, 82, 0, 51 },
						{ 0, 0, 29, 22, 0, 3, 0, 51, 0, 0, 0, 0, 7, 89, 19, 0 },
						{ 0, 0, 29, 1, 96, 0, 12, 23, 0, 28, 28, 7, 0, 0, 0, 36 },
						{ 3, 0, 0, 65, 0, 0, 4, 0, 50, 0, 82, 89, 0, 0, 0, 0 },
						{ 2, 0, 89, 97, 0, 76, 0, 0, 0, 0, 0, 19, 0, 0, 15, 73 },
						{ 0, 42, 85, 62, 44, 0, 0, 0, 29, 0, 51, 0, 36, 0, 73, 64 } },

				{ { 0, 18, 0, 22, 0, 8, 55, 1, 75, 96, 0, 47, 0, 30, 0, 0 },
						{ 18, 0, 0, 59, 35, 0, 0, 0, 27, 0, 0, 29, 0, 0, 71, 0 },
						{ 0, 0, 0, 62, 0, 68, 95, 59, 11, 0, 22, 0, 0, 77, 0, 0 },
						{ 22, 59, 62, 80, 93, 38, 0, 0, 88, 64, 94, 0, 86, 3, 84, 0 },
						{ 0, 35, 0, 93, 0, 49, 0, 0, 54, 65, 82, 0, 0, 88, 49, 0 },
						{ 8, 0, 68, 38, 49, 38, 83, 0, 0, 20, 30, 0, 0, 6, 0, 0 },
						{ 55, 0, 95, 0, 0, 83, 0, 0, 63, 0, 35, 24, 15, 0, 0, 33 },
						{ 1, 0, 59, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 77, 0 },
						{ 75, 27, 11, 88, 54, 0, 63, 0, 0, 0, 42, 38, 41, 0, 0, 77 },
						{ 96, 0, 0, 64, 65, 20, 0, 22, 0, 0, 0, 0, 48, 0, 53, 0 },
						{ 0, 0, 22, 94, 82, 30, 35, 0, 42, 0, 27, 53, 29, 0, 94, 8 },
						{ 47, 29, 0, 0, 0, 0, 24, 0, 38, 0, 53, 0, 30, 5, 0, 0 },
						{ 0, 0, 0, 86, 0, 0, 15, 0, 41, 48, 29, 30, 0, 0, 10, 81 },
						{ 30, 0, 77, 3, 88, 6, 0, 0, 0, 0, 0, 5, 0, 0, 0, 95 },
						{ 0, 71, 0, 84, 49, 0, 0, 77, 0, 53, 94, 0, 10, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 33, 0, 77, 0, 8, 0, 81, 95, 0, 0 } },

				{ { 0, 61, 0, 0, 13, 0, 28, 6, 14, 99, 0, 24, 73, 33, 18, 0 },
						{ 61, 0, 0, 0, 0, 0, 0, 0, 2, 0, 19, 56, 0, 55, 66, 0 },
						{ 0, 0, 0, 0, 0, 93, 30, 0, 51, 0, 0, 39, 79, 33, 14, 0 },
						{ 0, 0, 0, 0, 28, 23, 19, 79, 1, 93, 56, 62, 15, 0, 0, 51 },
						{ 13, 0, 0, 28, 94, 0, 84, 72, 0, 0, 0, 0, 0, 0, 7, 0 },
						{ 0, 0, 93, 23, 0, 0, 0, 26, 0, 73, 0, 0, 0, 0, 86, 0 },
						{ 28, 0, 30, 19, 84, 0, 0, 0, 0, 45, 0, 0, 0, 20, 0, 0 },
						{ 6, 0, 0, 79, 72, 26, 0, 0, 0, 56, 89, 0, 12, 43, 0, 0 },
						{ 14, 2, 51, 1, 0, 0, 0, 0, 0, 36, 11, 51, 0, 98, 0, 23 },
						{ 99, 0, 0, 93, 0, 73, 45, 56, 36, 85, 74, 0, 19, 7, 0, 0 },
						{ 0, 19, 0, 56, 0, 0, 0, 89, 11, 74, 63, 0, 96, 17, 0, 0 },
						{ 24, 56, 39, 62, 0, 0, 0, 0, 51, 0, 0, 0, 25, 70, 60, 88 },
						{ 73, 0, 79, 15, 0, 0, 0, 12, 0, 19, 96, 25, 0, 0, 0, 74 },
						{ 33, 55, 33, 0, 0, 0, 20, 43, 98, 7, 17, 70, 0, 16, 0, 0 },
						{ 18, 66, 14, 0, 7, 86, 0, 0, 0, 0, 0, 60, 0, 0, 85, 0 },
						{ 0, 0, 0, 51, 0, 0, 0, 0, 23, 0, 0, 88, 74, 0, 0, 0 } },

				{ { 0, 0, 98, 85, 0, 0, 66, 0, 0, 47, 0, 0, 27, 0, 0, 0 },
						{ 0, 0, 9, 0, 22, 0, 34, 20, 0, 0, 0, 83, 92, 0, 0, 77 },
						{ 98, 9, 0, 0, 49, 0, 96, 0, 0, 37, 0, 0, 90, 9, 94, 69 },
						{ 85, 0, 0, 0, 27, 0, 0, 71, 0, 0, 23, 20, 0, 0, 59, 0 },
						{ 0, 22, 49, 27, 70, 0, 0, 0, 12, 0, 0, 55, 42, 63, 70, 0 },
						{ 0, 0, 0, 0, 0, 0, 30, 0, 0, 52, 47, 51, 56, 33, 59, 0 },
						{ 66, 34, 96, 0, 0, 30, 0, 96, 69, 60, 0, 80, 42, 17, 0, 26 },
						{ 0, 20, 0, 71, 0, 0, 96, 0, 34, 69, 16, 96, 0, 0, 0, 48 },
						{ 0, 0, 0, 0, 12, 0, 69, 34, 0, 0, 51, 75, 25, 6, 18, 0 },
						{ 47, 0, 37, 0, 0, 52, 60, 69, 0, 0, 0, 9, 0, 0, 0, 0 },
						{ 0, 0, 0, 23, 0, 47, 0, 16, 51, 0, 0, 0, 30, 53, 48, 0 },
						{ 0, 83, 0, 20, 55, 51, 80, 96, 75, 9, 0, 0, 32, 0, 0, 0 },
						{ 27, 92, 90, 0, 42, 56, 42, 0, 25, 0, 30, 32, 0, 0, 58, 29 },
						{ 0, 0, 9, 0, 63, 33, 17, 0, 6, 0, 53, 0, 0, 0, 21, 66 },
						{ 0, 0, 94, 59, 70, 59, 0, 0, 18, 0, 48, 0, 58, 21, 0, 94 },
						{ 0, 77, 69, 0, 0, 0, 26, 48, 0, 0, 0, 0, 29, 66, 94, 0 } },

				{ { 0, 9, 0, 0, 64, 87, 15, 65, 34, 0, 0, 27, 0, 87, 0, 0 },
						{ 9, 0, 0, 69, 0, 26, 0, 0, 16, 0, 12, 4, 98, 52, 64, 3 },
						{ 0, 0, 0, 40, 16, 0, 35, 35, 0, 21, 33, 0, 0, 17, 0, 0 },
						{ 0, 69, 40, 0, 0, 0, 0, 62, 0, 4, 75, 78, 0, 32, 27, 0 },
						{ 64, 0, 16, 0, 0, 0, 85, 1, 48, 65, 1, 48, 93, 0, 0, 84 },
						{ 87, 26, 0, 0, 0, 0, 45, 0, 0, 35, 68, 24, 47, 24, 0, 0 },
						{ 15, 0, 35, 0, 85, 45, 47, 12, 20, 77, 0, 0, 0, 0, 0, 0 },
						{ 65, 0, 35, 62, 1, 0, 12, 0, 0, 80, 79, 49, 0, 0, 97, 0 },
						{ 34, 16, 0, 0, 48, 0, 20, 0, 0, 0, 0, 76, 47, 0, 89, 75 },
						{ 0, 0, 21, 4, 65, 35, 77, 80, 0, 0, 0, 2, 0, 60, 41, 0 },
						{ 0, 12, 33, 75, 1, 68, 0, 79, 0, 0, 0, 0, 10, 0, 0, 0 },
						{ 27, 4, 0, 78, 48, 24, 0, 49, 76, 2, 0, 0, 0, 0, 0, 0 },
						{ 0, 98, 0, 0, 93, 47, 0, 0, 47, 0, 10, 0, 0, 0, 0, 72 },
						{ 87, 52, 17, 32, 0, 24, 0, 0, 0, 60, 0, 0, 0, 78, 0, 0 },
						{ 0, 64, 0, 27, 0, 0, 0, 97, 89, 41, 0, 0, 0, 0, 0, 52 },
						{ 0, 3, 0, 0, 84, 0, 0, 0, 75, 0, 0, 0, 72, 0, 52, 33 } },

				{ { 0, 0, 0, 0, 0, 58, 0, 0, 0, 0, 12, 0, 0, 30, 9, 46 },
						{ 0, 0, 25, 0, 15, 0, 59, 6, 0, 23, 0, 35, 0, 0, 0, 0 },
						{ 0, 25, 7, 75, 0, 98, 43, 0, 36, 36, 75, 0, 71, 83, 7, 0 },
						{ 0, 0, 75, 0, 0, 43, 13, 0, 37, 77, 0, 0, 76, 53, 54, 50 },
						{ 0, 15, 0, 0, 0, 93, 95, 0, 0, 0, 0, 3, 59, 52, 0, 0 },
						{ 58, 0, 98, 43, 93, 92, 0, 15, 0, 0, 0, 51, 53, 29, 0, 11 },
						{ 0, 59, 43, 13, 95, 0, 0, 85, 8, 14, 26, 88, 0, 0, 0, 0 },
						{ 0, 6, 0, 0, 0, 15, 85, 0, 0, 23, 85, 0, 0, 0, 75, 0 },
						{ 0, 0, 36, 37, 0, 0, 8, 0, 0, 0, 0, 39, 30, 0, 21, 0 },
						{ 0, 23, 36, 77, 0, 0, 14, 23, 0, 0, 89, 2, 0, 0, 42, 0 },
						{ 12, 0, 75, 0, 0, 0, 26, 85, 0, 89, 0, 50, 94, 78, 0, 44 },
						{ 0, 35, 0, 0, 3, 51, 88, 0, 39, 2, 50, 0, 60, 69, 0, 58 },
						{ 0, 0, 71, 76, 59, 53, 0, 0, 30, 0, 94, 60, 0, 0, 84, 0 },
						{ 30, 0, 83, 53, 52, 29, 0, 0, 0, 0, 78, 69, 0, 0, 0, 78 },
						{ 9, 0, 7, 54, 0, 0, 0, 75, 21, 42, 0, 0, 84, 0, 0, 46 },
						{ 46, 0, 0, 50, 0, 11, 0, 0, 0, 0, 44, 58, 0, 78, 46, 0 } },

				{ { 0, 0, 0, 0, 0, 4, 26, 83, 0, 0, 58, 34, 0, 0, 20, 0 },
						{ 0, 0, 0, 39, 77, 58, 0, 2, 0, 22, 31, 97, 21, 0, 71, 0 },
						{ 0, 0, 0, 13, 98, 0, 0, 8, 61, 0, 27, 99, 0, 1, 76, 92 },
						{ 0, 39, 13, 0, 0, 0, 0, 6, 90, 10, 1, 0, 9, 73, 0, 0 },
						{ 0, 77, 98, 0, 82, 49, 0, 0, 68, 0, 0, 36, 0, 99, 45, 0 },
						{ 4, 58, 0, 0, 49, 0, 42, 49, 80, 93, 0, 0, 73, 0, 82, 64 },
						{ 26, 0, 0, 0, 0, 42, 8, 6, 0, 25, 69, 0, 89, 38, 0, 51 },
						{ 83, 2, 8, 6, 0, 49, 6, 0, 0, 0, 96, 31, 0, 0, 2, 0 },
						{ 0, 0, 61, 90, 68, 80, 0, 0, 0, 0, 0, 22, 0, 0, 0, 98 },
						{ 0, 22, 0, 10, 0, 93, 25, 0, 0, 0, 0, 60, 0, 0, 69, 6 },
						{ 58, 31, 27, 1, 0, 0, 69, 96, 0, 0, 0, 0, 78, 25, 0, 0 },
						{ 34, 97, 99, 0, 36, 0, 0, 31, 22, 60, 0, 13, 0, 0, 0, 0 },
						{ 0, 21, 0, 9, 0, 73, 89, 0, 0, 0, 78, 0, 0, 0, 0, 0 },
						{ 0, 0, 1, 73, 99, 0, 38, 0, 0, 0, 25, 0, 0, 0, 0, 62 },
						{ 20, 71, 76, 0, 45, 82, 0, 2, 0, 69, 0, 0, 0, 0, 1, 54 },
						{ 0, 0, 92, 0, 0, 64, 51, 0, 98, 6, 0, 0, 0, 62, 54, 0 } } };
		int[] sources = new int[] { 0, 11, 11, 4, 11, 6, 14, 14, 9, 6, 6, 3, 4, 10, 13, 13 };
		int[][] expectedDistances = new int[][] { { 0, 20, 35, 52, 57, 44, 62, 50, 48, 19, 49, 54, 9, 62, 57, 43 },
				{ 55, 114, 50, 70, 82, 75, 87, 31, 56, 59, 60, 0, 101, 80, 122, 54 },
				{ 45, 36, 27, 38, 44, 37, 68, 35, 38, 13, 31, 0, 23, 40, 39, 12 },
				{ 76, 97, 33, 18, 0, 46, 84, 42, 52, 63, 37, 43, 25, 83, 40, 44 },
				{ 3, 26, 13, 30, 57, 44, 53, 38, 32, 16, 27, 0, 22, 58, 46, 33 },
				{ 21, 10, 31, 37, 43, 36, 0, 28, 20, 15, 31, 35, 46, 18, 27, 10 },
				{ 28, 19, 65, 53, 18, 48, 36, 95, 54, 66, 34, 30, 61, 72, 0, 73 },
				{ 68, 21, 36, 46, 41, 32, 12, 65, 25, 33, 41, 34, 39, 25, 0, 37 },
				{ 34, 53, 78, 44, 50, 27, 10, 64, 29, 0, 53, 57, 18, 2, 20, 58 },
				{ 7, 77, 35, 13, 46, 22, 0, 35, 54, 40, 40, 19, 12, 4, 9, 48 },
				{ 43, 53, 57, 32, 74, 35, 0, 44, 56, 55, 35, 24, 15, 29, 25, 33 },
				{ 15, 3, 47, 0, 28, 23, 19, 21, 1, 34, 12, 39, 15, 29, 33, 24 },
				{ 64, 22, 27, 27, 0, 51, 35, 42, 12, 56, 50, 47, 37, 18, 30, 61 },
				{ 21, 12, 17, 22, 1, 38, 14, 2, 28, 18, 0, 16, 10, 34, 49, 15 },
				{ 30, 50, 46, 53, 52, 29, 66, 44, 60, 57, 42, 55, 82, 0, 39, 40 },
				{ 31, 11, 1, 14, 56, 35, 15, 9, 62, 24, 15, 40, 23, 0, 11, 30 } };

		for (int i = 0; i < adjacencyMatrices.length; i++) {
			int[][] adjacencyMatrix = adjacencyMatrices[i];
			int source = sources[i];
			int[] distancesExpected = expectedDistances[i];
			int n = adjacencyMatrix.length;

			Graph<Integer> g = GraphsTestUtils.createGraphFromAdjacencyMatrixWeightedInt(adjacencyMatrix);
			double[] distancesActual = Dijkstra.getInstace().calcDistances(g, Graphs.WEIGHT_INT_FUNC_DEFAULT, source);

			for (int v = 0; v < n; v++) {
				double expeced = distancesExpected[v];
				double actual = distancesActual[v];
				if (expeced != actual) {
					TestUtils
							.printTestStr("Distance to vertex " + v + " is wrong: " + expeced + " != " + actual + "\n");
					return false;
				}
			}
		}

		return true;
	}

}