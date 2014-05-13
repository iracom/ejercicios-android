package com.iraitzcompains.ejemplointent;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class ActivityForm extends Activity {

	EditText editText;
	TextView textView;
	Button btnOk;
	Button btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		editText = (EditText)findViewById(R.id.editTextForm);
		textView = (TextView)findViewById(R.id.textViewForm);
		btnOk = (Button)findViewById(R.id.btnOk);
		btnBack = (Button)findViewById(R.id.btnBack);
		
		Intent intent = getIntent();
		String mainText = String.valueOf(intent.getCharSequenceExtra("texto_main"));
		textView.setText(mainText);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent respuesta = new Intent(ActivityForm.this,MainActivity.class);
				respuesta.putExtra("texto_formulario", editText.getText().toString());
				setResult(RESULT_OK, respuesta);
				finish();
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

}
