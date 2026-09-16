Feature: Testing the Inbound Functionality in P09 Environment

  Scenario Outline: Testing From Asn Creation to Putaway
    Given user logged in
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<shippedQty>"
#    And user completes "Receive-Mixed"
#    And user completes "ReceiveByPallet"
    And user completes "Receiving"
    And user completes "PalletizeILPN"
#    And user completes "PalletPutaway"
#    And user completes "PalletPutaway"
#    And user completes "PalletizeILPN"
#    And user completes "UD-Putaway"
#    And user completes "ReceiveByUnit"


#    When user completes "UD-Putaway"

#    And user completes "Receiving" with "1" ILPN
    # user completes Receiving
#    Then user validates ILPN current Location
    Examples:
      | shippedQty |
      | 5,5        |


# There is no need to mention numbers as inside quotes
# so we can simply use 1 and in calling {int}



  # Planned for the change
  # user creates 1 itemLvlAsn in In Transit status with 1 LineItems with shippedQty
