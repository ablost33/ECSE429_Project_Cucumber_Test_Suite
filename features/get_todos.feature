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

Feature: Get specific todo by ID
  As a user, I want to be able to perform get requests to retrieve a specific todo

  
  Scenario: Retrieving specific todo by ID in XML
  	Given XML headers are set 
		And user performs GET with header request on the url: http://localhost:4567/todos
		Then the requested todo is returned in XML Format
		And success code is returned

  
  Scenario: Retrieving specific todo by ID in JSON
  	Given id of a specific todo to query
		And user performs GET request on the url: http://localhost:4567/todos/:id	 
		Then the requested todo is returned
		And success code is returned
		
	Scenario: Retrieving specific todo with invalid ID
  	Given invalid id of a todo
		When users want to get that specific instance of todos
		And user performs GET request on the url: http://localhost:4567/todos/:id	 
		Then error code is returned
		And errorMessage is displayed