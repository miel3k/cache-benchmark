package com.miel3k.cachebenchmark.cache;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Cache<K, V> {

    int cacheSize;
    HashMap<K, V> cacheMap;
    LinkedList<K> evictionList;
    int evictionCount = 0;
    int missCount = 0;

    protected Cache() {
    }

    public abstract void evict();

    public abstract V getValue(K key);

    public abstract Cache<K, V> putValue(K key, V value);

    public Cache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.cacheMap = new HashMap<>(cacheSize);
        this.evictionList = new LinkedList<>();
    }

    boolean isFull() {
        return cacheMap.size() == cacheSize;
    }

    void invalidate(K key) {
        cacheMap.remove(key);
        evictionList.remove(key);
    }

    void clean() {
        cacheMap.clear();
        evictionList.clear();
    }

    public HashMap<K, V> getCacheMap() {
        return cacheMap;
    }

    public int getEvictionCount() {
        return evictionCount;
    }

    public int getMissCount() {
        return missCount;
    }
}
