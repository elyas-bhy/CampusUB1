package com.dev.campus.home;

import com.dev.campus.R;
import com.dev.campus.home.HomeAdapter.HomeItemType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
