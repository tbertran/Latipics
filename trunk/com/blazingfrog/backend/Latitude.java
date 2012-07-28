package com.blazingfrog.backend;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

import com.blazingfrog.exceptions.NoLatitudeInfoFoundException;
import com.blazingfrog.exceptions.ProfileNotAuthorizedException;
import com.blazingfrog.latitude.LocationFeed;
import com.blazingfrog.misc.ConsoleFile;
import com.blazingfrog.misc.DefaultOptions;
import com.blazingfrog.oauth.OAuthMain;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;



public class Latitude {

	private static final int MAX_TRIES = 5;
	private static final String LOC_HISTORY_URL = "https://www.googleapis.com/latitude/v1/location";
	private double longit;
	private double latit;
	private Long picTimestamp;
   	private String token, tokenSecret;
	
	//public Latitude(long picTimestamp, String token, String tokenSecret, String picTimeMin, String picTimeMax) throws IOException, NoLatitudeInfoFoundException, ProfileNotAuthorizedException{
	public Latitude(long picTimestamp, String token, String tokenSecret) throws IOException, NoLatitudeInfoFoundException, ProfileNotAuthorizedException{
		this.picTimestamp = picTimestamp;
    	this.token = token;
    	this.tokenSecret = tokenSecret;
//    	this.picTimeMin = picTimeMin;
//    	this.picTimeMax = picTimeMax;
    	
    	getLocationHistory();
	}
	
	public double getLongitude() {
		return longit;
	}

	public double getLatitude() {
		return latit;
	}

	private void getLocationHistory() throws IOException, NoLatitudeInfoFoundException, ProfileNotAuthorizedException{
		
	    String responseMsg = null;
	    // Create a Jersey client
        Client client = Client.create();
        
		OAuthParameters params = new OAuthParameters().signatureMethod("HMAC-SHA1").
	    consumerKey(OAuthMain.CONSUMER_KEY).version("1.0");

		OAuthSecrets secrets = new OAuthSecrets().consumerSecret(OAuthMain.CONSUMER_SECRET);
	
	     // use the access token id and secret to create the request
        secrets.setTokenSecret(tokenSecret);
        params.token(token).timestamp().nonce();
        
        
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        
        queryParams.add("granularity", "best");
        //System.out.println("Pic timestamp: " + picTimestamp);
        Long picTimeMin = picTimestamp - (Long.parseLong(DefaultOptions.getMinPicTime().trim()) * 60000); // from Min to Ms
        picTimestamp += (Long.parseLong(DefaultOptions.getMaxPicTime().trim()) * 60000); // from Min to Ms
        
        //System.out.println("Range from: " + picTimeMin.toString());
        //System.out.println("Range to:   " + picTimestamp.toString());
        queryParams.add("min-time", picTimeMin.toString());
        queryParams.add("max-time", picTimestamp.toString());
        queryParams.add("max-results", "1");
		WebResource webResource = client.resource(LOC_HISTORY_URL).queryParams(queryParams);
		
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);
		webResource.addFilter(filter);
		responseMsg = null;
     	for (int idx=1;idx < MAX_TRIES;idx++){
     		try{
     			responseMsg = webResource.queryParams(queryParams).get(String.class);
     		}
     		catch (UniformInterfaceException e)
     		{
     			ConsoleFile.write("WARNING", "Google servers timed out, will try " + (MAX_TRIES - idx) + " more times.");
     			continue;
     		}
     		break;
     	}
     		
     	//System.out.println(responseMsg);
		if (responseMsg == null)
			throw new ProfileNotAuthorizedException();
		
		if (responseMsg.indexOf("items") == -1)
			throw new NoLatitudeInfoFoundException();
		
		Gson gson = new Gson();
		LocationFeed locationArray = gson.fromJson(responseMsg, LocationFeed.class);
		longit = locationArray.getData().getLocationList()[0].getLongitude();
		latit = locationArray.getData().getLocationList()[0].getLatitude();		
	}
	
}
