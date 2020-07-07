package com.miel3k.cachebenchmark.model;

public class BenchmarkResult {

    public static String HEADER = String.format(
            "%16s%16s%16s%16s%16s%16s%24s%24s%24s",
            "Cache policy",
            "Cache size",
            "Iterations",
            "Hit count",
            "Hit ratio (%)",
            "Evictions",
            "Read (avg) (ns)",
            "Cache (avg) read (ns)",
            "Repo (avg) read (ns)"
    );

    private final String cachePolicy;
    private final int cacheSize;
    private final int iterations;
    private final int hitCount;
    private final int hitRatio;
    private final int evictionCount;
    private long avgReadTime;
    private long avgCacheReadTime;
    private long avgRepositoryReadTime;

    public BenchmarkResult(
            String cachePolicy,
            int cacheSize,
            int iterations,
            int hitCount,
            int hitRatio,
            int evictionCount,
            long avgReadTime,
            long avgCacheReadTime,
            long avgRepositoryReadTime
    ) {
        this.cachePolicy = cachePolicy;
        this.cacheSize = cacheSize;
        this.iterations = iterations;
        this.hitCount = hitCount;
        this.hitRatio = hitRatio;
        this.evictionCount = evictionCount;
        this.avgReadTime = avgReadTime;
        this.avgCacheReadTime = avgCacheReadTime;
        this.avgRepositoryReadTime = avgRepositoryReadTime;
    }

    public String getCachePolicy() {
        return cachePolicy;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getIterations() {
        return iterations;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getHitRatio() {
        return hitRatio;
    }

    public int getEvictionCount() {
        return evictionCount;
    }

    public long getAvgReadTime() {
        return avgReadTime;
    }

    public long getAvgCacheReadTime() {
        return avgCacheReadTime;
    }

    public long getAvgRepositoryReadTime() {
        return avgRepositoryReadTime;
    }

    @Override
    public String toString() {
        return String.format(
                "%16s%16d%16d%16d%16d%16d%24d%24d%24d",
                cachePolicy,
                cacheSize,
                iterations,
                hitCount,
                hitRatio,
                evictionCount,
                avgReadTime,
                avgCacheReadTime,
                avgRepositoryReadTime
        );
    }
}
