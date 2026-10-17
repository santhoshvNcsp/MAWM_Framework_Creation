Feature: Testing Outbound Order Functionality in P09 Environment

  Scenario Outline: Dynamic Order Auto Pack HappyPath
    Given user logged into P09 Facility
    When user navigates to "Orders" pages
    Then user creates 1 order with lineItems "<items>" and "<quantities>" with order type "<orderType>" and priority "<orderPriority>"
    When user navigates to "Wave" ui
    Then user runs the wave and completes picking
    Then user ship confirms the order
    Examples:
    |items|quantities|orderType|orderPriority|
    |19005|5|190|5|