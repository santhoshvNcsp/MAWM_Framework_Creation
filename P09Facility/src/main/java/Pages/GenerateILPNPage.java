package Pages;

import com.p09.framework.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GenerateILPNPage extends BasePage {
    @FindBy(xpath = "//ion-button[@data-component-id='menu-toggle-button']")
    public WebElement menuToggleButton;
    @FindBy(xpath = "//input[@placeholder='Search Menu...']")
    public WebElement searchBarInLandingPage;
    @FindBy(id = "wmMobile")
    public WebElement clickWmMobileFromMenu;
    @FindBy(xpath = "//ion-searchbar[@data-component-id='search']/div/input")
    public WebElement searchBarInWmMobile;
    @FindBy(xpath = "//ion-label[@data-component-id='createilpn']")
    public WebElement clickCreateILPNInWm;
    @FindBy(xpath = "//input[@placeholder='Scan iLPN']")
    public WebElement scanLpnId;
    @FindBy(xpath = "//input[@placeholder='Scan Item']")
    public WebElement scanItem;
    @FindBy(xpath = "//input[contains(@data-component-id,'acceptquantity_naturalquantityfield_uni')]")
    public WebElement passQtyReceive;
    @FindBy(xpath = "//button[@data-component-id='action_endilpn_button']")
    public WebElement endILpnButton;


    public GenerateILPNPage() {
        super();
    }

    public void navigateToWM() throws InterruptedException {
        Thread.sleep(3000);
        click(menuToggleButton);
        type(searchBarInLandingPage, "WM Mobile");
        Thread.sleep(3000);
        click(clickWmMobileFromMenu);
        String parentWindowId = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equalsIgnoreCase(parentWindowId)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void ILpnCreation() throws InterruptedException {
        Thread.sleep(3000);
        type(searchBarInLandingPage, "Create Ilpn");
        Thread.sleep(4000);
        click(clickCreateILPNInWm);
        Thread.sleep(3000);
        type(scanLpnId, "LPN10002");
        Thread.sleep(2000);
        pressEnter(scanLpnId);
        Thread.sleep(3000);
        type(scanItem, "19005");
        Thread.sleep(2000);
        pressEnter(scanItem);
        Thread.sleep(2000);
        type(passQtyReceive, "20");
        Thread.sleep(2000);
        pressEnter(passQtyReceive);
        Thread.sleep(2000);
        click(endILpnButton);
        Thread.sleep(3000);

    }

}
