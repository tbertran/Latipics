package com.blazingfrog.backend;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.blazingfrog.googlemaps.AddressComponent;
import com.blazingfrog.googlemaps.GoogleMapsFeed;
import com.google.gson.Gson;

public class GoogleMaps {
	private double longit; 
	private double latit;
    private String jSONoutput = new String();
	
	public GoogleMaps(double longit, double latit){
		this.longit = longit;
		this.latit = latit;
	}
	
	public String getPlace() throws IOException {
		
		URL url = new URL("http://maps.google.com/maps/api/geocode/json?latlng=" + latit + "," + longit + "&sensor=false");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    StringBuffer sb = new StringBuffer();
	    String inputLine;
	    
		while ((inputLine = in.readLine()) != null)
			jSONoutput = sb.append(inputLine).append("\n").toString();
		
		in.close();
		return jSONoutput;
		
	}
	
	public String getLocality() throws IOException{
		String locality=null;
		if (jSONoutput.length() == 0)
			jSONoutput = this.getPlace();
		
		Gson gson = new Gson();
		GoogleMapsFeed googleMapsFeed = gson.fromJson(jSONoutput, GoogleMapsFeed.class);
		
		AddressComponent[] addCompArr = googleMapsFeed.getResults()[0].getAddress_components();
		
		for (AddressComponent addComp:addCompArr){
			if (addComp.getTypes()[0].equals("locality"))
			{
				locality = addComp.getLong_name();
				break;
			}
		}
		
		return locality;
	}
}
