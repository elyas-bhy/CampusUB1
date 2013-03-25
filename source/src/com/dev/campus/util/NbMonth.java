package com.dev.campus.util;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class NbMonth extends Dialog implements Button.OnClickListener, SeekBar.OnSeekBarChangeListener {

	private SeekBar mSeekBar; // barre glissante
	private TextView mMinLabel, mMaxLabel, mTimeUnitLabel; // valeurs qui seront affichées aux extrémités de la barre
	private String[] mRepeatStrings;  // ?
	private int[] mTimeUnitValues; // valeur actuelle
	private int mSeekBarPos; // position de la barre
	private Button mButton;

	protected void onCreate(Bundle savedInstanceState){
		CampusUB1App.LogD("ouverture fenetre");
		super.onCreate(savedInstanceState);
		setTitle(R.string.nbMonth_title);

		int timeUnit = CampusUB1App.persistence.getNbMonth();
		mRepeatStrings = CampusUB1App.getInstance().getResources().getStringArray(R.array.pref_nbMonth);
		mTimeUnitValues = CampusUB1App.getInstance().getResources().getIntArray(R.array.nbMonth);
		mSeekBarPos = -1;
			
		int value = Persistence.MaxNbMonth; // 6
		while (value != timeUnit && mSeekBarPos < (mTimeUnitValues.length - 1)) {
			mSeekBarPos++;
			value = mTimeUnitValues[mSeekBarPos];
		}
		
		setContentView(R.layout.dialog_layout);
		mMinLabel = (TextView) findViewById(R.id.dlg_min_label);
		mMaxLabel = (TextView) findViewById(R.id.dlg_max_label);
		mTimeUnitLabel = (TextView) findViewById(R.id.dlg_timeout_label);
		mMinLabel.setText(mRepeatStrings[0]);
		mMaxLabel.setText(mRepeatStrings[mRepeatStrings.length - 1]);
		mTimeUnitLabel.setText(mRepeatStrings[mSeekBarPos]);

		mButton = (Button) findViewById(R.id.dlg_btn_done);
		mButton.setOnClickListener(this); 

		mSeekBar = (SeekBar) findViewById(R.id.dlg_seekbar);
		mSeekBar.setMax(mTimeUnitValues.length - 1);
		mSeekBar.setProgress(mSeekBarPos);
		mSeekBar.setOnSeekBarChangeListener(this); 
		mSeekBar.requestFocus();
	}

	public NbMonth(Context context) {
		super(context);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		mTimeUnitLabel.setText(mRepeatStrings[progress]);
		CampusUB1App.persistence.setNbMonth(mTimeUnitValues[progress]);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		dismiss();
		
	}

}