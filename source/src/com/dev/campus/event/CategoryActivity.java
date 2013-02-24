package com.dev.campus.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dev.campus.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Category> categories = new ArrayList<Category>();
		categories.addAll(Arrays.asList(Category.values()));
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);
        setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.without_actionbar, menu);
		return true;
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Category item = (Category) getListAdapter().getItem(position);
    	Intent intent = new Intent(CategoryActivity.this, EventsActivity.class);
    	intent.putExtra(EventsActivity.EXTRA_CATEGORY, item);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
}
