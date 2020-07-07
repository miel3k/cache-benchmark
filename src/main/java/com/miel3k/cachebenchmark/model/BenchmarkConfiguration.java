package com.miel3k.cachebenchmark.model;

import com.miel3k.cachebenchmark.enums.CacheEvictionPolicy;

import java.util.List;

public class BenchmarkConfiguration {

    private final List<CacheEvictionPolicy> cacheEvictionPolicies;
    private final int cacheSize;
    private final int iterationsCount;
    private final boolean isWriteEnabled;
    private final boolean isWarmUpEnabled;

    public BenchmarkConfiguration(
            List<CacheEvictionPolicy> cacheEvictionPolicies,
            int cacheSize,
            int iterationsCount,
            boolean isWriteEnabled,
            boolean isWarmUpEnabled
    ) {
        this.cacheEvictionPolicies = cacheEvictionPolicies;
        this.cacheSize = cacheSize;
        this.iterationsCount = iterationsCount;
        this.isWriteEnabled = isWriteEnabled;
        this.isWarmUpEnabled = isWarmUpEnabled;
    }

    public List<CacheEvictionPolicy> getCacheEvictionPolicies() {
        return cacheEvictionPolicies;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getIterationsCount() {
        return iterationsCount;
    }

    public boolean isWriteEnabled() {
        return isWriteEnabled;
    }

    public boolean isWarmUpEnabled() {
        return isWarmUpEnabled;
    }
}
