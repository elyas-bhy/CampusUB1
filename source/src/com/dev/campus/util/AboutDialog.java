/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus.util;

import com.dev.campus.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;

/**
 * Utility dialog for the About section
 * @author CampusUB1 Development Team
 *
 */
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
