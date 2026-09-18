package Pages;

import com.aventstack.extentreports.util.Assert;
import com.p09.framework.config.ConfigManager;
import com.p09.framework.context.ScenarioContext;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.restassured.response.Response;

import model.InboundData;
import model.InboundItem;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class AsnPage extends BasePage {

    /*
     * Maximum number of ILPN records allowed
     * in a single createIlpnAndInventory request.
     *
     * Example:
     *
     * 40 Excel rows
     * MAX = 30
     *
     * Request 1 -> 30 ILPNs
     * Request 2 -> 10 ILPNs
     */
    private static final int MAX_ILPN_CREATE_REQUEST = 3;


    // =========================================================
    // ASN UI LOCATORS
    // =========================================================
    private final By asnStatusValidation =
            By.xpath("(//div[@data-component-id='AsnStatusDescription'])[1]");
    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;
    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;
    @FindBy(id = "ASN")
    public WebElement clickAsnSFromMenu;
    @FindBy(xpath = "//span[@data-component-id='ASNs']")
    public WebElement asnTextInAsnUi;
    @FindBy(xpath = "//button[@data-component-id='GenerateASN']")
    public WebElement createAsnButtonInAsnUi;
    @FindBy(xpath = "((//ion-input[@data-component-id='AsnId'])//div[2]/input)[2]")
    public WebElement asnIdFromGenerateAsn;
    @FindBy(xpath = "//input[@placeholder='Date']")
    public WebElement clickDateField;
    @FindBy(xpath = "//ion-input[@data-component-id='date-input-field']/div/input")
    public WebElement asnEstimatedDeliveryDate;
    @FindBy(xpath = "//ion-button[@data-component-id='submit-btn']")
    public WebElement submitAsnOnceCreated;
    @FindBy(xpath = "//ion-input[@data-component-id='AsnId']/label/div/input")
    public WebElement filterAsnById;
    @FindBy(xpath = "//button[@data-component-id='relatedLinks']")
    public WebElement clickRelatedLinks;
    @FindBy(linkText = "ASN Details")
    public WebElement asnDetailsText;
    @FindBy(xpath = "//button[@data-component-id='CreateASNDetail']")
    public WebElement createAsnDetail;
    @FindBy(xpath = "//ion-input[@data-component-id='AsnId']")
    public WebElement asnIdInDetail;
    @FindBy(xpath = "//button[@data-component-id='ItemIdSearch']")
    public WebElement selectItemSearchInDetail;
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
    @FindBy(xpath = "//ion-input[@data-component-id='ShippedQuantityInBridgedUomWithoutUomId']/label/div[2]/input")
    public WebElement shippedQty;
    @FindBy(xpath = "//ion-input[@data-component-id='QuantityUom']/label/div[2]/input")
    public WebElement quantityUomDrop;
    @FindBy(xpath = "//ion-searchbar[@data-component-id='searchbar']/div/input")
    public WebElement typeQtyUom;
    @FindBy(xpath = "//p[contains(text(),' Unit ')]")
    public WebElement selectUomAsUnit;
    @FindBy(xpath = "//ion-button[@data-component-id='Save']")
    public WebElement saveAsnDetail;
    @FindBy(xpath = "//button[@data-component-id='Close-Icon']")
    public WebElement closeCreatedAsnDetail;


    // =========================================================
    // WM MOBILE LOCATORS
    // =========================================================
    @FindBy(linkText = "ASNs")
    public WebElement redirectToAsnFromAsnDetail;
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
    @FindBy(xpath = "//input[@data-component-id='acceptquantity_naturalquantityfield_units']")
    public WebElement passQtyReceive;


    // =========================================================
    // VERIFY / PUTAWAY LOCATORS
    // =========================================================
    @FindBy(xpath = "//button[@data-component-id='action_endlpn_button']")
    public WebElement endLPNReceive;
    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;
    @FindBy(xpath = "//div[text()=' More ']")
    public WebElement more;
    @FindBy(xpath = "//span[text()='Verify ASN Without Variance View']")
    public WebElement verify;
    @FindBy(xpath = "//ion-icon[@data-component-id='close']")
    public WebElement closeMenuToggle;
    @FindBy(linkText = "LPN (Inventory)")
    public WebElement lpnInventoryAsn;
    @FindBy(xpath = "//ion-label[@data-component-id='190userdirectedputaway']")
    public WebElement clickUD;
    @FindBy(xpath = "//input[@placeholder='Scan Container']")
    public WebElement scanContainer;
    @FindBy(xpath = "//input[@placeholder='Scan Item']")
    public WebElement scanItemUD;
    @FindBy(xpath = "//input[@data-component-id='acceptitemquantity_naturalquantityfield_units']")
    public WebElement scanQtyUD;
    @FindBy(xpath = "//input[@placeholder='Scan Location']")
    public WebElement scanLocUD;
    @FindBy(xpath = "//span[normalize-space(.)='Current location :']/following-sibling::span")
    public WebElement currentLocation;


    @FindBy(xpath = "//span[text()='Edit']")
    public WebElement editAsn;

    @FindBy(xpath = "//ion-input[@data-component-id='VendorId']//input")
    public WebElement vendorId;

    @FindBy(xpath = "//button[@data-component-id='Close-Icon']")
    public WebElement closeAsnEdit;


    // =========================================================
    // EXISTING NAVIGATION METHOD
    // =========================================================

    public AsnPage() {
        super();
    }


    // =========================================================
    // EXISTING ASN UI CREATION
    // =========================================================

    public void navigateToUi(String uiName)
            throws InterruptedException {

        click(
                menuToggleButton,
                "Clicked"
        );

        click(
                searchBarInLandingPage,
                "Search Bar in Landing Page"
        );

        if (uiName.equalsIgnoreCase("ASNs")) {

            type(
                    searchBarInLandingPage,
                    uiName,
                    "Search Bar in Landing Page"
            );

            click(
                    clickAsnSFromMenu,
                    "Click ASN from Menu"
            );

            if (isDisplayed(
                    asnTextInAsnUi,
                    "ASN Text in ASN UI"
            )) {

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "Successfully navigated to "
                                + uiName
                                + " page"
                );

            } else {

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        "Failed to navigate to "
                                + uiName
                                + " page"
                );
            }

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Navigated to "
                            + uiName
                            + " page"
            );
        }
    }


    // =========================================================
    // EXISTING NAVIGATE ANY UI
    // =========================================================

    public String provideAsnDetails(
            List<String> items,
            String shippedQtyAsn,
            int asn, String status)
            throws InterruptedException {

        System.out.println(
                "Total Items " + items.size()
        );


        String[] quantitiesAsn =
                shippedQtyAsn.split(",");


        List<String> quantitiesList =
                new ArrayList<>();


        for (String quantity : quantitiesAsn) {

            quantitiesList.add(
                    quantity.trim()
            );
        }


        System.out.println(
                "Quantities "
                        + quantitiesList
        );


        int totalDetail =
                items.size();

        int cntr = 0;


//        Thread.sleep(5000);

//        waitForPageLoad();

//        wait.until(
//                ExpectedConditions
//                        .elementToBeClickable(
//                                createAsnButtonInAsnUi
//                        )
//        );


        click(
                createAsnButtonInAsnUi,
                "Clicked generate ASN button"
        );


        Random random =
                new Random();


        type(
                asnIdFromGenerateAsn,
                "ASN190"
                        + random.nextInt(100000),
                "ASN ID"
        );


        System.out.println(
                "Tried with normal click"
        );


        Thread.sleep(5000);


        String createdAsnId =
                asnIdFromGenerateAsn
                        .getAttribute("value");


        String ASN =
                "CreatedASN " + asn;


        ScenarioContext.set(
                ASN,
                createdAsnId
        );


        System.out.println(
                "Stored in ScenarioContext: "
                        + ASN
                        + " = "
                        + createdAsnId
        );


        click(
                submitAsnOnceCreated,
                "Clicked Submit ASN button"
        );


        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Created ASN ID: "
                        + createdAsnId
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "ASN Created In Planning Status"
        );


//        Thread.sleep(8000);


        type(
                filterAsnById,
                createdAsnId,
                "Filter ASN By ID"
        );


        if (Objects.equals(
                filterAsnById.getAttribute("value"),
                createdAsnId
        )) {

            pressEnter(
                    filterAsnById,
                    "Pressed Enter to filter ASN by ID"
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Filtered ASN By ID: "
                            + createdAsnId
            );

        } else {

            report.addReportStepWithScreenshot(
                    StepStatus.FAIL,
                    "Failed to filter ASN By ID: "
                            + createdAsnId
            );

            System.exit(0);
        }


        Thread.sleep(5000);


        WebElement asnGet =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        By.xpath(
                                                "//span[@data-component-id='AsnId' and normalize-space()='"
                                                        + createdAsnId
                                                        + "']"
                                        )
                                )
                );


        click(
                asnGet,
                "Clicked ASN ID from the list"
        );


