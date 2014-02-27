package com.hasbrobeta.battleship;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlacementActivity extends Activity {

	GridView mGridView;
	TextView mShipTV, mLocationTV;
	Button mShipBtn;
	
	private int lcoord = 0;
	private int ncoord = 0;
	private int direction = 0;
	private int shipType = -1;
	private int playerNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placement);

		playerNum = getIntent().getIntExtra("PLAYER_NUM", 0);
		
		mShipTV = (TextView) findViewById(R.id.ship_text);
		mLocationTV = (TextView) findViewById(R.id.location_text);
		
		mGridView = (GridView) findViewById(R.id.grid_view);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				int num = 1 + position % 10;
				int le = position / 10;
				char l = (char) ('A' + le);
				mLocationTV.setText("" + num + l);
				ncoord = num - 1;
				lcoord = le;
			}
		});
	}
	
	public void setText(String n, String l) {
		mLocationTV.setText(n + l);
	}
	
	public void ship2(View view) {
		this.shipType = 0;
		mShipTV.setText(getResources().getString(R.string.patrol_boat));
	}
	
	public void ship3d(View view) {
		this.shipType = 1;
		mShipTV.setText(getResources().getString(R.string.destroyer));
	}
	
	public void ship3s(View view) {
		this.shipType = 2;
		mShipTV.setText(getResources().getString(R.string.submarine));
	}
	
	public void ship4(View view) {
		this.shipType = 3;
		mShipTV.setText(getResources().getString(R.string.battleship));
	}
	
	public void ship5(View view) {
		this.shipType = 4;
		mShipTV.setText(getResources().getString(R.string.aircraft_carrier));
	}
	
	public void directionL(View view) {
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Left");
        this.direction = 0;
	}

	public void directionR(View view) {
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Right");
        this.direction = 2;
	}

	public void directionU(View view) {
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Up");
        this.direction = 1;
	}

	public void directionD(View view) {
		TextView tv = (TextView) findViewById(R.id.direction_text);
		tv.setText("Down");
        this.direction = 3;
	}
	
	public int getPic(int ship, int direction, int bit) {
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
		if (shipType == -1) {
			Toast.makeText(this, "Please select a ship to place!", Toast.LENGTH_SHORT).show();
	        return;
		}
		
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

		if (BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord].isOccupied() == false &&
				coord+(i-1)*adjust <= 99 &&
				coord+(i-1)*adjust >= 0 &&
				(this.direction!= 0 || coord - i + 1 >= 10*((int)(coord/10))) &&//need + 1 to allow ships along left edge (PB E1E2 for example)
				(this.direction!= 2 || coord + i <= 10+10*((int)(coord/10)))) {//+1 or -1 apparently not needed here, seems to be working fine
			for (int k = 0; k < i; k++) {
				if (BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+k*adjust].isOccupied()) {
					Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			for (int j = 0; j < i; j++) {
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setOccupied(true);
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipNum(this.shipType);
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipDirection(this.direction);//0 if horizontal, 1 if vertical
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipSegmentNum(j);

				Bitmap bmpOriginal = BitmapFactory.decodeResource(getResources(),getPic(shipType,direction,j));
				Bitmap bmpunoc = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
				Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas tempCanvas = new Canvas(bmResult);
				
				if (direction == 1) tempCanvas.rotate(270, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				if (direction == 0) tempCanvas.rotate(180, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				if (direction == 3) tempCanvas.rotate(90, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				
				tempCanvas.drawBitmap(bmpunoc,0,0,null);
				tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
				
				GridView g = (GridView) findViewById(R.id.grid_view);
				LinearLayout nv = (LinearLayout) g.getChildAt(coord+j*adjust);
				ImageView nvv = (ImageView) nv.findViewById(R.id.usquare);
				nvv.setImageBitmap(bmResult);
			}
			//this looks like only the button call is separate in terms of the logic. Unless there's an unaccounted for
			//else condition that for a ship type that matches none of them, much of this logic could be called after
			//the different button possibilities in one place instead of copied+pasted 4 times
			if (this.shipType == 0) {
				mShipBtn = (Button) findViewById(R.id.patrol_boat_button);
				mShipBtn.setEnabled(false);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') Toast.makeText(this, "Patrol Boat placed starting at "+ l + n + "!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(this, "Patrol Boat placed starting at "+ l + "10!", Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 1) {
				mShipBtn = (Button) findViewById(R.id.destroyer_button);
				mShipBtn.setEnabled(false);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') Toast.makeText(this, "Destroyer placed starting at "+ l + n + "!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(this, "Destroyer placed starting at "+ l + "10!", Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 2) {
				mShipBtn = (Button) findViewById(R.id.submarine_button);
				mShipBtn.setEnabled(false);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') Toast.makeText(this, "Submarine placed starting at "+ l + n + "!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(this, "Submarine placed starting at "+ l + "10!", Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 3) {
				mShipBtn = (Button) findViewById(R.id.battleship_button);
				mShipBtn.setEnabled(false);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') Toast.makeText(this, "Battleship placed starting at "+ l + n + "!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(this, "Battleship placed starting at "+ l + "10!", Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 4) {
				mShipBtn = (Button) findViewById(R.id.aircraft_carrier_button);
				mShipBtn.setEnabled(false);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') Toast.makeText(this, "Aircraft Carrier placed starting at "+ l + n + "!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(this, "Aircraft Carrier placed starting at "+ l + "10!", Toast.LENGTH_SHORT).show();
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			}
			this.shipType = -1;
			if (BattleshipFragment.sb.getPlayers()[playerNum].getNumShipsPlaced() == 5) {
				//leave this activity go to next one,
				//since we got all ships placed
				Intent returnIntent = new Intent();
				if (playerNum == 0) setResult(BattleshipFragment.PLAYER_ONE, returnIntent);
				else setResult(BattleshipFragment.PLAYER_TWO, returnIntent);
				finish();
			}
		} else {
			//print some kind of message
			//telling that we couldn't place it there
			Toast.makeText(this, "You cannot place your ship that way!", Toast.LENGTH_SHORT).show();
		}
	}
	
}
