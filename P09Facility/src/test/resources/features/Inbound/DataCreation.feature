Feature: ILPN Inventory Data Creation and UI Validation
  Scenario: Create ILpn inventory records
    Given user logged into P09 Facility
    When user creates ILpn inventory records using API
    Then user navigates to "ILPNs" ui
    And user verifies all created ILPNs and their locations in the UI



