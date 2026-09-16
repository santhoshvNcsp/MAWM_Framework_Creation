package Pages;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.pages.BasePage;
import com.p09.framework.reporting.StepStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id = "username")
    public WebElement username;
    @FindBy(id = "password")
    public WebElement password;
    @FindBy(name = "login")
    public WebElement loginButton;
    @FindBy(xpath = "//button[@data-component-id='tab-button-Warehouse Management']")
    public WebElement warehouseManagementTab;

    public LoginPage() {
        super();
    }

    public void login() {
        report.addReportStepWithScreenshot(StepStatus.PASS, "Logging in with username: " + ConfigManager.get("username"));
        type(username, ConfigManager.get("username"), "Username Field");
        report.pass("Entered username: " + ConfigManager.get("username"));
        type(password, ConfigManager.get("password"), "Password Field");
        click(loginButton, "Login Button");
        if (isDisplayed(warehouseManagementTab, "Warehouse Management Tab")) {
            report.addReportStepWithScreenshot(StepStatus.PASS, "Login successful");
            report.pass("Warehouse Management tab is displayed");
            report.addReportStepWithScreenshot(StepStatus.PASS, "User Landed to Landing Page");
        } else {
            report.addReportStepWithScreenshot(StepStatus.FAIL, "Login failed");
            report.fail("Failed to land on Landing Page");
        }
    }
}
