package com.dev.campus.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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


public class ScheduleParser {

	private static final String CHARSET = "CP1252";

	public ArrayList<ScheduleGroup> parseFeed(String url) throws MalformedURLException, IOException {
		// Cannot open iso-8859-1 encoding directly with Jsoup
		InputStream input = new URL(url).openStream();
		Document xmlDoc = Jsoup.parse(input, CHARSET, url);

		ArrayList<ScheduleGroup> allGroups = new ArrayList<ScheduleGroup>();
		for (Element resource : xmlDoc.select("resource")) {
			ScheduleGroup group = new ScheduleGroup(); 
			Elements name = resource.select("name");
			Elements link = resource.select("link[class=xml]");
			String xmlLink = link.attr("href");
			group.setGroup(name.text());
			group.setUrl(url.substring(0, url.length()-10)+xmlLink); // remove "finder.xml" to url and append file link
			allGroups.add(group);
		}

		return allGroups;
	}

	public static void parseSchedule(String url) throws MalformedURLException, IOException, ParseException {
		// Cannot open iso-8859-1 encoding directly with Jsoup
		InputStream input = new URL(url).openStream();
		Document xmlDoc = Jsoup.parse(input, CHARSET, url);
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
						.withValue(Events.DTSTART, calStartDate) // long
						.withValue(Events.DTEND, calEndDate) // long
						.withValue(Events.TITLE, calTitle) // String
						.withValue(Events.EVENT_LOCATION, calRoom) // String
						.withValue(Events.DESCRIPTION, calDesc) // String
						.withValue(Events.CALENDAR_ID, 1) // long
						.withValue(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID()) // String
						.build());
			}
		}
		batchInsertEvents(scheduleEvents);

	}

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