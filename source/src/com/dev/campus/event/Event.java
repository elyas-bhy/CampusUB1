package com.dev.campus.event;

public class Event {

	private String mCategory;
	private String mTitle;
	private String mDescription;
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
	
	
	public String getCategory() {
		return mCategory;
	}

	public void setCategory(String category) {
		mCategory = category;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public void setDescription(String description) {
		mDescription = description;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String time) {
		mTime = time;
	}

	public int getThumbnail() {
		return mThumbnail;
	}

	public void setThumbnail(int thumbnail) {
		mThumbnail = thumbnail;
	}

	public String getDetails() {
		return mDetails;
	}

	public void setDetails(String details) {
		mDetails = details;
	}

	public String getSource() {
		return mSource;
	}

	public void setSource(String source) {
		mSource = source;
	}

}
