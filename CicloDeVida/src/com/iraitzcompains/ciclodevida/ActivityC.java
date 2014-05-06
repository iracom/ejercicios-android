package com.iraitzcompains.ciclodevida;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);

		Log.d("CICLO_DE_VIDA","onCreate del ActivityC");
	}

	@Override
	protected void onPause() {
		Log.d("CICLO_DE_VIDA","onPause del ActivityC");
		super.onPause();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("CICLO_DE_VIDA","onStart del ActivityC");
	}
	
	@Override
	protected void onResume() {	
		super.onResume();
		Log.d("CICLO_DE_VIDA","onResume del ActivityC");
	}

	@Override
	protected void onStop() {
		Log.d("CICLO_DE_VIDA","onStop del ActivityC");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("CICLO_DE_VIDA","onDestroy del ActivityC");
		super.onDestroy();
	}
	
	public void abrirA(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void abrirB(View view) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
	}
	
	public void cerrar(View view) {
		finish();
	}
}
