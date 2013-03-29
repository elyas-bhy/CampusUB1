package com.dev.campus.map;

import com.dev.campus.map.MapActivity;
import com.dev.campus.map.Position;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

public class MapActivityTest extends 
	ActivityInstrumentationTestCase2<MapActivity> {

	private Solo mSolo;
	private MapActivity mActivity;

	public MapActivityTest() {
		super(MapActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mSolo = new Solo(getInstrumentation(), mActivity);	
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
    public void testPreConditions() {
        Activity activity = getActivity();
        assertNotNull(activity);
        assertNotNull(activity.findViewById(R.id.map_UB1));
    }
	
	public void testSearch(){
		// Test if search for an existing position returns a correct result
		String result = mActivity.searchPosition("LaBRI");
		assertEquals(result, Position.BAT_A27.getId());
	}
	
	public void testFakeSearch(){
		// Test if search for a non existant position returns null
		String result = mActivity.searchPosition("PoneyLand");
		assertNull(result);
	}
	
	public void testSuggestions(){
		// Test if suggestions for a given position belongs to the same marker 
		String firstSuggestion = mActivity.searchPosition("A27");
		String secondSuggestion = mActivity.searchPosition("LaBRI");
		assertEquals(firstSuggestion,secondSuggestion);
	}
	
		
}

	
