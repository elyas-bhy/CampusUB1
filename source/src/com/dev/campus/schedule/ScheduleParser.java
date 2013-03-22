package com.dev.campus.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ScheduleParser {

	public ArrayList<ScheduleGroup> parseFeed(String url) throws MalformedURLException, IOException {
		// Cannot open iso-8859-1 encoding directly with Jsoup
		InputStream input = new URL(url).openStream();
		Document xmlDoc = Jsoup.parse(input, "CP1252", url);

		ArrayList<ScheduleGroup> allGroups = new ArrayList<ScheduleGroup>();
		for (Element resource : xmlDoc.select("resource")) {
			ScheduleGroup group = new ScheduleGroup(); 
			Elements name = resource.select("name");
			Elements link = resource.select("link[class=xml]");
			String xmlLink = link.attr("href");
			group.setGroup(name.text());
			group.setUrl(xmlLink);
			allGroups.add(group);
		}

		return allGroups;
	}

}