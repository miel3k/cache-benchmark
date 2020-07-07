package com.miel3k.cachebenchmark.suites;

import com.miel3k.cachebenchmark.cache.Cache;
import com.miel3k.cachebenchmark.data.BookDataSource;
import com.miel3k.cachebenchmark.model.BenchmarkResult;
import com.miel3k.cachebenchmark.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CacheTestSuite implements TestSuite {

    private static final Random random = new Random();
    private final BookDataSource repository;
    private final String cachePolicy;
    private final Cache<Integer, Book> cache;
    private final int cacheSize;
    private final int iterationsCount;

    public CacheTestSuite(BookDataSource repository, String cachePolicy, Cache<Integer, Book> cache, int cacheSize, int numberOfIterations) {
        this.repository = repository;
        this.cachePolicy = cachePolicy;
        this.cache = cache;
        this.cacheSize = cacheSize;
        this.iterationsCount = numberOfIterations;
    }

    @Override
    public BenchmarkResult execute() {
        List<Long> cacheTimes = new ArrayList<>();
        List<Long> repositoryTimes = new ArrayList<>();
        long totalStartTime = System.nanoTime();
        for (int i = 0; i < iterationsCount; i++) {
            long cacheStartTime = System.nanoTime();
            Book book = cache.getValue(random.nextInt(cacheSize));
            long cacheEndTime = System.nanoTime();
            cacheTimes.add(cacheEndTime - cacheStartTime);
            if (book == null) {
                long repositoryStartTime = System.nanoTime();
                Book localBook = repository.getBook(i);
                long repositoryEndTime = System.nanoTime();
                repositoryTimes.add(repositoryEndTime - repositoryStartTime);
                cache.putValue(localBook.getId(), localBook);
            }
        }
        long totalEndTime = System.nanoTime();
        return createResult(totalStartTime, totalEndTime, cacheTimes, repositoryTimes);
    }

    private BenchmarkResult createResult(long totalStartTime, long totalEndTime, List<Long> cacheTimes, List<Long> repositoryTimes) {
        int hitCount = iterationsCount - cache.getMissCount();
        return new BenchmarkResult(
                cachePolicy,
                iterationsCount,
                hitCount,
                hitCount * 100 / iterationsCount,
                cache.getEvictionCount(),
                (totalEndTime - totalStartTime) / iterationsCount,
                (long) cacheTimes.stream().mapToLong(e -> e).average().orElse(0.0),
                (long) repositoryTimes.stream().mapToLong(e -> e).average().orElse(0.0)
        );
    }
}
