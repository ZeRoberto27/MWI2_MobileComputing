package at.technikum.wien.fh.wi.ma.shitdroid.service;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity.WcTable;

/**
 * 
 * @author Robert
 * 
 */
public class ShitroidContentProvider extends ContentProvider {

	private static final String DATABASE_NAME = "shitdroid.db";

	private static final int DATABASE_VERSION = 2;

	private static final String TABLE_NAME = "wc";

	public static final String AUTHORITY = "at.technikum.wien.fh.wi.ma.shitdroid.service";

	private static final UriMatcher sUriMatcher;

	private static final int WCS = 1;

	private static final int WCS_ID = 2;

	private static HashMap<String, String> wcProjectionMap;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("onCreate", "Creating Database");

			db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + WcTable.STANDORTID
					+ " VARCHAR(50) PRIMARY KEY," + WcTable.BEZIRK
					+ " VARCHAR(50)," + WcTable.STRASSE + " VARCHAR(120),"
					+ WcTable.ORIENTIERUNGSNR + " VARCHAR(50), "
					+ WcTable.TELEFON + " VARCHAR(100), "
					+ WcTable.OEFFNUNGSZEIT + " VARCHAR(50),"
					+ WcTable.INFORMATION + " VARCHAR(1000),"
					+ WcTable.ABTEILUNG + " ABTEILUNG(50)," + WcTable.KATEGORIE
					+ " VARCHAR(1000)," + WcTable.LAENGENGRAD
					+ " VARCHAR(100)," + WcTable.BREITENGRAD + " VARCHAR(100)"
					+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("onUpgrade", "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	private DatabaseHelper dbHelper;

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
		case WCS:
			break;
		case WCS_ID:
			where = where + "_id = " + uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		int count = db.delete(TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case WCS:
			return WcTable.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (sUriMatcher.match(uri) != WCS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, WcTable.STRASSE, values);

		if (rowId > 0) {
			Uri noteUri = ContentUris
					.withAppendedId(WcTable.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(wcProjectionMap);

		switch (sUriMatcher.match(uri)) {
		case WCS:
			break;
		case WCS_ID:
			selection = selection + "_id = " + uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case WCS:
			count = db.update(TABLE_NAME, values, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, TABLE_NAME, WCS);
		sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", WCS_ID);

		wcProjectionMap = new HashMap<String, String>();

		wcProjectionMap.put(WcTable.STANDORTID, WcTable.STANDORTID);
		wcProjectionMap.put(WcTable.BEZIRK, WcTable.BEZIRK);
		wcProjectionMap.put(WcTable.STRASSE, WcTable.STRASSE);
		wcProjectionMap.put(WcTable.ORIENTIERUNGSNR, WcTable.ORIENTIERUNGSNR);
		wcProjectionMap.put(WcTable.TELEFON, WcTable.TELEFON);
		wcProjectionMap.put(WcTable.OEFFNUNGSZEIT, WcTable.OEFFNUNGSZEIT);
		wcProjectionMap.put(WcTable.INFORMATION, WcTable.INFORMATION);
		wcProjectionMap.put(WcTable.ABTEILUNG, WcTable.ABTEILUNG);
		wcProjectionMap.put(WcTable.KATEGORIE, WcTable.KATEGORIE);
		wcProjectionMap.put(WcTable.LAENGENGRAD, WcTable.LAENGENGRAD);
		wcProjectionMap.put(WcTable.BREITENGRAD, WcTable.BREITENGRAD);
	}
}