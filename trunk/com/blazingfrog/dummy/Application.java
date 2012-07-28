/*
 * This class is a dummy class to allow for compilation on Windows platform
 * since that JRE doesn't have com.apple.eawt.Application
 */
package com.blazingfrog.dummy;

public class Application {
	public static Application getApplication(){
		return new Application();
	}
	public static void addApplicationListener(ApplicationAdapter a){
		
	}

	public static void setEnabledPreferencesMenu(boolean b){
		
	}
	
	public static boolean getEnabledPreferencesMenu(){
		return true;
	}
}
