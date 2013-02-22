package com.dev.campus.event;

import java.util.List;

import com.dev.campus.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {
	
	private Activity mContext;
	private List<Event> mEvents;
	
	private static class EventHolder {
		ImageView icon;
		TextView title;
		TextView category;
		TextView date;
		TextView description;
	}
	
	public EventAdapter(Activity context, List<Event> events) {
		super(context, R.layout.event_list_item, events);
		mContext = context;
		mEvents = events;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		EventHolder eventHolder = null;
		
		if (row == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row = inflater.inflate(R.layout.event_list_item, parent, false);
			
			eventHolder = new EventHolder();
			eventHolder.icon = (ImageView)row.findViewById(R.id.event_icon);
			eventHolder.title = (TextView)row.findViewById(R.id.event_title);
			eventHolder.category = (TextView)row.findViewById(R.id.event_category);
			eventHolder.date = (TextView)row.findViewById(R.id.event_date);
			eventHolder.description = (TextView)row.findViewById(R.id.event_description);
			row.setTag(eventHolder);
			
		} else {
			eventHolder = (EventHolder)row.getTag();
		}
		
		Event event = mEvents.get(position);
		eventHolder.icon.setImageResource(R.drawable.ic_test);
		eventHolder.title.setText(event.getTitle());
		eventHolder.category.setText(event.getCategory());
		eventHolder.date.setText(event.getDate());
		eventHolder.description.setText(event.getDescription());
		
		return row;
	}
}