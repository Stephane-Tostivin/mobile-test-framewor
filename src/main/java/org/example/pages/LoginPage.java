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

public class LoginPage {

    private AppiumDriver driver;
    private static Logger logger = Logger.getLogger(LoginPage.class.getName());

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
    }


    //===============================================================================================
    // Constants: default login and username
    //===============================================================================================
    private final String LOGIN_USERNAME = "company";
    private final String LOGIN_PASSWORD = "company";


    //===============================================================================================
    // Locators
    //===============================================================================================
    // TODO: remove the package name from the locator using id, it is not necessary
    private By loginScreen = By.id("android:id/title");
    private By usernameField = MobileBy.AccessibilityId("Uname");
    private By passwordField = MobileBy.AccessibilityId("Password");
    private By loginBtn = By.id("com.experitest.eribank:id/loginButton");
    private By loginErrorMsg = By.id("android:id/message");
    private By paymentScreen = By.id("com.experitest.eribank:id/paymentHomeView");


    //===============================================================================================
    // Actions
    //===============================================================================================

    /**
     * Make sure the Login screen is displayed
     * otherwise no action can be done on that screen
     * @return true or false
     */
    public boolean isloginPage() {
        // TODO: define a generic method to wait for a WebElement that should be called instead of repeating those 2 lignes each time
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> loginScreenReady = ExpectedConditions.presenceOfElementLocated(loginScreen);
        try {
            wait.until(loginScreenReady).click();
            return true;
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Set a value in the field Username
     * @param username
     */
    public void setUsername(String username) {
        if(isloginPage()) {
            driver.findElement(usernameField).sendKeys(username);
        }
    }

    /**
     * Set a value in the field Password
     * @param password
     */
    public void setPassword(String password) {
        if(isloginPage()) {
            driver.findElement(passwordField).sendKeys(password);
        }
    }

    /**
     * Click on the Login button
     */
    public void clickLogin() {
        if(isloginPage()) {
            driver.findElement(loginBtn).click();
        }
    }

    /**
     * Login to the application with the default user
     * @return PaymentHomePage
     */
    public PaymentHomePage doLogin() {
        logger.log(Level.INFO, "Login with username " + LOGIN_USERNAME + " and password " + LOGIN_PASSWORD);
        setUsername(LOGIN_USERNAME);
        setPassword(LOGIN_PASSWORD);
        clickLogin();
        return new PaymentHomePage(driver);
    }

    /**
     * Login to the application with provided credentials
     * @param username
     * @param password
     * @return PaymentHomePage
     */
    public PaymentHomePage doLogin(String username, String password) {
        logger.log(Level.INFO, "Login with username " + username + " and password " + password);
        setUsername(username);
        setPassword(password);
        clickLogin();
        return new PaymentHomePage(driver);
    }


    /**
     * Check if the user is authenticated after a login tentative
     * @return true or false
     */
    public boolean isLogged() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> paymentScreenReady = ExpectedConditions.presenceOfElementLocated(paymentScreen);

        try {
            wait.until(paymentScreenReady);
            return true;
        } catch (SessionNotCreatedException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * Get the error message displayed when the login (username or password) is incorrect
     * @return the text in the error message box
     */
    public String getErrorMessage() {
        String errorMsg = null;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<WebElement> errorMessageReady = ExpectedConditions.presenceOfElementLocated(loginErrorMsg);
        return errorMsg = wait.until(errorMessageReady).getText();

    }
}
