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

import com.dev.campus.R;
import com.dev.campus.event.Feed.FeedType;

public class EventAdapter extends ArrayAdapter<Event> {
	
	private Activity mContext;
	private List<Event> mEvents;
	
	private static class EventHolder {
		TextView title;
		TextView establishment;
		TextView date;
		TextView description;
		ImageView star;
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
			eventHolder.title = (TextView) row.findViewById(R.id.event_title);
			eventHolder.establishment = (TextView) row.findViewById(R.id.event_estb);
			eventHolder.date = (TextView) row.findViewById(R.id.event_date);
			eventHolder.description = (TextView) row.findViewById(R.id.event_description);
			eventHolder.star = (ImageView) row.findViewById(R.id.event_star);
			row.setTag(eventHolder);
			
		} else {
			eventHolder = (EventHolder)row.getTag();
		}
		
		Event event = mEvents.get(position);
		
		eventHolder.title.setText(event.getTitle());
		eventHolder.establishment.setText(event.getSource().getShortName());
		eventHolder.date.setText(event.getStringDate());
		eventHolder.description.setText(event.getDescription());
		
		if (event.isRead())
			eventHolder.title.setTextColor(mContext.getResources().getColor(R.color.blue_title_read));
		else
			eventHolder.title.setTextColor(mContext.getResources().getColor(R.color.blue_title));
		
		if (event.isStarred())
			eventHolder.star.setImageResource(R.drawable.ic_star_dark);
		else
			eventHolder.star.setImageResource(R.drawable.ic_unstar_dark);
		
		return row;
	}
}
