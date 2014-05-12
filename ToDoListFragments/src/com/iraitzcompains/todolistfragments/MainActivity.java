package com.iraitzcompains.todolistfragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements
		ImputFragment.IimputFragment {

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();


		if (savedInstanceState == null) {
			fragmentTransaction.add(R.id.input_fragment, new ImputFragment());

			fragmentTransaction.add(R.id.mi_lista, new MiLista(), "lista");

			fragmentTransaction.commit();
		}

	}

	@Override
	public void addToDo(String todo) {
		((MiLista) getFragmentManager().findFragmentByTag("lista"))
				.addItem(todo);
	}

}
