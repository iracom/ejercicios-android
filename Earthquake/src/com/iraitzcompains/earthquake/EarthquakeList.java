package com.iraitzcompains.earthquake;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EarthquakeList extends ListFragment {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

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

		setListAdapter(sca);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		new MyAsincTask(getActivity())
		.execute(getString(R.string.direccion));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		String where = null;
		String whereArgs[] = null;
		String order = null;
		
		Cursor c = cr.query(EarthquakeContentProvider.CONTENT_URI, from, where, whereArgs, order);
		
		while(c.moveToNext()) {
			Log.d("EARTHQUAKE", String.valueOf(c.getLong(c.getColumnIndex(EarthquakeContentProvider.TIME))));
		}
		
		sca.swapCursor(c);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(getActivity(),DetailActivity.class);
		i.putExtra("_id", id);
		startActivity(i);
		
	}

}
