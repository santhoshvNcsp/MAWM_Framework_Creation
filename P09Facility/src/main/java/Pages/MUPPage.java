package Pages;
import com.p09.framework.context.ScenarioContext;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import context.ASNData;
import context.ItemData;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import context.ILPNData;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MUPPage extends BasePage {
    public MUPPage() {
        super();
    }

    @FindBy(id = "ASN")
    public WebElement clickAsnSFromMenu;
    @FindBy(id = "wmMobile")
    public WebElement clickWmMobileFromMenu;
    @FindBy(xpath = "//ion-searchbar[@data-component-id='search']/div/input")
    public WebElement searchBarInWmMobile;
    @FindBy(xpath = "//ion-item[@data-component-id='190receiving']")
    public WebElement click190Receiving;
    @FindBy(xpath = "//input[@data-component-id='acceptasn_barcodetextfield_asn']")
    public WebElement passAsnIdReceive;
    @FindBy(xpath = "//input[@data-component-id='acceptlpn_barcodetextfield_lpn']")
    public WebElement passLpnIdReceive;
    @FindBy(xpath = "//ion-col[@data-component-id='acceptitem_barcodetextfield_lpn']")
    public WebElement storeLpnIdFromWm;
    @FindBy(xpath = "//input[@data-component-id='acceptitem_barcodetextfield_item']")
    public WebElement passItemReceive;
    @FindBy(xpath = "//input[contains(@data-component-id,'acceptquantity_naturalquantityfield_uni')]")
    public WebElement passQtyReceive;
    @FindBy(xpath = "//button[@data-component-id='action_endlpn_button']")
    public WebElement endLPNReceive;
    @FindBy(linkText = "LPN (Inventory)")
    public WebElement lpnInventoryAsn;
    @FindBy(xpath = "//ion-label[@data-component-id='190userdirectedputaway']")
    public WebElement clickUD;
    @FindBy(xpath = "//ion-label[@data-component-id='palletizeilpn']")
    public WebElement clickPalletizeILPN;
    @FindBy(xpath = "//input[@placeholder='Scan Container']")
    public WebElement scanContainer;
    @FindBy(xpath = "//input[@placeholder='Scan Item']")
    public WebElement scanItemUD;
    @FindBy(xpath = "//input[@data-component-id='acceptitemquantity_naturalquantityfield_units']")
    public WebElement scanQtyUD;
    @FindBy(xpath = "//input[@placeholder='Scan Location']")
    public WebElement scanLocUD;
    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;
    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;
    @FindBy(xpath = "//ion-input[@data-component-id='AsnId']/label/div/input")
    public WebElement filterAsnById;
    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;
    @FindBy(xpath = "//ion-label[@data-component-id='190receivebypallet']")
    public WebElement clickReceiveByPallet;
    @FindBy(xpath = "//input[@placeholder='Scan Pallet']")
    public WebElement scanPallet;
    @FindBy(xpath = "//span[.='Generate Pallet']")
    public WebElement generatePallet;
    @FindBy(xpath = "//ion-col[@data-component-id='acceptilpn_barcodetextfield_pallet']")
    public WebElement getPalletFromPalletizeTransaction;

    @FindBy(xpath = "//ion-col[@data-component-id='acceptlpn_barcodetextfield_pallet']")
    public WebElement getPallet;
    @FindBy(xpath = "//button[@data-component-id='action_endpallet_button']")
    public WebElement endPallet;
    @FindBy(xpath = "//ion-label[@data-component-id='190systemdirectedputaway']")
    public WebElement click190SD;
    @FindBy(xpath = "//button[@data-component-id='popover_confirm']")
    public WebElement confirmPrinter;
    @FindBy(xpath = "//input[@placeholder='Scan iLPN']")
    public WebElement scanILPN;

    @FindBy(xpath = "//span[@data-component-id='VendorId']")
    public WebElement vendorId;

    // =========================================================
// WM MOBILE - TASK PROCESSING
// =========================================================

    private final By enterTask =
            By.xpath("//ion-label[@data-component-id='entertask']");

    private final By taskInput =
            By.xpath("//input[@placeholder='Enter Task']");

    private final By tapToConfirm =
            By.xpath("//span[text()='Tap To Confirm']");

    private final By acceptLocation =
            By.xpath("//ion-col[@data-component-id='acceptlocation_barcodetextfield_location']");

    private final By scanLocation =
            By.xpath("//input[@placeholder='Scan Location']");

    private final By quantityInput =
            By.xpath("//input[contains(@data-component-id,'acceptquantity_naturalquantityfield_unit')]");

    private final By unitText =
            By.xpath("//span[contains(text(),' U')]");

    // E2E full-LPN picking: read the required LPN count from the Need field,
    // then enter that number into the LPN quantity input.
    private final By e2eLpnNeedText =
            By.xpath("(//ion-col[@data-component-id='acceptquantity_naturalquantityfield_need'])[1]");

    private final By e2eQuantityInput =
            By.xpath("//input[@data-component-id='acceptquantity_naturalquantityfield_lpn']");

    private final By okButton =
            By.xpath("//span[text()='Ok']");

    @FindBy(xpath = "//span[text()='Pick Exception']")
    public WebElement pickExceptionButton;

    @FindBy(xpath = "//div[contains(text(),'Unpicked')]")
    public WebElement unpickedMessage;

    @FindBy(xpath = "//button[@data-component-id='popover_confirm']")
    public WebElement  pickExceptionConfirmButton;


    private final By asnStatusValidation =
            By.xpath("(//div[@data-component-id='AsnStatusDescription'])[1]");

    // Optional inbound putaway warning. This message appears only for some locations.
    // It must never make the putaway flow fail when it is not present.
    private final By differentItemDedicatedWarning =
            By.xpath("//div[contains(text(),'dedicated')]");
    //permanently dedicated to a different item

    private final By confirmDifferentItemWarning =
            By.xpath("//button[@data-component-id='popover_confirm']");


    public void processWaveTasksInWmMobile(String waveNumber)
            throws InterruptedException {

        processWaveTasksInWmMobile(
                waveNumber,
                false,
                true,
                true,
                true,
                true
        );
    }


    public void processWaveTasksInWmMobile(
            String waveNumber,
            boolean subtractOneFromQuantity,
            boolean mandatoryOk,
            boolean clickOptionalOkIfPresent,
            boolean returnToTasksAfterEachTask,
            boolean verifyCompletedAfterEachTask)
            throws InterruptedException {
        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }
        Object taskObject =
                ScenarioContext.get("Task Numbers - " + waveNumber);
        if (!(taskObject instanceof List<?>)) {
            throw new IllegalStateException(
                    "Task numbers were not available in ScenarioContext for Wave: "
                            + waveNumber
            );
        }
        List<?> storedTasks = (List<?>) taskObject;
        if (storedTasks.isEmpty()) {
            throw new IllegalStateException(
                    "No tasks were available for Wave: " + waveNumber
            );
        }
        List<String> taskNumbers = new ArrayList<>();
        for (Object task : storedTasks) {
            if (task != null && !task.toString().isBlank()) {
                taskNumbers.add(task.toString().trim());
            }
        }
        if (taskNumbers.isEmpty()) {
            throw new IllegalStateException(
                    "No valid task IDs were available for Wave: " + waveNumber
            );
        }
        System.out.println("========================================");
        System.out.println("STARTING WM MOBILE TASK PROCESSING");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println("Total Tasks: " + taskNumbers.size());
        System.out.println("========================================");
        for (int i = 0; i < taskNumbers.size(); i++) {
            String taskId = taskNumbers.get(i);
            System.out.println("----------------------------------------");
            System.out.println(
                    "Processing Task " + (i + 1)
                            + " of " + taskNumbers.size()
                            + ": " + taskId
            );
            System.out.println("----------------------------------------");
            processSingleWaveTask(
                    taskId,
                    waveNumber,
                    subtractOneFromQuantity,
                    mandatoryOk,
                    clickOptionalOkIfPresent,
                    returnToTasksAfterEachTask,
                    verifyCompletedAfterEachTask,
                    false
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Task completed successfully in WM Mobile: " + taskId
            );
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "All " + taskNumbers.size()
                        + " task(s) completed successfully for Wave: "
                        + waveNumber
        );

        System.out.println("========================================");
        System.out.println("WM MOBILE TASK PROCESSING COMPLETED");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println("Total Tasks Completed: " + taskNumbers.size());
        System.out.println("========================================");
    }


    public void processFirstWaveTaskWithQuantityAdjustment(
            String waveNumber)
            throws InterruptedException {

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        Object taskObject =
                ScenarioContext.get("Task Numbers - " + waveNumber);

        if (!(taskObject instanceof List<?>)) {
            throw new IllegalStateException(
                    "Task numbers were not available in ScenarioContext for Wave: "
                            + waveNumber
            );
        }

        List<?> storedTasks = (List<?>) taskObject;

        if (storedTasks.isEmpty()) {
            throw new IllegalStateException(
                    "No tasks were available for Wave: " + waveNumber
            );
        }

        String taskId = storedTasks.get(0) == null
                ? null
                : storedTasks.get(0).toString().trim();

        if (taskId == null || taskId.isBlank()) {
            throw new IllegalStateException(
                    "First task ID was empty for Wave: " + waveNumber
            );
        }

        System.out.println(
                "Processing first task with quantity - 1: "
                        + taskId
        );

        processSingleWaveTask(
                taskId,
                waveNumber,
                true,
                false,
                false,
                false,
                false,
                false
        );
    }


    private void processSingleWaveTask(
            String taskId,
            String waveNumber,
            boolean subtractOneFromQuantity,
            boolean mandatoryOk,
            boolean clickOptionalOkIfPresent,
            boolean returnToTasksAfterEachTask,
            boolean verifyCompletedAfterEachTask,
            boolean e2eFullLpnPicking)
            throws InterruptedException {

        if (taskId == null || taskId.isBlank()) {
            throw new IllegalArgumentException(
                    "Task ID cannot be null or empty"
            );
        }

        String parentWindowId = driver.getWindowHandle();
        ScenarioContext.set("parentWindowId", parentWindowId);

        // =====================================================
        // OPEN WM MOBILE
        // =====================================================

        click(menuToggleButton);
        Thread.sleep(2000);

        type(searchBarInLandingPage, "WM Mobile");
        Thread.sleep(2000);

        click(clickWmMobileFromMenu);
        waitForPageLoad();
        Thread.sleep(2000);
        boolean childWindowFound = false;
        for (String handle : driver.getWindowHandles()) {

            if (!handle.equals(parentWindowId)) {
                driver.switchTo().window(handle);
                childWindowFound = true;
                break;
            }
        }

        if (!childWindowFound) {
            throw new IllegalStateException(
                    "WM Mobile child window was not opened for Task: "
                            + taskId
            );
        }
        try {
            searchAndWaitForNextElement(searchBarInWmMobile,"Enter Task", enterTask);
            Thread.sleep(2000);
            click(driver.findElement(enterTask));
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(taskInput)
            );
            WebElement taskInputElement =
                    driver.findElement(taskInput);
            type(taskInputElement, taskId);
            waitForInputValueContains(
                    taskInput,
                    taskId
            );
            pressEnter(taskInputElement);
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(tapToConfirm)
            );
            Thread.sleep(2000);
            click(driver.findElement(tapToConfirm));
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(acceptLocation)
            );
            WebElement acceptLocationElement =
                    driver.findElement(acceptLocation);
            String location = acceptLocationElement.getText();
            if (location == null || location.isBlank()) {
                throw new IllegalStateException(
                        "Accept location was empty for Task: " + taskId
                );
            }
            location = location.trim();

            System.out.println(
                    "Task " + taskId + " Location: " + location
            );
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(scanLocation)
            );

            WebElement scanLocationElement =
                    driver.findElement(scanLocation);

            type(scanLocationElement, location);

            waitForInputValueContains(
                    scanLocation,
                    location
            );

            Thread.sleep(1000);

            pressEnter(scanLocationElement);

            if (e2eFullLpnPicking) {
                // E2E path: this is a full-LPN pick. Enter 1 LPN and never
                // run the short-pick / Pick Exception logic.
                WebDriverWait e2eWait =
                        new WebDriverWait(driver, Duration.ofSeconds(15));

                WebElement needTextElement =
                        e2eWait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        e2eLpnNeedText));

                String needText = needTextElement.getText();
                if (needText == null || needText.isBlank()) {
                    throw new IllegalStateException(
                            "Need field text was empty for E2E task: " + taskId);
                }

                // Example UI text: "1 LPN". Extract only the numeric quantity.
                java.util.regex.Matcher needMatcher =
                        java.util.regex.Pattern.compile("(\\d+)")
                                .matcher(needText.trim());

                if (!needMatcher.find()) {
                    throw new IllegalStateException(
                            "Could not extract the required LPN quantity from Need text. "
                                    + "Task: " + taskId
                                    + " | Need text: " + needText);
                }

                String requiredLpnQuantity = needMatcher.group(1);

                WebElement quantityElement =
                        e2eWait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        e2eQuantityInput));

                if (!quantityElement.isEnabled()) {
                    throw new IllegalStateException(
                            "LPN quantity input is disabled for Task: " + taskId);
                }

                type(quantityElement, requiredLpnQuantity);

                // Wait for the exact value and then validate it before Enter.
                e2eWait.until(driver -> {
                    String value = driver.findElement(e2eQuantityInput)
                            .getAttribute("value");
                    return requiredLpnQuantity.equals(value == null ? null : value.trim());
                });

                String enteredQuantity = quantityElement.getAttribute("value");
                if (enteredQuantity == null
                        || !enteredQuantity.trim().equals(requiredLpnQuantity)) {
                    throw new IllegalStateException(
                            "LPN quantity validation failed. Expected: "
                                    + requiredLpnQuantity
                                    + " | Actual: " + enteredQuantity
                                    + " | Task: " + taskId);
                }

                System.out.println(
                        "E2E full-LPN pick | Task: " + taskId
                                + " | Need text: " + needText
                                + " | Extracted quantity: " + requiredLpnQuantity
                                + " | Entered quantity: " + enteredQuantity);

                pressEnter(quantityElement);

            } else {
                // Existing standard unit-picking and quantity-adjustment flow.
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(quantityInput)
                );
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(unitText)
                );

                String unitDisplay =
                        driver.findElement(unitText).getText();

                String displayedQuantity =
                        extractUnitQuantity(unitDisplay);

                int displayedQuantityValue =
                        Integer.parseInt(displayedQuantity);

                int quantityToEnterValue =
                        subtractOneFromQuantity
                                ? displayedQuantityValue - 1
                                : displayedQuantityValue;

                if (quantityToEnterValue <= 0) {
                    throw new IllegalStateException(
                            "Quantity after adjustment must be greater than zero. "
                                    + "Task: " + taskId
                                    + " | Displayed: " + displayedQuantityValue
                                    + " | Adjustment: "
                                    + (subtractOneFromQuantity ? "-1" : "none")
                    );
                }

                String quantityToEnter =
                        String.valueOf(quantityToEnterValue);

                System.out.println(
                        "Task " + taskId
                                + " Unit Display: " + unitDisplay
                                + " | Displayed Quantity: " + displayedQuantity
                                + " | Entering Quantity: " + quantityToEnter
                );

                WebElement quantityElement =
                        driver.findElement(quantityInput);

                type(quantityElement, quantityToEnter);

                waitForInputValueContains(
                        quantityInput,
                        quantityToEnter
                );

                pressEnter(quantityElement);

                if (subtractOneFromQuantity) {
                    handlePriorityTenPlusPickException();
                }
            }
            if (mandatoryOk) {

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(okButton)
                );

                click(driver.findElement(okButton));

            } else if (clickOptionalOkIfPresent) {

                clickOptionalOkButton();
            }

            System.out.println(
                    "Task WM quantity processing completed: " + taskId
            );

        } finally {
            if (driver.getWindowHandle().equals(parentWindowId)) {
                throw new IllegalStateException(
                        "WM Mobile window was closed or focus changed unexpectedly "
                                + "while processing Task: " + taskId
                );
            }

            driver.close();
            driver.switchTo().window(parentWindowId);

            Thread.sleep(2000);
        }
        if (returnToTasksAfterEachTask) {
            // The parent window is already on the Tasks page after the WM
            // Mobile child window is closed. Do not navigate through Menu
            // Toggle -> Wave Runs -> filter -> Related Links -> Tasks again.
            WavesPage wavesPage = new WavesPage();
            wavesPage.refreshTasksPage();

            if (verifyCompletedAfterEachTask) {
                refreshTasksAndVerifyCompleted(taskId);
            }
        }
    }


    private void handlePriorityTenPlusPickException()
            throws InterruptedException {

        WebDriverWait exceptionWait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(5)
                );

        Thread.sleep(2000);
        click(pickExceptionButton);
        // The application can render Unpicked after the Pick Exception click.
        isDisplayed(unpickedMessage);
        Thread.sleep(2000);
        click(pickExceptionConfirmButton);
        System.out.println(
                "Pick Exception -> Unpicked -> Confirm completed."
        );
        Thread.sleep(1500);
    }


    private void clickOptionalOkButton()
            throws InterruptedException {

        WebDriverWait optionalWait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(5)
                );

        try {
            WebElement ok =
                    optionalWait.until(d -> {
                        List<WebElement> elements =
                                d.findElements(okButton);

                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed()
                                        && element.isEnabled()) {
                                    return element;
                                }
                            } catch (Exception ignored) {
                                // DOM refresh.
                            }
                        }

                        return null;
                    });

            click(ok);
            System.out.println(
                    "Optional Ok button displayed and clicked."
            );

        } catch (TimeoutException e) {
            System.out.println(
                    "Optional Ok button was not displayed. Continuing."
            );
        }
    }


    /**
     * Handles the optional inbound putaway warning shown after scanning a location.
     *
     * If the warning is displayed, click Confirm. If it is not displayed,
     * continue immediately without failing the putaway flow.
     */
    private void handleDifferentItemDedicatedWarning() {

        WebDriverWait optionalWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            WebElement warning = optionalWait.until(driver -> {
                List<WebElement> elements =
                        driver.findElements(differentItemDedicatedWarning);

                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed()) {
                            return element;
                        }
                    } catch (Exception ignored) {
                        // DOM can refresh while WM Mobile is processing the location.
                    }
                }

                return null;
            });

            if (warning != null) {
                System.out.println(
                        "Inbound Putaway warning detected: "
                                + "permanently dedicated to a different item"
                );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                WebElement confirmButton = optionalWait.until(driver -> {
                    List<WebElement> elements =
                            driver.findElements(confirmDifferentItemWarning);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (WebElement element : elements) {
                        try {
                            if (element.isDisplayed() && element.isEnabled()) {
                                return element;
                            }
                        } catch (Exception ignored) {
                            // DOM can refresh while the confirmation dialog is rendered.
                        }
                    }

                    return null;
                });

                click(confirmButton);

                System.out.println(
                        "Confirmed inbound Putaway warning and continuing."
                );
            }

        } catch (org.openqa.selenium.TimeoutException e) {
            // Warning is optional. If it does not appear within the short window,
            // continue with the normal putaway flow.
            System.out.println(
                    "No inbound Putaway dedicated-item warning detected. Continuing."
            );
        }
    }

    private void waitForInputValueContains(
            By locator,
            String expectedValue) {

        wait.until(driver -> {

            try {
                WebElement element = driver.findElement(locator);
                String actualValue = element.getAttribute("value");

                return actualValue != null
                        && actualValue.contains(expectedValue);

            } catch (Exception e) {
                return false;
            }
        });
    }


    private String extractUnitQuantity(String unitDisplay) {

        if (unitDisplay == null || unitDisplay.isBlank()) {
            throw new IllegalStateException(
                    "Unit display text was empty"
            );
        }

        java.util.regex.Matcher matcher =
                java.util.regex.Pattern.compile(
                        "(\\d+)\\s*UNIT",
                        java.util.regex.Pattern.CASE_INSENSITIVE
                ).matcher(unitDisplay.trim());

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Unable to extract quantity from unit display: "
                            + unitDisplay
            );
        }

        return matcher.group(1);
    }


    private void refreshTasksAndVerifyCompleted(String taskId)
            throws InterruptedException {

        Thread.sleep(1000);
        By taskElementLocator = By.xpath(
                "//span[@data-component-id='TaskId' and normalize-space()='"
                        + taskId
                        + "']"
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(taskElementLocator)
        );

        WebElement taskElement =
                driver.findElement(taskElementLocator);
        By taskCardLocator = By.xpath(
                "//span[@data-component-id='TaskId' and normalize-space()='"
                        + taskId
                        + "']/ancestor::*[.//*[normalize-space()='Completed']][1]"
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(taskCardLocator)
        );

        WebElement taskCard =
                driver.findElement(taskCardLocator);

        String cardText = taskCard.getText();

        if (cardText == null
                || !cardText.toLowerCase().contains("completed")) {

            throw new IllegalStateException(
                    "Task was not completed after WM Mobile processing. "
                            + "Task: " + taskId
                            + " | Card: " + cardText
            );
        }

        System.out.println(
                "Verified Completed status for Task: " + taskId
        );
    }


    public void userCompletes(String transaction) throws InterruptedException {
        System.out.println("========== SCENARIO CONTEXT ==========");
        ScenarioContext.printAll();
        System.out.println("======================================");
        if (transaction.equalsIgnoreCase("ReceiveByUnit")) {
            receiveByUnitEachLpn();
            return;
        }
        else if (transaction.equalsIgnoreCase("Putaway")) {
            performPutaway();
            return;
        }
        if (transaction.equalsIgnoreCase("LPNReceiving")) {
            @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");
            if (createdASNData == null || createdASNData.isEmpty()) {
                throw new IllegalStateException("No Created LPN Level ASN data found in ScenarioContext");
            }
            System.out.println("========================================");
            System.out.println("STARTING LPN LEVEL ASN RECEIVING");
            System.out.println("Total ASNs: " + createdASNData.size());
            System.out.println("========================================");
            for (ASNData asnData : createdASNData) {
                String asnId = asnData.getAsn();
                List<ILPNData> ilpns = asnData.getIlpns();
                if (asnId == null || asnId.isBlank()) {

                    throw new IllegalStateException("ASN ID is null or empty in ASNData");
                }
                if (ilpns == null || ilpns.isEmpty()) {

                    throw new IllegalStateException("No ILPNs found for ASN: " + asnId);
                }
                System.out.println("========================================");

                System.out.println("Processing LPN Level ASN: " + asnId);

                System.out.println("Total LPNs: " + ilpns.size());

                System.out.println("========================================");


                // =================================================
                // OPEN WM MOBILE
                // =================================================

                click(menuToggleButton);

                Thread.sleep(4000);


                type(searchBarInLandingPage, "WM Mobile");


                // =================================================
                // STORE PARENT WINDOW
                // =================================================

                String parentWindowId = driver.getWindowHandle();


                ScenarioContext.set("parentWindowId", parentWindowId);


                // =================================================
                // OPEN WM MOBILE
                // =================================================

                click(clickWmMobileFromMenu);


                waitForPageLoad();

                //                Thread.sleep(5000);
                Thread.sleep(2000);

                // =================================================
                // SWITCH TO WM MOBILE WINDOW
                // =================================================

                for (String handle : driver.getWindowHandles()) {

                    if (!handle.equals(parentWindowId)) {

                        driver.switchTo().window(handle);

                        break;
                    }
                }
                Thread.sleep(3000);
                searchAndWaitForNextElement(searchBarInWmMobile,"190 Receiving", click190Receiving);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Searched for 190 Receiving");
                Thread.sleep(3000);
                click(click190Receiving);
                Thread.sleep(2000);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");
                type(passAsnIdReceive, asnId);
                pressEnter(passAsnIdReceive);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered ASN ID: " + asnId + " in 190 Receiving Transaction");
                Thread.sleep(2000);
                for (int i = 0; i < ilpns.size(); i++) {
                    ILPNData ilpnData = ilpns.get(i);
                    String lpnId = ilpnData.getIlpn();
                    if (lpnId == null || lpnId.isBlank()) {
                        throw new IllegalStateException("LPN ID is null or empty for ASN: " + asnId + ", LPN index: " + (i + 1));
                    }
                    System.out.println("----------------------------------------");
                    System.out.println("ASN: " + asnId + " | Processing LPN " + (i + 1) + " of " + ilpns.size() + ": " + lpnId);
                    System.out.println("----------------------------------------");
                    type(passLpnIdReceive, lpnId);
                    pressEnter(passLpnIdReceive);
                    System.out.println("Completed LPN Receiving: " + lpnId + " for ASN: " + asnId);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Lpn: " + lpnId);

                }
                System.out.println("========================================");

                System.out.println("LPN LEVEL ASN RECEIVING COMPLETED");

                System.out.println("ASN: " + asnId);

                System.out.println("Total LPNs Received: " + ilpns.size());

                System.out.println("========================================");
                driver.close();
                driver.switchTo().window(parentWindowId);
                System.out.println("Returned to parent window after ASN: " + asnId);
                click(menuToggleButton);
                Thread.sleep(4000);
                type(searchBarInLandingPage, "ASNs");
                Thread.sleep(3000);
                click(clickAsnSFromMenu);
                Thread.sleep(2000);
                type(filterAsnById, asnId);
                Thread.sleep(3000);
                pressEnter(filterAsnById);
                click(refresh);
                Thread.sleep(3000);
                click(refresh);
                AsnPage asnPage = new AsnPage();
                asnPage.verifyASN(asnId);
                System.out.println("ASN Verification Completed: " + asnId);
            }
            System.out.println("========================================");
            System.out.println("ALL LPN LEVEL ASN RECEIVING COMPLETED");
            System.out.println("Total ASNs Received: " + createdASNData.size());
            System.out.println("========================================");
            report.addReportStepWithoutScreenshot(StepStatus.PASS, "Successfully completed LPN Level ASN Receiving");
            return;
        }
        @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");
        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException("No Created ASN data found in ScenarioContext");
        }
        System.out.println("Total ASNs to Receive: " + createdASNData.size());
        for (ASNData asnData : createdASNData) {
            String asnId = asnData.getAsn();
            List<ItemData> items = asnData.getItems();
            System.out.println("======================================");
            System.out.println("Starting Receiving for ASN: " + asnId);
            System.out.println("Total Line Items: " + items.size());
            System.out.println("======================================");
            click(menuToggleButton);
            Thread.sleep(4000);
            type(searchBarInLandingPage, "WM Mobile");
            String parentWindowId = driver.getWindowHandle();
            ScenarioContext.set("parentWindowId", parentWindowId);
            click(clickWmMobileFromMenu);


            waitForPageLoad();

            Thread.sleep(5000);


            for (String handle : driver.getWindowHandles()) {

                if (!handle.equals(parentWindowId)) {

                    driver.switchTo().window(handle);

                    break;
                }
            }
            Thread.sleep(3000);
            if (transaction.equalsIgnoreCase("ReceiveByPallet")) {
                searchAndWaitForNextElement(searchBarInWmMobile,"190 ReceiveByPallet", clickReceiveByPallet);
                Thread.sleep(3000);
                click(clickReceiveByPallet);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Receive By Pallet Transaction");
                Thread.sleep(2000);
            } else {
                searchAndWaitForNextElement(searchBarInWmMobile,"190 Receiving", click190Receiving);
                click(click190Receiving);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");
                Thread.sleep(2000);
            }
            type(passAsnIdReceive, asnId);
            pressEnter(passAsnIdReceive);
            report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Asn Id " + asnId);
            if (transaction.equalsIgnoreCase("ReceiveByPallet")) {
                pressEnter(scanPallet);
                Thread.sleep(3000);
                String palletId = getPallet.getText();
                if (palletId == null || palletId.isBlank()) {
                    throw new IllegalStateException("Pallet ID could not be fetched after pallet generation");
                }
                ScenarioContext.set("CreatedPalletId", palletId);
                System.out.println("Generated Pallet ID: " + palletId);
                System.out.println("Stored CreatedPalletId in ScenarioContext: " + ScenarioContext.get("CreatedPalletId"));
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Pallet Id" + palletId);
            }
            boolean isMixed = transaction.toLowerCase().contains("mixed");
            System.out.println("Transaction: " + transaction);
            System.out.println("Mixed ILPN: " + isMixed);
            String currentLpnId = null;
            ILPNData currentIlpnData = null;
            for (ItemData itemData : items) {
                String item = itemData.getItem();
                String quantity = itemData.getShippedQty();
                System.out.println("--------------------------------------");
                System.out.println("Processing Item: " + item + " | Quantity: " + quantity);
                System.out.println("--------------------------------------");
                if (!isMixed || currentLpnId == null) {
                    pressEnter(passLpnIdReceive);
                    Thread.sleep(2000);
                    currentLpnId = storeLpnIdFromWm.getText();
                    System.out.println("Created ILPN/LPN: " + currentLpnId);
                    currentIlpnData = new ILPNData(currentLpnId);
                    asnData.addIlpn(currentIlpnData);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "LPN ID from WM Mobile: " + currentLpnId + " for Item: " + item);
                }
                currentIlpnData.addItem(itemData);
                type(passItemReceive, item);
                pressEnter(passItemReceive);
                Thread.sleep(4000);
                type(passQtyReceive, quantity);
                Thread.sleep(2000);
                pressEnter(passQtyReceive);
                Thread.sleep(3000);
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered quantity to receive " + quantity);
                if (!isMixed) {
                    click(endLPNReceive);
                    Thread.sleep(3000);
                    System.out.println("Completed ILPN: " + currentLpnId + " for Item: " + item);
                    currentLpnId = null;
                    currentIlpnData = null;
                }
            }
            if (isMixed) {
                click(endLPNReceive);
                Thread.sleep(2000);
                System.out.println("Completed Mixed ILPN: " + currentLpnId + " containing " + items.size() + " items");
            }
            if (transaction.equalsIgnoreCase("ReceiveByPallet")) {
                Thread.sleep(2000);
                click(endPallet);
            }
            System.out.println("Completed Receiving for ASN: " + asnId);
            driver.close();
            driver.switchTo().window(parentWindowId);
            System.out.println("Returned to parent window after ASN: " + asnId);
            click(menuToggleButton);
            type(searchBarInLandingPage, "ASNs");
            Thread.sleep(3000);
            click(clickAsnSFromMenu);
            Thread.sleep(3000);
            type(filterAsnById, asnId);
            Thread.sleep(3000);
            pressEnter(filterAsnById);
            click(refresh);
            waitForStatus(asnStatusValidation,
                    ()->click(refresh),
                    "In Receiving");
            AsnPage asnPage = new AsnPage();
            asnPage.verifyASN(asnId);
        }
        System.out.println("ALL ASN RECEIVING COMPLETED");
    }

    public void putaway() throws InterruptedException {
        @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");
        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException("No Created ASN data found in ScenarioContext");
        }
        System.out.println("Total ASNs for Putaway: " + createdASNData.size());
        LocationDetails locationDetails = new LocationDetails();
        for (ASNData asnData : createdASNData) {
            String asnId = asnData.getAsn();
            System.out.println("======================================");
            System.out.println("Starting Putaway for ASN: " + asnId);
            System.out.println("======================================");
            for (ILPNData ilpnData : asnData.getIlpns()) {
                String ilpn = ilpnData.getIlpn();
                System.out.println("Processing ILPN: " + ilpn);
                String location = locationDetails.getNextActiveLocation();
                if (location == null || location.isBlank()) {
                    throw new IllegalStateException("No active location available for ILPN: " + ilpn);
                }
                storeIlpnLocation(ilpn, location);
                for (ItemData itemData : ilpnData.getItems()) {
                    String item = itemData.getItem();
                    String quantity = itemData.getShippedQty();
                    System.out.println("ILPN: " + ilpn + " | Item: " + item + " | Qty: " + quantity + " | Location: " + location);
                    System.out.println("--------------------------------------");
                    click(menuToggleButton);
                    Thread.sleep(4000);
                    type(searchBarInLandingPage, "WM Mobile");
                    String parentWindowId = driver.getWindowHandle();
                    ScenarioContext.set("parentWindowId", parentWindowId);
                    click(clickWmMobileFromMenu);
                    waitForPageLoad();
                    Thread.sleep(5000);
                    for (String handle : driver.getWindowHandles()) {
                        if (!handle.equals(parentWindowId)) {
                            driver.switchTo().window(handle);
                            break;
                        }
                    }
                    Thread.sleep(5000);
                    searchAndWaitForNextElement(searchBarInWmMobile, "190 User Directed", clickUD);
                    Thread.sleep(2000);
                    click(clickUD);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 User Directed Transaction");
                    type(scanContainer, ilpn);
                    pressEnter(scanContainer);
                    type(scanItemUD, item);
                    pressEnter(scanItemUD);
                    type(scanQtyUD, quantity);
                    pressEnter(scanQtyUD);
                    type(scanLocUD, location);
                    Thread.sleep(2000);
                    pressEnter(scanLocUD);
                    Thread.sleep(10000);
                    handleDifferentItemDedicatedWarning();
                    driver.close();
                    driver.switchTo().window(parentWindowId);
                    System.out.println("Putaway completed for ILPN: " + ilpn + " | Item: " + item + " | Location: " + location);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "Putaway completed for ILPN: " + ilpn + " | Item: " + item + " | Qty: " + quantity + " | Location: " + location);
                }
            }
            click(menuToggleButton);
            Thread.sleep(4000);
            type(searchBarInLandingPage, "ASNs");
            Thread.sleep(3000);
            click(clickAsnSFromMenu);
            Thread.sleep(5000);
            type(filterAsnById, asnId);

            Thread.sleep(3000);

            pressEnter(filterAsnById);

            click(refresh);

            Thread.sleep(3000);

            // =====================================================
            // GET ILPN IDs FOR THIS ASN
            // =====================================================

            List<String> ilpns = new ArrayList<>();

            for (ILPNData ilpnData : asnData.getIlpns()) {

                ilpns.add(ilpnData.getIlpn());
            }

            // =====================================================
            // VALIDATE ALL ILPN LOCATIONS
            // =====================================================

            AsnPage asnPage = new AsnPage();

            asnPage.verifyILPNLocation(asnId, ilpns);
        }

        // =========================================================
        // ALL PUTAWAY COMPLETED
        // =========================================================

        System.out.println("======================================");

        System.out.println("ALL PUTAWAY COMPLETED");

        System.out.println("======================================");

        click(refresh);

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully Completed ASN Putaway Process");
    }

    public void systemDirectedPutaway() throws InterruptedException {

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get("CreatedASNData");

        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext"
            );
        }

        System.out.println(
                "Total ASNs for System Directed Putaway: "
                        + createdASNData.size()
        );

        // =========================================================
        // LOOP THROUGH ALL ASNs
        // =========================================================

        for (ASNData asnData : createdASNData) {

            String asnId = asnData.getAsn();

            System.out.println("======================================");
            System.out.println(
                    "Starting System Directed Putaway for ASN: " + asnId
            );
            System.out.println("======================================");


            // =====================================================
            // LOOP THROUGH ALL ILPNs
            // =====================================================

            for (ILPNData ilpnData : asnData.getIlpns()) {

                String ilpn = ilpnData.getIlpn();

                System.out.println("Processing ILPN: " + ilpn);

                System.out.println("--------------------------------------");
                System.out.println(
                        "ILPN: " + ilpn + " | ASN: " + asnId
                );
                System.out.println("--------------------------------------");


                // =================================================
                // STORE PARENT WINDOW
                // =================================================

                String parentWindowId = driver.getWindowHandle();

                ScenarioContext.set(
                        "parentWindowId",
                        parentWindowId
                );


                // =================================================
                // OPEN WM MOBILE
                // =================================================

                click(menuToggleButton);

                Thread.sleep(4000);

                type(
                        searchBarInLandingPage,
                        "WM Mobile"
                );

                Thread.sleep(3000);

                click(clickWmMobileFromMenu);

                waitForPageLoad();

                Thread.sleep(5000);


                // =================================================
                // SWITCH TO WM MOBILE WINDOW
                // =================================================

                for (String handle : driver.getWindowHandles()) {

                    if (!handle.equals(parentWindowId)) {

                        driver.switchTo().window(handle);

                        break;
                    }
                }

                Thread.sleep(5000);


                // =================================================
                // OPEN 190 SYSTEM DIRECTED
                // =================================================

//                type(
//                        searchBarInWmMobile,
//                        "190 System Directed"
//                );
//
//                Thread.sleep(3000);
//
//                click(click190SD);
                searchAndWaitForNextElement(searchBarInWmMobile, "190 System Directed", click190SD);

                Thread.sleep(4000);
                click(click190SD);

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "User Entered 190 System Directed Transaction"
                );


                // =================================================
                // SCAN ILPN / CONTAINER
                // =================================================

                type(
                        scanContainer,
                        ilpn
                );

                Thread.sleep(2000);

                pressEnter(scanContainer);

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "User Scanned the ILPN "
                                + ilpn
                                + " in 190 System Directed Transaction"
                );


                // =================================================
                // WAIT FOR SYSTEM DIRECTED LOCATION
                // =================================================

                By systemDirectedLocationLocator =
                        By.xpath(
                                "//ion-col[@data-component-id='acceptlocationforsystemdirectedputaway_barcodetextfield_location']"
                        );

                WebDriverWait wait =
                        new WebDriverWait(
                                driver,
                                Duration.ofSeconds(15)
                        );


                // =================================================
                // WAIT UNTIL LOCATION ELEMENT APPEARS
                // =================================================

                WebElement systemDirectedLocation =
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        systemDirectedLocationLocator
                                )
                        );


                // =================================================
                // WAIT UNTIL LOCATION TEXT IS AVAILABLE
                // =================================================

                String location =
                        wait.until(driver -> {

                            String text =
                                    systemDirectedLocation
                                            .getText()
                                            .trim();

                            return text.isEmpty()
                                    ? null
                                    : text;
                        });


                // =================================================
                // PRINT SYSTEM GENERATED LOCATION
                // =================================================

                System.out.println(
                        "System Directed Location received: "
                                + location
                );

                storeIlpnLocation(
                        ilpn,
                        location
                );


                // =================================================
                // VALIDATE LOCATION
                // =================================================

                if (location == null || location.isEmpty()) {

                    throw new IllegalStateException(
                            "System Directed Putaway location is empty "
                                    + "for ILPN: "
                                    + ilpn
                    );
                }


                // =================================================
                // SCAN SYSTEM DIRECTED LOCATION
                // =================================================

                type(
                        scanLocUD,
                        location
                );

                Thread.sleep(2000);

                pressEnter(scanLocUD);


                // =========================================================
                // OPTIONAL SCAN CONTAINER VALIDATION
                // =========================================================
                //
                // After scanning the location, sometimes the application
                // displays the Scan Container field.
                //
                // Give the UI 2 seconds to render the next step.
                //
                // Then wait up to 5 seconds for the existing
                // scanContainer element to become displayed.
                //
                // If it appears:
                //     1. Get Expected Container using getText()
                //     2. Type it into Scan Container
                //     3. Validate getAttribute("value")
                //     4. Press Enter
                //
                // If it does not appear:
                //     Continue the normal SD Putaway flow.
                // =========================================================

                Thread.sleep(2000);

                By expectedContainerLocator =
                        By.xpath(
                                "//ion-col[@data-component-id='acceptlpnandcompletemove_barcodetextfield_expectedcontainer']"
                        );

                WebDriverWait containerWait =
                        new WebDriverWait(
                                driver,
                                Duration.ofSeconds(5)
                        );

                boolean scanContainerDisplayed = false;

                try {

                    scanContainerDisplayed =
                            containerWait.until(
                                    driver ->
                                            scanContainer.isDisplayed()
                            );

                } catch (TimeoutException e) {

                    scanContainerDisplayed = false;
                }


                // =========================================================
                // SCAN CONTAINER IS PRESENT
                // =========================================================

                if (scanContainerDisplayed) {

                    System.out.println(
                            "Scan Container field is displayed after "
                                    + "scanning location for ILPN: "
                                    + ilpn
                    );


                    // =====================================================
                    // GET EXPECTED CONTAINER
                    // =====================================================

                    WebElement expectedContainerElement =
                            containerWait.until(
                                    ExpectedConditions.visibilityOfElementLocated(
                                            expectedContainerLocator
                                    )
                            );

                    String expectedContainer =
                            expectedContainerElement
                                    .getText()
                                    .trim();

                    if (expectedContainer.isEmpty()) {

                        throw new IllegalStateException(
                                "Expected container value is empty "
                                        + "for ILPN: "
                                        + ilpn
                        );
                    }

                    System.out.println(
                            "Expected Container received from UI: "
                                    + expectedContainer
                    );


                    // =====================================================
                    // TYPE EXPECTED CONTAINER
                    // =====================================================

                    type(
                            scanContainer,
                            expectedContainer
                    );


                    // =====================================================
                    // VALIDATE SCAN CONTAINER VALUE
                    // =====================================================

                    String enteredContainer =
                            containerWait.until(
                                    driver -> {

                                        String value =
                                                scanContainer
                                                        .getAttribute("value");

                                        if (value == null
                                                || value.trim().isEmpty()) {

                                            return null;
                                        }

                                        return value.trim();
                                    }
                            );


                    System.out.println(
                            "Entered Scan Container value: "
                                    + enteredContainer
                    );


                    // =====================================================
                    // COMPARE EXPECTED VS ACTUAL
                    // =====================================================

                    if (!expectedContainer.equals(enteredContainer)) {

                        throw new IllegalStateException(
                                "Scan Container validation failed. "
                                        + "Expected: "
                                        + expectedContainer
                                        + " | Actual: "
                                        + enteredContainer
                        );
                    }


                    System.out.println(
                            "Scan Container validation passed. "
                                    + "Expected: "
                                    + expectedContainer
                                    + " | Actual: "
                                    + enteredContainer
                    );


                    // =====================================================
                    // PRESS ENTER
                    // =====================================================

                    pressEnter(scanContainer);

                    report.addReportStepWithScreenshot(
                            StepStatus.PASS,
                            "Validated and scanned expected container "
                                    + expectedContainer
                                    + " for ILPN "
                                    + ilpn
                    );

                } else {

                    // =====================================================
                    // SCAN CONTAINER NOT PRESENT
                    // =====================================================

                    System.out.println(
                            "Scan Container field is not displayed after "
                                    + "location scan for ILPN: "
                                    + ilpn
                                    + ". Continuing normal flow."
                    );
                }


                // =================================================
                // WAIT FOR PUTAWAY COMPLETION
                // =================================================

                Thread.sleep(10000);


                // =================================================
                // CLOSE WM MOBILE
                // =================================================

                driver.close();


                // =================================================
                // RETURN TO PARENT WINDOW
                // =================================================

                driver.switchTo().window(parentWindowId);


                // =================================================
                // LOG COMPLETION
                // =================================================

                System.out.println(
                        "System Directed Putaway completed for ILPN: "
                                + ilpn
                                + " | Location: "
                                + location
                );

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "System Directed Putaway completed for ILPN: "
                                + ilpn
                                + " | Location: "
                                + location
                );
            }


            // =====================================================
            // OPEN ASN UI
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);

            type(
                    searchBarInLandingPage,
                    "ASNs"
            );

            Thread.sleep(3000);

            click(clickAsnSFromMenu);

            Thread.sleep(5000);


            // =====================================================
            // FILTER CURRENT ASN
            // =====================================================

            type(
                    filterAsnById,
                    asnId
            );

            Thread.sleep(3000);

            pressEnter(filterAsnById);


            // =====================================================
            // REFRESH ASN
            // =====================================================

            click(refresh);

            Thread.sleep(3000);


            // =====================================================
            // VERIFY CURRENT ASN
            // =====================================================

            List<String> ilpns = new ArrayList<>();

            for (ILPNData ilpnData : asnData.getIlpns()) {

                ilpns.add(
                        ilpnData.getIlpn()
                );
            }

            AsnPage asnPage = new AsnPage();

            asnPage.verifyILPNLocation(
                    asnId,
                    ilpns
            );
        }


        // =========================================================
        // ALL SYSTEM DIRECTED PUTAWAY COMPLETED
        // =========================================================

        System.out.println("======================================");
        System.out.println(
                "ALL SYSTEM DIRECTED PUTAWAY COMPLETED"
        );
        System.out.println("======================================");


        Thread.sleep(3000);

        click(refresh);

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Successfully Completed System Directed Putaway ASN Process"
        );
    }

    private void receiveByUnitEachLpn() throws InterruptedException {

        // =========================================================
        // GET CREATED ASN DATA
        // =========================================================

        @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");


        if (createdASNData == null || createdASNData.isEmpty()) {

            throw new IllegalStateException("No Created ASN data found in ScenarioContext");
        }


        System.out.println("========================================");

        System.out.println("STARTING RECEIVE BY UNIT - EACH UNIT IN SEPARATE LPN");

        System.out.println("Total ASNs: " + createdASNData.size());

        System.out.println("========================================");


        // =========================================================
        // PROCESS EACH ASN
        // =========================================================

        for (ASNData asnData : createdASNData) {

            String asnId = asnData.getAsn();

            List<ItemData> items = asnData.getItems();


            // =====================================================
            // VALIDATE ASN
            // =====================================================

            if (asnId == null || asnId.isBlank()) {

                throw new IllegalStateException("ASN ID is null or empty in ASNData");
            }


            if (items == null || items.isEmpty()) {

                throw new IllegalStateException("No items found for ASN: " + asnId);
            }


            System.out.println("========================================");

            System.out.println("Starting Receive By Unit for ASN: " + asnId);

            System.out.println("Total Line Items: " + items.size());

            System.out.println("========================================");


            // =====================================================
            // OPEN WM MOBILE
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);


            type(searchBarInLandingPage, "WM Mobile");


            // =====================================================
            // STORE PARENT WINDOW
            // =====================================================

            String parentWindowId = driver.getWindowHandle();


            ScenarioContext.set("parentWindowId", parentWindowId);


            // =====================================================
            // OPEN WM MOBILE
            // =====================================================

            click(clickWmMobileFromMenu);


            waitForPageLoad();

            Thread.sleep(5000);


            // =====================================================
            // SWITCH TO WM MOBILE WINDOW
            // =====================================================

            for (String handle : driver.getWindowHandles()) {

                if (!handle.equals(parentWindowId)) {

                    driver.switchTo().window(handle);

                    break;
                }
            }


            Thread.sleep(3000);


            // =====================================================
            // OPEN 190 RECEIVING
            // =====================================================

//            type(searchBarInWmMobile, "190 Receiving");
//
//
//            Thread.sleep(3000);
            searchAndWaitForNextElement(searchBarInWmMobile, "190 Receiving", click190Receiving);


            click(click190Receiving);


            Thread.sleep(2000);
            report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");


            // =====================================================
            // ENTER ASN
            // =====================================================

            type(passAsnIdReceive, asnId);


            pressEnter(passAsnIdReceive);

            report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the ASN " + asnId + " in 190 Receiving Transaction");

            Thread.sleep(7000);


            // =====================================================
            // PROCESS EACH ITEM
            // =====================================================

            for (ItemData itemData : items) {

                String item = itemData.getItem();

                String quantity = itemData.getShippedQty();


                // =================================================
                // VALIDATE QUANTITY
                // =================================================

                if (quantity == null || quantity.isBlank()) {

                    throw new IllegalStateException("Shipped quantity is null or empty for Item: " + item + " in ASN: " + asnId);
                }


                int shippedQty;

                try {

                    shippedQty = Integer.parseInt(quantity);

                } catch (NumberFormatException e) {

                    throw new IllegalStateException("Invalid shipped quantity: " + quantity + " for Item: " + item + " in ASN: " + asnId, e);
                }


                if (shippedQty <= 0) {

                    throw new IllegalStateException("Shipped quantity must be greater than 0" + " for Item: " + item + " in ASN: " + asnId);
                }


                System.out.println("----------------------------------------");

                System.out.println("Processing Item: " + item + " | Shipped Quantity: " + shippedQty);

                System.out.println("Required LPNs for Item: " + shippedQty);

                System.out.println("----------------------------------------");


                // =================================================
                // CREATE ONE LPN FOR EACH UNIT
                // =================================================

                for (int unit = 1; unit <= shippedQty; unit++) {


                    System.out.println("----------------------------------------");

                    System.out.println("Item: " + item + " | Unit " + unit + " of " + shippedQty);

                    System.out.println("Creating separate LPN for Unit " + unit);

                    System.out.println("----------------------------------------");


                    // =============================================
                    // AUTO-GENERATE NEW LPN
                    // =============================================

                    pressEnter(passLpnIdReceive);


                    Thread.sleep(5000);


                    // =============================================
                    // GET GENERATED LPN ID
                    // =============================================

                    String currentLpnId = storeLpnIdFromWm.getText();
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Auto Generated the LPN " + currentLpnId + " for Item " + item + " Unit " + unit + " of " + shippedQty + " in 190 Receiving Transaction");


                    if (currentLpnId == null || currentLpnId.isBlank()) {

                        throw new IllegalStateException("Generated LPN ID is null or empty" + " for Item: " + item + " Unit: " + unit + " ASN: " + asnId);
                    }


                    System.out.println("Created ILPN/LPN: " + currentLpnId);


                    // =============================================
                    // CREATE ILPN DATA
                    // =============================================

                    ILPNData currentIlpnData = new ILPNData(currentLpnId);


                    asnData.addIlpn(currentIlpnData);


                    // =============================================
                    // ADD ITEM TO ILPN
                    // =============================================

                    currentIlpnData.addItem(itemData);


                    // =============================================
                    // REPORT GENERATED LPN
                    // =============================================

                    report.addReportStepWithScreenshot(StepStatus.PASS, "LPN ID from WM Mobile: " + currentLpnId + " for Item: " + item + " Unit: " + unit + " of " + shippedQty);


                    // =============================================
                    // ENTER ITEM
                    // =============================================

                    type(passItemReceive, item);


                    pressEnter(passItemReceive);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Item " + item + " for LPN " + currentLpnId + " in 190 Receiving Transaction");


                    Thread.sleep(4000);


                    // =============================================
                    // ENTER QUANTITY = 1
                    // =============================================

                    type(passQtyReceive, "1");


                    Thread.sleep(2000);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Quantity 1 for Item " + item + " in LPN " + currentLpnId + " in 190 Receiving Transaction");


                    pressEnter(passQtyReceive);


                    Thread.sleep(3000);


                    // =============================================
                    // END CURRENT LPN
                    // =============================================

                    click(endLPNReceive);


                    Thread.sleep(3000);


                    System.out.println("Completed LPN: " + currentLpnId + " | Item: " + item + " | Quantity: 1");
                }


                System.out.println("Completed all " + shippedQty + " LPNs for Item: " + item);
            }


            // =====================================================
            // CURRENT ASN COMPLETED
            // =====================================================

            System.out.println("========================================");

            System.out.println("Receive By Unit Completed for ASN: " + asnId);

            System.out.println("========================================");


            // =====================================================
            // CLOSE WM MOBILE
            // =====================================================

            driver.close();


            driver.switchTo().window(parentWindowId);


            System.out.println("Returned to parent window after ASN: " + asnId);


            // =====================================================
            // OPEN ASNs UI
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);


            type(searchBarInLandingPage, "ASNs");


            Thread.sleep(3000);


            click(clickAsnSFromMenu);


            Thread.sleep(5000);


            // =====================================================
            // FILTER CURRENT ASN
            // =====================================================

            type(filterAsnById, asnId);


            Thread.sleep(3000);


            pressEnter(filterAsnById);


            click(refresh);


            Thread.sleep(3000);


            // =====================================================
            // VERIFY CURRENT ASN
            // =====================================================

            AsnPage asnPage = new AsnPage();


            asnPage.verifyASN(asnId);


            System.out.println("ASN Verification Completed: " + asnId);
        }


        // =========================================================
        // FINAL MESSAGE
        // =========================================================

        System.out.println("======================================");

        System.out.println("ALL RECEIVE BY UNIT ASN RECEIVING COMPLETED");

        System.out.println("======================================");


        report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully completed Receive By Unit " + "with each quantity in separate LPN");
    }

    //    public void palletPutaway() throws InterruptedException {
    //
    //        // =========================================================
    //        // GET PALLET ID FROM SCENARIO CONTEXT
    //        // =========================================================
    //
    //        String palletId = (String) ScenarioContext.get("CreatedPalletId");
    //
    //        if (palletId == null || palletId.isBlank()) {
    //
    //            throw new IllegalStateException("No Created Pallet ID found in ScenarioContext");
    //        }
    //
    //
    //        System.out.println("======================================");
    //
    //        System.out.println("STARTING PALLET PUTAWAY");
    //
    //        System.out.println("Pallet ID: " + palletId);
    //
    //        System.out.println("======================================");
    //
    //
    //        // =========================================================
    //        // GET NEXT ACTIVE LOCATION
    //        // =========================================================
    //
    //        LocationDetails locationDetails = new LocationDetails();
    //
    //        String location = locationDetails.getNextReserveLocation();
    //
    //        if (location == null || location.isBlank()) {
    //
    //            throw new IllegalStateException("No reserve location available for Pallet Putaway");
    //        }
    //
    //
    //        System.out.println("Assigned Location: " + location);
    //
    //
    //        // =========================================================
    //        // OPEN WM MOBILE
    //        // =========================================================
    //
    //        click(menuToggleButton);
    //
    //        Thread.sleep(4000);
    //
    //
    //        type(searchBarInLandingPage, "WM Mobile");
    //
    //
    //        // =========================================================
    //        // STORE PARENT WINDOW
    //        // =========================================================
    //
    //        String parentWindowId = driver.getWindowHandle();
    //
    //        ScenarioContext.set("parentWindowId", parentWindowId);
    //
    //
    //        // =========================================================
    //        // OPEN WM MOBILE
    //        // =========================================================
    //
    //        click(clickWmMobileFromMenu);
    //
    //        waitForPageLoad();
    //
    //        Thread.sleep(5000);
    //
    //
    //        // =========================================================
    //        // SWITCH TO WM MOBILE WINDOW
    //        // =========================================================
    //
    //        for (String handle : driver.getWindowHandles()) {
    //
    //            if (!handle.equals(parentWindowId)) {
    //
    //                driver.switchTo().window(handle);
    //
    //                break;
    //            }
    //        }
    //
    //
    //        Thread.sleep(5000);
    //
    //
    //        // =========================================================
    //        // OPEN 190 USER DIRECTED
    //        // =========================================================
    //
    //        type(searchBarInWmMobile, "190 User Directed");
    //
    //        Thread.sleep(3000);
    //
    //
    //        click(clickUD);
    //
    //        Thread.sleep(4000);
    //        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Putaway Transaction");
    //
    //
    //        // =========================================================
    //        // SCAN PALLET ID
    //        //
    //        // Pallet ID is entered into the existing
    //        // Scan Container field.
    //        // =========================================================
    //
    //        type(scanContainer, palletId);
    //
    //        Thread.sleep(2000);
    //
    //
    //        pressEnter(scanContainer);
    //
    //        Thread.sleep(3000);
    //        report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Pallet ID " + palletId + " in 190 User Directed Transaction");
    //
    //
    //        System.out.println("Scanned Pallet ID: " + palletId);
    //
    //
    //        // =========================================================
    //        // SCAN STORAGE LOCATION
    //        // =========================================================
    //
    //        type(scanLocUD, location);
    //
    //        Thread.sleep(2000);
    //
    //
    //        pressEnter(scanLocUD);
    //
    //        Thread.sleep(10000);
    //        report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Location " + location + " in 190 User Directed Transaction");
    //
    //
    //        // =========================================================
    //        // CLOSE WM MOBILE
    //        // =========================================================
    //
    //        driver.close();
    //
    //
    //        // =========================================================
    //        // RETURN TO PARENT WINDOW
    //        // =========================================================
    //
    //        driver.switchTo().window(parentWindowId);
    //
    //
    //        // =========================================================
    //        // PALLET PUTAWAY COMPLETED
    //        // =========================================================
    //
    //        System.out.println("======================================");
    //
    //        System.out.println("PALLET PUTAWAY COMPLETED");
    //
    //        System.out.println("Pallet ID: " + palletId + " | Location: " + location);
    //
    //        System.out.println("======================================");
    //
    //
    //        // =========================================================
    //        // REPORT
    //        // =========================================================
    //
    //        report.addReportStepWithScreenshot(StepStatus.PASS, "Pallet Putaway completed for Pallet: " + palletId + " | Location: " + location);
    //    }
