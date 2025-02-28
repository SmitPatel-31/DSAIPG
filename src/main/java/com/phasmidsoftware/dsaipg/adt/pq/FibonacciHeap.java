package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Comparator;
import java.util.Iterator;

/**
 * A simple Fibonacci Heap implementation functioning as a min-heap.
 * Supports basic operations: give (insert) and take (remove min).
 *
 * @param <K> the type of elements held in this heap
 */
public class FibonacciHeap<K> implements PriorityQueueInterface<K>, Iterable<K> {

    private Comparator<K> comparator;
    private Node<K> min;
    private int n;

    private static class Node<K> {
        K key;
        int degree;
        Node<K> parent;
        Node<K> child;
        Node<K> left;
        Node<K> right;
        boolean mark;

        Node(K key) {
            this.key = key;
            degree = 0;
            parent = null;
            child = null;
            left = this;
            right = this;
            mark = false;
        }
    }

    public FibonacciHeap(int capacity, boolean max, Comparator<K> comparator) {
        // Capacity and max are ignored; we implement a min-heap.
        this.comparator = comparator;
        min = null;
        n = 0;
    }

    @Override
    public boolean isEmpty() {
        return min == null;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void give(K key) {
        Node<K> node = new Node<>(key);
        if (min == null) {
            min = node;
        } else {
            // Insert node into the root list.
            node.left = min;
            node.right = min.right;
            min.right.left = node;
            min.right = node;
            if (comparator.compare(key, min.key) < 0) {
                min = node;
            }
        }
        n++;
    }

    @Override
    public K take() throws PQException {

    }

    private void consolidate() {

    }

    private void link(Node<K> y, Node<K> x) {

    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Iterator not implemented for FibonacciHeap");
    }
}