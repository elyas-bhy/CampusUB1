package com.dev.campus.event;

import java.util.ArrayList;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

import com.dev.campus.R;

public class EventMultiChoiceModeListener implements MultiChoiceModeListener {
	
	private ArrayList<Event> mSelectedEvents;
	private ListView mListView;
	
	public EventMultiChoiceModeListener(ListView listView) {
		mSelectedEvents = new ArrayList<Event>();
		mListView = listView;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		for (Event event : mSelectedEvents) {
			switch (item.getItemId()) {
			case R.id.menu_read:
				event.setRead(true);
				break;
			case R.id.menu_unread:
				event.setRead(false);
				break;
			case R.id.menu_star:
				event.setStarred(true);
				break;
			case R.id.menu_unstar:
				event.setStarred(false);
				break;
			}
		}
		mode.finish();
		return true;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.event_contextual_actionbar, menu);
        return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mSelectedEvents.clear();
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
		Event evt = (Event) mListView.getItemAtPosition(position);
		if (checked) {
			if (!mSelectedEvents.contains(evt))
				mSelectedEvents.add(evt);
		} else {
			if (mSelectedEvents.contains(evt))
				mSelectedEvents.remove(evt);
		}
	}

}
