
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
                menuToggleButton);
        click(
                searchBarInLandingPage);
        type(searchBarInLandingPage,uiName);
        if (Objects.requireNonNull(searchBarInLandingPage.getAttribute("value")).equalsIgnoreCase(uiName)){
            if(isDisplayed(clickOrdersFromOptions)) {
                click(clickOrdersFromOptions);
                if (isDisplayed(showingTextInOrdersPageForVerification)) {
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
            click(clearButton);
            if (clearButton.isEnabled()){
                click(clearButton);
            }
            Thread.sleep(2000);
            report.addReportStepWithScreenshot(StepStatus.PASS,"Cleared the Exitsing filter");

        }else {
            report.addReportStepWithScreenshot(StepStatus.PASS, "Filter not Avaialble");
        }
        createOrder();
    }

    public void createOrder() throws InterruptedException{
        click(clickCreateOrderButton);
        if (isDisplayed(expandAllDuringOrderCreation)){
            click(expandAllDuringOrderCreation);
            type(enterOrderType,"190");
            if (getAttribute(enterOrderType,"value").equalsIgnoreCase("190")){
                type(enterOrderOrgFacility,"P09");
                if (getAttribute(enterOrderOrgFacility,"value").equalsIgnoreCase("P09")){
                    if(isDisplayed(selectFacility)){
                        click(selectFacility);
                    }
                    hover(enterOrderDestFacility);
                    type(enterOrderDestFacility,"P09");
                    if(isDisplayed(selectFacility)){
                        click(selectFacility);
                    }
                    if (getAttribute(enterOrderDestFacility,"value").equalsIgnoreCase("P09")) {
                        Thread.sleep(2000);
                        click(saveCreatedOrder);
                        report.addReportStepWithScreenshot(StepStatus.PASS,"Order Created");
                        ScenarioContext.set("Created Order",getText(getCreatedOrderId));
                        report.addReportStepWithoutScreenshot(StepStatus.PASS,"order Id:"+getCreatedOrderId.getText());
                    }
                }
            }
        }
        click(closeCreatedOrder);
        Thread.sleep(2000);
        type(filterOrderByOrderId,ScenarioContext.get("Created Order").toString());
        if (getAttribute(filterOrderByOrderId,"value").equalsIgnoreCase(ScenarioContext.get("Created Order").toString())){
            pressEnter(filterOrderByOrderId);
            Thread.sleep(2000);
            click(clickOrderToRedirectRelatedLinks);
            Thread.sleep(2000);
            click(clickRelatedLinks);
            if (isDisplayed(clickOrderLines)){
                click(clickOrderLines);
                if (isDisplayed(noResFnd)){
                    click(createOrderLine);
                    if (isDisplayed(expandAllDuringOrderCreation)){
                        click(expandAllDuringOrderCreation);
                        type(passOrderLineValue,"1");

//                        type(passItemId,"19005");
                        click(itemSearchOrder);
                        click(
                                showAllFiltersAsnDetail);
                        type(primaryBarcode,"19005");
                        Thread.sleep(2000);
                        clickSearchAfterItemBarcodeEntered.click();
                        Thread.sleep(2000);
                        selectItemFromDetailSearch.click();
                        Thread.sleep(2000);
                        submitItemSearch.click();
                        Thread.sleep(2000);
                        type(passOrderLineQty,"5");
                        click(saveCreatedOrder);
                        click(closeCreatedOrder);
                        Thread.sleep(2000);
                        click(redirectToOrdersFromOrderLine);
                        Thread.sleep(2000);
                        click(clickOrderToRedirectRelatedLinks);
                        Thread.sleep(2000);
                        click(clickMoreOrders);
                        if (isDisplayed(assignPipeline)){
                            click(assignPipeline);
                            Thread.sleep(4000);
                            if (selectPipeline.isDisplayed()){
                                click(selectPipeline);
                                if (getText(selectDemoPipeline).contains("DEMO")){
                                    click(selectDemoPipeline);
                                }
                            }
                            Thread.sleep(3000);
                        }
                        click(submitItemSearch);
                        Thread.sleep(3000);
                        waitForStatus(orderStatusValidation,
                                () -> click(refresh),
                                "Released");
                        report.addReportStepWithScreenshot(StepStatus.PASS,"Order Created");
                        click(clickOrderToRedirectRelatedLinks);
                        Thread.sleep(2000);
                        click(clickMoreOrders);
                        if (isDisplayed(runWavesFromOrders)){
                            click(runWavesFromOrders);
                        }
                        if (isDisplayed(orderPlanStr)){
                            Thread.sleep(2000);
                            click(orderPlanStr);
                            Thread.sleep(2000);
                            type(typeOrderPlanStr,"190");
                            Thread.sleep(2000);
                            click(select190OrdPlan);
                            Thread.sleep(2000);
                            click(submitItemSearch);
                            Thread.sleep(2000);
                            waitForStatus(orderStatusValidation,
                                    () -> click(refresh),
                                    "Allocated");
                            Thread.sleep(2000);
                            click(clickOrderToRedirectRelatedLinks);
                            Thread.sleep(2000);
                            click(clickRelatedLinks);
                            Thread.sleep(2000);
                            click(clickOrderLines);
                            Thread.sleep(2000);
                            click(getOrderLineNumberForWaveNbr);
                            Thread.sleep(2000);
                            click(clickDetailsOrderLineForWaveNbr);
                            Thread.sleep(2000);
                            if (isDisplayed(expandAllDuringOrderCreation)) {
                                click(expandAllDuringOrderCreation);
                            }
                            Thread.sleep(2000);
                            hover(getWaveNbr);
                            ScenarioContext.set("wave nbr",getWaveNbr.getText());
                            System.out.println("Wave Number "+ScenarioContext.get("wave nbr"));
                            report.addReportStepWithoutScreenshot(StepStatus.PASS,ScenarioContext.get("wave nbr").toString());
                            click(closeCreatedOrder);
                            Thread.sleep(2000);
                            click(redirectToOrdersFromOrderLine);
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
