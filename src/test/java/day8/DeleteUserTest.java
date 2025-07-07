package day8;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteUserTest {
     
	static final String Base_URL="https://gorest.co.in/public/v2/users";
	static final String Bearer_Token="a200d225a2009ee5db16518e5251e0c0d9f15c3f99c5d7f5cd3535a46a3304d2";
	
	@Test(dependsOnMethods= {"day8.UpdateUserTest.UpdateUser"})
	void deleteUser(ITestContext context ) {





		given()
		.headers("Authorization", "Bearer "+Bearer_Token)

		.pathParam("id", (Integer)context.getAttribute("userId"))

		.when()
		.delete(Base_URL+"/{id}")

		.then()
		.statusCode(204)
		.log().body();


	}
}