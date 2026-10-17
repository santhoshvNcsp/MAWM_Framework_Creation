
package stepdefinitions;

import Pages.OrdersPage;
import Pages.WavesPage;
import com.p09.framework.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class WaveOperations {

    private final WavesPage wavesPage = new WavesPage();
    private final OrdersPage ordersPage = new OrdersPage();

    @Then("user runs the wave and completes picking")
    public void userProcessesTheWave() throws InterruptedException {
        wavesPage.processWave();
    }

    // Dedicated E2E flow: full-LPN picking without short picking.
    @Then("user runs the E2E wave and completes full-LPN picking")
    public void userProcessesE2EWave() throws InterruptedException {
        wavesPage.processE2EWave();
    }

    @And("user performs picking with the required quantity adjustment")
    public void userPerformsPickingWithRequiredQuantityAdjustment()
            throws InterruptedException {

        String priorityValue =
                ScenarioContext.getString("Created Order Priority");

        String waveNumber =
                ScenarioContext.getString("wave nbr");

        if (priorityValue == null || priorityValue.isBlank()) {
            throw new IllegalStateException(
                    "Created Order Priority was not available in ScenarioContext");
        }

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalStateException(
                    "Original Wave Number was not available in ScenarioContext");
        }

        int priority = parsePriority(priorityValue);

        if (priority >= 10) {
            wavesPage.processPriorityTenPlusInitialPick(waveNumber);

            wavesPage.verifyOlpnStatusAndCaptureScreenshot(
                    waveNumber,
                    "Picking",
                    "Original Wave oLPN status after the initial partial pick.");
        } else {
            wavesPage.processPriorityBelowTenWorkflow(waveNumber);
        }
    }

    @Then("user completes the outbound picking workflow for priority {string}")
    public void userCompletesOutboundPickingWorkflowForPriority(
            String priorityFromFeature) throws InterruptedException {

        String priorityValue =
                ScenarioContext.getString("Created Order Priority");

        String orderId =
                ScenarioContext.getString("Created Order");

        String originalWave =
                ScenarioContext.getString("wave nbr");

        if (priorityValue == null || priorityValue.isBlank()) {
            priorityValue = priorityFromFeature;
        }

        if (priorityValue == null || priorityValue.isBlank()) {
            throw new IllegalStateException(
                    "Order priority was not available in ScenarioContext or feature step");
        }

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalStateException(
                    "Created Order was not available in ScenarioContext");
        }

        if (originalWave == null || originalWave.isBlank()) {
            throw new IllegalStateException(
                    "Original Wave Number was not available in ScenarioContext");
        }

        int priority = parsePriority(priorityValue);

        if (priority < 10) {
            ordersPage.navigateToAnyUi("Orders");
            ordersPage.verifyOrderStatus(orderId, "Packed");

            System.out.println(
                    "Priority below 10 outbound workflow completed. Order: "
                            + orderId);
            return;
        }

        ordersPage.navigateToAnyUi("Orders");
        ordersPage.verifyOrderStatus(orderId, "Released");
        ordersPage.captureNewOrderLineAndScreenshot(orderId);

        wavesPage.runChaseFromOriginalWave(originalWave);

        ordersPage.navigateToAnyUi("Orders");
        ordersPage.captureChaseWaveNumberFromNewOrderLine(
                orderId,
                originalWave);

        String chaseWave =
                ScenarioContext.getString("Chase Wave Number");

        if (chaseWave == null || chaseWave.isBlank()) {
            throw new IllegalStateException(
                    "Chase Wave Number was not captured from the new Order Line");
        }

        System.out.println("========== CHASE WAVE DEBUG ==========");
        System.out.println("Original Wave Number: " + originalWave);
        System.out.println("Chase Wave Number: " + chaseWave);
        System.out.println("Starting Chase Wave processing: " + chaseWave);

        wavesPage.processChaseWave(chaseWave);

        System.out.println("Finished Chase Wave processing: " + chaseWave);
        System.out.println(
                "Calling oLPN verification with wave number: " + originalWave);

        wavesPage.verifyOlpnStatusAndCaptureScreenshot(
                originalWave,
                "Picked",
                "Original Wave oLPN status after Chase Wave completion.");

        System.out.println("========== END CHASE WAVE DEBUG ==========");

        ordersPage.navigateToAnyUi("Orders");
        ordersPage.verifyOrderStatus(orderId, "Allocated");
    }

    @And("user processes the allocated batch wave through tasks")
    public void userProcessesAllocatedBatchWaveThroughTasks()
            throws InterruptedException {

        String initialWaveNumber =
                ScenarioContext.getString("Initial Batch Wave Number");

        if (initialWaveNumber == null || initialWaveNumber.isBlank()) {
            throw new IllegalStateException(
                    "Initial Batch Wave Number was not available in ScenarioContext");
        }

        wavesPage.processBatchAfterAllocation();
    }

    private int parsePriority(String priorityValue) {
        try {
            return Integer.parseInt(priorityValue.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "Invalid order priority: " + priorityValue,
                    e);
        }
    }
}
