package day11;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

/**
 * GraphQL Testing with REST Assured
 * JSON Converter: https://datafetcher.com/graphql-json-body-converter
 * GraphQL queries must be wrapped in JSON format.
 */


public class GraphQL_QueryTests {

	private static final String BASE_URL = "https://hasura.io/learn/graphql";
	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY4NzJlYjc3YjA2NTY3Y2E1NzdiNjM1ZCJ9LCJuaWNrbmFtZSI6Im1hZGh1a2FyMTk5OTYiLCJuYW1lIjoibWFkaHVrYXIxOTk5NkBnbWFpbC5jb20iLCJwaWN0dXJlIjoiaHR0cHM6Ly9zLmdyYXZhdGFyLmNvbS9hdmF0YXIvNTY4ZDE1NWM4ODhkMDIzYTIyODI3YTgzYTBhNTU0NGQ_cz00ODAmcj1wZyZkPWh0dHBzJTNBJTJGJTJGY2RuLmF1dGgwLmNvbSUyRmF2YXRhcnMlMkZtYS5wbmciLCJ1cGRhdGVkX2F0IjoiMjAyNS0wNy0xMlQyMzoxMDo0OC40MDVaIiwiaXNzIjoiaHR0cHM6Ly9ncmFwaHFsLXR1dG9yaWFscy5hdXRoMC5jb20vIiwiYXVkIjoiUDM4cW5GbzFsRkFRSnJ6a3VuLS13RXpxbGpWTkdjV1ciLCJzdWIiOiJhdXRoMHw2ODcyZWI3N2IwNjU2N2NhNTc3YjYzNWQiLCJpYXQiOjE3NTIzNjE4NDksImV4cCI6MTc1MjM5Nzg0OSwic2lkIjoiSVl4NXFFY3ZlM0xZaldsVDFoVnpnZ2NXSWRyczJ5TVEiLCJhdF9oYXNoIjoid3A1eld1dS02U3otcnhLaC1Db1lQQSIsIm5vbmNlIjoiN0pQbjI2U1hKfnpoWlRGUXZJOTY3aUlITkpoTlBYdW0ifQ.NKA9FNkbVUoNjGV7YRTrXg8wd67dvEc1xMQd6thV_HK_BkV8oYvQatAFlneIrhtOHLAlQG8fYNJpYLseLYiwOOdoZflgmes2piVjVfJmAn5DMMHjul3j_wb3FV8Ifu9ShVAbPe04EVQbxBbGY8h8j2-WyZQtylvfYgiDp1kAh5mx1sFLWqvmdekWcyEgjFEjC8fk1PeY1-M5Hsm0nl58u8a6fnUit-hZdArI7WhUY7ixlXBohBWkoQLBDTZKw85XLxXUsnCu8OApRlO_IwdnQms1JAOmp6VYLH_LGAY8u_zHXtDxCf_Z_GQV2kt6vH94xPoMoPwYV-Fyx8cU8x__zg";


	//Fetch Users and Their Todos
	//@Test(priority=1)
	public void testFetchUsersAndTodos()
	{
		String graphqlQuery="{\r\n"
				+ "  \"query\": \"query { users { name todos { title } }}\"\r\n"
				+ "}";

		given()
		//.contentType("application/json")
		.contentType(ContentType.JSON)
		.header("Authorization", AUTH_TOKEN)
		.body(graphqlQuery)

		.when()
		.post(BASE_URL)


		.then()
		.statusCode(200)
		.body("data.users", hasSize(greaterThan(0)))
		.body("data.users[0]",  notNullValue())
		.body("data.users[0].todos", hasSize(greaterThanOrEqualTo(0)))
		.log().body();
	}


	//Fetch Limited Todos
	//@Test(priority=2)
	public void testFetchLimitedTodos()
	{
		String graphqlQuery="{\r\n"
				+ "  \"query\": \"query { todos(limit: 5) { id title }}\"\r\n"
				+ "}";

		given()
		//.contentType("application/json")
		.contentType(ContentType.JSON)
		.header("Authorization", AUTH_TOKEN)
		.body(graphqlQuery)

		.when()
		.post(BASE_URL)


		.then()
		.statusCode(200)
		.body("data.todos", hasSize(lessThanOrEqualTo(5)))
		.body("data.todos[0].id",  notNullValue())
		.body("data.todos[0].title", notNullValue())
		.log().body();
	}


	//Fetch Users with Recent  Todos
	//@Test(priority=3)
	public void testFetchWithRecentTodos()
	{
		String graphqlQuery="{\r\n"
				+ "  \"query\": \"query { users(limit: 2) { id name todos(order_by: {created_at: desc}, limit: 5) { id title } }}\"\r\n"
				+ "}";

		given()
		//.contentType("application/json")
		.contentType(ContentType.JSON)
		.header("Authorization", AUTH_TOKEN)
		.body(graphqlQuery)

		.when()
		.post(BASE_URL)


		.then()
		.statusCode(200)
		.body("data.users", hasSize(2))
		.body("data.users[0].todos",  hasSize(lessThanOrEqualTo(1)))
		.body("data.users[0].name", notNullValue())
		.log().body();
	}


	//Fetch Todos with Variables
	//@Test(priority=4)
	public void testFetchTodosWitVariables()
	{
		String graphqlQuery="{\r\n"
				+ "  \"query\": \"query ($limit: Int!) { todos(limit: $limit) { id title }}\",\r\n"
				+ "  \"variables\": {\r\n"
				+ "    \"limit\": 5\r\n"
				+ "  }\r\n"
				+ "}";

		given()
		//.contentType("application/json")
		.contentType(ContentType.JSON)
		.header("Authorization", AUTH_TOKEN)
		.body(graphqlQuery)

		.when()
		.post(BASE_URL)


		.then()
		.statusCode(200)
		.body("data.todos", hasSize(lessThanOrEqualTo(5)))
		.body("data.todos[0].id",  notNullValue())
		.body("data.todos[0].title", notNullValue())
		.log().body();
	}


	//Fetch Public Todos 
	@Test(priority=5)
	public void testFetchPublicTodos()
	{
		String graphqlQuery="{\r\n"
				+ "  \"query\": \"query { todos(where: {is_public: {_eq: true}}) { title is_public is_completed }}\"\r\n"
				+ "}";

		given()
		//.contentType("application/json")
		.contentType(ContentType.JSON)
		.header("Authorization", AUTH_TOKEN)
		.body(graphqlQuery)

		.when()
		.post(BASE_URL)


		.then()
		.statusCode(200)
		.body("data.todos", hasSize(greaterThanOrEqualTo(0)))
		.body("data.todos[0].is_public",equalTo(true))
		.body("data.todos[0].is_completed", notNullValue())
		.log().body();
	}


}
