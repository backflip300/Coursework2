package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;

public class TabbedPanel extends JFrame {

	private static final long serialVersionUID = 4251822216194254417L;
	private JTabbedPane tabbedPane;
	private JPanel panel1, panel2;
	private JFrame mFrame;
	static ImageIcon Icon = new ImageIcon("Images/lol.jpg");

	public TabbedPanel() {
		int width = (int) 930;
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
		getContentPane().add(topPanel);		// Create the tab pages
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
