package day9;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/*
 
http://localhost:3000/employees
http://localhost:3000/employees/1

http://localhost:3000/employees?last_name=King
http://localhost:3000/employees?first_name=Steven

http://localhost:3000/employees?gender=Female
http://localhost:3000/employees?gender=Male

 
 */



public class RequestAndResponseSpecification {
	
	
	RequestSpecification httpRequest;
	ResponseSpecification httpResponse;
	
	@BeforeClass
	void setup() 
	{
		// Create  request specification Builder
		
		RequestSpecBuilder reqBuilder=new RequestSpecBuilder();
		reqBuilder.setBaseUri("http://localhost:3000");
		reqBuilder.setBasePath("employees");
		
		httpRequest=reqBuilder.build();
		
		//Create response specification Builder
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resBuilder.expectHeader("Content-Type", equalTo("application/json"));
		
		
		httpResponse=resBuilder.build();
	

	
	}

	@Test(priority=1)

	void getAllEmployess()
	{
		given()
             .spec(httpRequest)
		.when()
		     .get()

		.then()
		   .spec(httpResponse)
		   .log().body()
		   .body("size()",greaterThan(0));

	}


	@Test(priority=2)

	void getMaleEmployess()
	{
		given()
		   .spec(httpRequest)
		   .queryParam("gender", "Male")
		   

		.when()
		    .get()

		.then()
		.spec(httpResponse)
		.log().body()
		.body("gender", everyItem(equalTo("Male")));

	}
	
	
	@Test(priority=4)

	void getFirstname()
	{
		given()
		.queryParam("first_name", "Steven")
		.spec(httpRequest)

		.when()
		.get()

		.then()
		.spec(httpResponse)
		.log().body()
		.body("first_name", everyItem(equalTo("Steven")));

	}
	

	@Test(priority=5)

	void getLastname()
	{
		given()
		.queryParam("last_name", "King")
		.spec(httpRequest)

		.when()
		.get()

		.then()
		.spec(httpResponse)
		.log().body()
		.body("last_name", everyItem(equalTo("King")));

	}
	
	
	@Test(priority=6)

	void getEmployeesbyId()
	{
		given()
		
		.spec(httpRequest)

		.when()
		.get("/1")

		.then()
		.spec(httpResponse)
		.log().body()
		.body("id",equalTo("1"));

	}
	
	
	@Test(priority=3)

	void getFemaleEmployess()
	{
		given()
		.queryParam("gender", "Female")
		.spec(httpRequest)

		.when()
		.get()

		.then()
		.spec(httpResponse)
		.log().body()
		.body("gender", everyItem(equalTo("Female")));

	}

}
