Feature: Inbound Process Using Item Level ASN
  Scenario Outline: Inbound Process Using Item Level ASN
    Given user logged into P09 Facility
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<shippedQty>"
    And user completes "Receiving"
    And user completes "PalletizeILPN"
    And user completes "PalletPutaway"
    Examples:
      | shippedQty |
      | 5,5        |
