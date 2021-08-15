package org.example.qa.factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverFactory {

    // Allow parallel runs
    public static ThreadLocal<AppiumDriver> tlDriver = new ThreadLocal<>();

    private static final String APPIUM_HUB_URL = "http://127.0.0.1:4723/wd/hub";

    static Logger logger = Logger.getLogger(DriverFactory.class.getName());

    /**
     * Initialize the threadLocal driver according to a given platform (Android / iOS)
     * @param caps  List of capabilities read from the json file
     * @return tlDriver.get()
     * @throws IOException
     */
    public AppiumDriver initDriver(DesiredCapabilities caps) throws IOException {

        logger.log(Level.INFO, "Initializing driver");
        String platformName = caps.getPlatform().name();

        if(platformName.equals("ANDROID")) {
            // Initialize the AndroidDriver
            // TODO: probably some capabilities should be adapted for Android
            tlDriver.set(new AndroidDriver<MobileElement>(new URL(APPIUM_HUB_URL), caps));
            logger.log(Level.INFO, "AndroidDriver initialized");

        } else if(platformName.equals("IOS")) {
            // Initialize the iOSDriver
            // TODO: probably some capabilities should be adapted for iOS
            tlDriver.set(new IOSDriver(new URL(APPIUM_HUB_URL), caps));
            logger.log(Level.INFO, "iOSDriver initialized");
        } else {
            logger.log(Level.SEVERE, "The requested driver does not exist! Please choose ANDROID or IOS");
            // TODO: add throwException to stop the execution of the program
        }

        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return getDriver();

    }

    /**
     * Get the driver for the local thread
     * @return the local thread driver
     */
    public static synchronized AppiumDriver getDriver() {
        return tlDriver.get();
    }

}
