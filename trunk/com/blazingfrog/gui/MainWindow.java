package com.blazingfrog.gui;


import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.blazingfrog.dummy.Application;
import com.blazingfrog.imported.BareBonesBrowserLaunch;
import com.blazingfrog.imported.FramePosition;
import com.blazingfrog.misc.ConsoleFile;
import com.blazingfrog.misc.Resources;


public class MainWindow {
	private static final String APP_NAME = "LatiPics";
	protected JFrame mainFrame;
	public static LookAndFeel defaulLaF;
	private JMenuItem optionsMenuItem;

	public JMenuItem getOptionsMenuItem() {
		return optionsMenuItem;
	}

	@SuppressWarnings("static-access")
	public MainWindow(){
		
		// set some mac-specific properties
	    System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
	    System.setProperty("apple.laf.useScreenMenuBar", "true");
	    System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);

	    if (Resources.getOSName().equals("mac")){    		
	    	Application macApplication = Application.getApplication();
			MyApplicationAdapter macAdapter = new MyApplicationAdapter(); //(mainFrame);
			//macApplication.addApplicationListener(macAdapter);
	
			macApplication.setEnabledPreferencesMenu(true);
	
	        System.setProperty("Quaqua.tabLayoutPolicy","wrap");
	        defaulLaF = UIManager.getLookAndFeel();
	        try { 
	        	UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
	        } catch (Exception e) {e.printStackTrace();}
		}
		else{
			try {
				 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch(Exception e) {System.out.println("Error setting Motif LAF: " + e);}
		}
        
        ConsoleFile.create();
        displayWindow();
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	private void displayWindow(){
		mainFrame = new Window1();
		FramePosition.centerFrameOnScreen(mainFrame);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Global Menu Bar
        JMenuBar menuBar = new JMenuBar();
        
        
        // "Tools" Menu
        JMenu menu = new JMenu("Tools");
        menu.setMnemonic(KeyEvent.VK_T);
        menu.getAccessibleContext().setAccessibleDescription("Tools Menu");
        
        // First menu item (Log File)
        JMenuItem menuItem = new MyMenuItem("See Log File"); // JMenuItem("See Log File", KeyEvent.VK_L);
        menuItem.getAccessibleContext().setAccessibleDescription("See the HTML log file");
        menu.add(menuItem);
        		
        // Second menu item (Location History)
        menuItem = new MyMenuItem("Go To Your Location History");
        menuItem.getAccessibleContext().setAccessibleDescription("See the HTML log file");
        menu.add(menuItem);
        menuBar.add(menu);
        
        if (Resources.getOSName().equals("windows")){
            // Third menu item (Options)
            optionsMenuItem = new MyMenuItem("Options");
            optionsMenuItem.getAccessibleContext().setAccessibleDescription("Options");
            menu.add(optionsMenuItem);
            menuBar.add(menu);
        }
        
        // "Help" Menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription("Help Menu");
        
        // First menu item (Log File)
        menuItem = new MyMenuItem("LatiPics Support");
        menuItem.getAccessibleContext().setAccessibleDescription("Access BlazingFrog's LatiPics support section");
        menu.add(menuItem);

        menuItem = new MyMenuItem("Contact Us");
        menuItem.getAccessibleContext().setAccessibleDescription("Create email to send to LatiPics support");
        menu.add(menuItem);

        menuItem = new MyMenuItem("Donate");
        menuItem.getAccessibleContext().setAccessibleDescription("Make a kind donation for all the great work!");
        menu.add(menuItem);
        
     // Windows OnlyP: Third menu item (About)
        if (Resources.getOSName().equals("windows")){
            menuItem = new MyMenuItem("About");
            menuItem.getAccessibleContext().setAccessibleDescription("About");
            menu.add(menuItem);
            menuBar.add(menu);
        }
        
        menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
		
		JDialog d = new JDialog(mainFrame);
		d.setTitle("LatiPics");
		d.setAlwaysOnTop(false);
		d.setUndecorated(true);
		d.setFocusableWindowState(false);


		 
		JRootPane rootPane = d.getRootPane();
		rootPane.setWindowDecorationStyle(JRootPane.FRAME);
		rootPane.setFont(new Font("Geneva", Font.PLAIN, 11));
		rootPane.putClientProperty("Quaqua.RootPane.isVertical", Boolean.TRUE);
		rootPane.putClientProperty("Quaqua.RootPane.isPalette", Boolean.TRUE);
		d.setVisible(true);
	}

	private class MyMenuItem extends JMenuItem implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyMenuItem(String text) {
		   super(text);
		   addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("See Log File")){
                try {
                    File logFile = ConsoleFile.getFile();
                    if (logFile != null)
                            Desktop.getDesktop().open(logFile);
                    
		        } catch (IOException e1) {
		                e1.printStackTrace();
		        }
			}
			
			if (e.getActionCommand().equals("Go To Your Location History")){
				BareBonesBrowserLaunch.openURL("https://www.google.com/latitude/apps/history/view");
			}
			
			if (e.getActionCommand().equals("Donate")){
				BareBonesBrowserLaunch.openURL("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=latipics%40blazingfrog%2ecom&lc=US&item_name=LatiPics%20by%20BlazingFrog%20%2d%20Thank%20You%21&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest");
			}
			
			if (e.getActionCommand().equals("LatiPics Support")){
				BareBonesBrowserLaunch.openURL("http://www.blazingfrog.com/bf/LatipicsHelp.html");
			}
			
			if (e.getActionCommand().equals("Options")){
				optionsMenuItem.setEnabled(false);
				new PrefPaneCaller((Window1) mainFrame);
			}
	        
			if (e.getActionCommand().equals("Contact Us")){
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().mail(new URI("mailto", "latipics@blazingfrog.com", null));
					} catch (IOException e1) {					
						JOptionPane.showMessageDialog(this,
							    "<html><head></head><body><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>LatiPics attempted to open your email program<br>but did not find it.</b><br><br>You can contact us by sending an email to <b>latipics@blazingfrog.com</b> for any question.</CENTER>",
							    "No Email Program Found",
							    JOptionPane.INFORMATION_MESSAGE,
							    Resources.getCustAttnIcon());
					}
						catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(this, "Please send an email to\nlatipics@blazingfrog.com\nfor any question.");
				}
			}
			
			if (e.getActionCommand().equals("About")){
				JDialog about = new About();
				FramePosition.centerFrameOnScreen(about);
				about.setAlwaysOnTop(true);
				about.setUndecorated(false);
				about.setResizable(false);
				about.setFocusableWindowState(true);
				about.setVisible(true);
			  }
		}
	}
}
