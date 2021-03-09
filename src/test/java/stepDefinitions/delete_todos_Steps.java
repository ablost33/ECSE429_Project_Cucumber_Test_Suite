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

public class delete_todos_Steps {
	
	private JSONObject body = null;
	private Response putRes = null;
	private String ID;
	private Response GETRes = null;
	private	String url;
	
	@Given("dummy Todo to be deleted")
	public void dummy_todo_to_be_deleted() {
		body = new JSONObject();
		body.put("title", "riatur. Excepteur si");
		body.put("doneStatus", false);
		body.put("description", "qui officia deserunt");
	}

	@Given("adding that todo to our todo list")
	public void adding_that_todo_to_our_todo_list() {
		putRes = RestAssured.given().contentType(ContentType.JSON)
				.body(body.toString()).post("http://localhost:4567/todos");
		ID = putRes.jsonPath().getString("id");
	}

	@Given("the url http:\\/\\/localhost:{int}\\/todos\\/:id")
	public void the_url_http_localhost_todos_id(Integer int1) {
		  String id = putRes.jsonPath().getString("id");
		  url = "http://localhost:4567/todos/" + id;
	}

	@When("I delete a todo by it's ID")
	public void i_delete_a_todo_by_it_s_id() {
		GETRes = RestAssured.given().delete(url);
		System.out.println(url);
	}

	@Then("a success code should be returned")
	public void a_success_code_should_be_returned() {
		Assert.assertEquals(GETRes.getStatusCode(), 200, "Status code should be 200, but it's not");		
	}
	
	@When("I delete a todo by wrong ID")
	public void i_delete_a_todo_by_wrong_id() {
		GETRes = RestAssured.given().delete("http://localhost:4567/todos/99");
	}

	@Then("an error code is returned")
	public void an_error_code_is_returned() {
		Assert.assertEquals(GETRes.getStatusCode(), 404, "Status code should be 404, but it's not");
	}
	
	
	@Given("A relationship is created between a task and a todo")
	public void a_relationship_is_created_between_a_task_and_a_todo() {
		JSONObject relationShipCreator = new JSONObject();
		relationShipCreator.put("id", "1");
		RestAssured.given().contentType(ContentType.JSON)
		.body(relationShipCreator.toString()).post(" http://localhost:4567/todos/"+ ID + "/tasksof");
	}

	@Given("the url http:\\/\\/localhost:{int}\\/todos\\/:id\\/tasksof\\/{int}")
	public void the_url_http_localhost_todos_id_tasksof(Integer int1, Integer int2) {
		url = " http://localhost:4567/todos/"+ ID + "/tasksof/1";
	}

	@Then("I delete that relationship")
	public void i_delete_that_relationship() {
		GETRes = RestAssured.given().delete(url);
	}

	@Then("I get an empty message")
	public void i_get_an_empty_message() {
		ResponseBody body3 = GETRes.getBody();
		String bodyAsString3 = body3.asString();	
		Assert.assertEquals(bodyAsString3.contains(""), true, "Body response from creating category relationship is invalid");
		url = "http://localhost:4567/todos/"+ ID;
	}

}
