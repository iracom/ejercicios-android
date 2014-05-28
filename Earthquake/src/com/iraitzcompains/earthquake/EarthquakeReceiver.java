package com.iraitzcompains.earthquake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EarthquakeReceiver extends BroadcastReceiver {
	public EarthquakeReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d("EARTHQUAKE","EarthquakeReceiver.onReceive");
		
		Intent myIntent = new Intent(context, EarthquakeUpdateService.class);
		myIntent.putExtra("url", context.getResources().getString(R.string.direccion));
		context.startService(myIntent);
		
		//throw new UnsupportedOperationException("Not yet implemented");
	}
}
