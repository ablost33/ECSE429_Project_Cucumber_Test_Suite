package stepDefinitions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class get_todos_Steps {

	private RequestSpecification spec;
	private Response response;
	private String url = null;
	private int ID = 0;
	
	@When("users want to get all instances of todos")
	public void users_want_to_get_all_instances_of_todos() {
		url = "http://localhost:4567/todos";
	}

	@When("user performs GET request on the url: http:\\/\\/localhost:{int}\\/todos")
	public void user_performs_get_request_on_the_url_http_localhost_todos(Integer int1) {
		response = RestAssured.get(url);
	}

	@Then("all todos are returned")
	public void all_todos_are_returned() {
		SoftAssert softAssert = new SoftAssert();
		String title = response.jsonPath().getString("title");
		softAssert.assertEquals(title, "file paperwork", "Title in response is not expected");
		
		String iden = response.jsonPath().getString("id");
		softAssert.assertEquals(iden, "2", "ID in response is not expected");
		
		String bool = response.jsonPath().getString("doneStatus");
		softAssert.assertEquals(bool, "false", "doneStatus in response is not expected");
		
		String title2 = response.jsonPath().getString("title");
		softAssert.assertEquals(title2, "scan paperwork", "Title in response is not expected");
		
		String iden2 = response.jsonPath().getString("id");
		softAssert.assertEquals(iden2, "1", "ID in response is not expected");
		
		String bool2 = response.jsonPath().getString("doneStatus");
		softAssert.assertEquals(bool2, "false", "doneStatus in response is not expected");
	}

	@Then("success code is returned")
	public void success_code_is_returned() {
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
	}

	@Given("id of a specific todo to query")
	public void id_of_a_specific_todo_to_query() {
		ID = 2;
		url = "http://localhost:4567/todos/"+ID;
	}

	@When("users want to get that specific instance of todos")
	public void users_want_to_get_that_specific_instance_of_todos() {
	}

	@When("user performs GET request on the url: http:\\/\\/localhost:{int}\\/todos\\/:id")
	public void user_performs_get_request_on_the_url_http_localhost_todos_id(Integer int1) {
		response = RestAssured.get(url);
	}

	@Then("the requested todo is returned")
	public void the_requested_todo_is_returned() {
		SoftAssert softAssert = new SoftAssert();
		String title = response.jsonPath().getString("title");
		softAssert.assertEquals(title, "file paperwork", "Title in response is not expected");
		
		String iden = response.jsonPath().getString("id");
		softAssert.assertEquals(iden, "2", "ID in response is not expected");
		
		String bool = response.jsonPath().getString("doneStatus");
		softAssert.assertEquals(bool, "false", "doneStatus in response is not expected");
	}

	@Given("invalid id of a todo")
	public void invalid_id_of_a_todo() {
		ID = 2324;
		url = "http://localhost:4567/todos/"+ID;
	}

	@Then("error code is returned")
	public void error_code_is_returned() {
		Assert.assertEquals(response.getStatusCode(), 404, "Status code should be 404, but it's not");
	}

	@Then("errorMessage is displayed")
	public void error_message_is_displayed() {
		ResponseBody body = response.getBody();
		String bodyAsString = body.asString(); 
		Assert.assertEquals(bodyAsString.contains("Could not find an instance with todos"), true, "Response body contains correct error code");
	}
	
	@Given("XML headers are set")
	public void xml_headers_are_set() {
		spec = new RequestSpecBuilder().
				setBaseUri("http://localhost:4567").
				build();
		Header xml = new Header("Accept", "application/xml");
		spec.header(xml);
	}

	@When("user performs GET with header request on the url: http:\\/\\/localhost:{int}\\/todos")
	public void user_performs_get_with_header_request_on_the_url_http_localhost_todos(Integer int1) {
		response = RestAssured.given(spec).get("/todos/2");
	}

	@Then("the requested todo is returned in XML Format")
	public void the_requested_todo_is_returned_in_xml_format() {
		SoftAssert softAssert = new SoftAssert();
		String title = response.xmlPath().getString("title");
		softAssert.assertEquals(title, "file paperwork", "Title in response is not expected");
		
		String iden = response.xmlPath().getString("id");
		softAssert.assertEquals(iden, "2", "ID in response is not expected");
		
		String bool = response.xmlPath().getString("doneStatus");
		softAssert.assertEquals(iden, "false", "doneStatus in response is not expected");
	}
	
	@Given("user performs GET with header request on url: http:\\/\\/localhost:{int}\\/todos")
	public void user_performs_get_with_header_request_on_url_http_localhost_todos(Integer int1) {
		response = RestAssured.given(spec).get("/todos");
	}
	@Then("all todos are returned in XML Format")
	public void all_todos_are_returned_in_xml_format() {
		Response response = RestAssured.given(spec).get("/todos");
		SoftAssert softAssert = new SoftAssert();
		String title = response.xmlPath().getString("title");
		softAssert.assertEquals(title, "file paperwork", "Title in response is not expected");
		softAssert.assertEquals(title, "scan paperwork", "Title in response is not expected");
		String iden = response.xmlPath().getString("id");
		softAssert.assertEquals(iden, "2", "ID in response is not expected");
		softAssert.assertEquals(iden, "1", "ID in response is not expected");
		String bool = response.xmlPath().getString("doneStatus");
		softAssert.assertEquals(iden, "false", "doneStatus in response is not expected");
	}

	@Given("user performs GET request on the url: http:\\/\\/localhost:{int}\\/todos\\/")
	public void user_performs_get_request_on_the_url_http_localhost_todos1(Integer int1) {
		response = RestAssured.get("http://localhost:4567/todos");
	}
	
	@Given("invalid url")
	public void invalid_url() {
	    url = "http://localhost:4567/todo";
	}
	@Given("user performs GET request on the url")
	public void user_performs_get_request_on_the_url() {
		response = RestAssured.get(url);
	}








}
