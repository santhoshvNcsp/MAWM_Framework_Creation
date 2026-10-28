
package Pages;

import com.p09.framework.context.ScenarioContext;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import context.OrderData;
import context.OrderLineData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

public class OrdersPage extends BasePage {
    @FindBy(xpath = "//button[@data-component-id='selectAllRows']")
    public WebElement selectAllRows;

    // =========================================================
    // ORDERS LANDING PAGE
    // =========================================================

    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;

    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;

    @FindBy(id = "orders")
    public WebElement clickOrdersFromOptions;

    @FindBy(xpath = "//span[contains(text(),'Showing ')]")
    public WebElement showingTextInOrdersPageForVerification;

    @FindBy(xpath = "//button[@data-component-id='Createddatetime-Clear']")
    public WebElement clearButton;


    // =========================================================
    // CREATE ORDER
    // =========================================================

    @FindBy(xpath = "//button[@data-component-id='CreateOrder']")
    public WebElement clickCreateOrderButton;

    @FindBy(xpath = "//ion-button[@data-component-id='ExpandAll']")
    public WebElement expandAllDuringOrderCreation;

    @FindBy(xpath = "//input[@placeholder='Order type']")
    public WebElement enterOrderType;

    @FindBy(xpath = "//input[@placeholder='Priority']")
    public WebElement ordPriority;

    @FindBy(xpath = "//input[@placeholder='Origin facility']")
    public WebElement enterOrderOrgFacility;

    @FindBy(xpath = "//input[@placeholder='Destination facility']")
    public WebElement enterOrderDestFacility;

    @FindBy(xpath = "//ion-button[@data-component-id='Save']")
    public WebElement saveCreatedOrder;

    @FindBy(xpath = "//span[@data-component-id='OrderId']")
    public WebElement getCreatedOrderId;

    @FindBy(xpath = "//p[text()=' P09 ']")
    public WebElement selectFacility;

    @FindBy(xpath = "//button[@data-component-id='Close-Icon']")
    public WebElement closeCreatedOrder;


    // =========================================================
    // ORDER FILTER
    // =========================================================

    @FindBy(xpath = "//ion-input[@data-component-id='OrderId']/label/div/input")
    public WebElement filterOrderByOrderId;

    @FindBy(xpath = "//span[text()=' Order : ']")
    public WebElement clickOrderToRedirectRelatedLinks;


    // =========================================================
    // RELATED LINKS
    // =========================================================

    @FindBy(xpath = "//button[@data-component-id='relatedLinks']")
    public WebElement clickRelatedLinks;

    @FindBy(linkText = "Order Lines")
    public WebElement clickOrderLines;

    @FindBy(xpath = "//span[text()='Ship Confirm']")
    public WebElement shipConfirm;


    // =========================================================
    // ORDER LINES
    // =========================================================

    @FindBy(xpath = "//strong[text()='No records found.']")
    public WebElement noResFnd;

    @FindBy(xpath = "//button[@data-component-id='CreateOrderLine']")
    public WebElement createOrderLine;

    @FindBy(xpath = "//input[@placeholder='Order line']")
    public WebElement passOrderLineValue;

    @FindBy(xpath = "//input[@placeholder='Item ID']")
    public WebElement passItemId;

    @FindBy(xpath = "//input[@placeholder='Ordered quantity']")
    public WebElement passOrderLineQty;

    // Flexible locator for Order Line values/cards on the Order Lines inquiry page.
    // The actual UI component id may be OrderLine, OrderLineId, OrderLineNumber, etc.
    private final By orderLineCandidates = By.xpath(
            "//*[contains(translate(@data-component-id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'orderline') " +
                    "and not(self::button) " +
                    "and not(self::ion-button) " +
                    "and not(self::input)]"
    );


    // =========================================================
    // ITEM SEARCH
    // =========================================================

    @FindBy(xpath = "//button[@data-component-id='ItemIdSearch']")
    public WebElement itemSearchOrder;

    @FindBy(xpath = "//ion-button[contains(text(), 'Show All Filters')]")
    public WebElement showAllFiltersAsnDetail;

    @FindBy(xpath = "//input[@placeholder='Primary Barcode']")
    public WebElement primaryBarcode;

    @FindBy(xpath = "//ion-button[@data-component-id='search-btn']")
    public WebElement clickSearchAfterItemBarcodeEntered;

    @FindBy(xpath = "//input[@data-component-id='radio']")
    public WebElement selectItemFromDetailSearch;

    @FindBy(xpath = "//ion-button[@data-component-id='submit-btn']")
    public WebElement submitItemSearch;


    // =========================================================
    // NAVIGATION BACK
    // =========================================================

    @FindBy(linkText = "Orders")
    public WebElement redirectToOrdersFromOrderLine;


    // =========================================================
    // MORE MENU
    // =========================================================

    @FindBy(xpath = "//div[text()=' More ']")
    public WebElement clickMoreOrders;

    @FindBy(xpath = "//span[text()='Assign Pipeline']")
    public WebElement assignPipeline;

    @FindBy(xpath = "//ion-input[@data-component-id='PipelineId']/label/div[2]/input")
    public WebElement selectPipeline;

    @FindBy(xpath = "//p[text()=' DEMO_NO_RTE_PIPELINE ']")
    public WebElement selectDemoPipeline;


    // =========================================================
    // REFRESH
    // =========================================================

    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;


    // =========================================================
    // RUN WAVE
    // =========================================================

    @FindBy(xpath = "//span[text()='Run Wave']")
    public WebElement runWavesFromOrders;

    @FindBy(xpath = "//ion-input[@data-component-id='PlanningStrategyId']/label/div[2]/input")
    public WebElement orderPlanStr;

    @FindBy(xpath = "//ion-searchbar[@data-component-id='searchbar']/div/input")
    public WebElement typeOrderPlanStr;

    @FindBy(xpath = "//p[text()=' 190 order planning strategy ']")
    public WebElement select190OrdPlan;


    // =========================================================
    // WAVE SUBMISSION TOAST
    // =========================================================

    @FindBy(xpath = "//div[@data-component-id='toast-message']")
    public WebElement waveSubmittedToast;


    // =========================================================
    // STATUS VALIDATION
    // =========================================================

    public static final By orderStatusValidation =
            By.xpath(
                    "(//div[@data-component-id='MinimumStatusDescription'])[1]"
            );


    // =========================================================
    // NAVIGATE TO ORDERS
    // =========================================================

