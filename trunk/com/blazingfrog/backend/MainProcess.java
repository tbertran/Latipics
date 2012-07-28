package com.blazingfrog.backend;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

import com.blazingfrog.exceptions.NoLatitudeInfoFoundException;
import com.blazingfrog.exceptions.ProfileNotAuthorizedException;
import com.blazingfrog.gui.PrefPaneCaller;
import com.blazingfrog.imported.RotatedJLabel;
import com.blazingfrog.misc.ConsoleFile;
import com.blazingfrog.misc.DefaultOptions;
import com.blazingfrog.misc.Folder;
import com.blazingfrog.misc.ImageFile;
import com.blazingfrog.misc.Resources;





public class MainProcess implements Runnable{ 
	private ArrayList<File> fileList;
	private RotatedJLabel imgLabel;
	private int nbPicsProcessed;
	private String picName;
	private boolean stopProcess;
	private String locality;
	private JLabel consoleLabel;
   	private boolean success = false;
   	private String token, tokenSecret;
   	private int nbUpdtPics;
    
    public String getLocality() {
		return locality;
	}

	public void setStopProcess(boolean stopProcess) {
		this.stopProcess = stopProcess;
	}

	public String getPicName() {
		return picName;
	}

	public MainProcess(ArrayList<File> fileList, RotatedJLabel imgLabel, JLabel consoleLabel, String token, String tokenSecret) {
    	this.fileList = fileList;
    	this.imgLabel = imgLabel;
    	this.consoleLabel = consoleLabel;
    	this.token = token;
    	this.tokenSecret = tokenSecret;
	}

    public void run(){
    		File file1 = fileList.get(0);
			ConsoleFile.writeStart();
    		if(file1.isDirectory()) {
	    		try {
					this.exploreFileList(new Folder().getListOfFiles(file1));
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ImageWriteException e) {
					e.printStackTrace();
				}
    		}
    		else{
    			try {
					this.exploreFileList(fileList);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ImageWriteException e) {
					e.printStackTrace();
				}
    		}
    		imgLabel.setIcon(Resources.getIPhotoCrosshair());
    		imgLabel.setRotation(0);
    		nbPicsProcessed = 0;
    }

    
	public void exploreFileList(ArrayList<File> fileList) throws IOException, ImageWriteException {
    	try {
	    	for (File picFile:fileList) {
	    		if (picFile.isFile()){
	    			
		    		picName = picFile.getName();
		    		this.processGPSInfo(picFile);
		    		
	    			if (!success){
	    				nbPicsProcessed++;
	    				Thread.sleep(750);
	    			}
	    			
		    		if (stopProcess)
		    			break;
	    		}
    		}
    	} 
    	catch (ImageReadException e) {
            e.printStackTrace();
        }
    	catch (InterruptedException e) {
			e.printStackTrace();
		}

    	ConsoleFile.writeFinish(nbUpdtPics);
    }
    
    @SuppressWarnings("unused")
	private void processGPSInfo(File file) throws ImageReadException, IOException, ImageWriteException{
        File dst = null;
        IImageMetadata metadata = null;
        JpegImageMetadata jpegMetadata = null;
        TiffImageMetadata exif = null;
        OutputStream os = null;
        TiffOutputSet outputSet = new TiffOutputSet();
    	long picTimestamp = 0;
    	BufferedImage pic = null;
    	success = false;
    	
        // establish metadata
        try {
            metadata = Sanselan.getMetadata(file);
        } catch (ImageReadException e) {
        	ConsoleFile.write("ERROR", file.getPath() + ": File is not an image (or format is not supported). SKIPPED.");
       		consoleLabel.setText(file.getName() + ": File is not an image. Skipped.");
       		consoleLabel.setForeground(Color.RED);
       		imgLabel.setRotation(0);
       		imgLabel.setIcon(Resources.getLpNotFoundLogo());
        	return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // establish jpegMedatadata
        if (metadata == null) {
        	ConsoleFile.write("ERROR", file.getPath() + ": Metadata not found. SKIPPED.");
       		consoleLabel.setText(file.getName() + ": Metadata not found. Picture cannot be processed. Skipped.");
       		consoleLabel.setForeground(Color.RED);
       		imgLabel.setRotation(0);
       		imgLabel.setIcon(Resources.getLpNotFoundLogo());
        	return;
        }
        else
        {
            jpegMetadata = (JpegImageMetadata) metadata;
            	

        }

       	int rot = 0;
        // establish exif
        if (jpegMetadata == null) {
        	ConsoleFile.write("ERROR",file.getPath() + ": JPEG METADATA not found. SKIPPED.");
       		consoleLabel.setText(file.getName() + ": JPEG Metadata not found. Picture cannot be processed. Skipped.");
       		consoleLabel.setForeground(Color.RED);
       		imgLabel.setRotation(0);
       		imgLabel.setIcon(Resources.getLpNotFoundLogo());
        	return;
        }
        else
        {
            exif = jpegMetadata.getExif();
            
        	// get thumbnail
	        pic = new ImageFile(exif).getEXIFThumbnail(); 
	        
			// handle thumbnail rotation
	        if (pic != null && jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_ORIENTATION) != null){
				switch(jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_ORIENTATION ).getIntValue()){
					case 3: rot = 180;break;
					case 4: rot = 180;break;
					case 5: rot = 90;break;
					case 6: rot = 90;break;
					case 7: rot = 270;break;
					case 8: rot = 270;break;
				}
			}
        }


