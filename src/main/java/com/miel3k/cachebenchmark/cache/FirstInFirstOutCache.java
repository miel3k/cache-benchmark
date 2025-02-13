package com.miel3k.cachebenchmark.cache;

public class FirstInFirstOutCache<V, K> extends Cache<K, V> {

    public FirstInFirstOutCache(int cacheSize) {
        super(cacheSize);
    }

    @Override
    public void evict() {
        evictionCount++;
        K key = evictionList.removeFirst();
        cacheMap.remove(key);
    }

    @Override
    public V getValue(K key) {
        V value = cacheMap.get(key);
        if (value == null) {
            missCount++;
        }
        return value;
    }

    @Override
    public Cache<K, V> putValue(K key, V value) {
        if (isFull()) {
            evict();
        }
        evictionList.add(key);
        cacheMap.put(key, value);
        return this;
    }
}