    public void navigateToAnyUi(
            String uiName)
            throws InterruptedException {

        click(menuToggleButton);

        click(searchBarInLandingPage);

        type(
                searchBarInLandingPage,
                uiName
        );

        Thread.sleep(2000);

        if (Objects.requireNonNull(
                searchBarInLandingPage
                        .getAttribute("value")
        ).equalsIgnoreCase(uiName)) {

            if (isDisplayed(
                    clickOrdersFromOptions
            )) {

                Thread.sleep(2000);

                click(
                        clickOrdersFromOptions
                );

                Thread.sleep(2000);

                if (isDisplayed(
                        showingTextInOrdersPageForVerification
                )) {

                    report.addReportStepWithScreenshot(
                            StepStatus.PASS,
                            "user entered the orders page"
                    );

                    clearCreatedDateTimeFilter();
                }

            }

        } else {

            report.addReportStepWithScreenshot(
                    StepStatus.FAIL,
                    "search bar contains some other value"
            );
        }
    }


    // =========================================================
    // CLEAR EXISTING FILTER
    // =========================================================

    public void clearCreatedDateTimeFilter()
            throws InterruptedException {

        try {

            if (!clearButton.isDisplayed()) {

                System.out.println(
                        "Created Date Time filter is not available."
                );

                return;
            }

        } catch (NoSuchElementException e) {

            System.out.println(
                    "Created Date Time filter is not available."
            );

            return;
        }


        if (!clearButton.isEnabled()) {

            System.out.println(
                    "Created Date Time filter is displayed "
                            + "but Clear button is disabled."
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Created Date Time filter not active"
            );

            return;
        }


        System.out.println(
                "Created Date Time filter is available. "
                        + "Clearing it."
        );

        click(
                clearButton
        );

        Thread.sleep(2000);

//        report.addReportStepWithScreenshot(
//                StepStatus.PASS,
//                "Created Date Time filter cleared"
//        );
    }


    // =========================================================
    // CREATE ORDER
    // =========================================================

    public String createOrder(
            String orderType,
            String orderPriority)
            throws InterruptedException {

        System.out.println("Creating Order");

        click(clickCreateOrderButton);

        Thread.sleep(2000);

        click(expandAllDuringOrderCreation);

        Thread.sleep(1000);

        type(
                enterOrderType,
                orderType
        );

        type(
                ordPriority,
                orderPriority
        );

        type(
                enterOrderOrgFacility,
                "P09"
        );

        type(
                enterOrderDestFacility,
                "P09"
        );

        click(selectFacility);

        Thread.sleep(1000);

        click(saveCreatedOrder);

        Thread.sleep(3000);

        String orderId =
                getCreatedOrderId
                        .getText()
                        .trim();

        System.out.println(
                "Created Order ID: "
                        + orderId
        );

        ScenarioContext.set(
                "Created Order",
                orderId
        );

        click(closeCreatedOrder);

        Thread.sleep(2000);

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Order created successfully. Order ID: "
                        + orderId
        );

