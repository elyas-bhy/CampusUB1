/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * Custom ListView adapter responsible for handling event items
 * @author CampusUB1 Development Team
 *
 */
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
