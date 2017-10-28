package ru.otus.hw;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class MyArrayList<E extends Object> implements List<E> {

    Object[] arr;

    private int size = 0;
    private final static int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        arr = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i]==o) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {        // TODO add Test
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {     // Todo add Test
        return null;
    }

    @Override
    public boolean add(E e) {
        arr[size] = e;
        size++;
        if (size == arr.length) increaseArray();
        return true;
    }

    @Override
    public boolean remove(Object o) {           // Todo add Test
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {   // Todo add Testints.set(1,10);
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {   // Todo add Test
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {  // Todo add Test
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {         // Todo add Test
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {         // Todo add Test
        return false;
    }

    @Override
    public void clear() {                           // Todo add Test

    }

    @Override
    public E get(int index) {
        if (index<0 || index > size) throw new IndexOutOfBoundsException();
        return (E)arr[index];
    }

    @Override
    public E set(int index, E element) {
        if (index<0 || index > size) throw new IndexOutOfBoundsException();
        E previous = (E) arr[index];
        arr[index] = element;
        return previous;
    }

    @Override
    public void add(int index, E element) {
        if (index<0 || index > size) throw new IndexOutOfBoundsException();
        Object[] objects = Arrays.copyOfRange(arr, index, arr.length);
        arr[index] = element;
        int count = 0;
        for (int i = index +1; i < arr.length; i++) {
            E curElement = (E) objects[count];
            if (curElement == null) break;
           arr[i] = curElement;
            count++;
        }
        size++;
        if (size == arr.length) increaseArray();
    }

    @Override
    public E remove(int index) {
        if (index<0 || index > size) throw new IndexOutOfBoundsException();

        Object[] objects = Arrays.copyOfRange(arr, index+1, arr.length);
        E element = (E) arr[index];
        int count = 0;
        for (int i = index; i < arr.length; i++) {
            E curElement = (E) objects[count];
            if (curElement == null) break;
            arr[i] = curElement;
            count++;
        }

        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {                  // Todo add Test
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {              // Todo add Test
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {         // Todo add Test
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);// Todo add Test
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {            // Todo add Test
        return null;
    }

    private void increaseArray() {
        arr = Arrays.copyOf(arr,arr.length*2 +1);
    }

    private class Itr implements Iterator<E> {

        int cursor = 0;
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            E element = (E) arr[cursor];
            cursor++;
            return element;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) arr, 0, size, c);
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {

        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.arr;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
