package stepdefinitions;

import Pages.OrdersPage;
import com.p09.framework.context.ScenarioContext;
import context.OrderData;
import context.OrderLineData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class OrdersOperations {

    private final OrdersPage ordersPage =
            new OrdersPage();
    @When("user navigates to {string} pages")
    public void navigateToOrdersPage(
            String uiName)
            throws InterruptedException {

        ordersPage.navigateToAnyUi(uiName);
    }

    @Then("user creates {int} order with lineItems {string} and {string} with order type {string} and priority {string}")
    public void createOrder(
            int orderCount,
            String itemsInput,
            String quantitiesInput,
            String orderType,
            String orderPriority)
            throws InterruptedException {


        // =====================================================
        // VALIDATE BASIC INPUT
        // =====================================================

        if (orderCount <= 0) {

            throw new IllegalArgumentException(
                    "Order count must be greater than 0"
            );
        }


        if (itemsInput == null
                || itemsInput.isBlank()) {

            throw new IllegalArgumentException(
                    "Items cannot be empty"
            );
        }


        if (quantitiesInput == null
                || quantitiesInput.isBlank()) {

            throw new IllegalArgumentException(
                    "Quantities cannot be empty"
            );
        }


        if (orderType == null
                || orderType.isBlank()) {

            throw new IllegalArgumentException(
                    "Order type cannot be empty"
            );
        }


        if (orderPriority == null
                || orderPriority.isBlank()) {

            throw new IllegalArgumentException(
                    "Order priority cannot be empty"
            );
        }


        // =====================================================
        // PARSE ITEMS
        // =====================================================

        String[] itemArray =
                itemsInput.split(",");


        List<String> items =
                new ArrayList<>();


        for (String item : itemArray) {

            if (item == null
                    || item.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Item cannot be empty"
                );
            }

            items.add(
                    item.trim()
            );
        }
        int lineItemCount =
                items.size();


        if (lineItemCount <= 0) {

            throw new IllegalArgumentException(
                    "At least one item must be provided"
            );
        }


        // =====================================================
        // PARSE QUANTITIES
        // =====================================================

        String[] quantityArray =
                quantitiesInput.split(",");


        List<String> quantities =
                new ArrayList<>();


        for (String quantity : quantityArray) {

            if (quantity == null
                    || quantity.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Quantity cannot be empty"
                );
            }

            quantities.add(
                    quantity.trim()
            );
        }


        if (quantities.isEmpty()) {

            throw new IllegalArgumentException(
                    "At least one quantity must be provided"
            );
        }


        // =====================================================
        // PRINT INPUT
        // =====================================================

        System.out.println(
                "========================================"
        );

        System.out.println(
                "ORDER CREATION"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Total Orders: "
                        + orderCount
        );

        System.out.println(
                "Line Items: "
                        + lineItemCount
        );

        System.out.println(
                "Items: "
                        + items
        );

        System.out.println(
                "Quantities: "
                        + quantities
        );

        System.out.println(
                "Order Type: "
                        + orderType
        );

        System.out.println(
                "Order Priority: "
                        + orderPriority
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // CREATE ORDER DATA LIST
        // =====================================================

        List<OrderData> createdOrderData =
                new ArrayList<>();


        // =====================================================
        // CREATE ORDERS
        // =====================================================

        for (int orderIndex = 0;
             orderIndex < orderCount;
             orderIndex++) {

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Starting Order "
                            + (orderIndex + 1)
                            + " of "
                            + orderCount
            );

            System.out.println(
                    "========================================"
            );


            // =================================================
            // CREATE ORDER IN UI
            // =================================================

            String createdOrderId =
                    ordersPage.createOrder(
                            orderType,
                            orderPriority
                    );

            OrderData orderData =
                    new OrderData(
                            createdOrderId,
                            orderType,
                            orderPriority,
                            lineItemCount
                    );


            // =================================================
            // CREATE ORDER LINE DATA
            // =================================================

            for (int lineIndex = 0;
                 lineIndex < lineItemCount;
                 lineIndex++) {

                String lineNumber =
                        String.valueOf(
                                lineIndex + 1
                        );


                String item =
                        items.get(lineIndex);


                /*
                 * =================================================
                 * CYCLIC QUANTITY
                 * =================================================
                 *
                 * Example:
                 *
                 * Items:
                 * 19005,19003,19001,19007
                 *
                 * Quantities:
                 * 5,7
                 *
                 * Result:
                 *
                 * 19005 -> 5
                 * 19003 -> 7
                 * 19001 -> 5
                 * 19007 -> 7
                 *
                 * The % operator makes the quantity
                 * list repeat automatically.
                 */

                String quantity =
                        quantities.get(
                                lineIndex
                                        % quantities.size()
                        );


                OrderLineData orderLineData =
                        new OrderLineData(
                                lineNumber,
                                item,
                                quantity
                        );


                orderData.addOrderLine(
                        orderLineData
                );


                System.out.println(
                        "Added Order Line: "
                                + lineNumber
                                + " | Item: "
                                + item
                                + " | Qty: "
                                + quantity
                );
            }


            // =================================================
            // CREATE ORDER LINES IN UI
            // =================================================

            ordersPage.createOrderLines(
                    orderData
            );


            // =================================================
            // ASSIGN PIPELINE
            // =================================================

            ordersPage.assignPipelineAndWaitForRelease(
                    orderData
            );


            // =================================================
            // RUN WAVE
            // =================================================

            ordersPage.runWave(
                    orderData
            );


            // =================================================
            // STORE ORDER DATA
            // =================================================

            createdOrderData.add(
                    orderData
            );


            System.out.println(
                    "Completed Order: "
                            + orderData.getOrderId()
            );
        }


        // =====================================================
        // STORE ALL ORDER DATA
        // =====================================================

        ScenarioContext.set(
                "CreatedOrderData",
                createdOrderData
        );


        // =====================================================
        // BACKWARD COMPATIBILITY
        // =====================================================

        if (!createdOrderData.isEmpty()) {

            OrderData firstOrder =
                    createdOrderData.get(0);


            ScenarioContext.set(
                    "Created Order",
                    firstOrder.getOrderId()
            );

            ScenarioContext.set(
                    "Created Order Priority",
                    firstOrder.getOrderPriority()
            );

            List<String> originalOrderLineNumbers =
                    new ArrayList<>();

            for (OrderLineData line : firstOrder.getOrderLines()) {
                if (line.getLineNumber() != null
                        && !line.getLineNumber().isBlank()) {
                    originalOrderLineNumbers.add(
                            line.getLineNumber().trim()
                    );
                }
            }

            ScenarioContext.set(
                    "Original Order Line Numbers",
                    originalOrderLineNumbers
            );

            System.out.println(
                    "Created Order: "
                            + firstOrder.getOrderId()
            );
        }


        // =====================================================
        // FINAL OUTPUT
        // =====================================================

        System.out.println(
                "========================================"
        );

        System.out.println(
                "ALL CREATED ORDER DATA"
        );

        System.out.println(
                "========================================"
        );


        for (OrderData orderData :
                createdOrderData) {

            System.out.println(
                    "Order ID: "
                            + orderData.getOrderId()
            );

            System.out.println(
                    "Order Type: "
                            + orderData.getOrderType()
            );

            System.out.println(
                    "Order Priority: "
                            + orderData.getOrderPriority()
            );

            System.out.println(
                    "Line Count: "
                            + orderData.getOrderLineCount()
            );

            System.out.println(
                    "Order Lines:"
            );


            for (OrderLineData line :
                    orderData.getOrderLines()) {

                System.out.println(
                        "  Line: "
                                + line.getLineNumber()
                                + " | Item: "
                                + line.getItem()
                                + " | Qty: "
                                + line.getQuantity()
                );
            }
        }


        System.out.println(
                "========================================"
        );
    }


    // =========================================================
    // EXISTING ORDER
    // =========================================================

    @Then("user processes existing order {string}")
    public void processExistingOrder(
            String orderId)
            throws InterruptedException {

        if (orderId == null
                || orderId.isBlank()) {

            throw new IllegalArgumentException(
                    "Existing order ID cannot be empty"
            );
        }


        orderId = orderId.trim();


        System.out.println(
                "========================================"
        );

        System.out.println(
                "PROCESSING EXISTING ORDER"
        );

        System.out.println(
                "Order ID: "
                        + orderId
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // CREATE ORDER DATA
        // =====================================================

        OrderData orderData =
                new OrderData(orderId);

        orderData.setOrderId(orderId);


        // =====================================================
        // STORE IN SCENARIO CONTEXT
        // =====================================================

        ScenarioContext.set(
                "Created Order",
                orderId
        );

        ScenarioContext.set(
                "ExistingOrderData",
                orderData
        );


        // =====================================================
        // FILTER EXISTING ORDER
        // =====================================================

        ordersPage.filterExistingOrder(
                orderId
        );


        // =====================================================
        // EXISTING ORDER IS ASSUMED RELEASED
        // =====================================================

        System.out.println(
                "Existing order is assumed to be Released."
        );


        // =====================================================
        // RUN WAVE
        // =====================================================

        ordersPage.runWave(
                orderData
        );


        System.out.println(
                "========================================"
        );

        System.out.println(
                "EXISTING ORDER WAVE PROCESS COMPLETED"
        );

        System.out.println(
                "Order ID: "
                        + orderId
        );

        System.out.println(
                "========================================"
        );
    }

    @Then("user ship confirms the order")
    public void userShipConfirmsTheOrder() throws InterruptedException {
        ordersPage.shipConfirmOrder();
    }




    @And("user assigns pipeline to all created orders and runs the initial wave")
    public void userAssignsPipelineAndRunsInitialBatchWave()
            throws InterruptedException {

        ordersPage.assignPipelineToBatchAndWaitForRelease();

        String waveNumber = ordersPage.runInitialWaveForBatch();

        System.out.println(
                "Initial batch wave submitted: " + waveNumber
        );
    }


    @And("user verifies all created orders are Allocated")
    public void userVerifiesAllBatchOrdersAreAllocated()
            throws InterruptedException {

        ordersPage.verifyAllBatchOrdersAllocated();
    }
    @Then("user creates orders with lineItems {string} and {string} with order type {string} and priorities {string}")
    public void userCreatesOrdersWithLineItemsAndPriorities(
            String items,
            String quantities,
            String orderType,
            String priorities) throws InterruptedException {

        ordersPage.createOrdersFromBatch(
                items,
                quantities,
                orderType,
                priorities
        );
    }

    @Then("user creates batch orders with lineItems {string} and {string} with order type {string} and priorities {string}")
    public void userCreatesBatchOrders(
            String items,
            String quantities,
            String orderType,
            String priorities) throws InterruptedException {

        ordersPage.createBatchOrders(
                items,
                quantities,
                orderType,
                priorities
        );
    }
}
