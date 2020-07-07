package com.miel3k.cachebenchmark.view;

import com.miel3k.cachebenchmark.model.BenchmarkResult;

import java.util.List;

public interface BenchmarkView {
    void displayResults(List<BenchmarkResult> results);
}
