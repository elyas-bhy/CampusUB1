package com.dev.campus.schedule;

import java.util.List;

import com.dev.campus.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends ArrayAdapter<Group> {

	private List<Group> mGroups;
	private Activity mContext;

	public ScheduleAdapter(Activity context, List<Group> scheduleGroups) {
		super(context, R.layout.schedule_list_group, scheduleGroups);
		mContext = context;
		mGroups = scheduleGroups;
	}

	private static class ScheduleHolder {
		TextView group;
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
			row.setTag(scheduleHolder);

		} else {
			scheduleHolder = (ScheduleHolder) row.getTag();
		}

		Group scheduleGroup = mGroups.get(position);
		scheduleHolder.group.setText(scheduleGroup.getTitle());

		return row;
	}

}
