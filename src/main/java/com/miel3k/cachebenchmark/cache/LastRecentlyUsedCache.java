package com.miel3k.cachebenchmark.cache;

public class LastRecentlyUsedCache<K, V> extends Cache<K, V> {

    public LastRecentlyUsedCache(int cacheSize) {
        super(cacheSize);
    }

    @Override
    public void evict() {
        K key = evictionList.removeLast();
        cacheMap.remove(key);
    }

    @Override
    public V getValue(K key) {
        boolean isRemoved = this.evictionList.remove(key);
        if (isRemoved) {
            evictionList.addFirst(key);
            return cacheMap.get(key);
        } else {
            return null;
        }
    }

    @Override
    public Cache<K, V> putValue(K key, V value) {
        if (isFull()) {
            evict();
        }
        evictionList.addFirst(key);
        cacheMap.put(key, value);
        return this;
    }
}
