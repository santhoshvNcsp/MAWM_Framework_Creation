package stepdefinitions;

import Pages.AsnPage;
import Pages.ItemDetails;
import com.p09.framework.context.ScenarioContext;
import context.ASNData;
import context.ILPNData;
import context.ItemData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class ASNOperations {

    AsnPage asnPage = new AsnPage();

    @When("user navigates to {string} page")
    public void navigateToASN(String uiName) throws InterruptedException {
        asnPage.navigateToAnyUi(uiName);
    }

    @Then("user creates {int} asn in {string} status with {int} lineItems with {string}")
    public void createASN(
            int asnCnt,
            String status,
            int asnDetailCnt,
            String shippedQtyCnt)
            throws InterruptedException {
        ItemDetails itemDetails = new ItemDetails();
        List<ASNData> createdASNData = new ArrayList<>();
        String[] quantityArray = shippedQtyCnt.split(",");
        List<String> quantities = new ArrayList<>();
        for (String quantity : quantityArray) {
            quantities.add(quantity.trim());
        }
        System.out.println("=================================");
        System.out.println("Total ASNs to Create: " + asnCnt);
        System.out.println("Line Items per ASN: " + asnDetailCnt);
        System.out.println("Quantities: " + quantities);
        System.out.println("=================================");
        for (int i = 0; i < asnCnt; i++) {
            System.out.println("=================================");
            System.out.println("Starting Creating ASN " + (i + 1));
            System.out.println("=================================");
            List<String> items = new ArrayList<>();
            for (int j = 0; j < asnDetailCnt; j++) {

                System.out.println(
                        "Starting Creation of lineItem " + (j + 1)
                );

                items.add(
                        itemDetails.primaryItems.get(j)
                );
            }
            System.out.println(
                    "Items for ASN " + (i + 1) + ": " + items
            );
            String createdAsn =
                    asnPage.provideAsnDetails(
                            items,
                            shippedQtyCnt,
                            i + 1,status
                    );
            System.out.println(
                    "Created ASN " + (i + 1) + ": " + createdAsn
            );
            ASNData asnData =
                    new ASNData(createdAsn);
            for (int j = 0; j < items.size(); j++) {
                String item = items.get(j);
                String quantity = quantities.get(j);
                ItemData itemData =
                        new ItemData(
                                item,
                                quantity
                        );
                asnData.addItem(itemData);
                System.out.println(
                        "Added Item: " + item
                                + " | Qty: " + quantity
                );
            }
            createdASNData.add(asnData);
            System.out.println(
                    "Stored ASN Data: " + createdAsn
            );
        }
        ScenarioContext.set(
                "CreatedASNData",
                createdASNData
        );
        // =========================================================
        // DEBUG OUTPUT
        // =========================================================
        System.out.println("=================================");
        System.out.println("ALL CREATED ASN DATA");
        System.out.println("=================================");
        for (ASNData asnData : createdASNData) {
            System.out.println(
                    "ASN: " + asnData.getAsn()
            );
            System.out.println(
                    "Items:"
            );
            for (ItemData itemData : asnData.getItems()) {
                System.out.println(
                        "  Item: " + itemData.getItem()
                                + " | Qty: "
                                + itemData.getShippedQty()
                );
            }
            System.out.println(
                    "ILPNs: " + asnData.getIlpns()
            );
        }
        System.out.println("=================================");
    }

    @Then("user creates {int} lpnLvlAsn in {string} status with {int} lineItems with {string}")
    public void userCreatesLpnLvlAsn(
            int asnCnt,
            String status,
            int numberOfLineItems,
            String shippedQty) throws Exception {

        if (asnCnt <= 0) {
            throw new IllegalArgumentException(
                    "ASN count must be greater than 0"
            );
        }

        if (numberOfLineItems <= 0) {
            throw new IllegalArgumentException(
                    "Line item count must be greater than 0"
            );
        }

        ItemDetails itemDetails =
                new ItemDetails();


        if (itemDetails.primaryItems.size()
                < numberOfLineItems) {

            throw new IllegalArgumentException(
                    "Not enough items available in primaryItems. "
                            + "Required: "
                            + numberOfLineItems
                            + ", Available: "
                            + itemDetails.primaryItems.size()
            );
        }

        String[] quantityArray =
                shippedQty.split(",");


        if (quantityArray.length
                != numberOfLineItems) {

            throw new IllegalArgumentException(
                    "Line item count ("
                            + numberOfLineItems
                            + ") does not match quantity count ("
                            + quantityArray.length
                            + ")"
            );
        }

        List<String> quantities =
                new ArrayList<>();
        for (String quantity : quantityArray) {

            quantities.add(
                    quantity.trim()
            );
        }
        List<ASNData> createdASNData =
                new ArrayList<>();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "Creating LPN Level ASNs"
        );
        System.out.println(
                "Total ASNs: "
                        + asnCnt
        );
        System.out.println(
                "Status: "
                        + status
        );

        System.out.println(
                "Line Items per ASN: "
                        + numberOfLineItems
        );

        System.out.println(
                "Quantities: "
                        + quantities
        );

        System.out.println(
                "========================================"
        );


        // =========================================================
        // CREATE MULTIPLE LPN LEVEL ASNs
        // =========================================================

        for (int asnIndex = 0;
             asnIndex < asnCnt;
             asnIndex++) {


            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Starting LPN Level ASN "
                            + (asnIndex + 1)
                            + " of "
                            + asnCnt
            );

            System.out.println(
                    "========================================"
            );


            // =====================================================
            // GET ITEMS FOR CURRENT ASN
            // =====================================================

            List<String> items =
                    new ArrayList<>();


            for (int itemIndex = 0;
                 itemIndex < numberOfLineItems;
                 itemIndex++) {

                items.add(
                        itemDetails.primaryItems.get(
                                itemIndex
                        )
                );
            }


            System.out.println(
                    "Items for ASN "
                            + (asnIndex + 1)
                            + ": "
                            + items
            );


            System.out.println(
                    "Quantities for ASN "
                            + (asnIndex + 1)
                            + ": "
                            + quantities
            );


            // =====================================================
            // CALL LPN LEVEL ASN API
            // =====================================================

            String response =
                    asnPage.createLpnLevelAsn(
                            items,
                            shippedQty
                    );


            // =====================================================
            // PRINT API RESPONSE
            // =====================================================

            System.out.println(
                    "LPN Level ASN API Response for ASN "
                            + (asnIndex + 1)
                            + ":"
            );

            System.out.println(response);


            // =====================================================
            // PARSE RESPONSE
            // =====================================================

            io.restassured.path.json.JsonPath jsonPath =
                    new io.restassured.path.json.JsonPath(
                            response
                    );


            // =====================================================
            // VALIDATE API SUCCESS
            // =====================================================

            Boolean success =
                    jsonPath.getBoolean("success");


            if (!Boolean.TRUE.equals(success)) {

                throw new AssertionError(
                        "LPN Level ASN creation failed for ASN "
                                + (asnIndex + 1)
                                + ".\n"
                                + response
                );
            }


            // =====================================================
            // GET ASN ID
            // =====================================================

            String createdAsnId =
                    jsonPath.getString(
                            "data.AsnId"
                    );


            if (createdAsnId == null
                    || createdAsnId.isBlank()) {

                throw new AssertionError(
                        "ASN ID was not returned for ASN "
                                + (asnIndex + 1)
                                + ".\n"
                                + response
                );
            }


            // =====================================================
            // GET ILPN IDs
            // =====================================================

            List<String> ilpnIds =
                    jsonPath.getList(
                            "data.Lpn.LpnId",
                            String.class
                    );


            // =====================================================
            // VALIDATE ILPN COUNT
            // =====================================================

            if (ilpnIds == null
                    || ilpnIds.size()
                    != numberOfLineItems) {

                throw new AssertionError(
                        "Expected "
                                + numberOfLineItems
                                + " ILPNs for ASN "
                                + createdAsnId
                                + " but API returned "
                                + (ilpnIds == null
                                ? 0
                                : ilpnIds.size())
                                + "\n"
                                + response
                );
            }


            // =====================================================
            // CREATE ASN DATA
            // =====================================================

            ASNData lpnLevelAsnData =
                    new ASNData(
                            createdAsnId
                    );


            // =====================================================
            // STORE ITEMS + ILPNs
            // =====================================================

            for (int itemIndex = 0;
                 itemIndex < ilpnIds.size();
                 itemIndex++) {


                String ilpnId =
                        ilpnIds.get(itemIndex);


                String item =
                        items.get(itemIndex);


                String quantity =
                        quantities.get(itemIndex);


                // =================================================
                // CREATE ITEM DATA
                // =================================================

                ItemData itemData =
                        new ItemData(
                                item,
                                quantity
                        );


                lpnLevelAsnData.addItem(
                        itemData
                );


                // =================================================
                // CREATE ILPN DATA
                // =================================================

                ILPNData ilpnData =
                        new ILPNData(
                                ilpnId
                        );


                ilpnData.addItem(
                        itemData
                );


                lpnLevelAsnData.addIlpn(
                        ilpnData
                );


                // =================================================
                // STORE INDIVIDUAL LPN
                //
                // Global numbering across all created ASNs
                // =================================================

                int globalLpnNumber =
                        (asnIndex * numberOfLineItems)
                                + itemIndex
                                + 1;


                ScenarioContext.set(
                        "Created LPN Level ILPN "
                                + globalLpnNumber,
                        ilpnId
                );


                System.out.println(
                        "ASN: "
                                + createdAsnId
                                + " | ILPN "
                                + (itemIndex + 1)
                                + ": "
                                + ilpnId
                                + " | Item: "
                                + item
                                + " | Qty: "
                                + quantity
                );
            }


            // =====================================================
            // STORE CURRENT LPN LEVEL ASN DATA
            // =====================================================

            ScenarioContext.set(
                    "Created LPN Level ASN "
                            + (asnIndex + 1),
                    createdAsnId
            );


            // =====================================================
            // KEEP FIRST ASN FOR BACKWARD COMPATIBILITY
            // =====================================================

            if (asnIndex == 0) {

                ScenarioContext.set(
                        "Created LPN Level ASN",
                        createdAsnId
                );
            }


            // =====================================================
            // ADD CURRENT ASN TO COMPLETE ASN LIST
            // =====================================================

            createdASNData.add(
                    lpnLevelAsnData
            );


            System.out.println(
                    "Stored LPN Level ASN Data: "
                            + createdAsnId
            );


            System.out.println(
                    "Completed LPN Level ASN "
                            + (asnIndex + 1)
                            + ": "
                            + createdAsnId
            );
        }


        // =========================================================
        // STORE ALL ASN DATA
        // =========================================================

        ScenarioContext.set(
                "CreatedASNData",
                createdASNData
        );


        // =========================================================
        // FINAL OUTPUT
        // =========================================================

        System.out.println(
                "========================================"
        );

        System.out.println(
                "ALL LPN LEVEL ASNs CREATED SUCCESSFULLY"
        );

        System.out.println(
                "Total ASNs: "
                        + createdASNData.size()
        );

        System.out.println(
                "========================================"
        );


        for (ASNData asnData :
                createdASNData) {

            System.out.println(
                    "ASN: "
                            + asnData.getAsn()
            );

            System.out.println(
                    "Items: "
                            + asnData.getItems()
            );

            System.out.println(
                    "ILPNs: "
                            + asnData.getIlpns()
            );
        }


        System.out.println(
                "========================================"
        );
    }


}