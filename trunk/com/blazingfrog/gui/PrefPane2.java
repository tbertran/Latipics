package com.blazingfrog.gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.blazingfrog.dummy.Application;
import com.blazingfrog.misc.DefaultOptions;
import com.blazingfrog.misc.Resources;
import com.blazingfrog.misc.UserProfile;
import com.blazingfrog.oauth.OAuthMain;


//VS4E -- DO NOT REMOVE THIS LINE!
public class PrefPane2 extends JDialog {

	private ArrayList<String> listProfiles;
	private static final long serialVersionUID = 1L;
	private JLabel jLabel0, jLabel2, jLabel3;
	private JTextField profName;
	private JTextField picTimeMin;
	private JTextField picTimeMax;
	private JList profList;
	private JScrollPane jScrollPane0;
	private JLabel jLabel1;
	private JButton oKBtn;
	private JButton cancelBtn;
	private JButton authBtn;
	private JLabel latipicsLogo;
	private Window1 parent;
	private JCheckBox ovrdCheckBox, updtDateCheckBox;
	
	public PrefPane2() {
		initComponents();
	}

	public PrefPane2(Frame parent) {
		super(parent);
		this.parent = (Window1) parent; // TBE
		initComponents();
	}

	public PrefPane2(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public PrefPane2(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}
	
	public PrefPane2(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public PrefPane2(Frame parent, String title, boolean modal,
			GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public PrefPane2(Dialog parent) {
		super(parent);
		initComponents();
	}

	public PrefPane2(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public PrefPane2(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public PrefPane2(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public PrefPane2(Dialog parent, String title, boolean modal,
			GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public PrefPane2(Window parent) {
		super(parent);
		initComponents();
	}

	public PrefPane2(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public PrefPane2(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public PrefPane2(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public PrefPane2(Window parent, String title, ModalityType modalityType,
			GraphicsConfiguration arg) {
		super(parent, title, modalityType, arg);
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("Geneva", Font.PLAIN, 13));
		setBackground(new Color(233, 233, 233));
		setResizable(false);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(27, 202, 12, 12), new Leading(12, 12, 12)));
		add(getPicTimeMin(), new Constraints(new Leading(288, 50, 12, 12), new Leading(217, 26, 12, 12)));
		add(getPicTimeMax(), new Constraints(new Leading(380, 50, 12, 12), new Leading(217, 26, 49, 52)));
		add(getProfName(), new Constraints(new Leading(27, 202, 12, 12), new Leading(48, 25, 12, 12)));
		add(getLatipicsLogo(), new Constraints(new Leading(75, 107, 12, 12), new Leading(123, 112, 12, 12)));
		add(getAuthBtn(), new Constraints(new Leading(57, 143, 12, 12), new Leading(81, 32, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(288, 12, 12), new Leading(14, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Leading(288, 142, 10, 10), new Leading(34, 126, 10, 10)));
		add(getJLabel3(), new Constraints(new Leading(288, 153, 12, 12), new Leading(195, 27, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(308, 116, 10, 10), new Leading(180, 21, 10, 10)));
		add(getJCheckBox0(), new Constraints(new Leading(27, 211, 10, 10), new Leading(240, 28, 10, 10)));
		add(getJCheckBox1(), new Constraints(new Leading(27, 223, 10, 10), new Leading(270, 28, 10, 10)));
		add(getCancelBtn(), new Constraints(new Leading(288, 61, 12, 12), new Leading(285, 28, 10, 10)));
		add(getOKBtn(), new Constraints(new Leading(369, 61, 12, 12), new Leading(285, 28, 10, 10)));
		setSize(453, 351);
	}

	private JCheckBox getJCheckBox0() {
		if (ovrdCheckBox == null) {
			ovrdCheckBox = new JCheckBox();
			ovrdCheckBox.setFont(new Font("Geneva", Font.BOLD, 11));
			ovrdCheckBox.setForeground(Color.darkGray);
			ovrdCheckBox.setSelected(true);
			ovrdCheckBox.setText("Override existing GPS information");
			ovrdCheckBox.setSelected(DefaultOptions.doOverrideGPS());
		}
		return ovrdCheckBox;
	}

	private JCheckBox getJCheckBox1(){
	if(updtDateCheckBox==null){
	updtDateCheckBox = new JCheckBox();
	updtDateCheckBox.setFont(new Font("Geneva", Font.BOLD, 11));
	updtDateCheckBox.setForeground(Color.darkGray);
	updtDateCheckBox.setSelected(true);
	updtDateCheckBox.setText("Update \"Last Updated\" Date on file");
	updtDateCheckBox.setSelected(DefaultOptions.doUpdateLastModified());
	}
	return updtDateCheckBox;
	}

	private JLabel getLatipicsLogo() {
		if (Resources.getOSName().equals("mac")){
			setBackground(new Color(233, 233, 233));
			setSize(455, 261);
		}else
			setSize(462, 291);
		
		if (latipicsLogo == null) {
			latipicsLogo = new JLabel();
			latipicsLogo.setIcon(Resources.getLpNotFoundLogo());
		}
		
		
		// For lack of a better place...
		this.addExitListeners();
		
		return latipicsLogo;
	}

	private void addExitListeners() {
	    ActionListener escListener = new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	exitThis();
	        }
	    };

	    getRootPane().registerKeyboardAction(escListener,
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);

		addWindowListener(new WindowAdapter()
	      {
	         public void windowClosing(WindowEvent e)
	         {
	           exitThis();
	         }
	      });
	}

	private JButton getOKBtn() {
		setBackground(new Color(233, 233, 233));
		if (oKBtn == null) {
			oKBtn = new JButton();
			oKBtn.setText("OK");
			oKBtn.setFocusable(true);
			oKBtn.setFont(new Font("Geneva", Font.BOLD, 11));
			oKBtn.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					oKBtnMouseMouseClicked(event);
				}
			});
			oKBtn.addKeyListener(new KeyAdapter() {
				
				public void keyPressed(KeyEvent event) {
					oKBtnKeyKeyPressed(event);
				}
			});
		}
		return oKBtn; 
	}
	
	private JButton getCancelBtn() {
		setBackground(new Color(233, 233, 233));
		if (cancelBtn == null) {
			cancelBtn = new JButton();
			cancelBtn.setText("Cancel");
			cancelBtn.setFocusable(true);
			if (Resources.getOSName().equals("mac")){
				cancelBtn.setFont(new Font("Geneva", Font.BOLD, 11));
			} else{
				cancelBtn.setFont(new Font("Geneva", Font.PLAIN, 9));
			}
			cancelBtn.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					exitThis();
				}
			});
			cancelBtn.addKeyListener(new KeyAdapter() {
				
				public void keyPressed(KeyEvent event) {
					if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ENTER) {
						exitThis();
					}
				}
			});
		}
		return cancelBtn; 
	}

	private JButton getAuthBtn() {
		if (authBtn == null) {
			authBtn = new JButton();
			authBtn.setText("Authorize Profile");
			authBtn.setFont(new Font("Geneva", Font.BOLD, 10));
			authBtn.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					processAuth();
				}
			});
			authBtn.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent event) {
					if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ENTER){
						processAuth();
					}
				}
			});
		}
		return authBtn;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Profiles:");
			jLabel1.setFont(new Font("Geneva", Font.BOLD, 14));
		}
		return jLabel1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getProfList());
		}
		return jScrollPane0;
	}

	private JList getProfList() {
		if (profList == null) {
			profList = new JList();
			listProfiles = UserProfile.listProfiles();
			refreshProfList();
			profList.setDoubleBuffered(false);
			profList.setBorder(null);
			profList.setFont(new Font("Geneva", Font.BOLD, 11));
			profList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			profList.putClientProperty("Quaqua.List.style", "striped");
		}

		profList.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					oKBtnKeyKeyPressed(event);
			}
		});
		return profList;
	}

	private JTextField getProfName() {
		if (profName == null) {
			profName = new JTextField();
			profName.setFont(new Font("Geneva", Font.BOLD, 11));
		}
		profName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER){
					processAuth();
				}
			}
		});
		return profName;
	}
	
	
	public JTextField getPicTimeMin() {
		if (picTimeMin == null) {
			picTimeMin = new JTextField();
			picTimeMin.setFont(new Font("Geneva", Font.BOLD, 10));
			picTimeMin.setHorizontalAlignment(JTextField.RIGHT);
		}
		
		picTimeMin.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					oKBtnKeyKeyPressed(event);
			}
		});
		return picTimeMin;
	}
	
	public JTextField getPicTimeMax() {
		if (picTimeMax == null) {
			picTimeMax = new JTextField();
			picTimeMax.setFont(new Font("Geneva", Font.BOLD, 11));
			picTimeMax.setHorizontalAlignment(JTextField.RIGHT);
		}
		picTimeMax.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					oKBtnKeyKeyPressed(event);
			}
		});
		return picTimeMax;
	}
	
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setFont(new Font("Geneva", Font.PLAIN, 11));
			jLabel0.setText("<html><head></head><body><CENTER>Pick a profile name you wish to use<br>with your Latitude account:</CENTER></body></html>");
		}
		return jLabel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setFont(new Font("Geneva", Font.BOLD, 14));
			jLabel2.setText
			("Latitude Range");
		}
		return jLabel2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setFont(new Font("Geneva", Font.PLAIN, 9));
			if (Resources.getOSName().equals("mac")){
				jLabel3.setText("<html><head></head><body>Minutes before&nbsp;&nbsp;Minutes after</body></html>");
			}else{
				jLabel3.setText("<html><head></head><body>Minutes before&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minutes after</body></html>");
			}
			
		}
		return jLabel3;
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	private void oKBtnMouseMouseClicked(MouseEvent event) {
		// Turn the "Prefs/Options" menu back on
		oKAction();
//		if (Resources.getOSName().equals("mac"))
//			Application.getApplication().setEnabledPreferencesMenu(true);
//		else
//			parent.getJMenuBar().getMenu(0).getMenuComponent(2).setEnabled(true);
//		
//		try {
//			UserProfile.setMinMaxTimes(getPicTimeMin().getText(), getPicTimeMax().getText());
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		this.setVisible(false);
	}

	private void processAuth(){
		int response=0;
		String profile = getProfName().getText().replaceAll("[ $.]","" );
		if (!profile.equals("")){
			if (UserProfile.exists(getProfName().getText())) {
				Object[] options = {"Replace", "Cancel"};
				response = JOptionPane.showOptionDialog(null,
					    "This profile is already associated with Latitude. Do you want to replace it?",
					    "Profile Already Used",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,  //the titles of buttons 
					    options[1]); //default button title		
			}
			if (response == 0){
				try {
					new OAuthMain(this, getProfName().getText());
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
				profName.setText("");
				refreshProfList();
			}
		}
	}
	
	private void refreshProfList(){
		String[] arrayProfiles = UserProfile.listProfiles().toArray(new String[listProfiles.size()]);
		profList.setListData(arrayProfiles);
		if (arrayProfiles.length != 0){
//			try {
				//profList.setSelectedValue(UserProfile.getDefault(), true);
				profList.setSelectedValue(DefaultOptions.getDefaultProfileName(), true);
				
//				picTimeMin.setText(UserProfile.getPicMinTime());
//				picTimeMax.setText(UserProfile.getPicMaxTime());
				picTimeMin.setText(DefaultOptions.getMinPicTime());
				picTimeMax.setText(DefaultOptions.getMaxPicTime());
				
//			} catch (InvalidPropertiesFormatException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}

	@SuppressWarnings("static-access")
	private void oKBtnKeyKeyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ENTER){
			oKAction();
		}
	}
	
	private void oKAction(){

		int before, after;
		// Validate the "before" minutes
		try{
			before = Integer.parseInt(picTimeMin.getText());
			if (before < 0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"<html><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>The number of minutes must be one or more digits, greater than 0.</b><br><br>Please fix the \"before\" number.</CENTER></font></html>",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE,
				    Resources.getLpIcon());

			picTimeMin.requestFocusInWindow();
			picTimeMin.setSelectionStart(0);
			picTimeMin.setSelectionEnd(picTimeMin.getText().length());
			return;
		} 

		// Validate the "after" minutes
		try{
			after = Integer.parseInt(picTimeMax.getText());
			if (after < 0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"<html><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>The number of minutes must be one or more digits, greater than 0.</b><br><br>Please fix the \"after\" number.</CENTER></font></html>",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE,
				    Resources.getLpIcon());

			picTimeMax.requestFocusInWindow();
			picTimeMax.setSelectionStart(0);
			picTimeMax.setSelectionEnd(picTimeMax.getText().length());
			return;
		} 
		
		// Make sure both are NOT 0
		if ((before == 0) && (after == 0)){
			JOptionPane.showMessageDialog(this,
					"<html><font face=\"Lucida Grande\" size=\"3\"><CENTER><b>Only one of the two numbers can be 0.</b><br><br>Please fix the \"before\" or the \"after\" number.</CENTER></font></html>",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE,
				    Resources.getLpIcon());

			picTimeMin.requestFocusInWindow();
			picTimeMin.setSelectionStart(0);
			picTimeMin.setSelectionEnd(picTimeMin.getText().length());
			return;
		}
		
		// Update the default profile
			if (profList.getSelectedValue() != null)
				DefaultOptions.setDefaultProfileName((String) profList.getSelectedValue());
		
		// Update the bef/aft minutes
		DefaultOptions.setMinPicTime(getPicTimeMin().getText());
		DefaultOptions.setMaxPicTime(getPicTimeMax().getText());

		// Update the override checkbox 
		DefaultOptions.setOverrideGPS(ovrdCheckBox.isSelected());

		// Update the override checkbox 
		DefaultOptions.setUpdateLastModified(updtDateCheckBox.isSelected());
		
		exitThis();
		

	}


	private void exitThis(){
		
		if (Resources.getOSName().equals("mac"))
			Application.getApplication().setEnabledPreferencesMenu(true);
		else
			parent.getJMenuBar().getMenu(0).getMenuComponent(2).setEnabled(true);

		this.setVisible(false);
	}
}
