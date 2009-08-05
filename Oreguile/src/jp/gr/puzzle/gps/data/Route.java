package jp.gr.puzzle.gps.data;

import android.database.Cursor;

public class Route {
	private long rowid;
	private String name;
	private long start;
	private long end;
	public Route() {}
	public long getRowid() {
		return rowid;
	}
	public void setRowid(long rowid) {
		this.rowid = rowid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public static Route create(Cursor cursor) {
		Route route = new Route();
		route.setRowid(cursor.getLong(0));
		route.setName(cursor.getString(1));
		route.setStart(cursor.getLong(2));
		route.setEnd(cursor.getLong(3));
		return route;
	}
}
