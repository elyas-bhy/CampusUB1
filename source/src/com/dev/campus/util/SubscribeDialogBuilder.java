package com.dev.campus.util;


import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.ac.HomeActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class SubscribeDialogBuilder extends AlertDialog.Builder {
	
	private static final Integer UB1_INDEX = 0;
	private static final Integer LABRI_INDEX = 1;
	
	private Context mContext;
	private ArrayList<Integer> mSelectedItems;

	public SubscribeDialogBuilder(Context context) {
		super(context);
		mContext = context;
		mSelectedItems = new ArrayList<Integer>();
		setTitle(R.string.selectEst);
		setMultiChoiceItems(R.array.establishments_array, null, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked) {
				if (isChecked) {
					mSelectedItems.add(which);
				} else if (mSelectedItems.contains(which)) {
					mSelectedItems.remove(which);
				}
			}
		});
		
		setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (mSelectedItems.size() > 0) {
					if (mSelectedItems.contains(UB1_INDEX)) {
						CampusUB1App.persistence.setSubscribeUB(true);
						CampusUB1App.persistence.setFilterUB(true);
					}
					if (mSelectedItems.contains(LABRI_INDEX)) {
						CampusUB1App.persistence.setSubscribeLabri(true);
						CampusUB1App.persistence.setFilterLabri(true);
					}
					
					mContext.startActivity(new Intent(mContext, HomeActivity.class));
				}
			}
		});
		
		setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mSelectedItems.clear();
			}
		});
	}

}