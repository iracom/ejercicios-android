package com.iraitzcompains.calculadora;

public class Calc {

	private static int num1, num2;
	private static char operacion;
	
	public static void sum (int numero) {
		num1 = numero;
		operacion = '+';
	}
	
	public static int igual(int numero) {
		int resultado = 0;
		num2 = numero;
		switch(operacion) {
		case '+':
			resultado = num1 + num2;
			break;
		case '-':
			break;
		case 'x':
			break;
		case '/':
			break;
		}
		return resultado;
	}
}
