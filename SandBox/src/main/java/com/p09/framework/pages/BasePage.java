package com.p09.framework.pages;

import com.p09.framework.driver.DriverManager;
import com.p09.framework.reporting.ReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected ReportManager report;

    /*
     * Normal UI wait
     */
    protected final WebDriverWait wait;

    /*
     * Fluent wait
     *
     * Used for business/application status validations.
     */
    protected final FluentWait<WebDriver> fluentWait;



    protected BasePage() {

        this.driver =
                DriverManager.getDriver();

        /*
         * Normal Selenium wait
         */
        this.wait =
                DriverManager.getWait();


        /*
         * Business/status FluentWait
         */
        this.fluentWait =
                DriverManager.getFluentWait();

        this.report = ReportManager.getInstance();

        PageFactory.initElements(
                driver,
                this);
    }


    // =========================================================
    // PAGE LOAD
    // =========================================================

    protected void waitForPageLoad() {

        wait.until(driver -> {

            try {

                return "complete".equals(
                        ((JavascriptExecutor) driver)
                                .executeScript(
                                        "return document.readyState"));

            } catch (Exception e) {

                return false;
            }
        });
    }


    // =========================================================
    // CLICK
    // =========================================================

    protected void click(
            WebElement element) {

        try {

            waitForClickable(element);
            element.click();

        } catch (ElementClickInterceptedException e) {

            System.out.println(
                    "[CLICK] Normal click failed"
                            + " | Attempting JS click");

            try {

                jsClick(element);

                System.out.println(
                        "[CLICK] JS click succeeded");

            } catch (Exception jsException) {

                System.err.println(
                        "[CLICK] JS click also failed");

                handleFailure(
                        element,
                        jsException);
            }

        } catch (TimeoutException e) {

            System.out.println(
                    "[CLICK] Normal click timed out"
                            + " | Attempting JS click");

            try {

                jsClick(element);

                System.out.println(
                        "[CLICK] JS click succeeded");

            } catch (Exception jsException) {

                System.err.println(
                        "[CLICK] JS click also failed");

                handleFailure(
                        element,
                        jsException);
            }

        } catch (Exception e) {

            handleFailure(
                    element,
                    e);
        }
    }


    // =========================================================
    // TYPE
    // =========================================================

    protected void type(
            WebElement element,
            String value) {

        try {

            waitForEditable(element);

            wait.until(driver -> {

                try {

                    if (!element.isDisplayed()
                            || !element.isEnabled()) {
                        return false;
                    }

                    /*
                     * Clear the existing value.
                     */
                    element.clear();

                    /*
                     * Verify that the value is actually cleared.
                     */
                    String currentValue =
                            element.getAttribute("value");

                    if (currentValue != null
                            && !currentValue.isEmpty()) {

                        return false;
                    }

                    /*
                     * Value is confirmed empty.
                     * Now send the new value.
                     */
                    element.sendKeys(value);

                    /*
                     * Verify that the new value was entered.
                     */
                    String enteredValue =
                            element.getAttribute("value");

                    return value.equals(enteredValue);

                } catch (
                        StaleElementReferenceException |
                        ElementNotInteractableException e) {

                    System.out.println(
                            "[TYPE] Retrying input operation");

                    return false;
                }
            });


        } catch (Exception e) {

            handleFailure(
                    element,
                    e);
        }
    }


    // =========================================================
    // PRESS ENTER
    // =========================================================

    protected void pressEnter(
            WebElement element) {

        try {

            waitForEditable(element);

            wait.until(driver -> {

                try {

                    element.sendKeys(
                            Keys.ENTER);

                    return true;

                } catch (
                        ElementNotInteractableException |
                        StaleElementReferenceException e) {

                    return false;
                }
            });


        } catch (Exception e) {

            handleFailure(
                    element,
                    e);
        }
    }


    // =========================================================
    // GET TEXT
    // =========================================================

    protected String getText(
            WebElement element) {

        try {

            waitForVisible(element);

            String text =
                    wait.until(driver -> {

                        try {

                            return element.getText();

                        } catch (
                                StaleElementReferenceException e) {

                            return null;
                        }
                    });


            return text;

        } catch (Exception e) {

            handleFailure(
                    element,
                    e);

            return null;
        }
    }


    // =========================================================
    // GET ATTRIBUTE
    // =========================================================

    protected String getAttribute(
            WebElement element,
            String attribute) {

        try {

            waitForVisible(element);

            String value =
                    wait.until(driver -> {

                        try {

                            return element.getAttribute(
                                    attribute);

                        } catch (
                                StaleElementReferenceException e) {

                            return null;
                        }
                    });


            return value;

        } catch (Exception e) {

            handleFailure(
                    element,
                    e);

            return null;
        }
    }


    // =========================================================
    // DISPLAYED
    // =========================================================

    protected boolean isDisplayed(
            WebElement element) {

        try {

            waitForVisible(element);

            return true;

        } catch (
                TimeoutException |
                NoSuchElementException |
                StaleElementReferenceException e) {

            return false;
        }
    }


    // =========================================================
    // SELECT
    // =========================================================

    protected void selectByVisibleText(
            WebElement element,
            String value) {

        try {

            waitForEditable(element);

            wait.until(driver -> {

                try {

                    new Select(element)
                            .selectByVisibleText(value);

                    return true;

                } catch (
                        ElementNotInteractableException |
                        StaleElementReferenceException |
                        NoSuchElementException e) {

                    return false;
                }
            });


        } catch (Exception e) {

            handleFailure(
                    element,
                    e);
        }
    }


    // =========================================================
    // CLICKABLE WAIT
    // =========================================================

    private void waitForClickable(
            WebElement element) {

        /*
         * 1. Element must exist/be resolvable
         * 2. Element must be visible
         * 3. Element must be enabled
         * 4. Selenium must consider it clickable
         */

        wait.until(driver -> {

            try {

                return element.isDisplayed();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });

        wait.until(
                ExpectedConditions
                        .visibilityOf(element));

        wait.until(driver -> {

            try {

                return element.isEnabled();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(element));
    }


    // =========================================================
    // EDITABLE WAIT
    // =========================================================

    private void waitForEditable(
            WebElement element) {

        /*
         * 1. Present/resolvable
         * 2. Visible
         * 3. Enabled
         */

        wait.until(driver -> {

            try {

                return element.isDisplayed();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });

        wait.until(
                ExpectedConditions
                        .visibilityOf(element));

        wait.until(driver -> {

            try {

                return element.isEnabled();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });
    }


    // =========================================================
    // VISIBLE WAIT
    // =========================================================

    private void waitForVisible(
            WebElement element) {

        wait.until(driver -> {

            try {

                return element.isDisplayed();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });

        wait.until(
                ExpectedConditions
                        .visibilityOf(element));
    }


    // =========================================================
    // JAVASCRIPT CLICK
    // =========================================================

    protected void jsClick(
            WebElement element) {

        waitForVisible(element);

        wait.until(driver -> {

            try {

                return element.isEnabled();

            } catch (
                    StaleElementReferenceException |
                    NoSuchElementException e) {

                return false;
            }
        });

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element);
    }


    // =========================================================
    // SCROLL
    // =========================================================

    protected void scrollIntoView(
            WebElement element) {

        waitForVisible(element);

        wait.until(driver -> {

            try {

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView"
                                        + "(true);",
                                element);

                return true;

            } catch (
                    StaleElementReferenceException e) {

                return false;
            }
        });
    }


    // =========================================================
    // HOVER
    // =========================================================

    protected void hover(
            WebElement element) {

        try {

            waitForVisible(element);

            wait.until(driver -> {

                try {

                    return element.isEnabled();

                } catch (
                        StaleElementReferenceException e) {

                    return false;
                }
            });

            new Actions(driver)
                    .moveToElement(element)
                    .perform();

        } catch (Exception e) {

            handleFailure(
                    element,
                    e);
        }
    }

    protected void acceptAlert() {

        wait.until(
                ExpectedConditions
                        .alertIsPresent());

        driver.switchTo()
                .alert()
                .accept();
    }


    protected void dismissAlert() {

        wait.until(
                ExpectedConditions
                        .alertIsPresent());

        driver.switchTo()
                .alert()
                .dismiss();
    }

    protected void waitForStatus(
            By statusLocator,
            Runnable actionIfNotReady,
            String expectedStatus) {

        fluentWait.until(driver -> {

            try {

                WebElement statusElement =
                        driver.findElement(
                                statusLocator);

                String actualStatus =
                        statusElement
                                .getText()
                                .trim();

                if (expectedStatus.equalsIgnoreCase(
                        actualStatus)) {

                    return true;
                }
                actionIfNotReady.run();

                return false;

            } catch (
                    NoSuchElementException |
                    StaleElementReferenceException e) {

                return false;
            }
        });
    }


    private void handleFailure(
            WebElement element,
            Exception exception) {

        String errorMessage =
                exception.getMessage();

        System.err.println(
                "[UI FAILURE] "
                        + exception.getClass().getSimpleName()
                        + " | "
                        + errorMessage);


    }
}
