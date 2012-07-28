package com.blazingfrog.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class DefaultOptions {

	private static Properties properties = new Properties();
	private static String overrideGPS = new String("no");
	private static String updateLastModified = new String("yes");
	private static String minPicTime = new String("30");
	private static String maxPicTime  = new String("30");
	private static String defaultProfileName  = new String();
	private final static String ROOT_PATH = Resources.getResourceDir();
	private static FileOutputStream propFile_o;
	private static FileInputStream propFile_i;
	private static String file_path = ROOT_PATH + "defaultProfile.xml";
	private static File defProfFile = new File(file_path);

	public static boolean doOverrideGPS() {
		readXML();
		return (overrideGPS.equals("yes")) ? true : false;
	}

	public static void setOverrideGPS(boolean overrideGPS) {
		DefaultOptions.overrideGPS = overrideGPS ? "yes" : "no";
		updateXML();
	}

	public static boolean doUpdateLastModified() {
		readXML();
		return (updateLastModified.equals("yes")) ? true : false;
	}

	public static void setUpdateLastModified(boolean updateLastModified) {
		DefaultOptions.updateLastModified = updateLastModified ? "yes" : "no";
		updateXML();
	}

	public static String getMinPicTime() {
		readXML();
		return minPicTime;
	}

	public static void setMinPicTime(String minPicTime) {
		DefaultOptions.minPicTime = minPicTime;
		updateXML();
	}

	public static String getMaxPicTime() {
		readXML();
		return maxPicTime;
	}

	public static void setMaxPicTime(String maxPicTime) {
		DefaultOptions.maxPicTime = maxPicTime;
		updateXML();
	}

	public static String getDefaultProfileName() {
		readXML();
		return defaultProfileName;
	}

	public static void setDefaultProfileName(String defaultProfileName) {
		DefaultOptions.defaultProfileName = defaultProfileName;
		updateXML();
	}

	private static void updateXML() {
		try {
			if (!defProfFile.exists())
				properties = new Properties();

			propFile_o = new FileOutputStream(defProfFile);
			properties.setProperty("PicTimeMin", minPicTime);
			properties.setProperty("PicTimeMax", maxPicTime);
			properties.setProperty("Profile", defaultProfileName);
			properties.setProperty("OverrideGPS", overrideGPS);
			properties.setProperty("UpdateLastModified", updateLastModified);

			properties.storeToXML(propFile_o, "LatiPics Default Profile",
					"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readXML() {

		if (!defProfFile.exists()) {
			updateXML();
		}

		try {
			propFile_i = new FileInputStream(file_path);
			properties.loadFromXML(propFile_i);

			overrideGPS = properties.getProperty("OverrideGPS");

			// Default value for users that were using the <1.2 versions
			if (overrideGPS == null) {
				overrideGPS = "no";
			}
			updateLastModified = properties.getProperty("UpdateLastModified");
			if (updateLastModified == null) {
				updateLastModified = "yes";
			}
			minPicTime = properties.getProperty("PicTimeMin");
			if (minPicTime == null) {
				minPicTime = "30";
			}
			maxPicTime = properties.getProperty("PicTimeMax");
			if (maxPicTime == null) {
				maxPicTime = "0";
			}
			defaultProfileName = properties.getProperty("Profile");

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
			ConsoleFile.write("WARNING", "Created new DefaultProfile.xml");
			setDefaultProfileName(UserProfile.listProfiles().get(0));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
