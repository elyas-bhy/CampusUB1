package com.dev.campus.event;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dev.campus.util.TimeExtractor;
import com.dev.campus.event.Feed.FeedType;

public class EventParser {

	private XmlPullParser mParser;
	
	private ArrayList<Event> mParsedEvents;
	private ArrayList<Date> mParsedEventDates;

	public EventParser() throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		mParser = factory.newPullParser();
	}
	
	public ArrayList<Event> getParsedEvents() {
		return mParsedEvents;
	}
	
	public ArrayList<Date> getParsedEventDates() {
		return mParsedEventDates;
	}

	private void setInput(Feed feed) throws IOException, XmlPullParserException {
		URL url = new URL(feed.getUrl());
		InputStream stream = url.openStream();
		mParser.setInput(stream, null);
	}

	public void parseEvents(Category category, ArrayList<Event> existingEvents) 
			throws XmlPullParserException, IOException, ParseException {
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Date> dates = new ArrayList<Date>();
		for (Feed feed : category.getFeeds()) {
			if (feed.getType().isFiltered()) {
				setInput(feed);
				Event event = new Event();
				Date buildDate = new Date(0);

				int eventType = mParser.getEventType();
				parseLoop:
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						if (mParser.getName().equals("lastBuildDate")) {
							buildDate = TimeExtractor.createDate(mParser.nextText(), "EEE, d MMM yyyy HH:mm:ss Z");
							dates.add(buildDate);
						}
						if (mParser.getName().equals("item")) {
							event = new Event();
						} 
						if (mParser.getName().equals("title")) {
							event.setTitle(mParser.nextText());
						} 
						if (mParser.getName().equals("description")) {
							String description = mParser.nextText();
							event.setDescription(description);
							if (feed.getType().equals(FeedType.LABRI_FEED))
								event.setDetails(description);
						} 
						if (mParser.getName().equals("content:encoded")) {
							event.setDetails(mParser.nextText());
						}
						if (mParser.getName().equals("pubDate")) {
							Date d = null;
							String text = mParser.nextText();
							d = TimeExtractor.getCorrectDate(text, event.getDetails());
							event.setDate(d);
						}
					}
					else if (eventType == XmlPullParser.END_TAG) {
						if (mParser.getName().equals("item")) {
							event.setCategory(category.toString());
							event.setSource(feed.getType());
							if (!event.getTitle().equals("")) {
								if(existingEvents.contains(event)) {
									break parseLoop;
								}
								events.add(event);
							}
						}
					}
					eventType = mParser.nextToken();
				}
			}
			//else if (feed.getType().equals(FeedType.LABRI_FEED_HTML)&& CampusUB1App.persistence.isFilteredLabri())
				//events = EventHtmlParser.parse(events, this.mCategory);
		}
		
		events.addAll(existingEvents);
		mParsedEvents = events;
		mParsedEventDates = dates;
	}

	public boolean isLatestVersion(Category category, List<Date> dates) throws IOException, XmlPullParserException, ParseException {
		int i = 0;
		for (Feed feed : category.getFeeds()) {
			if (feed.getType().isFiltered()) {
				setInput(feed);
				Date buildDate;
				
				int eventType = mParser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						if (mParser.getName().equals("lastBuildDate")) {
							buildDate = TimeExtractor.createDate(mParser.nextText(), "EEE, d MMM yyyy HH:mm:ss Z");
							if (dates.get(i++).getTime() != buildDate.getTime())
								return false;
						}
					}
					eventType = mParser.nextToken();
				}
			} else {
				//No build dates in HTML pages, so always update
				return false;
			}
		}
		return true;
	}
}
