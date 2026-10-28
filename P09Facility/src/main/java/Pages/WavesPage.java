
package Pages;
import com.p09.framework.context.ScenarioContext;
import com.p09.framework.driver.DriverManager;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;

import context.OrderData;
import context.OrderLineData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.interactions.Actions;

public class WavesPage extends BasePage {
    private final By transactionIdOnTaskPage =
            By.xpath("//span[@data-component-id='TransactionId']");

    // =========================================================
    // WAVE RUNS NAVIGATION
    // =========================================================

    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;

    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;

    @FindBy(xpath = "//button[@data-component-id='WaveRuns']")
    public WebElement clickWaveRuns;

    @FindBy(xpath = "//span[contains(text(),'Showing ')]")
    public WebElement showingTextInWavesPageForVerification;

    @FindBy(linkText = "Orders")
    public WebElement redirectToOrdersFromOrderLine;

    // =========================================================
    // WAVE FILTER
    // =========================================================

    @FindBy(xpath = "//ion-input[@data-component-id='OrderPlanningRunId-lookup-dialog-filter-input']/label/div[2]/input")
    public WebElement waveRunId;

    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;


    // =========================================================
    // WAVE
    // =========================================================

    @FindBy(xpath = "//span[@data-component-id='OrderPlanningRunId']")
    public WebElement selectTheWave;


    // =========================================================
    // RELATED LINKS
    // =========================================================

    @FindBy(xpath = "//button[@data-component-id='relatedLinks']")
    public WebElement clickRelatedLinks;

    @FindBy(linkText = "oLPNs")
    public WebElement oLpnSFromWave;

    @FindBy(linkText = "Allocations")
    public WebElement allocationSFromWave;

    @FindBy(linkText = "Tasks")
    public WebElement taskSFromWave;

    @FindBy(linkText = "Work Release Allocations")
    public WebElement workReleaseAllocationsFromWave;

    @FindBy(xpath = "//button[@data-component-id='selectAllRows']")
    public WebElement selectAllRows;

    @FindBy(linkText = "Pending Work Release Allocations Inquiry")
    public WebElement pendingWorkReleaseAllocationsInquiry;

    @FindBy(xpath = "//span[text()='More']")
    public WebElement more;

    @FindBy(xpath = "//span[text()='Generate Task']")
    public WebElement generateTask;

    @FindBy(xpath = "//button[@data-component-id='Yes']")
    public WebElement yesButton;

    // =========================================================
    // CHASE WAVE
    // =========================================================

    @FindBy(xpath = "//div[text()=' More ']")
    public WebElement moreForChase;

    @FindBy(xpath = "//span[contains(text(),'Run Chase')]")
    public WebElement runChase;

    @FindBy(xpath = "//ion-input[@data-component-id='PlanningStrategyId']/label/div[2]/input")
    public WebElement chasePlanningStrategy;

    @FindBy(xpath = "//ion-searchbar[@data-component-id='searchbar']/div/input")
    public WebElement chasePlanningStrategySearch;

    @FindBy(xpath = "//p[text()=' 190 order planning strategy ']")
    public WebElement select190ChasePlanningStrategy;

    @FindBy(xpath = "//ion-button[@data-component-id='submit-btn']")
    public WebElement submitChaseButton;
    @FindBy(xpath = "//div[text()=' More ']")
    public WebElement moreAllocations;

    @FindBy(xpath = "//span[@data-component-id='StatusDescription']")
    public List<WebElement> workReleaseStatusDescriptions;


    // =========================================================
    // RETURN TO WAVE RUNS
    // =========================================================

    @FindBy(linkText = "Wave Runs")
    public WebElement redirectToWaveRuns;


    // =========================================================
    // oLPN
    // =========================================================

    @FindBy(xpath = "//span[@data-component-id='OlpnId']")
    public List<WebElement> allOlpnNumbers;


    // =========================================================
    // TASK
    // =========================================================

    @FindBy(xpath = "//span[@data-component-id='TaskId']")
    public List<WebElement> allTaskNumbers;


    // =========================================================
    // WAVE STATUS
    // =========================================================

    public static final By waveStatus =
            By.xpath(
                    "//div[@data-component-id='PlanningStatusDescription']"
            );


    // =========================================================
    // NAVIGATE TO WAVE RUNS
    // =========================================================

