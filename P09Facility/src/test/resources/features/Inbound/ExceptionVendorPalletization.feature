Feature: Vendor Specific Palletization

  Scenario Outline: Vendor Specific Palletization Process
    Given user logged in
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<shippedQty>" for vendor "<vendorName>"
    And user completes "VendorSpecificReceiving"
    And user completes "Putaway"
    Examples:
      | shippedQty | vendorName |
      | 5          | NIKE       |
