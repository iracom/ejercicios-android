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
	String numero;
	TextView output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		output = (TextView) findViewById(R.id.output);
		texto = "";
		numero = "";
		Calc.inicializa();
	}

	/**
	 * Acciones de pulsar botones de la calculadora
	 * 
	 * @param v
	 */
	public void clickBoton(View v) {
		Button btn = (Button) v;
		char dato = btn.getText().toString().charAt(0);
		switch (dato) {
		case '+':
			// Calc.sum(Integer.parseInt(numero));
			Calc.sum(Float.parseFloat(numero));
			texto += "+";
			numero = "";
			output.setText(texto);
			break;
		case '-':
			// Calc.res(Integer.parseInt(numero));
			Calc.res(Float.parseFloat(numero));
			texto += "-";
			numero = "";
			output.setText(texto);
			break;
		case 'x':
			// Calc.mul(Integer.parseInt(numero));
			Calc.mul(Float.parseFloat(numero));
			texto += "x";
			numero = "";
			output.setText(texto);
			break;
		case '/':
			Calc.div(Float.parseFloat(numero));
			texto += "/";
			numero = "";
			output.setText(texto);
			break;
		case '=':
			if (!numero.equals("")) {
				numero = String.valueOf(Calc.igual(Float.parseFloat(numero)));
				texto = numero;
				output.setText(texto);
			}
			break;
		case '.':
			addPunto(dato);
			break;
		default:
			int num = Integer.parseInt(String.valueOf(dato));
			numero += String.valueOf(num);
			texto += numero;
			output.setText(texto);
			break;
		}
	}

	private void addPunto(char dato) {
		if (numero.equals("")) {
			numero += "0.";
			texto = numero;
		} else if (!numero.contains(String.valueOf(dato))) {
			numero += dato;
			texto = numero;
		}
		output.setText(texto);
	}

	public void clickBorrar(View v) {
		numero = "";
		texto = numero;
		output.setText(texto);
	}

	/**
	 * Para el giro de la pantalla del movil
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("texto", texto);
		outState.putString("numero", numero);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		texto = savedInstanceState.getString("texto");
		numero = savedInstanceState.getString("numero");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		output.setText(texto);
	}

}
