package com.dev.campus.event;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Category {
	
	UB1_ALL_NEWS("http://www.u-bordeaux1.fr/index.php?type=114", R.string.ub1_all_news),
	UB1_UNIVERSITY("http://www.u-bordeaux1.fr/index.php?type=100", R.string.ub1_university),
	UB1_FORMATION("http://www.u-bordeaux1.fr/index.php?type=101", R.string.ub1_formation),
	UB1_RESEARCH("http://www.u-bordeaux1.fr/index.php?type=102", R.string.ub1_research),
	UB1_INTERNATIONAL("http://www.u-bordeaux1.fr/index.php?type=103", R.string.ub1_international),
	UB1_STUDENTS("http://www.u-bordeaux1.fr/index.php?type=104", R.string.ub1_student_life),
	UB1_COMPANIES("http://www.u-bordeaux1.fr/index.php?type=105", R.string.ub1_companies),
	UB1_PRESS("http://www.u-bordeaux1.fr/index.php?type=106", R.string.ub1_press);
	
	private String mUrl;
	private int mName;
	
	Category(String url, int name) {
		mUrl = url;
		mName = name;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public String toString() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getString(mName);
	}

}
