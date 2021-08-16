package org.example.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UIActions {

    private AppiumDriver driver;
    public static final long WAITTIME = 10;

    public UIActions(AppiumDriver appiumDriver) {
        driver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForVisibility(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clear(MobileElement element) {
        waitForVisibility(element);
        element.clear();
    }

    public void click(MobileElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(MobileElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public String getAttribute(MobileElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
}
