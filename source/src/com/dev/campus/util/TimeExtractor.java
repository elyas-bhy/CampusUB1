package com.dev.campus.util;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("SimpleDateFormat")
public class TimeExtractor {

	public static List<String[]> parseTime(String text){

		ArrayList<String[]> dates = new ArrayList<String[]>();
		String hours = "[0-2]?[0-9]";
		String minutes = "[0-5][0-9]";
		String interval = "de" + hours + "[hH\"heure\":](" + minutes + ")?" 
						+ "à" + hours + "[hH\"heure\":](" + minutes + ")?";
		String time = hours + "[hH:](" + minutes + ")?";

		Pattern p = Pattern.compile(interval);
		Pattern p2 = Pattern.compile(time);
		Matcher m = p.matcher(text);
		Matcher m2 = p2.matcher(text);

		if (m.find()) {
			String s = m.group();
			if(s != null) {
				s = s.toLowerCase().replace("de", "");
				String[] tabDate = s.split("à");
				for (int i=0; i<tabDate.length;i++){
					if (tabDate[0].contains("h")) {
						dates.add(tabDate[0].split("h"));
					}else if (tabDate[0].contains("heure")){
						dates.add(tabDate[0].split("heure"));
					} else {
						dates.add(tabDate[0].split(":"));
					}
				}
			}
		}
		else if (m2.find()) {
			String s = m2.group();
			if (s != null) {
				s.toLowerCase();
				// Split the time into a table to help with the management
				if(s.contains("h")) {
					dates.add(s.split("h"));
				}if (s.contains("heure")){
					dates.add(s.split("heure"));
				} else {
					dates.add(s.split(":"));
				}
			}
		}
		return dates;
	}

	public static Date createDate(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		return sdf.parse(date);
	}

	public static void getCorrectDate(String date, String text, Date start, Date end) throws ParseException {
		List<String[]> time = parseTime(text);
		String format = "EEE, d MMM yyyy HH:mm:ss Z";
		start = createDate(date, format);
		end = createDate(date, format);

		if(time != null) {
			if(time.get(0).length > 1) { // Means we have parsed minutes
				start.setMinutes(Integer.parseInt(time.get(0)[1]));
			} else {
				// No minutes parsed, assume the time is at the start of the specified hour
				start.setMinutes(0);
			}
			start.setHours(Integer.parseInt(time.get(0)[0]));
			if (time.size() > 1){
				if(time.get(1).length > 1) { // Means we have parsed minutes
					end.setMinutes(Integer.parseInt(time.get(1)[1]));
				} else {
					// No minutes parsed, assume the time is at the start of the specified hour
					end.setMinutes(0);
				}
				end.setHours(Integer.parseInt(time.get(1)[0]));
			} if (time.size()==1){
				end.setMinutes(start.getMinutes());
				end.setHours(start.getHours());
			} else {
				/* no time parsed in text, set time to 00:00 to avoid displaying
				 * the time at which the article was posted
				 */
				start.setMinutes(0);
				start.setHours(0);
				end.setMinutes(0);
				end.setHours(0);
			}
		}
	}
	
	public static void getDateLabri(String date, Date start, Date end) {
		String[] tabDate = date.split("-");
		String[] tabDate2 = tabDate[0].split(":");
		String[] tabDate3 = tabDate[1].split(":");
		
		start.setHours(Integer.parseInt(tabDate2[0]));
		start.setMinutes(Integer.parseInt(tabDate2[1]));
		end.setHours(Integer.parseInt(tabDate3[0]));
		end.setMinutes(Integer.parseInt(tabDate3[1]));
	}


}