    public void navigateToWaveRuns()
            throws InterruptedException {

        // =====================================================
        // OPEN MENU
        // =====================================================

        click(
                menuToggleButton
        );

        Thread.sleep(2000);


        // =====================================================
        // SEARCH WAVE RUNS
        // =====================================================

        click(
                searchBarInLandingPage
        );

        Thread.sleep(1000);

        type(
                searchBarInLandingPage,
                "Wave Runs"
        );

        Thread.sleep(2000);


        // =====================================================
        // SELECT WAVE RUNS
        // =====================================================

        if (!isDisplayed(
                clickWaveRuns
        )) {

            throw new IllegalStateException(
                    "Wave Runs option was not displayed"
            );
        }

        click(
                clickWaveRuns
        );

        Thread.sleep(3000);


        // =====================================================
        // VALIDATE WAVE RUNS PAGE
        // =====================================================

        if (!isDisplayed(
                showingTextInWavesPageForVerification
        )) {

            throw new IllegalStateException(
                    "Wave Runs page was not displayed"
            );
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "User entered Wave Runs page"
        );
    }
    public void filterWave(
            String waveNumber)
            throws InterruptedException {

        if (waveNumber == null
                || waveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        // =====================================================
        // ENTER WAVE NUMBER
        // =====================================================

        click(
                waveRunId
        );

        Thread.sleep(1000);
        type(
                waveRunId,
                waveNumber
        );
        Thread.sleep(2000);
        String enteredWaveNumber =
                waveRunId.getAttribute("value");

        System.out.println(
                "Entered Wave Number: "
                        + enteredWaveNumber
        );

        if (!waveNumber.equals(enteredWaveNumber)) {

            throw new IllegalStateException(
                    "Wave number was not entered correctly. "
                            + "Expected: "
                            + waveNumber
                            + " | Actual: "
                            + enteredWaveNumber
            );
        }


        // =====================================================
        // PRESS ENTER
        // =====================================================

        pressEnter(waveRunId);

        Thread.sleep(3000);


        // =====================================================
        // REFRESH
        // =====================================================

        click(
                refresh
        );

        Thread.sleep(3000);


        // =====================================================
        // VALIDATE WAVE
        // =====================================================

        if (!isDisplayed(
                selectTheWave
        )) {

            throw new IllegalStateException(
                    "Wave was not displayed after filtering. "
                            + "Wave Number: "
                            + waveNumber
            );
        }
    }


    // =========================================================
    // SELECT WAVE
    // =========================================================

    public void selectWave()
            throws InterruptedException {

        click(
                selectTheWave
        );

        Thread.sleep(2000);
    }


    // =========================================================
    // OPEN RELATED LINKS
    // =========================================================

    public void openRelatedLinks()
            throws InterruptedException {

        click(
                clickRelatedLinks
        );

        Thread.sleep(2000);
    }


    // =========================================================
    // NAVIGATE TO TASKS PAGE
    // =========================================================

    public void navigateToTasksPage(
            String waveNumber)
            throws InterruptedException {

        if (waveNumber == null
                || waveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        // =====================================================
        // FILTER WAVE
        // =====================================================

        filterWave(
                waveNumber
        );

        // =====================================================

        // SELECT WAVE
        // =====================================================

        selectWave();

        // =====================================================
        // OPEN RELATED LINKS
        // =====================================================

        openRelatedLinks();

        // =====================================================
        // OPEN TASKS
        // =====================================================

        click(
                taskSFromWave
        );

        Thread.sleep(3000);

        // =====================================================
        // WAIT FOR TASKS PAGE
        // =====================================================

        wait.until(
                ExpectedConditions.visibilityOf(
                        showingTextInWavesPageForVerification
                )
        );

        System.out.println(
                "Tasks page opened for Wave: "
                        + waveNumber
        );
    }


    // =========================================================
    // REFRESH TASKS PAGE
    // =========================================================

    public void refreshTasksPage()
            throws InterruptedException {

        click(
                refresh
        );

        Thread.sleep(3000);

        wait.until(
                ExpectedConditions.visibilityOf(
                        showingTextInWavesPageForVerification
                )
        );

        System.out.println(
                "Tasks page refreshed successfully."
        );
    }


    // =========================================================
    // PROCESS oLPN
    // =========================================================

    private void processOLPN(
            String waveNumber)
            throws InterruptedException {

        // Wave is already filtered and selected by processWave().
        // Do not filter the wave again here.

        click(
                oLpnSFromWave
        );

        Thread.sleep(3000);


        // =====================================================
        // WAIT FOR SHOWING TEXT
        // =====================================================

        String showingText =
                waitForShowingText();

        int totalRecords =
                getTotalRecordCount(showingText);

        System.out.println(
                "oLPN Showing Text: "
                        + showingText
        );

        System.out.println(
                "Total oLPN Records: "
                        + totalRecords
        );


        // =====================================================
        // GET ALL oLPN NUMBERS
        // =====================================================

        wait.until(driver ->
                allOlpnNumbers != null
                        && !allOlpnNumbers.isEmpty()
        );

        List<String> olpnNumbers =
                new ArrayList<>();

        for (WebElement olpnElement : allOlpnNumbers) {

            String olpnNumber =
                    getText(olpnElement);

            if (olpnNumber != null
                    && !olpnNumber.isBlank()) {

                olpnNumber = olpnNumber.trim();

                olpnNumbers.add(olpnNumber);

                System.out.println(
                        "oLPN Number: "
                                + olpnNumber
                );
            }
        }

        if (olpnNumbers.isEmpty()) {

            throw new IllegalStateException(
                    "No oLPN numbers were found for Wave: "
                            + waveNumber
            );
        }


        // =====================================================
        // STORE ALL oLPN NUMBERS
        // =====================================================

        ScenarioContext.set(
                "oLPN Numbers - " + waveNumber,
                olpnNumbers
        );

        // Keep first oLPN for existing framework usage
        ScenarioContext.set(
                "oLPN nbr",
                olpnNumbers.get(0)
        );

        ScenarioContext.set(
                "oLPN Number - " + waveNumber,
                olpnNumbers.get(0)
        );


        // =====================================================
        // BUILD oLPN REPORT MESSAGE
        // =====================================================

        StringBuilder olpnReport =
                new StringBuilder();

        olpnReport.append(
                "oLPNs captured for Wave: "
        ).append(waveNumber);

        for (int i = 0; i < olpnNumbers.size(); i++) {

            olpnReport
                    .append("\n")
                    .append("oLPN ")
                    .append(i + 1)
                    .append(": ")
                    .append(olpnNumbers.get(i));
        }

        System.out.println(
                "Total oLPNs captured: "
                        + olpnNumbers.size()
        );
        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                olpnReport.toString()
        );
    }


    // =========================================================
    // RETURN TO WAVE RUNS
    // =========================================================

    private void returnToWaveRuns()
            throws InterruptedException {

        // =====================================================
        // CLICK WAVE RUNS
        // =====================================================

        if (!isDisplayed(
                redirectToWaveRuns
        )) {

            throw new IllegalStateException(
                    "Wave Runs navigation link was not displayed"
            );
        }

        click(
                redirectToWaveRuns
        );

        Thread.sleep(3000);


        // =====================================================
        // VALIDATE WAVE RUNS PAGE
        // =====================================================

        if (!isDisplayed(
                showingTextInWavesPageForVerification
        )) {

            throw new IllegalStateException(
                    "Failed to return to Wave Runs page"
            );
        }
    }


    // =========================================================
    // PROCESS ALLOCATIONS
    // =========================================================

    private void processAllocations(
            String waveNumber)
            throws InterruptedException {

        // Wave is already filtered and selected by processWave().
        // Related Links is already open for the current Wave.

        click(
                allocationSFromWave
        );

        Thread.sleep(3000);


        // =====================================================
        // WAIT FOR SHOWING TEXT
        // =====================================================

        String showingText =
                waitForShowingText();

        int totalAllocations =
                getTotalRecordCount(showingText);

        System.out.println(
                "Allocation Showing Text: "
                        + showingText
        );

        System.out.println(
                "Total Allocation Records: "
                        + totalAllocations
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Allocations displayed"
                        + "\nTotal Allocations: "
                        + totalAllocations
        );
    }


    // =========================================================
    // PROCESS WORK RELEASE ALLOCATIONS / GENERATE TASKS
    // =========================================================

    private void processWorkReleaseAllocations(
            String waveNumber)
            throws InterruptedException {

        // =====================================================
        // OPEN WORK RELEASE ALLOCATIONS
        // =====================================================

        if (!isDisplayed(
                workReleaseAllocationsFromWave
        )) {

            throw new IllegalStateException(
                    "Work Release Allocations link was not displayed for Wave: "
                            + waveNumber
            );
        }

        click(
                workReleaseAllocationsFromWave
        );

        Thread.sleep(2000);

        // =====================================================
        // VALIDATE WORK RELEASE ALLOCATION STATUS
        // =====================================================

        String showingText =
                waitForShowingText();

        int displayedRecords =
                getDisplayedRecordCount(showingText);

        System.out.println(
                "Work Release Allocations Showing Text: "
                        + showingText
        );

        boolean pendingRecords =
                hasNonReleasedStatus(
                        displayedRecords,
                        "Work Release Allocations"
                );

        if (!pendingRecords) {

            System.out.println(
                    "All displayed Work Release Allocations are already Released. "
                            + "Skipping Generate Task."
            );

            report.addReportStepWithoutScreenshot(
                    StepStatus.PASS,
                    "All Work Release Allocations are already Released for Wave: "
                            + waveNumber
            );

            return;
        }

        // =====================================================
        // SELECT ALL ROWS
        // =====================================================

        if (!isDisplayed(
                selectAllRows
        )) {

            throw new IllegalStateException(
                    "Select All Rows button was not displayed in Work Release Allocations"
            );
        }

        click(
                selectAllRows
        );

        // =====================================================
        // OPEN RELATED LINKS
        // =====================================================

        click(
                clickRelatedLinks
        );

        Thread.sleep(1000);

        // =====================================================
        // OPEN PENDING WORK RELEASE ALLOCATIONS INQUIRY
        // =====================================================

        if (!isDisplayed(
                pendingWorkReleaseAllocationsInquiry
        )) {

            throw new IllegalStateException(
                    "Pending Work Release Allocations Inquiry link was not displayed"
            );
        }

        click(
                pendingWorkReleaseAllocationsInquiry
        );

        Thread.sleep(2000);

        // =====================================================
        // VALIDATE PENDING WORK RELEASE ALLOCATION STATUS
        // =====================================================

        showingText =
                waitForShowingText();

        displayedRecords =
                getDisplayedRecordCount(showingText);

        System.out.println(
                "Pending Work Release Allocations Inquiry Showing Text: "
                        + showingText
        );

        pendingRecords =
                hasNonReleasedStatus(
                        displayedRecords,
                        "Pending Work Release Allocations Inquiry"
                );

        if (!pendingRecords) {

            System.out.println(
                    "All displayed Pending Work Release Allocations are already Released. "
                            + "Skipping Generate Task."
            );

            report.addReportStepWithoutScreenshot(
                    StepStatus.PASS,
                    "All Pending Work Release Allocations are already Released for Wave: "
                            + waveNumber
            );

            return;
        }

        // =====================================================
        // SELECT ALL ROWS
        // =====================================================

        if (!isDisplayed(
                selectAllRows
        )) {

            throw new IllegalStateException(
                    "Select All Rows button was not displayed in Pending Work Release Allocations Inquiry"
            );
        }

        click(
                selectAllRows
        );

        // =====================================================
        // OPEN MORE
        // IMPORTANT: KEEP EXISTING moreAllocations XPATH
        // =====================================================

        if (!isDisplayed(
                moreAllocations
        )) {

            throw new IllegalStateException(
                    "More option was not displayed in Pending Work Release Allocations Inquiry"
            );
        }

        click(
                moreAllocations
        );

        Thread.sleep(1000);

        // =====================================================
        // GENERATE TASK
        // =====================================================

        if (!isDisplayed(
                generateTask
        )) {

            throw new IllegalStateException(
                    "Generate Task option was not displayed"
            );
        }

        click(
                generateTask
        );

        // =====================================================
        // CONFIRM GENERATE TASK
        // =====================================================

        if (!isDisplayed(
                yesButton
        )) {
            throw new IllegalStateException(
                    "Yes confirmation button was not displayed after Generate Task"
            );
        }
        click(
                yesButton
        );
    }


    // =========================================================
    // WAIT FOR WAVE STATUS
    // =========================================================

    private void waitForWaveCompletion() {

        String currentStatus =
                getText(
                        DriverManager.getDriver().findElement(waveStatus)
                );

        if (currentStatus == null
                || currentStatus.isBlank()) {

            throw new IllegalStateException(
                    "Wave status was empty after filtering"
            );
        }

        currentStatus = currentStatus.trim();

        System.out.println(
                "Current Wave Status: "
                        + currentStatus
        );

        // =====================================================
        // ALREADY COMPLETED
        // =====================================================

        if ("Completed".equalsIgnoreCase(
                currentStatus
        )) {

            System.out.println(
                    "Wave status is already Completed."
            );

            return;
        }

        // =====================================================
        // WAIT FOR COMPLETED
        // REFRESH WHEN STATUS IS NOT READY
        // =====================================================

        System.out.println(
                "Wave status is not Completed. "
                        + "Waiting for status to change..."
        );

        waitForStatus(
                waveStatus,
                () -> {
                    System.out.println(
                            "Wave is not Completed yet. Refreshing Wave Runs..."
                    );

                    click(
                            refresh
                    );
                },
                "Completed"
        );

        String finalStatus =
                getText(
                        DriverManager.getDriver().findElement(waveStatus)
                );

        System.out.println(
                "Final Wave Status: "
                        + finalStatus
        );
    }


    // =========================================================
    // PROCESS TASKS
    // =========================================================

    private void processTasks(
            String waveNumber)
            throws InterruptedException {

        // Wave is already filtered and selected by processWave().
        // After Generate Task, processWave() returns to Wave Runs, selects
        // the same wave and opens Related Links before calling this method.

        click(
                taskSFromWave
        );

        Thread.sleep(3000);


        // =====================================================
        // TEMPORARY TASK PAGE WAIT
        //
        // Tasks page sometimes does not load the "Showing"
        // element immediately.
        //
        // We check periodically.
        // If the element is not displayed, refresh the page
        // and check again.
        // =====================================================

        FluentWait<WebDriver> taskWait =
                new FluentWait<>(DriverManager.getDriver())
                        .withTimeout(Duration.ofSeconds(600))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(NoSuchElementException.class);

        taskWait.until(driver -> {

            try {

                // =================================================
                // CHECK WHETHER SHOWING ELEMENT IS AVAILABLE
                // =================================================

                if (showingTextInWavesPageForVerification.isDisplayed()) {

                    System.out.println(
                            "Tasks page loaded successfully."
                    );

                    return true;
                }

            } catch (NoSuchElementException e) {

                System.out.println(
                        "Tasks Showing element not found yet."
                );
            }


            // =====================================================
            // REFRESH TASK PAGE
            // =====================================================

            System.out.println(
                    "Tasks page not ready. Refreshing..."
            );

            click(
                    refresh
            );

            return false;
        });


        // =====================================================
        // WAIT FOR SHOWING TEXT
        // =====================================================

        String showingText =
                waitForShowingText();

        int totalTasks =
                getTotalRecordCount(showingText);

        System.out.println(
                "Task Showing Text: "
                        + showingText
        );

        System.out.println(
                "Total Task Records: "
                        + totalTasks
        );


        // =====================================================
        // GET ALL TASK NUMBERS
        // =====================================================

        wait.until(driver ->
                allTaskNumbers != null
                        && !allTaskNumbers.isEmpty()
        );

        List<String> taskNumbers =
                new ArrayList<>();

        for (WebElement taskElement : allTaskNumbers) {

            String taskNumber =
                    getText(taskElement);

            if (taskNumber != null
                    && !taskNumber.isBlank()) {

                taskNumber = taskNumber.trim();

                taskNumbers.add(taskNumber);

                System.out.println(
                        "Task ID: "
                                + taskNumber
                );
            }
        }

        if (taskNumbers.isEmpty()) {

            throw new IllegalStateException(
                    "No task numbers were found for Wave: "
                            + waveNumber
            );
        }


        // =====================================================
        // STORE ALL TASK NUMBERS
        // =====================================================

        ScenarioContext.set(
                "Task Numbers - " + waveNumber,
                taskNumbers
        );

        // Keep first task for existing framework usage
        ScenarioContext.set(
                "Task Number - " + waveNumber,
                taskNumbers.get(0)
        );


        // =====================================================
        // BUILD TASK REPORT MESSAGE
        // =====================================================

        StringBuilder taskReport =
                new StringBuilder();



        for (int i = 0; i < taskNumbers.size(); i++) {

            taskReport
                    .append("\n")
                    .append("Task ")
                    .append(i + 1)
                    .append(": ")
                    .append(taskNumbers.get(i));
        }

        System.out.println(
                "Total Tasks captured: "
                        + taskNumbers.size()
        );

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                taskReport.toString()
        );
    }


