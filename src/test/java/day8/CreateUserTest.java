package day8;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;

public class CreateUserTest {

	static final String Base_URL="https://gorest.co.in/public/v2/users";
	static final String Bearer_Token="a200d225a2009ee5db16518e5251e0c0d9f15c3f99c5d7f5cd3535a46a3304d2";

	int userId ;

	Faker faker=new Faker();

	@Test()
	void createUser(ITestContext context) {


		JSONObject requestData=new JSONObject();
		requestData.put("name",faker.name().fullName());
		requestData.put("gender","Male");
		requestData.put("email",faker.internet().emailAddress());
		requestData.put("status","inactive");


		userId=given()
				.headers("Authorization", "Bearer "+Bearer_Token)
				.contentType("application/json")
				.body(requestData.toString())
				.when()
				.post(Base_URL)
				.then()
				.statusCode(201)
				//.log().body()
				.extract().response().jsonPath().getInt("id");
		       
		context.setAttribute("userId", userId);
	}




}
