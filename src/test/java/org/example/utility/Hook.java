package org.example.utility;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.example.factory.DriverFactory;
import org.example.utils.CapabilitiesReader;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hook {

    // Appium driver
    private static AppiumDriver driver;
    private static CapabilitiesReader capabilitiesReader;
    JSONObject config;
    DesiredCapabilities caps;
    String appiumHubUrl;

    // Logger
    private static Logger logger = Logger.getLogger(Hook.class.getName());


    @Before()
    public void setUp() {
        logger.log(Level.INFO, "Reading the capabilities");
        capabilitiesReader = new CapabilitiesReader();

        // Read the configuration file once
        config = capabilitiesReader.getConfiguration();

        // Build the capabilities and the Appium Hub URL
        caps = capabilitiesReader.getCaps(config);
        appiumHubUrl = capabilitiesReader.getAppiumHubURL(config);

        // Initialize the driver based upon those values
        logger.log(Level.INFO, "Configuring a new session");
        driver = new DriverFactory().initDriver(caps, appiumHubUrl);
    }

    @After(order = 0)
    public void tearDown() {
        logger.log(Level.INFO, "Quit Appium driver");
        driver.quit();
    }

    @After(order = 1)
    public void takeScreenshot(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            // Timestamp
            String now = LocalDateTime.now().toString();
            now = now.replace(":", "_")
               .replace(";", "_")
               .replace(".", "_");
            // Take screenshot
            String screenshotName = now + "-" + scenario.getName().replaceAll(" ", "_");
            File screenshotFile  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("target/screenshots/" + screenshotName + ".jpg"));
            logger.log(Level.INFO, "File "+ screenshotName + ".jpg generated");
        }
    }
}
