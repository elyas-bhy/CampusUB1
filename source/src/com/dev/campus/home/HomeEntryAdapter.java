package com.dev.campus.home;

import java.util.ArrayList;

import com.dev.campus.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeEntryAdapter extends ArrayAdapter<HomeEntryItem> {

	private ArrayList<HomeEntryItem> mItems;
	private LayoutInflater mLayoutInflater;

	public HomeEntryAdapter(Activity context, ArrayList<HomeEntryItem> items) {
		super(context, 0, items);
		mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItems = items;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final HomeEntryItem item = mItems.get(position);

		if (item != null) {
			if (item.isSection()) {
				row = mLayoutInflater.inflate(R.layout.home_list_item_separator, null);
				row.setOnClickListener(null);
				row.setOnLongClickListener(null);
				row.setLongClickable(false);
			} else {
				row = mLayoutInflater.inflate(R.layout.home_list_item_entry, null);
				ImageView icon = (ImageView) row.findViewById(R.id.home_list_item_entry_icon);
				TextView title = (TextView) row.findViewById(R.id.home_list_item_entry_title);
				title.setText(item.getTitle());
				icon.setImageResource(item.getIconResourceId());
			}
		}
		return row;
	}

}
