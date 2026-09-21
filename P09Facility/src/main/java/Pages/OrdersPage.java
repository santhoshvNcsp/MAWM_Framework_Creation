package Pages;

import com.p09.framework.context.ScenarioContext;
import com.p09.framework.driver.DriverManager;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;
import java.util.Random;

public class OrdersPage extends BasePage {
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
    @FindBy(xpath = "//button[@data-component-id='CreateOrder']")
    public WebElement clickCreateOrderButton;
    @FindBy(xpath = "//ion-button[@data-component-id='ExpandAll']")
    public WebElement expandAllDuringOrderCreation;
    @FindBy(xpath = "//input[@placeholder='Order type']")
    public WebElement enterOrderType;
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
    @FindBy(xpath = "//ion-input[@data-component-id='OrderId']/label/div/input")
    public WebElement filterOrderByOrderId;
    @FindBy(xpath = "//span[text()=' Order : ']")
    public WebElement clickOrderToRedirectRelatedLinks;
    @FindBy(xpath = "//button[@data-component-id='relatedLinks']")
    public WebElement clickRelatedLinks;
    @FindBy(linkText = "Order Lines")
    public WebElement clickOrderLines;
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
    @FindBy(linkText = "Orders")
    public WebElement redirectToOrdersFromOrderLine;
    @FindBy(xpath = "//div[text()=' More ']")
    public WebElement clickMoreOrders;
    @FindBy(xpath = "//span[text()='Assign Pipeline']")
    public WebElement assignPipeline;
    @FindBy(xpath = "//ion-input[@data-component-id='PipelineId']/label/div[2]/input")
    public WebElement selectPipeline;
    @FindBy(xpath = "//p[text()=' DEMO_NO_RTE_PIPELINE ']")
    public WebElement selectDemoPipeline;
    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;
    @FindBy(xpath = "//span[text()='Run Wave']")
    public WebElement runWavesFromOrders;
    @FindBy(xpath = "//ion-input[@data-component-id='PlanningStrategyId']/label/div[2]/input")
    public WebElement orderPlanStr;
    @FindBy(xpath = "//ion-searchbar[@data-component-id='searchbar']/div/input")
    public WebElement typeOrderPlanStr;
    @FindBy(xpath = "//p[text()=' 190 order planning strategy ']")
    public WebElement select190OrdPlan;
    @FindBy(xpath = "//span[@data-component-id='OrderLineId']")
    public WebElement getOrderLineNumberForWaveNbr;
    @FindBy(xpath = "//ion-button[@data-component-id='footer-panel-action-Details']")
    public WebElement clickDetailsOrderLineForWaveNbr;
    @FindBy(xpath = "//div[@data-component-id='OrderPlanningRunId']")
    public WebElement getWaveNbr;

    public static final By orderStatusValidation=By.xpath(
            ("(//div[@data-component-id='MinimumStatusDescription'])[1]"
    ));


    public void navigateToAnyUi(
            String uiName)
            throws InterruptedException {
        click(
                menuToggleButton,
                "Clicked"
        );
        click(
                searchBarInLandingPage,
                "Search Bar in Landing Page"
        );
        type(searchBarInLandingPage,uiName,"Search Bar");
        if (Objects.requireNonNull(searchBarInLandingPage.getAttribute("value")).equalsIgnoreCase(uiName)){
            if(isDisplayed(clickOrdersFromOptions,"Orders drop down displayed")) {
                click(clickOrdersFromOptions, "User navigated to Orders Page");
                if (isDisplayed(showingTextInOrdersPageForVerification,"Showing")) {
                    report.addReportStepWithScreenshot(StepStatus.PASS, "user entered the orders page");
                    clearCreatedDateTimeFilter();
                }
            }
        }else {
            report.addReportStepWithScreenshot(StepStatus.FAIL,"search bar contains some other value");
        }
    }

    public void clearCreatedDateTimeFilter() throws InterruptedException{
        System.out.println(clearButton.isSelected()+" is Selected");
        System.out.println(clearButton.isEnabled()+" is Enabled");
        if (clearButton.isEnabled()) {
            report.addReportStepWithScreenshot(StepStatus.INFO, "Filter Available");
            click(clearButton, "Cleared the old filter");
            if (clearButton.isEnabled()){
                click(clearButton, "Cleared the old filter");
            }
            Thread.sleep(2000);
            report.addReportStepWithScreenshot(StepStatus.PASS,"Cleared the Exitsing filter");

        }else {
            report.addReportStepWithScreenshot(StepStatus.PASS, "Filter not Avaialble");
        }
        createOrder();
    }

