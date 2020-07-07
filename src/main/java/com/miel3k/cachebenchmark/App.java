package com.miel3k.cachebenchmark;

import com.miel3k.cachebenchmark.controller.BenchmarkController;
import com.miel3k.cachebenchmark.enums.CacheEvictionPolicy;
import com.miel3k.cachebenchmark.model.BenchmarkConfiguration;
import com.miel3k.cachebenchmark.view.BenchmarkView;
import com.miel3k.cachebenchmark.view.ConsoleView;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name = "cb", mixinStandardHelpOptions = true, version = "cb - Cache Benchmark 0.0.1")
public class App implements Runnable {

    @CommandLine.Option(names = {"-fifo", "--fifo"}, description = "First in first out")
    boolean isFifoBenchmarkEnabled;

    @CommandLine.Option(names = {"-lru", "--lru"}, description = "Last recently used")
    boolean isLruBenchmarkEnabled;

    @CommandLine.Option(names = {"-lfru", "--lfru"}, description = "Least frequent recently used")
    boolean isLfruBenchmarkEnabled;

    @CommandLine.Option(names = {"-rr", "--rr"}, description = "Random replacement")
    boolean isRrBenchmarkEnabled;

    @CommandLine.Option(names = {"-cs", "--cacheSize"}, description = "cache size: (int)", defaultValue = "10")
    int cacheSize;

    @CommandLine.Option(names = {"-i", "--iterations"}, description = "iterations: (int)", defaultValue = "5")
    int iterations;

    @CommandLine.Option(names = {"-w", "--write"}, description = "write: write results to file")
    boolean isWriteEnabled;

    @CommandLine.Option(names = {"-wu", "--warmUp"}, description = "warm up: JVM warm-up")
    boolean isWarmUpEnabled;

    public void run() {
        System.out.println("Hello from Cache Benchmark");
        BenchmarkConfiguration benchmarkConfiguration = new BenchmarkConfiguration(
                parseCacheEvictionPolicies(),
                parseCacheSize(),
                parseIterations(),
                isWriteEnabled,
                isWarmUpEnabled
        );
        BenchmarkView benchmarkView = new ConsoleView();
        BenchmarkController benchmarkController = new BenchmarkController(benchmarkView, benchmarkConfiguration);
        benchmarkController.run();
    }

    private List<CacheEvictionPolicy> parseCacheEvictionPolicies() {
        List<CacheEvictionPolicy> cacheEvictionPolicyList = new ArrayList<>();
        if (isFifoBenchmarkEnabled) cacheEvictionPolicyList.add(CacheEvictionPolicy.FIFO);
        if (isLruBenchmarkEnabled) cacheEvictionPolicyList.add(CacheEvictionPolicy.LRU);
        if (isLfruBenchmarkEnabled) cacheEvictionPolicyList.add(CacheEvictionPolicy.LFRU);
        if (isRrBenchmarkEnabled) cacheEvictionPolicyList.add(CacheEvictionPolicy.RR);
        if (cacheEvictionPolicyList.isEmpty()) {
            cacheEvictionPolicyList.add(CacheEvictionPolicy.FIFO);
            cacheEvictionPolicyList.add(CacheEvictionPolicy.LRU);
            cacheEvictionPolicyList.add(CacheEvictionPolicy.LFRU);
            cacheEvictionPolicyList.add(CacheEvictionPolicy.RR);
        }
        return cacheEvictionPolicyList;
    }

    private int parseCacheSize() {
        if (cacheSize >= 0) {
            return cacheSize;
        } else {
            return 1;
        }
    }

    private int parseIterations() {
        if (iterations >= 0) {
            return iterations;
        } else {
            return 1;
        }
    }

    public static void main(final String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
