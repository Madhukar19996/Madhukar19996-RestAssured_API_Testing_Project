package day7;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.path.xml.XmlPath;
import io.restassured.matcher.*;
import io.restassured.module.jsv.JsonSchemaValidator;


public class SchemaValidations {
  //Json Schema Validation
	//@Test(priority=1)
	void testJsonSchema()
	{
		given()
		
		.when()
		     .get("https://mocktarget.apigee.net/json")
		
		.then()
		    .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"));
	}
	
	
	//Xml schema/XSD Schema Validation
		@Test(priority=2)
		void testXmlSchema()
		{
			given()
			
			.when()
			     .get("https://mocktarget.apigee.net/xml")
			
			.then()
			    .assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchema.xsd"));
		}
}
