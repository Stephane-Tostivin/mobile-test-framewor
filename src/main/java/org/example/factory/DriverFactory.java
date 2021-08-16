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

        // Set capabilities according to what has been requested through the environment variables
        // By default: Android 11 on PixelXL28
        String platformName = System.getProperty("PlatformName", caps.getPlatform().name());
        String platformVersion = System.getProperty("PlatformVersion", "11");
        String deviceName = System.getProperty("DeviceName", "PixelXL28");

        caps.setCapability(MobileCapabilityType.VERSION, platformVersion);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

        if(platformName.equalsIgnoreCase("android")) {
            // TODO: see if UIAutomator2 can be replaced by Espresso
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            //caps.setCapability("forceEspressoRebuild", true);

            // Initialize the AndroidDriver
            tlDriver.set(new AndroidDriver<AndroidElement>(new URL(APPIUM_HUB_URL), caps));
            logger.log(Level.INFO, "AndroidDriver initialized");

        } else if(platformName.equalsIgnoreCase("ios")) {
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

            // Initialize the iOSDriver
            tlDriver.set(new IOSDriver<IOSElement>(new URL(APPIUM_HUB_URL), caps));
            logger.log(Level.INFO, "iOSDriver initialized");
        } else {
            logger.log(Level.SEVERE, "The requested driver does not exist! Please choose Android or iOS");
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
