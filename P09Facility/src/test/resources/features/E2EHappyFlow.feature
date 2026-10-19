
Feature: E2E Process Flow

  Scenario Outline: E2E HappyFlow
    Given user logged into P09 Facility
    When user navigates to "ASNs" page
    Then user creates 1 asn in "In Transit" status with 1 lineItems with "<quantities>"
    #user creates asn ith 1 line item and validates the status as  "In Transit"
    And user completes "Receiving"
    And user completes "E2E-UD-Putaway"
    When user navigates to "Orders" pages
    Then user creates 1 order with lineItems "<items>" and "<quantities>" with order type "<orderType>" and priority "<orderPriority>"
    When user navigates to "Wave" ui
    Then user runs the E2E wave and completes full-LPN picking
    Then user ship confirms the order

    Examples:
      | quantities | items | orderType | orderPriority |
      | 5         | 19005 | 190       | 5             |

