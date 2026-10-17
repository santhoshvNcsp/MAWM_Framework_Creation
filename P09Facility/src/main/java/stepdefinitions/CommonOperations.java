package stepdefinitions;

import Pages.ILpnPage;
import Pages.OrdersPage;
import Pages.WavesPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonOperations {
    @Then("user navigates to {string} ui")
//    @When("user navigates to {string} ui")
    public void navigateToUi(String uiName) throws InterruptedException {
        if (uiName.equalsIgnoreCase("Orders")){
            OrdersPage ordersPage = new OrdersPage();
            ordersPage.navigateToAnyUi(uiName);
        } else if (uiName.equalsIgnoreCase("Wave")) {
            WavesPage wavesPage = new WavesPage();
            wavesPage.navigateToWaveRuns();
        } else if (uiName.equalsIgnoreCase("ILPNs")) {
            ILpnPage iLpnPage = new ILpnPage();
            iLpnPage.navigateToILpns();
        }

    }
}
