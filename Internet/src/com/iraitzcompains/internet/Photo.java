package com.iraitzcompains.internet;

public class Photo {

	private int id;
	private String name;
	private String image_url;
	
	public Photo() {
		this.id = 0;
		this.name = "";
		this.image_url = "";
	}
	
	public Photo(int id, String name, String image_url) {
		this.id = id;
		this.name = name;
		this.image_url = image_url;
	}
	
	public String getImage_url() {
		return this.image_url;
	}
	
	@Override
	public String toString() {
		super.toString();
		return "id: " + String.valueOf(this.id) + " | name: " + this.name + " | image_url: " + this.image_url;
	}
}
