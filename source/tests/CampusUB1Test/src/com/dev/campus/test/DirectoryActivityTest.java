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

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.directory.Contact;
import com.dev.campus.directory.DirectoryActivity;
import com.jayway.android.robotium.solo.Condition;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListAdapter;


public class DirectoryActivityTest extends 
				ActivityInstrumentationTestCase2<DirectoryActivity> {


	private Solo mSolo;
	private DirectoryActivity mActivity;

	public DirectoryActivityTest() {
		super(DirectoryActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mSolo = new Solo(getInstrumentation(), mActivity);
		CampusUB1App.persistence.setFilterUB(true);
		CampusUB1App.persistence.setFilterLabri(true);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testEditTextRequestFocus() {
		assertTrue(mSolo.getView(R.id.edit_text_first_name).hasFocus());
	}
	
	public void testLabriSearchResult() {
		final int INITIAL_ITEM_INDEX = 1;	//offest by 1 due to header view being included
		final ListAdapter adapter = mActivity.getListView().getAdapter();
		
		mSolo.clickOnView(mSolo.getView(R.id.menu_filters));
		mSolo.waitForDialogToOpen(500);
		mSolo.clickOnText(mSolo.getString(R.string.ub1));
		mSolo.clickOnButton(mSolo.getString(R.string.ok));
		mSolo.waitForDialogToClose(500);
		
		EditText firstName = (EditText) mSolo.getView(R.id.edit_text_first_name);
		EditText lastNane = (EditText) mSolo.getView(R.id.edit_text_last_name);
		
		mSolo.enterText(firstName, "Xavier");
		mSolo.enterText(lastNane, "Blanc");
		mSolo.clickOnView(mSolo.getView(R.id.button_search_directory));
		mSolo.waitForCondition(new Condition() {
			
			@Override
			public boolean isSatisfied() {
				return adapter.getCount() > INITIAL_ITEM_INDEX;
			}
		}, 60000);
		assertTrue(mSolo.searchText("BLANC Xavier"));
		
		Contact xb = (Contact) adapter.getItem(INITIAL_ITEM_INDEX);
		assertTrue(xb != null);
		assertEquals("Xavier", xb.getFirstName());
		assertEquals("Blanc", xb.getLastName());
		assertEquals("xavier.blanc@labri.fr", xb.getEmail());
		assertEquals("+33 5 40 00 69 33", xb.getTel());
	}
	
}
