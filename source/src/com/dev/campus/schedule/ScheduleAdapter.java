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
