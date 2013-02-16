package com.dev.campus.event;

import com.dev.campus.R;
import com.dev.campus.ac.EventsActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesActivity extends ListActivity {
	
	public static final String EXTRA_CATEGORY = "com.dev.campus.CATEGORY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        String[] values = new String[] { "Category 1", 
        								 "Category 2", 
        								 "Category 3", 
        								 "Category 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_categories, menu);
		return true;
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String item = (String) getListAdapter().getItem(position);
    	Intent intent = new Intent(CategoriesActivity.this, EventsActivity.class);
    	intent.putExtra(EXTRA_CATEGORY, item);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
}