        return orderId;
    }
    public void verifyPackedOrderAndCaptureScreenshot(
            String orderId)
            throws InterruptedException {

        if (orderId == null
                || orderId.isBlank()) {

            throw new IllegalArgumentException(
                    "Order ID cannot be null or empty"
            );
        }

        orderId = orderId.trim();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "FINAL ORDER VALIDATION"
        );

        System.out.println(
                "Order ID: "
                        + orderId
        );

        System.out.println(
                "========================================"
        );


        type(
                filterOrderByOrderId,
                orderId
        );


        String enteredOrderId =
                getAttribute(
                        filterOrderByOrderId,
                        "value"
                );

        if (enteredOrderId == null
                || !enteredOrderId
                .trim()
                .equalsIgnoreCase(orderId)) {

            throw new IllegalStateException(
                    "Order ID filter was not entered correctly. "
                            + "Expected: "
                            + orderId
                            + " | Actual: "
                            + enteredOrderId
            );
        }
        pressEnter(
                filterOrderByOrderId
        );
        Thread.sleep(3000);


        if (!isDisplayed(
                clickOrderToRedirectRelatedLinks
        )) {

            throw new IllegalStateException(
                    "Created Order was not found after filtering. "
                            + "Order ID: "
                            + orderId
            );
        }
        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Packed"
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Packed successfully. "
                        + "Order: "
                        + orderId
                        + " | Status: Packed"
        );

        System.out.println(
                "Order Packed validation completed successfully."
        );
    }


    // =========================================================
    // FILTER ORDER
    // =========================================================

    public void filterExistingOrder(
            String orderId)
            throws InterruptedException {

        System.out.println(
                "Entered filter existing order"
        );

//        // =====================================================
//        // CLOSE CREATED ORDER ONLY IF IT IS DISPLAYED
//        // =====================================================
//
//        if (isDisplayed(closeCreatedOrder)) {
//
//            System.out.println(
//                    "Close Created Order is displayed. "
//                            + "Closing the existing order details."
//            );
//
//            click(closeCreatedOrder);
//
//            Thread.sleep(2000);
//
//        } else {
//
//            System.out.println(
//                    "Close Created Order is not displayed. "
//                            + "Already on Orders page. Continuing."
//            );
//        }

        // =====================================================
        // ENTER ORDER ID
        // =====================================================

        type(
                filterOrderByOrderId,
                orderId
        );

        // =====================================================
        // VERIFY ORDER ID WAS ENTERED
        // =====================================================

        String enteredOrderId =
                getAttribute(
                        filterOrderByOrderId,
                        "value"
                );

        if (enteredOrderId == null
                || !enteredOrderId
                .trim()
                .equalsIgnoreCase(orderId)) {

            throw new IllegalStateException(
                    "Order ID filter value was not entered correctly. "
                            + "Expected: "
                            + orderId
                            + " | Actual: "
                            + enteredOrderId
            );
        }

        // =====================================================
        // APPLY FILTER
        // =====================================================

        pressEnter(
                filterOrderByOrderId
        );

        Thread.sleep(2000);

        // =====================================================
        // VERIFY ORDER WAS FOUND
        // =====================================================

        if (isDisplayed(
                clickOrderToRedirectRelatedLinks
        )) {

            // =================================================
            // SELECT ORDER
            // =================================================

            click(
                    clickOrderToRedirectRelatedLinks
            );

            Thread.sleep(2000);

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Existing Order Filtered and Selected"
                            + " | Order ID: "
                            + orderId
            );

        } else {

            throw new IllegalStateException(
                    "Existing order was not found: "
                            + orderId
            );
        }
    }


    // =========================================================
    // CREATE ORDER LINES
    // =========================================================

    public void createOrderLines(
            OrderData orderData)
            throws InterruptedException {

        String orderId =
                orderData.getOrderId();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "SELECTING CREATED ORDER"
        );

        System.out.println(
                "Order ID: "
                        + orderId
        );

        System.out.println(
                "========================================"
        );


        Thread.sleep(2000);


        click(
                filterOrderByOrderId
        );

        type(
                filterOrderByOrderId,
                orderId
        );

        pressEnter(
                filterOrderByOrderId
        );

        Thread.sleep(3000);

        System.out.println(
                "Filtered Orders using Order ID: "
                        + orderId
        );


        click(
                clickOrderToRedirectRelatedLinks
        );

        Thread.sleep(2000);

        System.out.println(
                "Created Order selected: "
                        + orderId
        );


        click(
                clickRelatedLinks
        );

        Thread.sleep(1500);

        System.out.println(
                "Related Links opened"
        );


        click(
                clickOrderLines
        );

        Thread.sleep(2500);

        System.out.println(
                "Navigated to Order Lines"
        );


        if (isDisplayed(
                noResFnd
        )) {

            System.out.println(
                    "No order lines found. Creating "
                            + orderData
                            .getOrderLines()
                            .size()
                            + " lines."
            );


            for (OrderLineData orderLine :
                    orderData.getOrderLines()) {

                createSingleOrderLine(
                        orderLine
                );
            }

            click(
                    redirectToOrdersFromOrderLine
            );

        } else {

            throw new RuntimeException(
                    "Order "
                            + orderId
                            + " already contains Order Lines. "
                            + "Expected No records found."
            );
        }
    }


    // =========================================================
    // CREATE SINGLE ORDER LINE
    // =========================================================

    private void createSingleOrderLine(
            OrderLineData orderLine)
            throws InterruptedException {

        System.out.println(
                "Creating Order Line: "
                        + orderLine.getLineNumber()
                        + " | Item: "
                        + orderLine.getItem()
                        + " | Qty: "
                        + orderLine.getQuantity()
        );


        click(
                createOrderLine
        );

        Thread.sleep(2000);


        type(
                passOrderLineValue,
                orderLine.getLineNumber()
        );

        if (isDisplayed(
                expandAllDuringOrderCreation
        )) {

            click(
                    expandAllDuringOrderCreation
            );
        }

        Thread.sleep(2000);

        click(
                itemSearchOrder
        );

        Thread.sleep(2000);


        click(
                showAllFiltersAsnDetail
        );

        Thread.sleep(1000);


        type(
                primaryBarcode,
                orderLine.getItem()
        );

        click(
                clickSearchAfterItemBarcodeEntered
        );

        Thread.sleep(2500);


        click(
                selectItemFromDetailSearch
        );

        Thread.sleep(500);

        click(
                submitItemSearch
        );

        Thread.sleep(2000);


        type(
                passOrderLineQty,
                orderLine.getQuantity()
        );


        Thread.sleep(2000);

        click(
                saveCreatedOrder
        );

        Thread.sleep(2500);


        click(
                closeCreatedOrder
        );

        Thread.sleep(2000);

        System.out.println(
                "Order Line "
                        + orderLine.getLineNumber()
                        + " created and popup closed."
        );
    }


    // =========================================================
    // ASSIGN PIPELINE
    // =========================================================

    public void assignPipelineAndWaitForRelease(
            OrderData orderData)
            throws InterruptedException {

        Thread.sleep(2000);

        click(
                clickOrderToRedirectRelatedLinks
        );

        Thread.sleep(2000);

        click(
                clickMoreOrders
        );

        Thread.sleep(2000);


        if (!isDisplayed(
                assignPipeline
        )) {

            throw new IllegalStateException(
                    "Assign Pipeline option was not displayed"
            );
        }


        click(
                assignPipeline
        );

        Thread.sleep(8000);


        // =====================================================
        // SELECT PIPELINE
        // =====================================================

        if (isDisplayed(
                selectPipeline
        )) {

            click(
                    selectPipeline
            );

            Thread.sleep(2000);

            if (getText(
                    selectDemoPipeline
            ).contains("DEMO")) {

                click(
                        selectDemoPipeline
                );
            }

        } else {

            throw new IllegalStateException(
                    "Pipeline field was not displayed"
            );
        }


        Thread.sleep(3000);


        // =====================================================
        // SUBMIT
        // =====================================================

        click(
                submitItemSearch
        );

        Thread.sleep(3000);


        // =====================================================
        // WAIT FOR RELEASED
        // =====================================================

        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Released"
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Released"
        );
    }


    // =========================================================
    // RUN WAVE
    // =========================================================

    public void runWave(
            OrderData orderData)
            throws InterruptedException {

        // =====================================================
        // EXISTING ORDER
        // =====================================================

        if (!isDisplayed(
                clickOrderToRedirectRelatedLinks
        )) {

            throw new IllegalStateException(
                    "Order is not selected before running wave"
            );
        }


        click(
                clickOrderToRedirectRelatedLinks
        );

        Thread.sleep(2000);


        // =====================================================
        // OPEN MORE
        // =====================================================

        click(
                clickMoreOrders
        );

        Thread.sleep(2000);


        // =====================================================
        // RUN WAVE
        // =====================================================

        if (!isDisplayed(
                runWavesFromOrders
        )) {

            throw new IllegalStateException(
                    "Run Wave option was not displayed"
            );
        }


        click(
                runWavesFromOrders
        );

        Thread.sleep(2000);


        // =====================================================
        // PLANNING STRATEGY
        // =====================================================

        if (!isDisplayed(
                orderPlanStr
        )) {

            throw new IllegalStateException(
                    "Order Planning Strategy field was not displayed"
            );
        }


        Thread.sleep(3000);

        click(
                orderPlanStr
        );

        Thread.sleep(2000);


        type(
                typeOrderPlanStr,
                "190"
        );

        Thread.sleep(2000);


        click(
                select190OrdPlan
        );

        Thread.sleep(2000);


        // =====================================================
        // SUBMIT WAVE
        // =====================================================

        click(
                submitItemSearch
        );


        // =====================================================
        // CAPTURE WAVE NUMBER FROM SUBMISSION TOAST
        // =====================================================

        String waveNumber =
                captureWaveNumberFromSubmissionToast();


        // =====================================================
        // STORE WAVE NUMBER
        // =====================================================

        ScenarioContext.set(
                "wave nbr",
                waveNumber
        );


        ScenarioContext.set(
                "Wave Number - "
                        + orderData.getOrderId(),
                waveNumber
        );


        // =====================================================
        // WAIT FOR ALLOCATED
        // =====================================================

        Thread.sleep(2000);

        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Allocated"
        );


        Thread.sleep(2000);


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Allocated Successfully"
                        + "\nWave Number: "
                        + waveNumber
        );


        System.out.println(
                "Wave Number: "
                        + waveNumber
        );
    }


    // =========================================================
    // CAPTURE WAVE NUMBER FROM SUBMISSION TOAST
    // =========================================================

    private String captureWaveNumberFromSubmissionToast()
            throws InterruptedException {

        System.out.println(
                "========================================"
        );

        System.out.println(
                "CAPTURING WAVE NUMBER FROM SUBMISSION TOAST"
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // WAIT FOR TOAST
        // =====================================================

        wait.until(
                ExpectedConditions.visibilityOf(
                        waveSubmittedToast
                )
        );


        // =====================================================
        // GET TOAST MESSAGE
        // =====================================================

        String toastMessage =
                getText(
                        waveSubmittedToast
                );


        System.out.println(
                "Wave Submission Toast: "
                        + toastMessage
        );


        // =====================================================
        // VALIDATE TOAST MESSAGE
        // =====================================================

        if (toastMessage == null
                || toastMessage.isBlank()) {

            throw new IllegalStateException(
                    "Wave submission toast message was empty"
            );
        }


        // =====================================================
        // EXTRACT WAVE NUMBER
        //
        // Example:
        //
        // Wave W09242026000000000004 is submitted (info)
        //
        // Start:
        // after "Wave "
        //
        // End:
        // before " is submitted"
        // =====================================================

        String startText =
                "Wave ";

        String endText =
                " is submitted";


        int startIndex =
                toastMessage.indexOf(
                        startText
                );

        int endIndex =
                toastMessage.indexOf(
                        endText
                );


        if (startIndex == -1
                || endIndex == -1) {

            throw new IllegalStateException(
                    "Unable to locate Wave Number in toast message: "
                            + toastMessage
            );
        }


        startIndex =
                startIndex
                        + startText.length();


        if (startIndex >= endIndex) {

            throw new IllegalStateException(
                    "Invalid Wave Number boundaries in toast message: "
                            + toastMessage
            );
        }


        String waveNumber =
                toastMessage.substring(
                        startIndex,
                        endIndex
                ).trim();


        // =====================================================
        // VALIDATE EXTRACTED WAVE NUMBER
        // =====================================================

        if (waveNumber.isBlank()) {

            throw new IllegalStateException(
                    "Wave Number extracted from toast was empty. "
                            + "Toast: "
                            + toastMessage
            );
        }


        // =====================================================
        // PRINT RESULT
        // =====================================================

        System.out.println(
                "Wave Number Captured From Toast: "
                        + waveNumber
        );

        System.out.println(
                "========================================"
        );


        return waveNumber;
    }


    // =========================================================
    // OPEN ORDER LINES FOR AN EXISTING ORDER
    // =========================================================

    public void openOrderLinesForOrder(
            String orderId)
            throws InterruptedException {


        click(clickRelatedLinks);
        Thread.sleep(1000);

        wait.until(
                ExpectedConditions.visibilityOf(
                        clickOrderLines
                )
        );

        click(clickOrderLines);
        Thread.sleep(2500);

        wait.until(
                ExpectedConditions.visibilityOf(
                        showingTextInOrdersPageForVerification
                )
        );
    }


    // =========================================================
    // FIND NEW ORDER LINE CREATED AFTER PICK EXCEPTION
    // =========================================================

    public void captureNewOrderLineAndScreenshot(
            String orderId)
            throws InterruptedException {

        openOrderLinesForOrder(orderId);

        List<String> existingLineNumbers =
                getOriginalOrderLineNumbers();

        FluentWait<WebDriver> lineWait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(90))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(Exception.class);

        WebElement newOrderLineElement =
                lineWait.until(d ->
                        findNewOrderLineElement(
                                existingLineNumbers
                        )
                );

        String newOrderLine =
                extractOrderLineNumber(
                        getText(newOrderLineElement),
                        true
                );

        if (newOrderLine == null || newOrderLine.isBlank()) {
            throw new IllegalStateException(
                    "New order line was found, but its number could not be extracted. "
                            + "UI text: "
                            + getText(newOrderLineElement)
            );
        }

        ScenarioContext.set(
                "New Order Line",
                newOrderLine
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "New Order Line created after partial pick. "
                        + "Order: " + orderId
                        + " | New Order Line: " + newOrderLine
        );

        System.out.println(
                "New Order Line identified: "
                        + newOrderLine
        );
    }
    public String captureChaseWaveNumberFromNewOrderLine(
            String orderId,
            String originalWaveNumber)
            throws InterruptedException {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException(
                    "Order ID cannot be null or empty");
        }

        if (originalWaveNumber == null || originalWaveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Original Wave Number cannot be null or empty");
        }

        openOrderLinesForOrder(orderId);

        String newOrderLine =
                ScenarioContext.getString("New Order Line");

        if (newOrderLine == null || newOrderLine.isBlank()) {
            throw new IllegalStateException(
                    "New Order Line was not available in ScenarioContext");
        }

        // Find and select the new order line.
        WebElement orderLineElement = findOrderLineElement(newOrderLine);

        if (orderLineElement == null) {
            throw new IllegalStateException(
                    "New Order Line was not found: " + newOrderLine);
        }

        click(orderLineElement);
        Thread.sleep(1000);

        // Open Details.
        By detailsButton =
                By.xpath("//ion-button[@data-component-id='footer-panel-action-Details']");

        wait.until(
                ExpectedConditions.elementToBeClickable(detailsButton));

        click(driver.findElement(detailsButton));

        // Expand all detail fields.
        By expandAllButton =
                By.xpath("//ion-button[@data-component-id='ExpandAll']");

        wait.until(
                ExpectedConditions.elementToBeClickable(expandAllButton));

        click(driver.findElement(expandAllButton));

        // Wait for the Run Number field to appear, then hover over it.
        By runNumberLocator =
                By.xpath("//div[@data-component-id='OrderPlanningRunId']");

        WebElement runNumberElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                runNumberLocator));

        hover(runNumberElement);

        // Re-read the element after hovering and wait until it contains text.
        FluentWait<WebDriver> runNumberWait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(2))
                        .ignoring(NoSuchElementException.class);

        String runNumberText = runNumberWait.until(d -> {
            WebElement element = d.findElement(runNumberLocator);

            if (!element.isDisplayed()) {
                return null;
            }

            String text = element.getText();

            return text == null || text.isBlank()
                    ? null
                    : text.trim();
        });

        if (runNumberText == null || runNumberText.isBlank()) {
            throw new IllegalStateException(
                    "Run Number was blank for new Order Line: "
                            + newOrderLine);
        }

        // The Run Number field may include a label as well as the value.
        // Extract the wave-shaped identifier from the displayed text.
        Matcher matcher =
                Pattern.compile("W\\d+", Pattern.CASE_INSENSITIVE)
                        .matcher(runNumberText);

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Could not extract a wave number from Run Number text. "
                            + "Order Line: " + newOrderLine
                            + " | Displayed text: " + runNumberText);
        }

        String chaseWaveNumber = matcher.group().trim();

        if (chaseWaveNumber.equalsIgnoreCase(originalWaveNumber.trim())) {
            throw new IllegalStateException(
                    "Run Number matched the original wave, not the Chase Wave. "
                            + "Original: " + originalWaveNumber
                            + " | Run Number: " + chaseWaveNumber);
        }

        ScenarioContext.set("Chase Wave Number", chaseWaveNumber);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Chase Wave Number captured from Run Number. "
                        + "Order Line: " + newOrderLine
                        + " | Chase Wave: " + chaseWaveNumber);

        System.out.println(
                "Chase Wave Number captured from new Order Line: "
                        + chaseWaveNumber);

        // Close the details panel and return to Orders.
        By closeDetailsButton =
                By.xpath("//button[@data-component-id='Close-Icon']");

        if (isDisplayed(driver.findElement(closeDetailsButton))) {
            click(driver.findElement(closeDetailsButton));
            Thread.sleep(1000);
        }

        click(redirectToOrdersFromOrderLine);
        Thread.sleep(1500);

        return chaseWaveNumber;
    }


    // =========================================================
    // VERIFY GENERIC ORDER STATUS
    // =========================================================

    public void verifyOrderStatus(
            String orderId,
            String expectedStatus)
            throws InterruptedException {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException(
                    "Order ID cannot be null or empty"
            );
        }

        if (expectedStatus == null || expectedStatus.isBlank()) {
            throw new IllegalArgumentException(
                    "Expected Order status cannot be null or empty"
            );
        }

        type(
                filterOrderByOrderId,
                orderId.trim()
        );

        String enteredOrderId =
                getAttribute(
                        filterOrderByOrderId,
                        "value"
                );

        if (enteredOrderId == null
                || !enteredOrderId.trim().equalsIgnoreCase(orderId.trim())) {
            throw new IllegalStateException(
                    "Order ID filter was not entered correctly. "
                            + "Expected: " + orderId
                            + " | Actual: " + enteredOrderId
            );
        }

        pressEnter(filterOrderByOrderId);
        Thread.sleep(2000);

        wait.until(
                ExpectedConditions.visibilityOf(
                        clickOrderToRedirectRelatedLinks
                )
        );

        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                expectedStatus
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order status validated. "
                        + "Order: " + orderId
                        + " | Status: " + expectedStatus
        );

        System.out.println(
                "Verified Order " + orderId
                        + " status: " + expectedStatus
        );
    }


    private List<String> getOriginalOrderLineNumbers() {

        List<String> lineNumbers =
                new ArrayList<>();

        Object stored =
                ScenarioContext.get(
                        "Original Order Line Numbers"
                );

        if (stored instanceof List<?>) {
            for (Object value : (List<?>) stored) {
                if (value != null && !value.toString().isBlank()) {
                    lineNumbers.add(value.toString().trim());
                }
            }
        }

        if (!lineNumbers.isEmpty()) {
            return lineNumbers;
        }

        Object createdOrderDataObject =
                ScenarioContext.get("CreatedOrderData");

        if (createdOrderDataObject instanceof List<?>) {
            List<?> orders = (List<?>) createdOrderDataObject;

            if (!orders.isEmpty()
                    && orders.get(0) instanceof OrderData) {

                OrderData firstOrder =
                        (OrderData) orders.get(0);

                for (OrderLineData line : firstOrder.getOrderLines()) {
                    if (line.getLineNumber() != null
                            && !line.getLineNumber().isBlank()) {
                        lineNumbers.add(
                                line.getLineNumber().trim()
                        );
                    }
                }
            }
        }

        return lineNumbers;
    }


    private WebElement findNewOrderLineElement(
            List<String> existingLineNumbers) {

        List<WebElement> candidates =
                driver.findElements(orderLineCandidates);

        for (WebElement candidate : candidates) {

            try {
                if (!candidate.isDisplayed()) {
                    continue;
                }

                String text = getText(candidate);

                if (text == null || text.isBlank()) {
                    continue;
                }

                String lineNumber =
                        extractOrderLineNumber(text, true);

                if (lineNumber != null
                        && !lineNumber.isBlank()
                        && !existingLineNumbers.contains(lineNumber.trim())) {

                    return candidate;
                }

            } catch (Exception ignored) {
                // The Order Lines page can refresh while the new line is generated.
            }
        }

        return null;
    }


    private WebElement findOrderLineElement(
            String lineNumber) {

        List<WebElement> candidates =
                driver.findElements(orderLineCandidates);

        for (WebElement candidate : candidates) {

            try {
                if (!candidate.isDisplayed()) {
                    continue;
                }

                String text = getText(candidate);

                if (text == null || text.isBlank()) {
                    continue;
                }

                if (text.trim().equals(lineNumber.trim())
                        || text.contains(lineNumber.trim())) {
                    return candidate;
                }

                String extracted =
                        extractOrderLineNumber(
                                text,
                                true
                        );

                if (lineNumber.equals(extracted)) {
                    return candidate;
                }

            } catch (Exception ignored) {
                // Continue searching other order line elements.
            }
        }

        return null;
    }


    private String extractOrderLineNumber(
            String text,
            boolean allowBareNumber) {

        if (text == null || text.isBlank()) {
            return null;
        }

        Pattern labelledPattern =
                Pattern.compile(
                        "(?i)(?:order\\s*line|line(?:\\s*number)?)"
                                + "\\s*[:#-]?\\s*(\\d+)"
                );

        Matcher labelledMatcher =
                labelledPattern.matcher(text);

        if (labelledMatcher.find()) {
            return labelledMatcher.group(1).trim();
        }

        if (allowBareNumber) {

            String trimmed = text.trim();

            if (trimmed.matches("\\d+")) {
                return trimmed;
            }

            for (String line : text.split("\\R")) {

                String candidate = line.trim();

                if (candidate.matches("\\d+")) {
                    return candidate;
                }
            }
        }

        return null;
    }


    private String findWaveNumberNearOrderLine(
            String lineNumber,
            String originalWaveNumber) {

        Pattern wavePattern =
                Pattern.compile(
                        "\\bW\\d{10,}\\b",
                        Pattern.CASE_INSENSITIVE
                );

        WebElement orderLineElement =
                findOrderLineElement(lineNumber);

        if (orderLineElement != null) {

            WebElement current =
                    orderLineElement;

            for (int level = 0; level < 8; level++) {

                try {
                    String text = getText(current);

                    String wave =
                            extractWaveNumber(
                                    text,
                                    wavePattern,
                                    originalWaveNumber
                            );

                    if (wave != null) {
                        return wave;
                    }

                    current = current.findElement(
                            By.xpath("./..")
                    );

                } catch (Exception e) {
                    break;
                }
            }
        }

        // Fallback: search visible elements whose data-component-id suggests
        // a Run/Wave field, then use getText() to find the new wave number.
        List<WebElement> runCandidates =
                driver.findElements(
                        By.xpath(
                                "//*[contains(translate(@data-component-id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'run') "
                                        + "or contains(translate(@data-component-id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'wave')]"
                        )
                );

        for (WebElement candidate : runCandidates) {

            try {
                if (!candidate.isDisplayed()) {
                    continue;
                }

                String wave =
                        extractWaveNumber(
                                getText(candidate),
                                wavePattern,
                                originalWaveNumber
                        );

                if (wave != null) {
                    return wave;
                }

            } catch (Exception ignored) {
                // Continue searching.
            }
        }

        return null;
    }


    private String extractWaveNumber(
            String text,
            Pattern wavePattern,
            String originalWaveNumber) {

        if (text == null || text.isBlank()) {
            return null;
        }

        Matcher matcher =
                wavePattern.matcher(text);

        while (matcher.find()) {

            String wave = matcher.group().trim();

            if (originalWaveNumber == null
                    || !wave.equalsIgnoreCase(
                    originalWaveNumber.trim()
            )) {
                return wave;
            }
        }

        return null;
    }


    // =========================================================
    // SHIP CONFIRM ORDER
    // =========================================================

    public void shipConfirmOrder()
            throws InterruptedException {

        System.out.println(
                "========================================"
        );

        System.out.println(
                "SHIP CONFIRM ORDER"
        );

        System.out.println(
                "========================================"
        );

        Thread.sleep(3000);

        click(
                clickOrderToRedirectRelatedLinks
        );

        Thread.sleep(2000);


        // =====================================================
        // OPEN MORE MENU
        // =====================================================

        click(
                clickMoreOrders
        );

        Thread.sleep(2000);


        // =====================================================
        // VERIFY SHIP CONFIRM OPTION
        // =====================================================

        if (!isDisplayed(
                shipConfirm
        )) {

            throw new IllegalStateException(
                    "Ship Confirm option was not displayed"
            );
        }


        System.out.println(
                "Ship Confirm option is displayed"
        );


        // =====================================================
        // CLICK SHIP CONFIRM
        // =====================================================

        click(
                shipConfirm
        );

        Thread.sleep(3000);


        // =====================================================
        // WAIT FOR SHIPPED STATUS
        // =====================================================

        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Shipped"
        );


        // =====================================================
        // REPORT
        // =====================================================

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Ship Confirmed successfully. "
                        + "Status: Shipped"
        );


        System.out.println(
                "Order Ship Confirmed successfully. "
                        + "Final Status: Shipped"
        );
    }
    public List<OrderData> createOrdersFromBatch(
            String itemsInput,
            String quantitiesInput,
            String orderType,
            String prioritiesInput) throws InterruptedException {

        List<String> items = splitCsv(itemsInput, "items");
        List<String> quantities = splitCsv(quantitiesInput, "quantities");
        List<String> priorities = splitCsv(prioritiesInput, "order priorities");

        validateBatchInputs(items, quantities, priorities);

        List<OrderData> createdOrders = new ArrayList<>();

        for (int orderIndex = 0; orderIndex < priorities.size(); orderIndex++) {

            String priority = priorities.get(orderIndex).trim();

            System.out.println("========================================");
            System.out.println("CREATING BATCH ORDER " + (orderIndex + 1));
            System.out.println("Priority: " + priority);
            System.out.println("========================================");

            OrderData orderData = new OrderData();
            orderData.setOrderType(orderType);
            orderData.setOrderPriority(priority);

            // Existing method creates the order and closes the order form.
            String orderId = createOrder(orderType, priority);
            orderData.setOrderId(orderId);

            List<String> quantitiesForThisOrder =
                    resolveQuantitiesForOrder(
                            items,
                            quantities,
                            priorities,
                            orderIndex
                    );

            for (int lineIndex = 0; lineIndex < items.size(); lineIndex++) {
                OrderLineData lineData = new OrderLineData(
                        String.valueOf(lineIndex + 1),
                        items.get(lineIndex),
                        quantitiesForThisOrder.get(lineIndex)
                );

                orderData.addOrderLine(lineData);
            }

            orderData.setOrderLineCount(orderData.getOrderLines().size());

            // Existing method filters to this order, opens Order Lines,
            // creates all lines in orderData, then returns to Orders.
            createOrderLines(orderData);

            createdOrders.add(orderData);

            System.out.println("Created batch order: " + orderData);
        }

        ScenarioContext.set("Batch Created Orders", createdOrders);

        System.out.println("========================================");
        System.out.println("BATCH ORDER CREATION COMPLETED");
        System.out.println("Total orders: " + createdOrders.size());
        System.out.println("========================================");

        return createdOrders;
    }





    private List<String> resolveQuantitiesForOrder(
            List<String> items,
            List<String> quantities,
            List<String> priorities,
            int orderIndex) {

        // Shared quantity pattern: one quantity for each item.
        if (quantities.size() == items.size()) {
            return new ArrayList<>(quantities);
        }

        // Different quantity per order: currently supported for one-item orders.
        if (items.size() == 1 && quantities.size() == priorities.size()) {
            List<String> orderQuantities = new ArrayList<>();
            orderQuantities.add(quantities.get(orderIndex));
            return orderQuantities;
        }

        throw new IllegalStateException(
                "Unable to determine quantities for order index " + orderIndex
        );
    }

    // =========================================================
