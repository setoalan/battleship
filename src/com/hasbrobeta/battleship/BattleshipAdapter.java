package com.hasbrobeta.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
		
		View v = inflater.inflate(R.layout.unoccupied_square, parent, false);
		ImageView iv = (ImageView) v.findViewById(R.id.usquare);
		Bitmap bgbig = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
		Bitmap bgsq = Bitmap.createScaledBitmap(bgbig, 12*bgbig.getWidth()/10, 12*bgbig.getHeight()/10, true);
		iv.setImageBitmap(bgsq);
		
		if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
				.getSquares()[position].isShot() && 
				BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
						.getSquares()[position].isOccupied()) {
			// Hit shots
			//iv.setBackgroundColor(Color.GREEN);
			Bitmap bmpOriginal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
			Bitmap bmpunoc = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.peg_hit);
			Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas tempCanvas = new Canvas(bmResult);
			tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
			tempCanvas.drawBitmap(bmpunoc,0,0,null);
			
			Bitmap fin = Bitmap.createScaledBitmap(bmResult, 12*bmResult.getWidth()/10, 12*bmResult.getHeight()/10, true);
			iv.setImageBitmap(fin);

			
			//iv.setImageBitmap(bmResult);

		} else if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
				.getSquares()[position].isShot()) {
			// Missed Shots
			//iv.setBackgroundColor(Color.RED);
			Bitmap bmpOriginal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
			Bitmap bmpunoc = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.peg_miss);
			Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas tempCanvas = new Canvas(bmResult);
			tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
			tempCanvas.drawBitmap(bmpunoc,0,0,null);
		
			Bitmap fin = Bitmap.createScaledBitmap(bmResult, 12*bmResult.getWidth()/10, 12*bmResult.getHeight()/10, true);
			iv.setImageBitmap(fin);

			//iv.setImageBitmap(bmResult);
		}
		
		return v;

	}

}
