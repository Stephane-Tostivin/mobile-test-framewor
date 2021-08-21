package org.example.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.SessionNotCreatedException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MakePaymentPage extends BasePage {

    private static Logger logger = Logger.getLogger(MakePaymentPage.class.getName());

    //===============================================================================================
    // Locators
    //===============================================================================================
    @AndroidFindBy(id = "makePaymentView")
    private MobileElement makePaymentScreen;

    @AndroidFindBy(id = "phoneTextField")
    private MobileElement phoneField;

    @AndroidFindBy(id = "nameTextField")
    private MobileElement nameField;

    @AndroidFindBy(id = "amountTextField")
    private MobileElement amountField;

    @AndroidFindBy(id = "countryTextField")
    private MobileElement countryField;

    @AndroidFindBy(accessibility = "Send Payment")
    private MobileElement sendPaymentButton;

    @AndroidFindBy(id = "cancelButton")
    private MobileElement cancelButton;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement yesButton;

    @AndroidFindBy(id = "android:id/button2")
    private MobileElement noButton;


    //===============================================================================================
    // Actions
    //===============================================================================================

    /**
     * Make sure the make payment screen is ready
     * @return true or false
     */
    public boolean isMakePaymentPage() {
        logger.log(Level.INFO, "Make sure the Make payment screen is displayed");
        try {
            waitForVisibility(makePaymentScreen);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Set a value in the field Phone
     * @param phoneNumber
     */
    public void setPhone(String phoneNumber) {
        logger.log(Level.INFO, "Set phone number to: " + phoneNumber);
        sendKeys(phoneField, phoneNumber);
    }

    /**
     * Set a value in the field Name
     * @param name
     */
    public void setName(String name) {
        logger.log(Level.INFO, "Set name to:" + name);
        sendKeys(nameField, name);
    }

    /**
     * Set a value in the field Amount
     * @param amount
     */
    public void setAmount(Integer amount) {
        logger.log(Level.INFO, "Set amount to:" + amount);
        sendKeys(amountField, amount.toString());
    }

    /**
     * Set a value in the field Country
     * @param country
     */
    public void setCountry(String country) {
        logger.log(Level.INFO, "Set country to:" + country);
        sendKeys(countryField, country);
    }

    /**
     * Select a country in the list
     * @param country
     */
    public void selectCountry(String country) {

    }

    /**
     * Click on the send Payment button
     */
    public void clickSendPaymentButton() {
        logger.log(Level.INFO, "Click on payment button");
        try {
            click(sendPaymentButton);
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Confirm the payment when prompted
     * @return PaymentHomePage
     */
    public PaymentHomePage confirmPayment() {
        logger.log(Level.INFO, "Click on confirm button");
        try {
            click(yesButton);
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return new PaymentHomePage();
    }

    public void refusePayment() {
        logger.log(Level.INFO, "Click on refuse button");
        try {
            click(noButton);
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Cancel the information set in the Make Payment screen
     */
    public void clickCancelButton() {
        logger.log(Level.INFO, "Click on cancel button");
        try {
            click(cancelButton);
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