// CREATE BATCH ORDERS
// Priorities determine how many orders are created.
// =========================================================

    public List<OrderData> createBatchOrders(
            String itemsInput,
            String quantitiesInput,
            String orderType,
            String prioritiesInput) throws InterruptedException {

        List<String> items = splitCsv(itemsInput, "items");
        List<String> quantities = splitCsv(quantitiesInput, "quantities");
        List<String> priorities = splitCsv(prioritiesInput, "priorities");

        validateBatchInputs(items, quantities, priorities);

        List<OrderData> batchOrders = new ArrayList<>();

        for (int orderIndex = 0; orderIndex < priorities.size(); orderIndex++) {

            String priority = priorities.get(orderIndex);

            OrderData orderData = new OrderData();
            orderData.setOrderType(orderType);
            orderData.setOrderPriority(priority);

            System.out.println(
                    "Creating batch order " + (orderIndex + 1)
                            + " of " + priorities.size()
                            + " | Priority: " + priority
            );

            String orderId = createOrder(orderType, priority);
            orderData.setOrderId(orderId);

            List<String> quantitiesForOrder =
                    getQuantitiesForOrder(
                            items,
                            quantities,
                            priorities,
                            orderIndex
                    );

            for (int lineIndex = 0; lineIndex < items.size(); lineIndex++) {
                orderData.addOrderLine(
                        new OrderLineData(
                                String.valueOf(lineIndex + 1),
                                items.get(lineIndex),
                                quantitiesForOrder.get(lineIndex)
                        )
                );
            }

            orderData.setOrderLineCount(orderData.getOrderLines().size());

            // Uses your existing order-line creation flow.
            // That method returns to the Orders inquiry after line creation.
            createOrderLines(orderData);

            batchOrders.add(orderData);

            System.out.println("Batch order saved: " + orderData);
        }

        ScenarioContext.set("Batch Created Orders", batchOrders);

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Batch orders created. Total: " + batchOrders.size()
        );

        return batchOrders;
    }


