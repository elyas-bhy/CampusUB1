package com.dev.campus.home;

public class HomeEntryItem {
	
	private int mIconResourceId;
	private int mTitleResourceId;
	private boolean isSection;
	
	public HomeEntryItem() {
		mIconResourceId = 0;
		mTitleResourceId = 0;
		isSection = true;
	}
	
	public HomeEntryItem(int iconResourceId, int titleResourceId) {
		mIconResourceId = iconResourceId;
		mTitleResourceId = titleResourceId;
		isSection = false;
	}
	
	public int getTitleResourceId() {
		return mTitleResourceId;
	}
	
	public int getIconResourceId() {
		return mIconResourceId;
	}
	
	public boolean isSection() {
		return isSection;
	}

}
