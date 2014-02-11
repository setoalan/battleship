package com.hasbrobeta.battleship;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class BattleshipSettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_settings);
	}
	
}
