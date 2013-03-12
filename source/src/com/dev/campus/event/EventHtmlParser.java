package com.dev.campus.event;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dev.campus.CampusUB1App;
import com.dev.campus.util.TimeExtractor;

public class EventHtmlParser {

	// pour faire le document complet contenant les evenement voulus pour les nb prochains mois
	public static ArrayList<Event> parse(int nbMois) throws IOException{ 
		
		ArrayList<Event> event = new ArrayList<Event>();
		int j=0;
		for (int i=0; i< nbMois; i++){
			Date d = new Date();
			Long current = (d.getTime()/1000)+j;  // nb of seconds since the 1st January 1970
			System.out.println("Date : " + current);
			Connection.Response res = Jsoup.connect("http://www.labri.fr/public/actu/accueil.php")
					.userAgent("Mozilla")
					.data("choix_intervalle", "mois")
					.data("mois", current.toString())
					.data("colloques", "1")
					.data("groupes", "1")
					.data("autres", "1")
					.data("theses", "1")
					.data("tous", "1")
					.referrer("http://www.labri.fr/public/actu/index.php")
					.method(Method.POST)
					.execute();
			Document doc = res.parse();
			event = parseDoc(doc, event);
			j+=2678400; // nb of seconds in a month (31 days)
		}
		return event;
	}

	public static ArrayList<Event> parseDoc(Document doc, ArrayList<Event> event){

		Elements tabBase = doc.getElementsByTag("table");
		Element evt = tabBase.remove(0);
		Element evt1 = tabBase.remove(0);
		Element evt2 = tabBase.remove(0);
		for (Element ev0 : tabBase){
			Event ev = new Event();
			Elements cases = ev0.getElementsByTag("td");
			int i = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			Date date = new Date();
			if (cases.isEmpty())
				CampusUB1App.LogD("empty table");
			else {
				for (Element c : cases){

					switch (i) {	
					case 0 :
						try {
							date = sdf.parse(c.text());
						} catch (ParseException e) {
							e.printStackTrace();
						}						
						break;
					case 1:
						ev.setTitle(c.text());
						break;
					case 2:
						String content = c.text();
						String[] dateLoc = content.split(" ");
						String loc = "";
						for (int l=1; l<dateLoc.length; l++){
							loc +=" " + dateLoc[l];
						}
						ev.setLocation(loc);
						String[] dateTab = TimeExtractor.parseTime(dateLoc[0]);
						date.setHours(Integer.parseInt(dateTab[0]));
						date.setMinutes(Integer.parseInt(dateTab[1]));
						break;
					case 3:
						ev.setDetails(c.text());
						break;
					default :
						break;
					}
					i++;
				}
			}
			ev.setDate(date);
			event.add(ev);
		}
		return event;	
	}
}