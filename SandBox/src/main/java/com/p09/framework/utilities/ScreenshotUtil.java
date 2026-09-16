package com.p09.framework.utilities;

import com.p09.framework.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtil {

    private static final String SCREENSHOT_FOLDER =
            "target/screenshots/";

    private ScreenshotUtil() {
    }

    public static String capture(String screenshotName) {

        File folder = new File(SCREENSHOT_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());

        String filePath =
                SCREENSHOT_FOLDER +
                        screenshotName +
                        "_" +
                        timestamp +
                        ".png";

        try {

            File source =
                    ((TakesScreenshot) DriverManager.getDriver())
                            .getScreenshotAs(OutputType.FILE);

            File destination = new File(filePath);

            FileUtils.copyFile(source, destination);

            return destination.getAbsolutePath();

        }

        catch (IOException e) {

            throw new RuntimeException(
                    "Unable to capture screenshot.",
                    e);

        }

    }

}