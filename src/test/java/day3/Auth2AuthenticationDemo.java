package day3;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


/*
  curl -v -X POST "https://api-m.sandbox.paypal.com/v1/oauth2/token" \
 -u "CLIENT_ID:CLIENT_SECRET" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "grant_type=client_credentials"

##Postman:
==========
1.Set the verb to POST.
2.Enter https://api-m.sandbox.paypal.com/v1/oauth2/token as the request URL.
3.Select the Authorization tab.
4.From the TYPE list, select Basic Auth.
5.In the Username field, enter your client ID.
6.In the Password field, enter your secret.
7.Select the Body tab.
8.Select the x-www-form-urlencoded option.
9.In the KEY field, enter grant_type.
10.In the VALUE field, enter client_credentials.
11.Select Send.

curl -v -X GET https://api-m.sandbox.paypal.com/v1/invoicing/invoices?page=3&page_size=4&total_count_required=true\
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer ACCESS-TOKEN"

 */
public class Auth2AuthenticationDemo {

	@Test
	void verifyOAuth2Authentication() {

		String clientId = "AToMU8MHkVA_zXs-VGUKjNtwrq4ollVrrABId8FTNAwT48a73lU30OMyykRyrhDK6jKUbwtCnkcC-4F6";
		String clientSecret = "EMsY7AecCvWEWN5TpLAWravPHLcrAcciYMdHSCriGAWXAi7s14T_2FVB0wn4fAJKVEcW687N2X82RzoJ";
		
		String token=given()
				.auth().preemptive().basic(clientId, clientSecret)
				.param("grant_type", "client_credentials")

				.when()
				.post("https://api-m.sandbox.paypal.com/v1/oauth2/token")
				.then()
				.statusCode(200)
				//.log().body()
				.extract().jsonPath().getString("access_token");
		System.out.println("Generated token: "+token);

		given()
		//.header("Authorization","Bearer " + token  )
		.auth().oauth2(token)   // OAuth2 authentication
		.queryParam("page", "3")
		.queryParam("page_size", "4")
		.queryParam("total_count_required", "true")

		.when()
		.get("https://api-m.sandbox.paypal.com/v1/invoicing/invoices?page=3&page_size=4&total_count_required=true")
		.then()
		.statusCode(200)
		.log().body();








	}

}


