package day4;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class CookiesTestDemo {
	@Test()
	void testCookiesInResponse()
	{  

		Response response=given()

				.when()
				.get("http://www.google.com")

				.then()
				.statusCode(200)
				.log().cookies()
				//.cookie("AEC","AVh_V2jTz1-64pQu2cYUTaUiNNCcDE_eDU2OoTXvuZ9oGQrezzEqbPHfjWU"); //Cookies value validate it should fail
				.cookie("AEC",notNullValue())
				.extract().response();

		//Extract a specific cookies 
		String CookiesValue= response.getCookie("AEC");
		System.out.println("Value of 'AEC' Cookie: " +CookiesValue);

		//Extract all the cookies 
		Map<String,String >allCookies=response.getCookies();
		System.out.println("All the Cookies: " +allCookies);

		//Printing cookies and their values using for loop
		for(String key:allCookies.keySet())
		{
			System.out.println(key + ":" +allCookies.get(key));
		}

		// Get detailed information about the Cookie
		Cookie cookie_Info=response.getDetailedCookie("AEC");
		System.out.println(cookie_Info.hasExpiryDate());
		System.out.println(cookie_Info.getExpiryDate());
		System.out.println(cookie_Info.hasValue());
		System.out.println(cookie_Info.getValue());
		System.out.println(cookie_Info.isSecured());

	}

}
