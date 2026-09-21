package stepdefinitions;

import Pages.OrdersPage;
import io.cucumber.java.en.When;

public class CommonOperations {
    @When("user navigates to {string} ui")
    public void navigateToUi(String uiName) throws InterruptedException {
        if (uiName.equalsIgnoreCase("Orders")){
            OrdersPage ordersPage = new OrdersPage();
            ordersPage.navigateToAnyUi(uiName);
        }

    }
}
