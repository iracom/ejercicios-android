package com.iraitzcompains.calculadorafragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TecladoSimple extends Fragment {

	private int[] btnNumeros = { R.id.btn_0, R.id.btn_1, R.id.btn_2,
			R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
			R.id.btn_8, R.id.btn_9 };
	private int[] btnOperaciones = { R.id.btn_sum, R.id.btn_res, R.id.btn_mul,
			R.id.btn_div, R.id.btn_delete, R.id.btn_punto, R.id.btn_igual };

	public interface ITeclado {
		public void pulsarDigito(String numero);

		public void pulsarOperacion(String operacion);
	}

	private ITeclado actividad;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			actividad = (ITeclado) activity;
		} catch (ClassCastException e) {
			Log.d("CALCF", "No se ha podido hacer Casting");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myActivity = inflater.inflate(R.layout.teclado_simple, container,
				false);

		Button b;
		for (int i = 0; i < btnNumeros.length; i++) {
			b = (Button) myActivity.findViewById(btnNumeros[i]);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					actividad
							.pulsarDigito(((Button) arg0).getText().toString());
				}
			});
		}
		for (int i=0;i<btnOperaciones.length;i++) {
			b = (Button)myActivity.findViewById(btnOperaciones[i]);
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					actividad.pulsarOperacion(((Button)v).getText().toString());
				}
			});
		}

		return myActivity;
	}
}