//            public void palletPutaway() throws InterruptedException {
//
//                // =========================================================
//                // GET PALLET ID FROM SCENARIO CONTEXT
//                // =========================================================
//
//                String palletId =
//                        (String) ScenarioContext.get("CreatedPalletId");
//
//                if (palletId == null || palletId.isBlank()) {
//
//                    throw new IllegalStateException(
//                            "No Created Pallet ID found in ScenarioContext"
//                    );
//                }
//
//                palletId = palletId.trim();
//
//
//                System.out.println("======================================");
//                System.out.println("STARTING PALLET PUTAWAY");
//                System.out.println("Pallet ID: " + palletId);
//                System.out.println("======================================");
//
//
//                // =========================================================
//                // GET NEXT RESERVE LOCATION
//                // =========================================================
//
//                LocationDetails locationDetails =
//                        new LocationDetails();
//
//                String location =
//                        locationDetails.getNextReserveLocation();
//
//                if (location == null || location.isBlank()) {
//
//                    throw new IllegalStateException(
//                            "No reserve location available for Pallet Putaway"
//                    );
//                }
//
//                location = location.trim();
//
//                System.out.println(
//                        "Assigned Location: " + location
//                );
//
//
//                // =========================================================
//                // OPEN WM MOBILE
//                // =========================================================
//
//                click(//                        menuToggleButton);
//
//                Thread.sleep(4000);
//
//
//                type(
//                        searchBarInLandingPage,
//                        "WM Mobile",
//                        "Search Bar in Landing Page"
//                );
//
//
//                // =========================================================
//                // STORE PARENT WINDOW
//                // =========================================================
//
//                String parentWindowId =
//                        driver.getWindowHandle();
//
//                ScenarioContext.set(
//                        "parentWindowId",
//                        parentWindowId
//                );
//
//
//                // =========================================================
//                // OPEN WM MOBILE
//                // =========================================================
//
//                click(//                        clickWmMobileFromMenu);
//
//                waitForPageLoad();
//
//                Thread.sleep(5000);
//
//
//                // =========================================================
//                // SWITCH TO WM MOBILE WINDOW
//                // =========================================================
//
//                for (String handle : driver.getWindowHandles()) {
//
//                    if (!handle.equals(parentWindowId)) {
//
//                        driver.switchTo().window(handle);
//
//                        break;
//                    }
//                }
//
//                Thread.sleep(5000);
//
//
//                // =========================================================
//                // OPEN 190 USER DIRECTED
//                // =========================================================
//
//                type(
//                        searchBarInWmMobile,
//                        "190 User Directed",
//                        "Search Bar in WM Mobile(190 User Directed)"
//                );
//
//                Thread.sleep(3000);
//
//
//                click(//                        clickUD);
//
//                Thread.sleep(4000);
//
//                report.addReportStepWithScreenshot(
//                        StepStatus.PASS,
//                        "User Entered 190 Putaway Transaction"
//                );
//
//
//                // =========================================================
//                // SCAN PALLET ID
//                // =========================================================
//
//                type(
//                        scanContainer,
//                        palletId,
//                        "Scan Pallet ID in 190 User Directed"
//                );
//
//                Thread.sleep(2000);
//
//
//                pressEnter(//                        scanContainer);
//
//                Thread.sleep(3000);
//
//                report.addReportStepWithScreenshot(
//                        StepStatus.PASS,
//                        "User Scanned the Pallet ID "
//                                + palletId
//                                + " in 190 User Directed Transaction"
//                );
//
//
//                System.out.println(
//                        "Scanned Pallet ID: " + palletId
//                );
//
//
//                // =========================================================
//                // SCAN STORAGE LOCATION
//                // =========================================================
//
//                type(
//                        scanLocUD,
//                        location,
//                        "Scan Location in 190 User Directed"
//                );
//
//                Thread.sleep(2000);
//
//
//                pressEnter(//                        scanLocUD);
//
//                Thread.sleep(10000);
//
//                report.addReportStepWithScreenshot(
//                        StepStatus.PASS,
//                        "User Scanned the Location "
//                                + location
//                                + " in 190 User Directed Transaction"
//                );
//
//
//                // =========================================================
//                // CLOSE WM MOBILE
//                // =========================================================
//
//                driver.close();
//
//
//                // =========================================================
//                // RETURN TO PARENT WINDOW
//                // =========================================================
//
//                driver.switchTo().window(parentWindowId);
//
//
//                // =========================================================
//                // PALLET PUTAWAY COMPLETED
//                // =========================================================
//
//                System.out.println("======================================");
//                System.out.println("PALLET PUTAWAY COMPLETED");
//                System.out.println(
//                        "Pallet ID: "
//                                + palletId
//                                + " | Location: "
//                                + location
//                );
//                System.out.println("======================================");
//
//
//                // =========================================================
//                // GET ALL CREATED ASN DATA
//                // =========================================================
//
//                @SuppressWarnings("unchecked")
//                List<ASNData> createdASNData =
//                        (List<ASNData>)
//                                ScenarioContext.get(
//                                        "CreatedASNData"
//                                );
//
//                if (createdASNData == null
//                        || createdASNData.isEmpty()) {
//
//                    throw new IllegalStateException(
//                            "No Created ASN data found in ScenarioContext"
//                    );
//                }
//
//
//                // =========================================================
//                // VERIFY EACH ASN
//                // =========================================================
//
//                for (ASNData asnData : createdASNData) {
//
//                    String asnId =
//                            asnData.getAsn();
//
//                    if (asnId == null || asnId.isBlank()) {
//
//                        throw new IllegalStateException(
//                                "ASN ID is null or empty in ASNData"
//                        );
//                    }
//
//
//                    // =====================================================
//                    // GET ILPNs FOR CURRENT ASN
//                    // =====================================================
//
//                    List<ILPNData> ilpnDataList =
//                            asnData.getIlpns();
//
//                    if (ilpnDataList == null
//                            || ilpnDataList.isEmpty()) {
//
//                        throw new IllegalStateException(
//                                "No ILPNs found for ASN: "
//                                        + asnId
//                        );
//                    }
//
//
//                    // =====================================================
//                    // CREATE ILPN STRING LIST
//                    // =====================================================
//
//                    List<String> ilpns =
//                            new ArrayList<>();
//
//                    Map<String, String> ilpnLocationMap =
//                            getIlpnLocationMap();
//
//                    for (ILPNData ilpnData : ilpnDataList) {
//
//                        String ilpn =
//                                ilpnData.getIlpn();
//
//                        if (ilpn == null || ilpn.isBlank()) {
//                            throw new IllegalStateException(
//                                    "ILPN is null or empty for ASN: "
//                                            + asnId
//                            );
//                        }
//
//                        ilpn = ilpn.trim();
//
//                        ilpns.add(ilpn);
//
//                        // Store ILPN -> Putaway Location
//                        ilpnLocationMap.put(
//                                ilpn,
//                                location
//                        );
//                        System.out.println(
//                                "Stored ILPN Location: "
//                                        + ilpn
//                                        + " -> "
//                                        + location
//                        );
//                    }
//                    System.out.println("STARTING PALLET ILPN VERIFICATION");
//                    System.out.println("ASN: " + asnId);
//                    System.out.println("Pallet ID: " + palletId);
//                    System.out.println("Putaway Location: " + location);
//                    System.out.println("ILPN Count: " + ilpns.size());
//                    AsnPage asnPage =
//                            new AsnPage();
//                    asnPage.verifyILPNLocation(
//                            asnId,
//                            ilpns
//                    );
//                }
//                report.addReportStepWithScreenshot(
//                        StepStatus.PASS,
//                        "Pallet Putaway and ILPN Verification completed "
//                                + "for Pallet: "
//                                + palletId
//                                + " | Location: "
//                                + location
//                );
//            }
    public void palletPutaway() throws InterruptedException {

        // =========================================================
        // GET PALLET ID FROM SCENARIO CONTEXT
        // =========================================================

        String palletId =
                (String) ScenarioContext.get("CreatedPalletId");

        if (palletId == null || palletId.isBlank()) {

            throw new IllegalStateException(
                    "No Created Pallet ID found in ScenarioContext"
            );
        }

        palletId = palletId.trim();


        System.out.println("======================================");
        System.out.println("STARTING PALLET PUTAWAY");
        System.out.println("Pallet ID: " + palletId);
        System.out.println("======================================");


        // =========================================================
        // GET NEXT RESERVE LOCATION
        // =========================================================

        LocationDetails locationDetails =
                new LocationDetails();

        String location =
                locationDetails.getNextReserveLocation();

        if (location == null || location.isBlank()) {

            throw new IllegalStateException(
                    "No reserve location available for Pallet Putaway"
            );
        }

        location = location.trim();

        System.out.println(
                "Assigned Location: " + location
        );


        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(menuToggleButton);

        Thread.sleep(4000);


        type(
                searchBarInLandingPage,
                "WM Mobile");


        // =========================================================
        // STORE PARENT WINDOW
        // =========================================================

        String parentWindowId =
                driver.getWindowHandle();

        ScenarioContext.set(
                "parentWindowId",
                parentWindowId
        );


        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(clickWmMobileFromMenu);

        waitForPageLoad();

        Thread.sleep(5000);


        // =========================================================
        // SWITCH TO WM MOBILE WINDOW
        // =========================================================

        for (String handle : driver.getWindowHandles()) {

            if (!handle.equals(parentWindowId)) {

                driver.switchTo().window(handle);

                break;
            }
        }

        Thread.sleep(5000);


        // =========================================================
        // OPEN 190 USER DIRECTED
        // =========================================================

        type(
                searchBarInWmMobile,
                "190 User Directed");
        searchAndWaitForNextElement(searchBarInWmMobile, "190 User Directed", clickUD);

        Thread.sleep(3000);


        click(clickUD);

        Thread.sleep(4000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered 190 Putaway Transaction"
        );
        type(
                scanContainer,
                palletId);
        Thread.sleep(2000);
        pressEnter(scanContainer);

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Scanned the Pallet ID "
                        + palletId
                        + " in 190 User Directed Transaction"
        );


        System.out.println(
                "Scanned Pallet ID: " + palletId
        );


        // =========================================================
        // SCAN STORAGE LOCATION
        // =========================================================

        type(
                scanLocUD,
                location);

        Thread.sleep(2000);
        report.addReportStepWithScreenshot(StepStatus.PASS, "Putaway Details Entered");
        pressEnter(scanLocUD);
        Thread.sleep(10000);
        handleDifferentItemDedicatedWarning();
