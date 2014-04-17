package com.hasbrobeta.battleship;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ask.scanninglibrary.ASKActivity;
import ask.scanninglibrary.Scannable;
import ask.scanninglibrary.ScannableFactory;
import ask.scanninglibrary.ScannableViewCustom;

public class BattleshipActivity extends ASKActivity {
	
	private boolean mASKPlayerOne;
	private SharedPreferences sharedPref;
	
	TextView mOK, mCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battleship);
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		mASKPlayerOne = sharedPref.getBoolean("ask_player_one", false);
		if (mASKPlayerOne)
			turnOnScanning();
		else
			turnOffScanning();
	}

	@Override
	public void onBackPressed() {
		final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.dialog_quit);
		mOK = (Button) dialog.findViewById(R.id.ok_btn);
		// NEED IMAGE FOR "OK"
		mOK.setBackgroundResource(R.drawable.background_game_over_1);
		mOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mCancel = (Button) dialog.findViewById(R.id.cancel_btn);
		// NEED IMAGE FOR "Cancel"
		mCancel.setBackgroundResource(R.drawable.background_game_over_2);
		mCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog.cancel();
			}
		});
		dialog.show();
	}
	
	
	@Override
	public Scannable createScannable() {
		Log.wtf("BattleShipt Activity", "My create scannable new");
		View ROOT = findViewById(android.R.id.content).getRootView();
		View lll = findViewById(R.id.grid_view_side);
		return new ScannableViewCustom(ROOT,lll);
	}
}
