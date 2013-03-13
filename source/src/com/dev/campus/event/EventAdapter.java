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
		//ImageView icon;
		TextView title;
		TextView date;
		TextView category;
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
			eventHolder.title = (TextView)row.findViewById(R.id.event_title);
			eventHolder.date = (TextView)row.findViewById(R.id.event_date);
			eventHolder.category = (TextView)row.findViewById(R.id.event_category);
			eventHolder.description = (TextView)row.findViewById(R.id.event_description);
			//eventHolder.icon = (ImageView)row.findViewById(R.id.event_icon);
			row.setTag(eventHolder);
			
		} else {
			eventHolder = (EventHolder)row.getTag();
		}
		
		Event event = mEvents.get(position);
		eventHolder.title.setText(event.getTitle());
		eventHolder.date.setText(event.getStringDate());
		eventHolder.category.setText(event.getCategory());
		eventHolder.description.setText(event.getDescription());
		//eventHolder.icon.setImageResource(R.drawable.ic_test);
		
		return row;
	}
}