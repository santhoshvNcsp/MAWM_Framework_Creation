Feature: Testing Outbound Order Functionality in P09 Environment

  Scenario Outline: Dynamic Order Auto Pack HappyPath
    Given user logged in
    When user navigates to "Orders" pages
    Then user creates 1 order with 2 lineItems with "<items>" and "<quantities>" type "<orderType>" priority "<orderPriority>"
    When user navigates to "Wave" ui
    Then user processes the wave

    Examples:
      | items       | quantities | orderType | orderPriority |
      | 19005,19003 | 5,5        | 190       | 5             |