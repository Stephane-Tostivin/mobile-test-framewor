package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
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
    /* The locators can be defined both for the Android and the iOS application
       using the appropriate annotation */
    @AndroidFindBy(id = "paymentHomeView")
    private MobileElement paymentHomeScreen;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]")
    private MobileElement balanceText;

    @AndroidFindBy(id = "makePaymentButton")
    private MobileElement buttonMakePayment;


    //===============================================================================================
    // Actions
    //===============================================================================================
    /**
     * Make sure the payment home screen is displayed
     * @return true or false
     */
    public boolean isPaymentHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> paymentScreenReady = ExpectedConditions.visibilityOf(paymentHomeScreen);
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
        ExpectedCondition<WebElement> balanceMessageReady = ExpectedConditions.visibilityOf(balanceText);
        try {
            wait.until(balanceMessageReady);
            balanceMessage = balanceText.getText();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return balanceMessage;
    }

    public MakePaymentPage clickMakePaymentButton() {
        buttonMakePayment.click();
        return new MakePaymentPage(driver);
    }

}
