package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlacementFragmentSide extends Fragment {

	//GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship_side2, container, false);
		
		//mGridView = (GridView) v.findViewById(R.id.grid_view_side);
		
		//mGridView.setAdapter(new BattleshipAdapterSide(getActivity()));
		
		/*mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
			}
		});*/
		
		return v;
	}
}
