package com.iraitzcompains.earthquake;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class EarthQuakeViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor c, int idx) {
		int time_idx = c.getColumnIndex(EarthquakeContentProvider.TIME);
		
		if(time_idx == idx) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
			String dateStr = sdf.format(c.getLong(time_idx));
			
			//Est‡ mal: ((TextView)view.findViewById(R.id.txtTime)).setText(dateStr);
			//El view que llega es el TextView, no el View completo
			((TextView)view).setText(dateStr);
			
			return true;
		}

		return false;
	}

}
