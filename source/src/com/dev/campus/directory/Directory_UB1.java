package com.dev.campus.directory;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

public class Directory_UB1 {
	
	private static LDAPConnection ldap;
	
	static boolean authenticate() throws LDAPException {
		ldap = new LDAPConnection("carnet.u-bordeaux1.fr", 389);
		Filter f1 = Filter.createEqualityFilter("cn", "Blanc Xavier");
	    SearchResult sr = ldap.search("ou=people,dc=u-bordeaux1,dc=fr", SearchScope.SUB,f1);
	    CampusUB1App.LogD(sr.getSearchEntries().get(0).toString());
	    if (sr.getEntryCount() < 1) {
	    	CampusUB1App.LogD("Error, not connected to ldap"); 
	        return false;
	    }
	    return true;
	}
	
	public static ArrayList<Contact> search(String lastName, String firstName) throws LDAPException {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		// TODO : bad filtering
		Filter f = Filter.createApproximateMatchFilter("cn",lastName + " " + firstName);
		/*Filter f2 = Filter.createANDFilter(
                Filter.createEqualityFilter("givenName", firstName),
                Filter.createEqualityFilter("sn", lastName));
		Filter f3 = Filter.create("(|(givenName="+firstName+"*)(sn="+lastName+"*))");*/
		SearchResult searchResults = ldap.search("ou=people,dc=u-bordeaux1,dc=fr",SearchScope.SUB,f);
		int entryCount = searchResults.getEntryCount();
		if (entryCount > 0)
		{	
			for(int contact_nb=0; contact_nb < entryCount; contact_nb++) {
				SearchResultEntry entry = searchResults.getSearchEntries().get(contact_nb);
				Contact contact = new Contact();
				contact.setEmail(entry.getAttributeValue("mail"));
				contact.setTel(entry.getAttributeValue("telephoneNumber"));
				contact.setFirstName(entry.getAttributeValue("givenName"));
				contact.setLastName(entry.getAttributeValue("sn"));
				contacts.add(contact);
			}
		}
		return contacts;
	}
}
