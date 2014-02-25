package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class PlacementFragment extends Fragment {

	GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_placement, container, false);
		
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		
		mGridView.setAdapter(new PlacementAdapter(getActivity()));
		
		return v;
	}
	
}
