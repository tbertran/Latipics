package com.blazingfrog.gui;

import java.awt.Dialog;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import com.blazingfrog.dummy.Application;
import com.blazingfrog.imported.FramePosition;
import com.blazingfrog.misc.Resources;


public class PrefPaneCaller {
	
	public PrefPaneCaller(){
		this(null);
	}
	
	@SuppressWarnings("static-access")
	public PrefPaneCaller(Window1 mainFrame){
		JDialog d = null;
		if (Resources.getOSName().equals("windows")){
			d = new PrefPane2(mainFrame);
			d.setTitle("Options");
			d.setUndecorated(false);
			d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			d.getContentPane().setBackground(Resources.getWinThemeColor());
			FramePosition.centerFrameOnScreen(d);
		}
		else{
			d = new PrefPane2(mainFrame, "", Dialog.ModalityType.APPLICATION_MODAL); 
			//d.setUndecorated(true);
			d.setTitle("Preferences");
			Application.getApplication().setEnabledPreferencesMenu(false);
		}
		d.setFocusableWindowState(true);
	
		JRootPane rootPane = d.getRootPane();
		rootPane.setFont(new Font("Geneva", Font.PLAIN, 11));

		d.setVisible(true);
	}
}
