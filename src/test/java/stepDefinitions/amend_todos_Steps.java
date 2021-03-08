package stepDefinitions;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;


public class amend_todos_Steps {
	
	private String DummyID;
	private String url;
	private JSONObject titleBody;
	private Response POSTresponse;
	
	@Given("dummy Todo to be amended")
	public void dummy_todo_to_be_amended() {
		JSONObject body = new JSONObject();
		body.put("title", "riatur. Excepteur si");
		body.put("doneStatus", false);
		body.put("description", "qui officia deserunt");
		Response putRes = RestAssured.given().contentType(ContentType.JSON)
				.body(body.toString()).post("http://localhost:4567/todos");
		DummyID = putRes.jsonPath().getString("id");
	}

	@Given("http:\\/\\/localhost:{int}\\/todos\\/:id")
	public void http_localhost_todos_id(Integer int1) {
	   url = "http://localhost:4567/todos/" + DummyID;
	}

	@When("I instantiate the title field")
	public void i_instantiate_the_title_field() {
		titleBody = new JSONObject();
		titleBody.put("title", "Modified Titled");
	}

	@When("passes that field in a post request to the Dummy todo")
	public void passes_that_field_in_a_post_request_to_the_dummy_todo() {
		POSTresponse = RestAssured.given().contentType(ContentType.JSON)
				.body(titleBody.toString()).post(url);
	}

	@Then("title is modified")
	public void title_is_modified() {
		SoftAssert softAssert = new SoftAssert();
		String modifTitle = POSTresponse.jsonPath().getString("title");
		softAssert.assertEquals(modifTitle, "Modified Titled");
	}
	
	@Then("amende success code is returned")
	public void amende_success_code_is_returned() {
		Assert.assertEquals(POSTresponse.getStatusCode(), 200, "Status code should be 200, but it's not");	
	}
	@Then("I delete dummy todo")
	public void i_delete_dummy_todo() {
		RestAssured.given().delete(url);
	}
	
	@When("passes that field in a put request to the Dummy todo")
	public void passes_that_field_in_a_put_request_to_the_dummy_todo() {
		POSTresponse = RestAssured.given().contentType(ContentType.JSON)
				.body(titleBody.toString()).put(url);
	}

	@When("I instantiate the value field")
	public void i_instantiate_the_value_field() {
		titleBody = new JSONObject();
		titleBody.put("value", "Modified Titled");
	}

	@Then("amend error code is returned")
	public void amend_error_code_is_returned() {
		Assert.assertEquals(POSTresponse.getStatusCode(), 400, "Status code should be 400, but it's not");	
	}

	@Then("amend error message is displayed")
	public void amend_error_message_is_displayed() {
		ResponseBody body1 = POSTresponse.getBody();
		String bodyAsString = body1.asString(); 
		Assert.assertEquals(bodyAsString.contains("\"errorMessages\":[\"Could not find field: value\"]"), true, "Response body does not correspond to expected result");
	}


}
