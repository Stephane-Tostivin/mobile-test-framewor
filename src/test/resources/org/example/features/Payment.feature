@PAYMENT
Feature: Payment
  In order to make a payment through the application
  As a valid customer user
  I need to define an amount in the payment screen and validate it

  Background:
    Given I am logged in as a valid user

  @SC1 @SMOKE
  Scenario Outline: payment with valid amount
    # Maybe the testing of various amounts should be tested through API as it is more a back-office issue
    Given the initial balance is <initialBalance>
    Given I am in the payment screen
    When I make a payment of <amount> with parameters:
    | name      | phoneNumber | country |
    | Andre     | 0600000000  | France  |
    And I confirm the payment
    Then the accountBalance should be updated to <updatedBalance>

    Examples:
    | initialBalance | amount | updatedBalance  |
    | 100.00         | 50     | 50.00           |

  @SC2
  Scenario Outline: payment with various amounts
    # Maybe the testing of various amounts should be tested through API as it is more a back-office issue
    Given the initial balance is <initialBalance>
    Given I am in the payment screen
    When I make a payment of <amount> with parameters:
      | name      | phoneNumber | country |
      | Andre     | 0600000000  | France  |
    And I confirm the payment
    Then the accountBalance should be updated to <updatedBalance>

    Examples:
      | initialBalance | amount | updatedBalance  |
      | 100.00         | 0      | 100.00          |
      | 100.00         | 100    | 0.00            |
      | 100.00         | 150    | -50.00          |
      | 100.00         | 100000 | -99900.00       |