package com.dev.campus.event;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class EventParser {
	
	private XmlPullParser mParser;
	
	public EventParser() throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		mParser = factory.newPullParser();
	}
	
	public void setInput(String address) throws IOException, XmlPullParserException {
		URL url = new URL(address);
		InputStream stream = url.openStream();
		mParser.setInput(stream, null);
	}
	
	public List<Event> getEvents() throws XmlPullParserException, IOException {
		ArrayList<Event> events = new ArrayList<Event>();
		Event event = new Event();

		int eventType = mParser.getEventType();
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	        if (eventType == XmlPullParser.START_DOCUMENT) {
	        	//TODO
	        } 
	        else if (eventType == XmlPullParser.END_DOCUMENT) {
	        	//TODO
	        }
	        else if (eventType == XmlPullParser.START_TAG) {
	        	if (mParser.getName().equals("item")) {
	        		event = new Event();
	        	} 
	        	if (mParser.getName().equals("title")) {
	        		event.setTitle(mParser.nextText());
	        	} 
	        	if (mParser.getName().equals("description")) {
	        		event.setDescription(mParser.nextText());
	        	} 
	        	if (mParser.getName().equals("content:encoded")) {
        			event.setDetails(mParser.nextText());
	        	} 
	        }
	        else if (eventType == XmlPullParser.END_TAG) {
	        	if (mParser.getName().equals("item")) {
	        		event.setCategory("News"); //temporary
	        		events.add(event);
	        	}
	        }
	        eventType = mParser.nextToken();
	    }
		return events;
	}
}