// =========================================================
// CSV AND INPUT VALIDATION
// =========================================================

    private List<String> splitCsv(String input, String fieldName) {

        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be empty."
            );
        }

        List<String> values = new ArrayList<>();

        for (String part : input.split(",")) {
            String value = part.trim();

            if (value.isEmpty()) {
                throw new IllegalArgumentException(
                        "Blank entry found in " + fieldName + ": " + input
                );
            }

            values.add(value);
        }

        return values;
    }


    private void validateBatchInputs(
            List<String> items,
            List<String> quantities,
            List<String> priorities) {

        if (priorities.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one order priority is required."
            );
        }

        boolean oneQuantityPerItem = quantities.size() == items.size();

        boolean oneItemWithQuantityPerOrder =
                items.size() == 1
                        && quantities.size() == priorities.size();

        if (!oneQuantityPerItem && !oneItemWithQuantityPerOrder) {
            throw new IllegalArgumentException(
                    "Unsupported batch input counts. Items=" + items.size()
                            + ", quantities=" + quantities.size()
                            + ", priorities=" + priorities.size()
                            + ". Use one quantity per item for shared line data, "
                            + "or one item with one quantity per order."
            );
        }

        for (String priority : priorities) {
            try {
                if (Integer.parseInt(priority) < 0) {
                    throw new IllegalArgumentException(
                            "Priority cannot be negative: " + priority
                    );
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Priority must be numeric: " + priority, e
                );
            }
        }

        for (String quantity : quantities) {
            try {
                if (Integer.parseInt(quantity) <= 0) {
                    throw new IllegalArgumentException(
                            "Quantity must be greater than zero: " + quantity
                    );
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Quantity must be numeric: " + quantity, e
                );
            }
        }
    }


    private List<String> getQuantitiesForOrder(
            List<String> items,
            List<String> quantities,
            List<String> priorities,
            int orderIndex) {

        // Shared line data: quantity count matches item count.
        if (quantities.size() == items.size()) {
            return new ArrayList<>(quantities);
        }

        // Per-order quantity mode: one item and one quantity per priority.
        if (items.size() == 1 && quantities.size() == priorities.size()) {
            return List.of(quantities.get(orderIndex));
        }

        throw new IllegalStateException(
                "Could not resolve quantities for batch order index " + orderIndex
        );
    }


