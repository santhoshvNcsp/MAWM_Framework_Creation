Feature: Inbound Flow Using Lpn Level Asn
  Scenario Outline: Lpn Level Asn Creation and Processing
    Given user logged into P09 Facility
    Then user creates 1 lpnLvlAsn in "In Transit" status with 1 lineItems with "<shippedQty>"
    And user completes "LPNReceiving"
    And user completes "PalletizeILPN"
    And user completes "SD-Putaway"
    Examples:
      |shippedQty|
      |10       |