package com.iraitzcompains.persistencia;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.os.Build;

public class SettingsActivity extends Activity {

	public static final String MY_PREFS = "MY_PREFS";
	public static final String MY_REFRESH = "autorefresh";
	public static final String MY_INTERVAL = "interval";
	public static final String MY_IND = "ind";
	
	Spinner interval;
	Switch refresh;
	ArrayAdapter<CharSequence> adapter;
	SharedPreferences misPreferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		refresh = (Switch)findViewById(R.id.switchRefresh);
		interval = (Spinner)findViewById(R.id.spinnerInterval);
		
		adapter = ArrayAdapter.createFromResource(this, R.array.intervalos, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		interval.setAdapter(adapter);
		
		misPreferencias = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		cargarPreferencias();
	}
	
	private void cargarPreferencias() {
		
		boolean isRefresh = misPreferencias.getBoolean(MY_REFRESH, false);
		int ind = misPreferencias.getInt(MY_IND, 0);
		
		refresh.setChecked(isRefresh);
		
		interval.setSelection(ind);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		SharedPreferences.Editor editor = misPreferencias.edit();
		
		editor.putBoolean(MY_REFRESH, refresh.isChecked());
		editor.putString(MY_INTERVAL, String.valueOf(interval.getSelectedItem()));
		editor.putInt(MY_IND, interval.getSelectedItemPosition());
		
		editor.apply();
	}
}