// =========================================================
// RETRIEVE BATCH ORDERS FROM SCENARIO CONTEXT
// =========================================================

    @SuppressWarnings("unchecked")
    public List<OrderData> getBatchCreatedOrders() {

        Object stored = ScenarioContext.get("Batch Created Orders");

        if (!(stored instanceof List<?>)) {
            throw new IllegalStateException(
                    "Batch Created Orders not found in ScenarioContext."
            );
        }

        List<?> rawList = (List<?>) stored;

        if (rawList.isEmpty()) {
            throw new IllegalStateException(
                    "Batch Created Orders list is empty."
            );
        }

        for (Object value : rawList) {
            if (!(value instanceof OrderData)) {
                throw new IllegalStateException(
                        "Unexpected value in Batch Created Orders: " + value
                );
            }
        }

        return (List<OrderData>) rawList;
    }


// =========================================================
// FILTER BATCH ORDERS
// Assumes the Order ID filter supports comma-separated OR values.
// =========================================================

    public void filterBatchOrders(List<OrderData> batchOrders)
            throws InterruptedException {

        if (batchOrders == null || batchOrders.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot filter an empty batch order list."
            );
        }

        List<String> orderIds = new ArrayList<>();

        for (OrderData order : batchOrders) {
            if (order.getOrderId() == null || order.getOrderId().isBlank()) {
                throw new IllegalStateException(
                        "A batch order has a blank Order ID: " + order
                );
            }
            orderIds.add(order.getOrderId().trim());
        }

        String filterValue = String.join(",", orderIds);

        // Existing BasePage.type helper is used to enter the filter.
        type(filterOrderByOrderId, filterValue);

        String actualValue = getAttribute(
                filterOrderByOrderId,
                "value"
        );

        if (actualValue == null
                || !actualValue.trim().equalsIgnoreCase(filterValue)) {
            throw new IllegalStateException(
                    "Batch Order ID filter value mismatch. Expected: "
                            + filterValue + " | Actual: " + actualValue
            );
        }

        pressEnter(filterOrderByOrderId);
        Thread.sleep(2500);

        wait.until(
                ExpectedConditions.visibilityOf(
                        showingTextInOrdersPageForVerification
                )
        );

        System.out.println("Filtered batch order IDs: " + filterValue);
    }


