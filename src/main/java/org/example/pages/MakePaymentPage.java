package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
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
    private By makePaymentScreen = By.id("com.experitest.eribank:id/makePaymentView");
    private By phoneField = By.id("com.experitest.eribank:id/phoneTextField");
    private By nameField = By.id("com.experitest.eribank:id/nameTextField");
    private By amountField = By.id("com.experitest.eribank:id/amountTextField");
    private By countryField = By.id("com.experitest.eribank:id/countryTextField");
    private By sendPaymentButton = MobileBy.AccessibilityId("Send Payment");
    private By cancelButton = By.id("com.experitest.eribank:id/cancelButton");
    private By yesButton = By.id("android:id/button1");
    private By noButton = By.id("android:id/button2");



    //===============================================================================================
    // Actions
    //===============================================================================================

    /**
     * Make sure the make payment screen is ready
     * @return true or false
     */
    public boolean isMakePaymentPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> makePaymentScreenReady = ExpectedConditions.presenceOfElementLocated(makePaymentScreen);
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
            driver.findElement(phoneField).sendKeys(phoneNumber);
        }
    }

    /**
     * Set a value in the field Name
     * @param name
     */
    public void setName(String name) {
        if(isMakePaymentPage()) {
            driver.findElement(nameField).sendKeys(name);
        }
    }

    /**
     * Set a value in the field Amount
     * @param amount
     */
    public void setAmount(Integer amount) {
        if(isMakePaymentPage()) {
            driver.findElement(amountField).sendKeys(amount.toString());
        }
    }

    /**
     * Set a value in the field Country
     * @param country
     */
    public void setCountry(String country) {
        if(isMakePaymentPage()) {
            driver.findElement(countryField).sendKeys(country);
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
        ExpectedCondition<WebElement> sendPaymentButtonReady = ExpectedConditions.presenceOfElementLocated(sendPaymentButton);
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
        ExpectedCondition<WebElement> yesButtonReady = ExpectedConditions.presenceOfElementLocated(yesButton);
        try {
            wait.until(yesButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return new PaymentHomePage(driver);
    }

    public void refusePayment() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> noButtonReady = ExpectedConditions.presenceOfElementLocated(noButton);
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
        ExpectedCondition<WebElement> cancelButtonReady = ExpectedConditions.presenceOfElementLocated(cancelButton);
        try {
            wait.until(cancelButtonReady).click();
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
