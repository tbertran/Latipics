package com.blazingfrog.imported;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

import com.blazingfrog.misc.ImageFile;


/* ImagePreview.java by FileChooserDemo2.java. */
public class ImagePreview extends JComponent implements PropertyChangeListener {
 
	int rot;
	private static final long serialVersionUID = 1L;
	ImageIcon thumbnail = null;
    File file = null;

    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(200, 100));
        fc.addPropertyChangeListener(this);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        
        //ImageIcon tmpIcon = new ImageIcon(file.getPath());
        BufferedImage thumb = getThumbnail(file);
        ImageIcon tmpIcon = null;
        if (thumb != null)
        	tmpIcon = new ImageIcon(thumb);
        else
        	tmpIcon = null;
        
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 190) {
                thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(190, -1, Image.SCALE_DEFAULT));
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
        
    }
    
    private BufferedImage getThumbnail(File file){
        IImageMetadata metadata = null;
        JpegImageMetadata jpegMetadata = null;
        TiffImageMetadata exif = null;
        try {
            metadata = Sanselan.getMetadata(file);
        } catch (ImageReadException e) {
			e.printStackTrace();    
			return null;
        } 
        catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        
        if (metadata != null)
        	jpegMetadata = (JpegImageMetadata) metadata;
        
        if (jpegMetadata != null)
        	exif = jpegMetadata.getExif();
        
        try {
        	
        	return new ImageFile(exif).getEXIFThumbnail();
			
		} catch (ImageReadException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
}