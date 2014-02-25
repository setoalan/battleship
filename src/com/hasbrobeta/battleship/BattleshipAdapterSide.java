package com.hasbrobeta.battleship;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BattleshipAdapterSide extends BaseAdapter {

	private Context mContext;
	
	public BattleshipAdapterSide(Context context) {
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.view_square_side, parent, false);
		ImageView iv = (ImageView) v.findViewById(R.id.view_square_side);
		if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
				.getSquares()[position].isOccupied() &&
				BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
						.getSquares()[position].isShot()) {
			// Current player's ship, shot
			iv.setBackgroundColor(Color.RED);
		} else if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
				.getSquares()[position].isOccupied()) {
			// Current player's ships, not shot
			iv.setBackgroundColor(Color.BLUE);
		} else if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
				.getSquares()[position].isShot()) {
			// Opponent's missed shot
			iv.setBackgroundColor(Color.GREEN);
		}
		
		return v;
	}
	
	public void refresh() {
		notifyDataSetChanged();
	}

}
