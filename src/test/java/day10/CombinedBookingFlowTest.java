package day10;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;


public class CombinedBookingFlowTest {


	// Step 1: DataProvider for auth credentials
	@DataProvider(name = "bookingFlowData")
	public Object[][] bookingFlowDataProvider() {
		return new Object[][]{
			{"admin", "password123", "Jim", "Brown", 111, true, "2018-01-01", "2019-01-01", "Breakfast"},
			{"admin", "password123", "Lisa", "Smith", 200, false, "2022-05-01", "2022-05-10", "Lunch"}
		};
	}

	@Test(dataProvider = "bookingFlowData")
	public void fullBookingFlow(String username, String password,
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
		.statusCode(201);  // As per API, success response is 201

		System.out.println("Booking ID " + bookingId + " deleted successfully.");
	}
}


