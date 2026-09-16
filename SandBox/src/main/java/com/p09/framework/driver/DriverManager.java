package com.p09.framework.driver;

import com.p09.framework.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    private static final ThreadLocal<WebDriverWait> wait =
            new ThreadLocal<>();

    private static final ThreadLocal<FluentWait<WebDriver>> fluentWait =
            new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initializeDriver() {

        WebDriver webDriver =
                DriverFactory.createDriver(
                        BrowserContext.getBrowser());

        driver.set(webDriver);

        /*
         * Normal UI wait
         */
        wait.set(new WebDriverWait(
                webDriver,
                Duration.ofSeconds(
                        ConfigManager.getInt(
                                "explicit.wait")
                )));

        /*
         * Fluent wait for business/status validation
         */
        fluentWait.set(
                new FluentWait<>(webDriver)
                        .withTimeout(
                                Duration.ofSeconds(
                                        ConfigManager.getInt(
                                                "fluent.wait")
                                ))
                        .pollingEvery(
                                Duration.ofSeconds(
                                        ConfigManager.getInt(
                                                "fluent.polling")
                                ))
        );
    }

    public static WebDriver getDriver() {

        WebDriver webDriver =
                driver.get();

        if (webDriver == null) {

            throw new IllegalStateException(
                    "WebDriver has not been initialized.");
        }

        return webDriver;
    }

    public static WebDriverWait getWait() {

        return wait.get();
    }

    public static FluentWait<WebDriver> getFluentWait() {

        return fluentWait.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();

            driver.remove();
        }

        wait.remove();

        fluentWait.remove();
    }
}