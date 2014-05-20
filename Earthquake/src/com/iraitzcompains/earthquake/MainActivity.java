package com.iraitzcompains.earthquake;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	EarthquakeDB eqdb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Earthquake eq = new Earthquake("1","Inventado",new Date().getTime(), "Detalle", 1.7, 0.5, 10.4, "www.url.es");
		Earthquake eq2 = new Earthquake("2","Inventado2",new Date().getTime(), "Detalle2", 1.7, 0.5, 10.4, "www.url.es");
		Earthquake eq3 = new Earthquake("3","Inventado3",new Date().getTime(), "Detalle3", 1.7, 0.5, 10.4, "www.url.es");
		
		eqdb = new EarthquakeDB(this);
		//insertarTerremoto(eq3);
		//actualizarTerremoto();
		//borrarTerremoto();
		mostrarTerremotos();
		eqdb.closeDB();
	}
	
	private void insertarTerremoto(Earthquake eq) {
		eqdb.insert(eq);
	}
	
	private void mostrarTerremotos() {
		ArrayList<Earthquake> terremotos = eqdb.getEarthquakes(1.0);
		for (Earthquake earthquake : terremotos) {
			Log.d("EARTHQUAKE",earthquake.toString());
		}
	}
	
	private void actualizarTerremoto() {
		Earthquake eq = new Earthquake(1,"1","Inventado",new Date().getTime(), "Detalle", 1.7, 0.5, 10.4, "www.url.es");
		String[] campos = {EarthquakeDBOpenHelper.PLACE, EarthquakeDBOpenHelper.DETAIL};
		String[] datos = {"Updateado","Updateado"};
		String where = EarthquakeDBOpenHelper._ID + " = ?";
		String[] whereArgs = {String.valueOf(eq.get_id())};
		eqdb.update(eq, campos, datos, where, whereArgs);
	}
	
	private void borrarTerremoto() {
		Earthquake eq = new Earthquake(3,"3","Inventado3",new Date().getTime(), "Detalle3", 1.7, 0.5, 10.4, "www.url.es");
		eqdb.detele(eq);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
