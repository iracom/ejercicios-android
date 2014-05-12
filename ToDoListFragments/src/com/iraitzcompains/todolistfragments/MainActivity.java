package com.iraitzcompains.todolistfragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements ImputFragment.IimputFragment {

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	
	ArrayList<String> lista;
	ArrayAdapter<String> adapter;
	
	MiLista miListaFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		lista = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
		miListaFragment = new MiLista();
		miListaFragment.setListAdapter(adapter);
		
		fragmentTransaction.replace(R.id.input_fragment, new ImputFragment());
		
		fragmentTransaction.replace(R.id.mi_lista, miListaFragment);
		
		fragmentTransaction.commit();
		
	}

	@Override
	public void addToDo(String todo) {
		lista.add(0, todo);
		adapter.notifyDataSetChanged();
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
