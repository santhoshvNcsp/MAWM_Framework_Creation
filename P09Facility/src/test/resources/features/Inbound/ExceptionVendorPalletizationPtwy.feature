Feature: Vendor Specific Receiving and Putaway
  # Receiving and putaway
  Scenario Outline: Vendor Specific Receiving and putaway
    Given user logged into P09 Facility
    And user completes "VendorSpecificReceiving" with ASN "<asn>" Item "<item>" Units "<units>"
    And user completes "Putaway"

    Examples:
      | asn         | item         | units |
      | ASN000000027960   | 19005       | 5    |
    |ASN000000027961                   |19005             | 5     |