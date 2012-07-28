package com.blazingfrog.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.blazingfrog.exceptions.ProfileAlreadyExistsException;


public class UserProfile {
	private static Properties properties = new Properties();
	private static FileOutputStream propFile_o;
	private static FileInputStream propFile_i;
	private final static String ROOT_PATH = Resources.getResourceDir(); //"WebContent/WEB-INF/";
	private static String file_path;
	
	public UserProfile() {
		properties = new Properties();
	}
	
	public static boolean exists(String profileName){
		file_path = ROOT_PATH + profileName + "$prof.xml";
		if (new File(file_path).exists())
			return true;
		return false;
	}
	
	public static void add(String profileName, String token, String tokenSecret) throws ProfileAlreadyExistsException, IOException{
		if (exists(profileName))
			throw new ProfileAlreadyExistsException();
		properties = new Properties();
		propFile_o = new FileOutputStream(ROOT_PATH + profileName + "$prof.xml");
		properties.setProperty("AccessToken", token);
		properties.setProperty("TokenSecret", tokenSecret);
		properties.storeToXML(propFile_o, "LatiPics Profiles", "UTF-8");
		
		if (listProfiles().size() == 1)
			DefaultOptions.setDefaultProfileName(profileName);
	}

	
    private static void read() throws InvalidPropertiesFormatException, IOException {
        propFile_i = new FileInputStream(file_path);
        properties.loadFromXML(propFile_i);
    }
    
	public static String getToken(String profileName) throws InvalidPropertiesFormatException, IOException{
		file_path = ROOT_PATH + profileName + "$prof.xml";
		read();
		return properties.getProperty("AccessToken");
	}
	
	public static String getTokenSecret(String profileName) throws InvalidPropertiesFormatException, IOException{
		file_path = ROOT_PATH + profileName + "$prof.xml";
		read();
		return properties.getProperty("TokenSecret");
	}
	
//	public static String getPicMinTime() throws InvalidPropertiesFormatException, IOException{
//		file_path = ROOT_PATH + "defaultProfile.xml";
//		read();
//		return properties.getProperty("PicTimeMin");
//	}
//
//	public static String getPicMaxTime() throws InvalidPropertiesFormatException, IOException{
//		file_path = ROOT_PATH + "defaultProfile.xml";
//		read();
//		return properties.getProperty("PicTimeMax");
//	}
	
	
	public static ArrayList<String> listProfiles(){
        String delim = "[$]";
        ArrayList<String> list = new ArrayList<String>();
        
    	ArrayList<File> listOfFiles = new Folder().getListOfFiles(new File(ROOT_PATH));
	    for (File file:listOfFiles) {
	    	String[] filesList = file.getName().split(delim);
	        if (filesList.length > 1 && filesList[1].equals("prof.xml")){
	        		list.add(filesList[0]);
	        }
	    }
	    return list;
	}
	
//	
//	public static void setDefault(String profileName) throws IOException {
//		File defProfFile = new File(ROOT_PATH + "defaultProfile.xml");
//		if (!defProfFile.exists())
//		{
//			//defProfFile.delete();
//			properties = new Properties();
//		}
//		else{
//			read();
//		}
//		propFile_o = new FileOutputStream(defProfFile);
//		properties.setProperty("Profile", profileName);
//		properties.storeToXML(propFile_o, "LatiPics Default Profile", "UTF-8");
//	}
//	
	
//	public static void setMinMaxTimes(String picTimeMin, String picTimeMax) throws IOException {
//		File defProfFile = new File(ROOT_PATH + "defaultProfile.xml");
//		if (!defProfFile.exists())
//		{
//			properties = new Properties();
//		}
//		else{
//			read();
//		}
//		propFile_o = new FileOutputStream(defProfFile);
//		properties.setProperty("PicTimeMin", picTimeMin);
//		properties.setProperty("PicTimeMax", picTimeMax);
//		properties.storeToXML(propFile_o, "LatiPics Default Profile", "UTF-8");
//	}
	
	
//	public static String getDefault() throws IOException {
//		file_path = ROOT_PATH + "defaultProfile.xml";
//		try {
//			read();
//		} catch (InvalidPropertiesFormatException e) {
//			ConsoleFile.write("WARNING", "Created new DefaultProfile.xml");
//			System.out.println("Created new DefaultProfile.xml");
//			setDefault(listProfiles().get(0));
//		}
//		return properties.getProperty("Profile");
//	}
}
