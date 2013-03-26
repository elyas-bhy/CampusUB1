package com.dev.campus.util;

import com.dev.campus.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;

public class AboutDialog extends AlertDialog.Builder {

	public AboutDialog(final Context context) {
		super(context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		builder.setView(inflater.inflate(R.layout.dialog_about, null))
		.setPositiveButton("Contact", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("plain/text");  
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"campusub1.dev@gmail.com"});
				context.startActivity(Intent.createChooser(emailIntent, 
											context.getResources().getString(R.string.menu_complete_action)));
			}
		});

		builder.show();
	}

}
