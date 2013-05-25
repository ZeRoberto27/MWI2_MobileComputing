package at.wien.technikum.fh.parkandride.support.providers;

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
import at.wien.technikum.fh.parkandride.domain.ParkAndRide.ParkAndRideTable;

/**
 * @author Michael Leitner
 * 
 */
public class ParkAndRideContentProvider extends ContentProvider {

	private static final String TAG = "ParkAndRideContentProvider";

	private static final String DATABASE_NAME = "parkandride.db";

	private static final int DATABASE_VERSION = 2;

	private static final String TABLE_NAME = "parkandrides";

	public static final String AUTHORITY = "at.wien.technikum.fh.parkandride.support.providers.ParkAndRideContentProvider";

	private static final UriMatcher sUriMatcher;

	private static final int PARKANDRIDES = 1;

	private static final int PARKANDRIDES_ID = 2;

	private static HashMap<String, String> parkAndRidesProjectionMap;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.v(TAG, "Creating Database");
			
			db.execSQL("CREATE TABLE " + TABLE_NAME + "( " 
					+ ParkAndRideTable.ID + " VARCHAR(50) PRIMARY KEY," 
					+ ParkAndRideTable.DISTRICT + " VARCHAR(50)," 
					+ ParkAndRideTable.ADDRESS + " VARCHAR(120)," 
					+ ParkAndRideTable.GARAGENAME + " VARCHAR(100), " 
					+ ParkAndRideTable.GARAGOPERATOR + " VARCHAR(100), "
					+ ParkAndRideTable.HOMEPAGE + " VARCHAR(150),"
					+ ParkAndRideTable.LONGITUDE + " VARCHAR(50),"
					+ ParkAndRideTable.LATITUDE + " VARCHAR(50)" +");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	private DatabaseHelper dbHelper;

	@Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case PARKANDRIDES:
                break;
            case PARKANDRIDES_ID:
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
		case PARKANDRIDES:
			return ParkAndRideTable.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (sUriMatcher.match(uri) != PARKANDRIDES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, ParkAndRideTable.GARAGENAME, values);

		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(ParkAndRideTable.CONTENT_URI,
					rowId);
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        qb.setProjectionMap(parkAndRidesProjectionMap);
 
        switch (sUriMatcher.match(uri)) {    
            case PARKANDRIDES:
                break;
            case PARKANDRIDES_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
 
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
 
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case PARKANDRIDES:
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
		sUriMatcher.addURI(AUTHORITY, TABLE_NAME, PARKANDRIDES);
		sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", PARKANDRIDES_ID);

		parkAndRidesProjectionMap = new HashMap<String, String>();

		parkAndRidesProjectionMap.put(ParkAndRideTable.ID, ParkAndRideTable.ID);
		parkAndRidesProjectionMap.put(ParkAndRideTable.DISTRICT,
				ParkAndRideTable.DISTRICT);
		parkAndRidesProjectionMap.put(ParkAndRideTable.ADDRESS,
				ParkAndRideTable.ADDRESS);
		parkAndRidesProjectionMap.put(ParkAndRideTable.GARAGENAME,
				ParkAndRideTable.GARAGENAME);
		parkAndRidesProjectionMap.put(ParkAndRideTable.GARAGOPERATOR,
				ParkAndRideTable.GARAGOPERATOR);
		parkAndRidesProjectionMap.put(ParkAndRideTable.HOMEPAGE,
				ParkAndRideTable.HOMEPAGE);
		parkAndRidesProjectionMap.put(ParkAndRideTable.LONGITUDE,
				ParkAndRideTable.LONGITUDE);
		parkAndRidesProjectionMap.put(ParkAndRideTable.LATITUDE,
				ParkAndRideTable.LATITUDE);
	}
}