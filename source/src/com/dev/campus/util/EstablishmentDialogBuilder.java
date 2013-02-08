package com.dev.campus.util;


import com.dev.campus.R;
import com.dev.campus.ac.LabriActivity;
import com.dev.campus.ac.UB1Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class EstablishmentDialogBuilder extends AlertDialog.Builder {
	
	private Context mContext;

	public EstablishmentDialogBuilder(Context context) {
		super(context);
		mContext = context;
		setTitle(R.string.selectEst);
		setItems(R.array.establishments_array, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent;
				switch(which) {
				case 0: //UB1
					intent = new Intent(mContext, UB1Activity.class);
					mContext.startActivity(intent);
					break;
				case 1: //LaBRI
					intent = new Intent(mContext, LabriActivity.class);
					mContext.startActivity(intent);
					break;
				default:
					break;
				}
				
			}
		});
	}

}