package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.LoginPage;
import org.testng.Assert;

import java.io.IOException;

public class LoginStep {

    private LoginPage loginPage = new LoginPage();

    public LoginStep() throws IOException {
    }


    //------------------------------------------------------------------------------------------------------------
    // Steps defined to test the login
    //------------------------------------------------------------------------------------------------------------
    @Given("I open the application")
    public void i_open_the_application() {
        loginPage.isloginPage();
    }

    @When("I enter  username {string} with password {string}")
    public void i_enter_username_with_password(String username, String password) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
    }

    @When("I click on button login")
    public void i_click_on_button_login() {
        loginPage.clickLogin();
    }

    @Then("I should be connected")
    public void i_should_be_connected() {
        Assert.assertTrue(loginPage.isLogged());
    }

    @Then("an error login message should be displayed")
    public void an_error_login_message_should_be_displayed() {
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password!");
    }
}