//        report.addReportStepWithScreenshot(
//                StepStatus.PASS,
//                "User Scanned the Location "
//                        + location
//                        + " in 190 User Directed Transaction"
//        );


        // =========================================================
        // CLOSE WM MOBILE
        // =========================================================

        driver.close();
        driver.switchTo().window(parentWindowId);
        System.out.println(
                "Returned to parent window after Pallet Putaway"
        );

        System.out.println("======================================");
        System.out.println("PALLET PUTAWAY COMPLETED");
        System.out.println(
                "Pallet ID: "
                        + palletId
                        + " | Location: "
                        + location
        );
        System.out.println("======================================");


        // =========================================================
        // GET ALL CREATED ASN DATA
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>)
                        ScenarioContext.get(
                                "CreatedASNData"
                        );

        if (createdASNData == null
                || createdASNData.isEmpty()) {

            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext"
            );
        }


        // =========================================================
        // VERIFY EACH ASN
        // =========================================================

        for (ASNData asnData : createdASNData) {

            String asnId =
                    asnData.getAsn();

            if (asnId == null || asnId.isBlank()) {

                throw new IllegalStateException(
                        "ASN ID is null or empty in ASNData"
                );
            }

            asnId = asnId.trim();


            // =====================================================
            // GET ILPNs FOR CURRENT ASN
            // =====================================================

            List<ILPNData> ilpnDataList =
                    asnData.getIlpns();

            if (ilpnDataList == null
                    || ilpnDataList.isEmpty()) {

                throw new IllegalStateException(
                        "No ILPNs found for ASN: "
                                + asnId
                );
            }


            // =====================================================
            // CREATE ILPN STRING LIST
            // =====================================================

            List<String> ilpns =
                    new ArrayList<>();

            Map<String, String> ilpnLocationMap =
                    getIlpnLocationMap();


            for (ILPNData ilpnData : ilpnDataList) {

                String ilpn =
                        ilpnData.getIlpn();

                if (ilpn == null || ilpn.isBlank()) {

                    throw new IllegalStateException(
                            "ILPN is null or empty for ASN: "
                                    + asnId
                    );
                }

                ilpn = ilpn.trim();

                ilpns.add(ilpn);


                // =================================================
                // STORE ILPN -> PUTAWAY LOCATION
                // =================================================

                ilpnLocationMap.put(
                        ilpn,
                        location
                );

                System.out.println(
                        "Stored ILPN Location: "
                                + ilpn
                                + " -> "
                                + location
                );
            }


            // =====================================================
            // NAVIGATE BACK TO ASN UI
            //
            // IMPORTANT:
            // verifyILPNLocation() expects to start from ASN LIST.
            // It will itself:
            //
            //     Click ASN
            //     -> Related Links
            //     -> LPN Inventory
            //     -> Refresh
            //     -> Validate Location
            // =====================================================

            System.out.println(
                    "Navigating to ASN UI for location verification: "
                            + asnId
            );


            // =====================================================
            // MENU TOGGLE
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);


            // =====================================================
            // OPEN ASNs UI
            // =====================================================

            type(
                    searchBarInLandingPage,
                    "ASNs");

            Thread.sleep(3000);


            click(clickAsnSFromMenu);

            Thread.sleep(4000);


            // =====================================================
            // FILTER CURRENT ASN
            // =====================================================

            type(
                    filterAsnById,
                    asnId);

            Thread.sleep(2000);


            pressEnter(filterAsnById);

            Thread.sleep(3000);


            click(refresh);

            Thread.sleep(3000);


            // =====================================================
            // START ILPN LOCATION VERIFICATION
            // =====================================================

            System.out.println(
                    "STARTING PALLET ILPN VERIFICATION"
            );

            System.out.println(
                    "ASN: " + asnId
            );

            System.out.println(
                    "Pallet ID: " + palletId
            );

            System.out.println(
                    "Putaway Location: " + location
            );

            System.out.println(
                    "ILPN Count: " + ilpns.size()
            );


            AsnPage asnPage =
                    new AsnPage();


            // =====================================================
            // EXISTING METHOD WILL DO:
            //
            // Click ASN
            // -> Related Links
            // -> LPN Inventory
            // -> Refresh
            // -> Validate ILPN Location
            // =====================================================

            asnPage.verifyILPNLocation(
                    asnId,
                    ilpns
            );
        }
