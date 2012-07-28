package com.blazingfrog.misc;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.UIManager;


public class Resources {
	private static final String APP_NAME = "LatiPics";
	private static final String WORKING_DIR = getWorkingDirectory().toString() + "/";
	private static final String RESOURCE_DIR = getResourcesDirectory().toString() + "/";
	
	private static final String LOG_URL = "file:" + RESOURCE_DIR + "consoleFile.html";
	private static final String LOG_FILE_PATH = RESOURCE_DIR + "consoleFile.html"; 
	private static final File IPHOTO_TMP_DIR = new File(WORKING_DIR + "iPhoto Library");
	
	private static final String LP_PREF_LOGO = "/resources/LatipicsLogoAlphaMed2Blur.png";
	private static final String LP_ICON_64 = "/resources/LatiPicsIcon64.png";
	private static final String LP_LOGO_128 = "/resources/LatipicsLogoAlpha128.png";
	private static final String BF_MAIN_FROG_MAC = "/resources/frog-more-transp.jpg";
	private static final String BF_MAIN_FROG_WIN = "/resources/frog-more-transpWindows.jpg";
	private static final String LP_NOT_FOUND_LOGO = "/resources/LatipicsLogoAlphaSmallGray.png";
	private static final String IPHOTO_CROSSHAIR = "/resources/crosshairAplha.png";
	private static final String LP_CUST_ATTN_ICON = "/resources/CustAttnIcon60.png";
	private static final Color WIN_THEME_COLOR = new Color(252,253,255); //238,238,238);
	private static final String MAC_PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private static final String WIN_PREFERRED_LOOK_AND_FEEL = UIManager.getSystemLookAndFeelClassName();
	private static String OS_NAME;
	private static File userDirectory = new File(System.getProperty("user.home", ".")); // default

	public static File getiPhotoTmpDir() {
		return IPHOTO_TMP_DIR;
	}
	public static String getLookAndFeel(){
		if (Resources.getOSName().equals("mac"))
			return MAC_PREFERRED_LOOK_AND_FEEL;
		else
			return WIN_PREFERRED_LOOK_AND_FEEL;
	}
	public static String getOSName(){
		return OS_NAME;
	}
	public static Color getWinThemeColor(){
		return WIN_THEME_COLOR;
	}
	public static ImageIcon getCustAttnIcon() {
		return new ImageIcon(Resources.class.getResource(LP_CUST_ATTN_ICON));
	}
	public static ImageIcon getIPhotoCrosshair() {
		return new ImageIcon(Resources.class.getResource(IPHOTO_CROSSHAIR));
	}
	public static ImageIcon getLpIcon() {
		return new ImageIcon(Resources.class.getResource(LP_ICON_64));
	}
	public static ImageIcon getLpLogo128() {
		return new ImageIcon(Resources.class.getResource(LP_LOGO_128));
	}
	public static String getLogFilePath() {
		return LOG_FILE_PATH;
	}
	public static ImageIcon getLpNotFoundLogo() {
		return new ImageIcon(Resources.class.getResource(LP_NOT_FOUND_LOGO));
	}
	public static String getResourceDir() {
		return RESOURCE_DIR;
	}
	public static ImageIcon getLpPrefLogo() {
		return new ImageIcon(Resources.class.getResource(LP_PREF_LOGO));
	}
	public static ImageIcon getBfMainFrog() {
		if (Resources.getOSName().equals("mac"))
			return new ImageIcon(Resources.class.getResource(BF_MAIN_FROG_MAC));
		else
			return new ImageIcon(Resources.class.getResource(BF_MAIN_FROG_WIN));
	}
	public static String getLogUrl() {
		return LOG_URL;
	}
	public static File getLogFile() {
		return new File(LOG_FILE_PATH);
	}
	public static File getUserDirectory() {
		return userDirectory;
	}
	public static void setUserDirectory(File userDirectory) {
		Resources.userDirectory = userDirectory;
	}
	
	/**
     * Returns the appropriate working directory for storing application data. The result of this method is platform
     * dependant: On linux, it will return ~/applicationName, on windows, the working directory will be located in the
     * user's application data folder. For Mac OS systems, the working directory will be placed in the proper location
     * in "Library/Application Support".
     * <p/>
     * This method will also make sure that the working directory exists. When invoked, the directory and all required
     * subfolders will be created.
     *
     * @param applicationName Name of the application, used to determine the working directory.
     * @return the appropriate working directory for storing application data.
     */
	public static File getWorkingDirectory() {
	    final String userHome = System.getProperty("user.home", ".");
	    File workingDirectory = null;
	    
	    final String sysName = System.getProperty("os.name").toLowerCase();
	    if (sysName.contains("windows")){
	        final String applicationData = System.getenv("APPDATA");
	        OS_NAME = "windows";
	       	if (applicationData != null)
	       		workingDirectory = new File(applicationData, "." + APP_NAME + "/");
	       	else
	       		workingDirectory = new File(userHome, '.' + APP_NAME + "/");
	    	}
	    else if (sysName.contains("mac")){
	    	OS_NAME = "mac";
	    	workingDirectory = new File(userHome, "Library/Application Support/" + APP_NAME);
	    }
	   
	        if (!workingDirectory.exists())
	            if (!workingDirectory.mkdirs())
	                throw new RuntimeException("The working directory could not be created: " + workingDirectory);

	        
	        return workingDirectory;
	}
	
	public static File getResourcesDirectory() {
	    final String userHome = System.getProperty("user.home", ".");
	    File resourcesDirectory = null;
	    
	    final String sysName = System.getProperty("os.name").toLowerCase();
	    if (sysName.contains("windows")){
	        final String applicationData = System.getenv("APPDATA");
	       	if (applicationData != null)
	       		resourcesDirectory = new File(applicationData, "." + APP_NAME + "/Resources/");
	       	else
	       		resourcesDirectory = new File(userHome, '.' + APP_NAME + "/Resources/");
	    	}
	    else if (sysName.contains("mac")){
	    	resourcesDirectory = new File(userHome, "Library/Application Support/" + APP_NAME + "/Resources");
	    }
        
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdirs())
                throw new RuntimeException("The Resources directory could not be created: " + resourcesDirectory);
        
        return resourcesDirectory;
	}
}
