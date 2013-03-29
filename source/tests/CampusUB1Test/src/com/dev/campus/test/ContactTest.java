package com.dev.campus.test;

import android.test.AndroidTestCase;

import com.dev.campus.directory.Contact;
import com.dev.campus.directory.Contact.ContactType;


public class ContactTest extends AndroidTestCase  {

	private String mFirstName;
	private String mLastName;
	private String mEmail;
	private String mTel;
	private String mWebsite;
	private Contact.ContactType mType;
	private Contact mContact;


	public ContactTest() {
		super();
	}

	protected void setUp() throws Exception {
		mFirstName = "John";
		mLastName = "Doe";
		mEmail = "email@email.com";
		mTel = "0123456789";
		mWebsite= "http://www.example.com";
		mType = ContactType.UB1_CONTACT;
		mContact = new Contact();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFirstName(String firstName) {
		mContact.setFirstName(mFirstName);
		assertEquals(mFirstName, mContact.getFirstName());
	}

	public void testLastName() {
		mContact.setLastName(mLastName);
		assertEquals(mLastName, mContact.getLastName());
	}

	public void testEmail() {
		mContact.setEmail(mEmail);
		assertEquals(mEmail, mContact.getEmail());
	}

	public void testTel() {
		mContact.setTel(mTel);
		assertEquals(mTel, mContact.getTel());
	}

	public void testWebsite() {
		mContact.setWebsite(mWebsite);
		assertEquals(mWebsite, mContact.getWebsite());
	}

	public void testType() {
		mContact.setType(mType);
		assertEquals(mType, mContact.getType());
	}

	public void testHasTel() {
		mContact.setTel(null);
		assertFalse(mContact.hasTel());
		mContact.setTel("");
		assertFalse(mContact.hasTel());
		mContact.setTel(mTel);
		assertTrue(mContact.hasTel());
	}

	public void testHasEmail() {
		mContact.setEmail(null);
		assertFalse(mContact.hasEmail());
		mContact.setEmail("");
		assertFalse(mContact.hasEmail());
		mContact.setEmail(mEmail);
		assertTrue(mContact.hasEmail());
	}

	public void testHasWebsite() {
		mContact.setWebsite(null);
		assertFalse(mContact.hasWebsite());
		mContact.setWebsite("");
		assertFalse(mContact.hasWebsite());
		mContact.setWebsite(mWebsite);
		assertTrue(mContact.hasWebsite());
	}
}
