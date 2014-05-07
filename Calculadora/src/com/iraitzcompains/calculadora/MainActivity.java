package com.iraitzcompains.calculadora;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;
import com.iraitzcompains.calculadora.Calc;

public class MainActivity extends Activity {

	String texto;
	TextView output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		output = (TextView) findViewById(R.id.output);
		texto = "";
	}

	public void clickBoton(View v) {
		Button btn = (Button) v;
		char dato = btn.getText().toString().charAt(0);
		switch (dato) {
		case '+':
			Log.d("CALC", "Se ha pulsado +");
			Calc.sum(Integer.parseInt(output.getText().toString()));
			break;
		case '-':
			Log.d("CALC", "Se ha pulsado -");
			break;
		case 'x':
			Log.d("CALC", "Se ha pulsado x");
			break;
		case '/':
			Log.d("CALC", "Se ha pulsado /");
			break;
		case '=':
			Log.d("CALC", "Se ha pulsado =");
			break;
		case '.':
			addPunto(dato);
			break;
		default:
			int num = Integer.parseInt(String.valueOf(dato));
			texto += String.valueOf(num);
			output.setText(texto);
			break;
		}
	}
	
	private void addPunto(char dato){
		if(texto.equals(""))
			texto += "0.";
		else if(!texto.contains(String.valueOf(dato)))
				texto += dato;
		output.setText(texto);
	}
	
	public void clickBorrar(View v){
		texto = "";
		output.setText(texto);
	}

}
