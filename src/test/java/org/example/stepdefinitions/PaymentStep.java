package org.example.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.LoginPage;
import org.example.pages.MakePaymentPage;
import org.example.pages.PaymentHomePage;
import org.example.qa.factory.DriverFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentStep {

    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    private PaymentHomePage paymentHomePage;
    private MakePaymentPage makePaymentPage;
    private static Logger logger = Logger.getLogger(PaymentStep.class.getName());


    //------------------------------------------------------------------------------------------------------------
    // Step to login as a valid user, usually a prerequisite for every scenario
    //------------------------------------------------------------------------------------------------------------
    @Given("I am logged in as a valid user")
    public void i_am_logged_in_as_a_valid_user() {
        paymentHomePage = loginPage.doLogin();
    }


    //------------------------------------------------------------------------------------------------------------
    // Steps defined to test payments
    //------------------------------------------------------------------------------------------------------------
    @Given("the initial balance is {double}")
    public void the_initial_balance_is(Double expectedBalance) {
        if(paymentHomePage.isPaymentHomePage()) {
            String balanceMsg = paymentHomePage.getBalance();
            Assert.assertTrue(balanceMsg.contains(expectedBalance.toString()));
        }
    }

    @Given("I am in the payment screen")
    public void i_am_in_the_payment_screen() {
        makePaymentPage = paymentHomePage.clickMakePaymentButton();
        makePaymentPage.isMakePaymentPage();
    }

    @When("I make a payment of {int} with parameters:")
    public void i_make_a_payment_of_with_parameters(Integer amount, DataTable parameters) {
        // Transform the dataTable into strings
        List<Map<String, String>> params = parameters.asMaps();
        String name = params.get(0).get("name");
        String phoneNumber = params.get(0).get("phoneNumber");
        String country = params.get(0).get("country");

        // Make the payment with all the expected items
        logger.log(Level.INFO, "Make a payment of " + amount + "$ for " + name + " (tel.: " + phoneNumber + ") from " + country);
        makePaymentPage.setPhone(phoneNumber);
        makePaymentPage.setAmount(amount);
        makePaymentPage.setName(name);
        makePaymentPage.setCountry(country);

        // Send the payment
        makePaymentPage.clickSendPaymentButton();
    }

    @And("I confirm the payment")
    public void i_confirm_the_payment() {
        paymentHomePage = makePaymentPage.confirmPayment();
    }
    @Then("the accountBalance should be updated to {double}")
    public void the_account_balance_should_be_updated_to(Double expectedBalance) {
        if (paymentHomePage.isPaymentHomePage()) {
            String balanceMsg = paymentHomePage.getBalance();
            Assert.assertTrue(balanceMsg.contains(expectedBalance.toString()));
        }
    }

}
