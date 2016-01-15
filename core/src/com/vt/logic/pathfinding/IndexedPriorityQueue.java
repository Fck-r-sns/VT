package com.vt.logic.pathfinding;

import java.util.HashMap;

/**
 * Created by fckrsns on 10.01.2016.
 */

public class IndexedPriorityQueue<K, V extends Comparable<V>> {
    public static class Result<K, V> {
        public K key;
        public V priority;

        public Result(K key, V priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    private int m_capacity;
    private int m_size;
    private V[] m_heap;
    private HashMap<K, Integer> m_keyToHeapPos;
    private K[] m_heapPosToKey;

    public IndexedPriorityQueue(int capacity) {
        m_capacity = capacity;
        m_size = 0;
        m_heap = (V[])new Comparable[m_capacity];
        m_keyToHeapPos = new HashMap<K, Integer>(m_capacity);
        m_heapPosToKey = (K[])new Object[m_capacity];
    }

    public void insert(K key, V priority) {
        if (m_size >= m_capacity)
            throw new IndexOutOfBoundsException("No more capacity");
        m_heap[m_size] = priority;
        m_keyToHeapPos.put(key, m_size);
        m_heapPosToKey[m_size] = key;
        if (m_size > 1)
            goUp(m_size);
        ++m_size;
    }

    public V getPriority(K key) {
        int pos = m_keyToHeapPos.get(key);
        return m_heap[pos];
    }

    public void changePriority(K key, V value) {
        int pos = m_keyToHeapPos.get(key);
        if (m_heap[pos].compareTo(value) > 0)
            goDown(pos);
        else
            goUp(pos);
    }

    public Result<K, V> deleteMin() {
        if (isEmpty())
            throw new IndexOutOfBoundsException("Queue is empty");
        K key = m_heapPosToKey[0];
        V priority = m_heap[0];
        --m_size;
        if (!isEmpty()) {
            swap(0, m_size);
            if (m_size > 1)
                goDown(0);
        }
        return new Result<K, V>(key, priority);
    }

    public boolean isEmpty() {
        return (m_size == 0);
    }

    public boolean contains(K key) {
        return m_keyToHeapPos.containsKey(key);
    }

    private void swap(int heapPos1, int heapPos2) {
        V tmp = m_heap[heapPos1];
        m_heap[heapPos1] = m_heap[heapPos2];
        m_heap[heapPos2] = tmp;
        K key1 = m_heapPosToKey[heapPos1];
        K key2 = m_heapPosToKey[heapPos2];
        m_keyToHeapPos.put(key1, heapPos2);
        m_keyToHeapPos.put(key2, heapPos1);
        m_heapPosToKey[heapPos1] = key2;
        m_heapPosToKey[heapPos2] = key1;
    }

    private void goUp(int pos) {
        do {
            int parentPos = (pos - 1) / 2;
            if (m_heap[pos].compareTo(m_heap[parentPos]) >= 0)
                break;
            swap(pos, parentPos);
            pos = parentPos;
        } while (pos != 0);
    }

    private void goDown(int pos) {
        do {
            int rightChild = (pos + 1) * 2;
            int leftChild = rightChild - 1;
            int minChild;
            if (leftChild >= m_size)
                break; // pos is leaf
            if (rightChild >= m_size)
                minChild = leftChild; // no right child
            else
                minChild = (m_heap[leftChild].compareTo(m_heap[rightChild]) <= 0) ? leftChild : rightChild;
            if (m_heap[pos].compareTo(m_heap[minChild]) <= 0)
                break;
            swap(pos, minChild);
            pos = minChild;
        } while (true);
    }
}
