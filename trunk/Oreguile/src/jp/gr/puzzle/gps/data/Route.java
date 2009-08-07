package jp.gr.puzzle.gps.data;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.location.Location;

import com.google.android.maps.GeoPoint;

public class Route {
	private long rowid;
	private String name;
	private long start;
	private long end;
	private float speed;
	private double length;
	private List<Location> locations = new ArrayList<Location>();
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
		route.setSpeed(cursor.getFloat(4));
		route.setLength(cursor.getDouble(5));
		return route;
	}
	public void add(GeoPoint point) {
		this.points.add(point);
	}
	public List<GeoPoint> getPoints() {
		return points;
	}
	public void add(Location location) {
		this.locations.add(location);
	}
	public void summary() {
		double s = 0.0;
		double l = 0.0;
		int n = 0;
		Location prev = null;
		for (Location loc: locations) {
			if (loc.hasSpeed()) {
				s += loc.getSpeed();
				n++;
			}
			if (prev != null) {
				l += loc.distanceTo(prev);
			}
			prev = loc;
		}
		setSpeed((float)(s / n));
		setLength(l);
	}
	
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
}