//        Thread.sleep(5000);


        click(
                clickRelatedLinks,
                "Clicked Related Links"
        );


        click(
                asnDetailsText,
                "Clicked ASN Details"
        );


//        waitForPageLoad();


        while (totalDetail != 0) {

            Thread.sleep(3000);


//            wait.until(
//                    ExpectedConditions
//                            .elementToBeClickable(
//                                    createAsnDetail
//                            )
//            );


            click(
                    createAsnDetail,
                    "Clicked Create ASN Detail"
            );


            Thread.sleep(3000);


            if (Objects.equals(
                    asnIdInDetail
                            .getAttribute("value"),
                    createdAsnId
            )) {

                click(
                        selectItemSearchInDetail,
                        "Clicked Item Search"
                );


                click(
                        showAllFiltersAsnDetail,
                        "Clicked Show All Filters"
                );


//                Thread.sleep(5000);


                type(
                        primaryBarcode,
                        items.get(cntr),
                        "Primary Barcode"
                );


                if (Objects.equals(
                        primaryBarcode
                                .getAttribute("value"),
                        items.get(cntr)
                )) {

                    pressEnter(
                            primaryBarcode,
                            "Pressed Enter for Primary Barcode"
                    );


                    report.addReportStepWithScreenshot(
                            StepStatus.PASS,
                            "Entered Primary Barcode: "
                                    + items.get(cntr)
                    );


                    click(
                            clickSearchAfterItemBarcodeEntered,
                            "Clicked Search After Entering Primary Barcode"
                    );


                    click(
                            selectItemFromDetailSearch,
                            "Selected Item From Detail Search"
                    );


                    click(
                            submitItemSearch,
                            "Clicked Submit Item Search"
                    );


                    waitForPageLoad();


                    type(
                            shippedQty,
                            quantitiesList.get(cntr),
                            "Shipped Quantity"
                    );


                    Thread.sleep(2000);


                    click(
                            quantityUomDrop,
                            "Clicked Quantity UOM Drop"
                    );


                    Thread.sleep(2000);


                    type(
                            typeQtyUom,
                            "Unit",
                            "Type Quantity UOM"
                    );
//                    Thread.sleep(4000);


                    click(
                            selectUomAsUnit,
                            "Selected UOM as Unit"
                    );


                    Thread.sleep(3000);


                    click(
                            saveAsnDetail,
                            "Clicked Save ASN Detail"
                    );


                    cntr++;


                } else {

                    report.addReportStepWithScreenshot(
                            StepStatus.FAIL,
                            "Failed to enter Primary Barcode: "
                                    + items.get(cntr)
                    );

                    System.exit(0);
                }


                click(
                        closeCreatedAsnDetail,
                        "Clicked Close Created ASN Detail"
                );
            }


            totalDetail--;
        }


        click(
                redirectToAsnFromAsnDetail,
                "Redirected to ASN from ASN Details"
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Redirected To ASNs Page From ASN Details Page"
        );
//        private final By asnStatusValidation =
//                By.xpath("(//div[@data-component-id='AsnStatusDescription'])[1]");
        waitForStatus(asnStatusValidation,
                () -> click(refresh, "Refreshing ASN Page" + driver.findElement(asnStatusValidation).getText()),
                status);


        return createdAsnId;
    }

    public String provideVendorSpecificAsnDetails(
            List<String> items,
            String shippedQtyAsn,
            int asn,
            String status,
            String vendorName)
            throws InterruptedException {

        // =========================================================
        // VALIDATE INPUT
        // =========================================================

        if (vendorName == null || vendorName.isBlank()) {
            throw new IllegalArgumentException(
                    "Vendor name cannot be null or empty"
            );
        }

        vendorName = vendorName.trim();

        System.out.println(
                "Creating ASN for Vendor: "
                        + vendorName
        );

        System.out.println(
                "Total Items: "
                        + items.size()
        );

        // =========================================================
        // PARSE QUANTITIES
        // =========================================================

        String[] quantitiesAsn =
                shippedQtyAsn.split(",");

        List<String> quantitiesList =
                new ArrayList<>();

        for (String quantity :
                quantitiesAsn) {

            quantitiesList.add(
                    quantity.trim()
            );
        }

        if (quantitiesList.size() != items.size()) {
            throw new IllegalArgumentException(
                    "Item count ("
                            + items.size()
                            + ") does not match quantity count ("
                            + quantitiesList.size()
                            + ")"
            );
        }

        System.out.println(
                "Quantities: "
                        + quantitiesList
        );

        int totalDetail =
                items.size();

        int cntr = 0;

        // =========================================================
        // GENERATE ASN
        // =========================================================

        click(
                createAsnButtonInAsnUi,
                "Clicked generate ASN button"
        );

        // =========================================================
        // GENERATE ASN ID
        // =========================================================

        Random random =
                new Random();

        type(
                asnIdFromGenerateAsn,
                "ASN190"
                        + random.nextInt(100000),
                "ASN ID"
        );

        System.out.println(
                "Tried with normal click"
        );

        Thread.sleep(5000);

        // =========================================================
        // GET CREATED ASN
        // =========================================================

        String createdAsnId =
                asnIdFromGenerateAsn
                        .getAttribute("value");

        if (createdAsnId == null
                || createdAsnId.isBlank()) {

            throw new IllegalStateException(
                    "Created ASN ID is empty"
            );
        }

        // =========================================================
        // STORE ASN
        // =========================================================

        String ASN =
                "CreatedASN " + asn;

        ScenarioContext.set(
                ASN,
                createdAsnId
        );

        System.out.println(
                "Stored in ScenarioContext: "
                        + ASN
                        + " = "
                        + createdAsnId
        );

        // =========================================================
        // SUBMIT ASN
        // =========================================================

        click(
                submitAsnOnceCreated,
                "Clicked Submit ASN button"
        );

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Created ASN ID: "
                        + createdAsnId
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "ASN Created In Planning Status"
        );

        // =========================================================
        // FILTER ASN
        // =========================================================

        type(
                filterAsnById,
                createdAsnId,
                "Filter ASN By ID"
        );

        if (Objects.equals(
                filterAsnById.getAttribute("value"),
                createdAsnId)) {

            pressEnter(
                    filterAsnById,
                    "Pressed Enter to filter ASN by ID"
            );

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Filtered ASN By ID: "
                            + createdAsnId
            );

        } else {

            report.addReportStepWithScreenshot(
                    StepStatus.FAIL,
                    "Failed to filter ASN By ID: "
                            + createdAsnId
            );

            throw new IllegalStateException(
                    "Failed to filter ASN by ID: "
                            + createdAsnId
            );
        }

        Thread.sleep(5000);

        // =========================================================
        // OPEN ASN
        // =========================================================

        WebElement asnGet =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        By.xpath(
                                                "//span[@data-component-id='AsnId' and normalize-space()='"
                                                        + createdAsnId
                                                        + "']"
                                        )
                                )
                );

        click(
                asnGet,
                "Clicked ASN ID from the list"
        );

        Thread.sleep(3000);

        // =========================================================
        // OPEN ASN DETAILS
        // =========================================================

        click(
                clickRelatedLinks,
                "Clicked Related Links"
        );

        click(
                asnDetailsText,
                "Clicked ASN Details"
        );

        // =========================================================
        // CREATE ASN LINE DETAILS
        // =========================================================

        while (totalDetail != 0) {

            Thread.sleep(3000);

            click(
                    createAsnDetail,
                    "Clicked Create ASN Detail"
            );

            Thread.sleep(3000);

            if (Objects.equals(
                    asnIdInDetail.getAttribute("value"),
                    createdAsnId)) {

                // =================================================
                // ITEM SEARCH
                // =================================================

                click(
                        selectItemSearchInDetail,
                        "Clicked Item Search"
                );

                click(
                        showAllFiltersAsnDetail,
                        "Clicked Show All Filters"
                );

                // =================================================
                // ENTER ITEM
                // =================================================

                type(
                        primaryBarcode,
                        items.get(cntr),
                        "Primary Barcode"
                );

                if (Objects.equals(
                        primaryBarcode.getAttribute("value"),
                        items.get(cntr))) {

                    pressEnter(
                            primaryBarcode,
                            "Pressed Enter for Primary Barcode"
                    );

                    report.addReportStepWithScreenshot(
                            StepStatus.PASS,
                            "Entered Primary Barcode: "
                                    + items.get(cntr)
                    );

                    click(
                            clickSearchAfterItemBarcodeEntered,
                            "Clicked Search After Entering Primary Barcode"
                    );

                    click(
                            selectItemFromDetailSearch,
                            "Selected Item From Detail Search"
                    );

                    click(
                            submitItemSearch,
                            "Clicked Submit Item Search"
                    );

                    waitForPageLoad();

                    // =================================================
                    // SHIPPED QUANTITY
                    // =================================================

                    type(
                            shippedQty,
                            quantitiesList.get(cntr),
                            "Shipped Quantity"
                    );

                    Thread.sleep(2000);

                    // =================================================
                    // QUANTITY UOM
                    // =================================================

                    click(
                            quantityUomDrop,
                            "Clicked Quantity UOM Drop"
                    );

                    Thread.sleep(2000);

                    type(
                            typeQtyUom,
                            "Unit",
                            "Type Quantity UOM"
                    );

                    click(
                            selectUomAsUnit,
                            "Selected UOM as Unit"
                    );

                    Thread.sleep(3000);

                    click(
                            saveAsnDetail,
                            "Clicked Save ASN Detail"
                    );

                    cntr++;

                } else {

                    report.addReportStepWithScreenshot(
                            StepStatus.FAIL,
                            "Failed to enter Primary Barcode: "
                                    + items.get(cntr)
                    );

                    throw new IllegalStateException(
                            "Failed to enter Primary Barcode: "
                                    + items.get(cntr)
                    );
                }

                click(
                        closeCreatedAsnDetail,
                        "Clicked Close Created ASN Detail"
                );
            }

            totalDetail--;
        }

        // =========================================================
        // REDIRECT TO ASN PAGE
        // =========================================================

        click(
                redirectToAsnFromAsnDetail,
                "Redirected to ASN from ASN Details"
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Redirected To ASNs Page From ASN Details Page"
        );

        // =========================================================
        // WAIT FOR EXPECTED STATUS
        // =========================================================

        waitForStatus(
                asnStatusValidation,
                () -> click(
                        refresh,
                        "Refreshing ASN Page "
                                + driver.findElement(
                                asnStatusValidation
                        ).getText()
                ),
                status
        );

        // =========================================================
        // OPEN ASN AGAIN
        // =========================================================

        WebElement vendorAsnGet =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        By.xpath(
                                                "//span[@data-component-id='AsnId' and normalize-space()='"
                                                        + createdAsnId
                                                        + "']"
                                        )
                                )
                );

        vendorAsnGet.click();

        Thread.sleep(3000);

        // =========================================================
        // MORE
        // =========================================================

        click(
                more,
                "Clicked More"
        );

        Thread.sleep(2000);

        // =========================================================
        // EDIT
        // =========================================================

        click(
                editAsn,
                "Clicked Edit ASN"
        );

        Thread.sleep(3000);

        // =========================================================
        // ENTER VENDOR
        //
        // IMPORTANT:
        // vendorName comes directly from Feature File.
        // =========================================================

        type(
                vendorId,
                vendorName,
                "Vendor ID"
        );

        System.out.println(
                "Entered Vendor ID: "
                        + vendorName
        );
        click(
                saveAsnDetail,
                "Clicked Save ASN Detail - First Time"
        );
        click(
                closeAsnEdit,
                "Closed ASN Edit"
        );

        Thread.sleep(2000);
        ScenarioContext.set(
                "VendorName",
                vendorName
        );
        System.out.println(
                "Vendor stored in ScenarioContext: "
                        + ScenarioContext.get("VendorName")
        );
        waitForStatus(
                asnStatusValidation,
                () -> click(
                        refresh,
                        "Refreshing ASN Page "
                                + driver.findElement(
                                asnStatusValidation
                        ).getText()
                ),
                status
        );

        return createdAsnId;
    }


    // =========================================================
    // EXISTING VERIFY ASN
    // =========================================================

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


        if (uiName.equalsIgnoreCase("ASNs")) {

            type(
                    searchBarInLandingPage,
                    uiName,
                    "Search Bar in Landing Page"
            );

            click(
                    clickAsnSFromMenu,
                    "Click ASN from Menu"
            );


            if (isDisplayed(
                    asnTextInAsnUi,
                    "ASN Text in ASN UI"
            )) {

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "Successfully navigated to "
                                + uiName
                                + " page"
                );

            } else {

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        "Failed to navigate to "
                                + uiName
                                + " page"
                );
            }


            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Navigated to "
                            + uiName
                            + " page"
            );
        }
    }

    public void verifyASN(
            String asn)
            throws InterruptedException {

        try {
            System.out.println("Verify asn Starting "+asn);

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            By.xpath(
                                                    "//span[@data-component-id='AsnId' and normalize-space()='"
                                                            + asn
                                                            + "']"
                                            )
                                    )
                    );

            System.out.println(asnGet+"Before click");
