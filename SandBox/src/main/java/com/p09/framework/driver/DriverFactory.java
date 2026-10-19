package com.p09.framework.driver;

import com.p09.framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":

//                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                if (ConfigManager.getBoolean("headless")) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                chromeOptions.addArguments("--start-maximized");

                return new ChromeDriver(chromeOptions);

            case "edge": {
                EdgeOptions edgeOptions = new EdgeOptions();

                if (ConfigManager.getBoolean("headless")) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                } else {
                    edgeOptions.addArguments("--start-maximized");
                }

                System.setProperty(
                        "webdriver.edge.driver",
                        "D:\\WebDrivers\\Edge\\msedgedriver.exe"
                );

                return new EdgeDriver(edgeOptions);
            }

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (ConfigManager.getBoolean("headless")) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--window-size=1920,1080");
                }

                return new FirefoxDriver(firefoxOptions);

            default:
                throw new IllegalArgumentException(
                        "Unsupported Browser : " + browser);
        }
    }
}