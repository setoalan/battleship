package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class BattleshipMenu extends Activity {
	
	private static SoundPool soundPool;
	private static boolean soundsEnabled;
	public static MediaPlayer mediaPlayer;
	public static int cannon,
				menu_cancel,
				menu_select,
				splash;
	
	public static void playSound(int soundID, int loop) {
		if (soundsEnabled)
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
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("play_music", false))
			if (!mediaPlayer.isPlaying())
				mediaPlayer.start();
		soundsEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("play_sounds", true);
	}
	
//	@Override
//	public void onPause() {
//		super.onPause();
//		if (mediaPlayer.isPlaying() && this.isFinishing()) {
//			mediaPlayer.pause();
//		}
//	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("play_music", false)) {
			mediaPlayer.start();
		} else if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.seekTo(0);
		}
		soundsEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("play_sounds", true);
	}
	
	public void startGame(View view) {
		playSound(menu_select, 0);
		Intent i = new Intent(this, BattleshipActivity.class);
		startActivity(i);
	}
	
	public void settings(View view) {
		playSound(menu_select, 0);
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}
	
}
