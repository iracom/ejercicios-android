package com.iraitzcompains.internet;

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

import android.app.Activity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	ArrayList<Photo> photos = new ArrayList<Photo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnJson = (Button)findViewById(R.id.btnJson);
		btnJson.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						conectarInternet();
					}
				});
				t.start();
			}
		});
	}
	
	private void conectarInternet() {
		String miUrl = getString(R.string.direccion);
		try {
			URL url = new URL(miUrl);
			
			//Create a new HTTP URL connection
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection)con;
			
			int responseCode = httpCon.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpCon.getInputStream();
				descargarJson(in);
			}
		} catch (MalformedURLException e) {
			Log.d("INTERNET","La url no es v‡lida");
		} catch (IOException e) {
			Log.d("INTERNET",e.getMessage());
		}
	}
	
	private void descargarJson(InputStream in) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		String line;
		try {
			while ((line = bReader.readLine()) != null) {
			    builder.append(line);
			}
		} catch (IOException e1) {
			Log.d("INTERNET",e1.getMessage());
		}

		try {
			JSONObject jSon = new JSONObject(builder.toString());
			JSONArray arrayJson = jSon.getJSONArray("photos");
			for(int i=0; i<arrayJson.length();i++) {
				JSONObject photoJson = arrayJson.getJSONObject(i);
				String id = photoJson.getString("id");
				String name = photoJson.getString("name");
				String image_url = photoJson.getString("image_url");
				Photo photo = new Photo(Integer.parseInt(id), name, image_url);
				photos.add(photo);
			}
			for (Photo miphoto : photos) {
				Log.d("INTERNET",miphoto.toString());
			}
		} catch (JSONException e) {
			Log.d("INTERNET",e.getMessage());
		}
		
	}
	
}
