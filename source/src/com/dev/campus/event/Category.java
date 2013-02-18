package com.dev.campus.event;

public enum Category {
	
	UB1_ALL_NEWS("http://www.u-bordeaux1.fr/index.php?type=114", "News - All News"),
	UB1_UNIVERSITY("http://www.u-bordeaux1.fr/index.php?type=100", "News - University"),
	UB1_FORMATION("http://www.u-bordeaux1.fr/index.php?type=101", "News - Formation"),
	UB1_RESEARCH("http://www.u-bordeaux1.fr/index.php?type=102", "News - Research"),
	UB1_INTERNATIONAL("http://www.u-bordeaux1.fr/index.php?type=103", "News - International"),
	UB1_STUDENTS("http://www.u-bordeaux1.fr/index.php?type=104", "News - Students Life"),
	UB1_COMPANIES("http://www.u-bordeaux1.fr/index.php?type=105", "News - Companies"),
	UB1_PRESS("http://www.u-bordeaux1.fr/index.php?type=106", "News - Press");
	
	private String mUrl;
	private String mName;
	
	Category(String url, String name) {
		mUrl = url;
		mName = name;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public String toString() {
		return mName;
	}

}
