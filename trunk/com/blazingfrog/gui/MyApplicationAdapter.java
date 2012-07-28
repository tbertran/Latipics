package com.blazingfrog.gui;


import javax.swing.JDialog;

import com.blazingfrog.dummy.ApplicationAdapter;
import com.blazingfrog.dummy.ApplicationEvent;
import com.blazingfrog.imported.FramePosition;


/**
 * MacAdapter.java
 * Copyright 2010, Alvin J. Alexander, devdaily.com.
 * 
 * This file is part of the DesktopShield application. This class implements the 
 * Apple/Mac/Java ApplicationAdapter class, specifically the handleQuit
 * method of that class, to help shut down this application properly.
 *
 * The DesktopShield application is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The DesktopShield application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the DesktopShield application. If not, see <http://www.gnu.org/licenses/>.
*/
public class MyApplicationAdapter extends ApplicationAdapter
{
	/*
	private JFrame mainFrame;
	public MyApplicationAdapter (JFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	*/
	
  public void handleQuit(ApplicationEvent e)
  {
	  System.exit(0);
  }

  public void handleAbout(ApplicationEvent e)
  {
    // tell the system we're handling this, so it won't display
    // the default system "about" dialog after ours is shown.	
    e.setHandled(true);
	JDialog about = new About();
	FramePosition.centerFrameOnScreen(about);
	about.setAlwaysOnTop(true);
	about.setUndecorated(false);
	about.setResizable(false);
	about.setFocusableWindowState(true);
	about.setVisible(true);
  }

  public void handlePreferences(ApplicationEvent e)
  {
	 // mainFrame.setFocusableWindowState(false);
	  new PrefPaneCaller();
  }
}