//        report.addReportStepWithScreenshot(
//                StepStatus.PASS,
//                "Pallet Putaway and ILPN Verification completed "
//                        + "for Pallet: "
//                        + palletId
//                        + " | Location: "
//                        + location
//        );
    }



    public void palletizeILPN() throws InterruptedException {

        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(menuToggleButton);
        Thread.sleep(4000);

        type(searchBarInLandingPage, "WM Mobile");

        // =========================================================
        // STORE PARENT WINDOW
        // =========================================================

        String parentWindowId = driver.getWindowHandle();

        ScenarioContext.set(
                "parentWindowId",
                parentWindowId
        );

        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(clickWmMobileFromMenu);

        waitForPageLoad();

        Thread.sleep(5000);

        // =========================================================
        // SWITCH TO WM MOBILE WINDOW
        // =========================================================

        boolean childWindowFound = false;

        for (String handle : driver.getWindowHandles()) {

            if (!handle.equals(parentWindowId)) {

                driver.switchTo().window(handle);

                childWindowFound = true;

                break;
            }
        }

        if (!childWindowFound) {

            throw new IllegalStateException(
                    "WM Mobile child window was not opened for Palletize ILPN"
            );
        }

        Thread.sleep(5000);

        // =========================================================
        // OPEN PALLETIZE ILPN
        // =========================================================

//        type(
//                searchBarInWmMobile,
//                "palletize ilpn"
//        );
        searchAndWaitForNextElement(searchBarInWmMobile, "palletize ilpn", clickPalletizeILPN);

        Thread.sleep(3000);

        click(clickPalletizeILPN);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered Palletize ILPN Transaction"
        );

        Thread.sleep(4000);

        // =========================================================
        // GET CREATED ASN DATA
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>)
                        ScenarioContext.get("CreatedASNData");

        if (createdASNData == null
                || createdASNData.isEmpty()) {

            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext"
            );
        }

        // Keep a separate pallet ID for each ASN for multi-ASN processing.
        Map<String, String> palletIdsByAsn = new LinkedHashMap<>();
        ScenarioContext.set("CreatedPalletIdsByASN", palletIdsByAsn);

        Thread.sleep(5000);

        System.out.println(
                "Total ASNs for Palletize: "
                        + createdASNData.size()
        );

        // =========================================================
        // PROCESS EACH ASN
        // =========================================================

        for (ASNData asnData : createdASNData) {

            // Generate and confirm a fresh pallet for each ASN.
            click(generatePallet);
            Thread.sleep(4000);
            click(confirmPrinter);
            Thread.sleep(5000);

            String asnId = asnData.getAsn();

            if (asnId == null || asnId.isBlank()) {

                throw new IllegalStateException(
                        "ASN ID is null or empty during Palletize ILPN"
                );
            }

            // =====================================================
            // PROCESS ALL ILPNs FOR CURRENT ASN
            // =====================================================

            List<ILPNData> ilpns =
                    asnData.getIlpns();

            if (ilpns == null || ilpns.isEmpty()) {

                throw new IllegalStateException(
                        "No ILPNs found for ASN: "
                                + asnId
                );
            }

            for (ILPNData ilpnData : ilpns) {

                Thread.sleep(3000);

                String ilpn =
                        ilpnData.getIlpn();

                if (ilpn == null || ilpn.isBlank()) {

                    throw new IllegalStateException(
                            "ILPN is null or empty for ASN: "
                                    + asnId
                    );
                }

                System.out.println(
                        "Processing ILPN: "
                                + ilpn
                );

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "Processing ILPN: "
                                + ilpn
                                + " for ASN: "
                                + asnId
                );

                // =================================================
                // SCAN ILPN
                // =================================================

                type(
                        scanILPN,
                        ilpn
                );

                Thread.sleep(3000);

                pressEnter(scanILPN);
            }

            // =====================================================
            // GET GENERATED PALLET ID
            // =====================================================

            String palletId =
                    getPalletFromPalletizeTransaction
                            .getText()
                            .trim();

            if (palletId.isBlank()) {

                throw new IllegalStateException(
                        "Pallet ID could not be fetched after "
                                + "Palletize ILPN for ASN: "
                                + asnId
                );
            }

            // =====================================================
            // STORE PALLET ID
            // =====================================================

            palletIdsByAsn.put(asnId.trim(), palletId);
            ScenarioContext.set("CreatedPalletIdsByASN", palletIdsByAsn);

            // Preserve the legacy key for existing single-ASN scenarios.
            ScenarioContext.set(
                    "CreatedPalletId",
                    palletId
            );

            System.out.println(
                    "Generated Pallet ID: "
                            + palletId
            );

            System.out.println(
                    "Stored CreatedPalletId in ScenarioContext: "
                            + ScenarioContext.get("CreatedPalletId")
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Pallet ID from Palletize ILPN: "
                            + palletId
            );

            Thread.sleep(2000);

            // =====================================================
            // END PALLET
            // =====================================================

            click(endPallet);

            Thread.sleep(3000);

            // =====================================================
            // IMPORTANT
            //
            // DO NOT VALIDATE PARENT LPN WHILE STILL IN WM MOBILE.
            //
            // The ASN / LPN Inventory UI is in the parent window.
            // =====================================================
        }

        // =========================================================
        // CLOSE WM MOBILE
        // =========================================================

        driver.close();

        // =========================================================
        // RETURN TO PARENT WINDOW
        // =========================================================

        driver.switchTo().window(parentWindowId);

        Thread.sleep(3000);

        System.out.println(
                "Returned to parent window after Palletize ILPN"
        );

        // Validate from the parent window after palletization.
        if (createdASNData.size() == 1) {
            verifyPalletizedILPNParentLpn();
        } else {
            verifyMultiAsnPalletizedILPNParentLpn();
        }
    }

    // =========================================================
    // GET ILPN LOCATION MAP
    // =========================================================

    @SuppressWarnings("unchecked")
    private Map<String, String> getIlpnLocationMap() {

        Map<String, String> ilpnLocationMap = (Map<String, String>) ScenarioContext.get("ILPN_LOCATION_MAP");

        if (ilpnLocationMap == null) {

            ilpnLocationMap = new HashMap<>();

            ScenarioContext.set("ILPN_LOCATION_MAP", ilpnLocationMap);
        }

        return ilpnLocationMap;
    }

    private void storeIlpnLocation(String ilpn, String location) {

        if (ilpn == null || ilpn.isBlank()) {

            throw new IllegalArgumentException("ILPN cannot be null or empty");
        }

        if (location == null || location.isBlank()) {

            throw new IllegalArgumentException("Location cannot be null or empty for ILPN: " + ilpn);
        }

        Map<String, String> ilpnLocationMap = getIlpnLocationMap();

        ilpnLocationMap.put(ilpn.trim(), location.trim());

        ScenarioContext.set("ILPN_LOCATION_MAP", ilpnLocationMap);

        System.out.println("Stored ILPN Location: " + ilpn + " -> " + location);
    }

    public void vendorSpecificReceiving()
            throws InterruptedException {

        // =========================================================
        // GET VENDOR
        // =========================================================

        Object vendorObject =
                ScenarioContext.get("VendorName");

        if (vendorObject == null) {
            throw new IllegalStateException(
                    "VendorName not found in ScenarioContext"
            );
        }

        String vendorName =
                vendorObject.toString().trim();

        if (vendorName.isBlank()) {
            throw new IllegalStateException(
                    "VendorName is empty in ScenarioContext"
            );
        }

        System.out.println(
                "======================================"
        );

        System.out.println(
                "VENDOR SPECIFIC RECEIVING"
        );

        System.out.println(
                "Vendor: " + vendorName
        );

        System.out.println(
                "======================================"
        );

        // =========================================================
        // GET ALL CREATED ASN DATA
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>)
                        ScenarioContext.get(
                                "CreatedASNData"
                        );

        if (createdASNData == null
                || createdASNData.isEmpty()) {

            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext"
            );
        }

        System.out.println(
                "Total ASNs to Receive: "
                        + createdASNData.size()
        );

        // =========================================================
        // PROCESS EACH ASN
        // =========================================================

        for (ASNData asnData :
                createdASNData) {

            String asnId =
                    asnData.getAsn();

            List<ItemData> items =
                    asnData.getItems();

            if (asnId == null
                    || asnId.isBlank()) {

                throw new IllegalStateException(
                        "ASN ID cannot be null or empty"
                );
            }

            if (items == null
                    || items.isEmpty()) {

                throw new IllegalStateException(
                        "No items found for ASN: "
                                + asnId
                );
            }

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Processing Vendor ASN: "
                            + asnId
            );

            System.out.println(
                    "Vendor: "
                            + vendorName
            );

            System.out.println(
                    "Items: "
                            + items.size()
            );

            System.out.println(
                    "======================================"
            );

            // =====================================================
            // OPEN WM MOBILE
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);

            type(
                    searchBarInLandingPage,
                    "WM Mobile");

            String parentWindowId =
                    driver.getWindowHandle();

            ScenarioContext.set(
                    "parentWindowId",
                    parentWindowId
            );

            click(clickWmMobileFromMenu);

            waitForPageLoad();

            Thread.sleep(5000);

            // =====================================================
            // SWITCH TO WM MOBILE
            // =====================================================

            for (String handle :
                    driver.getWindowHandles()) {

                if (!handle.equals(
                        parentWindowId)) {

                    driver.switchTo()
                            .window(handle);

                    break;
                }
            }

            Thread.sleep(3000);

            // =====================================================
            // OPEN 190 RECEIVING
            // =====================================================

