package org.example.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentHomePage {

    private AppiumDriver driver;
    private static Logger logger = Logger.getLogger(PaymentHomePage.class.getName());

    public PaymentHomePage(AppiumDriver driver) {
        this.driver = driver;
    }


    //===============================================================================================
    // Locators
    //===============================================================================================
    private By paymentHomeScreen = By.id("com.experitest.eribank:id/paymentHomeView");
    private By balanceText = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]");
    private By buttonMakePayment = By.id("com.experitest.eribank:id/makePaymentButton");


    //===============================================================================================
    // Actions
    //===============================================================================================
    /**
     * Make sure the payment home screen is displayed
     * @return true or false
     */
    public boolean isPaymentHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> paymentScreenReady = ExpectedConditions.presenceOfElementLocated(paymentHomeScreen);
        try {
            wait.until(paymentScreenReady);
            return true;
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    public String getBalance() {
        String balanceMessage = null;

        // The message is displayed after some time, so pls wait...
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> balanceMessageReady = ExpectedConditions.presenceOfElementLocated(balanceText);
        try {
            wait.until(balanceMessageReady);
            balanceMessage = driver.findElement(balanceText).getText();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return balanceMessage;
    }

    public MakePaymentPage clickMakePaymentButton() {
        driver.findElement(buttonMakePayment).click();
        return new MakePaymentPage(driver);
    }

}
