package com.vt.physics;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * Created by fckrsns on 02.03.2016.
 */
public class SpatialHashTable<T> {
    private ObjectMap<SpatialHash, ObjectSet<T>> m_data;
    private int m_bucketCapacity;

    public SpatialHashTable(int tableCapacity, int bucketCapacity) {
        m_data = new ObjectMap<SpatialHash, ObjectSet<T>>(tableCapacity);
        m_bucketCapacity = bucketCapacity;
    }

    public ObjectSet<T> getBucket(SpatialHash hash) {
        return m_data.get(hash);
    }

    public void add(T value) {
        if (value instanceof SpatiallyHashable) {
            SpatialHash hash = ((SpatiallyHashable) value).spatialHash();
            checkAndCreateBucket(hash);
            m_data.get(hash).add(value);
        } else {
            throw new IllegalArgumentException("Value is not instance of SpatiallyHashable");
        }
    }

    public void add(SpatialHash hash, T value) {
        checkAndCreateBucket(hash);
        m_data.get(hash).add(value);
    }

    public ObjectSet<T> removeBucket(SpatialHash hash) {
        return m_data.remove(hash);
    }

    public void clearAll() {
        m_data.clear();
    }

    private void checkAndCreateBucket(SpatialHash hash) {
        if (!m_data.containsKey(hash)) {
            m_data.put(hash, new ObjectSet<T>(m_bucketCapacity));
        }
    }

    public int getFullSize() {
        int res = 0;
        for (ObjectSet<T> set : m_data.values()) {
            res += set.size;
        }
        return res;
    }

    public ObjectMap<SpatialHash, ObjectSet<T>> getData() {
        return m_data;
    }
}
