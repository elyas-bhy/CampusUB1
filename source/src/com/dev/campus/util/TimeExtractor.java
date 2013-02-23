package com.dev.campus.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dev.campus.CampusUB1App;

@SuppressLint("SimpleDateFormat")
public class TimeExtractor {

	public static String[] parseTime(String text){
		String hours = "[0-2]?[0-9]";
		String minutes = "[0-5][0-9]";
		String time = hours + "[hH:](" + minutes + ")?";

		Pattern p = Pattern.compile(time);
		Matcher m = p.matcher(text);
		
		if(m.find()) {
			String s = m.group();
			if(s != null) {
				s = s.toLowerCase();
				// Split the time into a table to help with the management
				if(s.contains("h")) {
					return s.split("h");
				} else {
					return s.split(":");
				}
			}
		}
		return null;
	}

	public static Date createDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return sdf.parse(date);
	}

	public static Date getCorrectDate(String date, String text) throws ParseException{
		String[] time = parseTime(text);
		Date d = createDate(date);
		
		if(time != null) {
			if(time.length > 1) { // Means we have parsed minutes
				d.setMinutes(Integer.parseInt(time[1]));
			} else {
				// No minutes parsed, assume the time is at the start of the specified hour
				d.setMinutes(0);
			}
			d.setHours(Integer.parseInt(time[0]));
		} else {
			/* no time parsed in text, set time to 00:00 to avoid displaying
			 * the time at which the article was posted
			 */
			d.setHours(0);
			d.setMinutes(0);
		}
		return d;
	}
}