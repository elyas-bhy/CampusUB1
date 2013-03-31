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
