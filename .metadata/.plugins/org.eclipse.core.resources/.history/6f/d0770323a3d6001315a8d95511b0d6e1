package com.iraitzcompains.listatodo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity {
	
	private ArrayList<String> lista;
	private ArrayAdapter<String> adapter;

	private EditText texto;
	private ListView vLista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lista = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista);
		
		texto = (EditText)findViewById(R.id.campo);
		vLista = (ListView)findViewById(R.id.lista);
		
		vLista.setAdapter(adapter);
		
		texto.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
						lista.add(texto.getText().toString());
						adapter.notifyDataSetChanged();
						texto.setText("");
						return true;
					}
				}
				return false;
			}
		});
	}
	
	public void clickAdd(View v) {
		lista.add(texto.getText().toString());
		adapter.notifyDataSetChanged();
		texto.setText("");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList("lista", lista);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		lista.addAll(savedInstanceState.getStringArrayList("lista"));
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	
}
