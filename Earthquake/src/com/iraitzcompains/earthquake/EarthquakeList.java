package com.iraitzcompains.earthquake;

import java.util.ArrayList;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EarthquakeList extends ListFragment implements LoaderCallbacks<Cursor>{

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";
	public final static int ID_EARTHQUAKES_LOADER = 1;

	public ArrayList<Earthquake> earthquakesList;
	public SimpleCursorAdapter sca;
	
	private ContentResolver cr;

	SharedPreferences sp;
	
	private String from[] = {
			EarthquakeContentProvider.MAGNITUDE,
			EarthquakeContentProvider.PLACE,
			EarthquakeContentProvider.TIME,
			EarthquakeContentProvider._ID
	};
	
	private int to[] = {
			R.id.txtMagnitude,
			R.id.txtPlace,
			R.id.txtTime
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		cr = getActivity().getContentResolver();
		
		sca = new SimpleCursorAdapter(inflater.getContext(), R.layout.list_row, null, from, to, 0);
		sca.setViewBinder(new EarthQuakeViewBinder());

		//setListAdapter(sca);
		getLoaderManager().initLoader(ID_EARTHQUAKES_LOADER, null, this);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		new MyAsincTask(getActivity())
		.execute(getString(R.string.direccion));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(getActivity(),DetailActivity.class);
		i.putExtra(EarthquakeContentProvider._ID, id);
		startActivity(i);
		
	}
	
	@Override
	public void onResume() {
		getLoaderManager().restartLoader(ID_EARTHQUAKES_LOADER, null, this);
		super.onResume();
	}
	
	public void refreshEarthQuakes() {
		Intent intent = new Intent(getActivity(), EarthquakeUpdateService.class);
		intent.putExtra("url", getResources().getString(R.string.direccion));
		getActivity().startService(intent);
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
		sca.swapCursor(c);
		
		setListAdapter(sca);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		sca.swapCursor(null);
		
		setListAdapter(sca);
	}

	public void stopService() {
		Intent intent = new Intent (getActivity(),EarthquakeUpdateService.class);
		getActivity().stopService(intent);
	}

}
