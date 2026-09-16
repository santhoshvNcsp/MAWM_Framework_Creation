package stepdefinitions;

import Pages.AsnPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AsnCreation {

    //    @When("user navigates to {string} page")
    public void navigator(String word) throws InterruptedException {
        AsnPage asnPage = new AsnPage();
        asnPage.navigateToUi(word);

    }

    //    @Then("user generates the ASN with {string} lineItems")
//    public void generateAsn(String count) throws InterruptedException {
//        AsnPage asnPage = new AsnPage();
//        asnPage.provideAsnDetails(count);
//    }
    @And("user adds the ASN details")
    public void addAsnDetails() throws InterruptedException {
        AsnPage asnPage = new AsnPage();

    }
}