    public void createOrder() throws InterruptedException{
        click(clickCreateOrderButton,"Create Order Button Clicked");
        if (isDisplayed(expandAllDuringOrderCreation,"Expand All")){
            click(expandAllDuringOrderCreation,"Expanded");
            type(enterOrderType,"190","Order Type Value Entered");
            if (getAttribute(enterOrderType,"value","Order Type Validation").equalsIgnoreCase("190")){
                type(enterOrderOrgFacility,"P09","Org Facility");
                if (getAttribute(enterOrderOrgFacility,"value","Org Facility Validation").equalsIgnoreCase("P09")){
                    if(isDisplayed(selectFacility,"conf")){
                        click(selectFacility,"");
                    }
                    hover(enterOrderDestFacility);
                    type(enterOrderDestFacility,"P09","Destination Facility");
                    if(isDisplayed(selectFacility,"conf")){
                        click(selectFacility,"");
                    }
                    if (getAttribute(enterOrderDestFacility,"value","Dest Facility Validation").equalsIgnoreCase("P09")) {
                        Thread.sleep(2000);
                        click(saveCreatedOrder,"Order Created");
                        report.addReportStepWithScreenshot(StepStatus.PASS,"Order Created");
                        ScenarioContext.set("Created Order",getText(getCreatedOrderId,"Order Value"));
                        report.addReportStepWithoutScreenshot(StepStatus.PASS,"order Id:"+getCreatedOrderId.getText());
                    }
                }
            }
        }
        click(closeCreatedOrder,"Closed Order in Created Status");
        Thread.sleep(2000);
        type(filterOrderByOrderId,ScenarioContext.get("Created Order").toString(),"Filter Based on Order");
        if (getAttribute(filterOrderByOrderId,"value","Order").equalsIgnoreCase(ScenarioContext.get("Created Order").toString())){
            pressEnter(filterOrderByOrderId,"Filtered");
            Thread.sleep(2000);
            click(clickOrderToRedirectRelatedLinks,"Order Id Selected");
            Thread.sleep(2000);
            click(clickRelatedLinks,"Related Links From Orders");
            if (isDisplayed(clickOrderLines,"Order Lines")){
                click(clickOrderLines,"Navigated to order lines from orders");
                if (isDisplayed(noResFnd,"No Records Found")){
                    click(createOrderLine,"Order Line Create");
                    if (isDisplayed(expandAllDuringOrderCreation,"expand all order lines")){
                        click(expandAllDuringOrderCreation,"expanded order lines fields");
                        type(passOrderLineValue,"1","Order line entered");

//                        type(passItemId,"19005","Item Id Passed");
                        click(itemSearchOrder,"Clicked Search button to filter item");
                        click(
                                showAllFiltersAsnDetail,
                                "Clicked Show All Filters"
                        );
                        type(primaryBarcode,"19005","");
                        Thread.sleep(2000);
                        clickSearchAfterItemBarcodeEntered.click();
                        Thread.sleep(2000);
                        selectItemFromDetailSearch.click();
                        Thread.sleep(2000);
                        submitItemSearch.click();
                        Thread.sleep(2000);
                        type(passOrderLineQty,"5","OrderlineQty Passed");
                        click(saveCreatedOrder,"save order line");
                        click(closeCreatedOrder,"Closed Order Lines");
                        Thread.sleep(2000);
                        click(redirectToOrdersFromOrderLine,"OrderLine - Orders");
                        Thread.sleep(2000);
                        click(clickOrderToRedirectRelatedLinks,"Order Id Selected");
                        Thread.sleep(2000);
                        click(clickMoreOrders,"More clicked");
                        if (isDisplayed(assignPipeline,"checking assign pipeline option")){
                            click(assignPipeline,"Assign Pipeline Clicked");
                            Thread.sleep(4000);
                            if (selectPipeline.isDisplayed()){
                                click(selectPipeline,"Drop down opened");
                                if (getText(selectDemoPipeline,"").contains("DEMO")){
                                    click(selectDemoPipeline,"Selected");
                                }
                            }
                            Thread.sleep(3000);
                        }
                        click(submitItemSearch,"Submitted the PipeLine");
                        Thread.sleep(3000);
                        waitForStatus(orderStatusValidation,
                                () -> click(refresh, "Refreshing ASN Page" + driver.findElement(orderStatusValidation).getText()),
                                "Released");
                        report.addReportStepWithScreenshot(StepStatus.PASS,"Order Created");
                        click(clickOrderToRedirectRelatedLinks,"Order Id Selected");
                        Thread.sleep(2000);
                        click(clickMoreOrders,"");
                        if (isDisplayed(runWavesFromOrders,"Run Waves From Orders")){
                            click(runWavesFromOrders,"Run Waves");
                        }
                        if (isDisplayed(orderPlanStr,"Order Plan Str input")){
                            Thread.sleep(2000);
                            click(orderPlanStr,"orderPlanStr");
                            Thread.sleep(2000);
                            type(typeOrderPlanStr,"190","");
                            Thread.sleep(2000);
                            click(select190OrdPlan,"clicked");
                            Thread.sleep(2000);
                            click(submitItemSearch,"Submitted order plan str");
                            Thread.sleep(2000);
                            waitForStatus(orderStatusValidation,
                                    () -> click(refresh, "Refreshing ASN Page" + driver.findElement(orderStatusValidation).getText()),
                                    "Allocated");
                            Thread.sleep(2000);
                            click(clickOrderToRedirectRelatedLinks,"Order Id Selected");
                            Thread.sleep(2000);
                            click(clickRelatedLinks,"Related Links From Orders");
                            Thread.sleep(2000);
                            click(clickOrderLines,"Navigated to order lines from orders");
                            Thread.sleep(2000);
                            click(getOrderLineNumberForWaveNbr,"");
                            Thread.sleep(2000);
                            click(clickDetailsOrderLineForWaveNbr,"");
                            Thread.sleep(2000);
                            if (isDisplayed(expandAllDuringOrderCreation,"Expand All")) {
                                click(expandAllDuringOrderCreation, "Expanded");
                            }
                            Thread.sleep(2000);
                            hover(getWaveNbr);
                            ScenarioContext.set("wave nbr",getWaveNbr.getText());
                            System.out.println("Wave Number "+ScenarioContext.get("wave nbr"));
                            report.addReportStepWithoutScreenshot(StepStatus.PASS,ScenarioContext.get("wave nbr").toString());
                            click(closeCreatedOrder,"Closed Order Lines");
                            Thread.sleep(2000);
                            click(redirectToOrdersFromOrderLine,"OrderLine - Orders");
                            Thread.sleep(2000);

                        }
                    }
                }
            }




            Thread.sleep(4000);
            report.addReportStepWithScreenshot(StepStatus.PASS,"Order Filtered and clicked");
        }



    }
}
