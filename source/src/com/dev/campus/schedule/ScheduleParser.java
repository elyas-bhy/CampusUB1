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

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;


public class ScheduleParser {
	
	private final String CHARSET = "CP1252";

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

	public void parseSchedule(Context context, String url) throws MalformedURLException, IOException, ParseException {
		// Cannot open iso-8859-1 encoding directly with Jsoup
		InputStream input = new URL(url).openStream();
		Document xmlDoc = Jsoup.parse(input, CHARSET, url);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyH:m");

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
				/*
				Log.d("LogTag", "date: "+calStartDate);
				Log.d("LogTag", "startTime: "+calStartTime);
				Log.d("LogTag", "endTime: "+calEndTime);
				Log.d("LogTag", "type: "+calType);
				Log.d("LogTag", "module: "+calModule);
				Log.d("LogTag", "room: "+calRoom);
				Log.d("LogTag", "staff: "+calStaff);
				Log.d("LogTag", "group: "+calGroup);
				Log.d("LogTag", "   ------------   ");
				Log.d("LogTag", "calTitle: "+calTitle);
				Log.d("LogTag", "calDesc: "+calDesc);
				Log.d("LogTag", " ");
				 */
				synchronizeCalendar(1, context, calTitle, calDesc, calRoom, calStartDate, calEndDate); 
			}
		}

	}

	private void synchronizeCalendar(long calID, Context context, String title, String desc, String location, long startTime, long endTime) {
		try {
			ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
			//new batch operation
			ops.add(ContentProviderOperation.newInsert(Events.CONTENT_URI)
					.withValue(Events.DTSTART, startTime) //long
					.withValue(Events.DTEND, endTime) //long
					.withValue(Events.TITLE, title) //String
					.withValue(Events.EVENT_LOCATION, location) //String
					.withValue(Events.DESCRIPTION, desc) //String
					.withValue(Events.CALENDAR_ID, calID) //long
					.withValue(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID()) //String
					.build());

			if (ops.size() > 0) {
				ContentResolver cr = context.getContentResolver();
				ContentProviderResult[] results = cr.applyBatch(CalendarContract.AUTHORITY, ops);
				for (ContentProviderResult result : results) {
					Log.v("LogTag", "addBatchEvent: " + result.uri.toString());
				}
			} else {
				Log.w("LogTag", "No batch operations found! Do nothing");
			}

		} catch (Exception e) {
			Log.e("LogTag", "synchronizeCalendar", e);
		}
	}

}