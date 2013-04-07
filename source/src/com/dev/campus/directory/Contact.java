/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus.directory;

import java.util.Comparator;

/**
 * Basic holder class for contact data
 * @author CampusUB1 Development Team
 *
 */
public class Contact {

	private String firstName;
	private String lastName;
	private String email;
	private String tel;
	private String website;
	private ContactType type;

	public enum ContactType {
		UB1_CONTACT, LABRI_CONTACT;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	public boolean hasTel() {
		return (this.tel != null) && !((this.tel).equals(""));
	}

	public boolean hasEmail() {
		return this.email != null && !(this.email).equals("");
	}

	public boolean hasWebsite() {
		return this.website != null && !(this.website).equals("");
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}
	
	static class ContactComparator implements Comparator<Contact> {
		@Override
		public int compare(Contact c1, Contact c2) {
			return c1.getLastName().compareTo(c2.getLastName());
		}
	}

}
