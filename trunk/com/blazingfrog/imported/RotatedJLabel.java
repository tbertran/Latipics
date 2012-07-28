package com.blazingfrog.imported;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JLabel;


/**
* This class implements a JLabel variant where the
* content of the label is rotated by a specified
* angle.  The size of the label is automatically
* adjusted to accommodate the rotated size of the
* content.   The key to doing this is to use
* an AffineTransform to modify the Graphics2D
* object used for painting the content, and to
* override the getSize() method to return a
* rotated size.   Note that this implementation is
* not as efficient as it could be, because it
* recalculates some stuff that it could cache.
* Also, this implementation depends on a special
* behavior of its superclass JLabel - the JLabel
* getPreferredSize() method returns the bounding
* box for the content (text, icon, or both).
* If JLabel did not do this, then we would need to
* do it in this code, which would make this class
* much longer.
*  
* @author nziring
*/

public class RotatedJLabel 
  extends JLabel 
  implements PropertyChangeListener
{
  private static final long serialVersionUID = 1L;
  double angle;
  AffineTransform xform;
  Rectangle2D rbb;
  boolean rvalid = false;
  boolean wantRotatedSize = true;
  
  /**
   * Initialize the rotation support.  This would
   * normally be called exactly once, from a 
   * constructor.
   */
  private void initRotation() {
     recomputeTransform(0.0, super.getSize());
     addPropertyChangeListener(this);
  }
  
  /**
   * Compute the relevant affine transform.
   */
  private Dimension recomputeTransform(double newAngle, Dimension baseSize) {
     angle = newAngle;
     xform = new AffineTransform();
  
     // 1. start with the unrotated size
     Dimension urs;
     urs = baseSize;
     
     // 2. Compute the rotation
     xform.rotate(Math.PI * angle / 180.0, 0,0);
     
     // 3. Get the bounding box and transform it
     Rectangle2D bb;
     bb = new Rectangle2D.Double(0, 0, urs.width, urs.height);      
     Shape rbbs = xform.createTransformedShape(bb);
     
     // 4. Get bounds of the transformed shape to get the new bounds
     rbb = rbbs.getBounds2D();
     
     // 5. Add a pre-translation that ensures the drawing fits in the box
     AffineTransform prexlate;
     prexlate = new AffineTransform();
     prexlate.translate(- rbb.getX(), - rbb.getY());
     xform.preConcatenate(prexlate);
     
     // 6. If we have something reasonable, then rotation is valid
     if (rbb.getWidth() != 0.0) rvalid = true;

     // 7. Compute a new size to return
     Dimension ns = new Dimension((int) Math.ceil(rbb.getWidth()), 
           (int) Math.ceil(rbb.getHeight()));
     
     return ns;
  }

  /**
   * Return true if both superclass is valid and our local
   * rotational computations are valid.
   */
  @Override
  public boolean isValid() {
     boolean ret = rvalid && super.isValid();
     //System.err.println("isValid called, returning " + ret);
     return ret;
  }
  
  
  /**
   * 
   */
  public RotatedJLabel() {
     this("Label");
  }

  /**
   * Create a RotatedJLabel with a rotation of 0
   * and a given text content.
   *  
   * @param text
   */
  public RotatedJLabel(String text) {
     super(text);
     initRotation();
  }

  /**
   * @param image
   */
  public RotatedJLabel(Icon image) {
     super(image);
     initRotation();
  }

  /**
   * @param text
   * @param horizontalAlignment
   */
  public RotatedJLabel(String text, int horizontalAlignment) {
     super(text, horizontalAlignment);
     initRotation();
  }

  /**
   * @param image
   * @param horizontalAlignment
   */
  public RotatedJLabel(Icon image, int horizontalAlignment) {
     super(image, horizontalAlignment);
     initRotation();
  }

  /**
   * @param text
   * @param icon
   * @param horizontalAlignment
   */
  public RotatedJLabel(String text, Icon icon, int horizontalAlignment) {
     super(text, icon, horizontalAlignment);
     initRotation();
  }

  /**
   * Override getSize() to return the rotated size.
   */
  @Override
  public Dimension getSize() {
     Dimension ns = super.getSize();
     Dimension ps = super.getPreferredSize();
     //System.err.println("In getSize, returning ns=" + ns + " but could possibly return ps=" + ps);
     if (wantRotatedSize)
        return ns;
     else
        return ps;
  }
  
  
  /**
   * Override getPreferredSize() to tell our container
   * how much space we need.
   */
  @Override
  public Dimension getPreferredSize() {
     //System.err.println("getPreferredSize called!");
     Dimension ps = recomputeTransform(angle, super.getPreferredSize());
     return ps;
  }
  
  @Override
  public int getWidth() {
     return getSize().width;
  }
  
  @Override 
  public int getHeight() {
     return getSize().height;
  }
  
  /**
   * Override the paintComponent method.  This method
   * gets called when Swing needs to paint the body
   * of the label.  It receives a Graphics2D, which we
   * copy, and then apply the affine transform to the
   * copy, and then call the JLabel paintComponent
   * method with the transformed copy.  Will it work?
   */
  @Override
  protected void paintComponent(Graphics g) {
     Graphics2D rg = (Graphics2D) g.create();      
     rg.transform(xform);
     wantRotatedSize = false;
     super.paintComponent(rg);
     wantRotatedSize = true;
  }

  
  /**
   * Set the rotation angle of this RotatedJLabel.
   */
  public void setRotation(double a) {
     double oldangle = angle;
     angle = a;
     if (angle != oldangle) {
        rvalid = false;
        firePropertyChange("ROTATION", oldangle, angle);
     }
  }

  private boolean ignoreChange = false;
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
     if (ignoreChange) return;
     Dimension newsize = recomputeTransform(angle, super.getPreferredSize());
     ignoreChange = true;
     setSize(newsize);
     ignoreChange = false;
  }

}  