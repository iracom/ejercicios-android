package com.iraitzcompains.calculadorafragments;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements TecladoSimple.ITeclado {

	private Output display;
	private String numero;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		display = (Output) getFragmentManager().findFragmentById(R.id.output);
		numero = "";
		Calc.inicializa();
	}

	public void pulsarDigito(String numero) {
		proyectarNumero(numero);
		this.numero += numero;
	}

	private void proyectarNumero(String numero) {
		display.mostrarCalculo(numero);
	}

	@Override
	public void pulsarOperacion(String operacion) {
		char dato = operacion.charAt(0);
		switch (dato) {
		case '+':
			proyectarNumero(operacion);
			Calc.sum(Integer.parseInt(numero));
			numero = "";
			break;
		case '-':
			proyectarNumero(operacion);
			break;
		case 'x':
			proyectarNumero(operacion);
			break;
		case '/':
			proyectarNumero(operacion);
			break;
		case '=':
			numero = String.valueOf(Calc.igual(Integer.parseInt(numero)));
			display.mostrarResultado(numero);
			break;
		case '.':
			addPunto(dato);
			break;
		case 'D':
			display.vaciarDisplay();
			break;
		}

	}

	private void addPunto(char dato) {

	}

}
