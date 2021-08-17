package org.example.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = {"@target/failed-rerun.txt"},
        glue = {"org.example.stepdefinitions", "org.example.utility"},
        plugin = {"pretty",
                "html:target/cucumber-report.html",
                "json:C:\\Jenkins\\JsonReports\\report.json",
                "rerun:target/failed-rerun.txt"
        }
)

@Test

public class RunFailedTest extends AbstractTestNGCucumberTests {
}
