Feature: Testing Existing Order Wave Processing
  Scenario Outline: Existing Order Wave Processing
    Given user logged into P09 Facility
    When user navigates to "Orders" page
    Then user processes existing order "<orderId>"
    Examples:
      | orderId |
      | 123456  |