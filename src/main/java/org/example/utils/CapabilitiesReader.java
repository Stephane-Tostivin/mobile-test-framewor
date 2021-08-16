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

public class CapabilitiesReader {

    /**
     * Load from file capabilities.json the mobile capabilities
     * @return DesiredCapabilities
     * @throws URISyntaxException
     * @throws IOException
     */
    public static DesiredCapabilities getCaps() throws URISyntaxException, IOException {

        DesiredCapabilities caps = new DesiredCapabilities();
        String text = new String(Files.readAllBytes(Paths.get(DriverFactory.class.getResource("/configuration/capabilities.json").toURI())), StandardCharsets.UTF_8);
        JSONObject acceis = new JSONObject(text);

        // TODO: adapt the capabilities according to the situation:
        // - the application needs to be reinstalled (app instead of appPackage and appActivity)
        // - test on an emulator/simulator or on a real device (deviceName)
        // - the platform (Android / iOS) and some caps for the // tests: udid and wdaLocalPort for iOS
//        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, acceis.get("platformName"));
//        caps.setCapability(MobileCapabilityType.VERSION, acceis.get("platformVersion"));
//        caps.setCapability(MobileCapabilityType.DEVICE_NAME, acceis.get("deviceName"));
        caps.setCapability("appActivity", acceis.get("appActivity"));
        caps.setCapability("appPackage", acceis.get("appPackage"));
        //caps.setCapability(MobileCapabilityType.APP, acceis.get("appLocation"));

        return caps;
    }

}
