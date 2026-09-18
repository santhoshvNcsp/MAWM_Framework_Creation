package stepdefinitions;

import Pages.ItemDetails;
import Pages.MUPPage;
import com.p09.framework.context.ScenarioContext;
import io.cucumber.java.en.And;

import java.util.ArrayList;
import java.util.List;

public class WMOperations {
    MUPPage mupPage = new MUPPage();

    @And("user completes {string} with ASN {string} Item {string} Units {string}")
    public void userCompletesWithAsnItemUnits(
            String transaction,
            String asn,
            String item,
            String units) throws InterruptedException {

        if (transaction.equalsIgnoreCase("VendorSpecificReceiving")) {

            mupPage.vendorSpecificReceiving(
                    asn,
                    item,
                    units
            );

            return;
        }

        throw new IllegalArgumentException(
                "Unsupported transaction for ASN/Item/Units: "
                        + transaction
        );
    }

    @And("user completes {string}")
    public void InboundOperations(String process) throws InterruptedException {
        switch (process) {
            case "LPNReceiving":
            case "Receiving":
                System.out.println("Entered 190 Receiving");
                mupPage.userCompletes(process);
                break;
            case "UD-Putaway":
                System.out.println("Entered 190 UD Putaway");
                mupPage.putaway();
                break;
            case "ReceiveByPallet":
                System.out.println("Entered for Receive With Pallet");
                mupPage.userCompletes(process);
                break;
            case "Receive-Mixed":
                System.out.println("Entered 190 Receiving for mixed lpn receive");
                mupPage.userCompletes(process);
                break;
            case "SD-Putaway":
                System.out.println("Entered 190 System Directed Putaway");
                mupPage.systemDirectedPutaway();
                break;
            case "ReceiveByUnit":
                System.out.println("Entered 190 Receiving for Unit Receive");
                mupPage.userCompletes(process);
                break;
            case "PalletPutaway":
                System.out.println("Entered 190 Pallet Putaway");
                mupPage.palletPutaway();
                break;
            case "PalletizeILPN":
                System.out.println("Entered Palletize ILPN transaction");
                mupPage.palletizeILPN();
                break;
            case "VendorSpecificReceiving":
                System.out.println("Entered Vendor Specific Receiving");
                mupPage.vendorSpecificReceiving();
                break;
            case "Putaway":
                System.out.println("Entered Putaway - determining putaway type");
                mupPage.performPutaway();
                break;


            default:
                System.out.println("Unknown Screen");
                break;
        }
    }

}
