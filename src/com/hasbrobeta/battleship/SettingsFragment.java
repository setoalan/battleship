package com.hasbrobeta.battleship;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

	SharedPreferences sharedPref;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.fragment_settings);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (BattleshipMenu.musicEnabled)
			BattleshipMenu.mediaPlayer.start();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (BattleshipMenu.mediaPlayer.isPlaying())
			BattleshipMenu.mediaPlayer.pause();
	}
	
}
