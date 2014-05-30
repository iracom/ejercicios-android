package com.iraitzcompains.earthquake;

import java.util.HashMap;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EarthquakeMap extends MapFragment implements LoaderCallbacks<Cursor> {

	private HashMap<Marker, Long> eqHash;
	public final static int ID_EARTHQUAKES_LOADER = 1;
	
	private GoogleMap map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		eqHash = new HashMap<Marker, Long>();
		
		map = this.getMap();
		
		getLoaderManager().initLoader(ID_EARTHQUAKES_LOADER, null, this);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String minMag = sp.getString(getString(R.string.keyMagnitud), "0");
		
		String where = EarthquakeContentProvider.MAGNITUDE + " >= ? ";
		String whereArgs[] = {minMag};
		String order = EarthquakeContentProvider.TIME + " DESC";
		
		CursorLoader loader = new CursorLoader(getActivity(), EarthquakeContentProvider.CONTENT_URI, EarthquakeContentProvider.projection, where, whereArgs, order);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		int cnt = c.getCount();
		if(cnt > 0){
			
			if(map == null) {
				map = this.getMap();
			}
			
			int indId = c
					.getColumnIndexOrThrow(EarthquakeContentProvider._ID);
			int indLat = c.getColumnIndexOrThrow(EarthquakeContentProvider.LAT);
			int indLon = c.getColumnIndexOrThrow(EarthquakeContentProvider.LONG);
			int indPlace = c.getColumnIndexOrThrow(EarthquakeContentProvider.PLACE);
			while(c.moveToNext()) {
				double lat = c.getDouble(indLat);
				double lon = c.getDouble(indLon);
				MarkerOptions mOptions = new MarkerOptions().position(new LatLng(lat, lon)).title(c.getString(indPlace));
				Marker marker = map.addMarker(mOptions);
				long id = c.getLong(indId);
				eqHash.put(marker, id);
			}
			
			map.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					Intent intent = new Intent(getActivity(),DetailActivity.class);
					long id = eqHash.get(marker);
					intent.putExtra(EarthquakeContentProvider._ID, id);
					startActivity(intent);
					return true;
				}
			});
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
