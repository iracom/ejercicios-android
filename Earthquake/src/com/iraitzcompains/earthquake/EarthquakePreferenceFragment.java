package com.iraitzcompains.earthquake;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class EarthquakePreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
}
