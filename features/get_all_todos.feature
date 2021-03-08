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

Feature: Get all todos
  As a user, I want to be able to perform get requests to view all the todos stored in the todo manager 

  
  Scenario: Retrieving all todos in XML format
  	Given XML headers are set 
		And user performs GET with header request on url: http://localhost:4567/todos
		Then all todos are returned in XML Format
		And success code is returned

  
  Scenario: Retrieving specific todo by ID in JSON
  	Given user performs GET request on the url: http://localhost:4567/todos/ 
		Then all todos are returned
		And success code is returned
		
	Scenario: Retrieving specific todo with invalid url
  	Given invalid url
		And user performs GET request on the url
		Then error code is returned