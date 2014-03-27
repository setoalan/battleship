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

	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	public static boolean CURRENT_PLAYER = false;
	public static SingletonBean sb;
	
	private boolean mHit, mWin, mASKPlayerOne, mASKPlayerTwo, multi_play;
	private int mShip, mFiresLeft;
	private Board[] player;
	private SharedPreferences sharedPref;
	private String mGameType, mDeclareType;
	
	BattleshipAdapter mAdapter;
	Button mTransition, mWinner;
	GridView mGridView;
	TextView mWinnerTV;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sb = new SingletonBean();
		mWin = false;
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mASKPlayerOne = sharedPref.getBoolean("ask_player_one", false);
		mASKPlayerTwo = sharedPref.getBoolean("ask_player_two", false);
		multi_play = sharedPref.getBoolean("game_mode", true);
		player = BattleshipFragment.sb.getPlayers();
		mFiresLeft = player[CURRENT_PLAYER ? 1 : 0].getNumCurShips();
		mGameType = sharedPref.getString("game_type", "0");
		mDeclareType = sharedPref.getString("game_declare_type", "0");

		Intent i = new Intent(getActivity(), PlacementActivity.class);
		if (multi_play)
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
			if (!multi_play) {
				
				//ai placement here
				
			} else {
				startActivityForResult(i, PLAYER_TWO);
			}
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
		
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (player[CURRENT_PLAYER ? 1 : 0].getSquares()[position].isShot()) {
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
					if (mHit)
						mGridView.setEnabled(true);
					else
						showAlert(mWin, mHit, mShip);
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
			mWinner = (Button) dialog.findViewById(R.id.winner);
			if (CURRENT_PLAYER)
				mTransition.setBackgroundResource(R.drawable.background_game_over_1);
			else
				mTransition.setBackgroundResource(R.drawable.background_game_over_2);
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
					turnASKOnOff();
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
				if (keyCode == KeyEvent.KEYCODE_BACK) {}
                return true;
			}
		});
		dialog.show();
	}
	
	private void turnASKOnOff() {
		BattleshipActivity mBattleshipActivity = (BattleshipActivity) getActivity();
		
		if (!CURRENT_PLAYER) {
			if (mASKPlayerOne)
				mBattleshipActivity.turnOnScanning();
			else
				mBattleshipActivity.turnOffScanning();
		} else {
			if (mASKPlayerTwo && !multi_play)
				mBattleshipActivity.turnOnScanning();
			else
				mBattleshipActivity.turnOffScanning();
		}
		
		return;
	}

	private void setTransitionBackground(boolean hit, int ship) {
		BattleshipMenu.playSound(BattleshipMenu.cannon, 0);
		if (!hit) {
			BattleshipMenu.playSound(BattleshipMenu.splash, 0);
			mTransition.setBackgroundResource(R.drawable.background_miss);
		} else if (mDeclareType.equals("none")) {
			mTransition.setBackgroundResource(R.drawable.background_hit);
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
			case 5: if (mDeclareType.equals("hit"))
					mTransition.setBackgroundResource(R.drawable.background_hit_patrol);
				else
					mTransition.setBackgroundResource(R.drawable.background_hit);
				return;
			case 6: if (mDeclareType.equals("hit"))
					mTransition.setBackgroundResource(R.drawable.background_hit_destroyer);
				else
					mTransition.setBackgroundResource(R.drawable.background_hit);
				return;
			case 7: if (mDeclareType.equals("hit"))
					mTransition.setBackgroundResource(R.drawable.background_hit_sub);
				else
					mTransition.setBackgroundResource(R.drawable.background_hit);
				return;
			case 8: if (mDeclareType.equals("hit"))
					mTransition.setBackgroundResource(R.drawable.background_hit_battleship);
				else
					mTransition.setBackgroundResource(R.drawable.background_hit);
				return;
			case 9: if (mDeclareType.equals("hit"))
					mTransition.setBackgroundResource(R.drawable.background_hit_carrier);
				else
					mTransition.setBackgroundResource(R.drawable.background_hit);
				return;
			}
		}
		return;
	}
	
}
