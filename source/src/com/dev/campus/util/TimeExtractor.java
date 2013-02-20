package com.dev.campus.util;

import java.util.ArrayList;

import android.text.format.Time;

public class TimeExtractor {
	
	public static String extractTimes(String s) {
		String hours = "[0-2]?[0-9]";	
		String minutes = "([0-5][0-9])?";
		String timePattern = hours + "[hH]" + minutes;
		
		String startTime = "[Dd]e " + timePattern;
		String endTime = "[Aà] " + timePattern;
		String result = com.dev.campus.directory.labri.Directory.MyRegex(s, endTime);
		return result;
	}

}
