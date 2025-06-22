package day3;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class AuthenticationsDemo {

	//1. Basic Authentication.

	//@Test()
	void verifyBasicAuth()
	{
		given()
		.auth().basic("postman", "password")

		.when()
		.get("https://postman-echo.com/basic-auth")

		.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.log().body();

	}


	//2. Basic-preemptive.

	//@Test()
	void verifyBasicPreemptiveAuth()
	{
		given()
		.auth().preemptive().basic("postman", "password")

		.when()
		.get("https://postman-echo.com/basic-auth")

		.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.log().body();

	}

	//3. Bearer-Token Authentication.

	//@Test()
	void verifyBearrer_Token_Auth()
	{  
		
		String bearer_token="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";
		given()
		     .header("Authorization","Bearer " + bearer_token )


		.when()
		.get("https://api.github.com/user/repos")

		.then()
		.statusCode(200)
		.log().body();

	}


	//4. Digest Authentication.

	//@Test()
	void verifyDigestAuth()
	{
		given()
		.auth().digest("postman", "password")

		.when()
		.get("https://postman-echo.com/basic-auth")

		.then()
		.statusCode(200)
		.body("authenticated", equalTo(true))
		.log().body();

	}
	
	//5. API-KEY Authentication.

		@Test()
		void verifyAPIkeyAuth()
		{
			given()
			     .queryParam("q", "Amritsar")
			     .queryParam("appid", "310de8632d6434220c703019cc4667a7")
			

			.when()
			.get("https://api.openweathermap.org/data/2.5/weather")

			.then()
			.statusCode(200)
			.log().body();

		}





}
