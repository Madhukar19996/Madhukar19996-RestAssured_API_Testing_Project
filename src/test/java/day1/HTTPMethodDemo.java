package day1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

/*
   Pre-Condition : --- given()---> content type, set cookies, add auth, add param,set headers info etc...

   Action/steps : ---- when() --> get , post, put, delete

   Validation : ------- then() --> validate status code , extract response, extract headers cookies & response body ... 

 */

/*
 Validations:

  Status Code 
  Response Body
  Response Time 
  Content-Type
  Response String 

 */


public class HTTPMethodDemo {
	  
	int userId;

	//1) Test to retrieve users and validate the response .
	@Test(priority=1,enabled=true)
	void getUsers()
	{
	  given()
	  .when()
	       .get("https://reqres.in/api/users?page=2")
	  .then()
	        .statusCode(200)
	        .body("page", equalTo(2))
	        .body(containsString("email"))
	        .header("Content-Type",equalTo("application/json; charset=utf-8"))
	        .time(lessThan(2000L))
	        .log().all();
	        

	}
	
	
	// 2) Test to create new users and validate the response .
		@Test(priority=2)
		void createUsers()
		{
			HashMap<String,String> data=new HashMap<String,String>();
			data.put("name","Ayush" );
			data.put("job","tester" );
			
			
		  userId=given()
		        .contentType("application/json")
		        .header("x-api-key", "reqres-free-v1")
		        .body(data)
		  .when()
		       .post("https://reqres.in/api/users")
		  .then()
		        .statusCode(201)
		        .header("Content-Type",equalTo("application/json; charset=utf-8"))
		        .time(lessThan(4000L))
		        .body("name", equalTo("Ayush"))
		        .body("job", equalTo("tester"))
		        .body(containsString("id"))
		        .log().all()
		        .extract().jsonPath().getInt("id");

		}
		
		
		// 3) Test to update existing user and validate the response .
				@Test(priority=3,dependsOnMethods={"createUsers"})
				void updateUsers()
				{
					HashMap<String,String> data=new HashMap<String,String>();
					data.put("name","Ayush" );
					data.put("job","Developer" );
					
					
				  given()
				        .contentType("application/json")
				        .header("x-api-key", "reqres-free-v1")
				        .body(data)
				  .when()
				       .put("https://reqres.in/api/users/"+ userId)
				  .then()
				        .statusCode(200)
				        .header("Content-Type",equalTo("application/json; charset=utf-8"))
				        .time(lessThan(4000L))
				        .body("name", equalTo("Ayush"))
				        .body("job", equalTo("Developer"))
				        
				        .log().all();

				}
				
				// 3) Test to delete existing user and validate the response .
				@Test(priority=4,dependsOnMethods={"createUsers","updateUsers"})
				void deleteUsers()
				{
				
					
					
				  given()
				        
				        .header("x-api-key", "reqres-free-v1")
				        
				  .when()
				       .delete("https://reqres.in/api/users/"+ userId)
				  .then()
				        .statusCode(204)
				        
				        .time(lessThan(4000L))
				        .body(emptyOrNullString())
				        .log().all();

				}
		








}
