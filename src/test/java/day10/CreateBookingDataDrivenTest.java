package day10;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class CreateBookingDataDrivenTest {

	@DataProvider(name = "bookingData")
	public Object[][] bookingTestData() {
		return new Object[][]{
			{"Jim", "Brown", 111, true, "2018-01-01", "2019-01-01", "Breakfast"},
			{"Lisa", "Smith", 150, false, "2022-05-01", "2022-05-15", "Lunch"},
			{"Tom", "Hanks", 200, true, "2023-03-10", "2023-03-20", "Dinner"}
		};
	}

	@Test(dataProvider = "bookingData")
	public void createBookingTest(String firstname, String lastname, int totalprice,
			boolean depositpaid, String checkin, String checkout, String additionalneeds) {

		Map<String, Object> bookingdates = new HashMap<>();
		bookingdates.put("checkin", checkin);
		bookingdates.put("checkout", checkout);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("firstname", firstname);
		requestBody.put("lastname", lastname);
		requestBody.put("totalprice", totalprice);
		requestBody.put("depositpaid", depositpaid);
		requestBody.put("bookingdates", bookingdates);
		requestBody.put("additionalneeds", additionalneeds);

		given()
		.baseUri("https://restful-booker.herokuapp.com")
		.header("Content-Type", "application/json")
		.body(requestBody)
		.when()
		.post("/booking")
		.then()
		.statusCode(200)
		.body("booking.firstname", equalTo(firstname))
		.body("booking.lastname", equalTo(lastname))
		.body("booking.totalprice", equalTo(totalprice))
		.body("booking.depositpaid", equalTo(depositpaid))
		.body("booking.bookingdates.checkin", equalTo(checkin))
		.body("booking.bookingdates.checkout", equalTo(checkout))
		.body("booking.additionalneeds", equalTo(additionalneeds));
	}
	
	
	

}
