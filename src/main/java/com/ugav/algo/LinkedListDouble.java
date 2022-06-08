package com.ugav.algo;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedListDouble {

	static class Node<N extends Node<N>> {
		N next;
		N prev;
	}

	static <N extends Node<N>> N add(N head, N node) {
		if (head != null) {
			node.next = head;
			head.prev = node;
		}
		return node;
	}

	static class Iter<N extends Node<N>> {
		N ptr;

		Iter(N head) {
			ptr = head;
		}

		boolean hasNextNode() {
			return ptr != null;
		}

		N nextNode() {
			if (!(hasNextNode()))
				throw new NoSuchElementException();
			N ret = ptr;
			ptr = ptr.next;
			return ret;
		}

		N pickNextNode() {
			if (!(hasNextNode()))
				throw new NoSuchElementException();
			return ptr;
		}

	}

	static class IterNodes<N extends Node<N>> extends Iter<N> implements Iterator<N> {

		IterNodes(N head) {
			super(head);
		}

		@Override
		public boolean hasNext() {
			return hasNextNode();
		}

		@Override
		public N next() {
			return nextNode();
		}

	}

}
