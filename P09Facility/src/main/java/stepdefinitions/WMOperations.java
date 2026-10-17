package stepdefinitions;

import Pages.MUPPage;
import io.cucumber.java.en.And;

public class WMOperations {

    private final MUPPage mupPage = new MUPPage();

    @And("user completes {string} with ASN {string} Item {string} Units {string}")
    public void userCompletesWithAsnItemUnits(
            String transaction,
            String asn,
            String item,
            String units) throws InterruptedException {

        if (transaction.equalsIgnoreCase("VendorSpecificReceiving")) {
            mupPage.vendorSpecificReceiving(asn, item, units);
            return;
        }

        throw new IllegalArgumentException(
                "Unsupported transaction for ASN/Item/Units: " + transaction
        );
    }

    @And("user completes {string}")
    public void inboundOperations(String process) throws InterruptedException {

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
                System.out.println("Entered 190 Receiving for mixed LPN receive");
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

            case "PalletizeILPN":
                System.out.println("Starting palletization and parent-LPN validation");
                mupPage.palletizeILPN();
//                mupPage.performPutaway();
                System.out.println(
                        "Palletization and parent-LPN validation completed"
                );

                break;

            case "PalletPutaway":
                System.out.println(
                        "Starting pallet putaway and ILPN location validation"
                );
//                mupPage.palletPutaway();
                mupPage.performPutaway();
                System.out.println(
                        "Pallet putaway and ILPN location validation completed"
                );
                break;

            case "VendorSpecificReceiving":
                System.out.println("Entered Vendor Specific Receiving");
                mupPage.vendorSpecificReceiving();
                break;

            case "Putaway":
                System.out.println(
                        "Entered Putaway - determining putaway type"
                );
                mupPage.performPutaway();
                break;

            case "E2E-UD-Putaway":
                System.out.println(
                        "Entered E2E UD Putaway - performing putaway at fixed location"
                );
                mupPage.putawayAtFixedLocation("190E0012");
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown transaction: " + process
                );
        }
    }
}