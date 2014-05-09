package com.iraitzcompains.calculadorafragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Output extends Fragment {

	View view;
	TextView display;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_output, container, false);
		display = (TextView)view.findViewById(R.id.outputText);
		
		
		return view;
	}
	
	public void mostrarResultado(String res) {
		display.setText(display.getText() + res);
	}
}
