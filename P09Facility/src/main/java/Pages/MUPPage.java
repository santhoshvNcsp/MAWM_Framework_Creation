        package Pages;

        import com.p09.framework.context.ScenarioContext;
        import com.p09.framework.pages.BasePage;
        import com.p09.framework.reporting.StepStatus;
        import context.ASNData;
        import context.ItemData;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import context.ILPNData;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.HashMap;
        import java.util.Map;


        import java.time.Duration;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

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

                    private final By asnStatusValidation =
                        By.xpath("(//div[@data-component-id='AsnStatusDescription'])[1]");

        //    public void userCompletes(String transaction, String receivingQty) throws InterruptedException {
        //        String[] qtyToBeReceived = receivingQty.split(",");
        //        List<String> receivingQtyList = new ArrayList<>();
        //        for (String qty : qtyToBeReceived){
        //            receivingQtyList.add(qty.trim());
        //        }
        //        System.out.println("Receiving Qty List "+receivingQtyList);
        //        int cntr=0;
        //        System.out.println("Full List ");
        //        ScenarioContext.printAll();
        //        click(menuToggleButton,"Menu Toggle");
        //        Thread.sleep(4000);
        //        type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");
        //        ScenarioContext.set("parentWindowId", driver.getWindowHandle());
        //        click(clickWmMobileFromMenu, "Click WM Mobile from Menu");
        //        waitForPageLoad();
        //        Thread.sleep(5000);
        //        for (String handle : driver.getWindowHandles()) {
        //            if (!handle.equals(ScenarioContext.get("parentWindowId"))) {
        //                driver.switchTo().window(handle);
        //                break;
        //            }
        //        }
        //        Thread.sleep(3000);
        //        if (transaction.equalsIgnoreCase("Receiving")) {
        //            type(searchBarInWmMobile, "190 Receiving", "Search Bar in WM Mobile(190 Receiving)");
        //            Thread.sleep(3000);
        //            click(click190Receiving, "Clicked 190 Receiving");
        //            Thread.sleep(2000);
        //            type(passAsnIdReceive, ScenarioContext.get("Created Asn ID").toString(), "ASN ID in 190 Receiving");
        ////            Thread.sleep(4000);
        //            pressEnter(passAsnIdReceive, "Pressed Enter for ASN ID in 190 Receiving");
        ////            passAsnIdReceive.sendKeys(Keys.ENTER);
        //            Thread.sleep(7000);
        //            pressEnter(passLpnIdReceive, "Pressed Enter for LPN ID in 190 Receiving");
        ////            passLpnIdReceive.sendKeys(Keys.ENTER);
        //            Thread.sleep(5000);
        //            ScenarioContext.set("lpnIdFromWm", storeLpnIdFromWm.getText());
        //            report.addReportStepWithScreenshot(StepStatus.PASS, "LPN ID from WM Mobile: " + ScenarioContext.get("lpnIdFromWm"));
        //            ItemDetails itemDetails = new ItemDetails();
        //            Thread.sleep(3000);
        //            type(passItemReceive, itemDetails.primaryItems.getFirst(), "Item Barcode in 190 Receiving");

            /// /            Thread.sleep(3000);
        //            pressEnter(passItemReceive, "Pressed Enter for Item Barcode in 190 Receiving");
        //            Thread.sleep(4000);
        //            passQtyReceive.sendKeys(receivingQtyList.get(cntr));
        //            Thread.sleep(2000);
        //            pressEnter(passQtyReceive, "Pressed Enter for Quantity in 190 Receiving");
        //            Thread.sleep(3000);
        //            click(endLPNReceive, "Clicked End LPN in 190 Receiving");
        //            Thread.sleep(3000);
        //            driver.close();
        //            driver.switchTo().window(ScenarioContext.get("parentWindowId").toString());
        //            click(menuToggleButton, "Menu Toggle");
        //            Thread.sleep(4000);
        //            type(searchBarInLandingPage, "ASNs", "ASN UI");
        //            Thread.sleep(3000);
        //            click(clickAsnSFromMenu, "Clicked ASNs");
        //            Thread.sleep(3000);
        //            type(filterAsnById, ScenarioContext.get("Created Asn ID").toString(), "ASN Filtered");
        //            click(refresh, "Clicked Refresh in ASN UI");
        //            Thread.sleep(2000);
        //            report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully Receiving Completed");
        //
        //        }
        //        else {
        //            System.out.println("Developing");
        //        }
        //
        //
        //
        //    }
            public void userCompletes(String transaction) throws InterruptedException {

                // =========================================================
                // SCENARIO CONTEXT
                // =========================================================

                System.out.println("========== SCENARIO CONTEXT ==========");

                ScenarioContext.printAll();

                System.out.println("======================================");
                if (transaction.equalsIgnoreCase("ReceiveByUnit")) {

                    receiveByUnitEachLpn();

                    return;
                }


                // =========================================================
                // LPN LEVEL ASN RECEIVING
                //
                // No Item
                // No Quantity
                //
                // Flow:
                //
                // ASN 1
                //   -> LPN 1
                //   -> LPN 2
                //   -> Verification
                //
                // ASN 2
                //   -> LPN 3
                //   -> LPN 4
                //   -> Verification
                //
                // IMPORTANT:
                // LPNs are taken directly from ASNData.
                // We DO NOT use:
                // "Created LPN Level ILPN 1"
                // "Created LPN Level ILPN 2"
                // etc.
                // =========================================================

                if (transaction.equalsIgnoreCase("LPNReceiving")) {

                    // =====================================================
                    // GET ALL CREATED ASN DATA
                    // =====================================================

                    @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");


                    if (createdASNData == null || createdASNData.isEmpty()) {

                        throw new IllegalStateException("No Created LPN Level ASN data found in ScenarioContext");
                    }


                    System.out.println("========================================");

                    System.out.println("STARTING LPN LEVEL ASN RECEIVING");

                    System.out.println("Total ASNs: " + createdASNData.size());

                    System.out.println("========================================");


                    // =====================================================
                    // PROCESS EACH ASN
                    // =====================================================

                    for (ASNData asnData : createdASNData) {

                        // =================================================
                        // GET ASN ID
                        // =================================================

                        String asnId = asnData.getAsn();


                        // =================================================
                        // GET LPNs BELONGING TO THIS ASN
                        // =================================================

                        List<ILPNData> ilpns = asnData.getIlpns();


                        // =================================================
                        // VALIDATE ASN
                        // =================================================

                        if (asnId == null || asnId.isBlank()) {

                            throw new IllegalStateException("ASN ID is null or empty in ASNData");
                        }


                        // =================================================
                        // VALIDATE LPNs
                        // =================================================

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

                        click(menuToggleButton, "Menu Toggle");

                        Thread.sleep(4000);


                        type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");


                        // =================================================
                        // STORE PARENT WINDOW
                        // =================================================

                        String parentWindowId = driver.getWindowHandle();


                        ScenarioContext.set("parentWindowId", parentWindowId);


                        // =================================================
                        // OPEN WM MOBILE
                        // =================================================

                        click(clickWmMobileFromMenu, "Click WM Mobile from Menu");


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


                        // =================================================
                        // OPEN 190 RECEIVING
                        // =================================================

                        type(searchBarInWmMobile, "190 Receiving", "Search Bar in WM Mobile(190 Receiving)");

                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Searched for 190 Receiving");
                        Thread.sleep(3000);


                        click(click190Receiving, "Clicked 190 Receiving");


                        Thread.sleep(2000);
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");


                        // =================================================
                        // PASS ASN
                        //
                        // ASN IS ENTERED ONLY ONCE
                        // =================================================

                        type(passAsnIdReceive, asnId, "LPN Level ASN ID in 190 Receiving");


                        pressEnter(passAsnIdReceive, "Pressed Enter for LPN Level ASN: " + asnId);

                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered ASN ID: " + asnId + " in 190 Receiving Transaction");

        //                Thread.sleep(7000);
                        Thread.sleep(2000);


                        // =================================================
                        // PASS EACH EXISTING LPN
                        //
                        // IMPORTANT:
                        //
                        // We get the LPN directly from:
                        //
                        // asnData
                        //    -> ilpns
                        //       -> ilpnData
                        //          -> getIlpn()
                        //
                        // This prevents LPN overlap between ASNs.
                        // =================================================

                        for (int i = 0; i < ilpns.size(); i++) {

                            ILPNData ilpnData = ilpns.get(i);


                            // =================================================
                            // GET EXACT LPN FOR CURRENT ASN
                            // =================================================

                            String lpnId = ilpnData.getIlpn();


                            // =================================================
                            // VALIDATE LPN
                            // =================================================

                            if (lpnId == null || lpnId.isBlank()) {

                                throw new IllegalStateException("LPN ID is null or empty for ASN: " + asnId + ", LPN index: " + (i + 1));
                            }


                            System.out.println("----------------------------------------");

                            System.out.println("ASN: " + asnId + " | Processing LPN " + (i + 1) + " of " + ilpns.size() + ": " + lpnId);

                            System.out.println("----------------------------------------");


                            // =================================================
                            // PASS EXISTING LPN
                            //
                            // DO NOT PRESS ENTER AUTOMATICALLY FOR LPN
                            // CREATION.
                            //
                            // We are passing the LPN already created
                            // by the LPN Level ASN API.
                            // =================================================

                            type(passLpnIdReceive, lpnId, "Existing LPN ID in 190 Receiving");


                            // =================================================
                            // PRESS ENTER FOR EXISTING LPN
                            // =================================================

                            pressEnter(passLpnIdReceive, "Pressed Enter for LPN ID: " + lpnId);


        //                    Thread.sleep(5000);
        //                    Thread.sleep(2000);

                            System.out.println("Completed LPN Receiving: " + lpnId + " for ASN: " + asnId);
                            report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Lpn: " + lpnId);

                        }


                        // =====================================================
                        // ASN RECEIVING COMPLETED
                        // =====================================================

                        System.out.println("========================================");

                        System.out.println("LPN LEVEL ASN RECEIVING COMPLETED");

                        System.out.println("ASN: " + asnId);

                        System.out.println("Total LPNs Received: " + ilpns.size());

                        System.out.println("========================================");


                        // =====================================================
                        // CLOSE WM MOBILE
                        // =====================================================

                        driver.close();


                        driver.switchTo().window(parentWindowId);


                        System.out.println("Returned to parent window after ASN: " + asnId);


                        // =====================================================
                        // SAME VERIFICATION PROCESS
                        // =====================================================

                        click(menuToggleButton, "Menu Toggle");

                        Thread.sleep(4000);


                        type(searchBarInLandingPage, "ASNs", "ASN UI");

                        Thread.sleep(3000);


                        click(clickAsnSFromMenu, "Clicked ASNs");

        //                Thread.sleep(5000);
                        Thread.sleep(2000);

                        // =====================================================
                        // FILTER CURRENT ASN
                        // =====================================================

                        type(filterAsnById, asnId, "Filter ASN By ID");


                        Thread.sleep(3000);


                        pressEnter(filterAsnById, "Pressed Enter");


                        click(refresh, "Clicked Refresh in ASN UI");


                        Thread.sleep(3000);

                        click(refresh, "Clicked Refresh in ASN UI");


                        // =====================================================
                        // VERIFY CURRENT ASN
                        // =====================================================

                        AsnPage asnPage = new AsnPage();


                        asnPage.verifyASN(asnId);


                        System.out.println("ASN Verification Completed: " + asnId);
                    }


                    // =====================================================
                    // ALL LPN LEVEL ASNs COMPLETED
                    // =====================================================

                    System.out.println("========================================");

                    System.out.println("ALL LPN LEVEL ASN RECEIVING COMPLETED");

                    System.out.println("Total ASNs Received: " + createdASNData.size());

                    System.out.println("========================================");


                    report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully completed LPN Level ASN Receiving");


                    return;
                }


                // =========================================================
                // EXISTING RECEIVING FLOW
                //
                // This remains unchanged.
                //
                // Used for:
                //
                // Receiving
                // Receive-Mixed
                // ReceiveByPallet
                // =========================================================

                @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");


                if (createdASNData == null || createdASNData.isEmpty()) {

                    throw new IllegalStateException("No Created ASN data found in ScenarioContext");
                }


                System.out.println("Total ASNs to Receive: " + createdASNData.size());


                // =========================================================
                // PROCESS EACH ASN
                // =========================================================

                for (ASNData asnData : createdASNData) {

                    String asnId = asnData.getAsn();


                    List<ItemData> items = asnData.getItems();


                    System.out.println("======================================");

                    System.out.println("Starting Receiving for ASN: " + asnId);

                    System.out.println("Total Line Items: " + items.size());

                    System.out.println("======================================");


                    // =====================================================
                    // OPEN WM MOBILE
                    // =====================================================

                    click(menuToggleButton, "Menu Toggle");

                    Thread.sleep(4000);


                    type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");


                    String parentWindowId = driver.getWindowHandle();


                    ScenarioContext.set("parentWindowId", parentWindowId);


                    click(clickWmMobileFromMenu, "Click WM Mobile from Menu");


                    waitForPageLoad();

                    Thread.sleep(5000);


                    for (String handle : driver.getWindowHandles()) {

                        if (!handle.equals(parentWindowId)) {

                            driver.switchTo().window(handle);

                            break;
                        }
                    }


                    Thread.sleep(3000);


                    // =====================================================
                    // SELECT RECEIVING TRANSACTION
                    // =====================================================

                    if (transaction.equalsIgnoreCase("ReceiveByPallet")) {

                        type(searchBarInWmMobile, "190 ReceiveByPallet", "Search Bar in WM Mobile(190 ReceiveByPallet)");


                        Thread.sleep(3000);


                        click(clickReceiveByPallet, "Pallet Receiving");

                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Receive By Pallet Transaction");

                        Thread.sleep(2000);

                    } else {

                        type(searchBarInWmMobile, "190 Receiving", "Search Bar in WM Mobile(190 Receiving)");


        //                Thread.sleep(3000);


                        click(click190Receiving, "Clicked 190 Receiving");
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");


                        Thread.sleep(2000);
                    }


                    // =====================================================
                    // ENTER ASN
                    // =====================================================

                    type(passAsnIdReceive, asnId, "ASN ID in 190 Receiving");


                    pressEnter(passAsnIdReceive, "Pressed Enter for ASN ID in 190 Receiving");
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Asn Id " + asnId);


        //            Thread.sleep(7000);
                    Thread.sleep(3000);

                    // =====================================================
                    // PALLET FLOW
                    // =====================================================
                    if (transaction.equalsIgnoreCase("ReceiveByPallet")) {

                        // =====================================================
                        // AUTO-GENERATE PALLET
                        // =====================================================

                        pressEnter(scanPallet, "Pallet Id Auto Generated");

                        Thread.sleep(3000);


                        // =====================================================
                        // FETCH GENERATED PALLET ID
                        // =====================================================

                        String palletId = getPallet.getText();

                        if (palletId == null || palletId.isBlank()) {

                            throw new IllegalStateException("Pallet ID could not be fetched after pallet generation");
                        }


                        // =====================================================
                        // STORE PALLET ID
                        // =====================================================

                        ScenarioContext.set("CreatedPalletId", palletId);


                        // =====================================================
                        // LOG PALLET ID
                        // =====================================================

                        System.out.println("Generated Pallet ID: " + palletId);

                        System.out.println("Stored CreatedPalletId in ScenarioContext: " + ScenarioContext.get("CreatedPalletId"));
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Pallet Id" + palletId);

                    }


                    // =====================================================
                    // CHECK MIXED
                    // =====================================================

                    boolean isMixed = transaction.toLowerCase().contains("mixed");


                    System.out.println("Transaction: " + transaction);


                    System.out.println("Mixed ILPN: " + isMixed);


                    String currentLpnId = null;


                    ILPNData currentIlpnData = null;


                    // =====================================================
                    // PROCESS ITEMS
                    // =====================================================

                    for (ItemData itemData : items) {

                        String item = itemData.getItem();


                        String quantity = itemData.getShippedQty();


                        System.out.println("--------------------------------------");


                        System.out.println("Processing Item: " + item + " | Quantity: " + quantity);


                        System.out.println("--------------------------------------");


                        // =================================================
                        // CREATE / SELECT ILPN
                        // =================================================

                        if (!isMixed || currentLpnId == null) {


                            // =============================================
                            // AUTO-GENERATE LPN
                            // =============================================

                            pressEnter(passLpnIdReceive, "Pressed Enter for LPN ID in 190 Receiving");


        //                    Thread.sleep(5000);
                            Thread.sleep(2000);

                            currentLpnId = storeLpnIdFromWm.getText();


                            System.out.println("Created ILPN/LPN: " + currentLpnId);


                            // =============================================
                            // CREATE ILPN DATA
                            // =============================================

                            currentIlpnData = new ILPNData(currentLpnId);


                            asnData.addIlpn(currentIlpnData);


                            report.addReportStepWithScreenshot(StepStatus.PASS, "LPN ID from WM Mobile: " + currentLpnId + " for Item: " + item);
                        }


                        // =================================================
                        // ADD ITEM TO ILPN
                        // =================================================

                        currentIlpnData.addItem(itemData);


                        // =================================================
                        // ENTER ITEM
                        // =================================================

                        type(passItemReceive, item, "Item Barcode in 190 Receiving");


                        pressEnter(passItemReceive, "Pressed Enter for Item Barcode in 190 Receiving");

                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Item " + item);


                        Thread.sleep(4000);


                        // =================================================
                        // ENTER QUANTITY
                        // =================================================

                        type(passQtyReceive, quantity, "Quantity in 190 Receiving");


                        Thread.sleep(2000);


                        pressEnter(passQtyReceive, "Pressed Enter for Quantity in 190 Receiving");


                        Thread.sleep(3000);
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered quantity to receive " + quantity);


                        // =================================================
                        // END CURRENT ILPN
                        // =================================================

                        if (!isMixed) {

                            click(endLPNReceive, "Clicked End LPN in 190 Receiving");


                            Thread.sleep(3000);


                            System.out.println("Completed ILPN: " + currentLpnId + " for Item: " + item);


                            currentLpnId = null;


                            currentIlpnData = null;
                        }
                    }


                    // =====================================================
                    // MIXED TRANSACTION
                    // =====================================================

                    if (isMixed) {

                        click(endLPNReceive, "Clicked End Mixed ILPN in 190 Receiving");


        //                Thread.sleep(3000);
                        Thread.sleep(2000);

                        System.out.println("Completed Mixed ILPN: " + currentLpnId + " containing " + items.size() + " items");
                    }


                    // =====================================================
                    // END PALLET
                    // =====================================================

                    if (transaction.equalsIgnoreCase("ReceiveByPallet")) {

                        Thread.sleep(2000);


                        click(endPallet, "Pallet Ended");
                    }


                    // =====================================================
                    // CURRENT ASN COMPLETED
                    // =====================================================

                    System.out.println("Completed Receiving for ASN: " + asnId);


                    // =====================================================
                    // CLOSE WM MOBILE
                    // =====================================================

                    driver.close();


                    driver.switchTo().window(parentWindowId);


                    System.out.println("Returned to parent window after ASN: " + asnId);


                    // =====================================================
                    // OPEN ASNs UI
                    // =====================================================

                    click(menuToggleButton, "Menu Toggle");


        //            Thread.sleep(4000);


                    type(searchBarInLandingPage, "ASNs", "ASN UI");


                    Thread.sleep(3000);


                    click(clickAsnSFromMenu, "Clicked ASNs");


                    Thread.sleep(3000);


                    // =====================================================
                    // FILTER CURRENT ASN
                    // =====================================================

                    type(filterAsnById, asnId, "Filter ASN By ID");


                    Thread.sleep(3000);


                    pressEnter(filterAsnById, "Pressed Enter");


                    click(refresh, "Clicked Refresh in ASN UI");

                    waitForStatus(asnStatusValidation,
                            ()->click(refresh,"Refreshing ASN Page"+driver.findElement(asnStatusValidation).getText()),
                            "In Receiving");


        //            Thread.sleep(3000);


                    // =====================================================
                    // VERIFY CURRENT ASN
                    // =====================================================

                    AsnPage asnPage = new AsnPage();


                    asnPage.verifyASN(asnId);
                }


                // =========================================================
                // FINAL MESSAGE
                // =========================================================

                System.out.println("======================================");


                System.out.println("ALL ASN RECEIVING COMPLETED");


                System.out.println("======================================");


                report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully Receiving Completed");
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

                        // =================================================
                        // GET LOCATION ONCE FOR THIS ILPN
                        // =================================================

                        String location = locationDetails.getNextActiveLocation();

                        if (location == null || location.isBlank()) {

                            throw new IllegalStateException("No active location available for ILPN: " + ilpn);
                        }

                        // =================================================
                        // STORE EXPECTED LOCATION
                        // =================================================

                        storeIlpnLocation(ilpn, location);

                        // =================================================
                        // PROCESS ALL ITEMS OF THIS ILPN
                        // =================================================

                        for (ItemData itemData : ilpnData.getItems()) {

                            String item = itemData.getItem();

                            String quantity = itemData.getShippedQty();

                            System.out.println("--------------------------------------");

                            System.out.println("ILPN: " + ilpn + " | Item: " + item + " | Qty: " + quantity + " | Location: " + location);

                            System.out.println("--------------------------------------");

                            // =================================================
                            // YOUR EXISTING WM MOBILE FLOW
                            // =================================================

                            click(menuToggleButton, "Menu Toggle Clicked");

                            Thread.sleep(4000);

                            type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");

                            String parentWindowId = driver.getWindowHandle();

                            ScenarioContext.set("parentWindowId", parentWindowId);

                            click(clickWmMobileFromMenu, "Click WM Mobile from Menu");

                            waitForPageLoad();

                            Thread.sleep(5000);

                            for (String handle : driver.getWindowHandles()) {

                                if (!handle.equals(parentWindowId)) {

                                    driver.switchTo().window(handle);

                                    break;
                                }
                            }

                            Thread.sleep(5000);

                            type(searchBarInWmMobile, "190 User Directed", "Search Bar in WM Mobile(190 User Directed)");

                            Thread.sleep(3000);

                            click(clickUD, "Clicked 190 User Directed");

        //                    Thread.sleep(4000);
                            Thread.sleep(2000);

                            report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 User Directed Transaction");

                            // =================================================
                            // SCAN ILPN
                            // =================================================

                            type(scanContainer, ilpn, "Scan Container in 190 User Directed");

        //                    Thread.sleep(2000);

                            pressEnter(scanContainer, "Pressed Enter for Scan Container in 190 User Directed");

        //                    Thread.sleep(3000);
        //                    Thread.sleep(2000);
                            // =================================================
                            // SCAN ITEM
                            // =================================================

                            type(scanItemUD, item, "Scan Item in 190 User Directed");

        //                    Thread.sleep(2000);

                            pressEnter(scanItemUD, "Pressed Enter for Scan Item in 190 User Directed");

        //                    Thread.sleep(3000);
        //                    Thread.sleep(2000);
                            // =================================================
                            // SCAN QUANTITY
                            // =================================================

                            type(scanQtyUD, quantity, "Scan Quantity in 190 User Directed");

        //                    Thread.sleep(2000);

                            pressEnter(scanQtyUD, "Pressed Enter for Scan Quantity in 190 User Directed");

        //                    Thread.sleep(3000);

                            // =================================================
                            // SCAN LOCATION
                            // =================================================

                            type(scanLocUD, location, "Scan Location in 190 User Directed");

                            Thread.sleep(2000);

                            pressEnter(scanLocUD, "Pressed Enter for Scan Location in 190 User Directed");

                            Thread.sleep(10000);

                            // =================================================
                            // CLOSE WM MOBILE
                            // =================================================

                            driver.close();

                            driver.switchTo().window(parentWindowId);

                            System.out.println("Putaway completed for ILPN: " + ilpn + " | Item: " + item + " | Location: " + location);

                            report.addReportStepWithScreenshot(StepStatus.PASS, "Putaway completed for ILPN: " + ilpn + " | Item: " + item + " | Qty: " + quantity + " | Location: " + location);
                        }

                        // =====================================================
                        // IMPORTANT:
                        // Do NOT validate here for every ITEM.
                        // We validate ILPNs together after LPN Inventory opens.
                        // =====================================================
                    }

                    // =====================================================
                    // OPEN ASN UI
                    // =====================================================

                    click(menuToggleButton, "Menu Toggle");

                    Thread.sleep(4000);

                    type(searchBarInLandingPage, "ASNs", "ASN UI");

                    Thread.sleep(3000);

                    click(clickAsnSFromMenu, "Clicked ASNs");

                    Thread.sleep(5000);

                    // =====================================================
                    // FILTER ASN
                    // =====================================================

                    type(filterAsnById, asnId, "Filter ASN By ID");

                    Thread.sleep(3000);

                    pressEnter(filterAsnById, "Pressed Enter");

                    click(refresh, "Clicked Refresh in ASN UI");

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

                click(refresh, "Clicked Refresh in ASN UI");

                Thread.sleep(3000);

                report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully Completed ASN Putaway Process");
            }

            public void systemDirectedPutaway() throws InterruptedException {

                @SuppressWarnings("unchecked") List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");

                if (createdASNData == null || createdASNData.isEmpty()) {
                    throw new IllegalStateException("No Created ASN data found in ScenarioContext");
                }

                System.out.println("Total ASNs for System Directed Putaway: " + createdASNData.size());

                // =========================================================
                // LOOP THROUGH ALL ASNs
                // =========================================================

                for (ASNData asnData : createdASNData) {

                    String asnId = asnData.getAsn();

                    System.out.println("======================================");
                    System.out.println("Starting System Directed Putaway for ASN: " + asnId);
                    System.out.println("======================================");


                    // =====================================================
                    // LOOP THROUGH ALL ILPNs
                    // =====================================================

                    for (ILPNData ilpnData : asnData.getIlpns()) {

                        String ilpn = ilpnData.getIlpn();

                        System.out.println("Processing ILPN: " + ilpn);

                        System.out.println("--------------------------------------");
                        System.out.println("ILPN: " + ilpn + " | ASN: " + asnId);
                        System.out.println("--------------------------------------");


                        // =================================================
                        // STORE PARENT WINDOW
                        // =================================================

                        String parentWindowId = driver.getWindowHandle();

                        ScenarioContext.set("parentWindowId", parentWindowId);


                        // =================================================
                        // OPEN WM MOBILE
                        // =================================================

                        click(menuToggleButton, "Menu Toggle Clicked");

                        Thread.sleep(4000);

                        type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");

                        Thread.sleep(3000);

                        click(clickWmMobileFromMenu, "Click WM Mobile from Menu");

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

                        type(searchBarInWmMobile, "190 System Directed", "Search Bar in WM Mobile (190 System Directed)");

                        Thread.sleep(3000);

                        click(click190SD, "Clicked 190 System Directed");

                        Thread.sleep(4000);
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 System Directed Transaction");


                        // =================================================
                        // SCAN ILPN / CONTAINER
                        // =================================================

                        type(scanContainer, ilpn, "Scan Container in 190 System Directed");

                        Thread.sleep(2000);

                        pressEnter(scanContainer, "Pressed Enter for Scan Container in 190 System Directed");
                        report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the ILPN " + ilpn + " in 190 System Directed Transaction");


                        // =================================================
                        // WAIT FOR SYSTEM DIRECTED LOCATION
                        // =================================================

                        By systemDirectedLocationLocator = By.xpath("//ion-col[@data-component-id='acceptlocationforsystemdirectedputaway_barcodetextfield_location']");

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));


                        // =================================================
                        // WAIT UNTIL LOCATION ELEMENT APPEARS
                        // =================================================

                        WebElement systemDirectedLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(systemDirectedLocationLocator));


                        // =================================================
                        // WAIT UNTIL LOCATION TEXT IS AVAILABLE
                        // =================================================

                        String location = wait.until(driver -> {

                            String text = systemDirectedLocation.getText().trim();

                            return text.isEmpty() ? null : text;
                        });


                        // =================================================
                        // PRINT SYSTEM GENERATED LOCATION
                        // =================================================

                        System.out.println("System Directed Location received: " + location);
                        storeIlpnLocation(ilpn, location);


                        // =================================================
                        // VALIDATE LOCATION
                        // =================================================

                        if (location == null || location.isEmpty()) {

                            throw new IllegalStateException("System Directed Putaway location is empty " + "for ILPN: " + ilpn);
                        }


                        // =================================================
                        // SCAN SYSTEM DIRECTED LOCATION
                        // =================================================

                        type(scanLocUD, location, "Scan System Directed Location");

                        Thread.sleep(2000);

                        pressEnter(scanLocUD, "Pressed Enter for System Directed Location");


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

                        System.out.println("System Directed Putaway completed for ILPN: " + ilpn + " | Location: " + location);


                        report.addReportStepWithScreenshot(StepStatus.PASS, "System Directed Putaway completed for ILPN: " + ilpn + " | Location: " + location);
                    }


                    // =====================================================
                    // OPEN ASN UI
                    // =====================================================

                    click(menuToggleButton, "Menu Toggle");

                    Thread.sleep(4000);

                    type(searchBarInLandingPage, "ASNs", "ASN UI");

                    Thread.sleep(3000);

                    click(clickAsnSFromMenu, "Clicked ASNs");

                    Thread.sleep(5000);


                    // =====================================================
                    // FILTER CURRENT ASN
                    // =====================================================

                    type(filterAsnById, asnId, "Filter ASN By ID");

                    Thread.sleep(3000);

                    pressEnter(filterAsnById, "Pressed Enter");


                    // =====================================================
                    // REFRESH ASN
                    // =====================================================

                    click(refresh, "Clicked Refresh in ASN UI");

                    Thread.sleep(3000);


                    // =====================================================
                    // VERIFY CURRENT ASN
                    // =====================================================


                    List<String> ilpns = new ArrayList<>();

                    for (ILPNData ilpnData : asnData.getIlpns()) {

                        ilpns.add(ilpnData.getIlpn());
                    }

                    AsnPage asnPage = new AsnPage();

                    asnPage.verifyILPNLocation(asnId, ilpns);
                }


                // =========================================================
                // ALL SYSTEM DIRECTED PUTAWAY COMPLETED
                // =========================================================

                System.out.println("======================================");
                System.out.println("ALL SYSTEM DIRECTED PUTAWAY COMPLETED");
                System.out.println("======================================");


                Thread.sleep(3000);


                click(refresh, "Clicked Refresh in ASN UI");

                Thread.sleep(3000);


                report.addReportStepWithScreenshot(StepStatus.PASS, "Successfully Completed System Directed Putaway ASN Process");
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

                    click(menuToggleButton, "Menu Toggle");

                    Thread.sleep(4000);


                    type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");


                    // =====================================================
                    // STORE PARENT WINDOW
                    // =====================================================

                    String parentWindowId = driver.getWindowHandle();


                    ScenarioContext.set("parentWindowId", parentWindowId);


                    // =====================================================
                    // OPEN WM MOBILE
                    // =====================================================

                    click(clickWmMobileFromMenu, "Click WM Mobile from Menu");


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

                    type(searchBarInWmMobile, "190 Receiving", "Search Bar in WM Mobile(190 Receiving)");


                    Thread.sleep(3000);


                    click(click190Receiving, "Clicked 190 Receiving");


                    Thread.sleep(2000);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered 190 Receiving Transaction");


                    // =====================================================
                    // ENTER ASN
                    // =====================================================

                    type(passAsnIdReceive, asnId, "ASN ID in 190 Receiving");


                    pressEnter(passAsnIdReceive, "Pressed Enter for ASN ID in 190 Receiving");

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

                            pressEnter(passLpnIdReceive, "Pressed Enter for New LPN - Unit " + unit);


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

                            type(passItemReceive, item, "Item Barcode in 190 Receiving");


                            pressEnter(passItemReceive, "Pressed Enter for Item Barcode in 190 Receiving");
                            report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Item " + item + " for LPN " + currentLpnId + " in 190 Receiving Transaction");


                            Thread.sleep(4000);


                            // =============================================
                            // ENTER QUANTITY = 1
                            // =============================================

                            type(passQtyReceive, "1", "Quantity = 1 in 190 Receiving");


                            Thread.sleep(2000);
                            report.addReportStepWithScreenshot(StepStatus.PASS, "User Scanned the Quantity 1 for Item " + item + " in LPN " + currentLpnId + " in 190 Receiving Transaction");


                            pressEnter(passQtyReceive, "Pressed Enter for Quantity = 1 in 190 Receiving");


                            Thread.sleep(3000);


                            // =============================================
                            // END CURRENT LPN
                            // =============================================

                            click(endLPNReceive, "Clicked End LPN in 190 Receiving");


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

                    click(menuToggleButton, "Menu Toggle");

                    Thread.sleep(4000);


                    type(searchBarInLandingPage, "ASNs", "ASN UI");


                    Thread.sleep(3000);


                    click(clickAsnSFromMenu, "Clicked ASNs");


                    Thread.sleep(5000);


                    // =====================================================
                    // FILTER CURRENT ASN
                    // =====================================================

                    type(filterAsnById, asnId, "Filter ASN By ID");


                    Thread.sleep(3000);


                    pressEnter(filterAsnById, "Pressed Enter");


                    click(refresh, "Clicked Refresh in ASN UI");


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
        //        click(menuToggleButton, "Menu Toggle Clicked");
        //
        //        Thread.sleep(4000);
        //
        //
        //        type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");
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
        //        click(clickWmMobileFromMenu, "Click WM Mobile from Menu");
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
        //        type(searchBarInWmMobile, "190 User Directed", "Search Bar in WM Mobile(190 User Directed)");
        //
        //        Thread.sleep(3000);
        //
        //
        //        click(clickUD, "Clicked 190 User Directed");
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
        //        type(scanContainer, palletId, "Scan Pallet ID in 190 User Directed");
        //
        //        Thread.sleep(2000);
        //
        //
        //        pressEnter(scanContainer, "Pressed Enter for Pallet ID in 190 User Directed");
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
        //        type(scanLocUD, location, "Scan Location in 190 User Directed");
        //
        //        Thread.sleep(2000);
        //
        //
        //        pressEnter(scanLocUD, "Pressed Enter for Location in 190 User Directed");
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

                click(
                        menuToggleButton,
                        "Menu Toggle Clicked"
                );

                Thread.sleep(4000);


                type(
                        searchBarInLandingPage,
                        "WM Mobile",
                        "Search Bar in Landing Page"
                );


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

                click(
                        clickWmMobileFromMenu,
                        "Click WM Mobile from Menu"
                );

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
                        "190 User Directed",
                        "Search Bar in WM Mobile(190 User Directed)"
                );

                Thread.sleep(3000);


                click(
                        clickUD,
                        "Clicked 190 User Directed"
                );

                Thread.sleep(4000);

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "User Entered 190 Putaway Transaction"
                );


                // =========================================================
                // SCAN PALLET ID
                // =========================================================

                type(
                        scanContainer,
                        palletId,
                        "Scan Pallet ID in 190 User Directed"
                );

                Thread.sleep(2000);


                pressEnter(
                        scanContainer,
                        "Pressed Enter for Pallet ID in 190 User Directed"
                );

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
                        location,
                        "Scan Location in 190 User Directed"
                );

                Thread.sleep(2000);


                pressEnter(
                        scanLocUD,
                        "Pressed Enter for Location in 190 User Directed"
                );

                Thread.sleep(10000);

                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "User Scanned the Location "
                                + location
                                + " in 190 User Directed Transaction"
                );


                // =========================================================
                // CLOSE WM MOBILE
                // =========================================================

                driver.close();


                // =========================================================
                // RETURN TO PARENT WINDOW
                // =========================================================

                driver.switchTo().window(parentWindowId);


                // =========================================================
                // PALLET PUTAWAY COMPLETED
                // =========================================================

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

                        // Store ILPN -> Putaway Location
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
                    System.out.println("STARTING PALLET ILPN VERIFICATION");
                    System.out.println("ASN: " + asnId);
                    System.out.println("Pallet ID: " + palletId);
                    System.out.println("Putaway Location: " + location);
                    System.out.println("ILPN Count: " + ilpns.size());
                    AsnPage asnPage =
                            new AsnPage();
                    asnPage.verifyILPNLocation(
                            asnId,
                            ilpns,
                            palletId
                    );
                }
                report.addReportStepWithScreenshot(
                        StepStatus.PASS,
                        "Pallet Putaway and ILPN Verification completed "
                                + "for Pallet: "
                                + palletId
                                + " | Location: "
                                + location
                );
            }



            public void palletizeILPN() throws InterruptedException {
                click(menuToggleButton, "Menu Toggle Clicked");
                Thread.sleep(4000);
                type(searchBarInLandingPage, "WM Mobile", "Search Bar in Landing Page");


                // =================================================
                // STORE PARENT WINDOW
                // =================================================

                String parentWindowId = driver.getWindowHandle();

                ScenarioContext.set("parentWindowId", parentWindowId);


                // =================================================
                // OPEN WM MOBILE
                // =================================================

                click(clickWmMobileFromMenu, "Click WM Mobile from Menu");

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
                type(searchBarInWmMobile, "palletize ilpn", "palletize ilpn");

                Thread.sleep(3000);


                click(clickPalletizeILPN, "Clicked Palletize ILPN");
                report.addReportStepWithScreenshot(StepStatus.PASS, "User Entered Palletize ILPN Transaction");

                Thread.sleep(4000);
                click(generatePallet, "Clicked Generate Pallet");

                Thread.sleep(4000);
                click(confirmPrinter, "Clicked Confirm Printer");
                Thread.sleep(5000);
                List<ASNData> createdASNData = (List<ASNData>) ScenarioContext.get("CreatedASNData");
                if (createdASNData == null || createdASNData.isEmpty()) {
                    throw new IllegalStateException("No Created ASN data found in ScenarioContext");
                }
                Thread.sleep(5000);

                System.out.println("Total ASNs for Palletize: " + createdASNData.size());
                LocationDetails locationDetails = new LocationDetails();
                for (ASNData asnData : createdASNData) {
                    String asnId = asnData.getAsn();
                    for (ILPNData ilpnData : asnData.getIlpns()) {
                        Thread.sleep(3000);
                        String ilpn = ilpnData.getIlpn();
                        System.out.println("Processing ILPN: " + ilpn);
                        report.addReportStepWithScreenshot(StepStatus.PASS, "Processing ILPN: " + ilpn);
                        type(scanILPN, ilpn, "Scan ILPN in Palletize ILPN");
                        Thread.sleep(3000);
                        pressEnter(scanILPN, "Pressed Enter for ILPN in Palletize ILPN");
                    }
                    String palletId = getPalletFromPalletizeTransaction.getText();
                    ScenarioContext.set("CreatedPalletId", palletId);
                    report.addReportStepWithScreenshot(StepStatus.PASS, "Pallet ID from Palletize ILPN: " + palletId);
                    Thread.sleep(2000);
                    click(endPallet, "Clicked End Pallet");
                }
                driver.close();
                driver.switchTo().window(parentWindowId);
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

                    click(
                            menuToggleButton,
                            "Menu Toggle"
                    );

                    Thread.sleep(4000);

                    type(
                            searchBarInLandingPage,
                            "WM Mobile",
                            "Search Bar in Landing Page"
                    );

                    String parentWindowId =
                            driver.getWindowHandle();

                    ScenarioContext.set(
                            "parentWindowId",
                            parentWindowId
                    );

                    click(
                            clickWmMobileFromMenu,
                            "Click WM Mobile from Menu"
                    );

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

                    type(
                            searchBarInWmMobile,
                            "190 Receiving",
                            "Search Bar in WM Mobile"
                    );

                    Thread.sleep(3000);

                    click(
                            click190Receiving,
                            "Clicked 190 Receiving"
                    );

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
                            asnId,
                            "ASN ID in 190 Receiving"
                    );

                    pressEnter(
                            passAsnIdReceive,
                            "Pressed Enter for ASN ID: "
                                    + asnId
                    );

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

                    pressEnter(
                            scanPallet,
                            "Pallet ID Auto Generated"
                    );

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

                        pressEnter(
                                passLpnIdReceive,
                                "LPN ID Auto Generated"
                        );

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
                                item,
                                "Item Barcode in 190 Receiving"
                        );

                        pressEnter(
                                passItemReceive,
                                "Pressed Enter for Item: "
                                        + item
                        );

                        Thread.sleep(4000);

                        // =================================================
                        // ENTER UNITS
                        // =================================================

                        type(
                                passQtyReceive,
                                quantity,
                                "Quantity in 190 Receiving"
                        );

                        Thread.sleep(2000);

                        pressEnter(
                                passQtyReceive,
                                "Pressed Enter for Quantity: "
                                        + quantity
                        );

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

                        click(
                                endLPNReceive,
                                "Clicked End LPN"
                        );

                        Thread.sleep(3000);

                        System.out.println(
                                "Completed LPN: "
                                        + lpnId
                        );
                    }

                    // =====================================================
                    // END PALLET
                    // =====================================================

                    Thread.sleep(2000);

                    click(
                            endPallet,
                            "Ended Pallet"
                    );

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

                    click(
                            menuToggleButton,
                            "Menu Toggle"
                    );

                    Thread.sleep(4000);

                    type(
                            searchBarInLandingPage,
                            "ASNs",
                            "ASN UI"
                    );

                    Thread.sleep(3000);

                    click(
                            clickAsnSFromMenu,
                            "Clicked ASNs"
                    );

                    Thread.sleep(3000);

                    // =====================================================
                    // FILTER ASN
                    // =====================================================

                    type(
                            filterAsnById,
                            asnId,
                            "Filter ASN By ID"
                    );

                    Thread.sleep(2000);

                    pressEnter(
                            filterAsnById,
                            "Pressed Enter"
                    );

                    Thread.sleep(2000);

                    click(
                            refresh,
                            "Clicked Refresh in ASN UI"
                    );

                    // =====================================================
                    // WAIT FOR RECEIVING STATUS
                    // =====================================================

                    waitForStatus(
                            asnStatusValidation,
                            () -> click(
                                    refresh,
                                    "Refreshing ASN Page "
                                            + driver.findElement(
                                            asnStatusValidation
                                    ).getText()
                            ),
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
        }


