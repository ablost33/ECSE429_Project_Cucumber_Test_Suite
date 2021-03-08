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

public class post_todo_Steps {
	
	private String url = null;
	private JSONObject body = null;
	private String title = null;
	private String description = null;
	Response response = null;
	
	
	@Given("the URL http:\\/\\/localhost:{int}\\/todos")
	public void the_url_http_localhost_todos(Integer int1) {
	    url = "http://localhost:4567/todos";
	}

	@When("user creates a todo with {string}, false and {string}")	
	public void user_creates_a_todo_with_and(String string, String string3) {
		// Create JSON body
		body = new JSONObject();
		body.put("title", string);
		body.put("doneStatus", false);
		body.put("description", string3);
		title = string;
		description = string3;
	}
	
	@When("user creates a todo with {string}")
	public void user_creates_a_todo_with_string(String string) {
		// Create JSON body
		body = new JSONObject();
		body.put("title", string);
		title = string;
		description = "";
	}

	@When("passes that todo in a post request")
	public void passes_that_todo_in_a_post_request() {
		response = RestAssured.given().contentType(ContentType.JSON)
				.body(body.toString()).post(url);
	}

	@Then("success error code is returned")
	public void success_error_code_is_returned() {
		Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201, but it's not");		
	}

	@Then("object body is returned")
	public void object_body_is_returned() {
		SoftAssert softAssert = new SoftAssert();
		
		String titleLocal = response.jsonPath().getString("title");
		softAssert.assertEquals(titleLocal, title);
		String des = response.jsonPath().getString("description");
		softAssert.assertEquals(des, "pickup all my stuff", "You have the wrong description");
		String bool = response.jsonPath().getString("doneStatus");
		softAssert.assertEquals(bool, "false", "doneStatus in response is not expected");

		System.out.println("=======> Printing Added Todo: ");
		System.out.println("Title: " + title);
		System.out.println("Description: " + description);

	}
	
	@When("user creates a todo with false and {string}")
	public void user_creates_a_todo_with_false_and(String string) {
		// Create JSON body
		body = new JSONObject();
		body.put("doneStatus", false);
		body.put("description", string);
		description = string;
	}

	@Then("error code displayed")
	public void error_code_displayed() {
		Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, but it's not");	
	}

	@Then("error message is returned")
	public void error_message_is_returned() {
		ResponseBody body1 = response.getBody();
		String bodyAsString = body1.asString(); 
		Assert.assertEquals(bodyAsString.contains("\"errorMessages\":[\"title : field is mandatory\"]"), true, "Response body contains correct error code");
	}

	@Then("todo list state is restored by deleting newly created todo")
	public void todo_list_state_is_restored() {
		String iden = response.jsonPath().getString("id");
		RestAssured.given().delete("http://localhost:4567/todos/" + iden);
	}


}
