package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.example.factory.DriverFactory.getDriver;

public class BasePage {

    private AppiumDriver<?> driver;

    // Constant
    public static final long WAITTIME = 10;

    // Constructor
    public BasePage() {
        this.driver = getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    //===============================================================================================
    // WRAPPER METHODS
    //===============================================================================================
    // These methods intend to facilitate the integration of wait() into the most common actions

    /**
     * Wrapper for wait.until(ExpectedConditions.visibilityOfElement() method
     * @param element
     */
    public void waitForVisibility(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wrapper for clear() method
     * @param element
     */
    public void clear(MobileElement element) {
        waitForVisibility(element);
        element.clear();
    }

    /**
     * Wrapper for click() method
     * @param element
     */
    public void click(MobileElement element) {
        waitForVisibility(element);
        element.click();
    }

    /**
     * Wrapper for sendKeys() method
     * @param element
     * @param text
     */
    public void sendKeys(MobileElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    /**
     * Wrapper for getAttribute() method
     * @param element
     * @param attribute
     * @return attribute name
     */
    public String getAttribute(MobileElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
}
