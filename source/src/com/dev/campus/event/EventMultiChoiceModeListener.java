package com.dev.campus.event;

import java.util.ArrayList;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AbsListView.MultiChoiceModeListener;

import com.dev.campus.R;

public class EventMultiChoiceModeListener implements MultiChoiceModeListener {
	
	private ArrayList<Event> events;
	private ListView view;
	
	public EventMultiChoiceModeListener(ListView lv) {
		events =  new ArrayList<Event>();
		view = lv;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_read:
			mode.finish();
			return true;
		case R.id.menu_unread:
			mode.finish();
			return true;
		case R.id.menu_star:
			mode.finish();
			return true;
		case R.id.menu_unstar:
			mode.finish();
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2,
			boolean arg3) {
		// TODO Auto-generated method stub

	}

}
