Feature: Mixed ILPN Receiving and Putaway

  Scenario Outline: Mixed ILPN Receiving and Putaway
    Given user logged into P09 Facility
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 2 lineItems with "<shippedQty>"
    And user completes "Receive-Mixed"
#    And user completes "UD-Putaway"
    Examples:
      | shippedQty |
      | 5,5        |
