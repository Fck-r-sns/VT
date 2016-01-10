package com.vt.logic.pathfinding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by fckrsns on 10.01.2016.
 */

// for pathfinding only
class IndexedPriorityQueue<K, V> {
    private TreeSet<V> m_set;
    private HashMap<K, V> m_index;

    public IndexedPriorityQueue() {
        m_set = new TreeSet<V>();
        m_index = new HashMap<K, V>();
    }

    public IndexedPriorityQueue(Comparator<V> cmp) {
        m_set = new TreeSet<V>(cmp);
        m_index = new HashMap<K, V>();
    }

    public void add(K key, V value) {
        if (m_index.containsKey(key))
            throw new RuntimeException("Object with such key is already contained");
        m_index.put(key, value);
        m_set.add(value);
    }

    public V take(K key) {
        if (!m_index.containsKey(key))
            throw new RuntimeException("Object with such key was not found");
        V value = m_index.get(key);
        m_index.remove(key);
        m_set.remove(value);
        return value;
    }

    public V first() {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        return m_set.first();
    }

    public void remove(K key) {
        V value = m_index.get(key);
        m_index.remove(key);
        m_set.remove(value);
    }

    public boolean isEmpty() {
        return m_set.isEmpty();
    }

    public boolean containsKey(K key) {
        return m_index.containsKey(key);
    }

    public boolean containsValue(V value) {
        return m_set.contains(value);
    }
}
