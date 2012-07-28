package com.blazingfrog.latitude;


public class LocationList {
	private String kind;
	private LocationResource[] items;
	
	public LocationResource[] getLocationList(){
		return items;
	}

	public String getKind() {
		return kind;
	}
}
