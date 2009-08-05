package jp.gr.puzzle.gps.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Helper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_LOCATION_SQL =
		"create table location ( " +
		"    rawid integer primary key autoincrement " +
		"    ,route_id integer " +
		"    ,provider text " +
		"    ,mtime integer " +
		"    ,latitude real " +
		"    ,longitude real " +
		"    ,has_altitude text " +
		"    ,altitude real " +
		"    ,has_speed text " +
		"    ,speed real " +
		"    ,has_bearing text " +
		"    ,bearing real " +
		"    ,has_accuracy text " +
		"    ,accuracy real " +
		"    ,extras text " +
		" ) ";
	private static final String CREATE_ROUTE_SQL =
		"create table route ( " +
		"    rawid integer primary key autoincrement " +
		"    ,name text " +
		"    ,start integer" +
		"    ,end integer" +
		" ) ";
	private static final String DROP_LOCATION_SQL = "drop table if exists location";
	private static final String DROP_ROUTE_SQL = "drop table if exists route";
	
	public Helper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_LOCATION_SQL);
		db.execSQL(CREATE_ROUTE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_LOCATION_SQL);
		db.execSQL(DROP_ROUTE_SQL);
		onCreate(db);
	}

}
