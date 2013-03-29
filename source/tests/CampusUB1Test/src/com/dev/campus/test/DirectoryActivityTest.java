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
		//mSolo.finishOpenedActivities();
	}
	
	public void testEditTextRequestFocus() {
		assertTrue(mSolo.getView(R.id.edit_text_first_name).hasFocus());
	}
	
	public void testLabriSearchResult() {
		final int FIRST_ITEM_INDEX = 1;	//offest by 1 due to header view being included
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
				return adapter.getCount() > FIRST_ITEM_INDEX;
			}
		}, 60000);
		assertTrue(mSolo.searchText("BLANC Xavier"));
		
		Contact xb = (Contact) adapter.getItem(FIRST_ITEM_INDEX);
		assertTrue(xb != null);
		assertEquals("Xavier", xb.getFirstName());
		assertEquals("Blanc", xb.getLastName());
		assertEquals("xavier.blanc@labri.fr", xb.getEmail());
		assertEquals("+33 5 40 00 69 33", xb.getTel());
	}
	
}
