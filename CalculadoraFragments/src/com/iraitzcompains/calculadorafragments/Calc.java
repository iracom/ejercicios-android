package com.iraitzcompains.calculadorafragments;

import android.util.Log;

public class Calc {

	private static float fnum1, fnum2;
	private static char operacion;
	
	public static void inicializa () {
		fnum1 = 0;
		fnum2 = 0;
		operacion = 'n';
	}
	
	public static void sum(float numero) {
		Log.d("CALCF","numero="+String.valueOf(numero));
		if(operacion == 'n')
			operacion = '+';
		fnum1 = igual(numero);
		operacion = '+';
	}
	
	public static void res(float numero) {
		if(operacion == 'n')
			operacion = '-';
		fnum1 = igual(numero);
		operacion = '-';
	}
	
	public static void mul(float numero) {
		if(operacion == 'n')
			operacion = 'x';
		if(fnum1 == 0)
			fnum1 = numero;
		else
			fnum1 *= numero;
		operacion = 'x';
	}
	
	public static void div(float numero) {
		if(operacion == 'n')
			operacion = '/';
		if(fnum1 == 0)
			fnum1 = numero;
		else
			fnum1 /= numero;
		operacion = '/';
	}
	
	public static float igual(float numero) {
		float resultado = 0;
		fnum2 = numero;
		switch(operacion) {
		case '+':
			resultado = fnum1 + fnum2;
			break;
		case '-':
			resultado = fnum1 - fnum2;
			break;
		case 'x':
			resultado = fnum1 * fnum2;
			break;
		case '/':
			if(fnum2 != 0)
				resultado = fnum1 / fnum2;
			else
				resultado = 0;
			break;
		}
		return resultado;
	}
	
}
