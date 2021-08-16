package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MakePaymentPage {

    private AppiumDriver driver;
    private static Logger logger = Logger.getLogger(MakePaymentPage.class.getName());

    public MakePaymentPage(AppiumDriver driver) {
        this.driver = driver;
    }


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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> makePaymentScreenReady = ExpectedConditions.visibilityOf(makePaymentScreen);
        try {
            wait.until(makePaymentScreenReady).click();
            return true;
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Set a value in the field Phone
     * @param phoneNumber
     */
    public void setPhone(String phoneNumber) {
        if(isMakePaymentPage()) {
            phoneField.sendKeys(phoneNumber);
        }
    }

    /**
     * Set a value in the field Name
     * @param name
     */
    public void setName(String name) {
        if(isMakePaymentPage()) {
            nameField.sendKeys(name);
        }
    }

    /**
     * Set a value in the field Amount
     * @param amount
     */
    public void setAmount(Integer amount) {
        if(isMakePaymentPage()) {
            amountField.sendKeys(amount.toString());
        }
    }

    /**
     * Set a value in the field Country
     * @param country
     */
    public void setCountry(String country) {
        if(isMakePaymentPage()) {
            countryField.sendKeys(country);
        }
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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> sendPaymentButtonReady = ExpectedConditions.visibilityOf(sendPaymentButton);
        try {
            wait.until(sendPaymentButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Confirm the payment when prompted
     * @return PaymentHomePage
     */
    public PaymentHomePage confirmPayment() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> yesButtonReady = ExpectedConditions.visibilityOf(yesButton);
        try {
            wait.until(yesButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return new PaymentHomePage(driver);
    }

    public void refusePayment() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> noButtonReady = ExpectedConditions.visibilityOf(noButton);
        try {
            wait.until(noButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Cancel the information set in the Make Payment screen
     */
    public void clickCancelButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> cancelButtonReady = ExpectedConditions.visibilityOf(cancelButton);
        try {
            wait.until(cancelButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
