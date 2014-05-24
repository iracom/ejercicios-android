package com.iraitzcompains.earthquake;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EarthquakeDB {

	private Context context;
	private SQLiteDatabase db;

	private String[] RESULT_COLUMNS;
	private String WHERE;
	private String[] WHERE_ARGS;
	private String GROUP_BY;
	private String HAVING;
	private String ORDER;

	public EarthquakeDB(Context context) {
		this.context = context;
	}

	public void openDB() {
		EarthquakeDBOpenHelper eqDBOHelper = new EarthquakeDBOpenHelper(
				this.context, EarthquakeDBOpenHelper.DATABASE_NAME, null,
				EarthquakeDBOpenHelper.DATABASE_VERSION);
		try {
			db = eqDBOHelper.getWritableDatabase();
		} catch (Exception ex) {
			Log.d("EARTHQUAKE",
					"Ha ocurrido un error al abrir la base de datos en modo escritura: "
							+ ex.getMessage());
			db = eqDBOHelper.getReadableDatabase();
		}
	}

	public void closeDB() {
		db.close();
	}

	public long insert(Earthquake earthquake) {
		this.openDB();
		ContentValues insertValues = new ContentValues();
		insertValues.put(EarthquakeDBOpenHelper.STR_ID, earthquake.getIdStr());
		insertValues.put(EarthquakeDBOpenHelper.PLACE, earthquake.getPlace());
		insertValues.put(EarthquakeDBOpenHelper.TIME,
				String.valueOf(earthquake.getTime()));
		insertValues.put(EarthquakeDBOpenHelper.DETAIL, earthquake.getDetail());
		insertValues.put(EarthquakeDBOpenHelper.MAGNITUDE,
				String.valueOf(earthquake.getMagnitude()));
		insertValues.put(EarthquakeDBOpenHelper.LAT,
				String.valueOf(earthquake.getLat()));
		insertValues.put(EarthquakeDBOpenHelper.LONG,
				String.valueOf(earthquake.getLon()));
		insertValues.put(EarthquakeDBOpenHelper.URL, earthquake.getUrl());

		insertValues.put(EarthquakeDBOpenHelper.CREATE_AT,
				(new Date()).getTime());
		insertValues.put(EarthquakeDBOpenHelper.UPDATE_AT,
				(new Date()).getTime());

		long resultado = db.insert(EarthquakeDBOpenHelper.TABLE_NAME, null, insertValues);
		this.closeDB();
		
		return resultado;

	}

	public Cursor query(String[] result_columns, String where,
			String[] whereArgs, String groupBy, String having, String order) {
		
		RESULT_COLUMNS = result_columns;
		WHERE = where;
		WHERE_ARGS = whereArgs;
		GROUP_BY = groupBy;
		HAVING = having;
		ORDER = order;

		Cursor cursor = db.query(EarthquakeDBOpenHelper.TABLE_NAME,
				RESULT_COLUMNS, WHERE, WHERE_ARGS, GROUP_BY, HAVING, ORDER);
		return cursor;
	}

	public ArrayList<Earthquake> getEarthquakes(double magnitude) {
		ArrayList<Earthquake> terremotos = new ArrayList<Earthquake>();

		String[] columnas = { EarthquakeDBOpenHelper._ID,
				EarthquakeDBOpenHelper.STR_ID, EarthquakeDBOpenHelper.PLACE,
				EarthquakeDBOpenHelper.TIME, EarthquakeDBOpenHelper.DETAIL,
				EarthquakeDBOpenHelper.MAGNITUDE, EarthquakeDBOpenHelper.LAT,
				EarthquakeDBOpenHelper.LONG, EarthquakeDBOpenHelper.URL,
				EarthquakeDBOpenHelper.CREATE_AT,
				EarthquakeDBOpenHelper.UPDATE_AT };
		String where = EarthquakeDBOpenHelper.MAGNITUDE + " >= ?";
		String[] where_args = { String.valueOf(magnitude) };

		this.openDB();
		Cursor cursor = query(columnas, where, where_args, null, null, EarthquakeDBOpenHelper.TIME + " DESC");
		if (cursor.getCount() > 0) {
			int indId = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper._ID);
			int indIdStr = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.STR_ID);
			int indPlace = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.PLACE);
			int indTime = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.TIME);
			int indDetail = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.DETAIL);
			int indMagnitude = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.MAGNITUDE);
			int indLat = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.LAT);
			int indLon = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.LONG);
			int indUrl = cursor
					.getColumnIndexOrThrow(EarthquakeDBOpenHelper.URL);
			while (cursor.moveToNext()) {
				Earthquake e = new Earthquake(cursor.getInt(indId),
						cursor.getString(indIdStr), cursor.getString(indPlace),
						cursor.getLong(indTime), cursor.getString(indDetail),
						cursor.getDouble(indMagnitude),
						cursor.getDouble(indLat), cursor.getDouble(indLon),
						cursor.getString(indUrl));
				terremotos.add(e);
			}
		}
		cursor.close();
		this.closeDB();
		return terremotos;
	}
	
	public boolean earthquakeExist(Earthquake eq) {
		boolean exist = false;
		
		String[] columnaStrId = {EarthquakeDBOpenHelper.STR_ID};
		String where = EarthquakeDBOpenHelper.STR_ID + " = ?";
		String[] whereArgs = {eq.getIdStr()};
		
		this.openDB();
		Cursor cursor = query(columnaStrId,where,whereArgs,null,null,null);
		if(cursor.getCount() > 0)
			exist = true;
		
		return exist;
	}

	public void update(Earthquake eq, String[] campos, String[] datos,
			String where, String[] whereArgs) {
		ContentValues updateValues = new ContentValues();
		for (int i = 0; i < campos.length; i++) {
			updateValues.put(campos[i], datos[i]);
		}
		updateValues.put(EarthquakeDBOpenHelper.UPDATE_AT, (new Date()).getTime());
		this.openDB();
		db.update(EarthquakeDBOpenHelper.TABLE_NAME, updateValues, where,
				whereArgs);
		this.closeDB();
	}

	public void detele(Earthquake eq) {
		String whereClause = EarthquakeDBOpenHelper._ID + " = ?";
		String[] whereArgs = { String.valueOf(eq.get_id()) };
		this.openDB();
		db.delete(EarthquakeDBOpenHelper.TABLE_NAME, whereClause, whereArgs);
		this.closeDB();
	}
}
