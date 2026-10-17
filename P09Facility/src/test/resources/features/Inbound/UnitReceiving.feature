Feature: Unit Receiving

  Scenario Outline: Unit Receiving Scenario
    Given user logged into P09 Facility
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<shippedQty>"
    And user completes "ReceiveByUnit"
    Examples:
      | shippedQty |
      | 10,5        |
