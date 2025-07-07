package day8;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;

public class GetUserTest { 
	
	static final String Base_URL="https://gorest.co.in/public/v2/users";
	static final String Bearer_Token="a200d225a2009ee5db16518e5251e0c0d9f15c3f99c5d7f5cd3535a46a3304d2";

	@Test(dependsOnMethods= {"day8.CreateUserTest.createUser"})
	void GetUser(ITestContext context) {





		given()
		.headers("Authorization", "Bearer "+Bearer_Token)

		.pathParam("id", (Integer)context.getAttribute("userId"))

		.when()
		.get(Base_URL+"/{id}")

		.then()
		.statusCode(200)
		.log().body();


	}
}