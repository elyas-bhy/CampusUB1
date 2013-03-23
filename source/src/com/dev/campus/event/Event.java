package com.dev.campus.event;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;

import com.dev.campus.event.Feed.FeedType;

public class Event implements Serializable {

	private static final long serialVersionUID = 8284757527911114571L;
	
	private String mCategory;
	private String mTitle;
	private String mDescription;
	private Date mDate;
	private String mTime;
	private String mDetails;
	private FeedType mSource;
	private String mLocation;
	private boolean mRead;
	private boolean mStarred;

	
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

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
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

	public String getLocation(){
		return mLocation;
	}
	
	public void setLocation(String loc){
		mLocation = loc;
	}
	
	public boolean isRead() {
		return mRead;
	}
	
	public void setRead(boolean b) {
		mRead = b;
	}
	
	public boolean isStarred() {
		return mStarred;
	}
	
	public void setStarred(boolean b) {
		mStarred = b;
	}

	public String getStringDate() {
		String d = mDate.toString().substring(0,11);
		if (this.mDate.getHours() != 0)
			d += mDate.toString().substring(11, 16) + " ";
		return d + mDate.toString().substring(24);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Event))
			return false;

		String s1 = mTitle.toString() + mDate.toString();
		String s2 = ((Event)o).getTitle().toString() + ((Event)o).getDate().toString();
		
		return s1.equals(s2);
	}
	
	static class EventComparator implements Comparator<Event> {
		@Override
		public int compare(Event evt1, Event evt2) {
			return evt2.getDate().compareTo(evt1.getDate());
		}
	}
}
