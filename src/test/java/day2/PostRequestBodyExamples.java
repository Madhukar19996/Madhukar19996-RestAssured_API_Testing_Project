package day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;


/*
 --> Demonstrates different ways to create Post request bodies:
 1) Using HashMap
 2) Using org.json Library
 3) Using POJO class
 4) Using external json file 
 	
 */

public class PostRequestBodyExamples {	
		
	//1) Create Post request body using HashMap
	String studentId;
	  //@Test()
	  void createStudentsUsingHashMap()
	  {
		  
		  HashMap<String,Object>requestBody=new HashMap<>();
		  requestBody.put("name", "Harsh");
		  requestBody.put("location", "U.K");
		  requestBody.put("phone", "1199675518");
		  
		  String courses[]= {"Playwright","DBMS"};
		  requestBody.put("courses", courses);
		  
		  
		  studentId=given()
		       .contentType("application/json")
		       .body(requestBody)
		  
		  .when()
		       .post("http://localhost:3000/students")
		  
		  .then()
		       .statusCode(201)
		       .body("name", equalTo("Harsh"))
		       .body("location",equalTo("U.K"))
		       .body("phone",equalTo("1199675518"))
		       .body("courses[0]",equalTo("Playwright"))
		       .body("courses[1]",equalTo("DBMS"))
		       .header("Content-Type","application/json")
		       .log().body()
		       .extract().jsonPath().getString("id");
		  
		  
		  
	  }
	  
	//2) Create Post request body using org.json library
		
		 // @Test()
		  void createStudentsUsingJsonLibrary()
		  {
			  
			  JSONObject requestBody=new JSONObject();
			  requestBody.put("name", "Harsh");
			  requestBody.put("location", "U.K");
			  requestBody.put("phone", "1199675518");
			  
			  String courses[]= {"Playwright","DBMS"};
			  requestBody.put("courses", courses);
			  
			  
			  studentId=given()
			       .contentType("application/json")
			       .body(requestBody.toString())
			  
			  .when()
			       .post("http://localhost:3000/students")
			  
			  .then()
			       .statusCode(201)
			       .body("name", equalTo("Harsh"))
			       .body("location",equalTo("U.K"))
			       .body("phone",equalTo("1199675518"))
			       .body("courses[0]",equalTo("Playwright"))
			       .body("courses[1]",equalTo("DBMS"))
			       .header("Content-Type","application/json")
			       .log().body()
			       .extract().jsonPath().getString("id");
			  
		 }
		  
		  
		  
		//3. Create Post request body using POJO Class
			
			  //@Test()
			  void createStudentsUsingPOJOClass()
			  {
				  
                StudentPOJO requestBody= new StudentPOJO ();
                requestBody.setName("Ramesh");
                requestBody.setLocation("Japan");
                requestBody.setPhone("449867543");
                
                
                String courses[]= {"Cypress","JavaScript"};
                requestBody.setCourses(courses);
                
                
				  
				  
				  studentId=given()
				       .contentType("application/json")
				       .body(requestBody)
				  
				  .when()
				       .post("http://localhost:3000/students")
				  
				  .then()
				       .statusCode(201)
				       .body("name", equalTo(requestBody.getName()))
				       .body("location",equalTo(requestBody.getLocation()))
				       .body("phone",equalTo(requestBody.getPhone()))
				       .body("courses[0]",equalTo(requestBody.getCourses()[0]))
				       .body("courses[1]",equalTo(requestBody.getCourses()[1]))
				       .header("Content-Type","application/json")
				       .log().body()
				       .extract().jsonPath().getString("id");
				  
			 }
			  
			  
			//4. Create Post request body using External JSON file
				
			  @Test()
			  void createStudentsUsingExternalFile() throws FileNotFoundException
			  {   
				  File myfile= new File(".\\src\\test\\java\\day2\\Body.json");
				  FileReader fileReader=new FileReader(myfile);
				  JSONTokener jsonTokener=new JSONTokener(fileReader);
				  JSONObject requestBody=new JSONObject(jsonTokener);
              
                
				  
				  
				  studentId=given()
				       .contentType("application/json")
				       .body(requestBody.toString())
				  
				  .when()
				       .post("http://localhost:3000/students")
				  
				  .then()
				   .statusCode(201)
			       .body("name", equalTo("Ankit"))
			       .body("location",equalTo("Russia"))
			       .body("phone",equalTo("1535498765"))
			       .body("courses[0]",equalTo(".Net"))
			       .body("courses[1]",equalTo("FastAPI"))
			       .header("Content-Type","application/json")
			       .log().body()
			       .extract().jsonPath().getString("id");
				  
			 } 
		  
		  
		  
		  
		  
		  @AfterMethod()
		  void deleteStudentRecord()
		  {
			  given()
			  
			  .when()
			       .delete("http://localhost:3000/students/"+ studentId)
			  
			  .then()
			        .statusCode(200);
		  }

}
