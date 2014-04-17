package com.hasbrobeta.battleship;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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
	public View getView(int position, View convertView, final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.unoccupied_square, parent, false);
		if (position == 0)
		{
			v = inflater.inflate(R.layout.ob1, parent, false);
		}
		if (position == 1)
		{
			v = inflater.inflate(R.layout.ob2, parent, false);
		}
		v.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					GridView theView = ((GridView) parent);
					int pos = theView.getPositionForView(arg0);
					theView.performItemClick(theView, pos, theView.getItemIdAtPosition(pos));		
				}
				return false;
			}
		});
		return v;
	}

}
