package Pages;

import com.p09.framework.context.ScenarioContext;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WavesPage extends BasePage {
    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;
    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;
    @FindBy(xpath = "//button[@data-component-id='WaveRuns']")
    public WebElement clickWaveRuns;
    @FindBy(xpath = "//span[contains(text(),'Showing ')]")
    public WebElement showingTextInWavesPageForVerification;
    @FindBy(xpath = "//ion-input[@data-component-id='OrderPlanningRunId-lookup-dialog-filter-input']/label/div[2]/input")
    public WebElement waveRunId;
    @FindBy(xpath = "//ion-button[@data-component-id='refresh']")
    public WebElement refresh;
    @FindBy(xpath = "//span[@data-component-id='OrderPlanningRunId']")
    public WebElement selectTheWave;

    public  static final By waveStatus = By.xpath(
            "//div[@data-component-id='PlanningStatusDescription']"
    );

    public void navigateToAnyUi(
            String uiName)
            throws InterruptedException {
        click(
                menuToggleButton
        );
        click(
                searchBarInLandingPage
        );
        type(searchBarInLandingPage, uiName);
        if (getAttribute(searchBarInLandingPage,"value").equalsIgnoreCase(
                uiName
        )){
            click(clickWaveRuns);
        }
        if (isDisplayed(showingTextInWavesPageForVerification)){
            report.addReportStepWithScreenshot(StepStatus.PASS,"user navigated to Wave Runs Page");
            type(waveRunId, ScenarioContext.get("wave nbr").toString());
            if (getAttribute(waveRunId,"value").equalsIgnoreCase(ScenarioContext.get("wave nbr").toString())){
                pressEnter(waveRunId);
                Thread.sleep(2000);
                report.addReportStepWithScreenshot(StepStatus.PASS,"Wave Id Filtered");
                waitForStatus(waveStatus,
                        () -> click(refresh),
                        "Completed");
            }
            click(selectTheWave);

        }
    }
}
