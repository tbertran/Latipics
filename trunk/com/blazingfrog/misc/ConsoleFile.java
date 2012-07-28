package com.blazingfrog.misc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class ConsoleFile {
	
	private static FileWriter consoleFileFW = null; 
	private static PrintWriter consoleFileOutput = null;
	private static File consoleFile; 
	
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static Calendar cal;
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	
	
	public static void create(){
        try {
        	consoleFile = new File(Resources.getLogFilePath());
        	if (consoleFile.exists())
        		consoleFile.delete();  // deleteOnExit failing on windows
        	consoleFile.deleteOnExit();
    		
        	consoleFileFW = new FileWriter (consoleFile, true);
        	consoleFileOutput = new PrintWriter(consoleFileFW, true);
        	
        	consoleFileOutput.println
        	("<CENTER><FONT size=\"6\" face=\"Lucida Grande\" COLOR=\"black\">Latipics Log File</FONT><br><br><br></CENTER>");
        			
		} catch (IOException e) {
			e.printStackTrace(); // nothing
		}

	}
	
	public static void empty(){
		ConsoleFile.delete();
		ConsoleFile.create();
	}
	
	public static File getFile(){
		if (new File(Resources.getLogFilePath()).exists())
			return Resources.getLogFile();
		else
			return null;
	}
	
	public static void delete(){
        try {
        	consoleFileFW.close();
        	consoleFileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		consoleFile.delete();
	}
	
	public static String getURL(){
		return Resources.getLogUrl();
	}
	
	public static long getLength(){
		return consoleFile.length();
	}
	
	public static void writeStart(){
		cal = Calendar.getInstance();
		consoleFileOutput.println("<FONT size=\"4\" face=\"Courier\" COLOR=\"00008B\">" + sdf.format(cal.getTime()) + " - LatiPics started.</FONT><br />");
	}
	
	public static void writeFinish(int nbFiles){
		cal = Calendar.getInstance();
		consoleFileOutput.println("<FONT size=\"4\" face=\"Courier\" COLOR=\"00008B\">" + sdf.format(cal.getTime()) + " - LatiPics finished and processed "  + nbFiles + " pictures.</FONT><br />");
	}
	
	public static void write(String msgType, String line){
		StringBuffer lineSB = new StringBuffer();
		 cal = Calendar.getInstance();
		if (msgType == "ERROR"){
			line = lineSB.append("<FONT size=\"2\" face=\"Courier\" COLOR=\"FF0000\">&nbsp;&nbsp;"). // + sdf.format(cal.getTime()) + " - ").
			append(line).append("</FONT><br />").toString();
		} else {
			if (msgType == "WARNING"){
				line = lineSB.append("<FONT size=\"2\" face=\"Courier\" COLOR=\"808080\">&nbsp;&nbsp;"). // + sdf.format(cal.getTime()) + " - ").
				append(line).append("</FONT><br />").toString();
			} else{
				if (msgType == "NORMAL"){
					line = lineSB.append("<FONT size=\"2\" face=\"Courier\" COLOR=\"00008B\">&nbsp;&nbsp;"). //+ sdf.format(cal.getTime()) + " - ").
					append(line).append("</FONT><br />").toString();
				} else{
					line = lineSB.append("<FONT size=\"2\" face=\"Courier\" COLOR=\"FF0000\">&nbsp;&nbsp;!!!INVALID MESSAGE TYPE!!!"). // + sdf.format(cal.getTime()) + " - ").
					append(line).append("</FONT><br />").toString();
				}
			}
		}
		
		consoleFileOutput.println(line);
	}
	 
}
