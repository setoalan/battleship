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

	public int getPic(int ship, int direction, int bit)
	{
		if (ship == 0)
		{
			
			if (bit == 0)
			{
				return R.drawable.patrol_1;
			}
			if (bit == 1)
			{
				return R.drawable.patrol_2;
			}
		}
		else if (ship == 1)
		{
			if (bit == 0)
			{
				return R.drawable.destroyer_1;
			}
			if (bit == 1)
			{
				return R.drawable.destroyer_2;
			}
			if (bit == 2)
			{
				return R.drawable.destroyer_3;
			}
		}
		else if (ship == 2)
		{
			if (bit == 0)
			{
				return R.drawable.sub_1;
			}
			if (bit == 1)
			{
				return R.drawable.sub_2;
			}
			if (bit == 2)
			{
				return R.drawable.sub_3;
			}
		}
		else if (ship == 3)
		{
			if (bit == 0)
			{
				return R.drawable.battleship_1;
			}
			if (bit == 1)
			{
				return R.drawable.battleship_2;
			}
			if (bit == 2)
			{
				return R.drawable.battleship_3;
			}
			if (bit == 3)
			{
				return R.drawable.battleship_4;
			}
		}
		else if (ship == 4)
		{
			if (bit == 0)
			{
				return R.drawable.carrier_1;
			}
			if (bit == 1)
			{
				return R.drawable.carrier_2;
			}
			if (bit == 2)
			{
				return R.drawable.carrier_3;
			}
			if (bit == 3)
			{
				return R.drawable.carrier_4;
			}
			if (bit == 4)
			{
				return R.drawable.carrier_5;
			}
		}
		return -1;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.unoccupied_square, parent, false);
		ImageView iv = (ImageView) v.findViewById(R.id.usquare);
		Bitmap bgbig = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
		//Bitmap bgsq = Bitmap.createScaledBitmap(bgbig, bgbig.getWidth()/2, bgbig.getHeight()/2, true);
		Bitmap bgsq = Bitmap.createScaledBitmap(bgbig, 3*bgbig.getWidth()/4, 3*bgbig.getHeight()/4, true);
		iv.setImageBitmap(bgsq);
		
		
		
		if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
				.getSquares()[position].isOccupied())
		{
			int snum = BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
					.getSquares()[position].getShipNum();
			int sdir = BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
					.getSquares()[position].getShipDirection();
			int sseg = BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
					.getSquares()[position].getShipSegmentNum();
			Bitmap bmpunoc = BitmapFactory.decodeResource(mContext.getResources(),getPic(snum,sdir,sseg));
			Bitmap bmpOriginal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
			Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas tempCanvas = new Canvas(bmResult);
			if (sdir == 3)
			{
				tempCanvas.rotate(90, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
			}
			if (sdir == 1)
			{
				tempCanvas.rotate(270, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
			}
			if (sdir == 0)
			{
				tempCanvas.rotate(180, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
			}
			tempCanvas.drawBitmap(bmpOriginal,0,0,null);
			tempCanvas.drawBitmap(bmpunoc,0,0,null);			
			if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
							.getSquares()[position].isShot()) {
				// Current player's ship, shot
				//iv.setBackgroundColor(Color.RED);
				Bitmap bmpPeg = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.peg_hit);
				tempCanvas.drawBitmap(bmpPeg,0,0,null);				
			} 
//			Bitmap finbit = Bitmap.createScaledBitmap(bmResult, bmResult.getWidth()/2, bmResult.getHeight()/2, true);
			Bitmap finbit = Bitmap.createScaledBitmap(bmResult, 3*bmResult.getWidth()/4, 3*bmResult.getHeight()/4, true);
			iv.setImageBitmap(finbit);
		}
		
//		if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 1 : 0]
//				.getSquares()[position].isOccupied() &&
//				BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
//						.getSquares()[position].isShot()) {
//			// Current player's ship, shot
//			//iv.setBackgroundColor(Color.RED);
//			Bitmap bmpOriginal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
//			Bitmap bmpunoc = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.peg_hit);
//			Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
//			Canvas tempCanvas = new Canvas(bmResult);
//			tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
//			tempCanvas.drawBitmap(bmpunoc,0,0,null);
//			
//			iv.setImageBitmap(bmResult);
//			
//		} 
		else if (BattleshipFragment.sb.getPlayers()[BattleshipFragment.CURRENT_PLAYER ? 0 : 1]
				.getSquares()[position].isShot()) {
			// Opponent's missed shot
			Bitmap bmpOriginal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg);
			Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas tempCanvas = new Canvas(bmResult);
			tempCanvas.drawBitmap(bmpOriginal,0,0,null);
			Bitmap bmpPeg = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.peg_miss);
			tempCanvas.drawBitmap(bmpPeg,0,0,null);				
//			Bitmap finbit = Bitmap.createScaledBitmap(bmResult, bmResult.getWidth()/2, bmResult.getHeight()/2, true);
			Bitmap finbit = Bitmap.createScaledBitmap(bmResult, 3*bmResult.getWidth()/4, 3*bmResult.getHeight()/4, true);
			iv.setImageBitmap(finbit);
			//iv.setBackgroundColor(Color.GREEN);
		}
		
		return v;
	}
	
	public void refresh() {
		notifyDataSetChanged();
	}

}
