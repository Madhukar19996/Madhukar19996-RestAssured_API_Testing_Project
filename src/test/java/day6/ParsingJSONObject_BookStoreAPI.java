package day6;

import org.testng.annotations.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;



public class ParsingJSONObject_BookStoreAPI {
	
	@Test()
	void testJsonResponseBody()
	{
		 ResponseBody responseBody=given()
		
		
		.when()
		     .get("http://localhost:3000/store")
		
		.then()
		     .statusCode(200)
		     .extract().response().body();
		
		JsonPath jsonpath=new JsonPath(responseBody.asString());

		//Get the size of JSON Array 
		int bookCount=jsonpath.getInt("book.size()");
		
		//Validate at at least one book should be in store
		
		assertThat(bookCount,greaterThan(0));
		
		//Capture all the title of the book in a store
		for(int i=0;i<bookCount;i++)
		{
			String booktitle=jsonpath.getString("book["+i+"].title");
			System.out.println(booktitle);
		}
		
		
		// Search for specific book 
		boolean status=false;
		for(int i=0;i<bookCount;i++)
		{
			String booktitle=jsonpath.getString("book["+i+"].title");
			
			if(booktitle.equals("Moby Dick"))
			{
				status=true;
				break;
			}
		}
		assertThat(status,is(true));
		
		//calculate and validate the total price of all the books 
		double totalprice=0;
		for(int i=0;i<bookCount;i++)
		{
			double price=jsonpath.getDouble("book["+i+"].price");
			totalprice=price+totalprice;
			
			
		}
		System.out.println("Total Price of the Book :"+totalprice);
		
		assertThat(totalprice,is(53.92));
		     
				
				
				
	}

	
}