// =========================================================
// SELECT ALL FILTERED ROWS
// =========================================================

    private void selectAllFilteredOrderRows()
            throws InterruptedException {

        wait.until(
                ExpectedConditions.elementToBeClickable(selectAllRows)
        );

        click(selectAllRows);
        Thread.sleep(1000);
    }


// =========================================================
// ASSIGN PIPELINE TO BATCH ONCE
// =========================================================

    public void assignPipelineToBatchAndWaitForRelease()
            throws InterruptedException {

        List<OrderData> batchOrders = getBatchCreatedOrders();

        filterBatchOrders(batchOrders);

        // Select all filtered batch rows.
        selectAllFilteredOrderRows();

        // Existing More -> Assign Pipeline action.
        click(clickMoreOrders);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(assignPipeline));
        hover(assignPipeline);
        Thread.sleep(1000);
        click(assignPipeline);
        Thread.sleep(3000);

        wait.until(ExpectedConditions.elementToBeClickable(selectPipeline));
        click(selectPipeline);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOf(selectDemoPipeline));

        if (!getText(selectDemoPipeline).contains("DEMO")) {
            throw new IllegalStateException(
                    "Expected pipeline option DEMO_NO_RTE_PIPELINE was not shown."
            );
        }

        click(selectDemoPipeline);
        Thread.sleep(1000);

        click(submitItemSearch);
        Thread.sleep(2500);

        // Wait for the filtered batch's visible status to reach Released.
        // This validates the first displayed order; the full batch status
        // verification is done separately after the wave.
        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Released"
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Pipeline assignment submitted for batch orders: "
                        + getBatchOrderIds(batchOrders)
        );
    }


// =========================================================
// RUN INITIAL WAVE FOR BATCH ONCE
// =========================================================




    private List<String> getBatchOrderIds(List<OrderData> batchOrders) {

        List<String> ids = new ArrayList<>();

        for (OrderData order : batchOrders) {
            ids.add(order.getOrderId());
        }

        return ids;
    }

    // =========================================================
