package com.hasbrobeta.battleship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PlacementAdapter extends BaseAdapter {

	private Context mContext;
	
	public PlacementAdapter(Context context) {
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
		
		View v = inflater.inflate(R.layout.view_square, parent, false);
		v.setFocusable(false);
		v.setFocusableInTouchMode(false);
		v.setEnabled(false);
		v.setClickable(false);
		/*BattleshipActivity.Board board = BattleshipActivity.getBoard();
		if (board.squares[position].isOccupied = true)
		{
			v = inflater.inflate(R.layout.occupied_square, parent, false);
		}*/
		return v;
	}

}
