package com.blazingfrog.gui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class CheckBoxAccessory extends JPanel {

	private JCheckBox procDirCB;
	
	private static final long serialVersionUID = 1L;
	public CheckBoxAccessory(JFileChooser fc_in){
		final JFileChooser fc = fc_in;
		setLayout(new BorderLayout());
	    JPanel p = new JPanel();
	    procDirCB = new JCheckBox("Process entire folder");
	    procDirCB.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					fc.setSelectedFile(fc.getCurrentDirectory());
				}
				else{
					if (fc.getCurrentDirectory().listFiles()[0].isDirectory())
						fc.setSelectedFile(fc.getSelectedFile().listFiles()[0]);
				}
			}});
	    p.add(procDirCB);
	    add(p, BorderLayout.CENTER);
	}
	
	public void setCBState(boolean selected){
		procDirCB.setSelected(selected);
	}
}
