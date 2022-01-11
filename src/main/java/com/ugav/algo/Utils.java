package com.ugav.algo;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;

class Utils {

	private Utils() {
		throw new InternalError();
	}

	private static final double LOG2 = Math.log(2);
	private static final double LOG2_INV = 1 / LOG2;

	static double log2(double x) {
		return Math.log(x) * LOG2_INV;
	}

	static int log2(int x) {
		int r = 0xFFFF - x >> 31 & 0x10;
		x >>= r;
		int shift = 0xFF - x >> 31 & 0x8;
		x >>= shift;
		r |= shift;
		shift = 0xF - x >> 31 & 0x4;
		x >>= shift;
		r |= shift;
		shift = 0x3 - x >> 31 & 0x2;
		x >>= shift;
		r |= shift;
		r |= (x >> 1);
		return r;
	}

	static int log2ceil(int x) {
		int r = log2(x);
		return (1 << r) == x ? r : r + 1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Comparator DEFAULT_COMPARATOR = (a, b) -> ((Comparable) a).compareTo(b);

	@SuppressWarnings("unchecked")
	static <E> Comparator<E> getDefaultComparator() {
		return DEFAULT_COMPARATOR;
	}

	static <E> Iterable<E> iterable(Iterator<E> it) {
		/*
		 * java lack nice for loop syntax using iterators, hopefully this code will be
		 * inlined by the compiler and no object will be created here
		 */
		return new Iterable<>() {

			@Override
			public Iterator<E> iterator() {
				return it;
			}
		};
	}

	static class ArrayView<E> extends AbstractList<E> implements RandomAccess {

		private final E[] a;
		private int size;

		ArrayView(E[] array, int size) {
			a = Objects.requireNonNull(array);
			this.size = size;
		}

		public void setSize(int size) {
			if (size < 0 || size > a.length)
				throw new IllegalArgumentException();
			this.size = size;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public E get(int index) {
			if (index >= size)
				throw new IndexOutOfBoundsException("index " + index + " out of bound for size " + size);
			return a[index];
		}

		@Override
		public E set(int index, E element) {
			if (index >= size)
				throw new IndexOutOfBoundsException("index " + index + " out of bound for size " + size);
			E oldValue = a[index];
			a[index] = element;
			return oldValue;
		}

		@Override
		public int indexOf(Object o) {
			E[] a = this.a;
			if (o == null) {
				for (int i = 0; i < size; i++)
					if (a[i] == null)
						return i;
			} else {
				for (int i = 0; i < size; i++)
					if (o.equals(a[i]))
						return i;
			}
			return -1;
		}

		@Override
		public boolean contains(Object o) {
			return indexOf(o) >= 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new ArrayItr();
		}

		private class ArrayItr implements Iterator<E> {
			private int cursor;

			@Override
			public boolean hasNext() {
				return cursor < size;
			}

			@Override
			public E next() {
				int i = cursor;
				if (i >= size) {
					throw new NoSuchElementException();
				}
				cursor = i + 1;
				return a[i];
			}
		}
	}

	static class NullIterator<E> implements Iterator<E> {

		private int size;

		NullIterator(int size) {
			if (size < 0)
				throw new IllegalArgumentException();
			this.size = size;
		}

		@Override
		public boolean hasNext() {
			return size > 0;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			size--;
			return null;
		}

	}

	static class NullListIterator<E> implements ListIterator<E> {

		private final int size;
		private int idx;

		NullListIterator(int size) {
			this(size, 0);
		}

		NullListIterator(int size, int idx) {
			if (size < 0)
				throw new IllegalArgumentException();
			if (idx < 0 || idx > size)
				throw new IllegalArgumentException();
			this.size = size;
			this.idx = idx;
		}

		@Override
		public boolean hasNext() {
			return idx < size;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			idx++;
			return null;
		}

		@Override
		public boolean hasPrevious() {
			return idx > 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			idx--;
			return null;
		}

		@Override
		public int nextIndex() {
			return idx;
		}

		@Override
		public int previousIndex() {
			return idx - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}

	}

	static class NullList<E> extends AbstractList<E> {

		private final int size;

		NullList(int size) {
			if (size < 0)
				throw new IllegalArgumentException();
			this.size = size;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean contains(Object o) {
			return o == null && size > 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new NullIterator<>(size);
		}

		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E get(int index) {
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException();
			return null;
		}

		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int indexOf(Object o) {
			return o == null && size > 0 ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object o) {
			return o == null && size > 0 ? size - 1 : -1;
		}

		@Override
		public ListIterator<E> listIterator() {
			return new NullListIterator<>(size);
		}

		@Override
		public ListIterator<E> listIterator(int index) {
			return new NullListIterator<>(size, index);
		}

		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			if (fromIndex < 0 || fromIndex >= toIndex || toIndex > size)
				throw new IllegalArgumentException();
			if (fromIndex == 0 && toIndex == size)
				return this;
			return new NullList<>(toIndex - fromIndex);
		}

	}

}
