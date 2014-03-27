package com.hasbrobeta.battleship;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class BattleshipSettingsFragment extends PreferenceFragment {

	SharedPreferences sharedPref;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.fragment_settings);
	}
	
}
