package org.example.runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
        features = {"src/test/resources/org/example/features"},
        tags= "not @skip and not @wip",
        glue = {"org.example.stepdefinitions", "org.example.utility"},
        plugin = {"pretty:target/cucumber-pretty.txt",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "rerun:target/failed-rerun.txt"
        }
)

@Test
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}


