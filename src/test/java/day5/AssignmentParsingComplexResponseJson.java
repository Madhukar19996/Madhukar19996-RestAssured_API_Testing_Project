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

public class AssignmentParsingComplexResponseJson {

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
	void testRecentOrdersValidation()
	{   
		//Converting JSONObject--->JsonPath
		//String str =getJSONResponse().toString();
		//JsonPath json=new JsonPath (str);
		JsonPath jsonPath=new JsonPath (getJSONResponse().toString());

		//a) Verify the total number of orders is 2.
		int totalOrders = jsonPath.getInt("data.recentOrders.size()");
		assertThat(totalOrders, is(2));

		//b) Validate the orderId of the first order is 101 and that the total amount is 1226.49.
		int OrderId =jsonPath.getInt("data.recentOrders[0].orderId");
		Double totalAmount =jsonPath.getDouble("data.recentOrders[0].totalAmount");

		assertThat(OrderId,is(101));
		assertThat(totalAmount,is(1226.49));

		// Confirm the name of the second item in the first order is "Mouse" and its price is 25.50.
		String mouse =jsonPath.getString("data.recentOrders[0].items[1].name");
		Double price1 =jsonPath.getDouble("data.recentOrders[0].items[1].price");

		assertThat(mouse,is("Mouse"));
		assertThat(price1,is(25.5));


		//Check that the second order has only one item, a "Smartphone", with a price of 799.99

		String smartphone =jsonPath.getString("data.recentOrders[1].items[0].name");
		Double price2 =jsonPath.getDouble("data.recentOrders[1].items[0].price");

		assertThat(smartphone,is("Smartphone"));
		assertThat(price2,is(799.99));

	}        


	@Test(priority=2)
	void testPreferencesAndMetadataValidation()
	{   
		//Converting JSONObject--->JsonPath
		//String str =getJSONResponse().toString();
		//JsonPath json=new JsonPath (str);
		JsonPath jsonPath=new JsonPath (getJSONResponse().toString());

		//a) Verify the user speaks three languages: English, Spanish, and French.
		
		int totalLanguages = jsonPath.getInt("data.userDetails.preferences.languages.size()");
        assertThat(totalLanguages, is(3));
        
        String English =jsonPath.getString("data.userDetails.preferences.languages[0]");
        String Spanish =jsonPath.getString("data.userDetails.preferences.languages[1]");
        String French =jsonPath.getString("data.userDetails.preferences.languages[2]");
        
		assertThat(totalLanguages,is(3));
		assertThat(English,is("English"));
		assertThat(Spanish,is("Spanish"));
		assertThat(French,is("French"));
		
		
		//b) Confirm the requestId is "abc123xyz" and the responseTimeMs is less than 300 ms.
		
	    String reqId =jsonPath.getString("meta.requestId");
	    int responseTime =jsonPath.getInt("meta.responseTimeMs");
	    
	    assertThat(reqId,is("abc123xyz"));
	    assertThat(responseTime,is(250));




	}
	
	

	
}