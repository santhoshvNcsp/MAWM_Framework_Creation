
package Pages;

import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ILpnPage extends BasePage {

    // =========================================================
    // NAVIGATION LOCATORS
    // =========================================================

    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;

    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;

    @FindBy(xpath = "//button[@data-component-id='ILPNs']")
    public WebElement clickILPNs;

    @FindBy(xpath = "//span[contains(text(),'Showing ')]")
    public WebElement showingTextInWavesPageForVerification;

    @FindBy(xpath = "//ion-input[@data-component-id='IlpnId']/label/div[2]/input")
    public WebElement filterILpn;


    // =========================================================
    // ILPN RESULT LOCATORS
    // =========================================================

    private static final By SHOWING_RECORDS =
            By.xpath("//span[contains(normalize-space(.),'Showing ')]");

    private static final By ILPN_RESULT_IDS =
            By.xpath("//span[@data-component-id='IlpnId']");

    private static final By CURRENT_LOCATION_ID =
            By.xpath("//span[@data-component-id='CurrentLocationId']");

    private static final By CURRENT_DISPLAY_LOCATION =
            By.xpath("//span[@data-component-id='CurrentDisplayLocation']");

    private static final By CURRENT_LOCATION_TYPE =
            By.xpath("//span[@data-component-id='CurrentLocationTypeId']");

    private static final By PREVIOUS_LOCATION_ID =
            By.xpath("//span[@data-component-id='PreviousLocationId']");

    private static final By PREVIOUS_DISPLAY_LOCATION =
            By.xpath("//span[@data-component-id='PreviousDisplayLocation']");


    // =========================================================
    // PAGINATION LOCATORS
    // =========================================================

    private static final By PREVIOUS_PAGE_BUTTON =
            By.xpath("//ion-button[@data-component-id='arrow-back']");

    private static final By NEXT_PAGE_BUTTON =
            By.xpath("//ion-button[@data-component-id='arrow-forward-outline']");


    // =========================================================
    // PAGINATION TEXT PATTERN
    // =========================================================

    private static final Pattern RECORDS_PATTERN =
            Pattern.compile(
                    "Showing\\s+(\\d+)\\s*-\\s*(\\d+)\\s+of\\s+(\\d+)\\s+Records",
                    Pattern.CASE_INSENSITIVE
            );


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ILpnPage() {
        super();
    }


    // =========================================================
    // NAVIGATE TO ILPNS UI
    // =========================================================

    public void navigateToILpns() throws InterruptedException {

        click(menuToggleButton);

        Thread.sleep(2000);

        click(searchBarInLandingPage);

        Thread.sleep(1000);

        type(searchBarInLandingPage, "ILPNs");

        Thread.sleep(2000);

        if (!isDisplayed(clickILPNs)) {
            throw new IllegalStateException(
                    "ILPNs menu option was not displayed."
            );
        }

        click(clickILPNs);

        if (!isDisplayed(showingTextInWavesPageForVerification)) {
            throw new IllegalStateException(
                    "ILPNs page did not load. " +
                            "The records footer was not displayed."
            );
        }

        if (!isDisplayed(filterILpn)) {
            throw new IllegalStateException(
                    "ILPN filter field was not displayed on the ILPNs page."
            );
        }

        System.out.println("Successfully navigated to ILPNs UI.");
    }


    // =========================================================
    // FILTER AND VERIFY ALL API-CREATED ILPNS
    // =========================================================

    /**
     * Filters all expected ILPNs in one comma-separated value,
     * verifies the displayed ILPNs and their expected locations,
     * and navigates through every page of the filtered results.
     *
     * @param expectedLocationMap Map of ILPN -> expected location
     */
    public void filterAndVerifyCreatedILPNs(
            Map<String, String> expectedLocationMap
    ) throws InterruptedException {

        if (expectedLocationMap == null
                || expectedLocationMap.isEmpty()) {

            throw new IllegalStateException(
                    "Expected ILPN/location map is null or empty. " +
                            "Check that API creation stored the expected values."
            );
        }

        validateExpectedData(expectedLocationMap);

        List<String> expectedIlpns =
                new ArrayList<>(expectedLocationMap.keySet());

        String filterValue = String.join(",", expectedIlpns);

        System.out.println("ILPNs to filter: " + filterValue);

        WebDriverWait localWait =
                new WebDriverWait(driver, Duration.ofSeconds(60));

        localWait.ignoring(StaleElementReferenceException.class);

        // Wait for the filter field.
        localWait.until(
                ExpectedConditions.visibilityOf(filterILpn)
        );

        // Enter the comma-separated ILPN list.
        click(filterILpn);

        filterILpn.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        filterILpn.sendKeys(Keys.BACK_SPACE);

        type(filterILpn, filterValue);

        pressEnter(filterILpn);

        // Wait for the filtered results to load.
        localWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SHOWING_RECORDS
                )
        );

        // Framework reporting: no screenshot.
        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Filtered ILPNs successfully: " + expectedIlpns.size()
        );


        // =====================================================
        // VERIFY FILTERED RESULT COUNT
        // =====================================================

        int expectedTotal = expectedIlpns.size();

        localWait.until(webDriver -> {
            try {
                String footerText =
                        webDriver.findElement(SHOWING_RECORDS)
                                .getText()
                                .trim();

                Integer total = getTotalRecordCount(footerText);

                return total != null && total == expectedTotal;

            } catch (StaleElementReferenceException
                     | NoSuchElementException e) {

                return false;
            }
        });
        Set<String> verifiedIlpns = new LinkedHashSet<>();

        while (true) {
            String footerText = localWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            SHOWING_RECORDS
                    )
            ).getText().trim();

            RecordsPageInfo pageInfo =
                    parseRecordsPageInfo(footerText);


            // Confirm the filtered result count.
            if (pageInfo.totalRecords != expectedTotal) {

                throw new AssertionError(
                        "Filtered result count mismatch. Expected " +
                                expectedTotal + " records, but UI shows " +
                                pageInfo.totalRecords +
                                ". Footer: " + footerText
                );
            }


            // Get the ILPN IDs displayed on the current page.
            List<WebElement> visibleIlpnElements =
                    localWait.until(
                            ExpectedConditions.presenceOfAllElementsLocatedBy(
                                    ILPN_RESULT_IDS
                            )
                    );

            if (visibleIlpnElements.isEmpty()) {

                throw new AssertionError(
                        "No ILPN result rows were displayed."
                );
            }


            // =================================================
            // VERIFY EACH VISIBLE ILPN
            // =================================================

            for (int row = 1;
                 row <= visibleIlpnElements.size();
                 row++) {

                String actualIlpn = getTextFromLocator(
                        localWait,
                        By.xpath(
                                "(//span[@data-component-id='IlpnId'])[" +
                                        row + "]"
                        )
                );

                if (actualIlpn.isBlank()) {

                    throw new AssertionError(
                            "Blank ILPN ID found in visible row " + row
                    );
                }

                if (!expectedLocationMap.containsKey(actualIlpn)) {

                    throw new AssertionError(
                            "Unexpected ILPN displayed in UI: " +
                                    actualIlpn
                    );
                }

                if (!verifiedIlpns.add(actualIlpn)) {

                    throw new AssertionError(
                            "Duplicate ILPN displayed while processing " +
                                    "pages: " + actualIlpn
                    );
                }

                String expectedLocation =
                        expectedLocationMap.get(actualIlpn);

                verifyLocationForRow(
                        localWait,
                        actualIlpn,
                        expectedLocation,
                        row
                );

                System.out.println(
                        "Verified ILPN: " + actualIlpn +
                                " | Expected location: " + expectedLocation
                );
            }


            // =================================================
            // CHECK WHETHER ANOTHER PAGE EXISTS
            // =================================================

            if (pageInfo.endRecord >= pageInfo.totalRecords) {

                System.out.println(
                        "Reached last ILPN page: " + footerText
                );

                break;
            }


            // =================================================
            // CLICK NEXT PAGE
            // =================================================

            String previousFooter = footerText;

            WebElement nextButton = localWait.until(
                    ExpectedConditions.elementToBeClickable(
                            NEXT_PAGE_BUTTON
                    )
            );

            String ariaDisabled =
                    nextButton.getAttribute("aria-disabled");

            if (!nextButton.isEnabled()
                    || "true".equalsIgnoreCase(ariaDisabled)) {

                throw new AssertionError(
                        "More ILPN records remain, but the Next Page " +
                                "button is disabled. Footer: " + footerText
                );
            }

            // Use the framework's click method.
            click(nextButton);


            // =================================================
            // WAIT FOR THE NEXT PAGE TO LOAD
            // =================================================

            localWait.until(webDriver -> {
                try {
                    String updatedFooter =
                            webDriver.findElement(SHOWING_RECORDS)
                                    .getText()
                                    .trim();

                    return !updatedFooter.equals(previousFooter);

                } catch (StaleElementReferenceException
                         | NoSuchElementException e) {

                    return false;
                }
            });

            String updatedFooter = localWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            SHOWING_RECORDS
                    )
            ).getText().trim();

            System.out.println(
                    "Navigated to next ILPN page: " + updatedFooter
            );
        }


        // =====================================================
        // FINAL COMPLETENESS CHECK
        // =====================================================

        if (verifiedIlpns.size() != expectedTotal) {

            Set<String> missingIlpns =
                    new LinkedHashSet<>(expectedIlpns);

            missingIlpns.removeAll(verifiedIlpns);

            throw new AssertionError(
                    "Not all expected ILPNs were verified. " +
                            "Missing ILPNs: " + missingIlpns
            );
        }

        System.out.println(
                "Successfully verified all " +
                        verifiedIlpns.size() +
                        " ILPNs and their locations."
        );

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Successfully verified all " +
                        verifiedIlpns.size() +
                        " ILPNs and their locations across all pages."
        );
    }
    private void validateExpectedData(
            Map<String, String> expectedLocationMap
    ) {

        Set<String> uniqueIlpns = new LinkedHashSet<>();

        for (Map.Entry<String, String> entry :
                expectedLocationMap.entrySet()) {

            String ilpn = entry.getKey();
            String location = entry.getValue();

            if (ilpn == null || ilpn.trim().isEmpty()) {

                throw new IllegalStateException(
                        "Expected ILPN list contains a blank ILPN."
                );
            }

            if (location == null || location.trim().isEmpty()) {

                throw new IllegalStateException(
                        "Expected location is blank for ILPN: " + ilpn
                );
            }

            if (!uniqueIlpns.add(ilpn)) {

                throw new IllegalStateException(
                        "Duplicate ILPN in expected data: " + ilpn
                );
            }
        }
    }


    // =========================================================
    // VERIFY LOCATION FOR ONE ILPN ROW
    // =========================================================

    /**
     * Expected location contains R or B -> validate current location.
     * Expected location contains A       -> validate previous location.
     */
    private void verifyLocationForRow(
            WebDriverWait localWait,
            String ilpn,
            String expectedLocation,
            int row
    ) {

        boolean validateCurrent =
                expectedLocation.contains("R")
                        || expectedLocation.contains("B")
                ||expectedLocation.contains("E");

        boolean validatePrevious =
                expectedLocation.contains("A");

        if (validateCurrent == validatePrevious) {

            throw new IllegalArgumentException(
                    "Could not determine current or previous location " +
                            "validation for ILPN " + ilpn +
                            " with expected location " + expectedLocation
            );
        }


        // =====================================================
        // CURRENT LOCATION VALIDATION
        // =====================================================

        if (validateCurrent) {

            String actualCurrentLocation = getTextFromLocator(
                    localWait,
                    By.xpath(
                            "(//span[@data-component-id='CurrentLocationId'])[" +
                                    row + "]"
                    )
            );

            String actualCurrentDisplayLocation = getTextFromLocator(
                    localWait,
                    By.xpath(
                            "(//span[@data-component-id='CurrentDisplayLocation'])[" +
                                    row + "]"
                    )
            );

            String actualCurrentLocationType = getTextFromLocator(
                    localWait,
                    By.xpath(
                            "(//span[@data-component-id='CurrentLocationTypeId'])[" +
                                    row + "]"
                    )
            );

            if (!expectedLocation.equalsIgnoreCase(
                    actualCurrentLocation)
                    || !expectedLocation.equalsIgnoreCase(
                    actualCurrentDisplayLocation)
                    || !"STORAGE".equalsIgnoreCase(
                    actualCurrentLocationType)) {

                throw new AssertionError(
                        "Current location validation failed for ILPN " +
                                ilpn +
                                ". Expected location: " + expectedLocation +
                                " | Current Location: " + actualCurrentLocation +
                                " | Current Display Location: " +
                                actualCurrentDisplayLocation +
                                " | Current Location Type: " +
                                actualCurrentLocationType
                );
            }

        } else {


            // =================================================
            // PREVIOUS LOCATION VALIDATION
            // =================================================

            String actualPreviousLocation = getTextFromLocator(
                    localWait,
                    By.xpath(
                            "(//span[@data-component-id='PreviousLocationId'])[" +
                                    row + "]"
                    )
            );

            String actualPreviousDisplayLocation = getTextFromLocator(
                    localWait,
                    By.xpath(
                            "(//span[@data-component-id='PreviousDisplayLocation'])[" +
                                    row + "]"
                    )
            );

            if (!expectedLocation.equalsIgnoreCase(
                    actualPreviousLocation)
                    || !expectedLocation.equalsIgnoreCase(
                    actualPreviousDisplayLocation)) {

                throw new AssertionError(
                        "Previous location validation failed for ILPN " +
                                ilpn +
                                ". Expected location: " + expectedLocation +
                                " | Previous Location: " +
                                actualPreviousLocation +
                                " | Previous Display Location: " +
                                actualPreviousDisplayLocation
                );
            }
        }
    }


    // =========================================================
    // TEXT AND FOOTER HELPERS
    // =========================================================

    private String getTextFromLocator(
            WebDriverWait localWait,
            By locator
    ) {

        return localWait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        ).getText().trim();
    }


    private Integer getTotalRecordCount(String footerText) {

        Matcher matcher =
                RECORDS_PATTERN.matcher(footerText);

        if (!matcher.find()) {
            return null;
        }

        return Integer.parseInt(matcher.group(3));
    }


    private RecordsPageInfo parseRecordsPageInfo(
            String footerText
    ) {

        Matcher matcher =
                RECORDS_PATTERN.matcher(footerText);

        if (!matcher.find()) {

            throw new IllegalStateException(
                    "Could not parse ILPN records footer: " +
                            footerText
            );
        }

        int startRecord =
                Integer.parseInt(matcher.group(1));

        int endRecord =
                Integer.parseInt(matcher.group(2));

        int totalRecords =
                Integer.parseInt(matcher.group(3));

        if (totalRecords > 0
                && (startRecord < 1
                || endRecord < startRecord
                || endRecord > totalRecords)) {

            throw new IllegalStateException(
                    "Unexpected record range in footer: " +
                            footerText
            );
        }

        return new RecordsPageInfo(
                startRecord,
                endRecord,
                totalRecords
        );
    }


    // =========================================================
    // PAGINATION DATA CLASS
    // =========================================================

    private static class RecordsPageInfo {

        private final int startRecord;
        private final int endRecord;
        private final int totalRecords;

        private RecordsPageInfo(
                int startRecord,
                int endRecord,
                int totalRecords
        ) {

            this.startRecord = startRecord;
            this.endRecord = endRecord;
            this.totalRecords = totalRecords;
        }
    }
}