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

/**
 * Class responsible for parsing RSS and HTML-based feeds
 * @author CampusUB1 Development Team
 *
 */
public class EventParser {
	
	// number of seconds in a month (31 days)
	private final int MONTH_SECONDS = 2678400;

	private ArrayList<Event> mParsedEvents;
	private ArrayList<Date> mParsedBuildDates;
	
	public EventParser() {
		mParsedEvents = new ArrayList<Event>();
		mParsedBuildDates = new ArrayList<Date>();
	}

	public ArrayList<Event> getParsedEvents() {
		return mParsedEvents;
	}

	public ArrayList<Date> getParsedBuildDates() {
		return mParsedBuildDates;
	}

	/**
	 * Entry point for parsing all feeds from a specified category
	 * @param category the category for which feeds will be parsed
	 * @param existingEvents list containing previously-parsed events
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseEvents(Category category, ArrayList<Event> existingEvents) 
			throws IOException, ParseException {
		mParsedEvents = new ArrayList<Event>();
		mParsedBuildDates = new ArrayList<Date>();
		for (Feed feed : category.getFeeds()) {
			if (feed.getType().isSubscribedRSS()) {
				parseRSS(category, feed, existingEvents);
			}
			else if (feed.getType().equals(FeedType.LABRI_FEED_HTML) 
					&& CampusUB1App.persistence.isSubscribedLabri()) {
				parseLabriSection(category, feed, existingEvents);
			}
		}

		mParsedEvents.addAll(existingEvents);
	}

	/**
	 * Parses an RSS feed from a specified category
	 * @param category the containing category of the specified feed
	 * @param feed the RSS feed to parse
	 * @param existingEvents list of previously-parsed events
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseRSS(Category category, Feed feed, ArrayList<Event> existingEvents) 
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

			String pubDate = item.select("pubDate").text();
			TimeExtractor.getCorrectDate(pubDate, event);

			event.setCategory(category.toString());
			event.setSource(feed.getType());

			if (!event.getTitle().equals("") && !event.getDetails().equals("")
			 && event.getStartDate().getYear() != 70) { //ignore events having 1970 as date
				if (existingEvents.contains(event))
					break;
				mParsedEvents.add(event);
			}
		}
	}

	/**
	 * Entry point for parsing HTML-based LaBRI events
	 * @param category the containing category of the feed to parse
	 * @param feed the HTML feed to parse
	 * @param existingEvents list of previously-parsed events
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseLabriSection(Category category, Feed feed, ArrayList<Event> existingEvents) 
			throws IOException, ParseException {
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
			parseDocument(res.parse(), category, existingEvents);
			timestamp -= MONTH_SECONDS;
		}
		mParsedBuildDates.add(new Date());
	}

	/**
	 * Parses a LaBRI HTML document and retrieves found events
	 * @param doc the HTML document to parse
	 * @param category the containing category of the feed
	 * @param existingEvents list of previously-parsed events
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	private void parseDocument(Document doc, Category category, ArrayList<Event> existingEvents) 
			throws ParseException {
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
						event.setDescription(c.text());
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
			if (!event.getTitle().equals("") && !event.getDetails().equals("")
			 && event.getStartDate().getYear() != 70) { //ignore events having 1970 as date
				if (existingEvents.contains(event))
					break;
				mParsedEvents.add(event);
			}
		}
	}

	/**
	 * Checks if all feeds from a specified category are at their latest version
	 * @param category the category to check
	 * @param dates list of last-build-dates of feeds in specified category
	 * @return true if all feeds are up to date, else false
	 * @throws ParseException
	 * @throws IOException
	 */
	public boolean isLatestVersion(Category category, List<Date> dates) 
			throws ParseException, IOException {
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
				// Always return false for HTML-based events
				return false;
			}
		}
		return true;
	}
}
