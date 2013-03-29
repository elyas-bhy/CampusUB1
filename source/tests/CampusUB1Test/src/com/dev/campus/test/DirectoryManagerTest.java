package com.dev.campus.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.dev.campus.directory.Contact;
import com.dev.campus.directory.DirectoryManager;
import com.unboundid.ldap.sdk.LDAPException;

public class DirectoryManagerTest extends TestCase {
	
	List<Contact> contacts;
	DirectoryManager manager;
	
	public DirectoryManagerTest() {
		super();
	}

	protected void setUp() throws Exception {
		contacts = new ArrayList<Contact>();
		manager = new DirectoryManager();
	}

	protected void tearDown() throws Exception {
		contacts.clear();
		manager =  null;
	}
	
	public void testRealSearchUB1() {
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
	
	public void testFakeSearchUB1() {
		try {
			contacts = manager.searchUB1("foo", "bar");
			assertTrue(contacts.size() == 0);
		} catch(LDAPException e) {
			e.printStackTrace();
		}
	}
	
	public void testRealSearchLabri() {
		try {
			manager.parseLabriDirectory();
			contacts = manager.filterLabriResults("Xavier", "Blanc");
			Contact xb = contacts.get(0);
			assertEquals("Xavier", xb.getFirstName());
			assertEquals("Blanc", xb.getLastName());
			assertEquals("xavier.blanc@labri.fr", xb.getEmail());
			assertEquals("+33 5 40 00 69 33", xb.getTel());
			assertEquals("http://www.labri.fr/index.php?n=Annuaires.Profile&id=Blanc_ID1282551988", xb.getWebsite());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFakeSearchLabri() {
		try {
			manager.parseLabriDirectory();
			contacts = manager.filterLabriResults("foo", "bar");
			assertTrue(contacts.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
