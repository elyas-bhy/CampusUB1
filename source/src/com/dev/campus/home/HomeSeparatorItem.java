package com.dev.campus.home;

import com.dev.campus.R;
import com.dev.campus.home.HomeAdapter.HomeItemType;

import android.view.LayoutInflater;
import android.view.View;

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
