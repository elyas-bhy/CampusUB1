package com.dev.campus.directory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class Directory {
	
	public static String htmlDecode(String str) {
		Spanned spa = Html.fromHtml(str);
		return spa.toString();
	}
	
	public static String MyRegex(String str, String pattern) {
		String res = "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		while(m.find()) {
			res += m.group();
		}
		return res;
	}
	
	public static String capitalize(String str) {
		boolean replaced = false;
		for(int k=0; k<str.length(); k++) {
			char c = str.charAt(k);
			if( c < 97 || c > 122 )
				replaced = false;
			if( replaced==false && (c > 96 && c < 123) ) {
			    str = str.substring(0, k) + str.substring(k, k+1).toUpperCase() + str.substring(k+1);
			    replaced = true;
			}
		}
		return str;
	}
	
	public ArrayList<Contact> labriDirectoryParser() throws IOException {
		
		String dirContent="";
		String filepath = "com/dev/campus/directory/DirectoryLabri.txt";
		
		// Lecture du fichier texte
		try {
			InputStream ips = getClass().getClassLoader().getResourceAsStream(filepath);
			//InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while( (line=br.readLine())!=null ) {
				dirContent+=line+"\n";
			}
			br.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		// Traitement de l'input
		dirContent = dirContent.replaceAll("(\\r)|(\\n)|(\\t)", "");
		dirContent = dirContent.replaceAll("&amp;", "&");
		
		// Récupération du tableau
		dirContent = MyRegex(dirContent, "<table border(.*?)</table>");
		
		String tmp;
		Pattern p;
		Matcher m;
		
		// On enlève la première ligne qui contient les en-têtes des colonnes
		tmp = "";
		p = Pattern.compile("<tr(.*?)</tr>");
		m = p.matcher(dirContent);
		int k=0;
		while(m.find()) {
			if( k>0 )
				tmp += m.group();
			k++;
		}
		dirContent = tmp;
		
		tmp = "";
		p = Pattern.compile("<td(.*?)>(.*?)</td>");
		m = p.matcher(dirContent);
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Contact contact;
		int i=1;
		while(m.find()) {
			contact = new Contact();
			tmp = m.group();
			tmp = tmp.replaceAll("<td(.*?)>(.*?)</td>", "$2");
			tmp = htmlDecode(tmp.trim());
			
			if( i%8==1 ) { // Nom-Prénom
				String name = tmp;
				int offset = name.lastIndexOf(" "); // On sépare nom/prénom en fonction du dernier espace dans la chaîne
				String lastName = name.substring(0, offset);
				String firstName = name.substring(offset+1, name.length());
				//System.out.println("-"+firstName+"-"+"  -"+lastName+"-");
				Log.d("MyOwnDebug", firstName);
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
			}
			else if( i%8==2 ) { // Email
				tmp = tmp.replaceAll("<a href=\"mailto:([^\"]*)\"(.*)</a>", "$1");
				//System.out.println(tmp);
				contact.setEmail(tmp);
			}
			else if( i%8==3 ) { // Téléphone, Défaut : "+33 (0)5 40 00 "
				if( !tmp.equals("+33 (0)5 40 00") ) {
					//System.out.println(tmp);
					contact.setTel(tmp);
				}
			}
			//else if( i%8==4 ) { // Bureau
				//System.out.println(tmp);
				//contact.setDesk(tmp);
			//}
			//else if( i%8==5 ) { // Statut
				//System.out.println(tmp);
				//contact.setStatus(tmp);
			//}
			//else if( i%8==6 ) { // Equipe
				//System.out.println(tmp);
				//contact.setTeam(tmp);
			//}
			else if( i%8==7 ) { // Website
				tmp = tmp.replaceAll("<a href=\"http([^\"]*)\"(.*)</a>", "http$1");
				//System.out.println(tmp);
				contact.setWebsite(tmp);
			}
			else if( i%8==0 && i>0 ) { // Fonction
				//if( !tmp.equals("") ) {
					//System.out.println(tmp);
					//contact.setOffice(tmp);
				//}
				contacts.add(contact);
			}
				
			i++;
		}
	    
		/* Récupérer Nom/Prénom avec l'adresse email
		// -----------------------------------------  
		String mail = "john-doe.smith@labri.fr";
		int offset = mail.indexOf(".");
		String firstName = mail.substring(0, offset);
		firstName = capitalize(firstName);
		String lastName = mail.substring(offset+1, mail.indexOf("@"));
		//System.out.println(-"+firstName+"-"+"  -"+lastName+"-");
		//*/
		
		return contacts;
	}
	
}
