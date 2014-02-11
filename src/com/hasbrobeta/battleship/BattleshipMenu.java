package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BattleshipMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}
	
	public void startGame(View view) {
		Intent i = new Intent(this, BattleshipActivity.class);
		startActivity(i);
	}
	
	public void settings(View view) {
		Intent i = new Intent(this, BattleshipSettingsActivity.class);
		startActivity(i);
	}
	
}
