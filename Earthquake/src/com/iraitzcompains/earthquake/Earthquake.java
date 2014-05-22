package com.iraitzcompains.earthquake;

import java.io.Serializable;

import android.text.format.DateFormat;

public class Earthquake implements Serializable {

	private static final long serialVersionUID = 4983844022863032964L;
	
	private int _id;
	private String idStr;
	private String place;
	private long time;
	private String detail;
	private double magnitude;
	private double lat;
	private double lon;
	private String url;
	private long create_at;
	private long update_at;

	public Earthquake(int _id, String idStr, String place, long time,
			String detail, double magnitude, double lat, double lon, String url) {
		this(idStr, place, time, detail, magnitude, lat, lon, url);
		this._id = _id;
	}

	public Earthquake(String idStr, String place, long time, String detail,
			double magnitude, double lat, double lon, String url) {
		this.idStr = idStr;
		this.place = place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.lat = lat;
		this.lon = lon;
		this.url = url;
	}

	public int get_id() {
		return _id;
	}

	public String getIdStr() {
		return idStr;
	}

	public String getPlace() {
		return place;
	}

	public long getTime() {
		return time;
	}

	public String getDetail() {
		return detail;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public String getUrl() {
		return url;
	}

	public long getCreate_at() {
		return create_at;
	}

	public long getUpdate_at() {
		return update_at;
	}

	@Override
	public String toString() {
		String str = String.valueOf(getMagnitude()) + " " + getPlace() + " " + DateFormat.format("yyyy-MM-dd hh:mm:ss", this.getTime());
		return str;
	}
}
