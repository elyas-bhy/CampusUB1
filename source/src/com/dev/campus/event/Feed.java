package com.dev.campus.event;

public enum Feed {
	
	UB1_NEWS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=114"),
	UB1_UNIVERSITY(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=100"),
	UB1_FORMATION(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=101"),
	UB1_RESEARCH(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=102"),
	UB1_INTERNATIONAL(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=103"),
	UB1_STUDENTS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=104"),
	UB1_COMPANIES(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=105"),
	UB1_PRESS(FeedType.UB1_FEED, "http://www.u-bordeaux1.fr/index.php?type=106"),
	
	LABRI_NEWS(FeedType.LABRI_FEED, "http://www.labri.fr/rss/rss.php");
	
	public enum FeedType {
		UB1_FEED,
		LABRI_FEED;
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
