package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Edward
 * @version 1.0 jan9, 2016
 * 
 */

@SuppressWarnings("serial")
public class TabbedPanel extends JFrame {
	/** JTabbedPane contains both panels */
	private JTabbedPane tabbedPane;
	/**
	 * panel1 is for stock management, panel2 is for product managment and
	 * timetable creation
	 */
	private JPanel panel1, panel2;
	/**
	 * mFrame is the highest level of the Gui which encompases the tabbed pane
	 */
	private JFrame mFrame;
	/** icon is the thumbnail for the program */
	static ImageIcon Icon = new ImageIcon("Images/test.png");

	
	
	public TabbedPanel() {
		int width = (int) 800;
		int height = (int) 540;
		mFrame = new JFrame("Iventorize");
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setLayout(new MigLayout());
		setTitle("Inventorize");
		setSize(width, height);
		setBackground(Color.gray);
		mFrame.setIconImage(Icon.getImage());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		// Create the tab pages
		createtabs();
		mFrame.setIconImage(Icon.getImage());
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Stocks", panel1);
		tabbedPane.addTab("Timetable", panel2);

		mFrame.setIconImage(Icon.getImage());

		topPanel.add(tabbedPane, BorderLayout.NORTH);

	}

	private void createtabs() {
		Tab1 t1 = new Tab1();
		panel1 = t1.create();
		Tab2 t2 = new Tab2();
		panel2 = t2.create();
	}

	public static void main(String args[]) {
		// Create an instance of the test application
		TabbedPanel mainFrame = new TabbedPanel();
		mainFrame.setIconImage(Icon.getImage());
		mainFrame.setVisible(true);
		mainFrame.setResizable(true);
	}
}
