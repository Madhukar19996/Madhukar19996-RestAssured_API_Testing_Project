package day8;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class UpdateUserTest {

	static final String Base_URL="https://gorest.co.in/public/v2/users";
	static final String Bearer_Token="a200d225a2009ee5db16518e5251e0c0d9f15c3f99c5d7f5cd3535a46a3304d2";



	@Test(dependsOnMethods= {"day8.GetUserTest.GetUser"})
	void UpdateUser(ITestContext context) {

		Faker faker=new Faker();

		JSONObject requestData=new JSONObject();
		requestData.put("name",faker.name().fullName());
		requestData.put("gender","Male");
		requestData.put("email",faker.internet().emailAddress());
		requestData.put("status","active");



		given()
		.headers("Authorization", "Bearer "+Bearer_Token)
		.contentType("application/json")
		.pathParam("id",(Integer)context.getAttribute("userId"))
		.body(requestData.toString())

		.when()
		.put(Base_URL+"/{id}")

		.then()
		.statusCode(200)
		.log().body();


	}
}
