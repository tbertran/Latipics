package com.blazingfrog.oauth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.blazingfrog.exceptions.ProfileAlreadyExistsException;
import com.blazingfrog.gui.PrefPane2;
import com.blazingfrog.imported.BareBonesBrowserLaunch;
import com.blazingfrog.misc.ConsoleFile;
import com.blazingfrog.misc.Resources;
import com.blazingfrog.misc.UserProfile;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;


public class OAuthMain {
	
    // base URL for the API calls
    private static final String URL_REQUEST = "https://www.google.com/accounts/OAuthGetRequestToken";
    // authorization URL
    private static final String URL_AUTHORIZE = "https://www.google.com/latitude/apps/OAuthAuthorizeToken";
    // access URL
    private static final String URL_ACCESS = "https://www.google.com/accounts/OAuthGetAccessToken";
    
    public static final String CONSUMER_SECRET = "+kDlLvv7Tyx8VKy+QRKu0HIq";
    public static final String CONSUMER_KEY = "www.blazingfrog.com";
	private static final int MAX_TRIES = 5;
	private String req_response;
	private WebResource resource;
	private OAuthSecrets secrets;
	private OAuthParameters params;
	private OAuthClientFilter filter;
	private String token, secret;
	private String profileName;
	private PrefPane2 prefPane;
    
	public OAuthMain(PrefPane2 prefPane, String profileName) throws IOException, URISyntaxException{
		this.profileName = profileName;
		this.prefPane = prefPane;
		authorize();
	}

    public void authorize() throws IOException  {
        // Create a Jersey client
        Client client = Client.create();

        // ******************************************//
        // ********* REQUEST AUTHORIZATION ********* //
        // ******************************************//
        // Create a resource to be used to make SmugMug API calls
        resource = client.resource(URL_REQUEST)
                .queryParam("scope", "https://www.googleapis.com/auth/latitude");

        // Set the OAuth parameters
        secrets = new OAuthSecrets().consumerSecret(CONSUMER_SECRET);
        params = new OAuthParameters().consumerKey(CONSUMER_KEY).nonce().
                signatureMethod("HMAC-SHA1").timestamp().version("1.0")
        //.callback("oob")
        .callback("http://www.blazingfrog.com/latipics/bf-latipics-google.html");
        
        // Create the OAuth client filter
        filter = new OAuthClientFilter(client.getProviders(), params, secrets);
        // Add the filter to the resource
        resource.addFilter(filter);
        
        // make the request
     	for (int idx=1;idx < MAX_TRIES;idx++){
     		try{
     	        req_response = resource.get(String.class);
     		}
     		catch (UniformInterfaceException e)
     		{
     			ConsoleFile.write("WARNING", URL_REQUEST + " - Google servers timed out, will try " + (MAX_TRIES - idx) + " more times.");
     			continue;
     		}
     		break;
     	}

        String delims = "[=&]";
        String[] response_tokens = req_response.split(delims);
        token = URLDecoder.decode(response_tokens[1],"UTF-8");
        secret = URLDecoder.decode(response_tokens[3],"UTF-8");
        //System.out.println("\nBEFORE\nReq token " + req_token + "\nreq secret " + req_secret);

        // ****************************************//
        // ********* GET AUTHORIZE TOKEN ********* //
        // ****************************************//

        BareBonesBrowserLaunch.openURL(URL_AUTHORIZE + "?hd=default&domain=" + CONSUMER_KEY + "&location=all&granularity=best&oauth_token=" + token);
        String msg = null;
        if (Resources.getOSName().equals("mac"))
        	msg = "Google requests your authorization, your browser will now open. When access has been granted, please copy/paste the verification code here and click OK.";
        else 
        	msg = "<html><head></head><body><font size = 3><CENTER>Google now requests that you grant your authorization to LatiPics.<br><b>Your browser will now open</b>.<br><br>When access has been granted, please copy/paste the verification<br>code here and click OK.</CENTER></font></body></html>";
        
     	String auth_verifier = 
     			(String)JOptionPane.showInputDialog(
     			prefPane,
                msg,
                "Latitude Access Verification",
                JOptionPane.WARNING_MESSAGE,
                Resources.getLpIcon(),
                null,
                "");
     	if (auth_verifier == null || auth_verifier.length() == 0)
     		return;
     	
     	auth_verifier = URLDecoder.decode(auth_verifier,"UTF-8"); 
		
        // *************************************//
        // ********* GET ACCESS TOKEN ********* //
        // *************************************//
        
        // Create a resource to be used to make SmugMug API calls
        resource = client.resource(URL_ACCESS);
        
        // Set the OAuth parameters
        secrets = new OAuthSecrets().tokenSecret(secret).consumerSecret(CONSUMER_SECRET);
        params = new OAuthParameters().consumerKey(CONSUMER_KEY).token(token).verifier(auth_verifier).
        signatureMethod("HMAC-SHA1").timestamp().nonce().version("1.0");

        filter = new OAuthClientFilter(client.getProviders(), params, secrets);
        resource.addFilter(filter);
        
        String acc_response = null;
     	for (int idx=1;idx < MAX_TRIES;idx++){
     		try{
     			//System.out.println("resource URI: " + resource.getURI().to);
     	        acc_response = resource.get(String.class);
     		}
     		catch (UniformInterfaceException e)
     		{
     			ConsoleFile.write("WARNING", URL_ACCESS + " - Google servers timed out, will try " + (MAX_TRIES - idx) + " more times.");
     			continue;
     		}
     		break;
     	}
     	
        response_tokens = acc_response.split(delims);
        token = URLDecoder.decode(response_tokens[1],"UTF-8");
        secret = URLDecoder.decode(response_tokens[3],"UTF-8");
        
        // Store the profile

     	try {
			UserProfile.add(profileName, token, secret);
		} catch (ProfileAlreadyExistsException e) {
			JOptionPane.showMessageDialog(prefPane, "\"" + profileName + "\" already exists.");
		}
    }
}