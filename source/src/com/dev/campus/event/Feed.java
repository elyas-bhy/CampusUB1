package com.dev.campus.event;

import com.dev.campus.CampusUB1App;

public enum Feed {
	
	UB1_NEWS_ALL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=114"),
	UB1_NEWS_UNIVERSITY(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=100"),
	UB1_NEWS_FORMATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=101"),
	UB1_NEWS_RESEARCH(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=102"),
	UB1_NEWS_INTERNATIONAL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=103"),
	UB1_NEWS_STUDENTS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=104"),
	UB1_NEWS_COMPANIES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=105"),
	UB1_NEWS_PRESS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=106"),
	
	UB1_EVENTS_ALL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=115"),
	UB1_EVENTS_CONGRESS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=107"),
	UB1_EVENTS_FORMATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=108"),
	UB1_EVENTS_SEMINARS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=109"),
	UB1_EVENTS_SALON(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=110"),
	UB1_EVENTS_THESES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=111"),
	UB1_EVENTS_MANIFESTATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=112"),
	UB1_EVENTS_CONFERENCES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=113"),
	UB1_EVENTS_EXPOSITIONS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=116"),
	
	LABRI_NEWS(FeedType.LABRI_FEED, "http://www.labri.fr/rss/rss.php");
	
	
	public enum FeedType {
		UB1_FEED,
		LABRI_FEED,
		LABRI_FEED_HTML;
		
		public boolean isFiltered() {
			return (this.equals(FeedType.UB1_FEED) && CampusUB1App.persistence.isFilteredUB1()
				 || this.equals(FeedType.LABRI_FEED) && CampusUB1App.persistence.isFilteredLabri());
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
