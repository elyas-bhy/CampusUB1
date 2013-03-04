package com.dev.campus.directory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dev.campus.CampusUB1App;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;

import android.text.Html;

public class Directory {

	private static LDAPConnection LDAP;

	public static boolean isAuthenticatedLDAP() throws LDAPException {
		LDAP = new LDAPConnection("carnet.u-bordeaux1.fr", 389);
		Filter f1 = Filter.createEqualityFilter("cn", "Blanc Xavier");
		SearchResult sr = LDAP.search("ou=people,dc=u-bordeaux1,dc=fr", SearchScope.SUB, f1);
		if (sr.getEntryCount() < 1) {
			CampusUB1App.LogD("Error, not connected to ldap"); 
			return false;
		}
		return true;
	}

	public static ArrayList<Contact> searchUB1(String lastName, String firstName) throws LDAPException {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Filter f = Filter.create("(&(givenName=" + firstName + "*)(sn=" + lastName + "*))");
		String[] attributes = {"mail", "telephoneNumber", "givenName", "sn"};

		SearchRequest searchRequest = new SearchRequest("ou=people,dc=u-bordeaux1,dc=fr", SearchScope.SUB, f, attributes);

		searchRequest.setControls(new Control[] { new SimplePagedResultsControl(10, null)});
		SearchResult searchResult = LDAP.search(searchRequest);
		int entryCount = searchResult.getEntryCount();
		// Do something with the entries that are returned.
		if (entryCount > 0) {
			for (int contact_nb = 0; contact_nb < entryCount; contact_nb++) {
				SearchResultEntry entry = searchResult.getSearchEntries().get(contact_nb);
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


	public static ArrayList<Contact> filterLabriResults(ArrayList<Contact> contacts, String firstName, String lastName){
		ArrayList<Contact> matchingContacts = new ArrayList<Contact>();
		if (firstName == null)
			firstName = "";
		if (lastName == null)
			lastName = "";
		firstName = removeAccents(firstName).toLowerCase();
		lastName = removeAccents(lastName).toLowerCase();

		for (Contact c : contacts) {
			if (removeAccents(c.getFirstName()).toLowerCase().contains(firstName)
			 && removeAccents(c.getLastName()).toLowerCase().contains(lastName)) {
				matchingContacts.add(c);
			}
		}
		return matchingContacts;
	}

	public static ArrayList<Contact> parseLabriDirectory() throws IOException {
		String labriDirectory = "";
		String filepath = "com/dev/campus/directory/DirectoryLabri.txt";

		// Reading text file
		try {
			// InputStream ips = new FileInputStream(file);
			InputStream ips = Directory.class.getClassLoader().getResourceAsStream(filepath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				labriDirectory += line + "\n";
			}
			br.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}


		labriDirectory = labriDirectory.replaceAll("(\\r)|(\\n)|(\\t)", "");
		labriDirectory = labriDirectory.replaceAll("&amp;", "&");

		labriDirectory = labriDirectory.replaceFirst("<table border(.?)</table>", "");
		labriDirectory = labriDirectory.replaceFirst("<tr(.*?)</tr>", "");

		Pattern p = Pattern.compile("<td(.*?)>(.*?)</td>");
		Matcher m = p.matcher(labriDirectory);

		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Contact contact = new Contact();
		String buffer;
		int i = 1;
		while (m.find()) {
			buffer = m.group();
			buffer = buffer.replaceAll("<td(.*?)>(.*?)</td>", "$2");
			buffer = buffer.trim();

			if (i % 8 == 1) { // Name
				String name = buffer;
				int offset = name.lastIndexOf(" "); // Split first name/last name with last space
				// TODO control offset value
				String lastName = name.substring(0, offset);
				String firstName = name.substring(offset+1, name.length());
				contact.setFirstName(htmlDecode(firstName));
				contact.setLastName(htmlDecode(lastName));
			}
			else if(i % 8 == 2) { // Email
				buffer = buffer.replaceAll("<a href=\"mailto:([^\"]*)\"(.*)</a>", "$1");
				contact.setEmail(htmlDecode(buffer));
			}
			else if (i % 8 == 3) { // Telephone, Default : "+33 (0)5 40 00 "
				if (!buffer.equals("+33 (0)5 40 00")) {
					buffer = buffer.trim();
					contact.setTel(htmlDecode(buffer));
				}
			}
			else if (i % 8 == 7) { // Website
				buffer = buffer.replaceAll("<a href=\"http([^\"]*)\"(.*)</a>", "http$1");
				contact.setWebsite(htmlDecode(buffer));
			}
			else if (i % 8 == 0 && i > 0) {
				contacts.add(contact);
				contact = new Contact();
			}
			i++;
		}

		/* Retrieve the first name/last name with the email adress
		// -----------------------------------------  
		String mail = "john-doe.smith@labri.fr";
		int offset = mail.indexOf(".");
		String firstName = mail.substring(0, offset);
		firstName = capitalize(firstName);
		String lastName = mail.substring(offset+1, mail.indexOf("@"));
		//*/

		return contacts;
	}


	public static String htmlDecode(String str) {
		return Html.fromHtml(str).toString();
	}

	public static String removeAccents(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}

	public static String capitalize(String str) {
		str = str.toLowerCase();
		boolean charReplaced = false;
		for (int k = 0; k < str.length(); k++) {
			char currentChar = str.charAt(k);
			if (currentChar < 97 || currentChar > 122) // detecting new word, currentChar not in [a-z]
				charReplaced = false;
			if (charReplaced == false && (currentChar > 96 && currentChar < 123)) { // currentChar in [a-z]
				str = str.substring(0, k) + str.substring(k, k+1).toUpperCase() + str.substring(k+1); // capitalize currentChar in string
				charReplaced = true;
			}
		}
		return str;
	}
}
