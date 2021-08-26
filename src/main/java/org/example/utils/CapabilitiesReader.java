package org.example.utils;

import io.appium.java_client.remote.MobileCapabilityType;
import org.example.factory.DriverFactory;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapabilitiesReader {

    // CONSTANTS
    private static final String CAPABILITIES_FILE = "/configuration/capabilities.json";

    // Logger
    static Logger logger = Logger.getLogger(CapabilitiesReader.class.getName());

    public static JSONObject getConfiguration() {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(DriverFactory.class.getResource(CAPABILITIES_FILE).toURI())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the file: " + CAPABILITIES_FILE);
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Error parsing the file: " + CAPABILITIES_FILE);
        }
        JSONObject configuration = new JSONObject(text);
        return configuration;
    }
    /**
     * Load from file capabilities.json the mobile capabilities
     * @return DesiredCapabilities
     */
    public static DesiredCapabilities getCaps(JSONObject config) {
        DesiredCapabilities caps = new DesiredCapabilities();

        // By default we take the value from capabilities files
        // Except if a system env variable is defined with the expected name
        // It allows the user or Jenkins to change the capabilities on the fly

        // PlatformName
        String platformName = System.getenv("PLATFORM_NAME");
        if(platformName == null) {
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, config.get("platformName"));
        } else {
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        }

        // PlatformVersion
        String platformVersion = System.getenv("PLATFORM_VERSION");
        if(platformVersion == null) {
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.get("platformVersion"));
        } else {
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        }

        // DeviceName
        String deviceName = System.getenv("DEVICE_NAME");
        if(deviceName == null) {
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.get("deviceName"));
        } else {
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        }

        // Set the package location as capability APP only if installation is required
        String installRequired = System.getenv("TO_INSTALL");
        String appLocation = System.getenv("APP_LOCATION");
        if(installRequired != null) {
            // The APP should be installed from a given location, or by default from the path given in the capabilities file
            logger.log(Level.INFO, "Capability APP set ==> new installation of the application");
            if(appLocation == null) {
                caps.setCapability(MobileCapabilityType.APP, config.get("appLocation"));
            } else {
                caps.setCapability(MobileCapabilityType.APP, appLocation);
            }
        }
        caps.setCapability("appActivity", config.get("appActivity"));
        caps.setCapability("appPackage", config.get("appPackage"));

        return caps;
    }


    /**
     * Build the URL for the Appium server
     * @return the URL for the targeted Appium server
     */
    public static String getAppiumHubURL(JSONObject config) {
        String AppiumHubURL = null;

        // Read the targeted System Under Test from the system environment variable TARGET_SUT
        // By default: local
        String targetSUT = System.getenv("TARGET_SUT");
        if(targetSUT == null) {
            targetSUT = "local";
        }

        logger.log(Level.INFO, "The test system is: " + targetSUT);

        // Get the Appium URL if target is local
        if(targetSUT.equalsIgnoreCase("local")) {
            AppiumHubURL = config.getJSONObject("localEnv").getString("url");
        }

        // Get the Appium URL if target is BrowserStack
        else if(targetSUT.equalsIgnoreCase("browserstack")) {
            // Check if BROWSERSTACK_USERNAME is defined as system env variable
            // Otherwise we take the value in the capabilities files
            String username = System.getenv("BROWSERSTACK_USERNAME");
            if(username == null) {
                username = config.getJSONObject("BrowserStackEnv").getString("username");
            }

            // Check if BROWSERSTACK_ACCESS_KEY is defined as system env variable
            // Otherwise we take the value in the capabilities files
            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if(accessKey == null) {
                accessKey = config.getJSONObject("BrowserStackEnv").getString("access_key");
            }

            // Build the URL
            AppiumHubURL = "http://"+username+":"+accessKey+"@"+config.getJSONObject("BrowserStackEnv").getString("server")+"/wd/hub";
        }

        // Get the Appium URL as a local one if incorrect value for system variable has been given
        else {
            logger.log(Level.SEVERE, "The System Under Test targeted: " + targetSUT + " is unknown!");
            logger.log(Level.SEVERE, "Default local SUT will be considered instead");
            AppiumHubURL = config.getJSONObject("localEnv").getString("url");
        }

        logger.log(Level.INFO, "URL of the Appium server: " + AppiumHubURL);
        return AppiumHubURL;

    }

}
