package day11;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

public class GraphQL_ListingAllCountries_Assignment {

	@Test(priority = 1)
	public void testListingCountries() {
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"query Countries { countries { capital currency name }}\"\r\n"
				+ "}";

		ResponseBody responseBody=given()
				.contentType(ContentType.JSON)
				.body(graphqlQuery)
				.when()
				.post("https://countries.trevorblades.com/graphql")
				.then()
				.statusCode(200)
				.extract().response().body();

		JsonPath jsonpath=new JsonPath(responseBody.asString());

		//1) Find out how many countries data displayed
		int noOfCountries=jsonpath.getInt("data.countries.size()");
		System.out.println("Number of countries : "+noOfCountries);
		assertThat(noOfCountries, is(250));


		//2) Display each country, capital and currency
		for(int i=0; i<noOfCountries;i++)
		{
			String countryName=jsonpath.getString("data.countries["+i+"].name");
			String capital=jsonpath.getString("data.countries["+i+"].capital");
			String currency=jsonpath.getString("data.countries["+i+"].currency");
			System.out.println(countryName+"  "+capital+" "+currency);
		}

		//3) Check particular cuountry is exist in the response
		boolean status=false;
		for(int i=0; i<noOfCountries;i++)
		{
			String countryName=jsonpath.getString("data.countries["+i+"].name");
			if(countryName.equals("India"))
			{
				status=true;
				break;
			}
		}
		assertThat(status, is(true));

	}

}