        // establish outputSet
        if (exif == null) {
        	ConsoleFile.write("ERROR", file.getPath() + " : EXIF not found. SKIPPED.");
       		consoleLabel.setText(file.getName() + ": Picture cannot be processed.");
       		consoleLabel.setForeground(Color.RED);
       		imgLabel.setRotation(0);
       		imgLabel.setIcon(Resources.getLpNotFoundLogo());
        	return;
        }
        else
        {
            try {
                outputSet = exif.getOutputSet();
                /*
                System.out.println("tostring: " + outputSet);
                System.out.println("long ref: " + jpegMetadata.findEXIFValue(TiffConstants.GPS_TAG_GPS_LONGITUDE_REF).getStringValue());
                System.out.println("long: " + jpegMetadata.findEXIFValue(TiffConstants.GPS_TAG_GPS_LONGITUDE).toString());
                System.out.println("lat ref: " + jpegMetadata.findEXIFValue(TiffConstants.GPS_TAG_GPS_LATITUDE_REF).getStringValue());
                System.out.println("lat: " + jpegMetadata.findEXIFValue(TiffConstants.GPS_TAG_GPS_LATITUDE).toString());
                */
            } catch (ImageWriteException e) {
                e.printStackTrace();
            }
        }

        if (outputSet == null) {
        	ConsoleFile.write("ERROR", file.getPath() + " : OUTPUT SET not found. SKIPPED.");
       		consoleLabel.setText(file.getName() + ": Picture cannot be processed.");
        	if (pic == null){
	       		consoleLabel.setForeground(Color.RED);
	       		imgLabel.setRotation(0);
	       		imgLabel.setIcon(Resources.getLpNotFoundLogo());
        	}
        	return;
        }
        else
        {
            if (outputSet.getGPSDirectory() != null && !DefaultOptions.doOverrideGPS()) { 
            	ConsoleFile.write("WARNING",  file.getPath() + " : GPS info already present. SKIPPED.");
           		consoleLabel.setText(file.getName() + ": GPS info already present. Picture was skipped.");
           		consoleLabel.setForeground(Color.DARK_GRAY);
            	
				if (pic != null){
					imgLabel.setRotation(rot);
					imgLabel.setIcon(new ImageIcon(pic));
				}
				else{
					imgLabel.setIcon(Resources.getLpNotFoundLogo());
					imgLabel.setRotation(0);
				}
				return;
            }
            
            TiffField tiffField = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
			

            if (tiffField == null){
           		ConsoleFile.write("ERROR",  file.getPath() + " : No Original Date tag was found, please populate and try again. SKIPPED.");
           		imgLabel.setRotation(0);
           		imgLabel.setIcon(Resources.getLpNotFoundLogo());
           		consoleLabel.setForeground(Color.RED);
           		consoleLabel.setText(file.getName() + ": No Original Date found. Fix and try again. SKIPPED.");
           		return;
            }


			
            Date date = null;
         	try {
         			date = new SimpleDateFormat ("yyyy:MM:dd HH:mm:ss").parse(tiffField.getStringValue());
	         		Calendar cal = new GregorianCalendar();
	         		cal.setTime(date);
	         		picTimestamp = cal.getTimeInMillis();
               	} catch (ParseException e) {
               		ConsoleFile.write("ERROR", " : ERROR while parsing EXIF_TAG_DATE_TIME_ORIGINAL " + file.getPath() + ". SKIPPED.");
               		imgLabel.setRotation(0);
	           		imgLabel.setIcon(Resources.getLpNotFoundLogo());
	    			consoleLabel.setForeground(Color.RED);
               		consoleLabel.setText(file.getName() + ": Picture cannot be processed.");
               		return;
               	}
				
               	Latitude latitude = null;
				try {
					latitude = new Latitude(picTimestamp, token, tokenSecret);
				} catch (NoLatitudeInfoFoundException e) {
	    			consoleLabel.setForeground(Color.DARK_GRAY);
					ConsoleFile.write("WARNING", file.getPath() + " : No Latitude history found for timestamp: " + date + ". SKIPPED");
					consoleLabel.setText(file.getName() + ": No Latitude history found for timestamp: " + date);
					
					if (pic != null){
						imgLabel.setRotation(rot);
						imgLabel.setIcon(new ImageIcon(pic));
					}
					else{
						imgLabel.setIcon(Resources.getLpNotFoundLogo());
						imgLabel.setRotation(0);
					}
					return;
				}
				catch (ProfileNotAuthorizedException e) {
					Object[] options = {"Go To Preferences", "Cancel"};
					int rc = JOptionPane.showOptionDialog(new JFrame(), 
					"<html><font face=\"Lucida Grande\" size=\"3\"><b>The Google ID linked to profile <b>\"" + DefaultOptions.getDefaultProfileName() + 
					"\"</b> doesn't seem to have granted LatiPics access to your Latitude history.</b><br><br>You can create a new profile or one with the same name in the Preferences.</font></html>",
					"Profile Not Authorized",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE,
					Resources.getCustAttnIcon(),     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]); //default button title
					
					if (rc == 0){
						new PrefPaneCaller();
					}
					stopProcess = true;
					return;
				}

