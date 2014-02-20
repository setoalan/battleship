package com.hasbrobeta.battleship;

import android.app.Activity;
import android.os.Bundle;

public class BattleshipSettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new BattleshipSettingsFragment())
				.commit();
	}

}
