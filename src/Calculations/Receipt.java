package Calculations;

public class Receipt {
	private String code;
	private String dateOfPublish;
	private String type;
	private double value;
	private Company company;
	private String information[];

	public Receipt(String information[], String companyInformation[]) {
		this.information = information;
		setInformation(companyInformation);
	}

	public void setInformation(String companyInformation[]) {
		this.code = information[0];
		this.dateOfPublish = information[1];
		this.type = information[2];
		this.value = Double.parseDouble(information[3]);
		company = new Company(companyInformation);
	}

	public String getCode() {
		return code;
	}

	public String getDate() {
		return dateOfPublish;
	}

	public String getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	public Company getCompany() {
		return company;
	}

	public String getCompanyName() {
		return company.getName();
	}

	public String getCompanyCountry() {
		return company.getCountry();
	}

	public String getCompanyCity() {
		return company.getCity();
	}

	public String getCompanyStreet() {
		return company.getStreet();
	}

	public String getCompanyNumber() {
		return company.getNumber();
	}
}
