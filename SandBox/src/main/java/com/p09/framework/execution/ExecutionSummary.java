package com.p09.framework.execution;

import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionSummary {

    private int totalTestCases;

    private final AtomicInteger passedTestCases =
            new AtomicInteger(0);

    private final AtomicInteger failedTestCases =
            new AtomicInteger(0);

    public void setTotalTestCases(int totalTestCases) {

        this.totalTestCases = totalTestCases;

    }

    public void incrementPassed() {

        passedTestCases.incrementAndGet();

    }

    public void incrementFailed() {

        failedTestCases.incrementAndGet();

    }

    public int getTotalTestCases() {

        return totalTestCases;

    }

    public int getPassedTestCases() {

        return passedTestCases.get();

    }

    public int getFailedTestCases() {

        return failedTestCases.get();

    }

    public int getNotRunTestCases() {

        return totalTestCases
                - getPassedTestCases()
                - getFailedTestCases();

    }

    /**
     * Displays detailed execution progress
     * for sequential execution.
     */
    public synchronized void printExecutionProgress() {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("Execution progress");

        System.out.println(
                "Total no of test cases : "
                        + totalTestCases);

        System.out.println(
                "Total cases passed     : "
                        + getPassedTestCases());

        System.out.println(
                "Total cases failed     : "
                        + getFailedTestCases());

        System.out.println(
                "Total cases not run    : "
                        + getNotRunTestCases());

        System.out.println("----------------------------------------");
        System.out.println();

    }

    /**
     * Displays compact execution progress
     * for parallel execution.
     */
    public synchronized void printParallelProgress() {

        int completed =
                getPassedTestCases()
                        + getFailedTestCases();

        int remaining =
                totalTestCases - completed;

        System.out.println(
                "Parallel execution: "
                        + completed
                        + "/"
                        + totalTestCases
                        + " completed | Passed: "
                        + getPassedTestCases()
                        + " | Failed: "
                        + getFailedTestCases()
                        + " | Remaining: "
                        + remaining);

    }

    /**
     * Displays the final execution summary.
     */
    public synchronized void printSummary() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("Automation script completed");
        System.out.println("Report generated successfully");
        System.out.println();

        System.out.println(
                "Total no of test cases : "
                        + totalTestCases);

        System.out.println(
                "Total cases passed     : "
                        + getPassedTestCases());

        System.out.println(
                "Total cases failed     : "
                        + getFailedTestCases());

        System.out.println(
                "Total cases not run    : "
                        + getNotRunTestCases());

        System.out.println("========================================");
        System.out.println();

    }

}