//            type(
//                    searchBarInWmMobile,
//                    "190 Receiving");
            searchAndWaitForNextElement(searchBarInWmMobile, "190 Receiving", click190Receiving);

            Thread.sleep(3000);

            click(click190Receiving);

            Thread.sleep(2000);

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "User Entered 190 Receiving Transaction"
            );

            // =====================================================
            // ENTER ASN
            // =====================================================

            type(
                    passAsnIdReceive,
                    asnId);

            pressEnter(passAsnIdReceive);

            Thread.sleep(3000);

            // =====================================================
            // VENDOR-SPECIFIC PALLET FLOW
            //
            // Business rule:
            // Vendor-specific receiving uses:
            //
            // ASN
            //   ↓
            // Pallet
            //   ↓
            // LPN
            //   ↓
            // Item
            //   ↓
            // Units
            //   ↓
            // End LPN
            //   ↓
            // End Pallet
            // =====================================================

            // =====================================================
            // GENERATE PALLET
            // =====================================================

            pressEnter(scanPallet);

            Thread.sleep(3000);

            String palletId =
                    getPallet.getText();

            if (palletId == null
                    || palletId.isBlank()) {

                throw new IllegalStateException(
                        "Pallet ID could not be fetched for ASN: "
                                + asnId
                );
            }

            palletId =
                    palletId.trim();

            ScenarioContext.set(
                    "CreatedPalletId",
                    palletId
            );

            System.out.println(
                    "Generated Pallet ID: "
                            + palletId
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Generated Pallet ID: "
                            + palletId
            );

            // =====================================================
            // PROCESS ITEMS
            // =====================================================

            for (ItemData itemData :
                    items) {

                String item =
                        itemData.getItem();

                String quantity =
                        itemData.getShippedQty();

                System.out.println(
                        "--------------------------------------"
                );

                System.out.println(
                        "ASN: "
                                + asnId
                                + " | Vendor: "
                                + vendorName
                                + " | Item: "
                                + item
                                + " | Quantity: "
                                + quantity
                );

                System.out.println(
                        "--------------------------------------"
                );

                // =================================================
                // GENERATE LPN
                // =================================================

                pressEnter(passLpnIdReceive);

                Thread.sleep(2000);

                String lpnId =
                        storeLpnIdFromWm.getText();

                if (lpnId == null
                        || lpnId.isBlank()) {

                    throw new IllegalStateException(
                            "LPN ID could not be generated for ASN: "
                                    + asnId
                    );
                }

                lpnId =
                        lpnId.trim();

                System.out.println(
                        "Generated LPN: "
                                + lpnId
                );

                // =================================================
                // ENTER ITEM
                // =================================================

                type(
                        passItemReceive,
                        item);

                pressEnter(passItemReceive);

                Thread.sleep(4000);

                // =================================================
                // ENTER UNITS
                // =================================================

                type(
                        passQtyReceive,
                        quantity);

                Thread.sleep(2000);

                pressEnter(passQtyReceive);

                Thread.sleep(3000);

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "Received Item: "
                                + item
                                + " | Quantity: "
                                + quantity
                                + " | LPN: "
                                + lpnId
                );

                // =================================================
                // END LPN
                // =================================================

                click(endLPNReceive);

                Thread.sleep(3000);

                System.out.println(
                        "Completed LPN: "
                                + lpnId
                );

                // =====================================================
                // STORE GENERATED LPN AND ITEM IN ASN DATA
                //
                // palletPutaway() uses CreatedASNData to verify the
                // ILPN locations after the pallet putaway transaction.
                // Register the generated LPN here so that verification
                // can retrieve it from asnData.getIlpns().
                // =====================================================

                ILPNData ilpnData = new ILPNData(lpnId);
                ilpnData.addItem(itemData);
                asnData.addIlpn(ilpnData);

                System.out.println(
                        "Stored ILPN in ASNData: "
                                + lpnId
                                + " | ASN: "
                                + asnId
                                + " | Item: "
                                + item
                                + " | Total ILPNs for ASN: "
                                + asnData.getIlpns().size()
                );
            }

            // =====================================================
            // END PALLET
            // =====================================================

            Thread.sleep(2000);

            click(endPallet);

            Thread.sleep(3000);

            System.out.println(
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );

            // =====================================================
            // CLOSE WM MOBILE
            // =====================================================

            driver.close();

            driver.switchTo()
                    .window(parentWindowId);

            // =====================================================
            // RETURN TO ASN PAGE
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(4000);

            type(
                    searchBarInLandingPage,
                    "ASNs");

            Thread.sleep(3000);

            click(clickAsnSFromMenu);

            Thread.sleep(3000);

            // =====================================================
            // FILTER ASN
            // =====================================================

            type(
                    filterAsnById,
                    asnId);

            Thread.sleep(2000);

            pressEnter(filterAsnById);

            Thread.sleep(2000);

            click(refresh);

            // =====================================================
            // WAIT FOR RECEIVING STATUS
            // =====================================================

            waitForStatus(
                    asnStatusValidation,
                    () -> click(refresh),
                    "In Receiving"
            );

            // =====================================================
            // VERIFY ASN
            // =====================================================

            AsnPage asnPage =
                    new AsnPage();

            asnPage.verifyASN(asnId);

            System.out.println(
                    "ASN Verification Completed: "
                            + asnId
            );
        }

        // =========================================================
        // ALL ASNs COMPLETED
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "VENDOR SPECIFIC RECEIVING COMPLETED"
        );

        System.out.println(
                "Vendor: "
                        + vendorName
        );

        System.out.println(
                "Total ASNs: "
                        + createdASNData.size()
        );

        System.out.println(
                "======================================"
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Vendor Specific Receiving Completed for Vendor: "
                        + vendorName
        );
    }
    public void vendorSpecificReceiving(
            String asnInput,
            String itemInput,
            String unitsInput)
            throws InterruptedException {
        if (asnInput == null || asnInput.isBlank()) {
            throw new IllegalArgumentException(
                    "ASN cannot be empty"
            );
        }

        if (itemInput == null || itemInput.isBlank()) {
            throw new IllegalArgumentException(
                    "Item cannot be empty for ASN: " + asnInput
            );
        }

        if (unitsInput == null || unitsInput.isBlank()) {
            throw new IllegalArgumentException(
                    "Units cannot be empty for ASN: " + asnInput
            );
        }

        String asnId = asnInput.trim();


        // =========================================================
        // SPLIT ITEMS
        // =========================================================

        String[] itemArray =
                itemInput.split(",");
        String[] quantityArray =
                unitsInput.split(",");


        // =========================================================
        // VALIDATE ITEM / QUANTITY COUNT
        // =========================================================

        if (itemArray.length != quantityArray.length) {

            throw new IllegalArgumentException(
                    "Item and Units count must be the same for ASN: "
                            + asnId
                            + ". Items="
                            + itemArray.length
                            + ", Units="
                            + quantityArray.length
            );
        }


        // =========================================================
        // CREATE / GET CREATED ASN DATA
        //
        // We need CreatedASNData because both:
        //
        //     putaway()
        //
        // and
        //
        //     palletPutaway()
        //
        // read ILPN information from it.
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get(
                        "CreatedASNData"
                );


        if (createdASNData == null) {

            createdASNData =
                    new ArrayList<>();
        }


        // =========================================================
        // FIND EXISTING ASN DATA
        //
        // This is important for Scenario 1.
        //
        // If ASN creation already stored the ASN in
        // CreatedASNData, we reuse that ASNData object instead
        // of creating a duplicate.
        // =========================================================

        ASNData currentAsnData = null;

        for (ASNData existingAsnData : createdASNData) {

            if (existingAsnData == null) {
                continue;
            }

            String existingAsn =
                    existingAsnData.getAsn();

            if (existingAsn != null
                    && existingAsn.equalsIgnoreCase(asnId)) {

                currentAsnData =
                        existingAsnData;

                break;
            }
        }


        // =========================================================
        // CREATE ASN DATA IF NOT ALREADY PRESENT
        // =========================================================

        if (currentAsnData == null) {

            currentAsnData =
                    new ASNData(asnId);

            createdASNData.add(
                    currentAsnData
            );
        }


        // =========================================================
        // STORE UPDATED ASN DATA
        // =========================================================

        ScenarioContext.set(
                "CreatedASNData",
                createdASNData
        );


        // =========================================================
        // START
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "STARTING VENDOR SPECIFIC RECEIVING"
        );

        System.out.println(
                "ASN       : " + asnId
        );

        System.out.println(
                "Items     : " + itemInput
        );

        System.out.println(
                "Units     : " + unitsInput
        );

        System.out.println(
                "Total Items: " + itemArray.length
        );

        System.out.println(
                "======================================"
        );


        // =========================================================
        // CLEAR STALE PALLET
        //
        // Prevent previous scenario / ASN pallet from being
        // reused.
        // =========================================================

        ScenarioContext.set(
                "CreatedPalletId",
                ""
        );


        // =========================================================
        // OPEN ASN UI
        //
        // Vendor is captured from ASN itself.
        // =========================================================

        click(menuToggleButton);

        Thread.sleep(4000);


        type(
                searchBarInLandingPage,
                "ASNs");

        Thread.sleep(3000);


        click(clickAsnSFromMenu);

        Thread.sleep(3000);


        // =========================================================
        // FILTER ASN
        // =========================================================

        type(
                filterAsnById,
                asnId);

        Thread.sleep(2000);


        pressEnter(filterAsnById);

        Thread.sleep(2000);


        click(refresh);

        Thread.sleep(3000);


        // =========================================================
        // GET VENDOR FROM ASN
        // =========================================================

        String vendorName =
                vendorId.getText();


        if (vendorName == null) {
            vendorName = "";
        }


        vendorName =
                vendorName.trim();


        // =========================================================
        // DETERMINE RECEIVING TYPE
        //
        // Vendor exists
        //      -> Pallet Receiving
        //
        // Vendor empty
        //      -> Normal Receiving
        // =========================================================

        boolean hasVendor =
                !vendorName.isBlank();


        System.out.println(
                "ASN: "
                        + asnId
                        + " | Vendor: ["
                        + vendorName
                        + "]"
        );


        System.out.println(
                "Receiving Type: "
                        + (
                        hasVendor
                                ? "PALLET RECEIVING"
                                : "NORMAL RECEIVING"
                )
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                hasVendor
                        ? "Vendor found for ASN "
                        + asnId
                        + ": "
                        + vendorName
                        + " | Pallet receiving will be performed"

                        : "No vendor found for ASN "
                        + asnId
                        + " | Normal receiving will be performed"
        );


        // =========================================================
        // PERFORM RECEIVING
        //
        // IMPORTANT:
        //
        // All items belonging to this ASN are passed together.
        // =========================================================

        receiveVendorPallet(
                asnId,
                itemArray,
                quantityArray,
                vendorName,
                hasVendor,
                currentAsnData
        );


        // =========================================================
        // STORE FINAL ASN DATA
        // =========================================================

        ScenarioContext.set(
                "CreatedASNData",
                createdASNData
        );


        // =========================================================
        // DEBUG ASN DATA
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "CREATED ASN DATA AFTER RECEIVING"
        );

        System.out.println(
                "======================================"
        );


        System.out.println(
                "ASN: "
                        + currentAsnData.getAsn()
        );


        System.out.println(
                "Items:"
        );


        for (ItemData itemData :
                currentAsnData.getItems()) {

            System.out.println(
                    "  Item: "
                            + itemData.getItem()
                            + " | Qty: "
                            + itemData.getShippedQty()
            );
        }


        System.out.println(
                "ILPNs:"
        );


        for (ILPNData ilpnData :
                currentAsnData.getIlpns()) {

            System.out.println(
                    "  ILPN: "
                            + ilpnData.getIlpn()
            );

            for (ItemData itemData :
                    ilpnData.getItems()) {

                System.out.println(
                        "      Item: "
                                + itemData.getItem()
                                + " | Qty: "
                                + itemData.getShippedQty()
                );
            }
        }


        System.out.println(
                "======================================"
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Vendor Specific Receiving Completed for ASN: "
                        + asnId
                        + " | Vendor: "
                        + (
                        vendorName.isBlank()
                                ? "NONE"
                                : vendorName
                )
        );
        verifyAsnAfterReceiving();
    }


    /**
     * Performs 190 Receiving for one ASN.
     *
     * One ASN can contain multiple items.
     *
     * Example:
     *
     * ASN000000027883
     *
     *     19005 -> 5
     *     19003 -> 5
     *
     * For each item:
     *
     *     Generate LPN
     *     Store LPN in ASNData
     *     Store ItemData in ILPNData
     *     Enter Item
     *     Enter Quantity
     *     End LPN
     *
     * If vendor exists:
     *
     *     Generate pallet
     *     Store CreatedPalletId
     *     End pallet
     *
     * If vendor does not exist:
     *
     *     No pallet is generated
     *     No pallet is stored
     *     No End Pallet operation
     */
    private void receiveVendorPallet(
            String asnId,
            String[] items,
            String[] quantities,
            String vendorName,
            boolean hasVendor,
            ASNData asnData)
            throws InterruptedException {


        // =========================================================
        // VALIDATION
        // =========================================================

        if (items == null || quantities == null) {

            throw new IllegalArgumentException(
                    "Items and quantities cannot be null for ASN: "
                            + asnId
            );
        }


        if (items.length != quantities.length) {

            throw new IllegalArgumentException(
                    "Items and quantities count mismatch for ASN: "
                            + asnId
                            + " | Items="
                            + items.length
                            + " | Quantities="
                            + quantities.length
            );
        }


        if (items.length == 0) {

            throw new IllegalArgumentException(
                    "No items provided for ASN: "
                            + asnId
            );
        }


        // =========================================================
        // START
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "STARTING 190 RECEIVING"
        );

        System.out.println(
                "ASN       : " + asnId
        );

        System.out.println(
                "Vendor    : " + vendorName
        );

        System.out.println(
                "Item Count: " + items.length
        );

        System.out.println(
                "Pallet Flow: "
                        + (
                        hasVendor
                                ? "YES"
                                : "NO"
                )
        );

        System.out.println(
                "======================================"
        );


        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(menuToggleButton);

        Thread.sleep(4000);


        type(
                searchBarInLandingPage,
                "WM Mobile");


        // =========================================================
        // STORE PARENT WINDOW
        // =========================================================

        String parentWindowId =
                driver.getWindowHandle();


        ScenarioContext.set(
                "parentWindowId",
                parentWindowId
        );


        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(clickWmMobileFromMenu);


        waitForPageLoad();

        Thread.sleep(5000);


        // =========================================================
        // SWITCH TO WM MOBILE
        // =========================================================

        for (String handle :
                driver.getWindowHandles()) {

            if (!handle.equals(parentWindowId)) {

                driver.switchTo()
                        .window(handle);

                break;
            }
        }


        Thread.sleep(3000);


        // =========================================================
        // OPEN 190 RECEIVING
        //
        // BOTH FLOWS USE 190 RECEIVING
        // =========================================================

        searchAndWaitForNextElement(
                searchBarInWmMobile,
                "190 Receiving",
                click190Receiving
        );

        click(click190Receiving);

        Thread.sleep(2000);


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered 190 Receiving Transaction"
        );


        // =========================================================
        // ENTER ASN
        // =========================================================

        type(
                passAsnIdReceive,
                asnId);


        pressEnter(passAsnIdReceive);


        Thread.sleep(3000);


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered ASN: " + asnId
        );


        // =========================================================
        // PALLET FLOW
        //
        // ONLY WHEN VENDOR EXISTS
        // =========================================================

        String palletId = null;


        if (hasVendor) {

            // =====================================================
            // GENERATE PALLET
            // =====================================================

            pressEnter(scanPallet);


            Thread.sleep(3000);


            // =====================================================
            // GET PALLET ID
            // =====================================================

            palletId =
                    getPallet.getText();


            if (palletId == null
                    || palletId.isBlank()) {

                throw new IllegalStateException(
                        "Pallet ID could not be generated for ASN: "
                                + asnId
                );
            }


            palletId =
                    palletId.trim();


            // =====================================================
            // STORE PALLET ID
            // =====================================================

            ScenarioContext.set(
                    "CreatedPalletId",
                    palletId
            );


            System.out.println(
                    "Generated Pallet ID: "
                            + palletId
            );


            System.out.println(
                    "Stored CreatedPalletId: "
                            + ScenarioContext.get(
                            "CreatedPalletId"
                    )
            );


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Generated Pallet ID: "
                            + palletId
            );
        }


        // =========================================================
        // PROCESS EVERY ITEM
        //
        // IMPORTANT:
        //
        // One item = one generated LPN
        //
        // Example:
        //
        // 19005 -> LPN001 -> Qty 5
        // 19003 -> LPN002 -> Qty 5
        // =========================================================

        for (int i = 0;
             i < items.length;
             i++) {


            String item =
                    items[i].trim();


            String quantity =
                    quantities[i].trim();


            // =====================================================
            // VALIDATE ITEM
            // =====================================================

            if (item.isBlank()) {

                throw new IllegalArgumentException(
                        "Item cannot be empty at index "
                                + i
                                + " for ASN: "
                                + asnId
                );
            }


            // =====================================================
            // VALIDATE QUANTITY
            // =====================================================

            if (quantity.isBlank()) {

                throw new IllegalArgumentException(
                        "Quantity cannot be empty for Item: "
                                + item
                                + " | ASN: "
                                + asnId
                );
            }


            System.out.println(
                    "--------------------------------------"
            );

            System.out.println(
                    "Processing Item "
                            + (i + 1)
                            + " of "
                            + items.length
            );

            System.out.println(
                    "Item     : " + item
            );

            System.out.println(
                    "Quantity : " + quantity
            );

            System.out.println(
                    "--------------------------------------"
            );


            // =====================================================
            // CREATE ITEM DATA
            //
            // This object is shared between:
            //
            // ASNData
            // ILPNData
            // =====================================================

            ItemData itemData =
                    new ItemData(
                            item,
                            quantity
                    );


            // =====================================================
            // ADD ITEM TO ASN
            // =====================================================

            asnData.addItem(
                    itemData
            );


            // =====================================================
            // GENERATE LPN
            // =====================================================

            pressEnter(passLpnIdReceive);


            Thread.sleep(2000);


            // =====================================================
            // GET GENERATED LPN
            // =====================================================

            String lpnId =
                    storeLpnIdFromWm.getText();


            if (lpnId == null
                    || lpnId.isBlank()) {

                throw new IllegalStateException(
                        "LPN ID could not be generated for ASN: "
                                + asnId
                                + " | Item: "
                                + item
                );
            }


            lpnId =
                    lpnId.trim();


            System.out.println(
                    "Generated LPN: "
                            + lpnId
                            + " | Item: "
                            + item
            );


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Generated LPN: "
                            + lpnId
                            + " for Item: "
                            + item
            );


            // =====================================================
            // CREATE ILPN DATA
            // =====================================================

            ILPNData ilpnData =
                    new ILPNData(
                            lpnId
                    );


            // =====================================================
            // ADD ITEM TO ILPN
            // =====================================================

            ilpnData.addItem(
                    itemData
            );


            // =====================================================
            // ADD ILPN TO ASN
            // =====================================================

            asnData.addIlpn(
                    ilpnData
            );


            // =====================================================
            // OPTIONAL INDIVIDUAL LPN CONTEXT
            // =====================================================

            ScenarioContext.set(
                    "Created LPN "
                            + (i + 1),
                    lpnId
            );


            // =====================================================
            // ENTER ITEM
            // =====================================================

            type(
                    passItemReceive,
                    item);


            pressEnter(passItemReceive);


            Thread.sleep(4000);


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "User Entered Item: "
                            + item
                            + " for LPN: "
                            + lpnId
            );


            // =====================================================
            // ENTER QUANTITY
            // =====================================================

            type(
                    passQtyReceive,
                    quantity);


            Thread.sleep(2000);


            pressEnter(passQtyReceive);


            Thread.sleep(3000);


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "User Entered Quantity: "
                            + quantity
                            + " for Item: "
                            + item
            );


            // =====================================================
            // END LPN
            // =====================================================

            click(endLPNReceive);


            Thread.sleep(3000);


            System.out.println(
                    "Completed LPN: "
                            + lpnId
                            + " | Item: "
                            + item
                            + " | Quantity: "
                            + quantity
            );


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Completed LPN: "
                            + lpnId
                            + " | Item: "
                            + item
                            + " | Quantity: "
                            + quantity
            );
        }


        // =========================================================
        // END PALLET
        //
        // ONLY WHEN VENDOR EXISTS
        // =========================================================

        if (hasVendor) {

            Thread.sleep(2000);


            click(endPallet);


            Thread.sleep(3000);


            System.out.println(
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );

        } else {

            System.out.println(
                    "No vendor found for ASN: "
                            + asnId
                            + " | Pallet flow skipped"
            );
        }


        // =========================================================
        // STORE ASN DATA AGAIN
        //
        // This guarantees the latest ILPN information is
        // available to Putaway.
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> finalASNData =
                (List<ASNData>) ScenarioContext.get(
                        "CreatedASNData"
                );


        if (finalASNData == null) {

            finalASNData =
                    new ArrayList<>();

            finalASNData.add(
                    asnData
            );
        }


        ScenarioContext.set(
                "CreatedASNData",
                finalASNData
        );


        // =========================================================
        // CLOSE WM MOBILE
        // =========================================================

        driver.close();


        // =========================================================
        // RETURN TO PARENT WINDOW
        // =========================================================

        driver.switchTo()
                .window(parentWindowId);


        // =========================================================
        // FINAL LOG
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "RECEIVING COMPLETED"
        );

        System.out.println(
                "ASN    : " + asnId
        );

        System.out.println(
                "Vendor : "
                        + (
                        vendorName.isBlank()
                                ? "NONE"
                                : vendorName
                )
        );

        System.out.println(
                "Items  : " + items.length
        );

        System.out.println(
                "Pallet : "
                        + (
                        hasVendor
                                ? palletId
                                : "NOT CREATED"
                )
        );

        System.out.println(
                "ILPNs  : "
                        + asnData.getIlpns().size()
        );

        System.out.println(
                "======================================"
        );
    }



    private void receiveVendorPallet(
            String asnId,
            String item,
            String quantity,
            String vendorName,
            boolean hasVendor)
            throws InterruptedException {

        // =========================================================
        // START
        // =========================================================

        System.out.println("======================================");
        System.out.println("STARTING RECEIVING");
        System.out.println("ASN       : " + asnId);
        System.out.println("Vendor    : " + vendorName);
        System.out.println("Item      : " + item);
        System.out.println("Units     : " + quantity);
        System.out.println(
                "Pallet Flow: "
                        + (hasVendor ? "YES" : "NO")
        );
        System.out.println("======================================");

        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(menuToggleButton);

        Thread.sleep(4000);

        type(
                searchBarInLandingPage,
                "WM Mobile");

        // =========================================================
        // STORE PARENT WINDOW
        // =========================================================

        String parentWindowId =
                driver.getWindowHandle();

        ScenarioContext.set(
                "parentWindowId",
                parentWindowId
        );

        // =========================================================
        // OPEN WM MOBILE
        // =========================================================

        click(clickWmMobileFromMenu);

        waitForPageLoad();

        Thread.sleep(5000);

        // =========================================================
        // SWITCH TO WM MOBILE WINDOW
        // =========================================================

        for (String handle :
                driver.getWindowHandles()) {

            if (!handle.equals(parentWindowId)) {

                driver.switchTo()
                        .window(handle);

                break;
            }
        }

        Thread.sleep(3000);

        // =========================================================
        // OPEN 190 RECEIVING
        //
        // BOTH FLOWS USE 190 RECEIVING
        // =========================================================

        searchAndWaitForNextElement(
                searchBarInWmMobile,
                "190 Receiving",
                click190Receiving
        );

        click(click190Receiving);

        Thread.sleep(2000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered 190 Receiving Transaction"
        );

        // =========================================================
        // ENTER ASN
        // =========================================================

        type(
                passAsnIdReceive,
                asnId);

        pressEnter(passAsnIdReceive);

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered ASN: " + asnId
        );

        // =========================================================
        // PALLET FLOW
        //
        // ONLY WHEN VENDOR EXISTS
        // =========================================================

        String palletId = null;

        if (hasVendor) {

            // =====================================================
            // GENERATE PALLET
            // =====================================================

            pressEnter(scanPallet);

            Thread.sleep(3000);

            // =====================================================
            // GET PALLET ID
            // =====================================================

            palletId =
                    getPallet.getText();

            if (palletId == null ||
                    palletId.isBlank()) {

                throw new IllegalStateException(
                        "Pallet ID could not be generated for ASN: "
                                + asnId
                );
            }

            palletId =
                    palletId.trim();

            // =====================================================
            // STORE PALLET ID
            // =====================================================

            ScenarioContext.set(
                    "CreatedPalletId",
                    palletId
            );

            System.out.println(
                    "Generated Pallet ID: "
                            + palletId
            );

            System.out.println(
                    "Stored CreatedPalletId: "
                            + ScenarioContext.get(
                            "CreatedPalletId"
                    )
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Generated Pallet ID: "
                            + palletId
            );
        }

        // =========================================================
        // GENERATE LPN
        // =========================================================

        pressEnter(passLpnIdReceive);

        Thread.sleep(2000);

        // =========================================================
        // GET LPN ID
        // =========================================================

        String lpnId =
                storeLpnIdFromWm.getText();

        if (lpnId == null ||
                lpnId.isBlank()) {

            throw new IllegalStateException(
                    "LPN ID could not be generated for ASN: "
                            + asnId
            );
        }

        lpnId =
                lpnId.trim();

        System.out.println(
                "Generated LPN: " + lpnId
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Generated LPN: " + lpnId
        );

        // =========================================================
        // ENTER ITEM
        // =========================================================

        type(
                passItemReceive,
                item);

        pressEnter(passItemReceive);

        Thread.sleep(4000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered Item: " + item
        );

        // =========================================================
        // ENTER QUANTITY
        // =========================================================

        type(
                passQtyReceive,
                quantity);

        Thread.sleep(2000);

        pressEnter(passQtyReceive);

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User Entered Quantity: "
                        + quantity
        );

        // =========================================================
        // END LPN
        // =========================================================

        click(endLPNReceive);

        Thread.sleep(3000);

        System.out.println(
                "Completed LPN: " + lpnId
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Completed LPN: "
                        + lpnId
                        + " | Item: "
                        + item
                        + " | Quantity: "
                        + quantity
        );

        // =========================================================
        // END PALLET
        //
        // ONLY WHEN VENDOR EXISTS
        // =========================================================

        if (hasVendor) {

            Thread.sleep(2000);

            click(endPallet);

            Thread.sleep(3000);

            System.out.println(
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Completed Pallet: "
                            + palletId
                            + " for ASN: "
                            + asnId
            );
        } else {

            System.out.println(
                    "No vendor found for ASN: "
                            + asnId
                            + " | Pallet flow skipped"
            );
        }

        // =========================================================
        // CLOSE WM MOBILE
        // =========================================================

        driver.close();

        // =========================================================
        // RETURN TO PARENT WINDOW
        // =========================================================

        driver.switchTo()
                .window(parentWindowId);

        System.out.println("======================================");
        System.out.println(
                "RECEIVING COMPLETED"
        );
        System.out.println("ASN    : " + asnId);
        System.out.println("Vendor : " + vendorName);
        System.out.println("LPN    : " + lpnId);
        System.out.println(
                "Pallet : "
                        + (hasVendor
                        ? palletId
                        : "NOT CREATED")
        );
        System.out.println("======================================");
    }
    public void performPutaway()
            throws InterruptedException {

        // =========================================================
        // MULTI-ASN PALLET FLOW
        // =========================================================
        // Each ASN has its own generated pallet. Process each mapping
        // entry separately; CreatedPalletId is only the legacy single-ASN key.

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get("CreatedASNData");

        @SuppressWarnings("unchecked")
        Map<String, String> palletIdsByAsn =
                (Map<String, String>) ScenarioContext.get("CreatedPalletIdsByASN");

        if (createdASNData != null && createdASNData.size() > 1
                && palletIdsByAsn != null && !palletIdsByAsn.isEmpty()) {
            System.out.println("Multiple ASNs and pallet mappings detected. Starting per-ASN pallet putaway.");
            multiAsnPalletPutaway();
            return;
        }

        // =========================================================
        // SINGLE-ASN / LEGACY PALLET FLOW
        // =========================================================

        Object palletObject = ScenarioContext.get("CreatedPalletId");
        String palletId = palletObject == null ? "" : palletObject.toString().trim();

        // =========================================================
        // PALLET AVAILABLE
        // =========================================================

        if (!palletId.isBlank()) {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "PALLET FOUND"
            );

            System.out.println(
                    "Pallet ID: " + palletId
            );

            System.out.println(
                    "Performing Pallet Putaway"
            );

            System.out.println(
                    "======================================"
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Pallet available: "
                            + palletId
                            + " | Performing Pallet Putaway"
            );

            palletPutaway();

        }

        // =========================================================
        // NO PALLET
        // =========================================================

        else {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "NO PALLET FOUND"
            );

            System.out.println(
                    "Performing normal User Directed Putaway"
            );

            System.out.println(
                    "======================================"
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "No pallet available | Performing normal UD-Putaway"
            );

            putaway();
        }
    }

    public void verifyAsnAfterReceiving()
            throws InterruptedException {

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>)
                        ScenarioContext.get("CreatedASNData");

        if (createdASNData == null
                || createdASNData.isEmpty()) {

            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext"
            );
        }

        AsnPage asnPage = new AsnPage();

        for (ASNData asnData : createdASNData) {

            String asnId =
                    asnData.getAsn();

            if (asnId == null || asnId.isBlank()) {
                throw new IllegalStateException(
                        "ASN ID is empty in CreatedASNData"
                );
            }

            List<String> ilpns =
                    new ArrayList<>();

            for (ILPNData ilpnData :
                    asnData.getIlpns()) {

                String ilpn =
                        ilpnData.getIlpn();

                if (ilpn == null || ilpn.isBlank()) {
                    throw new IllegalStateException(
                            "ILPN is empty for ASN: "
                                    + asnId
                    );
                }

                ilpns.add(ilpn.trim());
            }

            if (ilpns.isEmpty()) {
                throw new IllegalStateException(
                        "No ILPNs found for ASN: "
                                + asnId
                );
            }

            // =====================================================
            // OPEN ASN UI
            // =====================================================

            click(menuToggleButton);

            Thread.sleep(3000);

            type(
                    searchBarInLandingPage,
                    "ASNs");

            Thread.sleep(3000);

            click(clickAsnSFromMenu);

            Thread.sleep(4000);

            // =====================================================
            // FILTER ASN
            // =====================================================

            type(
                    filterAsnById,
                    asnId);

            pressEnter(filterAsnById);

            click(refresh);

            // =====================================================
            // STATUS = IN RECEIVING
            // =====================================================

            waitForStatus(
                    asnStatusValidation,
                    () -> click(refresh),
                    "In Receiving"
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "ASN Status validated as In Receiving"
                            + " | ASN: " + asnId
            );

            // =====================================================
            // VERIFY ASN
            // =====================================================

            asnPage.verifyASN(asnId);

            // verifyASN() already waits for Verified.
            // =====================================================

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "ASN Status validated as Verified"
                            + " | ASN: " + asnId
            );

            // =====================================================
            // PARENT LPN VALIDATION
            // =====================================================

            Object palletObject =
                    ScenarioContext.get("CreatedPalletId");

            String palletId =
                    palletObject == null
                            ? ""
                            : palletObject.toString().trim();

            if (!palletId.isBlank()) {

                System.out.println(
                        "Pallet found: "
                                + palletId
                                + " | Starting Parent LPN validation"
                );

                asnPage.verifyILPNParentLpn(
                        asnId,
                        ilpns,
                        palletId
                );

            } else {

                System.out.println(
                        "No pallet found for ASN: "
                                + asnId
                                + " | Skipping Parent LPN validation"
                );
            }
        }

        System.out.println(
                "======================================"
        );

        System.out.println(
                "ASN / LPN POST-RECEIVING VALIDATION COMPLETED"
        );

        System.out.println(
                "======================================"
        );
    }
    /**
     * Multi-ASN phase 1: palletize every ASN, then validate every ILPN
     * parent LPN against that ASN's own generated pallet. This method
     * intentionally does not perform putaway.
     */
    public void multiAsnPalletizeAndValidate() throws InterruptedException {
        // palletizeILPN() now performs parent-LPN validation itself.
        palletizeILPN();
    }

    /** Validates all ASN-to-pallet parent-LPN relationships. */
    @SuppressWarnings("unchecked")
    public void verifyMultiAsnPalletizedILPNParentLpn() throws InterruptedException {
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get("CreatedASNData");
        Map<String, String> palletIdsByAsn =
                (Map<String, String>) ScenarioContext.get("CreatedPalletIdsByASN");

        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException("No Created ASN data found for multi-ASN parent-LPN validation");
        }
        if (palletIdsByAsn == null || palletIdsByAsn.isEmpty()) {
            throw new IllegalStateException("No ASN-to-pallet mapping found in ScenarioContext");
        }

        click(menuToggleButton);
        Thread.sleep(4000);
        type(searchBarInLandingPage, "ASNs");
        Thread.sleep(3000);
        click(clickAsnSFromMenu);
        Thread.sleep(5000);

        AsnPage asnPage = new AsnPage();
        for (int i = 0; i < createdASNData.size(); i++) {
            ASNData asnData = createdASNData.get(i);
            String asnId = asnData.getAsn();
            if (asnId == null || asnId.isBlank()) {
                throw new IllegalStateException("ASN ID is null or empty during multi-ASN parent-LPN validation");
            }
            asnId = asnId.trim();
            String palletId = palletIdsByAsn.get(asnId);
            if (palletId == null || palletId.isBlank()) {
                throw new IllegalStateException("No pallet ID stored for ASN: " + asnId);
            }
            List<ILPNData> ilpnDataList = asnData.getIlpns();
            if (ilpnDataList == null || ilpnDataList.isEmpty()) {
                throw new IllegalStateException("No ILPNs found for ASN: " + asnId);
            }
            List<String> ilpns = new ArrayList<>();
            for (ILPNData ilpnData : ilpnDataList) {
                String ilpn = ilpnData.getIlpn();
                if (ilpn == null || ilpn.isBlank()) {
                    throw new IllegalStateException("ILPN is null or empty for ASN: " + asnId);
                }
                ilpns.add(ilpn.trim());
            }

            type(filterAsnById, asnId);
            Thread.sleep(2000);
            pressEnter(filterAsnById);
            Thread.sleep(3000);
            click(refresh);
            Thread.sleep(3000);

            asnPage.verifyILPNParentLpn(asnId, ilpns, palletId);

            // Return directly from LPN Inventory to ASN UI before the next ASN.
            if (i < createdASNData.size() - 1) {
                WebElement asnsLink = wait.until(
                        ExpectedConditions.elementToBeClickable(By.linkText("ASNs"))
                );
                click(asnsLink);
                waitForPageLoad();
                Thread.sleep(4000);
            }
            System.out.println("Parent LPN validation passed for ASN: " + asnId
                    + " | Expected pallet: " + palletId);
        }
        System.out.println("All multi-ASN parent-LPN validations passed.");
    }


    /**
     * Multi-ASN pallet putaway. This method must be called only after
     * multiAsnPalletizeAndValidate() has completed successfully.
     * Each ASN is put away using its own pallet ID, then its ILPN locations
     * are verified before proceeding to the next ASN.
     */
    public void multiAsnPalletPutaway() throws InterruptedException {

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get("CreatedASNData");

        @SuppressWarnings("unchecked")
        Map<String, String> palletIdsByAsn =
                (Map<String, String>) ScenarioContext.get("CreatedPalletIdsByASN");

        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException("No Created ASN data found for multi-ASN pallet putaway");
        }
        if (palletIdsByAsn == null || palletIdsByAsn.isEmpty()) {
            throw new IllegalStateException("No ASN-to-pallet mapping found. Run palletization and parent-LPN validation first.");
        }

        // Ensure the complete pallet map exists before starting any putaway.
        for (ASNData asnData : createdASNData) {
            String asnId = asnData.getAsn();
            if (asnId == null || asnId.isBlank()) {
                throw new IllegalStateException("ASN ID is null or empty in CreatedASNData");
            }
            if (asnData.getIlpns() == null || asnData.getIlpns().isEmpty()) {
                throw new IllegalStateException("No ILPNs found for ASN: " + asnId);
            }
            String palletId = palletIdsByAsn.get(asnId.trim());
            if (palletId == null || palletId.isBlank()) {
                throw new IllegalStateException("No pallet ID stored for ASN: " + asnId);
            }
        }

        String parentWindowId = driver.getWindowHandle();
        LocationDetails locationDetails = new LocationDetails();
        Map<String, String> ilpnLocationMap = getIlpnLocationMap();
        AsnPage asnPage = new AsnPage();

        for (ASNData asnData : createdASNData) {
            String asnId = asnData.getAsn().trim();
            String palletId = palletIdsByAsn.get(asnId).trim();
            String location = locationDetails.getNextReserveLocation();

            if (location == null || location.isBlank()) {
                throw new IllegalStateException("No reserve location available for pallet " + palletId + " (ASN " + asnId + ")");
            }
            location = location.trim();

            System.out.println("Starting pallet putaway | ASN: " + asnId + " | Pallet: " + palletId + " | Location: " + location);

            // Launch WM Mobile in a separate window for this pallet.
            click(menuToggleButton);
            Thread.sleep(4000);
            type(searchBarInLandingPage, "WM Mobile");
            click(clickWmMobileFromMenu);
            waitForPageLoad();
            Thread.sleep(5000);

            boolean switchedToMobile = false;
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(parentWindowId)) {
                    driver.switchTo().window(handle);
                    switchedToMobile = true;
                    break;
                }
            }
            if (!switchedToMobile) {
                throw new IllegalStateException("WM Mobile window did not open for ASN " + asnId);
            }

            Thread.sleep(3000);
            type(searchBarInWmMobile, "190 User Directed");
            searchAndWaitForNextElement(searchBarInWmMobile, "190 User Directed", clickUD);
            Thread.sleep(3000);
            click(clickUD);
            Thread.sleep(4000);

            type(scanContainer, palletId);
            Thread.sleep(2000);
            pressEnter(scanContainer);
            Thread.sleep(3000);

            type(scanLocUD, location);
            Thread.sleep(2000);
            pressEnter(scanLocUD);
            Thread.sleep(10000);
            handleDifferentItemDedicatedWarning();

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Pallet putaway completed | ASN: " + asnId + " | Pallet: " + palletId + " | Location: " + location
            );

            driver.close();
            driver.switchTo().window(parentWindowId);
            System.out.println("Returned to parent window after putaway for ASN: " + asnId);

            List<String> ilpns = new ArrayList<>();
            for (ILPNData ilpnData : asnData.getIlpns()) {
                String ilpn = ilpnData.getIlpn();
                if (ilpn == null || ilpn.isBlank()) {
                    throw new IllegalStateException("ILPN is null or empty for ASN: " + asnId);
                }
                ilpn = ilpn.trim();
                ilpns.add(ilpn);
                ilpnLocationMap.put(ilpn, location);
            }

            // Navigate to ASN list and verify only the current ASN's ILPNs.
            click(menuToggleButton);
            Thread.sleep(4000);
            type(searchBarInLandingPage, "ASNs");
            Thread.sleep(3000);
            click(clickAsnSFromMenu);
            Thread.sleep(4000);
            type(filterAsnById, asnId);
            Thread.sleep(2000);
            pressEnter(filterAsnById);
            Thread.sleep(3000);
            click(refresh);
            Thread.sleep(3000);

            asnPage.verifyILPNLocation(asnId, ilpns);
            System.out.println("ILPN location validation passed | ASN: " + asnId + " | Location: " + location);
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Multi-ASN pallet putaway and ILPN location verification completed for all " + createdASNData.size() + " ASNs"
        );
    }

    /** Runs palletization for all created ASNs, then validates all parent LPNs.
     * Putaway is intentionally a separate step so no pallet is put away unless
     * every ASN's parent-LPN validation passes.
     */


    public void verifyPalletizedILPNParentLpn()
            throws InterruptedException {

        // =========================================================
        // GET CREATED ASN DATA
        // =========================================================

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>)
                        ScenarioContext.get("CreatedASNData");

        if (createdASNData == null
                || createdASNData.isEmpty()) {

            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext "
                            + "for Parent LPN validation"
            );
        }

        // =========================================================
        // GET GENERATED PALLET ID
        // =========================================================

        Object palletObject =
                ScenarioContext.get("CreatedPalletId");

        if (palletObject == null) {

            throw new IllegalStateException(
                    "CreatedPalletId not found in ScenarioContext "
                            + "after Palletize ILPN"
            );
        }

        String palletId =
                palletObject.toString().trim();

        if (palletId.isBlank()) {

            throw new IllegalStateException(
                    "CreatedPalletId is empty after Palletize ILPN"
            );
        }

        System.out.println(
                "======================================"
        );

        System.out.println(
                "STARTING PALLETIZED ILPN PARENT LPN VALIDATION"
        );

        System.out.println(
                "Expected Pallet ID: "
                        + palletId
        );

        System.out.println(
                "Total ASNs: "
                        + createdASNData.size()
        );

        System.out.println(
                "======================================"
        );

        // =========================================================
        // OPEN ASN UI
        // =========================================================

        click(menuToggleButton);

        Thread.sleep(4000);

        type(
                searchBarInLandingPage,
                "ASNs"
        );

        Thread.sleep(3000);

        click(clickAsnSFromMenu);

        Thread.sleep(5000);

        // =========================================================
        // VERIFY EACH ASN
        // =========================================================

        for (ASNData asnData : createdASNData) {

            String asnId =
                    asnData.getAsn();

            if (asnId == null || asnId.isBlank()) {

                throw new IllegalStateException(
                        "ASN ID is null or empty during "
                                + "Parent LPN validation"
                );
            }

            asnId = asnId.trim();

            // =====================================================
            // GET ILPNs FOR CURRENT ASN
            // =====================================================

            List<ILPNData> ilpnDataList =
                    asnData.getIlpns();

            if (ilpnDataList == null
                    || ilpnDataList.isEmpty()) {

                throw new IllegalStateException(
                        "No ILPNs found for ASN: "
                                + asnId
                );
            }

            List<String> ilpns =
                    new ArrayList<>();

            for (ILPNData ilpnData : ilpnDataList) {

                String ilpn =
                        ilpnData.getIlpn();

                if (ilpn == null || ilpn.isBlank()) {

                    throw new IllegalStateException(
                            "ILPN is null or empty for ASN: "
                                    + asnId
                    );
                }

                ilpns.add(
                        ilpn.trim()
                );
            }

            // =====================================================
            // FILTER CURRENT ASN
            // =====================================================

            type(
                    filterAsnById,
                    asnId
            );

            Thread.sleep(2000);

            pressEnter(filterAsnById);

            Thread.sleep(3000);

            click(refresh);

            Thread.sleep(3000);

            // =====================================================
            // EXISTING PARENT LPN VALIDATION
            //
            // We are reusing the validation already implemented
            // in AsnPage.
            //
            // It checks:
            //
            // ILPN
            //     ↓
            // Parent LPN
            //     ↓
            // Generated Pallet ID
            //
            // If mismatch -> IllegalStateException
            // -> Cucumber step FAILS
            // -> SD-Putaway DOES NOT execute
            // =====================================================

            AsnPage asnPage =
                    new AsnPage();

            asnPage.verifyILPNParentLpn(
                    asnId,
                    ilpns,
                    palletId
            );

            System.out.println(
                    "Parent LPN validation completed successfully "
                            + "for ASN: "
                            + asnId
            );
        }

        // =========================================================
        // FINAL SUCCESS
        // =========================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "PALLETIZED ILPN PARENT LPN VALIDATION PASSED"
        );

        System.out.println(
                "Pallet ID: "
                        + palletId
        );

        System.out.println(
                "======================================"
        );
    }


    public void putawayAtFixedLocation(String fixedLocation)
            throws InterruptedException {

        @SuppressWarnings("unchecked")
        List<ASNData> createdASNData =
                (List<ASNData>) ScenarioContext.get("CreatedASNData");

        if (createdASNData == null || createdASNData.isEmpty()) {
            throw new IllegalStateException(
                    "No Created ASN data found in ScenarioContext");
        }

        if (fixedLocation == null || fixedLocation.isBlank()) {
            throw new IllegalArgumentException(
                    "Fixed putaway location cannot be null or empty");
        }

        fixedLocation = fixedLocation.trim();

        for (ASNData asnData : createdASNData) {
            String asnId = asnData.getAsn();

            if (asnData.getIlpns() == null || asnData.getIlpns().isEmpty()) {
                throw new IllegalStateException(
                        "No ILPNs found for ASN: " + asnId);
            }

            System.out.println(
                    "Starting E2E Putaway | ASN: " + asnId
                            + " | Location: " + fixedLocation);

            for (ILPNData ilpnData : asnData.getIlpns()) {
                String ilpn = ilpnData.getIlpn();

                if (ilpn == null || ilpn.isBlank()) {
                    throw new IllegalStateException(
                            "ILPN is empty for ASN: " + asnId);
                }

                if (ilpnData.getItems() == null
                        || ilpnData.getItems().isEmpty()) {
                    throw new IllegalStateException(
                            "No items found for ILPN: " + ilpn);
                }

                storeIlpnLocation(ilpn, fixedLocation);

                for (ItemData itemData : ilpnData.getItems()) {
                    String item = itemData.getItem();
                    String quantity = itemData.getShippedQty();

                    String parentWindowId = driver.getWindowHandle();
                    ScenarioContext.set("parentWindowId", parentWindowId);

                    click(menuToggleButton);
                    Thread.sleep(2000);

                    type(searchBarInLandingPage, "WM Mobile");
                    Thread.sleep(2000);

                    click(clickWmMobileFromMenu);
                    waitForPageLoad();

                    boolean childWindowFound = false;

                    for (String handle : driver.getWindowHandles()) {
                        if (!handle.equals(parentWindowId)) {
                            driver.switchTo().window(handle);
                            childWindowFound = true;
                            break;
                        }
                    }

                    if (!childWindowFound) {
                        throw new IllegalStateException(
                                "WM Mobile window was not opened for ILPN: "
                                        + ilpn);
                    }

                    try {
                        searchAndWaitForNextElement(
                                searchBarInWmMobile,
                                "190 User Directed",
                                clickUD);

                        click(clickUD);

                        type(scanContainer, ilpn);
                        pressEnter(scanContainer);

                        type(scanItemUD, item);
                        pressEnter(scanItemUD);

                        type(scanQtyUD, quantity);
                        pressEnter(scanQtyUD);

                        type(scanLocUD, fixedLocation);
                        pressEnter(scanLocUD);

                        Thread.sleep(10000);
                        handleDifferentItemDedicatedWarning();

                        report.addReportStepWithScreenshot(
                                StepStatus.PASS,
                                "E2E Putaway completed for ILPN: " + ilpn
                                        + " | Item: " + item
                                        + " | Qty: " + quantity
                                        + " | Location: " + fixedLocation);

                    } finally {
                        if (driver.getWindowHandles().contains(parentWindowId)) {
                            if (!driver.getWindowHandle().equals(parentWindowId)) {
                                driver.close();
                            }

                            driver.switchTo().window(parentWindowId);
                        }
                    }
                }
            }

            // Verify the ILPN locations using the existing validation.
            click(menuToggleButton);
            Thread.sleep(2000);

            type(searchBarInLandingPage, "ASNs");
            Thread.sleep(2000);

            click(clickAsnSFromMenu);
            Thread.sleep(3000);

            type(filterAsnById, asnId);
            pressEnter(filterAsnById);

            click(refresh);
            Thread.sleep(3000);

            List<String> ilpns = new ArrayList<>();

            for (ILPNData ilpnData : asnData.getIlpns()) {
                ilpns.add(ilpnData.getIlpn());
            }

            AsnPage asnPage = new AsnPage();
            asnPage.verifyILPNLocation(asnId, ilpns);
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "E2E putaway and ILPN location verification completed"
                        + " at location: " + fixedLocation);
    }
    public void processE2EWaveTasksInWmMobile(String waveNumber)
            throws InterruptedException {

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty");
        }

        Object taskObject =
                ScenarioContext.get("Task Numbers - " + waveNumber);

        if (!(taskObject instanceof List<?>)) {
            throw new IllegalStateException(
                    "Task numbers were not available in ScenarioContext for Wave: "
                            + waveNumber);
        }

        List<?> storedTasks = (List<?>) taskObject;

        if (storedTasks.isEmpty()) {
            throw new IllegalStateException(
                    "No tasks were available for Wave: " + waveNumber);
        }

        List<String> taskNumbers = new ArrayList<>();

        for (Object task : storedTasks) {
            if (task != null && !task.toString().isBlank()) {
                taskNumbers.add(task.toString().trim());
            }
        }

        if (taskNumbers.isEmpty()) {
            throw new IllegalStateException(
                    "No valid task IDs were available for Wave: " + waveNumber);
        }

        System.out.println("========================================");
        System.out.println("E2E FULL-LPN WM MOBILE PICKING");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println("Total Tasks: " + taskNumbers.size());
        System.out.println("========================================");

        for (int i = 0; i < taskNumbers.size(); i++) {
            String taskId = taskNumbers.get(i);

            System.out.println(
                    "Processing E2E task " + (i + 1)
                            + " of " + taskNumbers.size()
                            + ": " + taskId);

            processSingleWaveTask(
                    taskId,
                    waveNumber,
                    false,  // Do not subtract one from the quantity.
                    true,   // Preserve mandatory OK handling.
                    true,   // Preserve optional OK handling.
                    true,   // Return to Tasks after each task.
                    true,   // Verify the task is Completed.
                    true    // Use the E2E full-LPN branch.
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "E2E full-LPN task completed successfully: " + taskId);
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "All " + taskNumbers.size()
                        + " E2E task(s) completed for Wave: " + waveNumber);

        System.out.println("E2E WM Mobile picking completed for Wave: " + waveNumber);
    }
}