               	success = true;
				locality = new GoogleMaps(latitude.getLongitude() , latitude.getLatitude()).getLocality();
				
			
				nbPicsProcessed++;
		        if (pic != null){
		        	imgLabel.setRotation(rot);
		           	imgLabel.setIcon(new ImageIcon(pic));
		           	
					consoleLabel.setText(file.getName() + ": Updating the location with \"" + locality + "\"");
					consoleLabel.setForeground(Color.black);
		        } else{
		           	imgLabel.setIcon(Resources.getLpNotFoundLogo());
		           	imgLabel.setRotation(0);
					consoleLabel.setForeground(Color.DARK_GRAY);
		           	consoleLabel.setText(file.getName() + ": Updating the location with " + locality + " (no thumbnail available)");
		           	ConsoleFile.write("WARNING",  file.getPath() + ": No thumbnail found.");
		        }

		        // TEST!!!!!!!!!!!!!!!!!!
				outputSet.setGPSInDegrees(latitude.getLongitude(), latitude.getLatitude());
		        nbUpdtPics++;
				ConsoleFile.write("NORMAL", file.getPath() + " : GPS info updated to " + 
						latitude.getLongitude() + " / " +
						latitude.getLatitude());
           
        }
        
        // Save last modified date
        long lastModified = file.lastModified();
        
        // create stream using temp file for dst
        try {
            dst = File.createTempFile("temp-" + System.currentTimeMillis(), ".jpeg");
            os = new FileOutputStream(dst);
            os = new BufferedOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write/update EXIF metadata to output stream
        try {
            new ExifRewriter().updateExifMetadataLossless(file,
                os, outputSet);
        } 	catch (ImageReadException e) {e.printStackTrace();} 
        	catch (ImageWriteException e) {e.printStackTrace();} 
        	catch (IOException e) {e.printStackTrace();} 
        	finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
        
        // copy temp file over original file
        try {
            FileUtils.copyFile(dst, file);
            if (!DefaultOptions.doUpdateLastModified()){
            	file.setLastModified(lastModified);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public int getNbPicsProcessed(){
    	return nbPicsProcessed;
    }
}