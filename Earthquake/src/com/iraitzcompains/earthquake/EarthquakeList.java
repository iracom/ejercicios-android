package com.iraitzcompains.earthquake;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EarthquakeList extends ListFragment implements
		MyAsincTask.IMyAsyncTask {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	public ArrayAdapter<Earthquake> aa;
	public ArrayList<Earthquake> earthquakesList;

	public EarthquakeDB eqdb;

	SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		earthquakesList = new ArrayList<Earthquake>();

		aa = new ArrayAdapter<Earthquake>(inflater.getContext(),
				android.R.layout.simple_list_item_1, earthquakesList);

		setListAdapter(aa);

		eqdb = new EarthquakeDB(inflater.getContext());

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			earthquakesList.addAll((ArrayList<Earthquake>) savedInstanceState
					.getSerializable(ITEMS_ARRAY));
			aa.notifyDataSetChanged();
		} else {
			// mostrarLista(0);
			// obtenerInformacion();
			new MyAsincTask(getActivity(),this)
					.execute(getString(R.string.direccion));
		}
	}

	private void mostrarLista(double magnitud) {
		mostrarTerremotos(magnitud);
	}

	private void mostrarTerremotos(double magnitud) {
		earthquakesList.clear();
		earthquakesList.addAll(eqdb.getEarthquakes(magnitud));
		aa.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(ITEMS_ARRAY, earthquakesList);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void showEarthquakes() {
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String strMag = sp.getString(getString(R.string.keyMagnitud), "0");
		int mag = Integer.parseInt(strMag);
		mostrarLista(mag);
	}

	@Override
	public void addEarthquakesToScreen(ArrayList<Earthquake> earthquakes) {
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String strMag = sp.getString(getString(R.string.keyMagnitud), "0");
		int mag = Integer.parseInt(strMag);
		Log.d("EARTHQUAKE","Terremotos " + earthquakes.toString());
		addEarthquakesToList(mag, earthquakes);
	}

	private void addEarthquakesToList(double magnitud,
			ArrayList<Earthquake> earthquakes) {
		for (Earthquake earthquake : earthquakes) {
			if(earthquake.getMagnitude() >= magnitud)
				earthquakesList.add(0, earthquake);
		}
		aa.notifyDataSetChanged();
	}

}
