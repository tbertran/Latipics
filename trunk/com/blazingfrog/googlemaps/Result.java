package com.blazingfrog.googlemaps;


public class Result {
	private String[] types;
	private String formatted_address;
	private AddressComponent[] address_components;
	
	public String[] getTypes() {
		return types;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public AddressComponent[] getAddress_components() {
		return address_components;
	}
	
	
}
