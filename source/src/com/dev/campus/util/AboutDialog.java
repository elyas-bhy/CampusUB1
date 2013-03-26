package com.dev.campus.util;

import com.dev.campus.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

public class AboutDialog extends AlertDialog.Builder {

	public AboutDialog(Context context) {
		super(context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		builder.setView(inflater.inflate(R.layout.dialog_about, null))
		.setNegativeButton("Contact", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//TODO
			}
		});

		builder.show();
	}

}
