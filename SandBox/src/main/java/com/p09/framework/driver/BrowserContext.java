package com.p09.framework.driver;

public final class BrowserContext {

    private static final ThreadLocal<String> browser =
            new ThreadLocal<>();

    private BrowserContext() {
    }

    public static void setBrowser(String browserName) {
        browser.set(browserName);
    }

    public static String getBrowser() {
        return browser.get();
    }

    public static void clear() {
        browser.remove();
    }

}