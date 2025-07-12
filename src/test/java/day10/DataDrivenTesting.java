package day10;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class DataDrivenTesting {



	@Test(dataProvider="excelDataProvider", dataProviderClass=DataProviders.class)
	public void testWithExcelData(String username, String password,
			String firstname, String lastname, int totalprice, boolean depositpaid,
			String checkin, String checkout, String additionalneeds)
	{
		fullBookingFlow(username,password,firstname,lastname,totalprice,depositpaid,checkin,checkout,additionalneeds);
	}


	//@Test(dataProvider="jsonDataProvider", dataProviderClass=DataProviders.class)
	public void testWithJsonData(Map<String,String> data)
	{
		fullBookingFlow(
				data.get("username"),
				data.get("password"),
				data.get("firstname"),
				data.get("lastname"),
				Integer.parseInt(data.get("totalprice")),
				Boolean.parseBoolean(data.get("depositpaid")),
				data.get("checkin"),
				data.get("checkout"),
				data.get("additionalneeds")
				);
	}
	
	//@Test(dataProvider = "csvDataProvider",dataProviderClass=DataProviders.class)
	public void testWithCSVData(String username, String password, String firstname, String lastname,
	                            int totalprice, boolean depositpaid, String checkin, String checkout,
	                            String additionalneeds) {
	    
	
		{
			fullBookingFlow(username,password,firstname,lastname,totalprice,depositpaid,checkin,checkout,additionalneeds);
		}
	}




	//fullBookingFlow
	void fullBookingFlow(String username, String password,
			String firstname, String lastname, int totalprice, boolean depositpaid,
			String checkin, String checkout, String additionalneeds) {

		// ---------------------
		// Step 1: Get Auth Token
		// ---------------------
		Map<String, Object> authBody = new HashMap<>();
		authBody.put("username", username);
		authBody.put("password", password);

		String token =
				given()
				.baseUri("https://restful-booker.herokuapp.com")
				.header("Content-Type", "application/json")
				.body(authBody)
				
				.when()
				.post("/auth")
				.then()
				.statusCode(200)
				//.log().body()
				.extract()
				.path("token");

		System.out.println("Generated Token: " + token);

		// ---------------------
		// Step 2: Create Booking
		// ---------------------
		Map<String, Object> bookingDates = new HashMap<>();
		bookingDates.put("checkin", checkin);
		bookingDates.put("checkout", checkout);

		Map<String, Object> bookingBody = new HashMap<>();
		bookingBody.put("firstname", firstname);
		bookingBody.put("lastname", lastname);
		bookingBody.put("totalprice", totalprice);
		bookingBody.put("depositpaid", depositpaid);
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", additionalneeds);

		int bookingId =
				given()
				.baseUri("https://restful-booker.herokuapp.com")
				.header("Content-Type", "application/json")
				.body(bookingBody)
				
				.when()
				.post("/booking")
				
				.then()
				.statusCode(200)
				.body("booking.firstname", equalTo(firstname))
				.body("booking.lastname", equalTo(lastname))
				.extract()
				
				.path("bookingid");

		System.out.println("Created Booking ID: " + bookingId);

		// ---------------------
		// Step 3: Delete Booking
		// ---------------------
		given()
		.baseUri("https://restful-booker.herokuapp.com")
		.header("Content-Type", "application/json")
		.header("Cookie", "token=" + token)
		
		.when()
		.delete("/booking/" + bookingId)
		.then()
	//	.log().body()
		.statusCode(201);  // As per API, success response is 201

		System.out.println("Booking ID " + bookingId + " deleted successfully.");
	}
}