//            click(
//                    asnGet,
//                    "Clicked ASN ID from the list"
//            );
            asnGet.click();
            System.out.println(asnGet);


        } catch (StaleElementReferenceException s) {
            System.out.println("Entered verify asn catch block");

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            By.xpath(
                                                    "//span[@data-component-id='AsnId' and normalize-space()='"
                                                            + asn
                                                            + "']"
                                            )
                                    )
                    );


//            click(
//                    asnGet,
//                    "Clicked ASN ID from the list"
//            );
            asnGet.click();
        }
        System.out.println("Verify asn Ending "+asn);


        Thread.sleep(3000);

        click(
                more,
                "Clicked More"
        );

        Thread.sleep(3000);

        click(
                verify,
                "Clicked Verify"
        );
        Thread.sleep(4000);
        waitForStatus(asnStatusValidation,
                () -> click(refresh, "Refreshing ASN Page" + driver.findElement(asnStatusValidation).getText()),
                "Verified");


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Successfully Verified ASN Without Variance"
        );
    }

    public void verifyILPNLocation(
            String asn,
            List<String> ilpns)
            throws InterruptedException {

        verifyILPNLocation(asn, ilpns, null);
    }


    public void verifyILPNLocation(
            String asn,
            List<String> ilpns,
            String palletId)
            throws InterruptedException {

        // =========================================================
        // 1. VALIDATE INPUT
        // =========================================================

        if (asn == null || asn.isBlank()) {
            throw new IllegalArgumentException(
                    "ASN cannot be null or empty"
            );
        }

        if (ilpns == null || ilpns.isEmpty()) {
            throw new IllegalArgumentException(
                    "ILPN list cannot be null or empty for ASN: "
                            + asn
            );
        }

        if (palletId != null && palletId.isBlank()) {
            throw new IllegalArgumentException(
                    "Pallet ID cannot be empty when pallet verification is requested"
            );
        }

        // =========================================================
        // 2. OPEN ASN
        // =========================================================

        try {

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            By.xpath(
                                                    "//span[@data-component-id='AsnId' and normalize-space()='"
                                                            + asn
                                                            + "']"
                                            )
                                    )
                    );

            click(
                    asnGet,
                    "Clicked ASN ID from the list"
            );

        } catch (StaleElementReferenceException s) {

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            By.xpath(
                                                    "//span[@data-component-id='AsnId' and normalize-space()='"
                                                            + asn
                                                            + "']"
                                            )
                                    )
                    );

            click(
                    asnGet,
                    "Clicked ASN ID from the list"
            );
        }

        Thread.sleep(3000);

        // =========================================================
        // 3. RELATED LINKS
        // =========================================================

        click(
                clickRelatedLinks,
                "To Navigate LPNs"
        );

        Thread.sleep(3000);

        // =========================================================
        // 4. OPEN LPN INVENTORY
        // =========================================================

        click(
                lpnInventoryAsn,
                "Redirected TO ILPNs Page"
        );

        Thread.sleep(4000);

        // =========================================================
        // 5. REFRESH LPN INVENTORY
        // =========================================================

        click(
                refresh,
                "Refreshed the ILPNs page"
        );

        Thread.sleep(3000);

        // =========================================================
        // 6. GET STARTING ILPN POSITION
        // =========================================================

        int ilpnPosition =
                getIlpnStartingPosition();

        System.out.println(
                "Starting ILPN Position: "
                        + ilpnPosition
        );

        // =========================================================
        // 7. GET ILPN LOCATION MAP
        // =========================================================

        @SuppressWarnings("unchecked")
        Map<String, String> ilpnLocationMap =
                (Map<String, String>)
                        ScenarioContext.get(
                                "ILPN_LOCATION_MAP"
                        );

        if (ilpnLocationMap == null) {

            throw new IllegalStateException(
                    "ILPN_LOCATION_MAP not found in ScenarioContext"
            );
        }

        WebDriverWait locationWait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30)
                );

        // =========================================================
        // 8. VALIDATE EACH ILPN
        // =========================================================

        for (String ilpn : ilpns) {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Validating ILPN: " + ilpn
            );

            System.out.println(
                    "ILPN Position: " + ilpnPosition
            );

            System.out.println(
                    "======================================"
            );

            // =====================================================
            // GET EXPECTED LOCATION
            // =====================================================

            String expectedLocation =
                    ilpnLocationMap.get(ilpn);

            if (expectedLocation == null
                    || expectedLocation.isBlank()) {

                throw new IllegalStateException(
                        "No expected location found for ILPN: "
                                + ilpn
                );
            }

            expectedLocation =
                    expectedLocation.trim();

            String locationType =
                    expectedLocation.toUpperCase();

            // =====================================================
            // DETERMINE LOCATION VALIDATION TYPE
            // =====================================================

            boolean validateCurrentLocation =
                    locationType.contains("R");

            boolean validatePreviousLocation =
                    locationType.contains("A");

            if (!validateCurrentLocation
                    && !validatePreviousLocation) {

                throw new IllegalStateException(
                        "Unable to determine location validation type for ILPN: "
                                + ilpn
                                + " | Location: "
                                + expectedLocation
                );
            }

            if (validateCurrentLocation
                    && validatePreviousLocation) {

                throw new IllegalStateException(
                        "Location contains both R and A. "
                                + "Unable to determine validation type for ILPN: "
                                + ilpn
                                + " | Location: "
                                + expectedLocation
                );
            }

            // =====================================================
            // BUILD COMMON XPATHS
            // =====================================================

            String currentLocationXpath =
                    "(//span[@data-component-id='CurrentLocationId'])["
                            + ilpnPosition
                            + "]";

            String currentDisplayLocationXpath =
                    "(//span[@data-component-id='CurrentDisplayLocation'])["
                            + ilpnPosition
                            + "]";

            String currentLocationTypeXpath =
                    "(//span[@data-component-id='CurrentLocationTypeId'])["
                            + ilpnPosition
                            + "]";

            String previousLocationXpath =
                    "(//span[@data-component-id='PreviousLocationId'])["
                            + ilpnPosition
                            + "]";

            String previousDisplayLocationXpath =
                    "(//span[@data-component-id='PreviousDisplayLocation'])["
                            + ilpnPosition
                            + "]";

            // ParentLpnId uses THE SAME ILPN POSITION.
            String parentLpnIdXpath =
                    "(//span[@data-component-id='ParentLpnId'])["
                            + ilpnPosition
                            + "]";

            // =====================================================
            // PRINT XPATHS
            // =====================================================

            System.out.println(
                    "Expected Location: "
                            + expectedLocation
            );

            System.out.println(
                    "Current Location XPath: "
                            + currentLocationXpath
            );

            System.out.println(
                    "Current Display Location XPath: "
                            + currentDisplayLocationXpath
            );

            System.out.println(
                    "Current Location Type XPath: "
                            + currentLocationTypeXpath
            );

            System.out.println(
                    "Previous Location XPath: "
                            + previousLocationXpath
            );

            System.out.println(
                    "Previous Display Location XPath: "
                            + previousDisplayLocationXpath
            );

            if (palletId != null) {
                System.out.println(
                        "Parent LPN ID XPath: "
                                + parentLpnIdXpath
                );
            }

            // =====================================================
            // VALIDATION FLAGS
            // =====================================================

            boolean locationValid = false;
            boolean displayLocationValid = false;
            boolean locationTypeValid = true;

            String actualLocation = "";
            String actualDisplayLocation = "";
            String actualLocationType = "";

            // =====================================================
            // R LOCATION
            // VALIDATE CURRENT LOCATION
            // =====================================================

            if (validateCurrentLocation) {

                WebElement currentLocationElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        currentLocationXpath
                                                )
                                        )
                        );

                WebElement currentDisplayLocationElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        currentDisplayLocationXpath
                                                )
                                        )
                        );

                WebElement currentLocationTypeElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        currentLocationTypeXpath
                                                )
                                        )
                        );

                actualLocation =
                        currentLocationElement
                                .getText()
                                .trim();

                actualDisplayLocation =
                        currentDisplayLocationElement
                                .getText()
                                .trim();

                actualLocationType =
                        currentLocationTypeElement
                                .getText()
                                .trim();

                System.out.println(
                        "Validation Type: CURRENT (R location)"
                );

                System.out.println(
                        "Current Location ID: "
                                + actualLocation
                );

                System.out.println(
                        "Current Display Location: "
                                + actualDisplayLocation
                );

                System.out.println(
                        "Current Location Type: "
                                + actualLocationType
                );

                // =================================================
                // VALIDATE CURRENT LOCATION ID
                // =================================================

                locationValid =
                        actualLocation.equalsIgnoreCase(
                                expectedLocation
                        );

                // =================================================
                // VALIDATE CURRENT DISPLAY LOCATION
                // =================================================

                displayLocationValid =
                        actualDisplayLocation.equalsIgnoreCase(
                                expectedLocation
                        );

                // =================================================
                // VALIDATE CURRENT LOCATION TYPE
                // =================================================

                locationTypeValid =
                        actualLocationType.equalsIgnoreCase(
                                "STORAGE"
                        );
            }

            // =====================================================
            // A LOCATION
            // VALIDATE PREVIOUS LOCATION
            // =====================================================

            if (validatePreviousLocation) {

                WebElement previousLocationElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        previousLocationXpath
                                                )
                                        )
                        );

                WebElement previousDisplayLocationElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        previousDisplayLocationXpath
                                                )
                                        )
                        );

                actualLocation =
                        previousLocationElement
                                .getText()
                                .trim();

                actualDisplayLocation =
                        previousDisplayLocationElement
                                .getText()
                                .trim();

                System.out.println(
                        "Validation Type: PREVIOUS (A location)"
                );

                System.out.println(
                        "Previous Location ID: "
                                + actualLocation
                );

                System.out.println(
                        "Previous Display Location: "
                                + actualDisplayLocation
                );

                // =================================================
                // VALIDATE PREVIOUS LOCATION ID
                // =================================================

                locationValid =
                        actualLocation.equalsIgnoreCase(
                                expectedLocation
                        );

                // =================================================
                // VALIDATE PREVIOUS DISPLAY LOCATION
                // =================================================

                displayLocationValid =
                        actualDisplayLocation.equalsIgnoreCase(
                                expectedLocation
                        );

                // Location Type is NOT required for A validation.
                locationTypeValid = true;
            }

            // =====================================================
            // PALLET VALIDATION
            // ParentLpnId[n] = palletId
            // =====================================================

            boolean parentLpnValid = true;
            String actualParentLpnId = "";

            if (palletId != null) {

                WebElement parentLpnIdElement =
                        locationWait.until(
                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                By.xpath(
                                                        parentLpnIdXpath
                                                )
                                        )
                        );

                actualParentLpnId =
                        parentLpnIdElement
                                .getText()
                                .trim();

                parentLpnValid =
                        actualParentLpnId.equalsIgnoreCase(
                                palletId.trim()
                        );

                System.out.println(
                        "Expected Parent LPN ID: "
                                + palletId
                );

                System.out.println(
                        "Actual Parent LPN ID: "
                                + actualParentLpnId
                );
            }

            // =====================================================
            // PRINT FINAL VALUES
            // =====================================================

            System.out.println(
                    "ILPN: "
                            + ilpn
                            + " | Expected Location: "
                            + expectedLocation
                            + " | Actual Location: "
                            + actualLocation
                            + " | Actual Display Location: "
                            + actualDisplayLocation
                            + " | Location Type: "
                            + actualLocationType
                            + " | Expected Pallet: "
                            + palletId
                            + " | Actual Parent LPN: "
                            + actualParentLpnId
            );

            // =====================================================
            // FINAL VALIDATION
            // =====================================================

            boolean validationPassed =
                    locationValid
                            && displayLocationValid
                            && locationTypeValid
                            && parentLpnValid;

            if (validationPassed) {

                String message;

                if (palletId != null) {

                    message =
                            "ILPN Location and Parent LPN Validation Passed for: "
                                    + ilpn
                                    + " | Location: "
                                    + expectedLocation
                                    + " | Pallet: "
                                    + palletId;

                } else {

                    message =
                            "ILPN Location Validation Passed for: "
                                    + ilpn
                                    + " | Location: "
                                    + expectedLocation;
                }

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        message
                );

            } else {

                String message;

                if (palletId != null) {

                    message =
                            "ILPN Location and Parent LPN Validation Failed for: "
                                    + ilpn
                                    + " | Expected Location: "
                                    + expectedLocation
                                    + " | Actual Location: "
                                    + actualLocation
                                    + " | Expected Pallet: "
                                    + palletId
                                    + " | Actual Parent LPN: "
                                    + actualParentLpnId;

                } else {

                    message =
                            "ILPN Location Validation Failed for: "
                                    + ilpn
                                    + " | Expected Location: "
                                    + expectedLocation
                                    + " | Actual Location: "
                                    + actualLocation
                                    + " | Actual Display Location: "
                                    + actualDisplayLocation
                                    + " | Location Type: "
                                    + actualLocationType;
                }

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        message
                );
            }

            // =====================================================
            // MOVE TO NEXT ILPN POSITION
            // =====================================================

            ilpnPosition++;
        }

        // =========================================================
        // FINAL VALIDATION MESSAGE
        // =========================================================

        if (palletId != null) {

            report.addReportStepWithoutScreenshot(
                    StepStatus.PASS,
                    "ILPN Location and Parent LPN Validation Completed "
                            + "for ASN: "
                            + asn
                            + " | Pallet: "
                            + palletId
            );

        } else {

            report.addReportStepWithoutScreenshot(
                    StepStatus.PASS,
                    "ILPN Location Validation Completed for ASN: "
                            + asn
            );
        }
    }


    // =========================================================
    // NEW API - CREATE ILPN + INVENTORY
    // =========================================================

    public String createLpnLevelAsn(
            List<String> items,
            String shippedQty) throws Exception {

        // =========================================================
        // 1. Validate Items
        // =========================================================

        if (items == null || items.isEmpty()) {

            throw new IllegalArgumentException(
                    "Items list cannot be empty"
            );
        }


        // =========================================================
        // 2. Split Shipped Quantities
        // =========================================================

        String[] quantities =
                shippedQty.split(",");


        // =========================================================
        // 3. Validate Item Count vs Quantity Count
        // =========================================================

        if (quantities.length != items.size()) {

            throw new IllegalArgumentException(
                    "Item count (" + items.size()
                            + ") does not match quantity count ("
                            + quantities.length + ")"
            );
        }


        // =========================================================
        // 4. Generate ASN ID
        // =========================================================

        Random random =
                new Random();

        String asnId =
                "ASN190"
                        + random.nextInt(100000);

        System.out.println(
                "Generated ASN ID: " + asnId
        );


        // =========================================================
        // 5. Create LPN List
        // =========================================================

        List<Map<String, Object>> lpnList =
                new ArrayList<>();


        // =========================================================
        // 6. Create One ILPN for Each Line Item
        // =========================================================

        for (int i = 0; i < items.size(); i++) {

            // -----------------------------------------------------
            // Generate ILPN ID
            // -----------------------------------------------------

            String lpnId =
                    "LPN"
                            + String.format(
                            "%06d",
                            random.nextInt(1000000)
                    );


            // -----------------------------------------------------
            // LPN Detail
            // -----------------------------------------------------

            Map<String, Object> detail =
                    new HashMap<>();

            detail.put(
                    "QuantityUomId",
                    "UNIT"
            );

            detail.put(
                    "ItemId",
                    items.get(i)
            );

            detail.put(
                    "ShippedQuantity",
                    Double.parseDouble(
                            quantities[i].trim()
                    )
            );

            detail.put(
                    "InventoryTypeId",
                    "F"
            );

            detail.put(
                    "ProductStatusId",
                    "LowStock"
            );

            detail.put(
                    "BatchNumber",
                    "VSBATCH999"
            );

            detail.put(
                    "CountryOfOrigin",
                    null
            );

            detail.put(
                    "InventoryAttribute1",
                    null
            );

            detail.put(
                    "InventoryAttribute2",
                    null
            );

            detail.put(
                    "InventoryAttribute3",
                    "1211"
            );

            detail.put(
                    "InventoryAttribute4",
                    null
            );

            detail.put(
                    "InventoryAttribute5",
                    null
            );

            detail.put(
                    "ExpiryDate",
                    "2024-06-24"
            );

            detail.put(
                    "ManufacturingDate",
                    null
            );


            // -----------------------------------------------------
            // LPN Detail List
            // -----------------------------------------------------

            List<Map<String, Object>> lpnDetails =
                    new ArrayList<>();

            lpnDetails.add(detail);


            // -----------------------------------------------------
            // LPN Actions
            // -----------------------------------------------------

            Map<String, String> lpnActions =
                    new HashMap<>();

            lpnActions.put(
                    "LpnDetail",
                    "RESET"
            );


            // -----------------------------------------------------
            // LPN Object
            // -----------------------------------------------------

            Map<String, Object> lpn =
                    new HashMap<>();

            lpn.put(
                    "AsnId",
                    asnId
            );

            lpn.put(
                    "AllocationTypeId",
                    null
            );

            lpn.put(
                    "LpnId",
                    lpnId
            );

            lpn.put(
                    "LpnTypeId",
                    "ILPN"
            );

            lpn.put(
                    "Actions",
                    lpnActions
            );

            lpn.put(
                    "LpnDetail",
                    lpnDetails
            );


            // -----------------------------------------------------
            // Add LPN to ASN
            // -----------------------------------------------------

            lpnList.add(lpn);


            System.out.println(
                    "Created ILPN "
                            + (i + 1)
                            + ": "
                            + lpnId
                            + " | Item: "
                            + items.get(i)
                            + " | Qty: "
                            + quantities[i].trim()
            );
        }


        // =========================================================
        // 7. ASN Actions
        // =========================================================

        Map<String, String> actions =
                new HashMap<>();

        actions.put(
                "Lpn",
                "RESET"
        );


        // =========================================================
        // 8. Build ASN Request
        // =========================================================

        Map<String, Object> request =
                new HashMap<>();

        request.put(
                "AsnId",
                asnId
        );

        request.put(
                "AsnOriginTypeId",
                "S"
        );

        request.put(
                "AsnLevelId",
                "LPN"
        );

        request.put(
                "DestinationFacilityId",
                "P09"
        );

        request.put(
                "Actions",
                actions
        );

        request.put(
                "Lpn",
                lpnList
        );


        // =========================================================
        // 9. Print Request
        // =========================================================

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "LPN LEVEL ASN REQUEST"
        );

        System.out.println(
                request
        );

        System.out.println(
                "=========================================="
        );


        // =========================================================
        // 10. Generate Access Token
        // =========================================================

        String token =
                getAccessToken();


        // =========================================================
        // 11. Get Base URL
        // =========================================================

        String baseUrl =
                ConfigManager.get(
                        "postmanUrl"
                );


        // =========================================================
        // 12. Build ASN API URL
        // =========================================================

        String asnUrl =
                baseUrl
                        + "/receiving/api/receiving/asn/save";


        System.out.println(
                "ASN API URL: " + asnUrl
        );


        // =========================================================
        // 13. Call ASN API
        // =========================================================

        Response response =
                given()

                        .header(
                                "Authorization",
                                token
                        )

                        .header(
                                "Content-Type",
                                "application/json"
                        )

                        .header(
                                "selectedLocation",
                                "P09"
                        )

                        .header(
                                "selectedOrganization",
                                "P09"
                        )

                        .body(request)

                        .when()
                        .post(asnUrl);


        // =========================================================
        // 14. Print Response
        // =========================================================

        System.out.println(
                "ASN API Status Code: "
                        + response.statusCode()
        );

        System.out.println(
                "ASN API Response:"
        );

        System.out.println(
                response.asPrettyString()
        );


        // =========================================================
        // 15. Validate HTTP Response
        // =========================================================

        if (response.statusCode() != 200) {

            throw new AssertionError(
                    "ASN API failed. HTTP Status: "
                            + response.statusCode()
                            + "\n"
                            + response.asPrettyString()
            );
        }


        // =========================================================
        // 16. Return Response
        // =========================================================

        return response.asString();
    }

    public String createIlpnAndInventory(
            List<InboundData> inboundDataList)
            throws Exception {

        // =========================================================
        // 1. Validate Input
        // =========================================================

        if (inboundDataList == null
                || inboundDataList.isEmpty()) {

            throw new IllegalArgumentException(
                    "No ILPN data found from Excel"
            );
        }


        // =========================================================
        // 2. Total ILPN Count
        // =========================================================

        int totalIlpns =
                inboundDataList.size();


        // =========================================================
        // 3. Maximum ILPNs Per API Request
        // =========================================================

        /*
         * Example:
         *
         * maxIlpnCreateRequest = 30
         *
         * 40 Excel rows:
         *
         * Request #1 -> 30 ILPNs
         * Request #2 -> 10 ILPNs
         */

        final int maxIlpnCreateRequest = 3;


        System.out.println(
                "=========================================="
        );

        System.out.println(
                "ILPN + INVENTORY DATA CREATION"
        );

        System.out.println(
                "Total ILPN rows from Excel: "
                        + totalIlpns
        );

        System.out.println(
                "Maximum ILPNs per request: "
                        + maxIlpnCreateRequest
        );

        System.out.println(
                "=========================================="
        );


        // =========================================================
        // 4. Generate Access Token
        // =========================================================

        String token =
                getAccessToken();


        // =========================================================
        // 5. Get Base URL
        // =========================================================

        String baseUrl =
                ConfigManager.get(
                        "postmanUrl"
                );


        if (baseUrl == null
                || baseUrl.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "postmanUrl is not configured"
            );
        }


        // =========================================================
        // 6. Build API URL
        // =========================================================

        String ilpnInventoryUrl =
                baseUrl
                        + "/dcinventory/api/dcinventory/ilpn/createIlpnAndInventory";


        System.out.println(
                "ILPN Inventory API URL: "
                        + ilpnInventoryUrl
        );


        // =========================================================
        // 7. Expiration Date
        // =========================================================

        /*
         * Current date + 1 month
         */

        String expirationDate =
                LocalDate.now()
                        .plusMonths(1)
                        .toString();


        System.out.println(
                "Expiration Date: "
                        + expirationDate
        );


        // =========================================================
        // 8. Selected Location
        // =========================================================

        /*
         * IMPORTANT
         *
         * selectedLocation is NOT the storage location.
         *
         * selectedLocation = P09
         *
         * Excel Location is used separately for:
         *
         * CurrentLocationId
         * LocnId
         * Inventory.LocationId
         */

        final String selectedLocation =
                "P09";


        // =========================================================
        // 9. Selected Business Unit
        // =========================================================

        final String selectedBusinessUnit =
                "P09";


        // =========================================================
        // 10. Validate Selected Context
        // =========================================================

        if (selectedLocation.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "selectedLocation is not configured"
            );
        }


        if (selectedBusinessUnit.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "selectedBusinessUnit is not configured"
            );
        }


        // =========================================================
        // 11. Process Excel Data In Batches
        // =========================================================

        int requestNumber = 1;

        String lastResponse = null;


        for (int start = 0;
             start < totalIlpns;
             start += maxIlpnCreateRequest) {


            // =====================================================
            // Determine Batch End
            // =====================================================

            int end =
                    Math.min(
                            start + maxIlpnCreateRequest,
                            totalIlpns
                    );


            List<InboundData> currentBatch =
                    inboundDataList.subList(
                            start,
                            end
                    );


            // =====================================================
            // Request Information
            // =====================================================

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "ILPN CREATE REQUEST #"
                            + requestNumber
            );

            System.out.println(
                    "Excel rows: "
                            + (start + 1)
                            + " to "
                            + end
            );

            System.out.println(
                    "ILPN count in request: "
                            + currentBatch.size()
            );

            System.out.println(
                    "Selected Location: "
                            + selectedLocation
            );

            System.out.println(
                    "Selected Business Unit: "
                            + selectedBusinessUnit
            );

            System.out.println(
                    "=========================================="
            );


            // =====================================================
            // 12. Build Request Body
            // =====================================================

            List<Map<String, Object>> requestBody =
                    new ArrayList<>();


            // =====================================================
            // 13. Build One ILPN Object Per Excel Row
            // =====================================================

            for (InboundData data :
                    currentBatch) {


                // =================================================
                // ILPN ID
                // =================================================

                String ilpn =
                        data.getIlpn();


                if (ilpn == null
                        || ilpn.trim().isEmpty()) {

                    throw new IllegalArgumentException(
                            "IlpnId cannot be empty"
                    );
                }


                // =================================================
                // Storage Location
                // =================================================

                /*
                 * This is the location coming from Excel.
                 *
                 * Example:
                 *
                 * 190R1011
                 */

                String location =
                        data.getLocation();


                if (location == null
                        || location.trim().isEmpty()) {

                    throw new IllegalArgumentException(
                            "Location cannot be empty for ILPN: "
                                    + ilpn
                    );
                }
                storeIlpnLocation(
                        ilpn,
                        location
                );


                // =================================================
                // Inventory List
                // =================================================

                List<Map<String, Object>> inventoryList =
                        new ArrayList<>();


                // =================================================
                // Create Inventory For Each Item
                // =================================================

                for (InboundItem item :
                        data.getItems()) {


                    Map<String, Object> inventory =
                            new HashMap<>();


                    // =============================================
                    // Quantity
                    // =============================================

                    String quantity =
                            item.getQuantity();


                    if (quantity == null
                            || quantity.trim().isEmpty()) {

                        throw new IllegalArgumentException(
                                "Quantity cannot be empty for ILPN: "
                                        + ilpn
                        );
                    }


                    int onHand;


                    try {

                        onHand =
                                Integer.parseInt(
                                        quantity.trim()
                                );

                    } catch (NumberFormatException e) {

                        throw new IllegalArgumentException(
                                "Invalid quantity '"
                                        + quantity
                                        + "' for ILPN: "
                                        + ilpn,
                                e
                        );
                    }


                    inventory.put(
                            "OnHand",
                            onHand
                    );


                    // =============================================
                    // InventoryContainerTypeId
                    // =============================================

                    inventory.put(
                            "InventoryContainerTypeId",
                            "ILPN"
                    );


                    // =============================================
                    // Inventory IlpnId
                    // =============================================

                    inventory.put(
                            "IlpnId",
                            ilpn
                    );


                    // =============================================
                    // ExpirationDate
                    // =============================================

                    inventory.put(
                            "ExpirationDate",
                            expirationDate
                    );


                    // =============================================
                    // Storage Location
                    //
                    // Excel Location
                    // =============================================

                    inventory.put(
                            "LocationId",
                            location
                    );


                    // =============================================
                    // InventoryContainerId
                    //
                    // IMPORTANT:
                    //
                    // InventoryContainerId = IlpnId
                    // =============================================

                    inventory.put(
                            "InventoryContainerId",
                            ilpn
                    );


                    // =============================================
                    // ItemId
                    // =============================================

                    String itemId =
                            item.getItem();


                    if (itemId == null
                            || itemId.trim().isEmpty()) {

                        throw new IllegalArgumentException(
                                "ItemId cannot be empty for ILPN: "
                                        + ilpn
                        );
                    }


                    inventory.put(
                            "ItemId",
                            itemId
                    );


                    // =============================================
                    // Add Inventory
                    // =============================================

                    inventoryList.add(
                            inventory
                    );


                    System.out.println(
                            "Prepared Inventory:"
                                    + " ILPN=" + ilpn
                                    + " | Item=" + itemId
                                    + " | OnHand=" + onHand
                                    + " | StorageLocation=" + location
                                    + " | InventoryContainerId=" + ilpn
                    );
                    report.addReportStepWithoutScreenshot(
                            StepStatus.PASS,
                            "ILPN Inventory Created"
                                    + " | ILPN: " + ilpn
                                    + " | Item: " + itemId
                                    + " | On Hand: " + onHand
                                    + " | Location: " + location
                    );
                }

                if (inventoryList.isEmpty()) {

                    throw new IllegalArgumentException(
                            "No Inventory records found for ILPN: "
                                    + ilpn
                    );
                }
                Map<String, Object> ilpnObject =
                        new HashMap<>();
                ilpnObject.put(
                        "Status",
                        3000
                );

                ilpnObject.put(
                        "Organization",
                        "P09"
                );


                // =================================================
                // IlpnTypeId
                // =================================================

                ilpnObject.put(
                        "IlpnTypeId",
                        "ILPN"
                );

                ilpnObject.put(
                        "IlpnId",
                        ilpn
                );

                ilpnObject.put(
                        "PhysicalEntityCodeId",
                        "ILPN"
                );

                ilpnObject.put(
                        "EstimatedWeight",
                        630.0
                );


                // =================================================
                // CurrentLocationId
                //
                // IMPORTANT:
                // This is Excel storage location.
                // NOT P09.
                // =================================================

                ilpnObject.put(
                        "CurrentLocationId",
                        location
                );


                // =================================================
                // LocnId
                //
                // IMPORTANT:
                // This is Excel storage location.
                // NOT P09.
                // =================================================

                ilpnObject.put(
                        "LocnId",
                        location
                );


                // =================================================
                // LpnFacilityStatus
                // =================================================

                ilpnObject.put(
                        "LpnFacilityStatus",
                        30
                );


                // =================================================
                // Volume
                // =================================================

                ilpnObject.put(
                        "Volume",
                        37.777
                );


                // =================================================
                // Inventory
                // =================================================

                ilpnObject.put(
                        "Inventory",
                        inventoryList
                );


                // =================================================
                // Add ILPN
                // =================================================

                requestBody.add(
                        ilpnObject
                );


                System.out.println(
                        "Prepared ILPN:"
                                + " " + ilpn
                                + " | Storage Location="
                                + location
                                + " | Inventory Count="
                                + inventoryList.size()
                );
            }


            // =====================================================
            // 14. Print Request Body
            // =====================================================

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "ILPN CREATE REQUEST BODY #"
                            + requestNumber
            );

            System.out.println(
                    requestBody
            );

            System.out.println(
                    "=========================================="
            );


            // =====================================================
            // 15. POST API
            // =====================================================

            Response response =
                    given()

                            // -------------------------------------
                            // Authorization
                            // -------------------------------------

                            .header(
                                    "Authorization",
                                    token
                            )

                            // -------------------------------------
                            // Content Type
                            // -------------------------------------

                            .header(
                                    "Content-Type",
                                    "application/json"
                            )

                            // -------------------------------------
                            // Selected Organization
                            // -------------------------------------

                            .header(
                                    "selectedOrganization",
                                    "P09"
                            )

                            // -------------------------------------
                            // Selected Location
                            //
                            // IMPORTANT:
                            // This is P09.
                            // -------------------------------------

                            .header(
                                    "selectedLocation",
                                    selectedLocation
                            )

                            // -------------------------------------
                            // Selected Business Unit
                            // -------------------------------------

                            .header(
                                    "selectedBusinessUnit",
                                    selectedBusinessUnit
                            )

                            // -------------------------------------
                            // Request Body
                            // -------------------------------------

                            .body(requestBody)

                            // -------------------------------------
                            // POST
                            // -------------------------------------

                            .when()

                            .post(
                                    ilpnInventoryUrl
                            );


            // =====================================================
            // 16. Print Response
            // =====================================================

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "ILPN CREATE RESPONSE #"
                            + requestNumber
            );

            System.out.println(
                    "HTTP STATUS: "
                            + response.statusCode()
            );

            System.out.println(
                    "RESPONSE:"
            );

            System.out.println(
                    response.asPrettyString()
            );

            System.out.println(
                    "=========================================="
            );


            // =====================================================
            // 17. Validate Response
            // =====================================================

            if (response.statusCode() != 200) {

                throw new AssertionError(
                        "ILPN + Inventory API failed."
                                + "\nRequest Number: "
                                + requestNumber
                                + "\nExcel Rows: "
                                + (start + 1)
                                + " to "
                                + end
                                + "\nILPN Count: "
                                + currentBatch.size()
                                + "\nSelected Location: "
                                + selectedLocation
                                + "\nHTTP Status: "
                                + response.statusCode()
                                + "\nResponse:"
                                + "\n"
                                + response.asPrettyString()
                );
            }


            // =====================================================
            // 18. Store Last Response
            // =====================================================

            lastResponse =
                    response.asString();


            System.out.println(
                    "Successfully created "
                            + currentBatch.size()
                            + " ILPN(s) in request #"
                            + requestNumber
            );


            requestNumber++;
        }


        // =========================================================
        // 19. Total Requests
        // =========================================================

        int totalRequests =
                (int) Math.ceil(
                        (double) totalIlpns
                                / maxIlpnCreateRequest
                );


        // =========================================================
        // 20. Final Log
        // =========================================================

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "ILPN + INVENTORY CREATION COMPLETED"
        );

        System.out.println(
                "Total ILPNs: "
                        + totalIlpns
        );

        System.out.println(
                "Total API Requests: "
                        + totalRequests
        );

        System.out.println(
                "Selected Location: "
                        + selectedLocation
        );

        System.out.println(
                "=========================================="
        );


        return lastResponse;
    }


    // =========================================================
    // GET ACCESS TOKEN
    // =========================================================

    private String getAccessToken() throws Exception {

        System.out.println(
                "Generating Access Token..."
        );


        // =========================================================
        // 1. Get Configuration
        // =========================================================

        String authUrl =
                ConfigManager.get(
                        "postmanAuthUrl"
                );

        String username =
                ConfigManager.get(
                        "postmanUsername"
                );

        String password =
                ConfigManager.get(
                        "postmanPassword"
                );

        String clientAuthorization =
                ConfigManager.get(
                        "postmanClientAuthorization"
                );


        // =========================================================
        // 2. Validate Configuration
        // =========================================================

        if (authUrl == null || authUrl.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "postmanAuthUrl is not configured"
            );
        }

        if (username == null || username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "postmanUsername is not configured"
            );
        }

        if (password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "postmanPassword is not configured"
            );
        }

        if (clientAuthorization == null
                || clientAuthorization.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "postmanClientAuthorization is not configured"
            );
        }


        // =========================================================
        // 3. Build Token URL
        // =========================================================

        String tokenUrl =
                authUrl
                        + "/oauth/token";


        System.out.println(
                "Token URL: " + tokenUrl
        );


        // =========================================================
        // 4. Call OAuth API
        // =========================================================

        Response response =
                given()

                        // -----------------------------------------
                        // Client Authorization
                        // -----------------------------------------

                        .header(
                                "Authorization",
                                clientAuthorization
                        )

                        // -----------------------------------------
                        // Form Content Type
                        // -----------------------------------------

                        .contentType(
                                "application/x-www-form-urlencoded"
                        )

                        // -----------------------------------------
                        // OAuth Grant Type
                        // -----------------------------------------

                        .formParam(
                                "grant_type",
                                "password"
                        )

                        // -----------------------------------------
                        // Username
                        // -----------------------------------------

                        .formParam(
                                "username",
                                username
                        )

                        // -----------------------------------------
                        // Password
                        // -----------------------------------------

                        .formParam(
                                "password",
                                password
                        )

                        // -----------------------------------------
                        // POST
                        // -----------------------------------------

                        .when()
                        .post(tokenUrl);


        // =========================================================
        // 5. Validate Token Response
        // =========================================================

        if (response.statusCode() != 200) {

            throw new AssertionError(
                    "Access Token generation failed. HTTP Status: "
                            + response.statusCode()
                            + "\n"
                            + response.asPrettyString()
            );
        }


        // =========================================================
        // 6. Extract Access Token
        // =========================================================

        String accessToken =
                response.jsonPath()
                        .getString("access_token");


        if (accessToken == null
                || accessToken.trim().isEmpty()) {

            throw new AssertionError(
                    "Access token is missing in OAuth response"
            );
        }


        // =========================================================
        // 7. Add Bearer Prefix
        // =========================================================

        String bearerToken =
                "Bearer " + accessToken;


        System.out.println(
                "Access Token Generated Successfully"
        );


        return bearerToken;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getIlpnLocationMap() {

        Map<String, String> ilpnLocationMap =
                (Map<String, String>) ScenarioContext.get("ILPN_LOCATION_MAP");

        if (ilpnLocationMap == null) {
            ilpnLocationMap = new HashMap<>();
            ScenarioContext.set(
                    "ILPN_LOCATION_MAP",
                    ilpnLocationMap
            );
        }

        return ilpnLocationMap;
    }

    private void storeIlpnLocation(
            String ilpn,
            String location) {

        Map<String, String> ilpnLocationMap =
                getIlpnLocationMap();

        ilpnLocationMap.put(
                ilpn,
                location
        );

        ScenarioContext.set(
                "ILPN_LOCATION_MAP",
                ilpnLocationMap
        );

        System.out.println(
                "Stored ILPN Location: "
                        + ilpn
                        + " → "
                        + location
        );
    }
    // =========================================================
