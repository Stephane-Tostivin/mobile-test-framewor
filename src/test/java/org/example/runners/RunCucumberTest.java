package org.example.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
        features = {"src/test/resources/org/example/features"},
        glue = {"org.example.stepdefinitions", "org.example.utility"},
         plugin = {"pretty",
                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
                "rerun:target/failed-rerun.txt"
        },
        monochrome = true
)

@Test
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}


