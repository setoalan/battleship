package com.hasbrobeta.battleship;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BattleshipAdapter extends BaseAdapter {

	private Context mContext;
	
	public BattleshipAdapter(Context context) {
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
		ImageView iv = (ImageView) v.findViewById(R.id.view_square);
		if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
				.getSquares()[position].isShot()) {
			iv.setBackgroundColor(Color.RED);
		}
		
		return v;
	}

}
