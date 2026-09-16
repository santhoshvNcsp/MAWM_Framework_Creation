package com.p09.framework.reporting;

import com.aventstack.extentreports.ExtentTest;

public final class ReportNode {

    private static final ThreadLocal<ExtentTest> node =
            new ThreadLocal<>();

}
