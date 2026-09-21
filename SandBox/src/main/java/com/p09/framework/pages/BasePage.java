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

    protected final ReportManager report =
            ReportManager.getInstance();


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
            WebElement element,
            String elementName) {

        try {

            waitForClickable(element);

            element.click();

            report.pass(
                    "Clicked : "
                            + elementName);

        } catch (ElementClickInterceptedException e) {

            try {

                jsClick(element);

                report.warning(
                        "Normal click intercepted. "
                                + "JavaScript click used for : "
                                + elementName);

            } catch (Exception jsException) {

                handleFailure(
                        element,
                        elementName,
                        jsException);
            }

        } catch (TimeoutException e) {

            try {

                jsClick(element);

                report.warning(
                        "Normal click timed out. "
                                + "JavaScript click used for : "
                                + elementName);

            } catch (Exception jsException) {

                handleFailure(
                        element,
                        elementName,
                        jsException);
            }

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
                    e);
        }
    }


    // =========================================================
    // TYPE
    // =========================================================

    protected void type(
            WebElement element,
            String value,
            String elementName) {

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

                    return false;
                }
            });

            report.pass(
                    "Entered '"
                            + value
                            + "' into "
                            + elementName);

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
                    e);
        }
    }


    // =========================================================
    // PRESS ENTER
    // =========================================================

    protected void pressEnter(
            WebElement element,
            String elementName) {

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

            report.pass(
                    "Pressed ENTER on : "
                            + elementName);

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
                    e);
        }
    }


    // =========================================================
    // GET TEXT
    // =========================================================

    protected String getText(
            WebElement element,
            String elementName) {

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

            report.info(
                    elementName
                            + " : "
                            + text);

            return text;

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
                    e);

            return null;
        }
    }


    // =========================================================
    // GET ATTRIBUTE
    // =========================================================

    protected String getAttribute(
            WebElement element,
            String attribute,
            String elementName) {

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

            report.info(
                    elementName
                            + " - "
                            + attribute
                            + " : "
                            + value);

            return value;

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
                    e);

            return null;
        }
    }


    // =========================================================
    // DISPLAYED
    // =========================================================

    protected boolean isDisplayed(
            WebElement element,
            String elementName) {

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
            String value,
            String elementName) {

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

            report.pass(
                    "Selected '"
                            + value
                            + "' from "
                            + elementName);

        } catch (Exception e) {

            handleFailure(
                    element,
                    elementName,
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
                    "Hover Element",
                    e);
        }
    }


    // =========================================================
    // ALERT
    // =========================================================

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


    // =========================================================
    // BUSINESS STATUS VALIDATION
    // =========================================================

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

                /*
                 * Expected status has not been reached.
                 *
                 * Perform the business-specific action,
                 * e.g. refresh the page.
                 */
                actionIfNotReady.run();

                return false;

            } catch (
                    NoSuchElementException |
                    StaleElementReferenceException e) {

                return false;
            }
        });
    }


    // =========================================================
    // FAILURE
    // =========================================================

    private void handleFailure(
            WebElement element,
            String elementName,
            Exception exception) {

        String errorMessage =
                exception.getMessage();

        report.fail(
                "Failed to perform operation on "
                        + elementName
                        + ". Reason: "
                        + errorMessage);
    }
}