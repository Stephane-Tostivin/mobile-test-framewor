package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage extends BasePage {

    private AppiumDriver driver;
    private static Logger logger = Logger.getLogger(LoginPage.class.getName());

    public LoginPage() throws IOException {
        super();
    }


    //===============================================================================================
    // Constants: default login and username
    //===============================================================================================
    private final String LOGIN_USERNAME = "company";
    private final String LOGIN_PASSWORD = "company";


    //===============================================================================================
    // Locators
    //===============================================================================================
    /* The locators can be defined both for the Android and the iOS application
       using the appropriate annotation */
    @AndroidFindBy(id = "android:id/title")
    @iOSXCUITFindBy(accessibility = "title")
    private MobileElement loginScreen;

    @AndroidFindBy(accessibility = "Uname")
    private MobileElement usernameField;

    @AndroidFindBy(accessibility = "Password")
    private MobileElement passwordField;

    @AndroidFindBy(id = "loginButton")
    private MobileElement loginBtn;

    @AndroidFindBy(id = "android:id/message")
    private MobileElement loginErrorMsg;

    @AndroidFindBy(id = "paymentHomeView")
    private MobileElement paymentScreen;


    //===============================================================================================
    // Actions
    //===============================================================================================

    /**
     * Make sure the Login screen is displayed
     * otherwise no action can be done on that screen
     * @return true or false
     */
    public boolean isloginPage() {
        try {
            waitForVisibility(loginScreen);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set a value in the field Username
     * @param username
     */
    public void setUsername(String username) {
            sendKeys(usernameField, username);
    }

    /**
     * Set a value in the field Password
     * @param password
     */
    public void setPassword(String password) {
            sendKeys(passwordField, password);
    }

    /**
     * Click on the Login button
     */
    public void clickLogin() {
            click(loginBtn);
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
        try {
            waitForVisibility(paymentScreen);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the error message displayed when the login (username or password) is incorrect
     * @return the text in the error message box
     */
    public String getErrorMessage() {
        String errorMsg = null;
        return errorMsg = getAttribute(loginErrorMsg, "text");

    }
}
