package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacementFragment extends Fragment {

	//GridLayout mGridView;
	GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.placement, container, false);
		
		//mGridView = (GridLayout) v.findViewById(R.id.grid_view);
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setAdapter(new BattleshipAdapter(getActivity()));

//		for(int i = 0; i < 100; i++)
//		{
//			//ImageView im = inflater.inflate(R.drawable.red,container,false);
//			View vv = inflater.inflate(R.layout.unoccupied_square, container,false);
//			mGridView.addView(vv);
//		}
		//mGridView.setAdapter(new BattleshipAdapter(getActivity()));
		//View sq = inflater.inflate(R.layout.view_square,container);
		//mGridView.addView(sq);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
//				int num = position%10;
//				char n = (char) num;
//				int le = position/10;
//				char l = (char) ('a' + le);
//				//setText(""+n,""+l);
//				TextView tv = (TextView) findViewById(R.id.textView3);
//				tv.setText(n);
//		        ncoord = num;
//		        tv = (TextView) findViewById(R.id.textView1);
//				tv.setText(l);
//		        lcoord = le;
			}
		});
		
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
