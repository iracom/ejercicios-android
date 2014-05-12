package com.iraitzcompains.todolistfragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

public class MiLista extends ListFragment {
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setListShown(true);
	}

}