package com.dev.campus.home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class HomeAdapter extends ArrayAdapter<HomeItem> {

	private ArrayList<HomeItem> mItems;
	private LayoutInflater mLayoutInflater;
	
	public enum HomeItemType {
		ENTRY_ITEM, SEPARATOR_ITEM;
	}

	public HomeAdapter(Activity context, ArrayList<HomeItem> items) {
		super(context, 0, items);
		mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItems = items;
	}
	
	@Override
	public int getViewTypeCount() {
		return HomeItemType.values().length;
	}
	
	@Override
	public int getItemViewType(int position) {
		return mItems.get(position).getViewType();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mItems.get(position).getView(mLayoutInflater, convertView);
	}

}
