package com.miel3k.cachebenchmark.controller;

import com.miel3k.cachebenchmark.enums.CacheEvictionPolicy;
import com.miel3k.cachebenchmark.model.BenchmarkConfiguration;
import com.miel3k.cachebenchmark.model.BenchmarkResult;
import com.miel3k.cachebenchmark.suites.TestSuite;
import com.miel3k.cachebenchmark.suites.TestSuiteFactory;
import com.miel3k.cachebenchmark.view.BenchmarkView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BenchmarkController {

    private final BenchmarkView view;
    private final BenchmarkConfiguration configuration;

    public BenchmarkController(BenchmarkView view, BenchmarkConfiguration configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    public void run() {
        List<TestSuite> testSuites = new ArrayList<>();
        for (CacheEvictionPolicy cacheEvictionPolicy : configuration.getCacheEvictionPolicies()) {
            TestSuite testSuite = TestSuiteFactory.getTestSuite(
                    cacheEvictionPolicy,
                    configuration.getCacheSize(),
                    configuration.getIterationsCount()
            );
            testSuites.add(testSuite);
        }
        List<BenchmarkResult> resultList = executeTestSuites(testSuites);
        view.displayResults(resultList);
        if (configuration.isWriteEnabled()) writeResults(resultList);
    }

    private List<BenchmarkResult> executeTestSuites(List<TestSuite> testSuites) {
        List<BenchmarkResult> resultList = new ArrayList<>();
        for (TestSuite suite : testSuites) {
            resultList.add(suite.execute());
        }
        return resultList;
    }

    private void writeResults(List<BenchmarkResult> results) {
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdir();
        }
        String cacheSizeText = "cs" + configuration.getCacheSize();
        String iterationsCountText = "i" + configuration.getIterationsCount();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileName = getCacheEvictionPoliciesText()
                + "_" + cacheSizeText
                + "_" + iterationsCountText
                + "_" + timestamp;
        String fileContent = getBenchmarkResultsText(results);
        try (PrintWriter printWriter = new PrintWriter("output/" + fileName + ".txt")) {
            printWriter.println(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getCacheEvictionPoliciesText() {
        List<String> caseTypes = configuration
                .getCacheEvictionPolicies()
                .stream()
                .map(type -> type.name().substring(0, 1))
                .collect(Collectors.toList());
        return String.join("_", caseTypes);
    }

    private String getBenchmarkResultsText(List<BenchmarkResult> results) {
        StringBuilder text = new StringBuilder();
        text.append(BenchmarkResult.HEADER);
        for (BenchmarkResult result : results) {
            String row = result.toString();
            text.append(row);
        }
        return text.toString();
    }
}
