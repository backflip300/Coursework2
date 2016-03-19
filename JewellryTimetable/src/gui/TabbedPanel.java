package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class TabbedPanel.
 *
 * @author Edward
 * @version 1.0 jan9, 2016
 */

@SuppressWarnings("serial")
public class TabbedPanel extends JFrame {

	/** JTabbedPane contains both panels. */
	private JTabbedPane tabbedPane;

	/**
	 * Panel1 is for stock management, panel2 is for product management and
	 * timetable creation and panel3 displays a scaled image of the current
	 * timetable.
	 */
	private JPanel panel1, panel2, panel3;

	/**
	 * mFrame is the highest level of the gui which encompass the tabbed pane.
	 */
	private JFrame mFrame;

	/** Icon is the thumbnail for the program. */
	static ImageIcon Icon = new ImageIcon("Images/test.png");

	/** The tab1. */
	private Tab1 tab1;

	/** The tab2. */
	private Tab2 tab2;

	/** The tab3. */
	private Tab3 tab3;

	/**
	 * Instantiates a new tabbed panel. Setting dimensions, layout, etc.
	 */
	public TabbedPanel() {

		int width = (int) 800;
		int height = (int) 540;
		// Add title and close button action
		mFrame = new JFrame("Iventorize");
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setLayout(new MigLayout());
		setTitle("Inventorize");
		setSize(width, height);
		setBackground(Color.gray);
		// Set thumbnail image.
		mFrame.setIconImage(Icon.getImage());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		// Create the tabs
		createtabs();
		mFrame.setIconImage(Icon.getImage());
		// Create a tabbed pane.
		tabbedPane = new JTabbedPane();
		// Add the tabs to the tabbed pane.
		tabbedPane.addTab("Stocks", panel1);
		tabbedPane.addTab("Products", panel2);
		tabbedPane.addTab("Timetable", panel3);
		tabbedPane.addChangeListener(tabchange);
		mFrame.setIconImage(Icon.getImage());

		topPanel.add(tabbedPane, BorderLayout.NORTH);

	}

	/**
	 * Create tabs.
	 */
	private void createtabs() {
		tab1 = new Tab1();
		panel1 = tab1.create();
		tab2 = new Tab2();
		panel2 = tab2.create(this);
		tab3 = new Tab3();
		panel3 = tab3.create();

	}

	/**
	 * Update timetable removes old timetable tab and creates a new one.
	 */
	public void updateTimetable() {
		tab3 = new Tab3();
		panel3 = tab3.create();
		tabbedPane.remove(2);

		tabbedPane.addTab("Timetable", panel3);

	}

	/**
	 * Sets the panel3.
	 *
	 * @param panel3
	 *            the new panel3
	 */
	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}

	/** The tabchange. */
	ChangeListener tabchange = new ChangeListener() {

		@Override
		// updates stock incase of change changed
		public void stateChanged(ChangeEvent changeEvent) {
			JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			int index = sourceTabbedPane.getSelectedIndex();
			tab1.update();
		}
	};

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		// Create an instance of the test application
		TabbedPanel mainFrame = new TabbedPanel();
		mainFrame.setIconImage(Icon.getImage());
		mainFrame.setVisible(true);
		mainFrame.setResizable(true);
	}

}
