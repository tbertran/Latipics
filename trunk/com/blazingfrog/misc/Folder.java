package com.blazingfrog.misc;

import java.io.File;
import java.util.ArrayList;

public class Folder {
	private ArrayList<File> listOfFiles;

	public ArrayList<File> getListOfFiles(File folder){
		listOfFiles = new ArrayList<File>();
		if (folder.isDirectory()) {
            String[] children = folder.list();
            for (int i=0; i<children.length; i++) {
            	File newFile = new File(folder, children[i]);
                listOfFiles.add(newFile);
            }
            return listOfFiles;
        }
		return null;
	} 

}
