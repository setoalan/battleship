package com.hasbrobeta.battleship;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class BattleshipFragment extends Fragment {
	
	public static SingletonBean sb;
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	public static boolean CURRENT_PLAYER = false;
	
	private boolean mHit;
	private boolean mWin;
	
	BattleshipAdapter mAdapter;
	GridView mGridView;
	Button mTransition, mWinner;
	TextView mWinnerTV;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sb = new SingletonBean();
		mWin = false;
		
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
				public void onClick(DialogInterface dialog, int which) {
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
				BattleshipFragment.sb.getPlayers()[CURRENT_PLAYER ? 1 : 0]
						.getSquares()[position].setShot(true);
				if (BattleshipFragment.sb.getPlayers()[CURRENT_PLAYER ? 1 : 0]
						.getSquares()[position].isShot() && 
						BattleshipFragment.sb.getPlayers()[CURRENT_PLAYER ? 0 : 1]
								.getSquares()[position].isOccupied()) {
					BattleshipFragment.sb.getPlayers()[CURRENT_PLAYER ? 1 : 0].addHitCounter();
					if (BattleshipFragment.sb.getPlayers()[CURRENT_PLAYER ? 1 : 0].getHitCounter() == 17) mWin = true;
					mHit = true;
				} else {
					mHit = false;
				}
				mAdapter.notifyDataSetChanged();
				showAlert(mWin, mHit);
			}
		});
		
		return v;
	}
	
	public void showAlert(boolean win, boolean hit) {
		final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		if (win) {
			dialog.setContentView(R.layout.dialog_winner);
			mWinnerTV = (TextView) dialog.findViewById(R.id.winner_tv);
			mWinnerTV.setText("Player " + (CURRENT_PLAYER ? 2 : 1) + " wins!");
			mWinner = (Button) dialog.findViewById(R.id.winner);
			mWinner.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		} else {
			dialog.setContentView(R.layout.dialog_transition);
			mTransition = (Button) dialog.findViewById(R.id.transition);
			if (mHit) mTransition.setBackgroundResource(R.drawable.background_hit);
			else mTransition.setBackgroundResource(R.drawable.background_miss);
			mTransition.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CURRENT_PLAYER = (CURRENT_PLAYER) ? false : true;
					BattleshipFragmentSide.refresh();
					mAdapter.notifyDataSetChanged();
					Thread timer = new Thread() {
						public void run() {
							try {
								sleep(500);
							} catch(InterruptedException e) {
								e.printStackTrace();
							} finally {
								dialog.dismiss();
							}
						}
					};
					timer.start();
				}
			});
		}
		dialog.show();
	}
	
}
