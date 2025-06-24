package day5;

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

public class ParsingComplexResponseJson {
	//User defined method --> This will read data from json ,convert to json response and return it .
	JSONObject getJSONResponse()
	{
		File myfile=new File(".\\src\\test\\resources\\complex.json");
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

	@Test(priority=1)
	void testUserDetailsValidation()
	{   
        //Converting JSONObject--->JsonPath
		//String str =getJSONResponse().toString();
		//JsonPath json=new JsonPath (str);
		JsonPath jsonPath=new JsonPath (getJSONResponse().toString());

		// a) Verify Status 
		String status =jsonPath.getString("status");
		assertThat(status,is("success"));
		
		 // b) Validate user details
		
		int Id =jsonPath.getInt("data.userDetails.id");
		String name =jsonPath.getString("data.userDetails.name");
		String email =jsonPath.getString("data.userDetails.email");
		
		assertThat(Id,is(12345));
		assertThat(name,is("John Doe"));
		assertThat(email,is("john.doe@example.com"));
		
		//c) Validate home phone number
		
		String homePhonetype =jsonPath.getString("data.userDetails.phoneNumbers[0].type");
		String homePhonenumber =jsonPath.getString("data.userDetails.phoneNumbers[0].number");
		
		assertThat(homePhonetype,is("home"));
		assertThat(homePhonenumber,is("123-456-7890"));
		
		// d) Validate geo coordinates
		
		double latitude =jsonPath.getDouble("data.userDetails.address.geo.latitude");
		double longitude =jsonPath.getDouble("data.userDetails.address.geo.longitude");
		
		assertThat(latitude,is(39.7817));
		assertThat(longitude,is(-89.6501));
		
		// e) Validate preferences
		
		boolean notifications =jsonPath.getBoolean("data.userDetails.preferences.notifications");
		String theme =jsonPath.getString("data.userDetails.preferences.theme");
		
		assertThat(notifications,is(true));
		assertThat(theme,is("dark"));
		
		
		
		

	}

}
