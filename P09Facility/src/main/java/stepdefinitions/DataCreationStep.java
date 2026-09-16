package stepdefinitions;

import Pages.AsnPage;
import io.cucumber.java.en.Then;
import model.InboundData;
import model.InboundItem;
import utils.ExcelReader;

import java.util.List;

public class DataCreationStep {
    @Then("user creates ILpns and perform putaway")
    public void userCreatesIlpnsAndPerformPutaway() {

        String filePath =
                "src/test/resources/testdata/IlpnInventoryData.xlsx";

        List<InboundData> dataList =
                ExcelReader.readInboundData(filePath);

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

    @Then("user creates ILPN inventory records")
    public void userCreatesIlpnInventoryRecords() throws Exception {

        String filePath =
                "src/test/resources/testdata/IlpnInventoryData.xlsx";

        AsnPage asnPage = new AsnPage();

        List<InboundData> inboundDataList =
                ExcelReader.readInboundData(filePath);

        asnPage.createIlpnAndInventory(
                inboundDataList
        );
    }
}
