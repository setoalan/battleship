package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

public class PlacementFragment extends Fragment {

	GridLayout mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.placement, container, false);
		
		mGridView = (GridLayout) v.findViewById(R.id.grid_view);
		for(int i = 0; i < 100; i++)
		{
			//ImageView im = inflater.inflate(R.drawable.red,container,false);
			View vv = inflater.inflate(R.layout.unoccupied_square, container,false);
			mGridView.addView(vv);
		}
		//mGridView.setAdapter(new BattleshipAdapter(getActivity()));
		//View sq = inflater.inflate(R.layout.view_square,container);
		//mGridView.addView(sq);
		/*mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
			}
		});*/
		
		return v;
	}
	
//	public void placeShip2(ImageView v)
//	{
//		View nv = getView();
//		RelativeLayout rl = (RelativeLayout) nv.findViewById(R.layout.fragment_battleship_side);
//		//RelativeLayout rl = new RelativeLayout(this);
//		//RelativeLayout rl =v.getParent();
//		rl.addView(v);
//	}
	
}
