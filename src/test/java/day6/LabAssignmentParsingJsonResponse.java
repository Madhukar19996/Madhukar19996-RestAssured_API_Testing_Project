package day6;

import org.testng.annotations.*;

import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;


public class LabAssignmentParsingJsonResponse {


	//User defined method --> This will read data from json ,convert to json response and return it .
	JSONObject getJSONResponse()
	{
		File myfile=new File(".\\src\\test\\resources\\simple.json");
		FileReader fileReader=null;
		try {
			fileReader=new FileReader(myfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONTokener jsonTokener=new JSONTokener(fileReader);
		JSONObject jsonResponse=new JSONObject(jsonTokener);
		return jsonResponse;
	}


	//1) Simple JSON Parsing
	
	@Test(priority=1)
	void testSimpleJsonParsingValidation()
	{   
		//Converting JSONObject--->JsonPath
		//String str =getJSONResponse().toString();
		//JsonPath json=new JsonPath (str);
		JsonPath jsonPath=new JsonPath (getJSONResponse().toString());

		//a) Verify the total number of orders is 2.
		String place_id = jsonPath.getString("place_id");
		assertThat(place_id, is("9172e95034d47483d8e0775710eb6a54"));
		System.out.println("place id :"+place_id);
	}

	
	
	//User defined method --> This will read data from json ,convert to json response and return it .
	JSONObject getJSONResponses()
	{
		File myfile=new File(".\\src\\test\\resources\\nested.json");
		FileReader fileReader=null;
		try {
			fileReader=new FileReader(myfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONTokener jsonTokener=new JSONTokener(fileReader);
		JSONObject jsonResponse=new JSONObject(jsonTokener);
		return jsonResponse;
	}

	@Test(priority=2)
	void testnestedJsonParsingValidation()
	{   
		//Converting JSONObject--->JsonPath
		//String str =getJSONResponse().toString();
		//JsonPath json=new JsonPath (str);
		JsonPath jsonPath=new JsonPath (getJSONResponses().toString());

		//a) Get the purchase amount of the course

		int purchase_amount = jsonPath.getInt("dashboard.purchaseAmount");
		assertThat(purchase_amount, is(1060));
		System.out.println("Purchase Amount :"+purchase_amount );
		
		//b) Get the title of the first course (Selenium).

		String first_coursetitle =jsonPath.getString("courses[0].title");
		assertThat(first_coursetitle,is("Selenium"));
		System.out.println("First Course Title  :"+first_coursetitle);
		
		//c) Get the total copies sold for Postman.
        int copies =jsonPath.getInt("courses[3].copies");
		
        assertThat(copies,is(5));
        System.out.println("Total Copies of Postman :"+copies);
        
        //d) Count the total number of courses
        int totalcoursecount =jsonPath.getInt("courses.size()");
        assertThat(totalcoursecount,is(4));
        System.out.println("Total courses count :"+totalcoursecount);


		//e) Print all the course titles

        {
        	for(int i=0;i<totalcoursecount;i++)
    		{
    			String coursestitle=jsonPath.getString("courses["+i+"].title");
    			System.out.println(coursestitle);
    		}
    		
		}



	}
}
