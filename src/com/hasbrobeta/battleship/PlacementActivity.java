package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ask.scanninglibrary.ASKActivity;

public class PlacementActivity extends ASKActivity {

	GridView mGridView;
	TextView mShipTV, mLocationTV, mCurrentPlayerTV;
	private boolean mASKPlayerOne, mASKPlayerTwo;
	private boolean isDrawn = true;
	private int lcoord = 0;
	private int ncoord = 0;
	private int direction = 2;
	private int shipType = 0;
	private int playerNum;
	
	private SharedPreferences sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placement);

		playerNum = getIntent().getIntExtra("PLAYER_NUM", 0);
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		mShipTV = (TextView) findViewById(R.id.ship_text);
		mLocationTV = (TextView) findViewById(R.id.location_text);
		mCurrentPlayerTV = (TextView) findViewById(R.id.current_player_place);
		if (playerNum == 0)
			mCurrentPlayerTV.setText("Player ONE");
		else 
			mCurrentPlayerTV.setText("Player TWO");
		
		mGridView = (GridView) findViewById(R.id.grid_view);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				int directionStored = direction;
				int lcoordStored = lcoord;
				int ncoordStored = ncoord;
				
				int num = 1 + position % 10;
				int le = position / 10;
				char l = (char) ('A' + le);
				mLocationTV.setText("" + num + l);
				if (direction == 0) helper(-1,num-1+10*le);
				else if (direction == 1) helper(-10,num-1+10*le);
				else if (direction == 2) helper(1,num-1+10*le);
				else if (direction == 3) helper(10,num-1+10*le);
				ncoord = num - 1;
				lcoord = le;
				Button holder = (Button) findViewById(R.id.rotate_button);
				if (!isDrawn) holder.performClick();
				if (!isDrawn)//if here, then this is not a valid placement
				{//so we undo it
					direction = directionStored;
					lcoord = lcoordStored;
					ncoord = ncoordStored;
					View vvv = null;
					if (direction == 0) directionL(vvv);
					else if (direction == 1) directionU(vvv);
					else if (direction == 2) directionR(vvv);
					else if (direction == 3) directionD(vvv);
					l = (char) ('A'+lcoord);
					mLocationTV.setText("" + (ncoord+1)+l);
					
					//Toast.makeText(PlacementActivity.this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
				}
			}
		});	
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		mASKPlayerOne = sharedPref.getBoolean("ask_player_one", false);
		mASKPlayerTwo = sharedPref.getBoolean("ask_player_two", false);
		if (playerNum == 0)
		{	
			if (mASKPlayerOne)
				turnOnScanning();
			else 
				turnOffScanning();
		}
		else
		{
			if (mASKPlayerTwo)
				turnOnScanning();
			else 
				turnOffScanning();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (BattleshipMenu.musicEnabled)
			BattleshipMenu.mediaPlayer.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (BattleshipMenu.mediaPlayer.isPlaying())
			BattleshipMenu.mediaPlayer.pause();
	}
	
	private boolean helper(int adjust, int newCoord)
	{//adjust -1 left, -10 up, 1 right, 10 down
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		int coord = ncoord + 10 * lcoord;
		
		int realadjust = 0;
		if (this.direction == 0) realadjust = -1;//left
		else if (this.direction == 1) realadjust = -10;//up
		else if (this.direction == 2) realadjust = 1;//right
		else if (this.direction == 3) realadjust = 10;//down

		int i = 0;
		if (this.shipType == 0) i = 2;
		else if (this.shipType == 1 || this.shipType == 2) i = 3;
		else if (this.shipType == 3) i = 4;
		else if (this.shipType == 4) i = 5;
		if (this.isDrawn)//erase previously drawn squares if there are any for this ship
		{
			for (int j = 0; j < i; j++) {												
				GridView g = (GridView) findViewById(R.id.grid_view);
				LinearLayout nv = (LinearLayout) g.getChildAt(coord+j*realadjust);
				if (nv != null && !BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+j*realadjust].isOccupied())
				{
					ImageView nvv = (ImageView) nv.findViewById(R.id.usquare);
					nvv.setImageResource(R.drawable.bg);
				}
			}
			this.isDrawn = false;
		}
		if (BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[newCoord].isOccupied() == false &&
				newCoord+(i-1)*adjust <= 99 &&
				newCoord+(i-1)*adjust >= 0 &&
				(adjust != -1 || newCoord - i + 1 >= 10*((int)(newCoord/10))) &&//need + 1 to allow ships along left edge (PB E1E2 for example)
				(adjust != 1 || newCoord + i <= 10+10*((int)(newCoord/10)))) {//+1 or -1 apparently not needed here, seems to be working fine
			for (int k = 0; k < i; k++) {
				if (BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[newCoord+k*adjust].isOccupied()) {
					//Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			for (int j = 0; j < i; j++) {
				Bitmap bmpOriginal = BitmapFactory.decodeResource(getResources(),getPic(shipType,direction,j));
				Bitmap bmpunoc = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
				Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas tempCanvas = new Canvas(bmResult);
				
				if (adjust == -10) tempCanvas.rotate(270, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				if (adjust == -1) tempCanvas.rotate(180, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				if (adjust == 10) tempCanvas.rotate(90, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				
				tempCanvas.drawBitmap(bmpunoc,0,0,null);
				tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
				
				GridView g = (GridView) findViewById(R.id.grid_view);
				LinearLayout nv = (LinearLayout) g.getChildAt(newCoord+j*adjust);
				ImageView nvv = (ImageView) nv.findViewById(R.id.usquare);
				nvv.setImageBitmap(bmResult);
			}
			this.isDrawn = true;
			return true;
		}
		else 
		{
			//Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	public void rotate(View view)
	{
		int dir = (this.direction);
		for (int i = 0; i < 4; i++)
		{
			if (dir == 0) directionU(view);
			else if (dir == 1) directionR(view);
			else if (dir == 2) directionD(view);
			else if (dir == 3) directionL(view);
			if (isDrawn) return;
			else dir = (dir+1)%4;
		}
		Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
	}
	
	public void directionL(View view) {		
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		helper(-1,this.ncoord + 10 * this.lcoord);
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Left");
        this.direction = 0;
	}

	public void directionR(View view) {
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		helper(1,this.ncoord + 10 * this.lcoord);
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Right");
        this.direction = 2;
	}

	public void directionU(View view) {
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		helper(-10,this.ncoord + 10 * this.lcoord);
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Up");
		this.direction = 1;
	}

	public void directionD(View view) {
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		helper(10,this.ncoord + 10 * this.lcoord);
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Down");
		this.direction = 3;
	}
	
	private void ship3d() {
		ImageView temp = (ImageView) findViewById(R.id.showShip);
		temp.setImageResource(R.drawable.button_destroyer_pressed);
		this.shipType = 1;
		mShipTV.setText(getResources().getString(R.string.destroyer));
	}
	
	private void ship3s() {
		ImageView temp = (ImageView) findViewById(R.id.showShip);
		temp.setImageResource(R.drawable.button_sub_pressed);
		this.shipType = 2;
		mShipTV.setText(getResources().getString(R.string.submarine));
	}
	
	private void ship4() {
		ImageView temp = (ImageView) findViewById(R.id.showShip);
		temp.setImageResource(R.drawable.button_battleship_pressed);
		this.shipType = 3;
		mShipTV.setText(getResources().getString(R.string.battleship));
	}
	
	private void ship5() {
		ImageView temp = (ImageView) findViewById(R.id.showShip);
		temp.setImageResource(R.drawable.button_carrier_pressed);
		this.shipType = 4;
		mShipTV.setText(getResources().getString(R.string.aircraft_carrier));
	}
	
	private int getPic(int ship, int direction, int bit) {
		if (ship == 0) {
			if (bit == 0) return R.drawable.patrol_1;
			if (bit == 1) return R.drawable.patrol_2;
		} else if (ship == 1) {
			if (bit == 0) return R.drawable.destroyer_1;
			if (bit == 1) return R.drawable.destroyer_2;
			if (bit == 2) return R.drawable.destroyer_3;
		} else if (ship == 2) {
			if (bit == 0) return R.drawable.sub_1;
			if (bit == 1) return R.drawable.sub_2;
			if (bit == 2) return R.drawable.sub_3;
		} else if (ship == 3) {
			if (bit == 0) return R.drawable.battleship_1;
			if (bit == 1) return R.drawable.battleship_2;
			if (bit == 2) return R.drawable.battleship_3;
			if (bit == 3) return R.drawable.battleship_4;
		} else if (ship == 4) {
			if (bit == 0) return R.drawable.carrier_1;
			if (bit == 1) return R.drawable.carrier_2;
			if (bit == 2) return R.drawable.carrier_3;
			if (bit == 3) return R.drawable.carrier_4;
			if (bit == 4) return R.drawable.carrier_5;
		}
		
		return -1;
	}
	
	public void placeShip(View v) {
		BattleshipMenu.playSound(BattleshipMenu.menu_select, 0);
		//if (shipType == -1) {
		//	Toast.makeText(this, "Please select a ship to place!", Toast.LENGTH_SHORT).show();
	     //   return;
		//}
		
		int coord = ncoord + 10 * lcoord;
		int adjust = 0;
		if (this.direction == 0) adjust = -1;//left
		else if (this.direction == 1) adjust = -10;//up
		else if (this.direction == 2) adjust = 1;//right
		else if (this.direction == 3) adjust = 10;//down
		
		int i = 0;
		if (this.shipType == 0) i = 2;
		else if (this.shipType == 1 || this.shipType == 2) i = 3;
		else if (this.shipType == 3) i = 4;
		else if (this.shipType == 4) i = 5;

		if (BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord].isOccupied() == false &&
				coord+(i-1)*adjust <= 99 &&
				coord+(i-1)*adjust >= 0 &&
				(this.direction!= 0 || coord - i + 1 >= 10*((int)(coord/10))) &&//need + 1 to allow ships along left edge (PB E1E2 for example)
				(this.direction!= 2 || coord + i <= 10+10*((int)(coord/10)))) {//+1 or -1 apparently not needed here, seems to be working fine
			for (int k = 0; k < i; k++) {
				if (BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+k*adjust].isOccupied()) {
					Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			for (int j = 0; j < i; j++) {
				BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+j*adjust].setOccupied(true);
				BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipNum(this.shipType);
				BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipDirection(this.direction);//0 if horizontal, 1 if vertical
				BattleshipFragment.singletonBean.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipSegmentNum(j);

			}

			if (this.shipType < 5) {
				BattleshipFragment.singletonBean.getPlayers()[playerNum].addNumShipsPlaced();
			}
			
			if (this.shipType == 0) {ship3d(); this.isDrawn = false;}
			else if (this.shipType == 1) {ship3s(); this.isDrawn = false;}
			else if (this.shipType == 2) {ship4(); this.isDrawn = false;}
			else if (this.shipType == 3) {ship5(); this.isDrawn = false;}
			else if (this.shipType == 4) {/*do nothing*/}

			if (BattleshipFragment.singletonBean.getPlayers()[playerNum].getNumShipsPlaced() == 5) {
				Intent returnIntent = new Intent();
				if (playerNum == 0) setResult(BattleshipFragment.PLAYER_ONE, returnIntent);
				else setResult(BattleshipFragment.PLAYER_TWO, returnIntent);
				finish();
			}
		} else {
			Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onBackPressed() {
		BattleshipMenu.playSound(BattleshipMenu.menu_cancel, 0);
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	

}