    // =========================================================
    // WORK RELEASE STATUS HELPERS
    // =========================================================

    private int getDisplayedRecordCount(
            String showingText) {

        Pattern pattern =
                Pattern.compile(
                        "Showing\\s+(\\d+)\\s*-\\s*(\\d+)\\s+of\\s+(\\d+)\\s+Record?",
                        Pattern.CASE_INSENSITIVE
                );

        Matcher matcher =
                pattern.matcher(showingText);

        if (matcher.find()) {

            int start =
                    Integer.parseInt(matcher.group(1));

            int end =
                    Integer.parseInt(matcher.group(2));

            return Math.max(0, end - start + 1);
        }

        // Fallback for any Showing format already supported by the framework.
        return getTotalRecordCount(showingText);
    }


    private boolean hasNonReleasedStatus(
            int displayedRecords,
            String pageName) {

        wait.until(driver ->
                workReleaseStatusDescriptions != null
                        && workReleaseStatusDescriptions.size() >= displayedRecords
        );

        int statusCount =
                Math.min(
                        displayedRecords,
                        workReleaseStatusDescriptions.size()
                );

        if (statusCount <= 0) {

            throw new IllegalStateException(
                    "No StatusDescription elements were found on "
                            + pageName
            );
        }

        boolean nonReleasedFound = false;

        for (int i = 0; i < statusCount; i++) {

            String status =
                    getText(
                            workReleaseStatusDescriptions.get(i)
                    );

            if (status == null) {
                status = "";
            }

            status = status.trim();

            System.out.println(
                    pageName
                            + " - Record "
                            + (i + 1)
                            + " Status: "
                            + status
            );

            if (!status.equalsIgnoreCase("Released")) {
                nonReleasedFound = true;
            }
        }

        return nonReleasedFound;
    }


