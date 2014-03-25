package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class BattleshipMenu extends Activity {
	
	private static SoundPool soundPool;
	private MediaPlayer mediaPlayer;
	public static int cannon,
				menu_cancel,
				menu_select,
				splash;
	
	public static void playSound(int soundID, int loop) {
		soundPool.play(soundID, 1.0f, 1.0f, 1, loop, 1);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		cannon = soundPool.load(this, R.raw.cannon, 1);
		menu_cancel = soundPool.load(this, R.raw.menu_cancel, 1);
		menu_select = soundPool.load(this, R.raw.menu_select, 1);
		splash = soundPool.load(this, R.raw.splash, 1);
		mediaPlayer = MediaPlayer.create(this, R.raw.must_persevere);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}
	
	public void startGame(View view) {
		playSound(menu_select, 0);
		Intent i = new Intent(this, BattleshipActivity.class);
		startActivity(i);
	}
	
	public void settings(View view) {
		playSound(menu_select, 0);
		Intent i = new Intent(this, BattleshipSettingsActivity.class);
		startActivity(i);
	}
	
}
