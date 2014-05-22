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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MyAsincTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

	private EarthquakeDB eqdb;
	private Context context;
	
	public interface IMyAsyncTask {
		public void showEarthquakes();
		public void addEarthquakes(ArrayList<Earthquake> earthquakes);
	}
	
	private IMyAsyncTask eqList;
	
	public MyAsincTask(Context context, IMyAsyncTask fragmento) {
		this.context = context;
		eqList = (IMyAsyncTask)fragmento;
		
		eqdb = new EarthquakeDB(context);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		eqList.showEarthquakes();
	}
	
	@Override
	protected ArrayList<Earthquake> doInBackground(String... params) {
		String url = params[0];
		descargarJson(url);
		return null;
	}
	
	private ArrayList<Earthquake> descargarJson(String strUrl) {
		ArrayList<Earthquake> terremotos = new ArrayList<Earthquake>();
		
		InputStream in = connectToInternet(strUrl);
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

					eqdb.insert(eq);
					terremotos.add(eq);
				}

			} catch (JSONException e) {
				Log.d("EARTHQUAKE", e.getMessage());
			}
			return terremotos;
		} else {
			return null;
		}
	}

	private InputStream connectToInternet(String strUrl) {
		InputStream in;
		//String miUrl = this.context.getString(R.string.direccion);
		try {
			URL url = new URL(strUrl);

			// Create a new HTTP URL connection
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) con;

			int responseCode = httpCon.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpCon.getInputStream();
				return in;
			}
		} catch (MalformedURLException e) {
			Log.d("EARTHQUAKE", "La url no es v‡lida");
		} catch (IOException e) {
			Log.d("EARTHQUAKE", e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Earthquake> result) {
		super.onPostExecute(result);
		eqList.addEarthquakes(result);
	}

}