    // =========================================================
    // SHOWING TEXT / RECORD COUNT HELPERS
    // =========================================================

    private String waitForShowingText() {

        wait.until(
                ExpectedConditions.visibilityOf(
                        showingTextInWavesPageForVerification
                )
        );

        String showingText =
                getText(
                        showingTextInWavesPageForVerification
                );

        if (showingText == null
                || showingText.isBlank()) {

            throw new IllegalStateException(
                    "Showing text was empty"
            );
        }

        return showingText.trim();
    }


    private int getTotalRecordCount(
            String showingText) {

        Pattern pattern =
                Pattern.compile(
                        "of\\s+(\\d+)\\s+Record?",
                        Pattern.CASE_INSENSITIVE
                );

        Matcher matcher =
                pattern.matcher(showingText);

        if (matcher.find()) {

            return Integer.parseInt(
                    matcher.group(1)
            );
        }

        throw new IllegalStateException(
                "Unable to extract total record count from: "
                        + showingText
        );
    }


    // =========================================================
    // FINAL oLPN SCREENSHOT AFTER ALL TASKS
    // =========================================================

    public void captureFinalOlpnScreenshot(
            String waveNumber)
            throws InterruptedException {

        if (waveNumber == null
                || waveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        System.out.println(
                "========================================"
        );

        System.out.println(
                "FINAL oLPN VALIDATION"
        );

        System.out.println(
                "Wave Number: "
                        + waveNumber
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // FILTER SAME WAVE
        // =====================================================

        filterWave(
                waveNumber
        );


        // =====================================================
        // SELECT WAVE
        // =====================================================

        selectWave();


        // =====================================================
        // OPEN RELATED LINKS
        // =====================================================

        openRelatedLinks();


        // =====================================================
        // OPEN oLPNs
        // =====================================================

        click(
                oLpnSFromWave
        );

        Thread.sleep(3000);


        // =====================================================
        // WAIT FOR oLPN PAGE
        // =====================================================

        String showingText =
                waitForShowingText();

        int totalOlpn =
                getTotalRecordCount(
                        showingText
                );


        if (totalOlpn <= 0) {

            throw new IllegalStateException(
                    "No oLPN records were displayed for Wave: "
                            + waveNumber
            );
        }


        // =====================================================
        // FINAL oLPN SCREENSHOT
        // =====================================================

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Final oLPN validation completed for Wave: "
                        + "\nTotal oLPNs: "
                        + totalOlpn
        );


        System.out.println(
                "Final oLPN screenshot captured successfully."
        );

        System.out.println(
                "Total oLPNs: "
                        + totalOlpn
        );
    }


    // =========================================================
    // PRIORITY >= 10 - INITIAL PARTIAL PICK PREPARATION
    // =========================================================

    public void processPriorityTenPlusInitialPick(
            String waveNumber)
            throws InterruptedException {

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        System.out.println("========================================");
        System.out.println("PRIORITY >= 10 INITIAL PICK");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println("========================================");

        // ---------------------------------------------------------
        // 1. FILTER WAVE / WAIT FOR COMPLETION
        // ---------------------------------------------------------

        navigateToWaveRuns();
        filterWave(waveNumber);
        waitForWaveCompletion();

        selectWave();
        openRelatedLinks();

        System.out.println(
                "Opening oLPNs for initial Wave: " + waveNumber
        );

        processOLPN(waveNumber);

// Return to Wave Runs using the existing breadcrumb/link.
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 2. ALLOCATIONS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processAllocations(waveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 3. WORK RELEASE ALLOCATIONS / GENERATE TASKS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(waveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 4. TASKS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processTasks(waveNumber);

        // ---------------------------------------------------------
        // 5. WM - PICK ONLY THE FIRST TASK WITH quantity - 1
        // ---------------------------------------------------------

        MUPPage mupPage = new MUPPage();
        mupPage.processFirstWaveTaskWithQuantityAdjustment(waveNumber);

        System.out.println(
                "Priority >= 10 initial partial pick completed for Wave: "
                        + waveNumber
        );
    }


    // =========================================================
    // RUN CHASE FROM ORIGINAL WAVE
    // =========================================================

    public void runChaseFromOriginalWave(
            String originalWaveNumber)
            throws InterruptedException {

        if (originalWaveNumber == null
                || originalWaveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Original wave number cannot be null or empty"
            );
        }

        System.out.println("========================================");
        System.out.println("RUNNING CHASE WAVE");
        System.out.println("Original Wave: " + originalWaveNumber);
        System.out.println("========================================");

        navigateToWaveRuns();
        filterWave(originalWaveNumber);
        selectWave();

        // ---------------------------------------------------------
        // OPEN MORE -> HOVER RUN CHASE
        // ---------------------------------------------------------

        if (!isDisplayed(moreForChase)) {
            throw new IllegalStateException(
                    "More option was not displayed for Wave: "
                            + originalWaveNumber
            );
        }

        click(moreForChase);
        Thread.sleep(1000);

        wait.until(
                ExpectedConditions.visibilityOf(runChase)
        );

        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(runChase).perform();
        Thread.sleep(1000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Run Chase option displayed for Wave: "
                        + originalWaveNumber
        );

        click(runChase);
        Thread.sleep(2000);

        // ---------------------------------------------------------
        // PLANNING STRATEGY
        // ---------------------------------------------------------

        wait.until(
                ExpectedConditions.visibilityOf(chasePlanningStrategy)
        );

        String strategyValue =
                getAttribute(
                        chasePlanningStrategy,
                        "value"
                );

        System.out.println(
                "Current Chase Planning Strategy: "
                        + strategyValue
        );

        if (strategyValue != null
                && strategyValue.contains("190")) {

            System.out.println(
                    "Planning Strategy already contains 190. "
                            + "Submitting directly."
            );

            click(submitChaseButton);

        } else {

            System.out.println(
                    "Planning Strategy does not contain 190. "
                            + "Searching and selecting 190."
            );

            click(chasePlanningStrategy);
            Thread.sleep(1000);

            type(
                    chasePlanningStrategySearch,
                    "190"
            );

            wait.until(
                    ExpectedConditions.visibilityOf(
                            select190ChasePlanningStrategy
                    )
            );

            click(select190ChasePlanningStrategy);
            Thread.sleep(500);
            click(submitChaseButton);
        }

        Thread.sleep(3000);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Chase Wave submitted for Original Wave: "
                        + originalWaveNumber
        );
    }


    // =========================================================
    // PROCESS CHASE WAVE
    // No oLPN processing is expected in the chase wave.
    // =========================================================

    public void processChaseWave(
            String chaseWaveNumber)
            throws InterruptedException {

        if (chaseWaveNumber == null
                || chaseWaveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Chase wave number cannot be null or empty"
            );
        }

        System.out.println("========================================");
        System.out.println("PROCESSING CHASE WAVE");
        System.out.println("Chase Wave: " + chaseWaveNumber);
        System.out.println("========================================");

        navigateToWaveRuns();
        filterWave(chaseWaveNumber);
        waitForWaveCompletion();

        // ---------------------------------------------------------
        // 1. ALLOCATIONS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processAllocations(chaseWaveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 2. WORK RELEASE ALLOCATIONS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(chaseWaveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 3. TASKS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processTasks(chaseWaveNumber);

        // ---------------------------------------------------------
        // 4. WM - FULL UNITS / OPTIONAL OK
        // ---------------------------------------------------------

        MUPPage mupPage = new MUPPage();

        mupPage.processWaveTasksInWmMobile(
                chaseWaveNumber,
                false,
                false,
                true,
                true,
                true
        );

        System.out.println(
                "Chase Wave processing completed: "
                        + chaseWaveNumber
        );
    }


