package com.iraitzcompains.internet;

import java.io.BufferedReader;
import java.io.File;
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
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	ArrayList<Photo> photos = new ArrayList<Photo>();
	long referenceRequest;
	TextView nombreArchivo, tamArchivo;
	DownloadManager downloadManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnJson = (Button) findViewById(R.id.btnJson);
		Button btnFichero = (Button)findViewById(R.id.btnDescarga);
		nombreArchivo = (TextView)findViewById(R.id.fileName);
		tamArchivo = (TextView)findViewById(R.id.fileSize);

		btnJson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						descargarJson();
					}
				});
				t.start();
			}
		});
		
		btnFichero.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String serviceString = Context.DOWNLOAD_SERVICE;
				downloadManager = (DownloadManager)getSystemService(serviceString);
				
				String dirFichero = getString(R.string.direccionFichero);
				Uri dFicheroUri = Uri.parse(dirFichero);
				DownloadManager.Request request = new Request(dFicheroUri);
				request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setDestinationInExternalFilesDir(MainActivity.this,	Environment.DIRECTORY_DOWNLOADS,	
						"icon_templates.zip");
				referenceRequest = downloadManager.enqueue(request);
				
				IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
				
				BroadcastReceiver receiver = new BroadcastReceiver() {
					
					@Override
					public void onReceive(Context context, Intent intent) {
						long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
						if(referenceRequest == reference) {
							Query query = new Query();
							query.setFilterById(reference);
							Cursor cursor = downloadManager.query(query);
							int reasonIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
							while(cursor.moveToNext()) {
								int reason = cursor.getInt(reasonIdx);
							}
						}
					}
				};
				
				registerReceiver(receiver, filter);
			}
		});
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
				Log.d("INTERNET", e1.getMessage());
			}

			try {
				JSONObject jSon = new JSONObject(builder.toString());
				JSONArray arrayJson = jSon.getJSONArray("photos");
				for (int i = 0; i < arrayJson.length(); i++) {
					JSONObject photoJson = arrayJson.getJSONObject(i);
					String id = photoJson.getString("id");
					String name = photoJson.getString("name");
					String image_url = photoJson.getString("image_url");
					Photo photo = new Photo(Integer.parseInt(id), name,
							image_url);
					photos.add(photo);
				}
				for (Photo miphoto : photos) {
					Log.d("INTERNET", miphoto.toString());
				}

			} catch (JSONException e) {
				Log.d("INTERNET", e.getMessage());
			}
		}

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
			Log.d("INTERNET", "La url no es v‡lida");
		} catch (IOException e) {
			Log.d("INTERNET", e.getMessage());
		}
		return null;
	}

}
