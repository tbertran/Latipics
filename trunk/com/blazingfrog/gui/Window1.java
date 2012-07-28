package com.blazingfrog.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.blazingfrog.backend.MainProcess;
import com.blazingfrog.dummy.Application;
import com.blazingfrog.imported.FileDrop;
import com.blazingfrog.imported.RotatedJLabel;
import com.blazingfrog.misc.ConsoleFile;
import com.blazingfrog.misc.DefaultOptions;
import com.blazingfrog.misc.Resources;
import com.blazingfrog.misc.UserProfile;


//VS4E -- DO NOT REMOVE THIS LINE!
public class Window1 extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar0;
	private JLabel mainLabel;
	private JButton startBtn;
	private static final int TIMER_FREQ = 100;
	private JTextField folderName;
	private RotatedJLabel imgLabel;
	private Thread batch;
	private JProgressBar jProgressBar0;
	private MainProcess mainProcess;
	private JLabel subLabel;
	private JButton stopBtn;
	private JButton selectFolderBtn;
	private Border border;
	private boolean stopProcess;
	private JLabel consoleLabel; 
	private JLabel frogLabelImg;
	private ArrayList<File> fileList;
	
	public Window1() {
		initComponents();
	}

	private void initComponents() {
		setBackground(new Color(251, 251, 251));
		setResizable(false);
		setLayout(new GroupLayout());
		add(getStartBtn(), new Constraints(new Leading(25, 150, 12, 12), new Leading(260, 33, 10, 10)));
		add(getStopBtn(), new Constraints(new Leading(187, 150, 12, 12), new Leading(260, 33, 10, 10)));
		add(getJProgressBar0(), new Constraints(new Leading(344, 511, 10, 10), new Leading(260, 34, 10, 10)));
		add(getImgLabel(), new Constraints(new Leading(30, 10, 10), new Leading(19, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(211, 633, 12, 12), new Leading(184, 32, 12, 12)));
		add(getmainLabel(), new Constraints(new Leading(198, 661, 10, 10), new Leading(13, 183, 12, 12)));
		add(getConsoleLabel(), new Constraints(new Leading(229, 625, 10, 10), new Leading(226, 28, 12, 12)));
		add(getFrogLabelImg(), new Constraints(new Leading(220, 642, 12, 12), new Leading(4, 312, 10, 10)));
		add(getSelectFolderBtn(), new Constraints(new Leading(25, 165, 12, 12), new Leading(180, 42, 12, 12)));
		add(getsubLabel(), new Constraints(new Leading(75, 70, 12, 12), new Leading(131, 20, 12, 12)));
		setJMenuBar(getJMenuBar0());
		setSize(869, 351);
	}

	private JLabel getFrogLabelImg() { 
		if (frogLabelImg == null) {
			frogLabelImg = new JLabel();
			frogLabelImg.setIcon(Resources.getBfMainFrog());
		}
		return frogLabelImg;
	}

	private JLabel getConsoleLabel() {
		if (consoleLabel == null) {
			consoleLabel = new JLabel();
			consoleLabel.setForeground(Color.DARK_GRAY);
			consoleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		}
		return consoleLabel;
	}

	private JButton getStopBtn() {
		if (stopBtn == null) {
			stopBtn = new JButton();
			stopBtn.setText("Stop");
			stopBtn.setFont(new Font("Geneva", Font.BOLD, 11));
			stopBtn.setFocusable(true);
			stopBtn.setDoubleBuffered(true);
			stopBtn.setEnabled(false);
			if (Resources.getOSName().equals("windows"))
				stopBtn.setBackground(new Color(238,238,238));
			stopBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent event) {
					stopBtnMouseMouseClicked(event);
				}
			});
		}
		return stopBtn;
	}


	private JLabel getmainLabel() {
		if (mainLabel == null) {
			mainLabel = new JLabel();
			mainLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 184));
			mainLabel.setText("LatiPics");
			mainLabel.setForeground(Color.GRAY);
			mainLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		}
		return mainLabel;
	}
	
	
	private JLabel getsubLabel() {
		if (subLabel == null) {
			subLabel = new JLabel();
			subLabel.setFont(new Font("Geneva", Font.ITALIC, 13));
			subLabel.setText("Drop Zone");
			subLabel.setForeground(Color.gray);
		}
		return subLabel;
	}

	private JProgressBar getJProgressBar0() { 
		if (jProgressBar0 == null) {
			jProgressBar0 = new JProgressBar();
			jProgressBar0.setMaximum(1); // default
			//  Windows: do not show unless running
			if (Resources.getOSName().equals("windows"))
				jProgressBar0.setVisible(false);
			else{
				jProgressBar0.setStringPainted(true);
			    border = BorderFactory.createTitledBorder(" ");
				jProgressBar0.setBorder(border);
			}
		}
		return jProgressBar0;
	}

	private JLabel getImgLabel() {
		if (imgLabel == null) {
			imgLabel = new RotatedJLabel(""); //JLabel();
			imgLabel.setMinimumSize(new Dimension(160, 106));
			imgLabel.setPreferredSize(new Dimension(160, 106)); //106));
			imgLabel.setMaximumSize(new Dimension(160, 160)); //106));
			imgLabel.setOpaque(true);
			imgLabel.setRotation(0);
			imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
			if (Resources.getOSName().equals("windows"))
				imgLabel.setBackground(Resources.getWinThemeColor());
			else
				imgLabel.setBackground(new Color(251, 251, 251));
			
			imgLabel.setIcon(Resources.getIPhotoCrosshair());
			new  FileDrop( imgLabel, new FileDrop.Listener()
		      { 
				public void  filesDropped( java.io.File[] files )
		          {   
					  if (batch == null || !batch.isAlive()){
						  resetVariables();
			    	  	  List<File> tmpFileList = Arrays.asList(files);
			    	  	  fileList = new ArrayList<File>(tmpFileList);
			    	  	  processSelection(new ArrayList<File>(fileList));
					  }
		          }   
		      });
		}
		return imgLabel;
	}

	private JTextField getJTextField0() {
		if (folderName == null) {
			folderName = new JTextField();
			folderName.setEditable(false);
			folderName.setBackground(Color.gray);
			folderName.setForeground(Color.WHITE);
			folderName.setFont(new Font("Monaco", Font.BOLD, 14));
			folderName.setHorizontalAlignment(JTextField.CENTER);
		}
		return folderName;
	}

	private JButton getStartBtn() {
		if (startBtn == null) {
			startBtn = new JButton();
			startBtn.setEnabled(false);
			startBtn.setFont(new Font("Geneva", Font.BOLD, 11));
			startBtn.setText("Start"); 
			if (Resources.getOSName().equals("windows"))
				startBtn.setBackground(Resources.getWinThemeColor());
			
			startBtn.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent event) {
					try {
						doProcess();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			startBtn.addKeyListener(new KeyAdapter() {
	
				public void keyPressed(KeyEvent event) {
					if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ENTER){
						try {
							doProcess();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		return startBtn;
	}


	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
		}
		return jMenuBar0;
	}

	private JButton getSelectFolderBtn() {
		if (selectFolderBtn == null) {
			selectFolderBtn = new JButton("Select a File or a Folder"); 
			selectFolderBtn.setFont(new Font("Geneva", Font.BOLD, 11));
			selectFolderBtn.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					selectFolderBtnMouseMouseClicked(event);
				}
			});
			selectFolderBtn.addKeyListener(new KeyAdapter() {
	
				public void keyPressed(KeyEvent event) {
					selectFolderBtnKeyKeyPressed(event);
				}
			});
		}
		return selectFolderBtn;
	}

	private void selectFolderBtnMouseMouseClicked(MouseEvent event) {
		if (selectFolderBtn.isEnabled())
			selectFileFolderAction();
	}

	private void timerActionPerformed(ActionEvent e) {
		jProgressBar0.setValue(mainProcess.getNbPicsProcessed());
	    border = BorderFactory.createTitledBorder("Processing...");
		jProgressBar0.setBorder(border);

	    javax.swing.Timer timer = (Timer) e.getSource();
	    
		if (!batch.isAlive()){
			timer.stop();
			resetVariables();
			
		    if (stopProcess){
			    border = BorderFactory.createTitledBorder("Action interrupted...");
				jProgressBar0.setValue(0);
		    } else{
			    border = BorderFactory.createTitledBorder("Complete.");
			    jProgressBar0.setValue(100);
	
		    }
		   	jProgressBar0.setBorder(border);
		}


	}
	
	private void doProcess() throws IOException{
		
		if (!startBtn.isEnabled())
			return;
		
		if (ConsoleFile.getLength() > 0)
        	ConsoleFile.empty();
		
		if (UserProfile.listProfiles().size() == 0){
			JOptionPane.showMessageDialog(this,
					"<html><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>You have not created any LatiPics profile to link<br> with your Google Latitude account.</b><br><br>Click OK to add a profile and link it to a Google ID.</CENTER></font></html>",
				    "No Profile Found",
				    JOptionPane.INFORMATION_MESSAGE,
				    Resources.getLpIcon());
			new PrefPaneCaller();
			return;
		}
		
        // Get the profile
        String profile = DefaultOptions.getDefaultProfileName();
        String token = UserProfile.getToken(profile);
        String tokenSecret = UserProfile.getTokenSecret(profile);
		
        initVariablesBeforeRunning();
        
        stopProcess = false;
        
        mainProcess = new MainProcess(fileList, imgLabel, consoleLabel, token, tokenSecret);
		batch = new Thread(mainProcess);
		batch.start();
		
		//jProgressBar0.setVisible(true); // Windows: was invisible
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        selectFolderBtn.setEnabled(false);
        
        // To assure focus
//        try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//        stopBtn.requestFocus();
        stopBtn.requestFocusInWindow();
        
        new javax.swing.Timer(TIMER_FREQ, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  timerActionPerformed(e);
          }
        }).start();
	}

	private void stopBtnMouseMouseClicked(ActionEvent event){ //MouseEvent event) {
		stopProcess = true;
		mainProcess.setStopProcess(stopProcess);
	}

	private void selectFolderBtnKeyKeyPressed(KeyEvent event) {
		if(event.getKeyChar() == ' '){
			selectFileFolderAction();
		}
	}
	
	@SuppressWarnings("static-access")
	private void selectFileFolderAction() {
		resetVariables();
		
		if (UserProfile.listProfiles().size() == 0){
			JOptionPane.showMessageDialog(this,
				    "<html><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>You have not created any LatiPics profile to link<br> with your Google Latitude account.</b><br><br>Click OK to add a profile and link it to a Google ID.</CENTER></font></html>",
				    "No Profile Found",
				    JOptionPane.INFORMATION_MESSAGE,
				    Resources.getLpIcon());
			new PrefPaneCaller();
			return;
		}
		
		// Do not allow pressing the filechooser if preferences are not enabled (preferences window open)
		if (Resources.getOSName().equals("mac") && !Application.getApplication().getEnabledPreferencesMenu())
			return;
		if (Resources.getOSName().equals("windows") && !this.getJMenuBar().getMenu(0).getMenuComponent(2).isEnabled())
			return;
		
		final JFileChooser fc = new JFileChooser(Resources.getUserDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif", "tiff", "bmp", "ico", "psd", "xmp");
		fc.setFileFilter(filter);

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);


		final CheckBoxAccessory checkBoxAccessory = new CheckBoxAccessory(fc);
		fc.setAccessory(checkBoxAccessory);
		fc.addPropertyChangeListener( new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ( evt.getPropertyName().equals("directoryChanged")){
					
					// uncheck checkbox if not on a folder
					if (fc.getSelectedFile() != null && !fc.getSelectedFile().isDirectory()){
						checkBoxAccessory.setCBState(false);
					}
				}
			}});
		
		int rc = fc.showOpenDialog(jMenuBar0);
		if (rc == JFileChooser.APPROVE_OPTION) {

			// save user directory
			if (fc.getSelectedFile().isDirectory())
				Resources.setUserDirectory(fc.getSelectedFile());
			else
				Resources.setUserDirectory(fc.getCurrentDirectory());
			
			// Get the file/directory and process it
			fileList = new ArrayList<File>();
			fileList.add(fc.getSelectedFile());				
			processSelection(fileList);
            
        } 
	}
	
	private void initVariablesBeforeRunning(){
		// Initialize variables before processing
		startBtn.setEnabled(true);
		startBtn.setFocusable(true);
		jProgressBar0.setValue(0);
		consoleLabel.setText("");
		subLabel.setText("");
		if (Resources.getOSName().equals("windows"))
			jProgressBar0.setVisible(true);
	}
	
	private void resetVariables(){
		startBtn.setEnabled(false);
		startBtn.setFocusable(false);
		jProgressBar0.setValue(0);
		if (Resources.getOSName().equals("windows"))
			jProgressBar0.setVisible(false);
		
		selectFolderBtn.setEnabled(true);
	    subLabel.setText("Drop Zone");
        folderName.setText("");
        consoleLabel.setText("");
	    stopBtn.setEnabled(false);
	}
	
	private void processSelection(ArrayList<File> fileList){
		int nbDir=0;
		int nbFiles=0;
		int totNbrFiles=0;
        if (fileList.size() > 1){
        	for (int idx=0;idx < fileList.size();idx++)
        	{
        		if (fileList.get(idx).isDirectory())
        		{
        			nbDir++;
        			// are files within the passed dir actually files or dirs? 
        			for (int idx2=0;idx2 < fileList.get(idx).listFiles().length;idx2++){
        				if (!fileList.get(idx).listFiles()[idx2].isDirectory()){
        					totNbrFiles++;
        				}
        			}
        		}
        		else{
        			nbFiles++;
        		}
        	}
        	totNbrFiles = totNbrFiles + nbFiles;
        	
        	if (nbFiles > 0 && nbDir > 0)
        		folderName.setText(nbFiles + " file(s) and " + nbDir + " folder(s) were selected (total number of files: " + totNbrFiles + ")");
        	if (nbFiles == 0 && nbDir > 0)
        		folderName.setText(nbDir + " folder(s) were selected (total number of files: " + totNbrFiles + ")");
          	if (nbFiles > 0 && nbDir == 0)
        		folderName.setText(totNbrFiles + " file(s) were selected");
          	
          	jProgressBar0.setMaximum(totNbrFiles);
        }
        else
        {
        	totNbrFiles = 1;
		    if (fileList.get(0).isFile()){
		    	jProgressBar0.setMaximum(1);
		    	folderName.setText(fileList.get(0).getName());
		    } else {
    			// are files within the passed dir actually files or dirs? 
    			for (int idx2=0;idx2 < fileList.get(0).listFiles().length;idx2++){
    				if (!fileList.get(0).listFiles()[idx2].isDirectory()){
    					totNbrFiles++;
    				}
    			}
		    	jProgressBar0.setMaximum(totNbrFiles);
		    	folderName.setText(fileList.get(0).getName() + " (" + totNbrFiles + " files)");
		    }
		    
        }
        if (totNbrFiles == 0)
        	return;
        
        initVariablesBeforeRunning();
        // To assure focus
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        startBtn.requestFocus();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
