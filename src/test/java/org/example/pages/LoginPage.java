package org.example.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage extends BasePage {

    private static Logger logger = Logger.getLogger(LoginPage.class.getName());

    public LoginPage() {
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
        logger.log(Level.INFO, "Make sure login screen is visible");
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
        logger.log(Level.INFO, "Set username to " + username);
        sendKeys(usernameField, username);
    }

    /**
     * Set a value in the field Password
     * @param password
     */
    public void setPassword(String password) {
        logger.log(Level.INFO, "Set password to " + password);
        sendKeys(passwordField, password);
    }

    /**
     * Click on the Login button
     */
    public void clickLogin() {
        logger.log(Level.INFO, "Click login button");
        click(loginBtn);
    }

    /**
     * Login to the application with the default user
     * @return PaymentHomePage
     */
    public PaymentHomePage doLogin() {
        setUsername(LOGIN_USERNAME);
        setPassword(LOGIN_PASSWORD);
        clickLogin();
        return new PaymentHomePage();
    }

    /**
     * Login to the application with provided credentials
     * @param username
     * @param password
     * @return PaymentHomePage
     */
    public PaymentHomePage doLogin(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLogin();
        return new PaymentHomePage();
    }


    /**
     * Check if the user is authenticated after a login tentative
     * @return true or false
     */
    public boolean isLogged() {
        logger.log(Level.INFO, "Make sure the user has been authenticated by changing screen");
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
        logger.log(Level.INFO, "Get the error message when login is incorrect");
        String errorMsg = null;
        return errorMsg = getAttribute(loginErrorMsg, "text");

    }
}
