package com.iraitzcompains.mapas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Intent intent = getIntent();
		long nombre = intent.getLongExtra("nombre", (long)0);
		
		TextView text = (TextView)findViewById(R.id.nombre);
		text.setText(String.valueOf(nombre));
	}
}
