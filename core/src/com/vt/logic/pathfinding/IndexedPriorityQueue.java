package com.vt.logic.pathfinding;

import java.util.HashMap;

/**
 * Created by fckrsns on 10.01.2016.
 */

public class IndexedPriorityQueue {
    private int m_capacity;
    private int m_size;
    private float[] m_heap;
    private int[] m_keyToHeapPos;
    private int[] m_heapPosToKey;

    public IndexedPriorityQueue(int capacity) {
        m_capacity = capacity;
        m_size = 0;
        m_heap = new float[m_capacity];
        m_keyToHeapPos = new int[m_capacity];
        m_heapPosToKey = new int[m_capacity];
    }

    public void insert(int key, float priority) {
        if (isEmpty()) {
            m_heap[0] = priority;
            m_keyToHeapPos[key] = 0;
            m_heapPosToKey[0] = key;
            ++m_size;
            return;
        }
        m_heap[m_size] = priority;
        m_keyToHeapPos[key] = m_size;
        m_heapPosToKey[m_size] = key;
        int pos = m_size;
        ++m_size;
        do {
            int parentPos = (pos - 1) / 2;
            if (m_heap[pos] >= m_heap[parentPos])
                break;
            swap(pos, parentPos);
            pos = parentPos;
        } while (pos != 0);
    }
//
//    public void changePriority(K key, V value) {
//
//    }

    public float deleteMin() {
        if (isEmpty())
            throw new IndexOutOfBoundsException("empty");
//        int res = m_heapPosToKey[0];
        float res = m_heap[0];
        --m_size;
        if (!isEmpty()) {
            swap(0, m_size);
            if (m_size >= 2) {
                int pos = 0;
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
        return res;
    }


    public boolean isEmpty() {
        return (m_size == 0);
    }
//
//    public boolean containsKey(K key) {
//
//    }
//
//    public boolean containsValue(V value) {
//
//    }

    private void swap(int heapPos1, int heapPos2) {
        float tmp = m_heap[heapPos1];
        m_heap[heapPos1] = m_heap[heapPos2];
        m_heap[heapPos2] = tmp;
        int key1 = m_heapPosToKey[heapPos1];
        int key2 = m_heapPosToKey[heapPos2];
        m_keyToHeapPos[key1] = heapPos2;
        m_keyToHeapPos[key2] = heapPos1;
        m_heapPosToKey[heapPos1] = key2;
        m_heapPosToKey[heapPos2] = key1;
    }
}
