package jp.gr.puzzle.gps.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RouteDao {
	private static final String TABLE_NAME    = "route";
	private static final String COLUMN_ROWID  = "rowid";
	private static final String COLUMN_NAME   = "name";
	private static final String COLUMN_START  = "start";
	private static final String COLUMN_END    = "end";
	private static final String COLUMN_SPEED  = "speed";
	private static final String COLUMN_LENGTH = "length";
	private static final String[] COLUMNS =
		{COLUMN_ROWID, COLUMN_NAME, COLUMN_START, COLUMN_END};
	
	private SQLiteDatabase db;

	public RouteDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	public long insert(Route route) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, route.getName());
		values.put(COLUMN_START, route.getStart());
		values.put(COLUMN_END, route.getEnd());
		values.put(COLUMN_SPEED, route.getSpeed());
		values.put(COLUMN_LENGTH, route.getLength());
		return db.insert(TABLE_NAME, null, values);
	}
	
	public long update(Route route) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, route.getName());
		values.put(COLUMN_START, route.getStart());
		values.put(COLUMN_END, route.getEnd());
		values.put(COLUMN_SPEED, route.getSpeed());
		values.put(COLUMN_LENGTH, route.getLength());
		String whereClause = "rowid = " + route.getRowid();
		return db.update(TABLE_NAME, values, whereClause, null);
	}
	
	public List<Route> findAll() {
		List<Route> routeList = new ArrayList<Route>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_START);
		while (cursor.moveToNext()) {
			routeList.add(Route.create(cursor));
		}
		return routeList;
	}
	
	public Route findById(long rowid) {
		String selection = "rowid = " + rowid;
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
		while (cursor.moveToNext()) {
			return Route.create(cursor);
		}
		return null;
	}
}
