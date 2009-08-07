package jp.gr.puzzle.gps.data;

import android.database.Cursor;
import android.location.Location;

public class LocationData {
	private long rowid;
	private long routeId;
	private String provider;
	private long time;
	private double latitude;
	private double longitude;
	private boolean hasAltitude;
	private double altitude;
	private boolean hasSpeed;
	private float speed;
	private boolean hasBearing;
	private float bearing;
	private boolean hasAccuracy;
	private float accuracy;
	private String extras;

	public LocationData() {
	}
	public long getRowid() {
		return rowid;
	}
	public void setRowid(long rowid) {
		this.rowid = rowid;
	}
	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public boolean hasAltitude() {
		return hasAltitude;
	}
	public void setHasAltitude(boolean hasAltitude) {
		this.hasAltitude = hasAltitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public boolean hasSpeed() {
		return hasSpeed;
	}
	public void setHasSpeed(boolean hasSpeed) {
		this.hasSpeed = hasSpeed;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public boolean hasBearing() {
		return hasBearing;
	}
	public void setHasBearing(boolean hasBearing) {
		this.hasBearing = hasBearing;
	}
	public float getBearing() {
		return bearing;
	}
	public void setBearing(float bearing) {
		this.bearing = bearing;
	}
	public boolean hasAccuracy() {
		return hasAccuracy;
	}
	public void setHasAccuracy(boolean hasAccuracy) {
		this.hasAccuracy = hasAccuracy;
	}
	public float getAccuracy() {
		return accuracy;
	} 
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	public static LocationData create(Location location) {
		LocationData data = new LocationData();
		data.setProvider(location.getProvider());
		data.setTime(location.getTime());
		data.setLatitude(location.getLatitude());
		data.setLongitude(location.getLongitude());
		data.setHasAltitude(location.hasAltitude());
		data.setAltitude(location.getAltitude());
		data.setHasSpeed(location.hasSpeed());
		data.setSpeed(location.getSpeed());
		data.setHasBearing(location.hasBearing());
		data.setBearing(location.getBearing());
		data.setHasAccuracy(location.hasAccuracy());
		data.setAccuracy(location.getAccuracy());
		data.setExtras(location.getExtras().toString());
		return data;
	}
	public static LocationData create(Cursor cursor) {
		LocationData data = new LocationData();
		data.setRowid(cursor.getLong(0));
		data.setRouteId(cursor.getLong(1));
		data.setProvider(cursor.getString(2));
		data.setTime(cursor.getLong(3));
		data.setLatitude(cursor.getDouble(4));
		data.setLongitude(cursor.getDouble(5));
		data.setHasAltitude(cursor.getString(6).equals("true"));
		data.setAltitude(cursor.getDouble(7));
		data.setHasSpeed(cursor.getString(8).equals("true"));
		data.setSpeed(cursor.getFloat(9));
		data.setHasBearing(cursor.getString(10).equals("true"));
		data.setBearing(cursor.getFloat(11));
		data.setHasAccuracy(cursor.getString(12).equals("true"));
		data.setAccuracy(cursor.getFloat(13));
		data.setExtras(cursor.getString(14));
		return data;
	}

}
