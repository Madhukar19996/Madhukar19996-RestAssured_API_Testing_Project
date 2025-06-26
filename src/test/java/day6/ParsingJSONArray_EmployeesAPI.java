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

public class ParsingJSONArray_EmployeesAPI {

	@Test()
	void testJsonResponseBody()
	{
		ResponseBody responseBody=given()

				.when()
				.get("http://localhost:3000/employees")
				.then()
				.statusCode(200)
				.extract().response().body();

		JsonPath jsonpath=new JsonPath(responseBody.asString());

		//Get the size of JSON Array 
		int empCount=jsonpath.getInt("size()");

		//Print all the details of the emp.
		for(int i=0; i<empCount;i++)
		{
			String firstname= jsonpath.getString("["+i+"].first_name");
			String lastname= jsonpath.getString("["+i+"].last_name");
			String email= jsonpath.getString("["+i+"].email");
			String gender= jsonpath.getString("["+i+"].gender");
			System.out.println(firstname+" "+lastname+" "+email+" "+gender);
		}
		
		//Search an Employee name "Virat" in the list 
		boolean status=false;
		
		for(int i=0; i<empCount;i++)
		{
			String firstname= jsonpath.getString("["+i+"].first_name");
			
			if(firstname.equals("virat"))
			{
				status=true;
				break;
			}
		}
		
		assertThat(status,is(true)); //Virat exists in the list or not .

	}



}
