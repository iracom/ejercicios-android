package com.iraitzcompains.ejemplointent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int FORMULARIO = 1;
	
	Button btnForm;
	EditText editText;
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnForm = (Button)findViewById(R.id.btnForm);
		editText = (EditText)findViewById(R.id.editText);
		textView = (TextView)findViewById(R.id.textView);
		
		btnForm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityForm.class);
				intent.putExtra("texto_main", editText.getText().toString());
				startActivityForResult(intent, FORMULARIO);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case FORMULARIO:
			if(resultCode == Activity.RESULT_OK){
				String formText = String.valueOf(data.getCharSequenceExtra("texto_formulario"));
				textView.setText(formText);
			}
			break;

		default:
			break;
		}
	}
	
	

}
