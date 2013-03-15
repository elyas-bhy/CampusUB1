package com.dev.campus.home;

public class HomeEntryItem {
	
	private int mIconResourceId;
	private String mTitle;
	private boolean isSection;
	
	public HomeEntryItem() {
		mIconResourceId = 0;
		mTitle = "";
		isSection = true;
	}
	
	public HomeEntryItem(int iconResourceId, String title) {
		mIconResourceId = iconResourceId;
		mTitle = title;
		isSection = false;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public int getIconResourceId() {
		return mIconResourceId;
	}
	
	public boolean isSection() {
		return isSection;
	}

}
