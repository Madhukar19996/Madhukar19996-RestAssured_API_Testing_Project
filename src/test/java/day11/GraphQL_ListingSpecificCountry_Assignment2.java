package day11;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GraphQL_ListingSpecificCountry_Assignment2 {

	@Test(priority = 1)
	public void testListingCountry() {
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"{ country(code: \\\"IN\\\") { name native capital phone emoji currency languages { code name } }}\"\r\n"
				+ "}";

		given()
		.contentType(ContentType.JSON)
		.body(graphqlQuery)
		.when()
		.post("https://countries.trevorblades.com/graphql")
		.then()
		.statusCode(200)
		.log().body()
		.body("data.country.name", equalTo("India"))
		.body("data.country.capital", equalTo("New Delhi"))
		.body("data.country.currency", equalTo("INR"))
		.body("data.country.languages[0].name", equalTo("Hindi"));

	}

}
