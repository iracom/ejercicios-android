package com.iraitzcompains.ejemplointent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int FORMULARIO = 1;
	private static final int CAMARA = 2;
	
	EditText editText;
	TextView textView;
	ImageView imageView;
	Button btnForm;
	Button btnCamera;
	Button btnContact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText)findViewById(R.id.editText);
		textView = (TextView)findViewById(R.id.textView);
		imageView = (ImageView)findViewById(R.id.imageView);
		
		btnForm = (Button)findViewById(R.id.btnForm);
		btnCamera = (Button)findViewById(R.id.btnCamera);
		
		btnForm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityForm.class);
				intent.putExtra("texto_main", editText.getText().toString());
				startActivityForResult(intent, FORMULARIO);
			}
		});
		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMARA);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case FORMULARIO:
			if(resultCode == Activity.RESULT_OK) {
				String formText = String.valueOf(data.getCharSequenceExtra("texto_formulario"));
				textView.setText(formText);
			}
			break;
		case CAMARA:
			if(resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
		        Bitmap imageBitmap = (Bitmap) extras.get("data");
				imageView.setImageBitmap(imageBitmap);
			}
		default:
			break;
		}
	}
	
	

}
