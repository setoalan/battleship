package com.hasbrobeta.battleship;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class BattleshipActivity extends Activity {

	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battleship);
		
		mGridView = (GridView) findViewById(R.id.gridView);
		
		mGridView.setAdapter(new BattleshipAdapter(this));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
				Toast.makeText(BattleshipActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});
		
	}

}
