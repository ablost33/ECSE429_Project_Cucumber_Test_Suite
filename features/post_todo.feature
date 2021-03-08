#Author: alexander.blostein@mail.mcgill.ca
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@tag
Feature: Create a new Todo
  As a user, I want to complete post requests in order to create new Todos.

  @tag1
  Scenario Outline: Create a todo with all fields
    Given the URL http://localhost:4567/todos
    When user creates a todo with "<title>", <doneStatus> and "<description>"
    And passes that todo in a post request
		Then success error code is returned
		And object body is returned
    And todo list state is restored by deleting newly created todo 
    
    Examples: 
      |id | title  | doneStatus | description  |
      |78 | Go outside | false | Need air |
      |97 | Visit grandparents | false | it's covid  |

  @tag2
  Scenario Outline: Create a todo with only title
    Given the URL http://localhost:4567/todos
    When user creates a todo with "<title>"
    And passes that todo in a post request
    Then success error code is returned
    And object body is returned
    And todo list state is restored by deleting newly created todo 

    Examples: 
      | title  | 
      | clean room | 
      | buy groceries |
      | Use bathroom |
      
  @tag3
  Scenario Outline: Create a todo without a title
    Given the URL http://localhost:4567/todos
    When user creates a todo with <doneStatus> and "<description>"
    And passes that todo in a post request
    Then error code displayed
    And error message is returned

    Examples: 
     | doneStatus | description  |
     | false | pickup all my stuff |
     | false | need food   |
     | false | Go downstairs and do it |
