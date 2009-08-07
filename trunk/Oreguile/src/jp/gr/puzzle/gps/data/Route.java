package jp.gr.puzzle.gps.data;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.database.Cursor;

public class Route {
	private long rowid;
	private String name;
	private long start;
	private long end;
	private List<GeoPoint> points = new ArrayList<GeoPoint>();
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
	public void add(GeoPoint point) {
		points.add(point);
	}
	public List<GeoPoint> getPoints() {
		return points;
	}
}
