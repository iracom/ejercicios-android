package com.iraitzcompains.todolistfragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MiLista extends ListFragment {
	
	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";
	
	private ArrayAdapter<String> aa;
	private ArrayList<String> todoItems;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		todoItems = new ArrayList<String>();
		
		aa = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1,todoItems);
		
		setListAdapter(aa);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addItem(String txt) {
		todoItems.add(0,txt);
		aa.notifyDataSetChanged();
	}
	
	//Esto es como hacer onRestoreInstanceState
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState != null) {
			todoItems.addAll(savedInstanceState.getStringArrayList(ITEMS_ARRAY));
			aa.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(ITEMS_ARRAY, todoItems);
		super.onSaveInstanceState(outState);
	}

}