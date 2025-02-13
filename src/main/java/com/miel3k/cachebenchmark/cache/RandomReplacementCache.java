package com.miel3k.cachebenchmark.cache;

import java.util.Random;

public class RandomReplacementCache<V, K> extends Cache<K, V> {

    public RandomReplacementCache(int cacheSize) {
        super(cacheSize);
    }

    @Override
    public void evict() {
        evictionCount++;
        int index = new Random().nextInt(evictionList.size());
        K key = evictionList.remove(index);
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
