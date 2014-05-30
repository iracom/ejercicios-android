package com.iraitzcompains.earthquake;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends Activity implements LoaderCallbacks<Cursor>{

	private long _id;
	public final static int ID_EARTHQUAKE_LOADER = 2;
	
	TextView txtMag, txtPlace, txtTime;
	MapFragment mapFragment;
	GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		txtMag = (TextView)findViewById(R.id.txtDetailMagnitude);
		txtPlace = (TextView)findViewById(R.id.txtDetailPlace);
		txtTime = (TextView)findViewById(R.id.txtDetailTime);
		
		mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapDetalle);
		
		map = mapFragment.getMap();
		
		if (savedInstanceState == null) {
			Intent intent = getIntent();
			if (intent != null) {
				_id = intent.getLongExtra(EarthquakeContentProvider._ID, 0);
				getLoaderManager().initLoader(ID_EARTHQUAKE_LOADER, null, this);
			}
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		String where = null;
		String whereArgs[] = null;
		String order = null;
		
		Uri rowAdress = ContentUris.withAppendedId(EarthquakeContentProvider.CONTENT_URI, _id);
		
		CursorLoader loader = new CursorLoader(this, rowAdress, EarthquakeContentProvider.projection, where, whereArgs, order);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		if(c.moveToFirst()) {
			int indMagnitude = c.getColumnIndex(EarthquakeContentProvider.MAGNITUDE);
			int indPlace = c.getColumnIndex(EarthquakeContentProvider.PLACE);
			int indTime = c.getColumnIndex(EarthquakeContentProvider.TIME);
			
			int indLat = c.getColumnIndexOrThrow(EarthquakeContentProvider.LAT);
			int indLon = c.getColumnIndexOrThrow(EarthquakeContentProvider.LONG);
			
			txtMag.setText(String.valueOf(c.getDouble(indMagnitude)));
			txtPlace.setText(c.getString(indPlace));
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
			txtTime.setText(sdf.format(c.getLong(indTime)));
			
			if (map == null) {
				map = mapFragment.getMap();
			}
			
			double lat = c.getDouble(indLat);
			double lon = c.getDouble(indLon);
			MarkerOptions mOptions = new MarkerOptions().position(new LatLng(lat, lon)).title(c.getString(indPlace));
			map.addMarker(mOptions);
			
			CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
	        map.moveCamera(center);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
