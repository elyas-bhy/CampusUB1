package com.dev.campus.event;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EventAdapter extends ArrayAdapter<Event> {
	
	private Context mContext;
	private List<Event> mEvents;
	
	public EventAdapter(Context context, List<Event> events) {
		super(context, android.R.layout.simple_list_item_1, events);
		mContext = context;
		mEvents = events;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
}