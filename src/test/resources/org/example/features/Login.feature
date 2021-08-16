@LOGIN
Feature: Login
  This is where we test the login to the Eribank mobile application

  @SC1 @SMOKE
  Scenario Outline: login with correct credentials
    Given I open the application
    When I enter  username <username> with password <password>
    And I click on button login
    Then I should be connected

    Examples:
      |username|password|
      |"company"|"company"|


  @SC2
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