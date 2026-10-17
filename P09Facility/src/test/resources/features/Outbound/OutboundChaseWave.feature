Feature: Outbound Picking with Chase Wave

  Scenario Outline: Dynamic Order Picking with Chase Wave
    Given user logged into P09 Facility
    When user navigates to "Orders" pages
    Then user creates batch orders with lineItems "<items>" and "<quantities>" with order type "<orderType>" and priorities "<orderPriority>"
    And user assigns pipeline to all created orders and runs the initial wave
    And user verifies all created orders are Allocated
    And user processes the allocated batch wave through tasks

    Examples:
      | items | quantities | orderType | orderPriority |
      | 19005 | 5          | 190       | 5,10          |