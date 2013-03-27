package com.dev.campus.event;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Category {
	// News
	MAIN_EVENTS(R.string.category_main_events, Feed.UB1_NEWS_ALL, Feed.LABRI_NEWS),
	UNIVERSITY(R.string.category_university, Feed.UB1_NEWS_UNIVERSITY),
	FORMATION(R.string.category_formation, Feed.UB1_NEWS_FORMATION, Feed.UB1_EVENTS_FORMATION),
	RESEARCH(R.string.category_research, Feed.UB1_NEWS_RESEARCH),
	INTERNATIONAL(R.string.category_international, Feed.UB1_NEWS_INTERNATIONAL),
	STUDENTS(R.string.category_student_life, Feed.UB1_NEWS_STUDENTS),
	COMPANIES(R.string.category_companies, Feed.UB1_NEWS_COMPANIES),
	PRESS(R.string.category_press, Feed.UB1_NEWS_PRESS),
	
	// Events
	EXPOSITION(R.string.category_exposition, Feed.UB1_EVENTS_EXPOSITIONS),
	THESES(R.string.category_theses, Feed.UB1_EVENTS_THESES, Feed.LABRI_THESES),
	SEMINARS(R.string.category_colloques, Feed.UB1_EVENTS_SEMINARS, Feed.LABRI_COLLOQUES),
	GROUPES(R.string.category_groupes, Feed.LABRI_GROUPES),
	AUTRES(R.string.category_autres, Feed.LABRI_AUTRES);
	
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
