package com.dev.campus;

import com.dev.campus.util.EstablishmentDialogBuilder;
import com.dev.campus.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	
	private EstablishmentDialogBuilder mEstablishmentDialogBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEstablishmentDialogBuilder = new EstablishmentDialogBuilder(this);
		mEstablishmentDialogBuilder.show();
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
