package com.iraitzcompains.earthquake;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
		if ( key.equals(getString(R.string.keyRefresh))) {
			//Log.d("PERSISTENCE","Se ha modificado " + key + " -> " + prefs.getBoolean(key, false));
			boolean isAutorefresh = prefs.getBoolean(key, false);
			if(isAutorefresh) {
//				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//				String interval = sp.getString(getString(R.string.keyInterval), "30");
				
				//Creamos una alarma para que se ejecute cada un intervalo de tiempo
				AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				//Tipo de ejecucion de la alarma
				int alarmType = AlarmManager.RTC;
				
				//Tiempo de intervalo en milisegundos
				//long intervalo = Integer.parseInt(interval) * 60 * 1000;
				long intervalo = 2000;
				
				//Crear un Intent que broadcasteara la accion
				String ALARM_ACTION = "ALARM_ACTION";
				Intent intentToFire = new Intent(ALARM_ACTION);
				PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intentToFire, 0);
				
				//Ejecutar la alarma
				alarm.setInexactRepeating(alarmType, 0, intervalo, alarmIntent);
				
			} else {
				Intent intent = new Intent(this, EarthquakeUpdateService.class);
				stopService(intent);
			}
		}
		else
			Log.d("PERSISTENCE","Se ha modificado " + key + " -> " + prefs.getString(key, "defecto"));
		
	}

}
