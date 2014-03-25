package com.hasbrobeta.battleship;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class BattleshipFragment extends Fragment {
	
	public static SingletonBean sb;
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	public static boolean CURRENT_PLAYER = false;
	
	private boolean mHit;
	private boolean mWin;
	private int mShip;
	private int mFiresLeft;
	private SharedPreferences sharedPref;
	private String mGameType;
	
	BattleshipAdapter mAdapter;
	GridView mGridView;
	Button mTransition, mWinner;
	TextView mWinnerTV;
	
	Board[] player;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sb = new SingletonBean();
		player = BattleshipFragment.sb.getPlayers();
		mWin = false;
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mGameType = sharedPref.getString("game_type", "0");

		Intent i = new Intent(getActivity(), PlacementActivity.class);
		i.putExtra("PLAYER_NUM", 0);
		startActivityForResult(i, PLAYER_ONE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0) {
			getActivity().finish();
			return;
		}
		switch (requestCode) {
		case PLAYER_ONE:
			Intent i = new Intent(getActivity(), PlacementActivity.class);
			i.putExtra("PLAYER_NUM", 1);
			startActivityForResult(i, PLAYER_TWO);
			return;
		case PLAYER_TWO:
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
			return;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_battleship, container, false);
		
		mAdapter = new BattleshipAdapter(getActivity());
		
		mFiresLeft = player[CURRENT_PLAYER ? 1 : 0].getNumCurShips();
		
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (player[CURRENT_PLAYER ? 1 : 0]
						.getSquares()[position].isShot()) {
					Toast.makeText(getActivity(), "Square already shot, please select another square", Toast.LENGTH_SHORT).show();
					return;
				}
				mGridView.setEnabled(false);
				player[CURRENT_PLAYER ? 1 : 0] .getSquares()[position].setShot(true);
				if (player[CURRENT_PLAYER ? 1 : 0].getSquares()[position].isShot() && 
						player[CURRENT_PLAYER ? 0 : 1].getSquares()[position].isOccupied()) {
					player[CURRENT_PLAYER ? 1 : 0].addHitCounter();
					if (player[CURRENT_PLAYER ? 1 : 0].getHitCounter() == 17) {
						mWin = true;
						showAlert(mWin, mHit, mShip);
					}
					mShip = shipSank(player[CURRENT_PLAYER ? 0 : 1].getSquares()[position]);
					mHit = true;
				} else {
					mHit = false;
				}
				mAdapter.notifyDataSetChanged();
				if (mGameType.equals("salvo")) {
					mGridView.setEnabled(true);
					mFiresLeft--;
					if (mFiresLeft == 0) {
						mFiresLeft = player[CURRENT_PLAYER ? 1 : 0].getNumCurShips();
						showAlert(mWin, mHit, mShip);
					}
				} else if (mGameType.equals("hitmiss")){
					if (mHit) {
						mGridView.setEnabled(true);
					} else {
						showAlert(mWin, mHit, mShip);
					}
				} else {
					showAlert(mWin, mHit, mShip);
				}
			}
		});
		
		return v;
	}
	
	private int shipSank(Square square) {
		switch (square.getShipNum()) {
		case 0:
			player[CURRENT_PLAYER ? 1 : 0].getShips().subPatrolBoat();
			if (player[CURRENT_PLAYER ? 1 : 0].getShips().getPatrolBoat() == 0) {
				player[CURRENT_PLAYER ? 1 : 0].subNumCurShips();
				return 0;
			}
			return 5;
		case 1:
			player[CURRENT_PLAYER ? 1 : 0].getShips().subDestroyer();
			if (player[CURRENT_PLAYER ? 1 : 0].getShips().getDestroyer() == 0) {
				player[CURRENT_PLAYER ? 1 : 0].subNumCurShips();
				return 1;
			}
			return 6;
		case 2:
			player[CURRENT_PLAYER ? 1 : 0].getShips().subSubmarine();
			if (player[CURRENT_PLAYER ? 1 : 0].getShips().getSubmarine() == 0) {
				player[CURRENT_PLAYER ? 1 : 0].subNumCurShips();
				return 2;
			}
			return 7;
		case 3:
			player[CURRENT_PLAYER ? 1 : 0].getShips().subBattleship();
			if (player[CURRENT_PLAYER ? 1 : 0].getShips().getBattleship() == 0) {
				player[CURRENT_PLAYER ? 1 : 0].subNumCurShips();
				return 3;
			}
			return 8;
		case 4:
			player[CURRENT_PLAYER ? 1 : 0].getShips().subAircraftCarrier();
			if (player[CURRENT_PLAYER ? 1 : 0].getShips().getAircraftCarrier() == 0) {
				player[CURRENT_PLAYER ? 1 : 0].subNumCurShips();
				return 4;
			}
			return 9;
		default:
			return -1;
		}
	}
	
	private void showAlert(boolean win, boolean hit, int ship) {
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
			setTransitionBackground(hit, ship);
			mTransition.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CURRENT_PLAYER = (CURRENT_PLAYER) ? false : true;
					BattleshipFragmentSide.refresh();
					mAdapter.notifyDataSetChanged();
					mGridView.setEnabled(true);
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
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// do nothing
                }
                return true;
			}
		});
		dialog.show();
	}

	private void setTransitionBackground(boolean hit, int ship) {
		if (!hit) {
			mTransition.setBackgroundResource(R.drawable.background_miss);
		} else {
			switch(ship) {
			case 0: mTransition.setBackgroundResource(R.drawable.background_sunk_patrol);
				return;
			case 1: mTransition.setBackgroundResource(R.drawable.background_sunk_destroyer);
				return;
			case 2: mTransition.setBackgroundResource(R.drawable.background_sunk_sub);
				return;
			case 3: mTransition.setBackgroundResource(R.drawable.background_sunk_battleship);
				return;
			case 4: mTransition.setBackgroundResource(R.drawable.background_sunk_carrier);
				return;
			case 5: mTransition.setBackgroundResource(R.drawable.background_hit_patrol);
				return;
			case 6: mTransition.setBackgroundResource(R.drawable.background_hit_destroyer);
				return;
			case 7: mTransition.setBackgroundResource(R.drawable.background_hit_sub);
				return;
			case 8: mTransition.setBackgroundResource(R.drawable.background_hit_battleship);
				return;
			case 9: mTransition.setBackgroundResource(R.drawable.background_hit_carrier);
				return;
			}
		}
		return;
	}
	
}
