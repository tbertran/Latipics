package com.blazingfrog.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.blazingfrog.imported.BareBonesBrowserLaunch;
import com.blazingfrog.misc.Resources;


//VS4E -- DO NOT REMOVE THIS LINE!
public class About extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3; 
	private JButton jButton0;
	private JLabel jLabel4;

	public About() {
		try {
			initComponents();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() throws URISyntaxException {
		setFont(new Font("Geneva", Font.BOLD, 16));
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(105, 113, 10, 10), new Leading(12, 113, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(128, 12, 12), new Leading(133, 28, 10, 10)));
		add(getJLabel3(), new Constraints(new Leading(70, 12, 12), new Leading(186, 39, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(123, 84, 10, 10), new Leading(167, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(116, 12, 12), new Leading(215, 10, 10)));
		setSize(320, 270);
		if (Resources.getOSName().equals("mac")){
			add(getJButton0(), new Constraints(new Leading(193, 97, 10, 10), new Leading(195, 12, 12)));
			getContentPane().setBackground(new Color(254, 254, 254));
		}
		else
		{			
			add(getJButton0(), new Constraints(new Leading(166, 117, 10, 10), new Leading(192, 12, 12)));
			getContentPane().setBackground(Resources.getWinThemeColor());
			addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
					try {
						UIManager.setLookAndFeel(Resources.getLookAndFeel());
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
		      }
		    });
		}
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("All rights reserved.");
			jLabel4.setFont(new Font("Geneva", Font.PLAIN, 10));
		}
		return jLabel4;
	}

	private JButton getJButton0() throws URISyntaxException{
		final String uri = "http://www.blazingfrog.com";
		if (jButton0 == null) {
			if (Resources.getOSName().equals("windows")){
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
			jButton0 = new JButton();
			if (Resources.getOSName().equals("windows"))
				jButton0.setBackground(Resources.getWinThemeColor());
			jButton0.setText("<HTML><FONT face=\"Geneva\" color=\"#000099\" size=\"2\"><U>Blazing Frog</U>.</FONT></HTML>");
			jButton0.setHorizontalAlignment(SwingConstants.LEFT);
			jButton0.setBorderPainted(false);
			jButton0.setOpaque(false);
			jButton0.setToolTipText(uri.toString());
			jButton0.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                	open(uri);
	                }
	        });
		}
		
		return jButton0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("<html>Copyright © 2010-2011</html>");
			jLabel3.setFont(new Font("Geneva", Font.PLAIN, 10));
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Version 1.2");
			jLabel2.setFont(new Font("Geneva", Font.PLAIN, 14));
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("LatiPics");
			jLabel1.setFont(new Font("Geneva", Font.BOLD, 18));
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setIcon(Resources.getLpLogo128());
		}
		return jLabel0;
	}

    private void open(String uri) {
 		this.setVisible(false);
 		BareBonesBrowserLaunch.openURL(uri);
    }
}
