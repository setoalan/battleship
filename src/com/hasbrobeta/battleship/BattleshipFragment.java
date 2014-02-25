package com.hasbrobeta.battleship;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	public static boolean CURRENT_PLAYER = false;
	
	BattleshipAdapter mAdapter;
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
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Please hand tablet to player one.");
			builder.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					BattleshipFragmentSide.refresh();
					mAdapter.notifyDataSetChanged();
				}
			});
			builder.show();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship, container, false);
		
		mAdapter = new BattleshipAdapter(getActivity());
		
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setAdapter(mAdapter);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
						.getSquares()[position].setShot(true);
				if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
						.getSquares()[position].isShot() && 
						BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
								.getSquares()[position].isOccupied()) {
					BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0].addHitCounter();
					if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0].getHitCounter() == 17) {
						Log.i("TAG", "WE HAVE A WINNER");
					}
				}
				mAdapter.notifyDataSetChanged();
				showAlert();
			}
		});
		
		return v;
	}
	
	
	public void showAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Please hand tablet to other player.");
		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				CURRENT_PLAYER = (CURRENT_PLAYER) ? false : true;
				BattleshipFragmentSide.refresh();
				mAdapter.notifyDataSetChanged();
			}
		});
		builder.show();
	}
}
