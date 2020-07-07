package com.miel3k.cachebenchmark.view;

import com.miel3k.cachebenchmark.model.BenchmarkResult;

import java.util.List;

public class ConsoleView implements BenchmarkView {

    public ConsoleView() {
    }

    @Override
    public void displayResults(List<BenchmarkResult> results) {
        System.out.println(BenchmarkResult.HEADER);
        for (BenchmarkResult result : results) {
            System.out.println(result.toString());
        }
    }
}
