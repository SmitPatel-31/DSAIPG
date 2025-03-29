package com.phasmidsoftware.dsaipg.util;

import com.phasmidsoftware.dsaipg.sort.*;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class SorterBenchmark<T extends Comparable<T>> {

    private final SortWithHelper<T> sorter;
    private final InstrumentedComparableHelper<T> helper;
    private final T[] ts;
    private final int nRuns;
    private final TimeLogger[] timeLoggers;

    public SorterBenchmark(SortWithHelper<T> sorter, T[] ts, int nRuns, TimeLogger[] timeLoggers) {
        this.sorter = sorter;
        this.ts = ts;
        this.nRuns = nRuns;
        this.timeLoggers = timeLoggers;
        this.helper = new InstrumentedComparableHelper<>(sorter.toString(), ts.length, new Config());
    }

    public void runBenchmark() {
        // Run with instrumentation (to collect comparisons, swaps, etc.)
        System.out.println("Running with instrumentation...");
        helper.resetStatistics();
        double timeWithInstrumentation = runSort(true);

        // Run without instrumentation (only to measure time)
        System.out.println("Running without instrumentation...");
        double timeWithoutInstrumentation = runSort(false);

        // Print out the results
        System.out.println("With Instrumentation - Time: " + timeWithInstrumentation);
        System.out.println("Without Instrumentation - Time: " + timeWithoutInstrumentation);

        // Capture and print instrumentation stats
        System.out.println("Comparisons: " + helper.getComparisons());
        System.out.println("Swaps: " + helper.getSwaps());
        System.out.println("Hits: " + helper.getHits());
    }

    private double runSort(boolean withInstrumentation) {
        if (withInstrumentation) {
            return Benchmark_Timer.runFromSupplier(() -> generateRandomArray(ts), nRuns);
        } else {
            sorter.sort(ts); // This assumes sorter has a `sort` method
            return sorter.getTime(); // Assuming the sort object provides time data
        }
    }

    private T[] generateRandomArray(T[] lookupArray) {
        Random rand = new Random();
        return sorter.getHelper().random(lookupArray, rand);
    }
}
