Feature: Testing the Lpn Level Asn Creation Functionality
  Scenario Outline: Test the Lpn Level Asn Creation Process
    Given user logged in
#    When user navigates to "ASNs" page
    Then user creates 1 lpnLvlAsn in "In Transit" status with 1 lineItems with "<shippedQty>"
    And user completes "LPNReceiving"
#    And user completes "SD-Putaway"
    And user completes "PalletizeILPN"
    And user completes "PalletPutaway"
    Examples:
    |shippedQty|
    |10       |