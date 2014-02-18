package com.hasbrobeta.battleship;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//public class PlacementActivity extends ASKActivity {
public class PlacementActivity extends Activity {

	private String[] mNavigation;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private GridView mGridView;
	
	public class Square {
		boolean isOccupied;
		int shipNum;
		int shipSegmentNum;
		int shipDirection;
		boolean isShot;
		public Square() { isOccupied = false; shipNum = -1; shipSegmentNum = -1; shipDirection = -1; isShot = false;}
	}
	public class Board {
		public int numShipsPlaced = 0;
		public Square[] squares;// = new Square[100];
		public Board() {
			this.squares = new Square[100]; 
			for (int i = 0; i < 100; i++) 
				this.squares[i] = new Square();
			}
	}
	
	public int lcoord = 0;
	public int ncoord = 0;
	public int direction = 0;
	public int shipType = -1;
	public Board board;// = Board();// = new Board();
	
	/*public static Board getBoard() {
		return board;
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placement);
		
		mNavigation = getResources().getStringArray(R.array.navigation_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mGridView = (GridView) findViewById(R.id.grid_view);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mNavigation));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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
		
		board = new Board();
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Toast.makeText(PlacementActivity.this, position + "", Toast.LENGTH_SHORT).show();
//			int num = position%10;
//			char n = (char) num;
//			int le = position/10;
//			char l = (char) ('a' + le);
//			//setText(""+n,""+l);
//			TextView tv = (TextView) findViewById(R.id.textView3);
//			tv.setText(n);
//	        ncoord = num;
//	        tv = (TextView) findViewById(R.id.textView1);
//			tv.setText(l);
//	        lcoord = le;
		}
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
		
		if (this.board.squares[coord].isOccupied == false &&
				coord+(i-1)*adjust < 99 &&
				coord+(i-1)*adjust > 0 &&
				(this.direction!= 0 || coord - i >= 10*((int)(coord/10))) &&
				(this.direction!= 2 || coord + i <= 10+10*((int)(coord/10)))) {	
			for (int k = 0; k < i; k++) {
				if (this.board.squares[coord+k*adjust].isOccupied == true) {
					TextView tv = (TextView) findViewById(R.id.textView5);
					tv.setText("Your cannot place your ship that way!");
				}
			}
			for (int j = 0; j < i; j++) {
				this.board.squares[coord+j*adjust].isOccupied = true;
				this.board.squares[coord+j*adjust].shipNum = this.shipType;
				this.board.squares[coord+j*adjust].shipDirection = this.direction%2;//0 if horizontal, 1 if vertical
				this.board.squares[coord+j*adjust].shipSegmentNum = j;
				ImageView shipbit = new ImageView(this);//(ImageView) findViewById(R.layout.fragment_battleship);
				Drawable drawable = getResources().getDrawable(R.drawable.yellow);
				shipbit.setImageDrawable(drawable);
				GridView g = (GridView) findViewById(R.id.grid_view);
				LinearLayout nv = (LinearLayout) g.getChildAt(coord+j*adjust);
				ImageView nvv = (ImageView) nv.findViewById(R.id.usquare);
				nvv.setImageDrawable(drawable);
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
			} else if (this.shipType == 1) {
				Button button = (Button) findViewById(R.id.d3);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Patrol Boat placed starting at "+ l + n + "!");
				else tv.setText("Patrol Boat placed starting at "+ l + "10!");
			} else if (this.shipType == 2) {
				Button button = (Button) findViewById(R.id.s3);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Patrol Boat placed starting at "+ l + n + "!");
				else tv.setText("Patrol Boat placed starting at "+ l + "10!");
			} else if (this.shipType == 3) {
				Button button = (Button) findViewById(R.id.b4);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Patrol Boat placed starting at "+ l + n + "!");
				else tv.setText("Patrol Boat placed starting at "+ l + "10!");
			} else if (this.shipType == 4) {
				Button button = (Button) findViewById(R.id.ac5);
			    button.setEnabled(false);
			    TextView tv = (TextView) findViewById(R.id.textView5);
				char l = 'A'; for (int x = 0; x < lcoord; x++) l++;
				char n = '1'; for (int x = 0; x < ncoord; x++) n++;
				if (n != ':') tv.setText("Patrol Boat placed starting at "+ l + n + "!");
				else tv.setText("Patrol Boat placed starting at "+ l + "10!");
			}
			this.shipType = -1;
			if (this.board.numShipsPlaced == 5) {
				//leave this activity go to next one,
				//since we got all ships placed
			}
		} else {
			//print some kind of message
			//telling that we couldn't place it there
			TextView tv = (TextView) findViewById(R.id.textView5);
			tv.setText("Your cannot place your ship that way!");
		}
	}
	
	
}
