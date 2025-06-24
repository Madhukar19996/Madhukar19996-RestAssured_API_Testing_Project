package day4;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class FileUploadAndDownload {
	
	//1) Single file upload 
	
	//@Test()
	
	void uploadSingleFile()
	{
		File myfile=new File("C:\\Users\\Madhukar Pandey\\Desktop\\students.json");
		given()
		     .multiPart("file",myfile)
		     .contentType("multipart/form-data")
		
		.when()
		     .post("http://localhost:8080/uploadFile")
		
		
		.then()
		     .statusCode(200)
		     .body("fileName", equalTo("students.json"))
		     .log().body();
	}
	
	//2) Multiple files upload 
	
		//@Test()
		
		void multipleFilesUpload()
		{
			File myfile1=new File("C:\\Users\\Madhukar Pandey\\Desktop\\students.json");
			File myfile2=new File("C:\\Users\\Madhukar Pandey\\Desktop\\csvjson.json");
			
			given()
			     .multiPart("files",myfile1)
			     .multiPart("files",myfile2)
			     .contentType("multipart/form-data")
			
			.when()
			     .post("http://localhost:8080/uploadMultipleFiles")
			
			
			.then()
			     .statusCode(200)
			     .body("[0].fileName", equalTo("students.json"))
			     .body("[1].fileName", equalTo("csvjson.json"))
			     .log().body();
		}
		
		@Test()
		void downloadfile()
		{
			given()
			//1) Single file upload 
			     
			
			.when()
			     .get("http://localhost:8080/downloadFile/{filename}")
			.then()
			      .statusCode(200)
			      .log().body();
		}


}
