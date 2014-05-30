package com.iraitzcompains.mapas;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;

public class MyLocationUpdateReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Esto sirve para que siga obteniendo las posiciones aun cuando la aplicacion no este ejecutandose.
		//Lo que se podr’a hacer es guardar en una BBDD, para que luego el MainActivity (por ejemplo) acceda a ella y pinte las posiciones.
		String key = LocationManager.KEY_LOCATION_CHANGED;
		Location location = (Location)intent.getExtras().get(key);
		
		
	}

}
