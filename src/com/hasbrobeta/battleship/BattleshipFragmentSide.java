package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class BattleshipFragmentSide extends Fragment {
	
	static BattleshipAdapterSide mSideAdapter;
	GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship_side, container, false);
		
		mSideAdapter = new BattleshipAdapterSide(getActivity());
		
		mGridView = (GridView) v.findViewById(R.id.grid_view_side);
		mGridView.setAdapter(mSideAdapter);
		mGridView.setEnabled(false);
		
		return v;
	}
	
	public static void refresh() {
		mSideAdapter.notifyDataSetChanged();
	}
	
}
