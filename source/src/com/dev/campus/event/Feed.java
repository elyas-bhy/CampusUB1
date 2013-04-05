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

import com.dev.campus.CampusUB1App;

/**
 * Enumeration of all available feeds 
 * @author CampusUB1 Development Team
 *
 */
public enum Feed {
	
	// UB1 news RSS feeds
	UB1_NEWS_ALL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=114"),
	UB1_NEWS_UNIVERSITY(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=100"),
	UB1_NEWS_FORMATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=101"),
	UB1_NEWS_RESEARCH(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=102"),
	UB1_NEWS_INTERNATIONAL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=103"),
	UB1_NEWS_STUDENTS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=104"),
	UB1_NEWS_COMPANIES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=105"),
	UB1_NEWS_PRESS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=106"),
	
	// UB1 events RSS feeds
	UB1_EVENTS_ALL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=115"),
	UB1_EVENTS_CONGRESS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=107"),
	UB1_EVENTS_FORMATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=108"),
	UB1_EVENTS_SEMINARS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=109"),
	UB1_EVENTS_SALON(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=110"),
	UB1_EVENTS_THESES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=111"),
	UB1_EVENTS_MANIFESTATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=112"),
	UB1_EVENTS_CONFERENCES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=113"),
	UB1_EVENTS_EXPOSITIONS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=116"),
	
	// LaBRI news RSS feeds
	LABRI_NEWS(FeedType.LABRI_FEED, "http://www.labri.fr/rss/rss.php"),
	
	// LaBRI HTML-based feeds, use query keywords instead of URL
	LABRI_COLLOQUES(FeedType.LABRI_FEED_HTML, "colloques"),
	LABRI_THESES(FeedType.LABRI_FEED_HTML, "theses"),
	LABRI_GROUPES(FeedType.LABRI_FEED_HTML, "groupes"),
	LABRI_AUTRES(FeedType.LABRI_FEED_HTML, "autres");
	
	
	public enum FeedType {
		UB1_FEED("Bordeaux 1"),
		LABRI_FEED("LaBRI"),
		LABRI_FEED_HTML("LaBRI");
	
		private String mShortName;
		
		private FeedType(String shortName) {
			mShortName = shortName;
		}
		
		public String getShortName() {
			return mShortName;
		}
		
		public boolean isSubscribedRSS() {
			return (this.equals(FeedType.UB1_FEED) && CampusUB1App.persistence.isSubscribedUB1()
				 || this.equals(FeedType.LABRI_FEED) && CampusUB1App.persistence.isSubscribedLabri());
		}
		
		public boolean isFiltered() {
			return (this.equals(FeedType.UB1_FEED) && CampusUB1App.persistence.isFilteredUB1()
				 || this.equals(FeedType.LABRI_FEED) && CampusUB1App.persistence.isFilteredLabri()
				 || this.equals(FeedType.LABRI_FEED_HTML) && CampusUB1App.persistence.isFilteredLabri());
		}
	}
	
	private FeedType mType;
	private String mUrl;
	
	private Feed(FeedType type, String url) {
		mType = type;
		mUrl = url;
	}
	
	public FeedType getType() {
		return mType;
	}
	
	public String getUrl() {
		return mUrl;
	}
}
