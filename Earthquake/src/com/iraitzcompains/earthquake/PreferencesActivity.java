package com.iraitzcompains.earthquake;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.util.Log;
import android.preference.PreferenceManager;

public class PreferencesActivity extends Activity implements OnSharedPreferenceChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_preferences);
		
		getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new EarthquakePreferenceFragment())
        .commit();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs,
			String key) {
		if ( key.equals(getString(R.string.keyRefresh)))
			Log.d("PERSISTENCE","Se ha modificado " + key + " -> " + prefs.getBoolean(key, false));
			//boolean isAutorefresh =  
		else
			Log.d("PERSISTENCE","Se ha modificado " + key + " -> " + prefs.getString(key, "defecto"));
		
	}

}
