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

package com.dev.campus.test;

import android.test.ActivityInstrumentationTestCase2;

import com.dev.campus.R;
import com.dev.campus.schedule.ScheduleActivity;
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
	

}
