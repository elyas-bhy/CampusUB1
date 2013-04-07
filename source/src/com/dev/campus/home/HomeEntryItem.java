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

package com.dev.campus.home;

import com.dev.campus.R;
import com.dev.campus.home.HomeAdapter.HomeItemType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Implementation of HomeItem used for menu entries
 * @author CampusUB1 Development Team
 *
 */
public class HomeEntryItem implements HomeItem {
	
	private int mIconResourceId;
	private int mTitleResourceId;

	public HomeEntryItem(int iconResourceId, int titleResourceId) {
		mIconResourceId = iconResourceId;
		mTitleResourceId = titleResourceId;
	}

	@Override
	public int getViewType() {
		return HomeItemType.ENTRY_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View row;
		if (convertView == null) {
			row = inflater.inflate(R.layout.home_list_item_entry, null);
		} else {
			row = convertView;
		}
		
		ImageView icon = (ImageView) row.findViewById(R.id.home_list_item_entry_icon);
		TextView title = (TextView) row.findViewById(R.id.home_list_item_entry_title);
		icon.setImageResource(mIconResourceId);
		title.setText(mTitleResourceId);
		return row;
	}

}
