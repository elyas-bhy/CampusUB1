package com.dev.campus.event;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class EventPagerAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<Event> mEvents;

	public EventPagerAdapter(FragmentManager fm, ArrayList<Event> events) {
		super(fm);
		mEvents = events;
	}

	@Override
	public Fragment getItem(int position) {
		return EventFragment.create(mEvents.get(position));
	}

	@Override
	public int getCount() {
		return mEvents.size();
	}

}
