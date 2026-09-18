Feature: Vendor Specific Receiving and Putaway
  Scenario Outline: Vendor Specific Palletization Process
    Given user logged in
    And user completes "VendorSpecificReceiving" with ASN "<asn>" Item "<item>" Units "<units>"
    And user completes "Putaway"

    Examples:
      | asn         | item         | units |
      | ASN000000027892   | 19005       | 5    |
    |ASN000000027886                   |19005,19003        5     | 5,5      |