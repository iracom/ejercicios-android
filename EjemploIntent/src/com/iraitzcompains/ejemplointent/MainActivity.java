package com.iraitzcompains.ejemplointent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int FORMULARIO = 1;
	private static final int CAMARA = 2;
	private static final int CONTACTOS = 3;
	
	EditText editText;
	TextView textView;
	ImageView imageView;
	Button btnForm;
	Button btnCamera;
	Button btnContact;
	File image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText)findViewById(R.id.editText);
		textView = (TextView)findViewById(R.id.textView);
		imageView = (ImageView)findViewById(R.id.imageView);
		
		btnForm = (Button)findViewById(R.id.btnForm);
		btnCamera = (Button)findViewById(R.id.btnCamera);
		btnContact = (Button)findViewById(R.id.btnContact);
		
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
				
				// Ensure that there's a camera activity to handle the intent
			    if (intent.resolveActivity(getPackageManager()) != null) {
			        // Create the File where the photo should go
			        File photoFile = null;
			        try {
			            photoFile = createImageFile();
			        } catch (IOException ex) {
			            // Error occurred while creating the File
			        	Log.d("INTENT","Error: " + ex.getMessage());
			        }
			        // Continue only if the File was successfully created
			        if (photoFile != null) {
			            intent.putExtra(MediaStore.EXTRA_OUTPUT,
			                    Uri.fromFile(photoFile));
			            startActivityForResult(intent, CAMARA);
			        }
			    }
			}
		});
		
		btnContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				intent.setType(Phone.CONTENT_TYPE);
				startActivityForResult(intent, CONTACTOS);
			}
		});
		
	}
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStorageDirectory();
	    image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );
	    return image;
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
				/*Bundle extras = data.getExtras();
		        Bitmap imageBitmap = (Bitmap) extras.get("data");
				imageView.setImageBitmap(imageBitmap);*/
				setPic();
			}
		case CONTACTOS:
			if(resultCode == Activity.RESULT_OK) {
				// Get the URI that points to the selected contact
	            Uri contactUri = data.getData();
	            // We only need the NUMBER column, because there will be only one row in the result
	            String[] projection = {Phone.NUMBER};

	            // Perform the query on the contact to get the NUMBER column
	            // We don't need a selection or sort order (there's only one result for the given URI)
	            // CAUTION: The query() method should be called from a separate thread to avoid blocking
	            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
	            // Consider using CursorLoader to perform the query.
	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            // Retrieve the phone number from the NUMBER column
	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(column);

	            // Do something with the phone number...
			}
		default:
			break;
		}
	}
	
	//Codifica la imagen para que se vea bien
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = imageView.getWidth();
	    int targetH = imageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    imageView.setImageBitmap(bitmap);
	}
	
	

}
