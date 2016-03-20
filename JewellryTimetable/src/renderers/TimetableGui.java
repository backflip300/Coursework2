package renderers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import gui.Tab2;
import gui.TabbedPanel;
import processing.ExtractProduct;
import processing.FileAccess;
import processing.Product;

/**
 * The Class TTGui.
 */
@SuppressWarnings("serial")
public class TimetableGui extends JPanel {

	/** The products table model. */
	private DefaultTableModel ttTableModel, productsTableModel;

	/** The tabbed panel which contains 3 tabs. */
	private TabbedPanel tabbedPanel;

	/** The Desired products contains all products desired to create. */
	private String[] DesiredProducts;

	/** The products contains all products that exist. */
	private Product[] products;

	/** The p extractor extracts products from products text file. */
	private ExtractProduct pExtractor = new ExtractProduct();

	/** The time a product takes to create, used in creating guis. */
	private int time;

	/**
	 * The num of product contains the amount of each product that needs to be
	 * created.
	 */
	ArrayList<Integer> numOfProduct;

	/**
	 * The stocks file allows access to the Stocks text file for
	 * reading/writing.
	 */
	private FileAccess stocksFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));

	/** The hours in a certain time. */
	private String hours;

	/** The mins in a certain time. */
	private String mins;

	/**
	 * The timetablecolor determines the colour of the products in the preview,
	 * turns red when not all fit.
	 */
	private Color timetablecolor = Color.green;

	/** The thickness of each day on the gui. */
	private int thickness;

	/** The total length of the timetable preview gui. */
	private int totalLength;

	/**
	 * The insufficient stock contains all stocks with less than required
	 * amount.
	 */
	private ArrayList<String> insufficientStock;

	/** The Names of each stock to create. */
	private ArrayList<String> Names;

	/** The Times of each stock to create. */
	private ArrayList<Integer> Times;

	/** The key on the timetable gui. */
	private ArrayList<String> key;

	/** The day time holds the total time and amount filled in each day. */
	private int[][] dayTime;

	/**
	 * The sorted products contains each product sorted into its day on the
	 * timetable.
	 */
	private String[][] sortedProducts;

	/** The stock amount holds the quantity of each stock. */

	private ArrayList<Integer> stockAmount = new ArrayList<Integer>();

	/**
	 * The enoughstock determines whether there is enough stock to create all
	 * products.
	 */
	private boolean enoughstock;

	/** The fits determines whether all products fit into the availbe time. */
	private boolean fits;

	/**
	 * Instantiates a new TT gui.
	 *
	 * @param ttDefaultTableModel
	 *            the timetable default table model
	 * @param tab
	 *            the tab2
	 */
	public TimetableGui(DefaultTableModel ttDefaultTableModel, Tab2 tab) {

		totalLength = tab.getGuiSize().width;
		thickness = tab.getGuiSize().height / 5;

		this.ttTableModel = ttDefaultTableModel;
		// initialize arrays.
		sortedProducts = new String[5][3600];
		dayTime = new int[5][2];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 2; y++) {
				dayTime[x][y] = 0;
			}
		}
	}

	/**
	 * Creates the timetable.
	 */
	public void createTimetable() {
		timetablecolor = Color.green;
		getDesiredproducts();
		shuttleSort();
		filldays();
		if (fits == true) {
			checkStock();
		}

	}

	/**
	 * Check stock.
	 */
	private void checkStock() {

		// temporary array hold text file
		ArrayList<String> stockFile = new ArrayList<String>();
		// name of each stock
		ArrayList<String> stockName = new ArrayList<String>();
		// clear Arraylists
		stockFile.clear();
		stockName.clear();
		stockAmount.clear();

		insufficientStock = new ArrayList<String>();
		stockFile = stocksFile.sReadFileData();
		// Add names and quantities of each stock
		for (int i = 0; i < stockFile.size() / 2; i++) {
			stockName.add(stockFile.get(2 * i));
			stockAmount.add(Integer.parseInt(stockFile.get(2 * i + 1)));
		}
		// amount of each stock to be take away
		int totalAmount = 0;
		// Take away each stock needed by products being produced.
		for (Product toProduce : products) {
			int i = 0;

			for (int s = 0; s < toProduce.stocks.length; s++) {

				totalAmount = numOfProduct.get(i) * toProduce.quantity[s];
				for (int x = 0; x < stockName.size(); x++) {
					if (stockName.get(x).equals(toProduce.stocks[s])) {
						stockAmount.set(x, stockAmount.get(x) - totalAmount);
					}
				}

			}
			i++;
		}

		// check if all products are still non-negative
		enoughstock = true;
		for (int j = 0; j < stockAmount.size(); j++) {

			if (stockAmount.get(j) < 0) {
				insufficientStock.add(stockName.get(j));
				enoughstock = false;
			}
		}

	}

	/**
	 * Filldays putting products into availble time and check if they all fit.
	 */
	private void filldays() {
		dayTime = new int[5][2];
		// Get amount of time availbe in each day.
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 2; y++) {
				dayTime[x][y] = 0;
				dayTime[x][0] = getMinutes((String) ttTableModel.getValueAt(x, 2))
						- getMinutes((String) ttTableModel.getValueAt(x, 1));
			}
		}
		// First fit algorithm on products to fit them into days
		fits = true;
		boolean added = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 3600; y++) {
				sortedProducts[x][y] = " ";
			}
		}
		fitting: for (int b = 0; b < Names.size(); b++) {
			if (fits == true) {
				added = false;
				fits = false;
				for (int c = 0; c < 5; c++) {
					if (Times.get(b) <= dayTime[c][0] - dayTime[c][1] && added == false) {
						for (int d = 0; d < 1000; d++) {
							if (sortedProducts[c][d].equals(" ") && added == false) {
								sortedProducts[c][d] = Names.get(b) + "|" + Times.get(b);
								dayTime[c][1] = dayTime[c][1] + Times.get(b);

								added = true;
								fits = true;
							}
						}
					}
				}

			}
			if (fits == false) {
				break fitting;
			}
		}

	}

	/**
	 * Shuttle sort sorts the products into descending order, moving there times
	 * aswell.
	 */
	private void shuttleSort() {

		int timeSwap1, timeSwap2;
		String nameSwap1, nameSwap2;
		boolean toSwap = true;
		int z;
		for (int y = 0; y < Times.size() - 1; y++) {
			toSwap = true;
			timeSwap1 = Times.get(y);
			timeSwap2 = Times.get(y + 1);
			nameSwap1 = Names.get(y);
			nameSwap2 = Names.get(y + 1);
			z = y;
			if (timeSwap2 > timeSwap1) {
				while (toSwap == true) {
					Times.set(z, timeSwap2);
					Times.set(z + 1, timeSwap1);
					Names.set(z, nameSwap2);
					Names.set(z + 1, nameSwap1);
					if (z == 0) {
						toSwap = false;
					} else if (Times.get(z) > Times.get(z - 1) == false) {
						toSwap = false;
					} else {
						z--;
						timeSwap1 = Times.get(z);
						timeSwap2 = Times.get(z + 1);
						nameSwap1 = Names.get(z);
						nameSwap2 = Names.get(z + 1);
					}
				}
			}
		}
	}

	/**
	 * Gets the desiredproducts from the table.
	 *
	 * @return the desiredproducts
	 */
	private void getDesiredproducts() {

		Names = new ArrayList<String>();
		Times = new ArrayList<Integer>();
		numOfProduct = new ArrayList<Integer>();
		ArrayList<String> uniqueNames = new ArrayList<String>();
		for (int i = 0; i < productsTableModel.getRowCount(); i++) {

			// If amount to produce does not equal 0 add the product and it's
			// quantity to produce.
			if ((productsTableModel.getValueAt(i, 1)).toString() != "0") {

				numOfProduct.add(Integer.parseInt((String) productsTableModel.getValueAt(i, 1)));
				uniqueNames.add((String) productsTableModel.getValueAt(i, 0));

			}
		}
		DesiredProducts = new String[uniqueNames.size()];

		for (int i = 0; i < uniqueNames.size(); i++) {
			DesiredProducts[i] = uniqueNames.get(i);
		}
		products = pExtractor.Extractproducts(DesiredProducts);
		// Put each product to produce name and time into 2 arraylists.
		for (int i = 0; i < products.length; i++) {
			for (int x = 0; x < numOfProduct.get(i); x++) {
				Names.add(products[i].Name);
				Times.add(products[i].time);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double division;
		division = (double) totalLength / 1440;

		for (int i = 0; i < 5; i++) {
			g.setColor(Color.WHITE);
			g.drawRect(1, i * thickness - 1, totalLength - 2, thickness - 2);
			g.setColor(Color.RED);

			time = getMinutes(ttTableModel.getValueAt(i, 2).toString());
			g.fillRect((int) (time * division), i * thickness, totalLength - (int) (time * division), thickness + 1);

			time = getMinutes(ttTableModel.getValueAt(i, 1).toString());
			g.fillRect(0, i * thickness, (int) (time * division), thickness + 1);

			g.setColor(timetablecolor);

			g.fillRect((int) (time * division), i * thickness, (int) (dayTime[i][1] * division), thickness + 1);
			g.setColor(Color.black);
			g.drawRect(0, i * thickness, totalLength, thickness);
		}

	}

	/**
	 * Update.
	 *
	 * @param timetableDefaultTableModel
	 *            the timetable default table model
	 * @param productsTableModel
	 *            the products table model
	 * @param create
	 *            Determines wheter to overwrite the old timetable or just
	 *            validate
	 * @param tabbedPanel
	 *            the tabbedpanel
	 * @param createtimetable
	 *            the createtimetable button
	 */
	public void update(DefaultTableModel timetableDefaultTableModel, DefaultTableModel productsTableModel,
			boolean create, TabbedPanel tabbedPanel, JButton createtimetable) {
		this.tabbedPanel = tabbedPanel;
		this.ttTableModel = timetableDefaultTableModel;
		this.productsTableModel = productsTableModel;
		createTimetable();
		String buttonText = "";
		System.out.println(fits);
		if (fits == false) {
			timetablecolor = Color.red;
			buttonText = "Insufficient time";
			createtimetable.setEnabled(false);
		} else if (enoughstock == false) {

			buttonText = "Insufficient stock: ";
			for (String insufficient : insufficientStock) {
				buttonText = buttonText + insufficient + ", ";
			}
			createtimetable.setEnabled(false);
		} else {
			buttonText = "Create new timetable";
			createtimetable.setEnabled(true);
		}
		createtimetable.setText(buttonText);
		if (create == true) {
			saveImage();
			removestocks();
		}

		repaint();
	}

	/**
	 * Removestocks takes away the stocks needed to create the products from the
	 * current amount in stock.
	 */
	private void removestocks() {
		System.out.println(stockAmount.size());
		for (int i = 0; i < stockAmount.size(); i++) {
			stocksFile.sEditline(stockAmount.get(i).toString(), 2 * i + 1);
		}

	}

	/**
	 * Prints the timetable creates and overwrites the old timetable with the
	 * new timetable.
	 *
	 * @param ttDefaultTableModel
	 *            the tt default table model
	 * @param productsTableModel
	 *            the products table model
	 */
	public void printTimetable(DefaultTableModel ttDefaultTableModel, DefaultTableModel productsTableModel) {
		this.ttTableModel = ttDefaultTableModel;
		this.productsTableModel = productsTableModel;
		createTimetable();
		saveImage();
	}

	/**
	 * Save image.
	 */
	private void saveImage() {
		MakeKey();
		MakeTimetable();
	}

	/**
	 * Make timetable.
	 */
	private void MakeTimetable() {
		int width = 1800, height = 600;
		try {
			BufferedImage timeSheet = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			Graphics2D image = timeSheet.createGraphics();

			Font keyFont = new Font("TimesRoman", Font.BOLD, 30);
			Font timeFont = new Font("TimesRoman", Font.ITALIC, 20);
			Font prodNumbers = new Font("TimesRoman", Font.BOLD, 15);
			image.setFont(keyFont);
			FontMetrics fontMetrics = image.getFontMetrics();
			int stringHeight = fontMetrics.getAscent();
			image.setPaint(Color.white);
			image.fillRect(0, 0, width, height);
			image.setPaint(Color.black);
			image.drawString("Key:", 100, 100 - stringHeight);
			int fromtop = 100;
			int fromleft = 300;
			int daywidth = 100;
			int daylength = 1440;
			int totaltime;

			for (int i = 0; i < key.size(); i++) {

				image.drawString(i + "         " + key.get(i), 100, 100 + stringHeight * i);
			}

			image.setFont(timeFont);
			for (int i = 0; i <= 24; i++) {
				image.setPaint(Color.black);
				image.drawLine(fromleft + i * 60, fromtop, fromleft + i * 60, fromtop - 20);
				image.drawString(i + ":00", fromleft + i * 60, fromtop - 25);

				if (i % 2 == 1) {
					image.setPaint(new Color(230, 230, 255));
					image.fillRect(fromleft + i * 60, fromtop, 60, daywidth * 5);
				}
			}
			image.setFont(prodNumbers);
			for (int i = 0; i < 5; i++) {
				image.setPaint(Color.RED);
				time = getMinutes(ttTableModel.getValueAt(i, 2).toString());
				image.fillRect(fromleft + time, fromtop + i * daywidth, daylength - time, daywidth);
				time = getMinutes(ttTableModel.getValueAt(i, 1).toString());
				image.fillRect(fromleft, fromtop + i * daywidth, time, daywidth);

				image.setPaint(Color.black);
				totaltime = time;
				for (int x = 0; x < 1000; x++) {
					if (sortedProducts[i][x] == " ") {
						break;
					} else {
						time = Integer.parseInt(sortedProducts[i][x].substring(sortedProducts[i][x].indexOf("|") + 1));
						totaltime = totaltime + time;
						image.drawLine(fromleft + totaltime, fromtop + i * daywidth, fromleft + totaltime,
								fromtop + (i + 1) * daywidth);
						image.drawString(getkey(sortedProducts[i][x].substring(0, sortedProducts[i][x].indexOf("|"))),
								fromleft + totaltime - (int) (time / 2),
								fromtop + i * daywidth + (int) (daywidth * 0.5));
					}
				}
				image.setPaint(Color.black);
				image.drawLine(fromleft, fromtop + i * daywidth, fromleft + daylength, fromtop + i * daywidth);

			}
			image.drawLine(fromleft, fromtop + 5 * daywidth, fromleft + daylength, fromtop + 5 * daywidth);
			ImageIO.write(timeSheet, "PNG", new File("Images/Timetable.png"));

		} catch (IOException ie) {
			ie.printStackTrace();
		}
		tabbedPanel.updateTimetable();
	}

	/**
	 * Make key for the timetable.
	 */
	private void MakeKey() {
		key = new ArrayList<String>();
		key = Names;

		// add elements to al, including duplicates
		Set<String> hs = new HashSet<>();
		hs.addAll(key);
		key.clear();
		key.addAll(hs);

	}

	/**
	 * Gets the key for the timetable.
	 *
	 * @param t
	 *            the t
	 * @return the key
	 */
	private String getkey(String t) {
		for (int i = 0; i < key.size(); i++) {
			if (t.equals(key.get(i))) {
				t = Integer.toString(i);
				break;
			}
		}
		return t;
	}

	/**
	 * Gets the minutes from a time in the form "00:00".
	 *
	 * @param time
	 *            the time
	 * @return the minutes
	 */
	private int getMinutes(String time) {

		hours = time.substring(0, 2);
		mins = time.substring(3);
		int totMins = Integer.parseInt(hours) * 60 + Integer.parseInt(mins);

		return totMins;
	}
}
