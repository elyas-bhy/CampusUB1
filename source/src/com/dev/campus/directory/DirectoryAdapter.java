package com.dev.campus.directory;

import java.util.List;

import com.dev.campus.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DirectoryAdapter extends ArrayAdapter<Contact> {

	private List<Contact> mContacts;
	private Activity mContext;

	public DirectoryAdapter(Activity context, List<Contact> contacts) {
		super(context, R.layout.directory_list_item, contacts);
		mContext = context;
		mContacts = contacts;
	}

	private static class ContactHolder {
		TextView name;
		TextView tel;
		TextView email;
		ImageView website;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ContactHolder contactHolder = null;

		if (row == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row = inflater.inflate(R.layout.directory_list_item, parent, false);

			contactHolder = new ContactHolder();
			contactHolder.name = (TextView) row.findViewById(R.id.contact_name);
			contactHolder.website = (ImageView) row.findViewById(R.id.contact_website);
			contactHolder.tel = (TextView) row.findViewById(R.id.contact_tel);
			contactHolder.email = (TextView) row.findViewById(R.id.contact_email);
			row.setTag(contactHolder);

		} else {
			contactHolder = (ContactHolder) row.getTag();
		}

		Contact contact = mContacts.get(position);
		contactHolder.name.setText(contact.getLastName().toUpperCase() + " " + contact.getFirstName());

		if (contact.getTel() != null) {
			contactHolder.tel.setText(contact.getTel());
		} else {
			contactHolder.tel.setText(R.string.tel_not_specified);
		}

		if (contact.getEmail() != null) {
			contactHolder.email.setText(contact.getEmail());
		} else {
			contactHolder.email.setText(R.string.email_not_specified);
		}

		Drawable drawableWebsite = mContext.getResources().getDrawable(R.drawable.website);
		contactHolder.website.setImageDrawable(drawableWebsite);
		if (contact.getWebsite() == null || contact.getWebsite().equals("")) {
			contactHolder.website.getDrawable().mutate().setColorFilter(null);
		}

		return row;
	}

}
