package day11;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matcher;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;

public class GraphQLMutationTests {

	private static final String BASE_URL = "https://hasura.io/learn/graphql";
	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY4NzJlYjc3YjA2NTY3Y2E1NzdiNjM1ZCJ9LCJuaWNrbmFtZSI6Im1hZGh1a2FyMTk5OTYiLCJuYW1lIjoibWFkaHVrYXIxOTk5NkBnbWFpbC5jb20iLCJwaWN0dXJlIjoiaHR0cHM6Ly9zLmdyYXZhdGFyLmNvbS9hdmF0YXIvNTY4ZDE1NWM4ODhkMDIzYTIyODI3YTgzYTBhNTU0NGQ_cz00ODAmcj1wZyZkPWh0dHBzJTNBJTJGJTJGY2RuLmF1dGgwLmNvbSUyRmF2YXRhcnMlMkZtYS5wbmciLCJ1cGRhdGVkX2F0IjoiMjAyNS0wNy0xMlQyMzoxMDo0OC40MDVaIiwiaXNzIjoiaHR0cHM6Ly9ncmFwaHFsLXR1dG9yaWFscy5hdXRoMC5jb20vIiwiYXVkIjoiUDM4cW5GbzFsRkFRSnJ6a3VuLS13RXpxbGpWTkdjV1ciLCJzdWIiOiJhdXRoMHw2ODcyZWI3N2IwNjU2N2NhNTc3YjYzNWQiLCJpYXQiOjE3NTIzNjE4NDksImV4cCI6MTc1MjM5Nzg0OSwic2lkIjoiSVl4NXFFY3ZlM0xZaldsVDFoVnpnZ2NXSWRyczJ5TVEiLCJhdF9oYXNoIjoid3A1eld1dS02U3otcnhLaC1Db1lQQSIsIm5vbmNlIjoiN0pQbjI2U1hKfnpoWlRGUXZJOTY3aUlITkpoTlBYdW0ifQ.NKA9FNkbVUoNjGV7YRTrXg8wd67dvEc1xMQd6thV_HK_BkV8oYvQatAFlneIrhtOHLAlQG8fYNJpYLseLYiwOOdoZflgmes2piVjVfJmAn5DMMHjul3j_wb3FV8Ifu9ShVAbPe04EVQbxBbGY8h8j2-WyZQtylvfYgiDp1kAh5mx1sFLWqvmdekWcyEgjFEjC8fk1PeY1-M5Hsm0nl58u8a6fnUit-hZdArI7WhUY7ixlXBohBWkoQLBDTZKw85XLxXUsnCu8OApRlO_IwdnQms1JAOmp6VYLH_LGAY8u_zHXtDxCf_Z_GQV2kt6vH94xPoMoPwYV-Fyx8cU8x__zg";

	static int insertedTodoid ;

	@Test(priority=1)
	public void testInsertTodo()
	{
		String insertMutation="{\r\n"
				+ "  \"query\": \"mutation { insert_todos(objects: [{title: \\\"sdet\\\"}]) { affected_rows returning { id created_at title } }}\"\r\n"
				+ "}";

		Response response =given()
				//.contentType("application/json")
				.contentType(ContentType.JSON)
				.header("Authorization", AUTH_TOKEN)
				.body(insertMutation)

				.when()
				.post(BASE_URL)


				.then()
				.statusCode(200)
				.extract().response();

		//Extract the response body 
		String responseBody=response.body().asString();
		System.out.println("Insert mutation response :" +responseBody);

		//Extract the ID of the inserted to (use it for updation and deletion)
		insertedTodoid=response.jsonPath().getInt("data.insert_todos.returning[0].id");

		System.out.println("Inserted Todos :"+insertedTodoid);

		//Validate the title to inserted Todos is "sdet"
		String InsertedTitle=response.jsonPath().getString("data.insert_todos.returning[0].title");
		assertThat(InsertedTitle,is("sdet"));



	}

	@Test(dependsOnMethods="testInsertTodo")
	public void testupdateTodos() {


		String updateMutation="{\r\n"
				+ "  \"query\": \"mutation { update_todos(where: {id: {_eq: "+insertedTodoid+"}}, _set: {title: \\\"Automation Engineer\\\",is_completed: true}) { affected_rows returning { id title is_completed } }}\"\r\n"
				+ "}";
		Response response=given()
				//.contentType("application/json")
				.contentType(ContentType.JSON)
				.header("Authorization", AUTH_TOKEN)
				.body(updateMutation)

				.when()
				.post(BASE_URL)


				.then()
				.statusCode(200)
				.extract().response();

		//Extract the updated body 
		String responseBody=response.body().asString();
		System.out.println("Insert mutation response :" +responseBody);

		//Validate the updated title is "Automation Engineer"
		String UpadtedTitle=response.jsonPath().getString("data.update_todos.returning[0].title");
		boolean isCompleted=response.jsonPath().getBoolean("data.update_todos.returning[0].is_completed");
		System.out.println("Updated title :" +UpadtedTitle);
		assertThat(UpadtedTitle,is("Automation Engineer"));
		assertThat(isCompleted,is(true));

	}

	@Test(dependsOnMethods="testupdateTodos")
	public void testDeleteTodo() {

		String deleteMutation = "{\r\n"
				+ "  \"query\": \"mutation { delete_todos(where: {id: {_eq:"+insertedTodoid+"} }) { affected_rows returning { title } } } \"\r\n"
				+ "}";

		// Send the request and validate the response
		Response response = 

				given()
				.contentType(ContentType.JSON)
				.header("Authorization", AUTH_TOKEN)
				.body(deleteMutation)
				.when()
				.post(BASE_URL)
				.then()
				.statusCode(200)
				.extract().response();

		// Extract the response body
		String responseBody = response.body().asString();
		System.out.println("Delete Response: " + responseBody);

		// Validate that the todo is deleted
		int affectedRows = response.jsonPath().getInt("data.delete_todos.affected_rows");
		assertThat(affectedRows, is(1));



	}
}