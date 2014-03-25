package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class BattleshipMenu extends Activity {
	
	private SoundPool soundPool;
	private int soundID;
	private MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		mediaPlayer = MediaPlayer.create(this, R.raw.must_persevere);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		soundID = soundPool.load(this, R.raw.menu_select, 1);
	}
	
	public void startGame(View view) {
		soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1);
		Intent i = new Intent(this, BattleshipActivity.class);
		startActivity(i);
	}
	
	public void settings(View view) {
		soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1);
		Intent i = new Intent(this, BattleshipSettingsActivity.class);
		startActivity(i);
	}
	
}
