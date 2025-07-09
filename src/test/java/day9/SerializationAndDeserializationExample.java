package day9;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class SerializationAndDeserializationExample {

	String stuId;
	@Test()
	public void testSerilization() {
		String courses[] = {"Selenium", "java", "Python"};
		Student stu = new Student("Madhukar", "Noida", "998845321", courses);

		stuId = given()
				.contentType("application/json")
				.body(stu)
				.when()
				.post("http://localhost:3000/students")
				.then()
				.statusCode(201)
				.log().body()
				.extract().response().jsonPath().getString("id");
	}

	@Test(dependsOnMethods = "testSerilization")
	public void testDeserilization() {
		Response response = given()
				.pathParam("id", stuId)
				.when()
				.get("http://localhost:3000/students/{id}")
				.then()
				.statusCode(200)
				.extract().response();

		String extractedId = response.jsonPath().getString("id");
		Student stu = response.as(Student.class);
		assertThat(stu.getName(), is("Madhukar"));
		assertThat(stu.getLocation(), is("Noida"));
		assertThat(stu.getPhone(), is("998845321"));
		assertThat(stu.getCourses()[0], is("Selenium"));
       
		//Printing students all details 
		System.out.println("Student details: " + stu + " | Extracted ID: " + extractedId);
		
		
	}

	@Test(dependsOnMethods = "testDeserilization") // ✅ now runs strictly last
	public void deleteStudent() {
		System.out.println("Deleting Student with id ==> " + stuId);

		given()
		.pathParam("id", stuId)
		.when()
		.delete("http://localhost:3000/students/{id}")
		.then()
		.statusCode(200);
	}


}
