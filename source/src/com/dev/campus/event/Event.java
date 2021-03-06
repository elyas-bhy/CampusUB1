/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus.event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import com.dev.campus.event.Feed.FeedType;

/**
 * Basic holder class for event data
 * @author CampusUB1 Development Team
 *
 */
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
		SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy, HH:mm", Locale.getDefault());
		return sdf.format(mStartDate);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Event))
			return false;
		Event event = (Event) o;
		return event.getTitle().equals(mTitle) && event.getStartDate().equals(mStartDate);
	}
	
	static class EventComparator implements Comparator<Event> {
		@Override
		public int compare(Event evt1, Event evt2) {
			return evt2.getStartDate().compareTo(evt1.getStartDate());
		}
	}
	
	public String toString() {
		String s = "[title: " + mTitle + ", startDate:" + mStartDate  
				+ ", endDate: "+ mEndDate + ", location:" + mLocation 
				+ ", details: " + mDetails + ", source: " + mSource + "]";
		return s;
	}
}
