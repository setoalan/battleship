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

//public class PlacementActivity extends ASKActivity {
public class PlacementActivity extends Activity {

	private GridView mGridView;
	
	public int lcoord = 0;
	public int ncoord = 0;
	public int direction = 0;
	public int shipType = -1;
	
	private int playerNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placement);

		playerNum = getIntent().getIntExtra("PLAYER_NUM", 0);
		
		mGridView = (GridView) findViewById(R.id.grid_view);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
				int num = 1+position%10;
				int le = position/10;
				char l = (char) ('A' + le);
				//setText(""+n,""+l);
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText(""+num);
		        ncoord = num - 1;
		        tv = (TextView) findViewById(R.id.textView1);
				tv.setText(""+l);
		        lcoord = le;
			}
		}
		);
	}
	
	public void setText(String n, String l)
	{
		TextView tv = (TextView) findViewById(R.id.textView3);
		tv.setText(n);
        tv = (TextView) findViewById(R.id.textView1);
		tv.setText(l);
	}
	
	public void ship2(View view) {
		this.shipType = 0;
	}
	
	public void ship3d(View view) {
		this.shipType = 1;
	}
	
	public void ship3s(View view) {
		this.shipType = 2;
	}
	
	public void ship4(View view) {
		this.shipType = 3;
	}
	
	public void ship5(View view) {
		this.shipType = 4;
	}
	
	public void directionL(View view) {
		TextView tv = (TextView) findViewById(R.id.textView4);
		tv.setText("Left");
        this.direction = 0;
	}

	public void directionR(View view) {
		TextView tv = (TextView) findViewById(R.id.textView4);
		tv.setText("Right");
        this.direction = 2;
	}

	public void directionU(View view) {
		TextView tv = (TextView) findViewById(R.id.textView4);
		tv.setText("Up");
        this.direction = 1;
	}

	public void directionD(View view) {
		TextView tv = (TextView) findViewById(R.id.textView4);
		tv.setText("Down");
        this.direction = 3;
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
	
	public void placeShip(View v) {
		if (shipType == -1) {
			TextView tv = (TextView) findViewById(R.id.textView5);
	        tv.setText("Please select a ship to Place!");	
	        return;
		}
		int coord = ncoord + 10*lcoord;
		int adjust = 0;
		if (this.direction == 0)
			adjust = -1;//left
		else if (this.direction == 1)
			adjust = -10;//up
		else if (this.direction == 2)
			adjust = 1;//right
		else if (this.direction == 3)
			adjust = 10;//down
		int i = 0;
		if (this.shipType == 0)
			i = 2;
		else if (this.shipType == 1 || this.shipType == 2)
			i = 3;
		else if (this.shipType == 3)
			i = 4;
		else if (this.shipType == 4)
			i = 5;

		if (BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord].isOccupied() == false &&
				coord+(i-1)*adjust <= 99 &&
				coord+(i-1)*adjust >= 0 &&
				(this.direction!= 0 || coord - i + 1 >= 10*((int)(coord/10))) &&//need + 1 to allow ships along left edge (PB E1E2 for example)
				(this.direction!= 2 || coord + i <= 10+10*((int)(coord/10)))) {//+1 or -1 apparently not needed here, seems to be working fine
			for (int k = 0; k < i; k++) {
				if (BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+k*adjust].isOccupied()) {
					TextView tv = (TextView) findViewById(R.id.textView5);
					tv.setText("Your cannot place your ship that way!");
					return;
				}
			}
			for (int j = 0; j < i; j++) {
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setOccupied(true);
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipNum(this.shipType);
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipDirection(this.direction%2);//0 if horizontal, 1 if vertical
				BattleshipFragment.sb.getPlayers()[playerNum].getSquares()[coord+j*adjust].setShipSegmentNum(j);

				Bitmap bmpOriginal = BitmapFactory.decodeResource(getResources(),getPic(shipType,direction,j));
				Bitmap bmpunoc = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
				Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas tempCanvas = new Canvas(bmResult);
				if (direction == 1)
				{
					tempCanvas.rotate(270, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				}
				if (direction == 0)
				{
					tempCanvas.rotate(180, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				}
				if (direction == 3)
				{
					tempCanvas.rotate(90, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
				}
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
				Button button = (Button) findViewById(R.id.pb2);
		        button.setEnabled(false);
		        TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Patrol Boat placed starting at "+ l + n + "!");
				else tv.setText("Patrol Boat placed starting at "+ l + "10!");
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 1) {
				Button button = (Button) findViewById(R.id.d3);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Destroyer placed starting at "+ l + n + "!");
				else tv.setText("Destroyer placed starting at "+ l + "10!");
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 2) {
				Button button = (Button) findViewById(R.id.s3);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Submarine placed starting at "+ l + n + "!");
				else tv.setText("Submarine placed starting at "+ l + "10!");
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 3) {
				Button button = (Button) findViewById(R.id.b4);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Battleship placed starting at "+ l + n + "!");
				else tv.setText("Battleship placed starting at "+ l + "10!");
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			} else if (this.shipType == 4) {
				Button button = (Button) findViewById(R.id.ac5);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Aircraft Carrier placed starting at "+ l + n + "!");
				else tv.setText("Aircraft Carrier placed starting at "+ l + "10!");
				BattleshipFragment.sb.getPlayers()[playerNum].addNumShipsPlaced();
			}
			this.shipType = -1;
			if (BattleshipFragment.sb.getPlayers()[playerNum].getNumShipsPlaced() == 5) {
				//leave this activity go to next one,
				//since we got all ships placed
				Intent returnIntent = new Intent();
				if (playerNum == 0)
					setResult(BattleshipFragment.PLAYER_ONE, returnIntent);
				else
					setResult(BattleshipFragment.PLAYER_TWO, returnIntent);
				finish();
			}
		} else {
			//print some kind of message
			//telling that we couldn't place it there
			TextView tv = (TextView) findViewById(R.id.textView5);
			tv.setText("Your cannot place your ship that way!");
		}
	}
	
	
}
