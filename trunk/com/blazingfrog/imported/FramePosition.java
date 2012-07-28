package com.blazingfrog.imported;
/* 
 * JCommon : a free general purpose class library for the Java(tm) platform
 * 
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ----------------------
 * RefineryUtilities.java
 * ----------------------
 * (C) Copyright 2000-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Jon Iles;
 *
 * $Id: RefineryUtilities.java,v 1.11 2007/11/02 17:50:36 taqua Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 26-Nov-2001 : Changed name to SwingRefinery.java to make it obvious that this is not part of
 *               the Java APIs (DG);
 * 10-Dec-2001 : Changed name (again) to JRefineryUtilities.java (DG);
 * 28-Feb-2002 : Moved system properties classes into com.jrefinery.ui.about (DG);
 * 19-Apr-2002 : Renamed JRefineryUtilities-->RefineryUtilities.  Added drawRotatedString()
 *               method (DG);
 * 21-May-2002 : Changed frame positioning methods to accept Window parameters, as suggested by
 *               Laurence Vanhelsuwe (DG);
 * 27-May-2002 : Added getPointInRectangle method (DG);
 * 26-Jun-2002 : Removed unnecessary imports (DG);
 * 12-Jul-2002 : Added workaround for rotated text (JI);
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 08-May-2003 : Added a new drawRotatedString() method (DG);
 * 09-May-2003 : Added a drawRotatedShape() method (DG);
 * 10-Jun-2003 : Updated aligned and rotated string methods (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 07-Nov-2003 : Added rotateShape() method (DG);
 * 16-Mar-2004 : Moved rotateShape() method to ShapeUtils.java (DG);
 * 07-Apr-2004 : Modified text bounds calculation with TextUtilities.getTextBounds() (DG);
 * 21-May-2004 : Fixed bug 951870 - precision in drawAlignedString() method (DG);
 * 30-Sep-2004 : Deprecated and moved a number of methods to the TextUtilities class (DG);
 * 04-Oct-2004 : Renamed ShapeUtils --> ShapeUtilities (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0 release (DG);
 *
 */

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Method;

/**
 * 
 * 
 * @author David Gilbert
 */

public class FramePosition {
  /**
   * Positions the specified frame in the middle of the screen.
   *
   * @param frame  the frame to be centered on the screen.
   */
  public static void centerFrameOnScreen(final Window frame) {
      positionFrameOnScreen(frame, 0.5, 0.5);
  }
  /**
   * Positions the specified frame at a relative position in the screen, where
   * 50% is considered to be the center of the screen.
   * 
   * @param frame
   *          the frame.
   * @param horizontalPercent
   *          the relative horizontal position of the frame (0.0 to 1.0, where
   *          0.5 is the center of the screen).
   * @param verticalPercent
   *          the relative vertical position of the frame (0.0 to 1.0, where 0.5
   *          is the center of the screen).
   */
  public static void positionFrameOnScreen(final Window frame, final double horizontalPercent,
      final double verticalPercent) {

    final Rectangle s = getMaximumWindowBounds();
    final Dimension f = frame.getSize();
    final int w = Math.max(s.width - f.width, 0);
    final int h = Math.max(s.height - f.height, 0);
    final int x = (int) (horizontalPercent * w) + s.x;
    final int y = (int) (verticalPercent * h) + s.y;
    frame.setBounds(x, y, f.width, f.height);

  }

  /**
   * Computes the maximum bounds of the current screen device. If this method is
   * called on JDK 1.4, Xinerama-aware results are returned. (See Sun-Bug-ID
   * 4463949 for details).
   * 
   * @return the maximum bounds of the current screen.
   */
  public static Rectangle getMaximumWindowBounds() {
    final GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment
        .getLocalGraphicsEnvironment();
    try {
      final Method method = GraphicsEnvironment.class.getMethod("getMaximumWindowBounds",
          (Class[]) null);
      return (Rectangle) method.invoke(localGraphicsEnvironment, (Object[]) null);
    } catch (Exception e) {
      // ignore ... will fail if this is not a JDK 1.4 ..
    }

    final Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
    return new Rectangle(0, 0, s.width, s.height);
  }
}