package com.dev.campus.home;

import android.view.LayoutInflater;
import android.view.View;

public interface HomeItem {
	
	public int getViewType();
	public View getView(LayoutInflater inflater, View convertView);

}
