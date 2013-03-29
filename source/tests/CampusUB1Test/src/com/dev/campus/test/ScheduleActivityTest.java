package com.dev.campus.test;

import android.app.Activity;
import android.database.DataSetObserver;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.dev.campus.R;
import com.dev.campus.schedule.ScheduleActivity;
import com.dev.campus.schedule.ScheduleAdapter;
import com.jayway.android.robotium.solo.Solo;


public class ScheduleActivityTest extends ActivityInstrumentationTestCase2<ScheduleActivity> {
	
	private Solo mSolo;
	private ScheduleActivity mActivity;

	public ScheduleActivityTest() {
		super(ScheduleActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mSolo = new Solo(getInstrumentation(), mActivity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mActivity.findViewById(R.id.schedule_spinner));
	}
	
	@UiThreadTest
	public void testScheduleGroup() {
		final ScheduleAdapter adapter = (ScheduleAdapter) mActivity.getListView().getAdapter();
		adapter.registerDataSetObserver(new DataSetObserver() {
			
			@Override
			public void onChanged() {
				for(int i = 0; i < adapter.getCount(); i++) {
					
				}
			}
		});
	}
	
	

}
