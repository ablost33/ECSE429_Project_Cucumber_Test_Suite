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
Feature: Amend todo fields
  As as user, I want to be able to amend the fields of an existing TODO
  
Background:
	Given dummy Todo to be amended
	

  Scenario: amend TODO fields using POST
    Given http://localhost:4567/todos/:id
    When I instantiate the title field
    And passes that field in a post request to the Dummy todo
		Then amende success code is returned
		And title is modified
    And I delete dummy todo

 
  Scenario: amend TODO fields using PUT
    Given http://localhost:4567/todos/:id
    When I instantiate the title field
    And passes that field in a put request to the Dummy todo
		Then amende success code is returned
		And title is modified
    And I delete dummy todo
    
Scenario: amend invalid TODO fields using PUT
    Given http://localhost:4567/todos/:id
    When I instantiate the value field
    And passes that field in a post request to the Dummy todo
		Then amend error code is returned
		And amend error message is displayed
    And I delete dummy todo