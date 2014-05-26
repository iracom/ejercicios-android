package com.iraitzcompains.earthquake;

import java.util.Date;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class EarthquakeContentProvider extends ContentProvider {

	private static final String uri = "content://com.iraitzcompains.provider.earthquake/earthquakes";

	public static final Uri CONTENT_URI = Uri.parse(uri);

	// Columnas
	public static final String _ID = EarthquakeOpenHelper._ID;
	public static final String STR_ID = EarthquakeOpenHelper.STR_ID;
	public static final String PLACE = EarthquakeOpenHelper.PLACE;
	public static final String TIME = EarthquakeOpenHelper.TIME;
	public static final String DETAIL = EarthquakeOpenHelper.DETAIL;
	public static final String MAGNITUDE = EarthquakeOpenHelper.MAGNITUDE;
	public static final String LAT = EarthquakeOpenHelper.LAT;
	public static final String LONG = EarthquakeOpenHelper.LONG;
	public static final String URL = EarthquakeOpenHelper.URL;
	public static final String CREATE_AT = EarthquakeOpenHelper.CREATE_AT;
	public static final String UPDATE_AT = EarthquakeOpenHelper.UPDATE_AT;

	// SQLiteOpenHelper
	private class EarthquakeOpenHelper extends SQLiteOpenHelper {

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

		private static final String DATABASE_CREATE = "CREATE TABLE "
				+ TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + STR_ID
				+ " TEXT UNIQUE, " + PLACE + " TEXT, " + TIME + " NUMERIC, "
				+ DETAIL + " TEXT, " + MAGNITUDE + " NUMERIC, " + LAT
				+ " NUMERIC, " + LONG + " NUMERIC, " + URL + " TEXT, "
				+ CREATE_AT + " NUMERIC, " + UPDATE_AT + " NUMERIC)";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

		public EarthquakeOpenHelper(Context context, String name,
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

	// Base de datos
	public static final String DATABASE_NAME = EarthquakeOpenHelper.DATABASE_NAME;
	public static final String TABLE_NAME = EarthquakeOpenHelper.TABLE_NAME;
	public static final int DATABASE_VERSION = EarthquakeOpenHelper.DATABASE_VERSION;
	private EarthquakeOpenHelper eqSqliteHelper;

	// Uri Matcher
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.iraitzcompains.provider.earthquake",
				"earthquakes", ALLROWS);
		uriMatcher.addURI("com.iraitzcompains.provider.earthquake",
				"earthquakes/#", SINGLE_ROW);
	}
	
	@Override
	public boolean onCreate() {
		eqSqliteHelper = new EarthquakeOpenHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
		
		return false;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = eqSqliteHelper.getWritableDatabase();
		
		values.put(EarthquakeContentProvider.CREATE_AT, new Date().getTime());
		values.put(EarthquakeContentProvider.UPDATE_AT, new Date().getTime());
		
		long id = db.insert(TABLE_NAME, null, values);
		
		if(id > -1) {
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			
			getContext().getContentResolver().notifyChange(insertedId, null);
			
			return insertedId;
		}
		
		return null;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs,
			String sortOrder) {
		SQLiteDatabase db;
		try {
			db = eqSqliteHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = eqSqliteHelper.getReadableDatabase();
		}
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		switch(uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(" _ID = " + rowID);
			break;
		}
		
		queryBuilder.setTables(TABLE_NAME);
		
		Cursor cursor = queryBuilder.query(db, projection, where, whereArgs, null, null, sortOrder);
		
		return cursor;
	}
	
	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		switch (uriMatcher.match(arg0)) {
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.paad.provider.elemental";
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.paad.provider.elemental";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
}
