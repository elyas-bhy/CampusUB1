package com.dev.campus.event;

import java.io.Serializable;

import com.dev.campus.event.Feed.FeedType;

public class Event implements Serializable {

	private static final long serialVersionUID = 8284757527911114571L;
	
	private String mCategory;
	private String mTitle;
	private String mDescription;
	private String mDate;
	private String mTime;
	private String mDetails;
	private FeedType mSource;
	
	public Event() {
		mCategory = null;
		mTitle = null;
		mDate = null;
		mTime = null;
		mDetails = null;
		mSource = null;
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

	public String getDetails() {
		return mDetails;
	}

	public void setDetails(String details) {
		mDetails = details;
	}

	public FeedType getSource() {
		return mSource;
	}

	public void setSource(FeedType source) {
		mSource = source;
	}

}
