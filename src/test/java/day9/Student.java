package day9;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true )
public class Student {

	//variables

	String name;
	String location;
	String phone;
	String courses[];


	//Contractors 

	public  Student() {} // default constructor 

	public Student(String name, String location, String phone, String [] courses) 
	{
		this.name=name;
		this.location=location;
		this.phone=phone;
		this.courses=courses;
	}




	//setters & getters 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}



	//toString()

	@Override
	public String toString()
	{
		StringBuilder sb= new StringBuilder();

		sb.append(name).append(" ").append(location).append(" ").append(phone).append(" ");
		
		if(courses!=null && courses.length>0)
		{
			
			for(String course:courses)
			{
			  sb.append(course).append(" ");
			}
			
		}
		return sb.toString();

	}


}
