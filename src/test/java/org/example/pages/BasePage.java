package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.example.factory.DriverFactory;
import org.example.utils.CapabilitiesReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.factory.DriverFactory.getDriver;

public class BasePage {

    private AppiumDriver<?> driver;
    private static CapabilitiesReader capabilitiesReader;
    DesiredCapabilities caps;

    // Logger
    private static Logger logger = Logger.getLogger(BasePage.class.getName());

    // Constant
    public static final long WAITTIME = 10;

    // Constructor
    public BasePage() throws IOException {
        this.driver = getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    //===============================================================================================
    // WRAPPER METHODS
    //===============================================================================================
    // These methods intend to facilitate the integration of wait() into the most common actions

    /**
     * Wrapper for wait.until(ExpectedConditions.visibilityOfElement() method
     * @param element
     */
    public void waitForVisibility(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wrapper for clear() method
     * @param element
     */
    public void clear(MobileElement element) {
        waitForVisibility(element);
        element.clear();
    }

    /**
     * Wrapper for click() method
     * @param element
     */
    public void click(MobileElement element) {
        waitForVisibility(element);
        element.click();
    }

    /**
     * Wrapper for sendKeys() method
     * @param element
     * @param text
     */
    public void sendKeys(MobileElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    /**
     * Wrapper for getAttribute() method
     * @param element
     * @param attribute
     * @return attribute name
     */
    public String getAttribute(MobileElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }


    //===============================================================================================
    // HOOKS
    //===============================================================================================
    @Before()
    public void setUp() throws URISyntaxException, IOException {
        logger.log(Level.INFO, "Reading the capabilities");
        capabilitiesReader = new CapabilitiesReader();
        caps = capabilitiesReader.getCaps();

        logger.log(Level.INFO, "Configuring a new session");
        driver = new DriverFactory().initDriver(caps);
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