// RUN INITIAL WAVE FOR BATCH ONCE
// Assumes the batch order filter is still active after
// pipeline assignment.
// =========================================================

    public String runInitialWaveForBatch()
            throws InterruptedException {

        List<OrderData> batchOrders = getBatchCreatedOrders();

        // Do not refilter here. The Orders inquiry should still
        // be showing the batch selected during pipeline assignment.
        selectAllFilteredOrderRows();

        click(clickMoreOrders);
        Thread.sleep(1000);

        wait.until(
                ExpectedConditions.elementToBeClickable(runWavesFromOrders)
        );
        click(runWavesFromOrders);
        Thread.sleep(5000);

        wait.until(
                ExpectedConditions.elementToBeClickable(orderPlanStr)
        );
        click(orderPlanStr);
        Thread.sleep(1000);

        type(typeOrderPlanStr, "190");
        Thread.sleep(1500);

        wait.until(
                ExpectedConditions.visibilityOf(select190OrdPlan)
        );
        click(select190OrdPlan);
        Thread.sleep(1000);

        click(submitItemSearch);

        String initialWaveNumber = captureWaveNumberFromSubmissionToast();

        ScenarioContext.set(
                "Initial Batch Wave Number",
                initialWaveNumber
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Initial wave submitted for batch orders. Wave: "
                        + initialWaveNumber
                        + " | Orders: " + getBatchOrderIds(batchOrders)
        );

        return initialWaveNumber;
    }


// =========================================================
// VERIFY ALL BATCH ORDERS ARE ALLOCATED
// Refreshes the currently filtered batch list and validates
// every displayed order status without filtering individually.
// =========================================================

    public void verifyAllBatchOrdersAllocated()
            throws InterruptedException {

        List<OrderData> batchOrders = getBatchCreatedOrders();

        By allOrderStatuses =
                By.xpath("//div[@data-component-id='MinimumStatusDescription']");

        int expectedOrderCount = batchOrders.size();

        FluentWait<WebDriver> allocatedWait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(180))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);

        List<String> finalStatuses = allocatedWait.until(d -> {

            // Refresh the currently filtered batch list on each attempt.
            click(refresh);

            List<WebElement> statusElements =
                    d.findElements(allOrderStatuses);

            // Wait until the list contains a status for every batch order.
            if (statusElements.size() != expectedOrderCount) {
                return null;
            }

            List<String> currentStatuses = new ArrayList<>();

            for (WebElement statusElement : statusElements) {

                if (!statusElement.isDisplayed()) {
                    return null;
                }

                String statusText = statusElement.getText();

                if (statusText == null || statusText.isBlank()) {
                    return null;
                }

                currentStatuses.add(statusText.trim());
            }

            boolean allAllocated = currentStatuses.stream()
                    .allMatch(status ->
                            status.equalsIgnoreCase("Allocated")
                                    || status.toLowerCase().contains("allocated")
                    );

            return allAllocated ? currentStatuses : null;
        });

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "All batch orders reached Allocated status. "
                        + "Orders: " + getBatchOrderIds(batchOrders)
                        + " | Statuses: " + finalStatuses
        );

        System.out.println(
                "All batch orders are Allocated. "
                        + "Orders: " + getBatchOrderIds(batchOrders)
                        + " | Statuses: " + finalStatuses
        );
    }
    public void shipConfirmBatchOrders(List<OrderData> batchOrders)
            throws InterruptedException {

        if (batchOrders == null || batchOrders.size() != 2) {
            throw new IllegalArgumentException(
                    "Expected exactly 2 batch orders for Ship Confirm.");
        }

        List<String> expectedOrderIds = new ArrayList<>();
        for (OrderData order : batchOrders) {
            if (order == null || order.getOrderId() == null
                    || order.getOrderId().isBlank()) {
                throw new IllegalStateException(
                        "Batch contains an order with a blank Order ID.");
            }
            expectedOrderIds.add(order.getOrderId().trim());
        }

        // Filter both IDs together and select the resulting rows.
        filterBatchOrders(batchOrders);
        Thread.sleep(2000);
        By orderRows = By.xpath("//a[@data-component-id='OrderId']");
        wait.until(driver -> driver.findElements(orderRows).size() == 2);

        selectAllFilteredOrderRows();
        Thread.sleep(2000);

        // Do not click an individual order row; that would change the selection.
        wait.until(ExpectedConditions.elementToBeClickable(clickMoreOrders));
        click(clickMoreOrders);
        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(shipConfirm));
        click(shipConfirm);
        Thread.sleep(2000);

        // Wait until the two filtered rows both show Shipped.
        By statuses = By.xpath(
                "//div[@data-component-id='MinimumStatusDescription']");
        FluentWait<WebDriver> shippedWait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(180))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);

        List<String> finalStatuses = shippedWait.until(d -> {
            click(refresh);

            List<WebElement> idElements = d.findElements(orderRows);
            List<WebElement> statusElements = d.findElements(statuses);

            if (idElements.size() != 2 || statusElements.size() != 2) {
                return null;
            }

            List<String> visibleIds = new ArrayList<>();
            for (WebElement idElement : idElements) {
                String id = idElement.getText();
                if (id == null || id.isBlank()) return null;
                visibleIds.add(id.trim());
            }

            if (!visibleIds.containsAll(expectedOrderIds)
                    || !expectedOrderIds.containsAll(visibleIds)) {
                throw new IllegalStateException(
                        "Filtered rows do not match the expected batch. Expected: "
                                + expectedOrderIds + " | Actual: " + visibleIds);
            }

            List<String> currentStatuses = new ArrayList<>();
            for (WebElement statusElement : statusElements) {
                String status = statusElement.getText();
                if (status == null || status.isBlank()) return null;
                currentStatuses.add(status.trim());
            }

            boolean allShipped = currentStatuses.stream()
                    .allMatch(status -> status.equalsIgnoreCase("Shipped"));
            return allShipped ? currentStatuses : null;
        });

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Batch Ship Confirm completed. Orders: "
                        + expectedOrderIds + " | Statuses: " + finalStatuses);

        System.out.println("Batch Ship Confirm completed. Orders: "
                + expectedOrderIds + " | Statuses: " + finalStatuses);
    }

    public void selectCurrentlyFilteredOrder(String expectedOrderId)
            throws InterruptedException {
        if (expectedOrderId == null || expectedOrderId.isBlank()) {
            throw new IllegalArgumentException(
                    "Expected Order ID cannot be null or empty.");
        }
        String actualOrderId =
                getAttribute(filterOrderByOrderId, "value");

        if (actualOrderId == null
                || !actualOrderId.trim()
                .equalsIgnoreCase(expectedOrderId.trim())) {

            throw new IllegalStateException(
                    "The currently filtered order does not match the expected order. "
                            + "Expected: " + expectedOrderId
                            + " | Actual: " + actualOrderId);
        }
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clickOrderToRedirectRelatedLinks));

        click(clickOrderToRedirectRelatedLinks);
        Thread.sleep(1500);
    }


}