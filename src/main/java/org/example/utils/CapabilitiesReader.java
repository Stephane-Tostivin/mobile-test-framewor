package org.example.utils;

import org.example.factory.DriverFactory;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapabilitiesReader {

    // CONSTANTS
    private static final String CAPABILITIES_FILE = "/configuration/capabilities.json";

    // Logger
    static Logger logger = Logger.getLogger(CapabilitiesReader.class.getName());

    /**
     * Read the capabilities file
     * @return a JSONObject with the content of the file
     */
    public static JSONObject getConfiguration() {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(DriverFactory.class.getResource(CAPABILITIES_FILE).toURI())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the file: " + CAPABILITIES_FILE);
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Error parsing the file: " + CAPABILITIES_FILE);
        }
        return new JSONObject(text);
    }


    /**
     * Get the target environment for tests
     * The target may be set from the system environment variable targetSUT
     * If not, the default value is "local"
     * @param verbose Verbose mode
     * @return the name of the target environment
     */
    private static String getTargetEnv(boolean verbose) {
        String targetEnv = System.getenv("targetSUT").toLowerCase();

        if (!targetEnv.equals("browserstack")) {
            // If system variable targetSUT is not defined or has not an expected value, default env is local
            targetEnv = "local";
        }

        if (verbose) {
            logger.log(Level.INFO, "Target test environment: " + targetEnv);
        }
        return targetEnv;
    }

    /**
     * Get the desired capabilities from the system env and/or the capabilities json file
     * The caps read in the capabilities file are taken into account
     * Except if a variable with the cap name is provided in the system environment, in that case it takes precedence
     * @return DesiredCapabilities
     */
    public static DesiredCapabilities getCaps(JSONObject config) {
        DesiredCapabilities caps = new DesiredCapabilities();

        // 1. Set the common capabilities
        JSONObject commonCapabilities = (JSONObject) config.get("capabilities");
        Map<String, Object> commonMap = commonCapabilities.toMap();
        Iterator it = commonMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (System.getenv(pair.getKey().toString()) == null) {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+pair.getValue().toString()+" (from "+CAPABILITIES_FILE+")");
                caps.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
            else {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+System.getenv(pair.getKey().toString())+" (from system environment)");
                caps.setCapability(pair.getKey().toString(), System.getenv(pair.getKey().toString()));
            }
        }

        // 2. Set the device capabilities
        JSONObject deviceCapabilities = (JSONObject) config.get("devices");
        Map<String, Object> deviceMap = deviceCapabilities.toMap();
        it = deviceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (System.getenv(pair.getKey().toString()) == null) {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+pair.getValue().toString()+" (from "+CAPABILITIES_FILE+")");
                caps.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
            else {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+System.getenv(pair.getKey().toString())+" (from system environment)");
                caps.setCapability(pair.getKey().toString(), System.getenv(pair.getKey().toString()));
            }
        }

        // 3. Set capabilities according to the target environment
        String targetEnv = getTargetEnv(true);
        JSONObject targetCapabilities = (JSONObject) config.get(targetEnv);
        Map<String, Object> targetMap = targetCapabilities.toMap();
        it = targetMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (System.getenv(pair.getKey().toString()) == null) {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+pair.getValue().toString()+" (from "+CAPABILITIES_FILE+")");
                caps.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
            else {
                logger.log(Level.INFO, "Capability "+pair.getKey().toString()+" = "+System.getenv(pair.getKey().toString())+" (from system environment)");
                caps.setCapability(pair.getKey().toString(), System.getenv(pair.getKey().toString()));
            }
        }

        return caps;
    }


    /**
     * Build the URL for the Appium server
     * @return the URL for the targeted Appium server
     */
    public static String getAppiumHubURL(JSONObject config) {
        String AppiumHubURL = null;
        String targetEnv = getTargetEnv(false); // The string has only 2 possible values: local or browserstack

        switch (targetEnv) {
            case "local":
                AppiumHubURL = "http://"+config.getJSONObject(targetEnv).getString("server")+"/wd/hub";
                break;

            case "browserstack":
                // Check if BROWSERSTACK_USERNAME is defined as system env variable
                // Otherwise we take the value in the capabilities files
                String username = System.getenv("BROWSERSTACK_USERNAME");
                if(username == null) {
                    username = config.getJSONObject(targetEnv).getString("username");
                }

                // Check if BROWSERSTACK_ACCESS_KEY is defined as system env variable
                // Otherwise we take the value in the capabilities files
                String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                if(accessKey == null) {
                    accessKey = config.getJSONObject(targetEnv).getString("access_key");
                }

                AppiumHubURL = "http://"+username+":"+accessKey+"@"+config.getJSONObject(targetEnv).getString("server")+"/wd/hub";
                break;
        }

        logger.log(Level.INFO, "URL of the Appium server: " + AppiumHubURL);
        return AppiumHubURL;
    }

}
