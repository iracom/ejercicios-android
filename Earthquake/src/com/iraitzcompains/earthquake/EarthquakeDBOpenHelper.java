package com.iraitzcompains.earthquake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EarthquakeDBOpenHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Earthquake.db";
	public static final String TABLE_NAME = "Earthquake";
	public static final int DATABASE_VERSION = 1;

	public static final String _ID = "_id";
	public static final String STR_ID = "str_id"; 
	public static final String PLACE = "place";
	public static final String TIME = "time";
	public static final String DETAIL = "detail";
	public static final String MAGNITUDE = "magnitude";
	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String URL = "url";
	public static final String CREATE_AT = "create_at";
	public static final String UPDATE_AT = "update_at";

	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ STR_ID + " TEXT UNIQUE, "
			+ PLACE + " TEXT, "
			+ TIME + " NUMERIC, "
			+ DETAIL + " TEXT, "
			+ MAGNITUDE + " NUMERIC, "
			+ LAT + " NUMERIC, "
			+ LONG + " NUMERIC, "
			+ URL + " TEXT, "
			+ CREATE_AT + " NUMERIC, "
			+ UPDATE_AT + " NUMERIC)";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ DATABASE_NAME;

	public EarthquakeDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
