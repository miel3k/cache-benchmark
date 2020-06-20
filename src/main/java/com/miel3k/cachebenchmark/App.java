package com.miel3k.cachebenchmark;

import picocli.CommandLine;

@CommandLine.Command(name = "cb", mixinStandardHelpOptions = true, version = "cb - Cache Benchmark 0.0.1")
public class App implements Runnable {

    public void run() {
        System.out.println("Hello from Cache Benchmark");
    }

    public static void main(final String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
