package com.dev.campus.event;

public class Event {
	
	private String mCategory;
	private String mTitle;
	private String mDate;
	private String mTime;
	private String mThumbnail;
	private String mDetails;
	private String mSource;
	
	public Event() {
		mCategory = null;
		mTitle = null;
		mDate = null;
		mTime = null;
		mThumbnail = null;
		mDetails = null;
		mSource = null;
	}
	
	public Event(String title) {
		this();
		mTitle = title;
	}

}
