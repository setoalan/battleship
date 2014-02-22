package com.hasbrobeta.battleship;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class BattleshipFragment extends Fragment {
	
	public static SingletonBean sb;
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	GridView mGridView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sb = new SingletonBean();
		
		Intent i = new Intent(getActivity(), PlacementActivity.class);
		i.putExtra("PLAYER_NUM", 0);
		startActivityForResult(i, PLAYER_ONE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PLAYER_ONE) {
			Intent i = new Intent(getActivity(), PlacementActivity.class);
			i.putExtra("PLAYER_NUM", 1);
			startActivityForResult(i, PLAYER_TWO);
		} else if (requestCode == PLAYER_TWO) {
			// Display Dialog and then load data to main screen to player one;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship, container, false);
		
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		
		mGridView.setAdapter(new BattleshipAdapter(getActivity()));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		return v;
	}
	
}
