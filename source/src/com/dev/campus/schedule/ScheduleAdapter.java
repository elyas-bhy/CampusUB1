package com.dev.campus.schedule;

import java.util.List;

import com.dev.campus.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleAdapter extends ArrayAdapter<ScheduleGroup> {

	private List<ScheduleGroup> mScheduleGroups;
	private Activity mContext;

	public ScheduleAdapter(Activity context, List<ScheduleGroup> scheduleGroups) {
		super(context, R.layout.schedule_list_group, scheduleGroups);
		mContext = context;
		mScheduleGroups = scheduleGroups;
	}

	private static class ScheduleHolder {
		TextView group;
		TextView url;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ScheduleHolder scheduleHolder = null;

		if (row == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row = inflater.inflate(R.layout.schedule_list_group, parent, false);

			scheduleHolder = new ScheduleHolder();
			scheduleHolder.group = (TextView) row.findViewById(R.id.schedule_group);
			//scheduleHolder.url = (TextView) row.findViewById(R.id.schedule_url);
			row.setTag(scheduleHolder);

		} else {
			scheduleHolder = (ScheduleHolder) row.getTag();
		}

		ScheduleGroup scheduleGroup = mScheduleGroups.get(position);
		scheduleHolder.group.setText(scheduleGroup.getGroup());
		//scheduleHolder.url.setText(scheduleGroup.getUrl());

		return row;
	}

}
