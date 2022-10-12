package laba1;


public class Employees {
	private String id;
	private String firstName; 
	private String lastName; 
	private String location;  


	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}   
	@Override
	public String toString() {
		
		return "Language: id = " + this.id + " firstName = " + this.firstName
				+ " lastName = " + this.lastName 	+ " location = " + this.location;
	}   
}
