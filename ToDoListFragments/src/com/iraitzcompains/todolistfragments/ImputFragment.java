package com.iraitzcompains.todolistfragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ImputFragment extends Fragment{

	View view;
	TextView inputText;
	
	public interface IimputFragment {
		public void addToDo (String todo);
	}
	
	private IimputFragment actividad;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			actividad = (IimputFragment)activity;
		} catch (ClassCastException e) {
			Log.d("CALCF", "No se ha podido hacer Casting");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.input_fragment, container, false);
		inputText = (TextView)view.findViewById(R.id.input_text);
		
		Button b = (Button)view.findViewById(R.id.add_button);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				actividad.addToDo(inputText.getText().toString());
				inputText.setText("");
			}
		});
		
		inputText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER ) {
						actividad.addToDo(inputText.getText().toString());
						inputText.setText("");
					}
				}
				return false;
			}
		});
		
		return view;
	}
	
}
