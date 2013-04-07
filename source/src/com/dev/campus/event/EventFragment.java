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

package com.dev.campus.event;

import com.dev.campus.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Custom implementation of a Fragment holding views of an event's detailed view
 * @author CampusUB1 Development Team
 *
 */
public class EventFragment extends Fragment {

	public static final String ARG_EVENT_KEY = "com.dev.campus.EVENT_KEY";
	private Event mEvent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEvent = (Event) getArguments().get(ARG_EVENT_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event, container, false);
		((TextView) rootView.findViewById(R.id.event_view_title)).setText(mEvent.getTitle());
		((TextView) rootView.findViewById(R.id.event_view_category)).setText(mEvent.getCategory());
		((TextView) rootView.findViewById(R.id.event_view_date)).setText(mEvent.getStringDate());

		TextView detailsView = (TextView) rootView.findViewById(R.id.event_view_details);
		detailsView.setText(Html.fromHtml(mEvent.getDetails()));
		Linkify.addLinks(detailsView, Linkify.EMAIL_ADDRESSES);
		detailsView.setMovementMethod(LinkMovementMethod.getInstance());
		return rootView;
	}

	public static EventFragment create(Event event) {
		EventFragment fragment = new EventFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_EVENT_KEY, event);
		fragment.setArguments(args);
		return fragment;
	}

	public EventFragment() {

	}
}
