package stepdefinitions;

import Pages.AsnPage;
import Pages.ILpnPage;
import com.p09.framework.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.InboundData;
import model.InboundItem;
import utils.ExcelReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCreationStep {

    private static final String FILE_PATH =
            "src/test/resources/testdata/IlpnInventoryData.xlsx";

    @Then("user creates ILpns and perform putaway")
    public void userCreatesIlpnsAndPerformPutaway() {

        List<InboundData> dataList =
                ExcelReader.readInboundData(FILE_PATH);

        for (InboundData data : dataList) {

            System.out.println("ILPN: " + data.getIlpn());
            System.out.println("Location: " + data.getLocation());

            for (InboundItem item : data.getItems()) {

                System.out.println(
                        "Item: " + item.getItem()
                                + " | Quantity: " + item.getQuantity()
                );
            }

            System.out.println("-------------------------");
        }
    }

    @When("user creates ILpn inventory records using API")
    public void userCreatesIlpnInventoryRecords() throws Exception {
        AsnPage asnPage = new AsnPage();
        List<InboundData> inboundDataList =
                ExcelReader.readInboundData(FILE_PATH);
        asnPage.createIlpnAndInventory(inboundDataList);
    }
    @Then("user verifies all created ILPNs and their locations in the UI")
    public void userVerifiesAllCreatedILPNsAndTheirLocationsInTheUI()
            throws InterruptedException {
        List<InboundData> inboundDataList =
                ExcelReader.readInboundData(FILE_PATH);
        if (inboundDataList == null || inboundDataList.isEmpty()) {
            throw new IllegalStateException(
                    "No ILPN data found in Excel for UI verification."
            );
        }
        List<String> ilpns = new ArrayList<>();
        for (InboundData data : inboundDataList) {
            if (data.getIlpn() == null || data.getIlpn().isBlank()) {
                throw new IllegalStateException(
                        "Excel contains an empty ILPN ID."
                );
            }

            ilpns.add(data.getIlpn().trim());
        }
        @SuppressWarnings("unchecked")
        Map<String, String> expectedLocationMap =
                (Map<String, String>) ScenarioContext.get("ILPN_LOCATION_MAP");
        if (expectedLocationMap == null || expectedLocationMap.isEmpty()) {
            throw new IllegalStateException(
                    "ILPN_LOCATION_MAP was not found in ScenarioContext. "
                            + "Ensure the API creation step completed successfully."
            );
        }
        ILpnPage ilpnPage = new ILpnPage();
        ilpnPage.filterAndVerifyCreatedILPNs(expectedLocationMap);
    }
}