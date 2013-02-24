package com.dev.campus.directory;

import java.util.List;

import com.dev.campus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DirectoryAdapter extends BaseAdapter {

	List<Contact> contacts;
	LayoutInflater inflater;
	
	public DirectoryAdapter(Context context, List<Contact> contacts) {
		inflater = LayoutInflater.from(context);
		this.contacts = contacts;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private static class ContactHolder {
		TextView name;
		TextView tel;
		TextView email;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ContactHolder contactHolder = null;
		
		if (row == null) {
			row = inflater.inflate(R.layout.directory_list_item, parent, false);
			
			contactHolder = new ContactHolder();
			contactHolder.name = (TextView)row.findViewById(R.id.contact_name);
			contactHolder.tel = (TextView)row.findViewById(R.id.contact_tel);
			contactHolder.email = (TextView)row.findViewById(R.id.contact_email);
			// TO DO
			// -> add the popup menu action
			// -> add "visit website" to the popup menu action
			// -> show icon website if there is a website
			row.setTag(contactHolder);
			
		} else {
			contactHolder = (ContactHolder)row.getTag();
		}
		
		Contact contact = contacts.get(position);
		
		contactHolder.name.setText(contact.getLastName().toUpperCase()+" "+contact.getFirstName());
		contactHolder.tel.setText(contact.getTel());
		contactHolder.email.setText(contact.getEmail());
		
		return row;
	}

}
