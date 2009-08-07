package jp.gr.puzzle.gps.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocationDao {
	private static final String TABLE_NAME          = "location";
	private static final String COLUMN_ROWID        = "rawid";
	private static final String COLUMN_ROUTE_ID     = "route_id";
	private static final String COLUMN_PROVIDER     = "provider";
	private static final String COLUMN_MTIME        = "mtime";
	private static final String COLUMN_LATITUDE     = "latitude";
	private static final String COLUMN_LONGITUDE    = "longitude";
	private static final String COLUMN_HAS_ALTITUDE = "has_altitude";
	private static final String COLUMN_ALTITUDE     = "altitude";
	private static final String COLUMN_HAS_SPEED    = "has_speed";
	private static final String COLUMN_SPEED        = "speed";
	private static final String COLUMN_HAS_BEARING  = "has_bearing";
	private static final String COLUMN_BEARING      = "bearing";
	private static final String COLUMN_HAS_ACCURACY = "has_accuracy";
	private static final String COLUMN_ACCURACY     = "accuracy";
	private static final String COLUMN_EXTRAS       = "extras";
	private static final String[] COLUMNS =
		{	COLUMN_ROWID, COLUMN_ROUTE_ID, COLUMN_PROVIDER, COLUMN_MTIME,
			COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_HAS_ALTITUDE, COLUMN_ALTITUDE,
			COLUMN_HAS_SPEED, COLUMN_SPEED, COLUMN_HAS_BEARING, COLUMN_BEARING,
			COLUMN_HAS_ACCURACY, COLUMN_ACCURACY, COLUMN_EXTRAS	};
	
	private SQLiteDatabase db;
	
	public LocationDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	public long insert(LocationData data) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ROUTE_ID, data.getRouteId());
		values.put(COLUMN_MTIME, data.getTime());
		values.put(COLUMN_LATITUDE, data.getLatitude());
		values.put(COLUMN_LONGITUDE, data.getLongitude());
		values.put(COLUMN_HAS_ALTITUDE, data.hasAltitude());
		values.put(COLUMN_ALTITUDE, data.getAltitude());
		values.put(COLUMN_HAS_SPEED, data.hasSpeed());
		values.put(COLUMN_SPEED, data.getSpeed());
		values.put(COLUMN_HAS_BEARING, data.hasBearing());
		values.put(COLUMN_BEARING, data.getBearing());
		values.put(COLUMN_HAS_ACCURACY, data.hasAccuracy());
		values.put(COLUMN_ACCURACY, data.getAccuracy());
		values.put(COLUMN_EXTRAS, data.getExtras().toString());
		return db.insert(TABLE_NAME, null, values);
	}
	
	public long update(LocationData data) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ROUTE_ID, data.getRouteId());
		values.put(COLUMN_PROVIDER, data.getProvider());
		values.put(COLUMN_MTIME, data.getTime());
		values.put(COLUMN_LATITUDE, data.getLatitude());
		values.put(COLUMN_LONGITUDE, data.getLongitude());
		values.put(COLUMN_HAS_ALTITUDE, data.hasAltitude());
		values.put(COLUMN_ALTITUDE, data.getAltitude());
		values.put(COLUMN_HAS_SPEED, data.hasSpeed());
		values.put(COLUMN_SPEED, data.getSpeed());
		values.put(COLUMN_HAS_BEARING, data.hasBearing());
		values.put(COLUMN_BEARING, data.getBearing());
		values.put(COLUMN_HAS_ACCURACY, data.hasAccuracy());
		values.put(COLUMN_ACCURACY, data.getAccuracy());
		values.put(COLUMN_EXTRAS, data.getExtras().toString());
		String whereClause = "rowid = " + data.getRowid();
		return db.update(TABLE_NAME, values, whereClause, null);
	}
	
	public List<LocationData> findALl() {
		List<LocationData> locationList = new ArrayList<LocationData>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_MTIME);
		while (cursor.moveToNext()) {
			locationList.add(LocationData.create(cursor));
		}
		return locationList;
	}
	
	public List<LocationData> findByRouteId(long routeId) {
		String selection = "routeId = " + routeId;
		List<LocationData> locationList = new ArrayList<LocationData>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, selection, null, null, null, COLUMN_MTIME);
		while (cursor.moveToNext()) {
			locationList.add(LocationData.create(cursor));
		}
		return locationList;
	}
	
	public LocationData findById(long rowid) {
		String selection = "rowid = " + rowid;
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
		while (cursor.moveToNext()) {
			return LocationData.create(cursor);
		}
		return null;
	}
	
	public int delete(long rowid) {
		return db.delete(TABLE_NAME, "rowid = " + rowid, null);
	}
}
