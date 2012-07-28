package com.blazingfrog.misc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.formats.tiff.JpegImageData;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

public class ImageFile {
	
	private TiffImageMetadata tiff;
	public ImageFile(TiffImageMetadata tiff){
		this.tiff = tiff;
	}
	
	
	public BufferedImage getEXIFThumbnail() throws ImageReadException,IOException { 
		ArrayList<?> dirs = tiff.getDirectories(); 
		for (int i = 0; i < dirs.size(); i++) { 
			TiffImageMetadata.Directory dir = (TiffImageMetadata.Directory) dirs.get(i); 
			
			// Debug.debug("dir", dir); 
			BufferedImage image = dir.getThumbnail(); 
			if (null != image)
				return image;
		    	//return this.getScaledInstance(image, 160, 106, RenderingHints.KEY_INTERPOLATION, false); //image; 
			                        
			JpegImageData jpegImageData = dir.getJpegImageData(); 
			if(jpegImageData!=null){ 
				ByteArrayInputStream input = new ByteArrayInputStream(jpegImageData.data); 
			    image = ImageIO.read(input); 
			    if (image!=null) 
			    	return image;
			    	//return this.getScaledInstance(image, 160, 110, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false); //image; 
			}	 
		}
		return null; 
	}
	
	

	  /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
	/*
    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
    */
}
