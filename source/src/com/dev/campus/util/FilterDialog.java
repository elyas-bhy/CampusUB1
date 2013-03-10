package com.dev.campus.util;


import java.util.ArrayList;
import java.util.HashSet;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.directory.DirectoryActivity;
import com.dev.campus.event.EventsActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;

/**
 * Custom AlertDialog class which handles filters management
 * @author elyas-bhy
 *
 */
public class FilterDialog extends AlertDialog {
	
	private AlertDialog mFilterDialog;
	private Activity mContext;
	
	public FilterDialog(Activity context) {
		super(context);
		mContext = context;
	}
	
	/**
	 * Creates and shows an instance of FilterDialog
	 */
	public void showDialog() {
		FilterDialogBuilder builder = new FilterDialogBuilder(mContext);
		mFilterDialog = builder.create();
		mFilterDialog.show();
		initializePositiveButton();
	}
	
	/**
	 * Check that positive button is enabled only if 
	 * at least one filter is activated
	 */
	private void initializePositiveButton() {
		if (!CampusUB1App.persistence.isFilteredUB1()
		 && !CampusUB1App.persistence.isFilteredLabri())
			mFilterDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		else
			mFilterDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
	}

	/**
	 * Custom AlertDialog builder class which provides filters management
	 * @author elyas-bhy
	 *
	 */
	private class FilterDialogBuilder extends AlertDialog.Builder {
		
		private final Integer UB1_INDEX = 0;
		private final Integer LABRI_INDEX = 1;
		
		private HashSet<Integer> mSelectedItems;
		
		public FilterDialogBuilder(Activity context) {
			super(context);
			Resources res = context.getResources();
			
			mSelectedItems = new HashSet<Integer>();
			boolean[] checkedItems = new boolean[2];
			int index = 0;
			ArrayList<String> itemsList = new ArrayList<String>();
			
			if (CampusUB1App.persistence.isSubscribedUB1()) {
				itemsList.add(res.getString(R.string.ub1));
				if (CampusUB1App.persistence.isFilteredUB1()) {
					mSelectedItems.add(UB1_INDEX);
					checkedItems[index++] = true;
				} else
					checkedItems[index++] = false;
			}
			
			if (CampusUB1App.persistence.isSubscribedLabri()) {
				itemsList.add(res.getString(R.string.labri));
				if (CampusUB1App.persistence.isFilteredLabri()) {
					mSelectedItems.add(LABRI_INDEX);
					checkedItems[index++] = true;
				} else
					checkedItems[index++] = false;
			}
			
			final CharSequence[] items = itemsList.toArray(new CharSequence[itemsList.size()]);
			
			setCancelable(false);
			setTitle(R.string.filter_results);
			setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

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
					boolean filter_ub1 = mSelectedItems.contains(UB1_INDEX);
					boolean filter_labri = mSelectedItems.contains(LABRI_INDEX);
					
					CampusUB1App.persistence.setFilterUB(filter_ub1);
					CampusUB1App.persistence.setFilterLabri(filter_labri);
					
					//Find a better workaround for this (getClass() ?)
					if (mContext instanceof EventsActivity)
						((EventsActivity)mContext).reloadEvents();
					else if (mContext instanceof DirectoryActivity)
						((DirectoryActivity)mContext).reloadContacts();
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