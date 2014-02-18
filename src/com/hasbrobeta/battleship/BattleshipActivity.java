package com.hasbrobeta.battleship;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BattleshipActivity extends Activity {

	private String[] mNavigation;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battleship);
		
		mNavigation = getResources().getStringArray(R.array.navigation_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mNavigation));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Toast.makeText(BattleshipActivity.this, position + "", Toast.LENGTH_SHORT).show();
		}
		
	}

}
