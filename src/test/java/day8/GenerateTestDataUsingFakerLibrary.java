package day8;

import org.testng.annotations.Test;
import com.github.javafaker.Faker;

public class GenerateTestDataUsingFakerLibrary {


	@Test
	void fakeDataGenerator()
	{
		Faker faker=new Faker();
		
		String fullname=faker.name().fullName();
		String firstname=faker.name().firstName();
		String lastname=faker.name().lastName();
		
		//String email=faker.internet().emailAddress();
		String email=faker.internet().safeEmailAddress();
		String password=faker.internet().password(5, 9);   //here 5- minimum  9 maximum
		String phoneno=faker.phoneNumber().cellPhone();
		
		System.out.println("Full Name: "+fullname);
		System.out.println("First Name: "+firstname);
		System.out.println("last Name: "+lastname);
		System.out.println("Email:"+email);
		System.out.println("Password:"+password);
		System.out.println("Phone No:"+ phoneno);
		
	}
	
}
