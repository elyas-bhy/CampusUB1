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

package com.dev.campus.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dev.campus.CampusUB1App;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;

/**
 * Class responsible for parsing semester groups and schedule events,
 * as well as providing integration with the device's default calendar.
 * @author CampusUB1 Development Team
 *
 */
public class ScheduleParser {

	// Encoding character set
	private static final String CHARSET = "CP1252";

	/**
	 * Parses the list of groups for a specified semester
	 * @param url the URL of the specified semester
	 * @return list of all groups available in the specified semester
	 * @throws IOException
	 */
	public ArrayList<Group> fetchGroups(String url) throws IOException {
		ArrayList<Group> allGroups = new ArrayList<Group>();
		Document xmlDoc;
		try {
			// Cannot open iso-8859-1 encoding directly with Jsoup
			InputStream input = new URL(url).openStream();
			xmlDoc = Jsoup.parse(input, CHARSET, url);
		} catch(IOException e) {
			throw e;
		}

		for (Element resource : xmlDoc.select("resource")) {
			Group group = new Group(); 
			Elements name = resource.select("name");
			Elements link = resource.select("link[class=xml]");
			String xmlLink = link.attr("href");
			group.setTitle(name.text());
			group.setUrl(url.substring(0, url.length()-10)+xmlLink); // remove "finder.xml" to url and append file link
			allGroups.add(group);
		}

		return allGroups;
	}

	/**
	 * Parses the events of a given schedule,
	 * then inserts them to the default calendar
	 * @param url the URL of the schedule to parse
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void parseSchedule(String url) throws IOException, ParseException {
		Document xmlDoc;
		try {
			// Cannot open iso-8859-1 encoding directly with Jsoup
			InputStream input = new URL(url).openStream();
			xmlDoc = Jsoup.parse(input, CHARSET, url);
		} catch(IOException e) {
			throw e;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyH:m");
		ArrayList<ContentProviderOperation> scheduleEvents = new ArrayList<ContentProviderOperation>();

		for (Element span : xmlDoc.select("event[date]")) {
			String calStartTime = span.select("starttime").text();
			String calEndTime = span.select("endtime").text();
			String calType = span.select("category").text();
			String calModule = span.select("module").text();
			String calRoom = span.select("room").text();
			String calStaff = span.select("staff").text();
			String calNotes = span.select("notes").text();
			String calGroup = "";
			Elements group = span.select("group");
			for (Element groupItem : group.select("item")) {
				calGroup += groupItem.text()+"\n";
			}

			// Calculate real dates
			String calParseDate = span.attr("date");
			String calDay = span.select("day").text();
			Date startDate = dateFormat.parse(calParseDate + calStartTime);
			Date endDate = dateFormat.parse(calParseDate + calEndTime);
			long calStartDate = startDate.getTime() + 86400000*Integer.parseInt(calDay); // 86400000 : number of milliseconds per day
			long calEndDate = endDate.getTime() + 86400000*Integer.parseInt(calDay);

			String calTitle = (calType.equals("")) ? calModule : calType+" "+calModule ;
			String calDesc  = (calNotes.equals("")) ? "" : calNotes+"\n" ;
			calDesc = (calStaff.equals("")) ? calDesc+calGroup+"\n" : calDesc+calGroup+"Prof: "+calStaff+"\n" ;

			if (calStartDate > 0 && !calTitle.equals("")) {
				scheduleEvents.add(ContentProviderOperation.newInsert(Events.CONTENT_URI)
						.withValue(Events.DTSTART, calStartDate)
						.withValue(Events.DTEND, calEndDate)
						.withValue(Events.TITLE, calTitle)
						.withValue(Events.EVENT_LOCATION, calRoom)
						.withValue(Events.DESCRIPTION, calDesc)
						.withValue(Events.CALENDAR_ID, 1)
						.withValue(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID())
						.build());
			}
		}
		batchInsertEvents(scheduleEvents);

	}

	/**
	 * Performs a batch insertion of calendar events
	 * @param ops
	 */
	private static void batchInsertEvents(ArrayList<ContentProviderOperation> ops) {
		try {
			if (ops.size() > 0) {
				ContentResolver cr = CampusUB1App.getInstance().getContentResolver();
				cr.applyBatch(CalendarContract.AUTHORITY, ops);
			}
		} catch (Exception e) {
			Log.e("LogTag", "batch insert failed: ", e);
		}
	}

}