package com.dev.campus.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.dev.campus.directory.Contact;
import com.dev.campus.directory.DirectoryManager;
import com.unboundid.ldap.sdk.LDAPException;

public class DirectoryManagerTest extends TestCase {
	
	List<Contact> contacts = new ArrayList<Contact>();
	DirectoryManager manager;
	
	public DirectoryManagerTest() {
		super();
	}

	protected void setUp() throws Exception {
		
	}

	protected void tearDown() throws Exception {
		contacts.clear();
	}
	
	public void testRealSearchUB1(String firstName, String lastName) {
		try {
			contacts = manager.searchUB1("Xavier", "Blanc");
			Contact xb = contacts.get(0);
			assertFalse(xb == null);
			assertEquals("Xavier", xb.getFirstName());
			assertEquals("Blanc", xb.getLastName());
			assertEquals("xavier.blanc@u-bordeaux1.fr", xb.getEmail());
			assertEquals("+33 5 40 00 69 33", xb.getTel());			
		} catch (LDAPException e) {
			e.printStackTrace();
		}
	}
	
	public void testFakeSearchUB1(String firstName, String lastName) {
		try {
			contacts = manager.searchUB1("foo", "bar");
			Contact fb = contacts.get(0);
			assertTrue(fb == null);
		} catch(LDAPException e) {
			e.printStackTrace();
		}
	}
}