    // =========================================================
    // VERIFY oLPN STATUS FOR A WAVE
    // =========================================================

    public void verifyOlpnStatusAndCaptureScreenshot(
            String waveNumber,
            String expectedStatus,
            String reportMessage)
            throws InterruptedException {

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty"
            );
        }

        if (expectedStatus == null || expectedStatus.isBlank()) {
            throw new IllegalArgumentException(
                    "Expected oLPN status cannot be null or empty"
            );
        }

        boolean returnedThroughBreadcrumb = false;

        if (isDisplayed(redirectToWaveRuns)) {

            System.out.println(
                    "Currently on a Wave Runs child page. "
                            + "Returning through breadcrumb."
            );

            click(redirectToWaveRuns);
            returnedThroughBreadcrumb = true;

            wait.until(
                    ExpectedConditions.visibilityOf(waveRunId)
            );

        } else if (!isDisplayed(waveRunId)) {

            System.out.println(
                    "Not on Wave Runs. Navigating through the menu."
            );

            navigateToWaveRuns();

            wait.until(
                    ExpectedConditions.visibilityOf(waveRunId)
            );
        }

        // The wave filter is retained when returning from Tasks via the
        // Wave Runs breadcrumb, so do not filter it a second time.
        System.out.println("========== OLPN VERIFICATION DEBUG ==========");
        System.out.println("Wave number passed to method: " + waveNumber);
        System.out.println("Returned through breadcrumb: " + returnedThroughBreadcrumb);
        System.out.println("Wave filter value before filtering: "
                + waveRunId.getAttribute("value"));
        if (!returnedThroughBreadcrumb) {
            filterWave(waveNumber);
        } else {
            System.out.println(
                    "Returned to Wave Runs with the previous filter retained. "
                            + "Skipping wave filtering."
            );
        }
        System.out.println(
                "Applying requested wave filter: " + waveNumber
        );

        filterWave(waveNumber);

        System.out.println(
                "Wave filter value after filtering: "
                        + waveRunId.getAttribute("value")
        );
        System.out.println("Wave filter value before selectWave(): "
                + waveRunId.getAttribute("value"));

        selectWave();
        openRelatedLinks();
        System.out.println("Opening oLPNs for requested wave: " + waveNumber);
        click(oLpnSFromWave);
        Thread.sleep(2500);

        waitForShowingText();

        FluentWait<WebDriver> statusWait =
                new FluentWait<>(DriverManager.getDriver())
                        .withTimeout(Duration.ofSeconds(120))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);

        statusWait.until(driver -> {

            try {
                wait.until(driver2 ->
                        allOlpnNumbers != null
                                && !allOlpnNumbers.isEmpty()
                );

                if (allOlpnNumbers.isEmpty()) {
                    return false;
                }

                boolean allMatched = true;

                for (WebElement olpnElement : allOlpnNumbers) {

                    String olpnNumber =
                            getText(olpnElement);

                    if (olpnNumber == null
                            || olpnNumber.isBlank()) {
                        continue;
                    }

                    if (!isOlpnStatusVisible(
                            olpnElement,
                            expectedStatus
                    )) {
                        allMatched = false;
                        break;
                    }
                }

                if (allMatched) {
                    return true;
                }

                // Status can be updated asynchronously on the inquiry page.
                click(refresh);
                return false;

            } catch (Exception e) {
                return false;
            }
        });

        StringBuilder message =
                new StringBuilder(
                        reportMessage == null || reportMessage.isBlank()
                                ? "oLPN status validated"
                                : reportMessage
                );

        message.append("\nWave: ").append(waveNumber);
        message.append("\nExpected oLPN Status: ")
                .append(expectedStatus);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                message.toString()
        );

        System.out.println(
                "Verified oLPN status '"
                        + expectedStatus
                        + "' for Wave: "
                        + waveNumber
        );
    }
    private boolean isOlpnStatusVisible(
            WebElement olpnElement,
            String expectedStatus) {

        WebElement current = olpnElement;

        for (int level = 0; level < 7; level++) {

            try {
                String cardText = getText(current);

                if (containsExactStatusLine(
                        cardText,
                        expectedStatus
                )) {
                    return true;
                }

                current = current.findElement(
                        By.xpath("./..")
                );

            } catch (Exception e) {
                break;
            }
        }

        return false;
    }


    private boolean containsExactStatusLine(
            String text,
            String expectedStatus) {

        if (text == null || text.isBlank()) {
            return false;
        }

        Pattern pattern =
                Pattern.compile(
                        "(?i)(?<![A-Z])"
                                + Pattern.quote(expectedStatus.trim())
                                + "(?![A-Z])"
                );

        Matcher matcher =
                pattern.matcher(text);

        return matcher.find();
    }


    // =========================================================
    // COMPLETE WAVE PROCESS
    // =========================================================


    public void processWave() throws InterruptedException {
        processWaveFlow(false);
    }

    public void processE2EWave() throws InterruptedException {
        processWaveFlow(true);
    }

    private void processWaveFlow(boolean e2eFullLpnPicking)
            throws InterruptedException {

        String waveNumber =
                ScenarioContext.getString("wave nbr");

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalStateException(
                    "Wave number was not available in ScenarioContext");
        }

        System.out.println("========================================");
        System.out.println(
                e2eFullLpnPicking
                        ? "PROCESSING E2E WAVE - FULL LPN PICK"
                        : "PROCESSING WAVE");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println("========================================");

        // 1. Filter wave and wait for completion.
        filterWave(waveNumber);
        waitForWaveCompletion();

        // 2. Process oLPNs.
        selectWave();
        openRelatedLinks();
        processOLPN(waveNumber);

        // 3. Process allocations.
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processAllocations(waveNumber);

        // 4. Generate tasks from work release allocations.
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(waveNumber);

        // 5. Open Tasks and capture task IDs.
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processTasks(waveNumber);

        // 6. Process tasks in WM Mobile.
        MUPPage mupPage = new MUPPage();

        if (e2eFullLpnPicking) {
            System.out.println(
                    "Starting E2E full-LPN picking for Wave: " + waveNumber);

            mupPage.processE2EWaveTasksInWmMobile(waveNumber);
        } else {
            System.out.println(
                    "Starting standard WM Mobile picking for Wave: " + waveNumber);

            mupPage.processWaveTasksInWmMobile(waveNumber);
        }

        // 7. Final oLPN verification.
        navigateToWaveRuns();
        captureFinalOlpnScreenshot(waveNumber);

        // 8. Verify the order is packed.
        String createdOrder =
                ScenarioContext.getString("Created Order");

        if (createdOrder == null || createdOrder.isBlank()) {
            throw new IllegalStateException(
                    "Created Order was not available in ScenarioContext");
        }

        OrdersPage ordersPage = new OrdersPage();
        ordersPage.navigateToAnyUi("Orders");
        ordersPage.verifyPackedOrderAndCaptureScreenshot(createdOrder);

        System.out.println("========================================");
        System.out.println(
                e2eFullLpnPicking
                        ? "E2E WAVE PROCESSING COMPLETED"
                        : "WAVE PROCESSING COMPLETED");
        System.out.println("Wave Number: " + waveNumber);
        System.out.println(
                "oLPN Number: " + ScenarioContext.get("oLPN nbr"));
        System.out.println("========================================");
    }


    public void returnToWaveRunsFromTasks()
            throws InterruptedException {

        System.out.println(
                "Returning from Tasks page to Wave Runs using link text."
        );

        returnToWaveRuns();
    }
    public void processPriorityBelowTenWorkflow(String originalWaveNumber)
            throws InterruptedException {
        if (originalWaveNumber == null || originalWaveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Original wave number cannot be null or empty");
        }
        System.out.println("========================================");
        System.out.println("PRIORITY BELOW 10 OUTBOUND WORKFLOW");
        System.out.println("Original Wave: " + originalWaveNumber);
        System.out.println("========================================");
        navigateToWaveRuns();
        filterWave(originalWaveNumber);
        waitForWaveCompletion();
        selectWave();
        openRelatedLinks();
        processOLPN(originalWaveNumber);
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processAllocations(originalWaveNumber);
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(originalWaveNumber);
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processTasks(originalWaveNumber);
        MUPPage mupPage = new MUPPage();
        mupPage.processFirstWaveTaskWithQuantityAdjustment(
                originalWaveNumber);
        returnToWaveRuns();
//        filterWave(originalWaveNumber);
//        selectWave();
//        openRelatedLinks();
//        click(oLpnSFromWave);
        Thread.sleep(3000);
        verifyOlpnStatusAndCaptureScreenshot(
                originalWaveNumber,
                "Packed",
                "Original Wave oLPN status after initial partial pick.");

        returnToWaveRuns();
//        filterWave(originalWaveNumber);
        selectWave();
        openRelatedLinks();
        click(taskSFromWave);
        Thread.sleep(3000);

        verifyTaskStatusForWave(originalWaveNumber, "Completed");
        String orderId = ScenarioContext.getString("Created Order");
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalStateException(
                    "Created Order was not available in ScenarioContext");
        }
        OrdersPage ordersPage = new OrdersPage();
        ordersPage.navigateToAnyUi("Orders");
        ordersPage.captureNewOrderLineAndScreenshot(orderId);

