package day7;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.path.xml.XmlPath;


public class ParsingXML {

	/**
	 * Test case to validate XML response data
	 * Validates:
	 * - HTTP status code
	 * - Content type
	 * - Specific XML element values
	 */

	//@Test(priority=1)
	void testXMLRResponse1()
	{
		given()

		.when()
		.get("https://mocktarget.apigee.net/xml")

		.then()
		.statusCode(200)
		.contentType("application/xml")
		.header("Content-Type", "application/xml; charset=utf-8")
		.header("Content-Type",equalTo("application/xml; charset=utf-8"))

		.body("root.city", equalTo("San Jose"))
		.body("root.firstName", equalTo("John"))
		.body("root.lastName", equalTo("Doe"))
		.body("root.state", equalTo("CA"))
		.log().body();		      

	}	

	/**
	 * Test case to validate attributes in an XML response
	 * Validates:
	 * - HTTP status code
	 * - Content type
	 * - Specific XML attributes using '@' notation
	 */


	//@Test(priority=2)
	void testXMLRResponse2()
	{
		given()

		.when()
		.get("https://httpbin.org/xml")

		.then()
		.statusCode(200)
		.contentType("application/xml")
		.body("slideshow.@title",equalTo("Sample Slide Show"))
		.body("slideshow.@date",equalTo("Date of publication"))
		.body("slideshow.@author",equalTo("Yours Truly"))
		//.body("slideshow.@title",equalTo("Sample Slide Show"))
		.log().body();		      

	}

	/**
	 * Test case to extract and parse XML response data using XmlPath
	 * Validates:
	 * - Number of slides in the response
	 * - Slide titles
	 * - Number of slide items
	 * - Specific slide item values
	 */

	@Test(priority=3)
	void testXMLParsingRResponse()
	{
		Response response=given()

				.when()
				.get("https://httpbin.org/xml")

				.then()
				.statusCode(200)
				.contentType("application/xml")
				.extract().response();

		XmlPath xmlPath=new XmlPath(response.asString());

		//1) Number of slides & capturing title of slides in the response
		List<String>slidetitles=xmlPath.getList("slideshow.slide.title");

		//Counting slides
		assertThat(slidetitles.size(),is(2));

		//Validate slide titles
		assertThat(slidetitles.get(0),is("Wake up to WonderWidgets!"));  //specific title 
		assertThat(slidetitles.get(1),is("Overview"));//specific title

		assertThat(slidetitles,hasItems("Wake up to WonderWidgets!","Overview")); //multiple title

		//Number of items 

		List<String>Items=xmlPath.getList("slideshow.slide.item");
		System.out.println("Number of Items:"+Items.size());
		assertThat(Items.size(),is(3));

		//Validate value of itmes
		assertThat(Items.get(0),is("WonderWidgets"));
		assertThat(Items.get(2),is("buys"));

		assertThat(Items,hasItems("WonderWidgets","buys"));

		//Check presence of items in the response
		boolean status=false;
		for(String item:Items)
		{
			if(item.equals("WonderWidgets"))
			{
				status=true;
				break;
			}
		}
        
		assertThat(status, is(true));


	}







}
