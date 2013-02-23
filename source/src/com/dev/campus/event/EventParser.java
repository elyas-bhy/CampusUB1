package com.dev.campus.event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dev.campus.event.Category.CategoryType;

import android.os.Environment;

public class EventParser {


	private XmlPullParser mParser;
	private List<Event> mEvents;
	private Category mCategory;

	public EventParser() throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		mParser = factory.newPullParser();
	}

	public void setInput(Category category) throws IOException, XmlPullParserException {
		mCategory = category;
		URL url = new URL(mCategory.getUrl());
		InputStream stream = url.openStream();
		mParser.setInput(stream, null);
	}

	public List<Event> getEvents() {
		return mEvents;
	}

	public List<Event> parseEvents() throws XmlPullParserException, IOException {
		ArrayList<Event> events = new ArrayList<Event>();
		Event event = new Event();

		int eventType = mParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				if (mParser.getName().equals("item")) {
					event = new Event();
				} 
				if (mParser.getName().equals("title")) {
					event.setTitle(mParser.nextText());
				} 
				if (mParser.getName().equals("description")) {
					String description = mParser.nextText();
					event.setDescription(description);
					if (mCategory.getType().equals(CategoryType.LABRI_FEED))
						event.setDetails(description);
				} 
				if (mParser.getName().equals("content:encoded")) {
					event.setDetails(mParser.nextText());
				}
				if (mParser.getName().equals("pubDate")) {
					event.setDate(mParser.nextText());
				}
			}
			else if (eventType == XmlPullParser.END_TAG) {
				if (mParser.getName().equals("item")) {
					event.setCategory("News"); //temporary
					if (!event.getTitle().equals(""))
						events.add(event);
				}
			}
			eventType = mParser.nextToken();
		}

		mEvents = events;
		return mEvents;
	}

	public void saveEvents() throws XmlPullParserException {
		ObjectOutputStream oos = null;
		try {
			File history = new File(Environment.getExternalStorageDirectory() + "/history.dat");
			history.getParentFile().createNewFile();
			FileOutputStream fout = new FileOutputStream(history);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(mEvents);
		}
		catch (FileNotFoundException ex) {
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
