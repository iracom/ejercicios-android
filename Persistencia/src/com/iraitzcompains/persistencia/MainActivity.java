package com.iraitzcompains.persistencia;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView datoInterval, datoRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
			Intent settingsActivity = new Intent(this,SettingsActivity.class);
			startActivity(settingsActivity);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		datoRefresh = (TextView)findViewById(R.id.txtOnOff);
		datoInterval = (TextView)findViewById(R.id.txtIntervalData);
		
		cargarPreferencias();
	}
	
	private void cargarPreferencias() {
		
		SharedPreferences misPreferencias = getSharedPreferences(SettingsActivity.MY_PREFS, MODE_PRIVATE);
		
		boolean isRefresh = misPreferencias.getBoolean(SettingsActivity.MY_REFRESH, false);
		String strInterval = misPreferencias.getString(SettingsActivity.MY_INTERVAL, datoInterval.getText().toString());
		
		if(isRefresh) {
			datoRefresh.setText("On");
		} else {
			datoRefresh.setText("Off");
		}
		
		datoInterval.setText(strInterval);
	}

}
