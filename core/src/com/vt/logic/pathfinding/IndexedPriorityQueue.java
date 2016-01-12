package com.vt.logic.pathfinding;

import java.util.HashMap;

/**
 * Created by fckrsns on 10.01.2016.
 */

public class IndexedPriorityQueue<K> {
    public static class Result<K> {
        public K key;
        public float priority;

        public Result(K key, float priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    private int m_capacity;
    private int m_size;
    private float[] m_heap;
    private HashMap<K, Integer> m_keyToHeapPos;
    private K[] m_heapPosToKey;

    public IndexedPriorityQueue(int capacity) {
        m_capacity = capacity;
        m_size = 0;
        m_heap = new float[m_capacity];
        m_keyToHeapPos = new HashMap<K, Integer>(m_capacity);
        m_heapPosToKey = (K[])new Object[m_capacity];
    }

    public void insert(K key, float priority) {
        if (m_size >= m_capacity)
            throw new IndexOutOfBoundsException("No more capacity");
        m_heap[m_size] = priority;
        m_keyToHeapPos.put(key, m_size);
        m_heapPosToKey[m_size] = key;
        if (m_size > 1)
            goUp(m_size);
        ++m_size;
    }

    public float getPriority(K key) {
        int pos = m_keyToHeapPos.get(key);
        return m_heap[pos];
    }

    public void changePriority(K key, float value) {
        int pos = m_keyToHeapPos.get(key);
        if (m_heap[pos] > value)
            goDown(pos);
        else
            goUp(pos);
    }

    public Result<K> deleteMin() {
        if (isEmpty())
            throw new IndexOutOfBoundsException("Queue is empty");
        K key = m_heapPosToKey[0];
        float priority = m_heap[0];
        --m_size;
        if (!isEmpty()) {
            swap(0, m_size);
            if (m_size > 1)
                goDown(0);
        }
        return new Result<K>(key, priority);
    }

    public boolean isEmpty() {
        return (m_size == 0);
    }

    public boolean contains(K key) {
        return m_keyToHeapPos.containsKey(key);
    }

    private void swap(int heapPos1, int heapPos2) {
        float tmp = m_heap[heapPos1];
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
            if (m_heap[pos] >= m_heap[parentPos])
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
                minChild = (m_heap[leftChild] <= m_heap[rightChild]) ? leftChild : rightChild;
            if (m_heap[pos] <= m_heap[minChild])
                break;
            swap(pos, minChild);
            pos = minChild;
        } while (true);
    }
}
