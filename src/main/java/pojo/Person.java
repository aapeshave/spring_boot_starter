package pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("address")
	private String address;

	@JsonProperty("UUID")
	private String UUID;

	@JsonProperty("password")
	private String password;

	public Person() {
	}

	public Person(String firstName, String lastName, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, String> getPersonAsMap(){
		Map<String, String> personMap = new HashMap<>();
		personMap.put("firstName", this.firstName);
		personMap.put("lastName", this.lastName);
		personMap.put("address", this.address);
		return personMap;
	}
	
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + "]";
	}

}
