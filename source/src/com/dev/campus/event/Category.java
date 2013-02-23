package com.dev.campus.event;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Category {
	
	UB1_ALL_NEWS(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=114", R.string.ub1_all_news),
	UB1_UNIVERSITY(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=100", R.string.ub1_university),
	UB1_FORMATION(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=101", R.string.ub1_formation),
	UB1_RESEARCH(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=102", R.string.ub1_research),
	UB1_INTERNATIONAL(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=103", R.string.ub1_international),
	UB1_STUDENTS(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=104", R.string.ub1_student_life),
	UB1_COMPANIES(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=105", R.string.ub1_companies),
	UB1_PRESS(CategoryType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=106", R.string.ub1_press),
	
	LABRI_NEWS(CategoryType.LABRI_FEED, "http://www.labri.fr/rss/rss.php", R.string.labri);
	
	public enum CategoryType {
		UB1_FEED,
		LABRI_FEED;
	}
	
	private CategoryType mType;
	private String mUrl;
	private int mName;
	public static ArrayList<Category> ub1Feeds = new ArrayList<Category>();
	public static ArrayList<Category> labriFeeds = new ArrayList<Category>();
	
	static {
		for (Category c : Category.values()) {
			if (c.getType().equals(CategoryType.UB1_FEED))
				ub1Feeds.add(c);
			else if (c.getType().equals(CategoryType.LABRI_FEED))
				labriFeeds.add(c);
		}
	}
	
	
	private Category(CategoryType type, String url, int name) {
		mType = type;
		mUrl = url;
		mName = name;
	}
	
	public CategoryType getType() {
		return mType;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public String toString() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getString(mName);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Category> getUb1Feeds() {
	    return (ArrayList<Category>)ub1Feeds.clone();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Category> getLabriFeeds() {
	    return (ArrayList<Category>)labriFeeds.clone();
	}
}
