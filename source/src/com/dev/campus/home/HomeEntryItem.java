package com.dev.campus.home;

public class HomeEntryItem {
	
	private String title;
	private boolean isSection;
	
	public HomeEntryItem() {
		this.title = "";
		this.isSection = true;
	}
	
	public HomeEntryItem(String title) {
		this.title = title;
		this.isSection = false;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean isSection() {
		return isSection;
	}

}
