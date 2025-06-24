package day4;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class AssignmentSingleFileUpload {

	//1) Single file upload 
	
	@Test()

	void uploadSingleFile()
	{
		File myfile=new File("C:\\Users\\Madhukar Pandey\\Desktop\\Sales Data.txt");
		given()
		.multiPart("file",myfile)
		.contentType("multipart/form-data")

		.when()
		.post("https://the-internet.herokuapp.com/upload")


		.then()
		.statusCode(200)
		//.body("fileName", equalTo("Sales Data.txt"))
		.log().body();
	}
	
	//2) Single file download 
	@Test()
	void downloadSingleFile()
	{
		
		given()
		      .pathParam("filename", "test.txt")
		

		.when()
		    .get("https://the-internet.herokuapp.com/download/{filename}")


		.then()
		.statusCode(200)
		.log().body();
	}


}
