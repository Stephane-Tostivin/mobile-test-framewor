package org.example.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {

    static Logger logger = Logger.getLogger(LoggerFactory.class.getName());

    /**
     * This method is required to configure the logger from a configuration file
     * @return logManager
     */
    public static LogManager configureLogger() {
        // Configuration Loggger
        LogManager logManager = LogManager.getLogManager();
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("configuration/logging.properties");
        try {
            logManager.readConfiguration(inputStream);
        } catch (SecurityException | IOException e) {
            logger.severe(e.getMessage());
        }
        return logManager;
    }

}
