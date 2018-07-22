package Calculations;

public class Company {
	private String name;
	private String country;
	private String city;
	private String street;
	private String number;

	public Company(String information[]) {
		name = information[0];
		country = information[1];
		city = information[2];
		street = information[3];
		number = information[4];
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}
}
