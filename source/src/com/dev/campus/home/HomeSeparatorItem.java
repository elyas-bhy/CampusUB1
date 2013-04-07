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

/**
 * Implementation of HomeItem used for menu separators
 * @author CampusUB1 Development Team
 *
 */
public class HomeSeparatorItem implements HomeItem {

	@Override
	public int getViewType() {
		return HomeItemType.SEPARATOR_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View row;
		if (convertView == null) {
			row = inflater.inflate(R.layout.home_list_item_separator, null);
		} else {
			row = convertView;
		}

		row.setOnClickListener(null);
		row.setOnLongClickListener(null);
		row.setLongClickable(false);
		return row;
	}

}
