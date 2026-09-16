Feature: Testing all Inbound Functionality
  Scenario Outline: Testing from Asn Creation to Putaway to Storage
    Given user logged in
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<shippedQty>"
    And user completes "Receive-Mixed"
    Examples:
    |shippedQty|
    |5,5       |