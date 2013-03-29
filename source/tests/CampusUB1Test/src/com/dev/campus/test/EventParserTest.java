package com.dev.campus.test;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.test.AndroidTestCase;

import com.dev.campus.event.Category;
import com.dev.campus.event.Event;
import com.dev.campus.event.EventParser;
import com.dev.campus.event.Feed;


public class EventParserTest extends AndroidTestCase  {

	private EventParser mEventParser;
	private ArrayList<Date> mDates;

	public EventParserTest() {
		super();
	}

	protected void setUp() throws Exception {
		mEventParser = new EventParser();
		mDates = new ArrayList<Date>();
		mDates.add(new Date(0));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIsLatestVersionHtmlFalse() throws ParseException, IOException {
		assertFalse(mEventParser.isLatestVersion(Category.AUTRES, mDates));
	}


	public void testNotLatestVersion() throws ParseException, IOException {
		assertFalse(mEventParser.isLatestVersion(Category.UNIVERSITY, mDates));
	}

	public void testIsLatestVersion() throws ParseException, IOException {
		mEventParser.parseRSS(Category.UNIVERSITY, Feed.UB1_NEWS_UNIVERSITY, new ArrayList<Event>());
		assertTrue(mEventParser.isLatestVersion(Category.UNIVERSITY, mEventParser.getParsedBuildDates()));
	}

}
