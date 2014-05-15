package com.iraitzcompains.ejemplointent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// LLamada recivida
		incomingCall(arg0, arg1);
		// SMS recivido
		incomingSMS(arg0, arg1);
		// Cambio de/a modo abion
		airplaneModeChange(arg0, arg1);
		//Cambio de conexion
		connectivityChange(arg0, arg1);
	}

	private void incomingCall(Context context, Intent intent) {
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

		if (state != null) {

			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG)
						.show();
				Log.d("BROADCAST", "Phone Is Ringing");
			}

			if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				Toast.makeText(context, "Call Recieved", Toast.LENGTH_LONG)
						.show();
				Log.d("BROADCAST", "Call Recieved");
			}

			if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
				Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG)
						.show();
				Log.d("BROADCAST", "Phone Is Idle");
			}
		}
	}

	private void incomingSMS(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			if (pdus != null) {
				SmsMessage messages = SmsMessage
						.createFromPdu((byte[]) pdus[0]);
				Log.d("BROADCAST", messages.getMessageBody());
				Toast.makeText(context,
						"SMS Received : " + messages.getMessageBody(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void airplaneModeChange(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null) {
			if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
				Log.d("BROADCAST", "Modo avion cambiado");
				if(Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1) {
					Log.d("BROADCAST","Modo avion activado");
				}
				else {
					Log.d("BROADCAST","Modo avion desactivado");
				}
			}
		}
	}
	
	private void connectivityChange(Context context, Intent intent) {
		/*Log.d("BROADCAST", " " + ConnectivityManager.EXTRA_NO_CONNECTIVITY);
		boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
        //Log.d("BROADCAST","noConnectivity == " + noConnectivity);
        if(noConnectivity) {
        	Log.d("BROADCAST", "No hay conexion " + reason);
        }*/
		
		//Arkaitz
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            Log.d("RECEIVER", "Network available");
        } else {
        	Log.d("RECEIVER", "Network NOT available");
        }
    
	}

}