//        navigateToWaveRuns();
        runChaseFromOriginalWave(originalWaveNumber);

        ordersPage.navigateToAnyUi("Orders");
        ordersPage.captureChaseWaveNumberFromNewOrderLine(
                orderId,
                originalWaveNumber);

        String chaseWave =
                ScenarioContext.getString("Chase Wave Number");

        if (chaseWave == null || chaseWave.isBlank()) {
            throw new IllegalStateException(
                    "Chase Wave Number was not captured from the new Order Line");
        }
        processPriorityBelowTenChaseWave(chaseWave);
        returnToWaveRuns();
        filterWave(chaseWave);
        selectWave();
        openRelatedLinks();
        click(oLpnSFromWave);
        Thread.sleep(3000);
        verifyOlpnStatusAndCaptureScreenshot(
                chaseWave,
                "Packed",
                "Chase Wave oLPN status after full picking.");
        ordersPage.navigateToAnyUi("Orders");
        ordersPage.verifyOrderStatus(orderId, "Packed");
        System.out.println("========================================");
        System.out.println("PRIORITY BELOW 10 WORKFLOW COMPLETED");
        System.out.println("Original Wave: " + originalWaveNumber);
        System.out.println("Chase Wave: " + chaseWave);
        System.out.println("Order: " + orderId);
        System.out.println("========================================");
    }
    public void processPriorityBelowTenChaseWave(String chaseWaveNumber)
            throws InterruptedException {

        if (chaseWaveNumber == null || chaseWaveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Chase wave number cannot be null or empty");
        }

        System.out.println("========================================");
        System.out.println("PROCESSING BELOW-10 CHASE WAVE");
        System.out.println("Chase Wave: " + chaseWaveNumber);
        System.out.println("========================================");

        navigateToWaveRuns();
        filterWave(chaseWaveNumber);
        waitForWaveCompletion();

        // oLPNs
        selectWave();
        openRelatedLinks();
        processOLPN(chaseWaveNumber);

        // Allocations
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processAllocations(chaseWaveNumber);

        // Work Release Allocations / Generate Tasks
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(chaseWaveNumber);

        // Tasks
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processTasks(chaseWaveNumber);

        // Full quantity pick on all available Chase Wave tasks.
        // mandatoryOk=true matches the existing full-pick behavior.
        MUPPage mupPage = new MUPPage();
        mupPage.processWaveTasksInWmMobile(
                chaseWaveNumber,
                false,
                true,
                false,
                true,
                true);

        System.out.println(
                "Below-10 Chase Wave WM Mobile picking completed: "
                        + chaseWaveNumber);
    }


