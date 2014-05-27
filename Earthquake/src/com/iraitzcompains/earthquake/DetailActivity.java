package com.iraitzcompains.earthquake;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		txtMag = (TextView)findViewById(R.id.txtDetailMagnitude);
		txtPlace = (TextView)findViewById(R.id.txtDetailPlace);
		txtTime = (TextView)findViewById(R.id.txtDetailTime);
		
		
		if (savedInstanceState == null) {
			Intent intent = getIntent();
			if (intent != null) {
				_id = intent.getLongExtra("_id", 0);
			}
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String minMag = sp.getString(getString(R.string.keyMagnitud), "0");
		
		String where = EarthquakeContentProvider.MAGNITUDE + " >= ? ";
		String whereArgs[] = {minMag};
		String order = EarthquakeContentProvider.TIME + " DESC";
		
		Uri rowAdress = ContentUris.withAppendedId(EarthquakeContentProvider.CONTENT_URI, _id);
		
		CursorLoader loader = new CursorLoader(this, rowAdress, EarthquakeContentProvider.projection, where, whereArgs, order);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
