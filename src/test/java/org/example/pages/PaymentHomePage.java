package org.example.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentHomePage extends BasePage {

    private static Logger logger = Logger.getLogger(PaymentHomePage.class.getName());

    public PaymentHomePage() {
    }


    //===============================================================================================
    // Locators
    //===============================================================================================
    /* The mobile locators can be defined both for the Android and the iOS application
       using the appropriate annotation */
    @AndroidFindBy(id = "paymentHomeView")
    private MobileElement paymentHomeScreen;

    // This one is a locator for a webelement in a webview - not a mobile element
    @FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]")
    private WebElement balanceText;

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
        logger.log(Level.INFO, "Make sure the Payment home page is displayed");
        try {
            waitForVisibility(paymentHomeScreen);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the balance displayed in the PaymentHome screen
     * @return the message with balance amount
     */
    public String getBalance() {
        String balanceMessage = null;

        // The message is displayed after some time, so pls wait...
        try {
            waitForVisibility(balanceText);
            logger.log(Level.INFO, "Get the balance message");
            balanceMessage = balanceText.getText();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return balanceMessage;
    }

    /**
     * Click on the button Make Payment
     * @return
     */
    public MakePaymentPage clickMakePaymentButton() {
        logger.log(Level.INFO, "Click on the Make Payment button");
        buttonMakePayment.click();
        return new MakePaymentPage();
    }

}
