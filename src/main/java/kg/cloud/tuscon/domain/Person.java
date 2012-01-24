package kg.cloud.tuscon.domain;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id=null;
	private String firstName = "";
	private String lastName = "";
	private String gender="";
	private Date dob=null;
	private String company="";
	private String email = "";
	private String secondaryEmail="";
	private String phoneNumber = "";
	private String mobilePhoneNumber="";
	private String faxNumber="";
	private String streetAddress = "";
	private Integer postalCode = null;
	private String companyType ="";
	private String websiteUrl="";
	private String sektor="";
	private String organization;
	private String foundation="";
	private String membership="";
	private String common="";
	
	public Person(){}
	
	public Person(Integer id, String fName, String lName,
			String gender, Date dob,String company, String email, String sEmail,
			String phone, String mobile, String fax, String street,
			int postal, String compType, String sektor,String organization,
			String website, String found, String member, String common) {
		this.id=id;
		this.firstName=fName;
		this.lastName=lName;
		this.gender=gender;
		this.dob=dob;
		this.company=company;
		this.email=email;
		this.secondaryEmail=sEmail;
		this.phoneNumber=phone;
		this.mobilePhoneNumber=mobile;
		this.faxNumber=fax;
		this.streetAddress=street;
		this.postalCode=postal;
		this.companyType=compType;
		this.sektor=sektor;
		this.organization=organization;
		this.websiteUrl=website;
		this.foundation=found;
		this.membership=member;
		this.common=common;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getSektor() {
		return sektor;
	}

	public void setSektor(String sektor) {
		this.sektor = sektor;
	}

	public String getFoundation() {
		return foundation;
	}

	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
