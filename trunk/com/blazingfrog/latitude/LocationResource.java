
package com.blazingfrog.latitude;

public class LocationResource {
	private String kind, timestampMs;
    private double latitude, longitude;
    private int accuracy;
    private int speed, heading, altitude, altitudeAccuracy;
    private String placeid;
    
    public LocationResource(){}
    
    public String getKind(){
    	return kind;
    }
    
    public String getTimestampMs(){
    	return timestampMs;
    }

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getSpeed() {
		return speed;
	}

	public int getHeading() {
		return heading;
	}

	public int getAltitude() {
		return altitude;
	}

	public int getAltitudeAccuracy() {
		return altitudeAccuracy;
	}

	public String getPlaceid() {
		return placeid;
	}
}
