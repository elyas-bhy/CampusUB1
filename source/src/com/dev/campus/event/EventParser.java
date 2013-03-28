package com.dev.campus.event;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import com.dev.campus.CampusUB1App;
import com.dev.campus.event.Feed.FeedType;
import com.dev.campus.util.TimeExtractor;

public class EventParser {
	
	// number of seconds since January 1st 1970
	private final int MONTH_SECONDS = 2678400;

	private ArrayList<Event> mParsedEvents;
	private ArrayList<Date> mParsedBuildDates;

	public ArrayList<Event> getParsedEvents() {
		return mParsedEvents;
	}

	public ArrayList<Date> getParsedBuildDates() {
		return mParsedBuildDates;
	}

	public void parseEvents(Category category, ArrayList<Event> existingEvents) throws IOException, ParseException {
		mParsedEvents = new ArrayList<Event>();
		mParsedBuildDates = new ArrayList<Date>();
		for (Feed feed : category.getFeeds()) {
			if (feed.getType().isSubscribedRSS()) {
				parseRSS(category, feed, existingEvents);
			}
			else if (feed.getType().equals(FeedType.LABRI_FEED_HTML) && CampusUB1App.persistence.isSubscribedLabri()) {
				parseLabriSection(category, feed, existingEvents);
			}
		}

		mParsedEvents.addAll(existingEvents);
	}

	private void parseRSS(Category category, Feed feed, ArrayList<Event> existingEvents) 
			throws IOException, ParseException {
		Event event;
		Date buildDate = new Date(0);

		String url = feed.getUrl();
		InputStream input = new URL(url).openStream();
		Document xmlDoc = Jsoup.parse(input, "UTF-8", url);
		if (xmlDoc.toString().contains("iso-8859-1")) {
			InputStream inputISO = new URL(url).openStream();
			xmlDoc = Jsoup.parse(inputISO, "CP1252", url);
		}

		String lastBuildDate = xmlDoc.select("lastBuildDate").text();
		buildDate = TimeExtractor.createDate(lastBuildDate, "EEE, d MMM yyyy HH:mm:ss Z");
		mParsedBuildDates.add(buildDate);

		for (Element item : xmlDoc.select("item")) {
			event = new Event();

			String title = item.select("title").text();
			event.setTitle(title);

			String description = item.select("description").text();
			event.setDescription(description);

			if (feed.getType().equals(FeedType.LABRI_FEED))
				event.setDetails(description);
			else
				event.setDetails(item.select("content|encoded").text());

			Date d = null;
			String pubDate = item.select("pubDate").text();
			TimeExtractor.getCorrectDate(pubDate, event.getDetails(), event.getStartDate(), event.getEndDate());
			
			event.setStartDate(d);

			event.setCategory(category.toString());
			event.setSource(feed.getType());

			if (!event.getTitle().equals("")) {
				if (existingEvents.contains(event)) {
					break;
				}
				mParsedEvents.add(event);
			}
		}
	}

	public void parseLabriSection(Category category, Feed feed, ArrayList<Event> existingEvents) throws IOException, ParseException {
		int months = CampusUB1App.persistence.getUpcomingEventsRange();
		int timestamp = MONTH_SECONDS * months;
		for (int i = 0; i < months; i++) {
			Long current = System.currentTimeMillis() / 1000 + timestamp;
			Connection.Response res = Jsoup.connect("http://www.labri.fr/public/actu/accueil.php")
					.userAgent("Mozilla")
					.data("choix_intervalle", "mois")
					.data("mois", current.toString())
					.data(feed.getUrl(), "1")
					.referrer("http://www.labri.fr/public/actu/index.php")
					.method(Method.POST)
					.execute();
			parseDoc(res.parse(), category, existingEvents);
			timestamp -= MONTH_SECONDS;
		}
		mParsedBuildDates.add(new Date());
	}

	@SuppressLint("SimpleDateFormat")
	private void parseDoc(Document doc, Category category, ArrayList<Event> existingEvents) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Elements tabBase = doc.getElementsByTag("table");
		tabBase.remove(0);
		tabBase.remove(0);
		tabBase.remove(0);
		for (Element element : tabBase) {
			int i = 0;
			Event event = new Event();
			Elements cases = element.getElementsByTag("td");
			Date dateStart = new Date();
			Date dateEnd = new Date();
			if (!cases.isEmpty()) {
				for (Element c : cases) {
					switch (i) {	
					case 0 :
						dateStart = sdf.parse(c.text());						
						break;
					case 1:
						event.setTitle(c.text());
						break;
					case 2:
						String content = c.text();
						String[] dateLoc = content.split(" ");
						String loc = "";
						for (int l = 1; l < dateLoc.length; l++) {
							loc += " " + dateLoc[l];
						}
						event.setLocation(loc);
						TimeExtractor.getDateLabri(dateLoc[0], dateStart, dateEnd);
						break;
					case 3:
						event.setDetails(c.text());
						break;
					default :
						break;
					}
					i++;
				}
			}
			event.setCategory(category.toString());
			event.setSource(FeedType.LABRI_FEED_HTML);
			event.setStartDate(dateStart);
			event.setEndDate(dateEnd);
			if (!event.getTitle().equals("") || !event.getDetails().equals(""))
				if (existingEvents.contains(event))
					break;
			mParsedEvents.add(event);
		}
	}

	public boolean isLatestVersion(Category category, List<Date> dates) throws ParseException, IOException {
		int i = 0;
		for (Feed feed : category.getFeeds()) {
			if (feed.getType().isSubscribedRSS()) {
				Date buildDate;
				String url = feed.getUrl();
				InputStream input = new URL(url).openStream();
				Document xmlDoc = Jsoup.parse(input, "UTF-8", url);
				if (xmlDoc.toString().contains("iso-8859-1")) {
					InputStream inputISO = new URL(url).openStream();
					xmlDoc = Jsoup.parse(inputISO, "CP1252", url);
				}

				String lastBuildDate = xmlDoc.select("lastBuildDate").text();
				buildDate = TimeExtractor.createDate(lastBuildDate, "EEE, d MMM yyyy HH:mm:ss Z");
				if (dates.get(i++).getTime() != buildDate.getTime())
					return false;

			}
			else {
				return false;
			}
		}
		return true;
	}
}
