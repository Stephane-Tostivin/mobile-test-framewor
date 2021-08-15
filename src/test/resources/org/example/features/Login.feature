@login
Feature: Login
  This is where we test the login to the Eribank mobile application

  @smoke
  Scenario Outline: login with correct credentials
    Given I open the application
    When I enter  username <username> with password <password>
    And I click on button login
    Then I should be connected

    Examples:
      |username|password|
      |"company"|"company"|


  Scenario Outline: login with incorrect credentials
    Given I open the application
    When I enter  username <username> with password <password>
    And I click on button login
    Then an error login message should be displayed

    Examples:
      |username|password|
      |"dummyUser"|"dummyPassword"|
      |"123"      |"123"          |
      |""         |""             |