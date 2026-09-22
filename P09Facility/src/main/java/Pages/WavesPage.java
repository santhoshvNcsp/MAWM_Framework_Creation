package Pages;

import com.p09.framework.context.ScenarioContext;
import com.p09.framework.driver.DriverManager;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;

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

public class WavesPage extends BasePage {

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


    // =========================================================
    // FILTER WAVE
    // =========================================================

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


        // =====================================================
        // VERIFY ENTERED VALUE BEFORE PRESS ENTER
        // =====================================================

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

        report.addReportStepWithoutScreenshot(
                StepStatus.PASS,
                "Wave displayed: "
                        + waveNumber
        );
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
    // PROCESS oLPN
    // =========================================================

    private void processOLPN(
            String waveNumber)
            throws InterruptedException {

        filterWave(
                waveNumber
        );

        selectWave();

        openRelatedLinks();

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


        // =====================================================
        // REPORT ALL oLPN NUMBERS
        // =====================================================

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

        filterWave(
                waveNumber
        );

        selectWave();

        openRelatedLinks();

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


        // =====================================================
        // REPORT ALLOCATION COUNT ONLY
        // =====================================================

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Allocations displayed for Wave: "
                        + waveNumber
                        + "\nTotal Allocations: "
                        + totalAllocations
        );
    }


    // =========================================================
    // PROCESS TASKS
    // =========================================================

    private void processTasks(
            String waveNumber)
            throws InterruptedException {

        filterWave(
                waveNumber
        );

        selectWave();

        openRelatedLinks();

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

        taskReport.append(
                "Tasks captured for Wave: "
        ).append(waveNumber);

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


        // =====================================================
        // REPORT ALL TASK IDs
        // =====================================================

        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                taskReport.toString()
        );
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
                        "of\\s+(\\d+)\\s+Records",
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
    // COMPLETE WAVE PROCESS
    // =========================================================

    public void processWave()
            throws InterruptedException {

        // =====================================================
        // GET WAVE NUMBER FROM SCENARIO CONTEXT
        // =====================================================

        String waveNumber =
                ScenarioContext.getString(
                        "wave nbr"
                );

        if (waveNumber == null
                || waveNumber.isBlank()) {

            throw new IllegalStateException(
                    "Wave number was not available in ScenarioContext"
            );
        }


        System.out.println(
                "========================================"
        );

        System.out.println(
                "PROCESSING WAVE"
        );

        System.out.println(
                "Wave Number: "
                        + waveNumber
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // WAVE RUNS
        // =====================================================

        navigateToWaveRuns();


        // =====================================================
        // 1. oLPNs
        // =====================================================

        System.out.println(
                "Opening oLPNs for Wave: "
                        + waveNumber
        );

        processOLPN(
                waveNumber
        );


        // =====================================================
        // RETURN TO WAVE RUNS
        // =====================================================

        returnToWaveRuns();


        // =====================================================
        // 2. ALLOCATIONS
        // =====================================================

        System.out.println(
                "Opening Allocations for Wave: "
                        + waveNumber
        );

        processAllocations(
                waveNumber
        );


        // =====================================================
        // RETURN TO WAVE RUNS
        // =====================================================

        returnToWaveRuns();


        // =====================================================
        // 3. TASKS
        // =====================================================

        System.out.println(
                "Opening Tasks for Wave: "
                        + waveNumber
        );

        processTasks(
                waveNumber
        );


        // =====================================================
        // FINAL
        // =====================================================

        System.out.println(
                "========================================"
        );

        System.out.println(
                "WAVE PROCESSING COMPLETED"
        );

        System.out.println(
                "Wave Number: "
                        + waveNumber
        );

        System.out.println(
                "oLPN Number: "
                        + ScenarioContext.get(
                        "oLPN nbr"
                )
        );

        System.out.println(
                "========================================"
        );


        report.addReportStepWithScreenshot(
                StepStatus.PASS,
                "Wave processing completed successfully: "
                        + waveNumber
        );
    }
}