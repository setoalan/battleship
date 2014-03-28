package com.hasbrobeta.battleship;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;
import ask.scanninglibrary.ASKActivity;
import ask.scanninglibrary.Scannable;
import ask.scanninglibrary.ScannableFactory;

public class BattleshipActivity extends ASKActivity {
	
	private boolean mASKPlayerOne;
	private SharedPreferences sharedPref;
	
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
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Quit game and return to main menu?");
		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				dialog.cancel();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	
	}
	
	@Override
	public Scannable createScannable() {
		Log.wtf("BattleShipt Activity", "My create scannable");
		LinearLayout ll = (LinearLayout) findViewById(R.layout.fragment_battleship);
		return ScannableFactory.createScannable(ll);
	}
}
