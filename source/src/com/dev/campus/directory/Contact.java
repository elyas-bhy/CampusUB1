package com.dev.campus.directory;

import java.util.Comparator;

public class Contact {

	private String firstName;
	private String lastName;
	private String email;
	private String tel;
	private String website;

	public Contact() {

	}
	public Contact(String firstName, String lastName, String email, String tel, String website) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.tel = tel;
		this.website = website;
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
	
	public boolean existsEmail(){
		if(this.email == null)
			return false;
		return true;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public boolean existsTel(){
		String [] notValid = new String[] {null,"Non renseigne"};
		if(this.tel == notValid[0] || this.tel.equals(notValid[1]))
			return false;
		return true;
	}
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public boolean existsWebsite(){
		if(this.website == null)
			return false;
		return true;
	}
	
}

	

class ContactComparator implements Comparator<Contact> {
	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getLastName().compareTo(c2.getLastName());
	}
}
