package com.miel3k.cachebenchmark.suites;

import com.miel3k.cachebenchmark.cache.FirstInFirstOutCache;
import com.miel3k.cachebenchmark.cache.LastRecentlyUsedCache;
import com.miel3k.cachebenchmark.cache.LeastFrequentRecentlyUsedCache;
import com.miel3k.cachebenchmark.cache.RandomReplacementCache;
import com.miel3k.cachebenchmark.data.BookRepository;
import com.miel3k.cachebenchmark.enums.CacheEvictionPolicy;

public class TestSuiteFactory {

    public static TestSuite getTestSuite(CacheEvictionPolicy policy, int cacheSize, int numberOfIterations) {
        BookRepository bookRepository = new BookRepository();
        if (CacheEvictionPolicy.FIFO.name().equalsIgnoreCase(policy.name())) {
            return new CacheTestSuite(bookRepository, policy.name(), new FirstInFirstOutCache<>(cacheSize), cacheSize, numberOfIterations);
        } else if (CacheEvictionPolicy.LRU.name().equalsIgnoreCase(policy.name())) {
            return new CacheTestSuite(bookRepository, policy.name(), new LastRecentlyUsedCache<>(cacheSize), cacheSize, numberOfIterations);
        } else if (CacheEvictionPolicy.LFRU.name().equalsIgnoreCase(policy.name())) {
            return new CacheTestSuite(bookRepository, policy.name(), new LeastFrequentRecentlyUsedCache<>(cacheSize), cacheSize, numberOfIterations);
        } else if (CacheEvictionPolicy.RR.name().equalsIgnoreCase(policy.name())) {
            return new CacheTestSuite(bookRepository, policy.name(), new RandomReplacementCache<>(cacheSize), cacheSize, numberOfIterations);
        }
        return null;
    }
}
