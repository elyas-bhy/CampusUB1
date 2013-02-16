package com.dev.campus.event;

public class Event {
	
	private String mCategory;
	private String mTitle;
	private String mDate;
	private String mTime;
	private int mThumbnail;
	private String mDetails;
	private String mSource;
	
	public Event() {
		mCategory = null;
		mTitle = null;
		mDate = null;
		mTime = null;
		mThumbnail = -1;
		mDetails = null;
		mSource = null;
	}
	
	public Event(int thumbnail, String title, String category, String details) {
		this();
		mThumbnail = thumbnail;
		mTitle = title;
		mCategory = category;
		mDetails = details;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getCategory() {
		return mCategory;
	}
	
	public String getDetails() {
		return mDetails;
	}
	
	public int getThumbnail() {
		return mThumbnail;
	}

}