// GET STARTING ILPN POSITION
// =========================================================

    private int getIlpnStartingPosition() {

        By showingRecordsLocator =
                By.xpath(
                        "//span[contains(normalize-space(text()), 'Showing ')]"
                );

        WebElement showingRecords =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                showingRecordsLocator
                        )
                );

        String text =
                showingRecords.getText().trim();

        System.out.println(
                "LPN Inventory Record Text: " + text
        );

        Matcher matcher =
                Pattern.compile(
                        "Showing\\s+(\\d+)(?:\\s*-\\s*(\\d+))?\\s+of\\s+(\\d+)\\s+records?",
                        Pattern.CASE_INSENSITIVE
                ).matcher(text);

        if (!matcher.find()) {

            throw new IllegalStateException(
                    "Unable to extract ILPN position from text: "
                            + text
            );
        }

        int startingPosition =
                Integer.parseInt(matcher.group(1));

        int totalRecords =
                Integer.parseInt(matcher.group(3));

        System.out.println(
                "Starting Position: " + startingPosition
        );

        System.out.println(
                "Total Records: " + totalRecords
        );

        return startingPosition;
    }

    public void verifyILPNParentLpn(
            String asn,
            List<String> ilpns,
            String palletId)
            throws InterruptedException {

        // =========================================================
        // 1. VALIDATE INPUT
        // =========================================================

        if (asn == null || asn.isBlank()) {
            throw new IllegalArgumentException(
                    "ASN cannot be null or empty"
            );
        }

        if (ilpns == null || ilpns.isEmpty()) {
            throw new IllegalArgumentException(
                    "ILPN list cannot be null or empty for ASN: " + asn
            );
        }

        if (palletId == null || palletId.isBlank()) {
            throw new IllegalArgumentException(
                    "Pallet ID cannot be null or empty"
            );
        }

        palletId = palletId.trim();

        // =========================================================
        // 2. OPEN ASN
        // =========================================================

        try {

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//span[@data-component-id='AsnId' and normalize-space()='"
                                                    + asn
                                                    + "']"
                                    )
                            )
                    );

            click(
                    asnGet,
                    "Clicked ASN ID for Parent LPN Validation"
            );

        } catch (StaleElementReferenceException e) {

            WebElement asnGet =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//span[@data-component-id='AsnId' and normalize-space()='"
                                                    + asn
                                                    + "']"
                                    )
                            )
                    );

            click(
                    asnGet,
                    "Clicked ASN ID for Parent LPN Validation"
            );
        }

        Thread.sleep(3000);

        // =========================================================
        // 3. OPEN LPN INVENTORY
        // =========================================================

        click(
                clickRelatedLinks,
                "Clicked Related Links"
        );

        Thread.sleep(2000);

        click(
                lpnInventoryAsn,
                "Opened LPN Inventory"
        );

        Thread.sleep(4000);

        click(
                refresh,
                "Refreshed LPN Inventory"
        );

        Thread.sleep(3000);

        // =========================================================
        // 4. GET ALL ILPN IDs
        // =========================================================

        List<WebElement> ilpnElements =
                driver.findElements(
                        By.xpath(
                                "//span[@data-component-id='IlpnId']"
                        )
                );

        // =========================================================
        // 5. GET ALL ILPN TYPES
        // =========================================================

        List<WebElement> lpnTypeElements =
                driver.findElements(
                        By.xpath(
                                "//span[@data-component-id='IlpnTypeDescription']"
                        )
                );

        // =========================================================
        // 6. GET ONLY NON-EMPTY PARENT LPN VALUES
        // =========================================================

        List<WebElement> parentLpnElements =
                driver.findElements(
                        By.xpath(
                                "//span[@data-component-id='ParentLpnId'][normalize-space()]"
                        )
                );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "LPN INVENTORY DATA"
        );

        System.out.println(
                "Total ILPN IDs       : "
                        + ilpnElements.size()
        );

        System.out.println(
                "Total LPN Types      : "
                        + lpnTypeElements.size()
        );

        System.out.println(
                "Non-empty Parent LPN : "
                        + parentLpnElements.size()
        );

        System.out.println(
                "======================================"
        );

        // =========================================================
        // 7. BUILD ILPN-ONLY LIST
        // =========================================================

        List<String> receivedIlpns =
                new ArrayList<>();

        for (int i = 0;
             i < ilpnElements.size()
                     && i < lpnTypeElements.size();
             i++) {

            String actualIlpn =
                    ilpnElements.get(i)
                            .getText()
                            .trim();

            String lpnType =
                    lpnTypeElements.get(i)
                            .getText()
                            .trim();

            System.out.println(
                    "Position " + (i + 1)
                            + " | ILPN: " + actualIlpn
                            + " | Type: " + lpnType
            );

            // -----------------------------------------------------
            // ONLY TAKE ILPN
            // -----------------------------------------------------

            if ("ILPN".equalsIgnoreCase(lpnType)) {

                receivedIlpns.add(actualIlpn);
            }
        }

        System.out.println(
                "Actual ILPN records: "
                        + receivedIlpns
        );

        // =========================================================
        // 8. VALIDATE EACH EXPECTED ILPN
        // =========================================================

        for (String expectedIlpn : ilpns) {

            String expectedIlpnValue =
                    expectedIlpn.trim();

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Parent LPN Validation"
            );

            System.out.println(
                    "ASN            : " + asn
            );

            System.out.println(
                    "Expected ILPN  : "
                            + expectedIlpnValue
            );

            System.out.println(
                    "Expected Pallet: "
                            + palletId
            );

            // =====================================================
            // FIND ILPN POSITION AMONG ILPN RECORDS
            // =====================================================

            int ilpnIndex =
                    -1;

            for (int i = 0;
                 i < receivedIlpns.size();
                 i++) {

                if (receivedIlpns.get(i)
                        .equalsIgnoreCase(
                                expectedIlpnValue)) {

                    ilpnIndex = i;
                    break;
                }
            }

            // =====================================================
            // ILPN NOT FOUND
            // =====================================================

            if (ilpnIndex == -1) {

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        "ILPN not found in LPN Inventory"
                                + " | ASN: " + asn
                                + " | Expected ILPN: "
                                + expectedIlpnValue
                );

                throw new IllegalStateException(
                        "ILPN not found in LPN Inventory"
                                + " | ASN: " + asn
                                + " | ILPN: "
                                + expectedIlpnValue
                );
            }

            // =====================================================
            // VALIDATE PARENT LPN EXISTS
            // =====================================================

            if (ilpnIndex >= parentLpnElements.size()) {

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        "Parent LPN not found for ILPN"
                                + " | ASN: " + asn
                                + " | ILPN: "
                                + expectedIlpnValue
                );

                throw new IllegalStateException(
                        "Parent LPN not found for ILPN"
                                + " | ILPN: "
                                + expectedIlpnValue
                );
            }

            // =====================================================
            // GET PARENT LPN
            // =====================================================

            String actualParentLpn =
                    parentLpnElements
                            .get(ilpnIndex)
                            .getText()
                            .trim();

            System.out.println(
                    "--------------------------------------"
            );

            System.out.println(
                    "ILPN Index      : "
                            + ilpnIndex
            );

            System.out.println(
                    "Actual ILPN     : "
                            + expectedIlpnValue
            );

            System.out.println(
                    "Actual Parent   : "
                            + actualParentLpn
            );

            System.out.println(
                    "Expected Parent : "
                            + palletId
            );

            // =====================================================
            // VALIDATE
            // =====================================================

            if (!actualParentLpn.equalsIgnoreCase(
                    palletId)) {

                report.addReportStepWithScreenshot(
                        StepStatus.FAIL,
                        "Parent LPN Validation Failed"
                                + " | ASN: " + asn
                                + " | ILPN: "
                                + expectedIlpnValue
                                + " | Expected Parent LPN: "
                                + palletId
                                + " | Actual Parent LPN: "
                                + actualParentLpn
                );

                throw new IllegalStateException(
                        "Parent LPN validation failed"
                                + " | ILPN: "
                                + expectedIlpnValue
                                + " | Expected: "
                                + palletId
                                + " | Actual: "
                                + actualParentLpn
                );
            }

            // =====================================================
            // PASS
            // =====================================================

            report.addReportStepWithScreenshot(
                    StepStatus.PASS,
                    "Parent LPN Validation Passed"
                            + " | ASN: " + asn
                            + " | ILPN: "
                            + expectedIlpnValue
                            + " | Parent LPN: "
                            + actualParentLpn
            );

            System.out.println(
                    "Parent LPN validation PASSED"
            );
        }

        // =========================================================
        // FINAL RESULT
        // =========================================================

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Parent LPN validation completed"
                        + " | ASN: " + asn
                        + " | Pallet: " + palletId
        );
    }


}