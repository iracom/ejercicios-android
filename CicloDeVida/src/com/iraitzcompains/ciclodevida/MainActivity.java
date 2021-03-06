package com.iraitzcompains.ciclodevida;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d("CICLO_DE_VIDA","onCreate del ActivityA");
	}

	@Override
	protected void onPause() {
		Log.d("CICLO_DE_VIDA","onPause del ActivityA");
		super.onPause();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("CICLO_DE_VIDA","onStart del ActivityA");
	}
	
	@Override
	protected void onResume() {	
		super.onResume();
		Log.d("CICLO_DE_VIDA","onResume del ActivityA");
	}

	@Override
	protected void onStop() {
		Log.d("CICLO_DE_VIDA","onStop del ActivityA");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("CICLO_DE_VIDA","onDestroy del ActivityA");
		super.onDestroy();
	}
	
	public void abrirB(View view) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
	}
	
	public void abrirC(View view) {
		Intent intent = new Intent(this, ActivityC.class);
		startActivity(intent);
	}
	
	public void cerrar(View view) {
		finish();
	}
}
