package com.dev.campus.event;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Category {
	MAIN_EVENTS(R.string.category_main_events, Feed.UB1_NEWS_ALL, Feed.LABRI_NEWS),
	UNIVERSITY(R.string.category_university, Feed.UB1_NEWS_UNIVERSITY),
	FORMATION(R.string.category_formation, Feed.UB1_NEWS_FORMATION),
	RESEARCH(R.string.category_research, Feed.UB1_NEWS_RESEARCH),
	INTERNATIONAL(R.string.category_international, Feed.UB1_NEWS_INTERNATIONAL),
	STUDENTS(R.string.category_student_life, Feed.UB1_NEWS_STUDENTS),
	COMPANIES(R.string.category_companies, Feed.UB1_NEWS_COMPANIES),
	PRESS(R.string.category_press, Feed.UB1_NEWS_PRESS);
	
	private int mName;
	private Feed[] mFeeds;
	
	private Category(int name, Feed... feeds) {
		mName = name;
		mFeeds = feeds;
	}
	
	public Feed[] getFeeds() {
		return mFeeds;
	}
	
	public String toString() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getString(mName);
	}

}
