package day8;

import org.json.JSONObject;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;

public class ChainingAPIs {


	static final String Base_URL="https://gorest.co.in/public/v2/users";
	static final String Bearer_Token="a200d225a2009ee5db16518e5251e0c0d9f15c3f99c5d7f5cd3535a46a3304d2";

	int userId ;

	Faker faker=new Faker();

	@Test()
	void createUser() {


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
	}


	@Test(dependsOnMethods= {"createUser"})
	void GetUser() {





		given()
		.headers("Authorization", "Bearer "+Bearer_Token)

		.pathParam("id", userId)

		.when()
		.get(Base_URL+"/{id}")

		.then()
		.statusCode(200)
		.log().body();


	}


	@Test(dependsOnMethods= {"createUser","GetUser"})
	void UpdateUser() {

		JSONObject requestData=new JSONObject();
		requestData.put("name",faker.name().fullName());
		requestData.put("gender","Male");
		requestData.put("email",faker.internet().emailAddress());
		requestData.put("status","active");



		given()
		.headers("Authorization", "Bearer "+Bearer_Token)
		.contentType("application/json")
		.pathParam("id", userId)
		.body(requestData.toString())

		.when()
		.put(Base_URL+"/{id}")

		.then()
		.statusCode(200)
		.log().body();


	}

	@Test(dependsOnMethods= {"UpdateUser"})
	void deleteUser() {





		given()
		.headers("Authorization", "Bearer "+Bearer_Token)

		.pathParam("id", userId)

		.when()
		.delete(Base_URL+"/{id}")

		.then()
		.statusCode(204)
		.log().body();


	}

}
