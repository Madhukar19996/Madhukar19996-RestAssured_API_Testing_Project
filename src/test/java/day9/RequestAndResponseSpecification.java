package day9;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestAndResponseSpecification {
	
	@Test(priority=1)
	
	void getAllEmployess()
	{
		given()
		
		.when()
		     .get("http://localhost:3000/employees")
		
		.then()
		     .statusCode(200)
		     .log().body()
		     .body("size()",greaterThan(0));
	
	}
	
	
@Test(priority=2)
	
	void getMaleEmployess()
	{
		given()
		    .queryParam("gender", "Male")
		
		.when()
		     .get("http://localhost:3000/employees")
		
		.then()
		     .statusCode(200)
		     .log().body()
		     .body("gender", everyItem(equalTo("Male")));
	
	}

}
