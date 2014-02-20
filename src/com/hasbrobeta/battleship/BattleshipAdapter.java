package com.hasbrobeta.battleship;

import android.content.Context;
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
		//LayoutInflater inflater = (LayoutInflater) mContext
		//		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(R.drawable.bg);
		return imageView;
		//View v = inflater.inflate(R.layout.unoccupied_square, parent, false);
		
		//return v;
	}

}
