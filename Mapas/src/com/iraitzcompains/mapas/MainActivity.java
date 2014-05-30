package com.iraitzcompains.mapas;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.Fragment.SavedState;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView txtLat, txtLon, txtAlt;
	Button btnRefresh;
	LocationManager locationManager;
	HashMap<Marker, Long> myHash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.map_layout);

		String serviceString = Context.LOCATION_SERVICE;

//		locationManager = (LocationManager) getSystemService(serviceString);
//
//		txtLat = (TextView) findViewById(R.id.txtLatitud);
//		txtLon = (TextView) findViewById(R.id.txtLongitud);
//		txtAlt = (TextView) findViewById(R.id.txtAltura);
//
//		btnRefresh = (Button) findViewById(R.id.btnRefresh);
//
//		btnRefresh.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//			}
//		});

//		obtenerLocalizacion();
		
		//Instanciar el mapa
		FragmentManager fragmentManager = getFragmentManager();
		GoogleMap map = ((MapFragment)fragmentManager.findFragmentById(R.id.map)).getMap();
		
		MarkerOptions markerOpt1 = new MarkerOptions().position(new LatLng(40.418889, -3.691944)).title("Madrid");
		MarkerOptions markerOpt2 = new MarkerOptions().position(new LatLng(43.218334, -2.019888)).title("Andoain");
		
		Marker marker1 = map.addMarker(markerOpt1);
		Marker marker2 = map.addMarker(markerOpt2);
		
		myHash = new HashMap<Marker, Long>();
		myHash.put(marker1, (long)1);
		myHash.put(marker2, (long)2);
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Intent intent = new Intent(MainActivity.this,DetailActivity.class);
				long id = myHash.get(marker);
				intent.putExtra("nombre", id);
				startActivity(intent);
				return true;
			}
		});
	}

	private void obtenerLocalizacion() {

		// Ultima localizaci—n conocida
		// Criteria criteria = new Criteria();
		// criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// criteria.setAltitudeRequired(false);
		// criteria.setSpeedRequired(false);
		//
		// String bestProvider = locationManager.getBestProvider(criteria,
		// true);
		//
		// LocationProvider provider =
		// locationManager.getProvider(bestProvider);
		//
		// Location location =
		// locationManager.getLastKnownLocation(bestProvider);
		//
		// if (location != null) {
		// txtLat.setText(String.valueOf(location.getLatitude()));
		// txtLon.setText(String.valueOf(location.getLongitude()));
		// txtAlt.setText(String.valueOf(location.getAltitude()));
		// }

		// Obtiene localizaci—n actual
		String provider = LocationManager.GPS_PROVIDER;
		int t = 5000; // milliseconds
		int distance = 5; // meters

		LocationListener locListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					txtLat.setText(String.valueOf(location.getLatitude()));
					txtLon.setText(String.valueOf(location.getLongitude()));
					txtAlt.setText(String.valueOf(location.getAltitude()));
				} else {
					Log.d("GEO", "onLocationChanged: Location is null");
				}
			}
		};

		locationManager.requestLocationUpdates(provider, t, distance,
				locListener);

		//Esto lo utilizamos si queremos que se vayan guardando las posiciones una vez nos hayamos salido de la aplicacion
//		String provider = LocationManager.GPS_PROVIDER;
//		int t = 5000;
//		int distance = 5;
//		
//		final int locationUpdateRC = 0;
//		int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//		
//		Intent intent = new Intent(MainActivity.this, MyLocationUpdateReceiver.class);
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, locationUpdateRC, intent, flags);
//		
//		locationManager.requestLocationUpdates(provider, t, distance, pendingIntent);
	}
}
