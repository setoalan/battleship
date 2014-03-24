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
	
	static BattleshipAdapterSide mSideAdapter;
	GridView mGridView;
	TextView mGameType;
	private SharedPreferences sharedPref;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship_side, container, false);
		
		mSideAdapter = new BattleshipAdapterSide(getActivity());
		
		mGridView = (GridView) v.findViewById(R.id.grid_view_side);
		mGridView.setAdapter(mSideAdapter);
		mGridView.setEnabled(false);
	
		mGameType = (TextView) v.findViewById(R.id.game_type);
		mGameType.setText("Game Type: " + getGameType());
		
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
	
	public static void refresh() {
		mSideAdapter.notifyDataSetChanged();
	}
	
}
