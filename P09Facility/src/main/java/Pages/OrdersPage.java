package Pages;

import com.p09.framework.context.ScenarioContext;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import context.OrderData;
import context.OrderLineData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

public class OrdersPage extends BasePage {

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
    // WAVE NUMBER
    // =========================================================

    @FindBy(xpath = "//span[@data-component-id='OrderLineId']")
    public WebElement getOrderLineNumberForWaveNbr;

    @FindBy(xpath = "//ion-button[@data-component-id='footer-panel-action-Details']")
    public WebElement clickDetailsOrderLineForWaveNbr;

    @FindBy(xpath = "//div[@data-component-id='OrderPlanningRunId']")
    public WebElement getWaveNbr;


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

        System.out.println(
                clearButton.isSelected()
                        + " is Selected"
        );

        System.out.println(
                clearButton.isEnabled()
                        + " is Enabled"
        );

        if (clearButton.isEnabled()) {

            report.addReportStepWithScreenshot(
                    StepStatus.INFO,
                    "Filter Available"
            );

            click(clearButton);

            if (clearButton.isEnabled()) {

                click(clearButton);
            }

            Thread.sleep(2000);

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Cleared the Existing filter"
            );

        } else {

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Filter not Available"
            );
        }
    }


    // =========================================================
    // CREATE ORDER
    // =========================================================

    public String createOrder(String orderType, String orderPriority) throws InterruptedException {

        System.out.println("Creating Order");

        click(clickCreateOrderButton);
        Thread.sleep(2000);

        click(expandAllDuringOrderCreation);
        Thread.sleep(1000);

        type(enterOrderType, orderType);
        type(ordPriority, orderPriority);
        type(enterOrderOrgFacility, "P09");
        type(enterOrderDestFacility, "P09");

        click(selectFacility);
        Thread.sleep(1000);

        click(saveCreatedOrder);
        Thread.sleep(3000);

        String orderId = getCreatedOrderId.getText().trim();

        System.out.println("Created Order ID: " + orderId);

        ScenarioContext.set("Created Order", orderId);
        click(closeCreatedOrder);
        Thread.sleep(2000);
        return orderId;
    }


    // =========================================================
    // FILTER ORDER
    // =========================================================

    public void filterExistingOrder(
            String orderId)
            throws InterruptedException {
        System.out.println("Entered filter exitsing order");
        click(closeCreatedOrder);

        Thread.sleep(2000);

        type(
                filterOrderByOrderId,
                orderId
        );

        if (!getAttribute(
                filterOrderByOrderId,
                "value"
        ).equalsIgnoreCase(orderId)) {

            throw new IllegalStateException(
                    "Order ID filter value was not entered correctly. Expected: "
                            + orderId
            );
        }

        pressEnter(
                filterOrderByOrderId
        );

        Thread.sleep(2000);

        if (isDisplayed(
                clickOrderToRedirectRelatedLinks
        )) {

            click(
                    clickOrderToRedirectRelatedLinks
            );

            Thread.sleep(2000);

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Existing Order Filtered and Selected"
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

    public void createOrderLines(OrderData orderData) throws InterruptedException {

        String orderId = orderData.getOrderId();

        System.out.println("========================================");
        System.out.println("SELECTING CREATED ORDER");
        System.out.println("Order ID: " + orderId);
        System.out.println("========================================");

        /*
         * STEP 1:
         * Make sure we are back on the Orders list.
         */
        Thread.sleep(2000);

        /*
         * STEP 2:
         * Filter the Orders page using the newly created Order ID.
         */
        click(filterOrderByOrderId);
        type(filterOrderByOrderId, orderId);

        pressEnter(filterOrderByOrderId);
        Thread.sleep(3000);

        System.out.println("Filtered Orders using Order ID: " + orderId);

        /*
         * STEP 3:
         * SELECT THE CREATED ORDER.
         *
         * This MUST happen before Related Links.
         */
        click(clickOrderToRedirectRelatedLinks);
        Thread.sleep(2000);

        System.out.println("Created Order selected: " + orderId);

        /*
         * STEP 4:
         * Now open Related Links.
         */
        click(clickRelatedLinks);
        Thread.sleep(1500);

        System.out.println("Related Links opened");

        /*
         * STEP 5:
         * Navigate to Order Lines.
         */
        click(clickOrderLines);
        Thread.sleep(2500);

        System.out.println("Navigated to Order Lines");

        /*
         * STEP 6:
         * Validate whether the order already contains order lines.
         */
        if (isDisplayed(noResFnd)) {

            System.out.println(
                    "No order lines found. Creating "
                            + orderData.getOrderLines().size()
                            + " lines."
            );

            /*
             * STEP 7:
             * Create every order line dynamically.
             */
            for (OrderLineData orderLine : orderData.getOrderLines()) {

                createSingleOrderLine(orderLine);

            }
            click(redirectToOrdersFromOrderLine);


        } else {

            throw new RuntimeException(
                    "Order " + orderId
                            + " already contains Order Lines. "
                            + "Expected No records found."
            );
        }
    }


    // =========================================================
    // CREATE SINGLE ORDER LINE
    // =========================================================

    private void createSingleOrderLine(OrderLineData orderLine)
            throws InterruptedException {

        System.out.println(
                "Creating Order Line: "
                        + orderLine.getLineNumber()
                        + " | Item: "
                        + orderLine.getItem()
                        + " | Qty: "
                        + orderLine.getQuantity()
        );

        /*
         * Create Order Line
         */
        click(createOrderLine);
        Thread.sleep(2000);

        /*
         * Order Line Number
         */
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
        click(itemSearchOrder);
        Thread.sleep(2000);

        /*
         * Show All Filters
         */
        click(showAllFiltersAsnDetail);
        Thread.sleep(1000);

        /*
         * Search using Primary Barcode
         */
        type(primaryBarcode, orderLine.getItem());

        click(clickSearchAfterItemBarcodeEntered);
        Thread.sleep(2500);

        /*
         * Select Item
         */
        click(selectItemFromDetailSearch);
        Thread.sleep(500);

        click(submitItemSearch);
        Thread.sleep(2000);

        /*
         * Ordered Quantity
         */
        type(
                passOrderLineQty,
                orderLine.getQuantity()
        );

        /*
         * Save/Submit Order Line
         */
        Thread.sleep(2000);
        click(saveCreatedOrder);
        Thread.sleep(2500);

        /*
         * IMPORTANT:
         * Close the Order Line popup before proceeding
         * to the next Order Line.
         */
        click(closeCreatedOrder);
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
        click(clickOrderToRedirectRelatedLinks);
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

        Thread.sleep(4000);


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

        click(clickOrderToRedirectRelatedLinks);
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


        Thread.sleep(2000);

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

        Thread.sleep(2000);


        // =====================================================
        // WAIT FOR ALLOCATED
        // =====================================================

        waitForStatus(
                orderStatusValidation,
                () -> click(refresh),
                "Allocated"
        );


        Thread.sleep(2000);


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Allocated Successfully"
        );


        // =====================================================
        // GET WAVE NUMBER
        // =====================================================

        captureWaveNumber(
                orderData
        );
    }


    // =========================================================
    // CAPTURE WAVE NUMBER
    // =========================================================

    private void captureWaveNumber(
            OrderData orderData)
            throws InterruptedException {

        // =====================================================
        // SELECT ORDER
        // =====================================================

        click(
                clickOrderToRedirectRelatedLinks
        );

        Thread.sleep(2000);


        // =====================================================
        // RELATED LINKS
        // =====================================================

        click(
                clickRelatedLinks
        );

        Thread.sleep(2000);


        // =====================================================
        // ORDER LINES
        // =====================================================

        click(
                clickOrderLines
        );

        Thread.sleep(2000);
        click(
                getOrderLineNumberForWaveNbr
        );

        Thread.sleep(2000);
        click(
                clickDetailsOrderLineForWaveNbr
        );

        Thread.sleep(2000);


        if (isDisplayed(
                expandAllDuringOrderCreation
        )) {

            click(
                    expandAllDuringOrderCreation
            );
        }

        Thread.sleep(2000);


        hover(
                getWaveNbr
        );


        // =====================================================
        // CAPTURE WAVE NUMBER
        // =====================================================

        String waveNumber =
                getText(getWaveNbr);


        if (waveNumber == null
                || waveNumber.isBlank()) {

            throw new IllegalStateException(
                    "Wave number was not captured for order: "
                            + orderData.getOrderId()
            );
        }


        ScenarioContext.set(
                "wave nbr",
                waveNumber
        );


        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Wave Number: "
                        + waveNumber
        );


        System.out.println(
                "Wave Number: "
                        + waveNumber
        );


        // =====================================================
        // STORE WAVE NUMBER IN ORDER DATA CONTEXT
        // =====================================================

        ScenarioContext.set(
                "Wave Number - "
                        + orderData.getOrderId(),
                waveNumber
        );


        // =====================================================
        // CLOSE DETAILS
        // =====================================================

        click(
                closeCreatedOrder
        );
        Thread.sleep(2000);
        click(
                redirectToOrdersFromOrderLine
        );
        Thread.sleep(2000);
    }
}