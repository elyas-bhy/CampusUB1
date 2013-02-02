package com.dev.campus.util;

import com.dev.campus.UB1Activity;
import com.dev.campus.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class EstablishmentDialogBuilder extends AlertDialog.Builder {

	public EstablishmentDialogBuilder(Context context) {
		super(context);
		setTitle("Select your desired establishment");
		setItems(R.array.establishments_array, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which) {
				case 0: 
					Intent intent = new Intent(getContext(), UB1Activity.class);
					getContext().startActivity(intent);
				}
				
			}
		});
	}

}