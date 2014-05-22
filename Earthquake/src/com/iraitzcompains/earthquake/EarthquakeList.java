package com.iraitzcompains.earthquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class EarthquakeList extends ListFragment {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private ArrayAdapter<Earthquake> aa;
	private ArrayList<Earthquake> earthquakesList;

	EarthquakeDB eqdb;
	Button btnJson;
	
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
			earthquakesList.addAll((ArrayList<Earthquake>)savedInstanceState
					.getSerializable(ITEMS_ARRAY));
			aa.notifyDataSetChanged();
		} else {
			mostrarLista(0);
			obtenerInformacion();
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

	private void obtenerInformacion() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				descargarJson();
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void descargarJson() {

		InputStream in = connectToInternet();
		if (in != null) {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					in));
			StringBuilder builder = new StringBuilder();
			String line;
			try {
				while ((line = bReader.readLine()) != null) {
					builder.append(line);
				}
			} catch (IOException e1) {
				Log.d("EARTHQUAKE", e1.getMessage());
			}

			try {
				JSONObject jSon = new JSONObject(builder.toString());
				JSONArray featuresJson = jSon.getJSONArray("features");
				for (int i = 0; i < featuresJson.length(); i++) {
					JSONObject featureJson = featuresJson.getJSONObject(i);

					JSONObject propertiesJson = featureJson
							.getJSONObject("properties");
					double mag = propertiesJson.getDouble("mag");
					String place = propertiesJson.getString("place");
					long time = propertiesJson.getLong("time");
					String url = propertiesJson.getString("url");
					String detail = propertiesJson.getString("detail");

					JSONObject geometryJson = featureJson
							.getJSONObject("geometry");
					JSONArray coordsJson = geometryJson
							.getJSONArray("coordinates");
					double lon = coordsJson.getDouble(0);
					double lat = coordsJson.getDouble(1);

					String strId = featureJson.getString("id");

					Earthquake eq = new Earthquake(strId, place, time, detail,
							mag, lat, lon, url);

					insertarTerremoto(eq);
				}

			} catch (JSONException e) {
				Log.d("EARTHQUAKE", e.getMessage());
			}
		}
	}

	private void insertarTerremoto(Earthquake eq) {
		eqdb.insert(eq);
	}

	private InputStream connectToInternet() {
		InputStream in;
		String miUrl = getString(R.string.direccion);
		try {
			URL url = new URL(miUrl);

			// Create a new HTTP URL connection
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) con;

			int responseCode = httpCon.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpCon.getInputStream();
				return in;
			}
		} catch (MalformedURLException e) {
			Log.d("EARTHQUAKE", "La url no es v�lida");
		} catch (IOException e) {
			Log.d("EARTHQUAKE", e.getMessage());
		}
		return null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(ITEMS_ARRAY, earthquakesList);
		super.onSaveInstanceState(outState);
	}

	private void actualizarTerremoto() {
		Earthquake eq = new Earthquake(1, "1", "Inventado",
				new Date().getTime(), "Detalle", 1.7, 0.5, 10.4, "www.url.es");
		String[] campos = { EarthquakeDBOpenHelper.PLACE,
				EarthquakeDBOpenHelper.DETAIL };
		String[] datos = { "Updateado", "Updateado" };
		String where = EarthquakeDBOpenHelper._ID + " = ?";
		String[] whereArgs = { String.valueOf(eq.get_id()) };
		eqdb.update(eq, campos, datos, where, whereArgs);
	}

	private void borrarTerremoto() {
		Earthquake eq = new Earthquake(3, "3", "Inventado3",
				new Date().getTime(), "Detalle3", 1.7, 0.5, 10.4, "www.url.es");
		eqdb.detele(eq);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		//Autorefresh
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		boolean isRefresh = sp.getBoolean(getString(R.string.keyRefresh), false);
		if(isRefresh) {
			Log.d("EARTHQUAKE", "Se actualiza automaticamente");
		}
		
		//Magnitud
		String strMag = sp.getString(getString(R.string.keyMagnitud), "0");
		int mag = Integer.parseInt(strMag);
		mostrarLista(mag);
	}
	
}
