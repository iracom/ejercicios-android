package com.iraitzcompains.earthquake;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity{
	
	private static final int SHOW_PREFERENCES = 0;
	
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		if(savedInstanceState == null) {
//			//Se a–ade un action bar para poder seleccionar entre lista de terremotos y mapa.
//			ActionBar abar = getActionBar();
//			
//			abar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//			
//			ActionBar.Tab tabList = abar.newTab().setText("Terremotos");
//			ActionBar.Tab tabMap = abar.newTab().setText("Mapa");
//			
//			Fragment listFragment = new EarthquakeList();
//			Fragment mapFragment = new EarthquakeMap();
//			
//			tabList.setTabListener(new MyTabListener(listFragment));
//			tabMap.setTabListener(new MyTabListener(mapFragment));
//			
//			abar.addTab(tabList);
//			abar.addTab(tabMap);
			
			fragmentTransaction.add(R.id.mi_lista, new EarthquakeList(), "Earthquakes");
			
			fragmentTransaction.commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivityForResult(i, SHOW_PREFERENCES);
			
			return true;
		} else if(id == R.id.action_refresh) {
			((EarthquakeList)getFragmentManager().findFragmentByTag("Earthquakes")).refreshEarthQuakes();
		} else if(id == R.id.action_stop_service) {
			((EarthquakeList)getFragmentManager().findFragmentByTag("Earthquakes")).stopService();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		((EarthquakeList)getFragmentManager().findFragmentByTag("Earthquakes")).stopService();
		super.onDestroy();
	}

}
