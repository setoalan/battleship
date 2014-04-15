package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class BattleshipFragmentSide extends Fragment {
	
	private static BattleshipAdapterSide mSideAdapter;
	private static TextView mCurrentPlayer;
	private SharedPreferences sharedPref;
	private String mGameType;
	
	GridView mGridView;
	TextView mGameTypeTV;
	static TextView mShotsRemaining;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship_side, container, false);
		
		mSideAdapter = new BattleshipAdapterSide(getActivity());
		
		mGridView = (GridView) v.findViewById(R.id.grid_view_side);
		mGridView.setAdapter(mSideAdapter);
		mGridView.setEnabled(false);
	
		mGameTypeTV = (TextView) v.findViewById(R.id.game_type);
		mGameTypeTV.setText("Game Type: " + getGameType());
		
		mCurrentPlayer = (TextView) v.findViewById(R.id.current_player);
		mCurrentPlayer.setText("Player ONE");
		
		mGameType = sharedPref.getString("game_type", "0");
		mShotsRemaining = (TextView) v.findViewById(R.id.shots_remaining);
		if (mGameType.equals("salvo")) {
			mShotsRemaining.setText("Shots Remaining: 5");
		} else if (mGameType.equals("hitmiss")) {
			mShotsRemaining.setText("Shots Remaining: ~");
		} else {
			mShotsRemaining.setText("Shots Remaining: 1");
		}
		
		return v;
	}
	
	private String getGameType() {
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		if (sharedPref.getString("game_type", "0").equals("classic")) {
			return "Classic";
		} else if (sharedPref.getString("game_type", "0").equals("salvo")) {
			return "Salvo";
		} else {
			return "Hit Until Miss";
		}
	}
	
	public static void decrementFiresLeft(int shotsRemain) {
		mShotsRemaining.setText("Shots Remaining: " + shotsRemain);
	}
	
	public static void resetFiresLeft(int remainingShips) {
		mShotsRemaining.setText("Shots Remaining: " + remainingShips);
	}
	
	public static void refresh() {
		if (BattleshipFragment.CURRENT_PLAYER)
			mCurrentPlayer.setText("Player TWO");
		else
			mCurrentPlayer.setText("Player ONE");
		mSideAdapter.notifyDataSetChanged();
	}
	
}
