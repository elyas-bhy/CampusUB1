package com.dev.campus.event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dev.campus.CampusUB1App;
import com.dev.campus.util.TimeExtractor;
import com.dev.campus.event.Feed.FeedType;

import android.content.Context;

public class EventParser {

	private Context mContext;
	private XmlPullParser mParser;
	private List<Event> mEvents;
	private List<Date> mEventDates;
	private Category mCategory;

	public EventParser(Context context) throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		mParser = factory.newPullParser();
		mContext = context;
	}
	
	public List<Event> getEvents() {
		return mEvents;
	}

	private void setInput(Feed feed) throws IOException, XmlPullParserException {
		URL url = new URL(feed.getUrl());
		InputStream stream = url.openStream();
		mParser.setInput(stream, null);
	}

	public void parseEvents(Category category) throws XmlPullParserException, IOException, ParseException {
		mCategory = category;
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Date> dates = new ArrayList<Date>();
		for (Feed feed : mCategory.getFeeds()) {
			if (feed.getType().equals(FeedType.UB1_FEED) || feed.getType().equals(FeedType.LABRI_FEED)) {
				setInput(feed);
				Event event = new Event();
				Date date = new Date(0);

				int eventType = mParser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						if (mParser.getName().equals("lastBuildDate")) {
							date = TimeExtractor.createDate(mParser.nextText(), "EEE, d MMM yyyy HH:mm:ss Z");
						}
						if (mParser.getName().equals("item")) {
							event = new Event();
							date = new Date(0);
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
							String d = null;
							try {
								String text = mParser.nextText();
								d = TimeExtractor.getCorrectDate(text, event.getDetails());
								event.setDate(d);
							} catch(Exception e){
								CampusUB1App.LogD("DATE: " + d);
							}
						}
					}
					else if (eventType == XmlPullParser.END_TAG) {
						if (mParser.getName().equals("item")) {
							event.setCategory(mCategory.toString());
							event.setSource(feed.getType());
							if (!event.getTitle().equals("")) {
								events.add(event);
								dates.add(date);
							}
						}
					}
					eventType = mParser.nextToken();
				}
			}
		}

		mEvents = events;
		mEventDates = dates;
	}

	public void saveEvents() throws XmlPullParserException {
		ObjectOutputStream oos = null;
		try {
			File history = new File(mContext.getFilesDir() + "/history_" + mCategory.toString().replace(" ", "") + ".dat");
			history.getParentFile().createNewFile();
			FileOutputStream fout = new FileOutputStream(history);
			oos = new ObjectOutputStream(fout);
			
			SimpleEntry<List<Event>,List<Date>> map = new SimpleEntry<List<Event>,List<Date>>(mEvents, mEventDates);
			oos.writeObject(map);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();  
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
