package com.dev.campus.util;

import android.annotation.SuppressLint;

import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static Date createDate(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		return sdf.parse(date);
	}
	
	public static String formatDate(Date d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		return sdf.format(d);
	}

	public static String getCorrectDate(String date, String text) throws ParseException{
		String[] time = parseTime(text);
		String format = "EEE, d MMM yyyy HH:mm:ss Z";
		Date d = createDate(date, format);
		format = "EEE, d MMM yyyy HH:mm";
		
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
			format = "EEE, d MMM yyyy";
		}
		return formatDate(d, format);
	}
}