// =========================================================
// VERIFY TASK STATUS ON CURRENT TASKS PAGE
// =========================================================

    private void verifyTaskStatusForWave(
            String waveNumber,
            String expectedStatus) {

        Object taskObject =
                ScenarioContext.get("Task Numbers - " + waveNumber);

        if (!(taskObject instanceof List<?>)) {
            throw new IllegalStateException(
                    "Task numbers were not available in ScenarioContext for Wave: "
                            + waveNumber);
        }

        List<?> tasks = (List<?>) taskObject;

        if (tasks.isEmpty()) {
            throw new IllegalStateException(
                    "No tasks were captured for Wave: " + waveNumber);
        }

        for (Object task : tasks) {
            if (task == null || task.toString().isBlank()) {
                continue;
            }

            String taskId = task.toString().trim();

            By taskStatusLocator = By.xpath(
                    "//span[@data-component-id='TaskId' and normalize-space()='"
                            + taskId
                            + "']/ancestor::*[.//*[normalize-space()='"
                            + expectedStatus
                            + "']][1]");


            System.out.println(
                    "Verified task status " + expectedStatus
                            + " for Task: " + taskId);
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Verified " + expectedStatus
                        + " status for tasks in Wave: " + waveNumber);
    }
    private void processBatchTaskTransactions(String waveNumber)
            throws InterruptedException {

        if (waveNumber == null || waveNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Wave number cannot be null or empty.");
        }

        /*
         * The batch contains tasks from both priority orders.
         * MUPPage already processes every captured task one-by-one.
         * For this batch flow we intentionally:
         *   1. enter displayed quantity - 1
         *   2. execute Pick Exception handling
         *   3. return to Tasks after every task
         *   4. verify that the same task is Completed before continuing
         */
        MUPPage mupPage = new MUPPage();

        mupPage.processWaveTasksInWmMobile(
                waveNumber,
                true,   // subtract 1 from displayed quantity
                false,  // no mandatory OK after Pick Exception
                false,  // no optional OK
                true,   // return to Tasks after every task
                true    // verify the same task is Completed after every task
        );

        System.out.println(
                "All batch tasks processed successfully for Wave: "
                        + waveNumber);
    }

    public void processBatchAfterAllocation()
            throws InterruptedException {

        String originalWaveNumber =
                ScenarioContext.getString("Initial Batch Wave Number");

        if (originalWaveNumber == null
                || originalWaveNumber.isBlank()) {

            throw new IllegalStateException(
                    "Initial Batch Wave Number was not found in ScenarioContext.");
        }

        System.out.println("========================================");
        System.out.println("PROCESSING INITIAL BATCH WAVE");
        System.out.println("Wave: " + originalWaveNumber);
        System.out.println("========================================");
        navigateToWaveRuns();
        filterWave(originalWaveNumber);
        waitForWaveCompletion();
        selectWave();
        openRelatedLinks();
        processOLPN(originalWaveNumber);
        returnToWaveRuns();
        selectWave();
        openRelatedLinks();
        processAllocations(originalWaveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 3. WORK RELEASE ALLOCATIONS
        //    Generate tasks when required.
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processWorkReleaseAllocations(originalWaveNumber);
        returnToWaveRuns();

        // ---------------------------------------------------------
        // 4. TASKS - CAPTURE ALL TASK NUMBERS
        // ---------------------------------------------------------

        selectWave();
        openRelatedLinks();
        processTasks(originalWaveNumber);

        Object capturedTasks =
                ScenarioContext.get("Task Numbers - " + originalWaveNumber);

        System.out.println(
                "DEBUG | Wave: " + originalWaveNumber
                        + " | Captured task data: " + capturedTasks);

        // ---------------------------------------------------------
        // 5. WM MOBILE PICKING
        //    Every task gets quantity - 1 and is verified Completed
        //    before the next task is processed.
        // ---------------------------------------------------------

        processBatchTaskTransactions(originalWaveNumber);

        // ---------------------------------------------------------
        // 6. POST-PICK VALIDATION
        //    This phase intentionally stops after the new Order Line
        //    validation. Run Chase is NOT part of this phase.
        // ---------------------------------------------------------

        validateBatchAfterPicking(originalWaveNumber);
    }

    public void validateBatchAfterPicking(
            String originalWaveNumber)
            throws InterruptedException {

        if (originalWaveNumber == null
                || originalWaveNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Original wave number cannot be null or empty.");
        }

        System.out.println("========================================");
        System.out.println("BATCH POST-PICKING VALIDATION");
        System.out.println("Original Wave: " + originalWaveNumber);
        System.out.println("========================================");

        OrdersPage ordersPage = new OrdersPage();

        List<OrderData> batchOrders =
                ordersPage.getBatchCreatedOrders();

        if (batchOrders == null || batchOrders.size() != 2) {

            throw new IllegalStateException(
                    "Expected exactly 2 batch orders but found: "
                            + (batchOrders == null
                            ? 0
                            : batchOrders.size()));
        }

        OrderData belowTenOrder = null;
        OrderData tenOrAboveOrder = null;

        // ---------------------------------------------------------
        // IDENTIFY THE TWO ORDERS BY PRIORITY
        // ---------------------------------------------------------

        for (OrderData order : batchOrders) {

            if (order.getOrderPriority() == null
                    || order.getOrderPriority().isBlank()) {

                throw new IllegalStateException(
                        "Order priority is missing for order: "
                                + order.getOrderId());
            }

            int priority;

            try {
                priority = Integer.parseInt(
                        order.getOrderPriority().trim());
            } catch (NumberFormatException e) {

                throw new IllegalStateException(
                        "Invalid order priority for order "
                                + order.getOrderId()
                                + ": "
                                + order.getOrderPriority(),
                        e);
            }

            if (priority < 10) {

                if (belowTenOrder != null) {
                    throw new IllegalStateException(
                            "Multiple orders found with priority < 10.");
                }

                belowTenOrder = order;

            } else {

                if (tenOrAboveOrder != null) {
                    throw new IllegalStateException(
                            "Multiple orders found with priority >= 10.");
                }

                tenOrAboveOrder = order;
            }
        }

        if (belowTenOrder == null) {
            throw new IllegalStateException(
                    "No batch order with priority < 10 was found.");
        }

        if (tenOrAboveOrder == null) {
            throw new IllegalStateException(
                    "No batch order with priority >= 10 was found.");
        }

        System.out.println(
                "Priority < 10 Order: "
                        + belowTenOrder.getOrderId()
                        + " | Priority: "
                        + belowTenOrder.getOrderPriority());

        System.out.println(
                "Priority >= 10 Order: "
                        + tenOrAboveOrder.getOrderId()
                        + " | Priority: "
                        + tenOrAboveOrder.getOrderPriority());
        validateBatchOlpnStatuses(
                originalWaveNumber,
                belowTenOrder,
                tenOrAboveOrder
        );
        ordersPage.navigateToAnyUi("Orders");
        ordersPage.filterBatchOrders(batchOrders);

        validateBatchOrderStatuses(
                belowTenOrder,
                tenOrAboveOrder
        );
        ordersPage.filterExistingOrder(
                tenOrAboveOrder.getOrderId());

        captureBatchPackedOrderLineQuantity(
                tenOrAboveOrder);
        if(isDisplayed(redirectToOrdersFromOrderLine)){
            click(redirectToOrdersFromOrderLine);
        }else {
            ordersPage.navigateToAnyUi("Orders");
        }
        ordersPage.filterExistingOrder(
                belowTenOrder.getOrderId());

        captureBatchNewOrderLineAndScreenshot(
                belowTenOrder);
        runChaseFromOriginalWave(
                originalWaveNumber);
        ordersPage.navigateToAnyUi("Orders");

        ordersPage.verifyOrderStatus(
                belowTenOrder.getOrderId(),
                "Allocated");
        ordersPage.selectCurrentlyFilteredOrder(
                belowTenOrder.getOrderId());

        String chaseWaveNumber =
                ordersPage.captureChaseWaveNumberFromNewOrderLine(
                        belowTenOrder.getOrderId(),
                        originalWaveNumber);

        if (chaseWaveNumber == null
                || chaseWaveNumber.isBlank()) {

            throw new IllegalStateException(
                    "Chase Wave Number was not captured from the new Order Line.");
        }

        // ---------------------------------------------------------
        // 10. PROCESS THE CHASE WAVE
        //     Allocations -> Work Release Allocations -> Tasks
        //     -> Process all Chase Wave tasks.
        // ---------------------------------------------------------

        processChaseWave(chaseWaveNumber);

        // ---------------------------------------------------------
        // 11. RETURN TO ORIGINAL WAVE
        //     Verify the original/old oLPN is now Packed.
        // ---------------------------------------------------------

        navigateToWaveRuns();

        verifyOlpnStatusAndCaptureScreenshot(
                originalWaveNumber,
                "Packed",
                "Original Wave oLPN is Packed after Chase Wave task completion.");

        // ---------------------------------------------------------
        // 12. FINAL ORDER VALIDATION
        //     Both orders must be Packed before batch Ship Confirm.
        // ---------------------------------------------------------

        ordersPage.navigateToAnyUi("Orders");

        ordersPage.verifyOrderStatus(
                belowTenOrder.getOrderId(),
                "Packed");

        ordersPage.verifyOrderStatus(
                tenOrAboveOrder.getOrderId(),
                "Packed");

        // ---------------------------------------------------------
        // 13. SHIP CONFIRM BOTH BATCH ORDERS TOGETHER
        // ---------------------------------------------------------

        ordersPage.navigateToAnyUi("Orders");
        ordersPage.shipConfirmBatchOrders(batchOrders);

        System.out.println("========================================");
        System.out.println("BATCH CHASE WAVE PROCESSING COMPLETED");
        System.out.println("Original Wave: " + originalWaveNumber);
        System.out.println("Priority < 10 Order: "
                + belowTenOrder.getOrderId());
        System.out.println("Priority >= 10 Order: "
                + tenOrAboveOrder.getOrderId());
        System.out.println("Chase Wave: " + chaseWaveNumber);
        System.out.println("Final Order Status: Shipped");
        System.out.println("Original Wave oLPN Status: Packed");
        System.out.println("========================================");
    }

    private void validateBatchOlpnStatuses(
            String waveNumber,
            OrderData belowTenOrder,
            OrderData tenOrAboveOrder)
            throws InterruptedException {

        System.out.println("========================================");
        System.out.println("VALIDATING BATCH oLPN STATUSES");
        System.out.println("========================================");

//        navigateToWaveRuns();
//        filterWave(waveNumber);
//        waitForWaveCompletion();
        returnToWaveRuns();

        selectWave();
        openRelatedLinks();
        click(oLpnSFromWave);

        Thread.sleep(2500);
        waitForShowingText();

        // allOlpnNumbers is already List<WebElement>
        List<WebElement> olpnElements = allOlpnNumbers;

        if (olpnElements.size() != 2) {

            throw new IllegalStateException(
                    "Expected 2 oLPNs for batch wave "
                            + waveNumber
                            + " but found: "
                            + olpnElements.size());
        }

        boolean packingFound = false;
        boolean packedFound = false;

        for (WebElement olpnElement : olpnElements) {

            String olpnNumber = getText(olpnElement);

            if (olpnNumber == null || olpnNumber.isBlank()) {
                continue;
            }

            olpnNumber = olpnNumber.trim();

            System.out.println(
                    "Checking oLPN: " + olpnNumber);

            if (isOlpnStatusVisible(
                    olpnElement,
                    "Packing")) {

                packingFound = true;

                System.out.println(
                        "Verified oLPN "
                                + olpnNumber
                                + " -> Packing");
            }

            if (isOlpnStatusVisible(
                    olpnElement,
                    "Packed")) {

                packedFound = true;

                System.out.println(
                        "Verified oLPN "
                                + olpnNumber
                                + " -> Packed");
            }
        }

        if (!packingFound) {

            throw new AssertionError(
                    "No oLPN with Packing status was found for wave: "
                            + waveNumber);
        }

        if (!packedFound) {

            throw new AssertionError(
                    "No oLPN with Packed status was found for wave: "
                            + waveNumber);
        }

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Initial batch wave oLPN status validation completed."
                        + "\nPriority < 10 Order: "
                        + belowTenOrder.getOrderId()
                        + " -> expected oLPN status: Packing"
                        + "\nPriority >= 10 Order: "
                        + tenOrAboveOrder.getOrderId()
                        + " -> expected oLPN status: Packed"
                        + "\nWave: " + waveNumber
        );

        System.out.println(
                "Batch oLPN validation passed: Packing + Packed");
    }


    private void validateBatchOrderStatuses(
            OrderData belowTenOrder,
            OrderData tenOrAboveOrder)
            throws InterruptedException {

        By orderRows = By.xpath(
                "//div[@data-component-id='MinimumStatusDescription']");

        FluentWait<WebDriver> statusWait =
                new FluentWait<>(DriverManager.getDriver())
                        .withTimeout(Duration.ofSeconds(120))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);

        statusWait.until(driver -> {

            List<WebElement> statuses =
                    driver.findElements(orderRows);

            if (statuses.size() != 2) {
                return false;
            }

            boolean releasedFound = false;
            boolean packedFound = false;

            for (WebElement statusElement : statuses) {

                String status = getText(statusElement);

                if (status == null || status.isBlank()) {
                    return false;
                }

                status = status.trim();

                System.out.println(
                        "Filtered batch order status: " + status);

                if (status.equalsIgnoreCase("Released")) {
                    releasedFound = true;
                }

                if (status.equalsIgnoreCase("Packed")) {
                    packedFound = true;
                }
            }

            return releasedFound && packedFound;
        });

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Batch order status validation completed."
                        + "\nPriority < 10 Order: "
                        + belowTenOrder.getOrderId()
                        + " -> Released"
                        + "\nPriority >= 10 Order: "
                        + tenOrAboveOrder.getOrderId()
                        + " -> Packed"
        );

        System.out.println(
                "Batch order status validation passed: Released + Packed");
    }

    private void captureBatchPackedOrderLineQuantity(
            OrderData packedOrder)
            throws InterruptedException {

        String orderId = packedOrder.getOrderId();

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalStateException(
                    "Packed order ID is missing.");
        }

        System.out.println("========================================");
        System.out.println("PRIORITY >= 10 ORDER LINE QUANTITY");
        System.out.println("Order: " + orderId);
        System.out.println("========================================");

        OrdersPage ordersPage = new OrdersPage();
        ordersPage.openOrderLinesForOrder(orderId);

        Thread.sleep(2000);

        // Initial quantity stored during order creation
        OrderLineData orderLine =
                packedOrder.getOrderLines().getFirst();

        String initialQuantity =
                orderLine.getQuantity();

        // Current packed quantity from Order Lines UI
        By orderedQuantity = By.xpath(
                "//span[@data-component-id='OrderedQuantity']"
        );

        WebElement orderedQuantityElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                orderedQuantity
                        )
                );

        String currentPackedQuantity =
                getText(orderedQuantityElement);

        // Report only - no comparison/validation
        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Order Line Quantity"
                        + "\nOrder: " + orderId
                        + "\nOrder Line: "
                        + orderLine.getLineNumber()
                        + "\nInitial Quantity: "
                        + initialQuantity
                        + "\nCurrent Packed Quantity: "
                        + currentPackedQuantity
        );

        System.out.println(
                "Initial Quantity: " + initialQuantity);

        System.out.println(
                "Current Packed Quantity: "
                        + currentPackedQuantity);
    }

    private void captureBatchNewOrderLineAndScreenshot(
            OrderData partialPickOrder)
            throws InterruptedException {

        String orderId = partialPickOrder.getOrderId();

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalStateException(
                    "Priority < 10 Order ID is missing.");
        }

        List<String> existingLineNumbers = new ArrayList<>();

        for (var line : partialPickOrder.getOrderLines()) {
            if (line.getLineNumber() != null
                    && !line.getLineNumber().isBlank()) {
                existingLineNumbers.add(
                        line.getLineNumber().trim());
            }
        }

        if (existingLineNumbers.isEmpty()) {
            throw new IllegalStateException(
                    "No original Order Line numbers were stored for order: "
                            + orderId);
        }

        OrdersPage ordersPage = new OrdersPage();
        ordersPage.openOrderLinesForOrder(orderId);

        By orderLineCandidates = By.xpath(
                "//*[contains(translate(@data-component-id,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'),'orderline') "
                        + "and not(self::button) "
                        + "and not(self::ion-button) "
                        + "and not(self::input)]"
        );

        FluentWait<WebDriver> lineWait =
                new FluentWait<>(DriverManager.getDriver())
                        .withTimeout(Duration.ofSeconds(90))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(Exception.class);

        WebElement newOrderLineElement =
                lineWait.until(driver -> {

                    List<WebElement> candidates =
                            driver.findElements(orderLineCandidates);

                    for (WebElement candidate : candidates) {

                        try {
                            if (!candidate.isDisplayed()) {
                                continue;
                            }

                            String text = getText(candidate);

                            if (text == null || text.isBlank()) {
                                continue;
                            }

                            String lineNumber =
                                    extractBatchOrderLineNumber(text);

                            if (lineNumber != null
                                    && !existingLineNumbers.contains(
                                    lineNumber.trim())) {
                                return candidate;
                            }

                        } catch (Exception ignored) {
                            // UI may refresh while the new line is created.
                        }
                    }

                    return null;
                });

        String newOrderLine =
                extractBatchOrderLineNumber(
                        getText(newOrderLineElement));

        if (newOrderLine == null || newOrderLine.isBlank()) {
            throw new IllegalStateException(
                    "A new Order Line element was found, but the line number "
                            + "could not be extracted. UI text: "
                            + getText(newOrderLineElement));
        }

        ScenarioContext.set(
                "Batch New Order Line",
                newOrderLine);

        ScenarioContext.set(
                "New Order Line",
                newOrderLine);

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "New Order Line created after partial pick."
                        + "\nOrder: " + orderId
                        + "\nOriginal Order Lines: " + existingLineNumbers
                        + "\nNew Order Line: " + newOrderLine
        );

        System.out.println(
                "New Order Line identified for priority < 10 order: "
                        + newOrderLine);
    }

    private String extractBatchOrderLineNumber(String text) {

        if (text == null || text.isBlank()) {
            return null;
        }

        Pattern labelledPattern =
                Pattern.compile(
                        "(?i)(?:order\\s*line|line(?:\\s*number)?)"
                                + "\\s*[:#-]?\\s*(\\d+)"
                );

        Matcher labelledMatcher =
                labelledPattern.matcher(text);

        if (labelledMatcher.find()) {
            return labelledMatcher.group(1).trim();
        }

        String trimmed = text.trim();

        if (trimmed.matches("\\d+")) {
            return trimmed;
        }

        for (String line : text.split("\\R")) {

            String candidate = line.trim();

            if (candidate.matches("\\d+")) {
                return candidate;
            }
        }

        return null;
    }

}