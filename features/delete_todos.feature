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
Feature: Deleting information in Todos
  As a user, I want to perform DELETE operations on the endpoint in order to delete data stored in todos.

Background:
	Given dummy Todo to be deleted
	And adding that todo to our todo list 

  @tag1
  Scenario: Deleting a todo by its ID 
		Given the url http://localhost:4567/todos/:id
		When I delete a todo by it's ID
		Then a success code should be returned

  @tag2
  Scenario: Delete the instance of the relationship named tasksof between todo and project using :id
    Given A relationship is created between a task and a todo
    And the url http://localhost:4567/todos/:id/tasksof/1
    Then I delete that relationship
    And a success code should be returned
		And I get an empty message
		And I delete a todo by it's ID

  @tag1
  Scenario: Deleting a todo with an invalid
		Given the url http://localhost:4567/todos/:id
		When I delete a todo by wrong ID
		Then an error code is returned
		And I delete a todo by it's ID