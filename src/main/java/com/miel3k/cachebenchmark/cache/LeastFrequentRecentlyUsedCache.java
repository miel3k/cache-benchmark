package com.miel3k.cachebenchmark.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LeastFrequentRecentlyUsedCache<K, V> extends Cache<K, V> {

    LinkedList<K> privilegedEvictionList;
    HashMap<K, Integer> occurrencesMap;
    HashMap<K, Integer> privilegedOccurrencesMap;

    public LeastFrequentRecentlyUsedCache(int cacheSize) {
        super(cacheSize);
        privilegedEvictionList = new LinkedList<>();
        occurrencesMap = new HashMap<>(cacheSize / 2);
        privilegedOccurrencesMap = new HashMap<>(cacheSize / 2);
    }

    @Override
    public void evict() {
        evictionCount++;

        K keyMin = occurrencesMap.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        occurrencesMap.remove(keyMin);
        evictionList.remove(keyMin);

        K keyLastPrivileged = privilegedEvictionList.remove(privilegedEvictionList.size() - 1);
        evictionList.addFirst(keyLastPrivileged);

        cacheMap.remove(keyMin);
    }

    @Override
    public V getValue(K key) {
        if (evictionList.contains(key)) {
            occurrencesMap.replace(key, occurrencesMap.get(key) + 1);
            evictionList.remove(key);
            if (privilegedEvictionList.size() == cacheSize / 2) {
                K keyLastPrivileged = privilegedEvictionList.remove(privilegedEvictionList.size() - 1);
                evictionList.addFirst(keyLastPrivileged);
            }
            privilegedEvictionList.addFirst(key);
            return cacheMap.get(key);
        } else if (privilegedEvictionList.contains(key)) {
            occurrencesMap.replace(key, occurrencesMap.get(key) + 1);
            privilegedEvictionList.remove(key);
            privilegedEvictionList.addFirst(key);
            return cacheMap.get(key);
        } else {
            missCount++;
            return null;
        }
    }

    @Override
    public Cache<K, V> putValue(K key, V value) {
        if (isFull()) {
            evict();
        }
        if (privilegedEvictionList.size() == cacheSize / 2) {
            K keyLastPrivileged = privilegedEvictionList.remove(privilegedEvictionList.size() - 1);
            evictionList.addFirst(keyLastPrivileged);
        }
        if (occurrencesMap.containsKey(key)) {
            occurrencesMap.replace(key, occurrencesMap.get(key) + 1);
        } else {
            occurrencesMap.put(key, 1);
        }
        privilegedEvictionList.addFirst(key);
        cacheMap.put(key, value);
        return this;
    }
}
