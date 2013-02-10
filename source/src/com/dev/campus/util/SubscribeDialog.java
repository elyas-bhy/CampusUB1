package com.dev.campus.util;


import java.util.HashSet;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.ac.HomeActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class SubscribeDialog extends AlertDialog {
	
	private SubscribeDialogBuilder mSubscribeDialogBuilder;
	private AlertDialog mSubscribeDialog;
	
	public SubscribeDialog(Activity context) {
		super(context);
		mSubscribeDialogBuilder = new SubscribeDialogBuilder(context);
		mSubscribeDialog = mSubscribeDialogBuilder.create();
	}
	
	public void show() {
		mSubscribeDialog.show();
		mSubscribeDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
	}

	private class SubscribeDialogBuilder extends AlertDialog.Builder {
		
		private final Integer UB1_INDEX = 0;
		private final Integer LABRI_INDEX = 1;
		
		private Activity mContext;
		private HashSet<Integer> mSelectedItems;
		
		public SubscribeDialogBuilder(Activity context) {
			super(context);
			
			mContext = context;
			mSelectedItems = new HashSet<Integer>();
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
					
					if (mSelectedItems.size() == 0)
						((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
					else
						((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
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
						mContext.finish();
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

}