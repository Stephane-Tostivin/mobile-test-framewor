package org.example.factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @usage: This class is used to initiate an Android or an iOS driver
 *          on a local thread
 */

public class DriverFactory {

    // Allow parallel runs
    public static ThreadLocal<AppiumDriver> tlDriver = new ThreadLocal<>();

    // Logger
    static Logger logger = Logger.getLogger(DriverFactory.class.getName());

    /**
     * Initialize the threadLocal driver according to a given platform (Android / iOS)
     * @param caps  List of capabilities read from the json file
     * @return tlDriver.get()
     */
    public AppiumDriver initDriver(DesiredCapabilities caps, String appiumHubUrl) {

        // Differentiate the driver initialization for Android and iOS
        String platformName = caps.getCapability(MobileCapabilityType.PLATFORM_NAME).toString();

        if(platformName.equalsIgnoreCase("android")) {
            logger.log(Level.INFO, "Initializing an Android driver");
            // TODO: see if UIAutomator2 can be replaced by Espresso
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            //caps.setCapability("forceEspressoRebuild", true);

            // Initialize the AndroidDriver
            try {
                tlDriver.set(new AndroidDriver<AndroidElement>(new URL(appiumHubUrl), caps));
            } catch (MalformedURLException e) {
                logger.log(Level.SEVERE, "Invalid URL: " + appiumHubUrl);
            }
            logger.log(Level.INFO, "AndroidDriver initialized");

        } else if(platformName.equalsIgnoreCase("ios")) {
            logger.log(Level.INFO, "Initializing an iOS driver");
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

            // Initialize the iOSDriver
            try {
                tlDriver.set(new IOSDriver<IOSElement>(new URL(appiumHubUrl), caps));
            } catch (MalformedURLException e) {
                logger.log(Level.SEVERE, "Invalid URL: " + appiumHubUrl);
            }
            logger.log(Level.INFO, "iOSDriver initialized");

        } else {
            logger.log(Level.SEVERE, "The requested driver does not exist! Please choose Android or iOS");
        }

        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return getDriver();

    }

    /**
     * Get the driver for the local thread
     * @return the local thread driver
     */
    public static AppiumDriver getDriver() {
        return tlDriver.get();
    }

}
