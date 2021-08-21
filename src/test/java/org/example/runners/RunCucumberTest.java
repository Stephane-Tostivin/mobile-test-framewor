package org.example.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
        features = {"src/test/resources/org/example/features"},
        glue = {"org.example.stepdefinitions", "org.example.utility"},
         plugin = {"pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "rerun:target/failed-rerun.txt"
        },
        monochrome = true
)

@Test
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}


