package com.dev.campus.event;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

public class EventMultiChoiceModeListener implements MultiChoiceModeListener {
	
	private ArrayList<Event> events;
	private ListView view;
	
	public EventMultiChoiceModeListener(ListView lv) {
		events = new ArrayList<Event>();
		view = lv;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		for(Event evt : events) {
			switch(item.getItemId()) {
			case R.id.menu_read:
				evt.setRead(true);
				break;
			case R.id.menu_unread:
				evt.setRead(false);
				break;
			case R.id.menu_star:
				evt.setStarred(true);
				break;
			case R.id.menu_unstar:
				evt.setStarred(false);
				break;
			}
			mode.finish();
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.event_contextual, menu);
        return true;

	}

	@Override
	public void onDestroyActionMode(ActionMode arg0) {

	}

	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
			boolean checked) {
		Event evt = (Event) view.getItemAtPosition(position);
		if(checked) {
			view.getChildAt(position).setBackgroundColor(Color.RED);
			if(!events.contains(evt))
				events.add(evt);
		} else {
			view.getChildAt(position).setBackgroundColor(Color.WHITE);
			if(events.contains(evt))
				events.remove(evt);
		}
		android.util.Log.d("Ryan", "item checked at position : " + position + " with id : " + id);
	}

}
