package com.dev.campus.event;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.dev.campus.event.Feed.FeedType;

public class Event implements Serializable {

	private static final long serialVersionUID = 8284757527911114571L;
	
	private String mCategory;
	private String mTitle;
	private String mDescription;
	private String mDetails;
	private String mLocation;
	private FeedType mSource;
	private Date mStartDate;
	private Date mEndDate;
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

	public Date getStartDate() {
		return mStartDate;
	}

	public void setStartDate(Date date) {
		mStartDate = date;
	}

	public Date getEndDate() {
		return mEndDate;
	}

	public void setEndDate(Date date) {
		mEndDate = date;
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
		String d = mStartDate.toString().substring(0,11);
		if (this.mStartDate.getHours() != 0)
			d += mStartDate.toString().substring(11, 16) + " ";
		return d + mStartDate.toString().substring(24);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Event))
			return false;

		String s1 = "" + mTitle + mStartDate;
		String s2 = "" + ((Event)o).getTitle() + ((Event)o).getStartDate();
		
		return s1.equals(s2);
	}
	
	static class EventComparator implements Comparator<Event> {
		@Override
		public int compare(Event evt1, Event evt2) {
			return evt2.getStartDate().compareTo(evt1.getStartDate());
		}
	}
	
	public String toString() {
		String s = "[title: " + mTitle + ", startDate:" + mStartDate  + ", endDate: "+ mEndDate + ", location:" + mLocation 
				+ ", details: " + mDetails + ", source: " + mSource + "]";
		return s;
	}
}
