package renderers;

import gui.Tab2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import processing.ExtractProduct;
import processing.FileAccess;
import processing.Product;

@SuppressWarnings("serial")
public class TTGui extends JPanel {

	private DefaultTableModel ttTableModel, productsTableModel;

	private String[] DesiredProducts;
	private Product[] products;
	private ExtractProduct pExtractor = new ExtractProduct();
	private int time;
	ArrayList<Integer> numOfProduct;
	private String hours;
	private String mins;
	private String nameSwap1, nameSwap2;
	private int thickness = 60;
	private int totalLength;
	private ArrayList<String> Names, key,
			uniqueNames;
	private ArrayList<Integer> Times;
	private int[][] dayTime;
	private String[][] sortedProducts;
	private int timeSwap1, timeSwap2;
	private int z;
	private int totaltime;
	private boolean toSwap = true;
	private double division;

	public TTGui(DefaultTableModel ttDefaultTableModel, Tab2 tab) {

		totalLength = tab.getGuiSize().width;
		thickness = tab.getGuiSize().height / 5;
		division = (double) totalLength / 1440;
		this.ttTableModel = ttDefaultTableModel;
		sortedProducts = new String[5][1000];
		dayTime = new int[5][2];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 2; y++) {
				dayTime[x][y] = 0;
			}
		}
	}

	public void createTimetable() {
		
		Times = new ArrayList<Integer>();
		Names = new ArrayList<String>();
		numOfProduct = new ArrayList<Integer>();
		uniqueNames = new ArrayList<String>();
		for (int i = 0; i < productsTableModel.getRowCount(); i++) {

			if ((productsTableModel.getValueAt(i, 1)).toString() != "0") {
				
				numOfProduct.add(Integer.parseInt((String) productsTableModel
						.getValueAt(i, 1)));
				uniqueNames.add((String) productsTableModel.getValueAt(i, 0));
				

			}
		}
		DesiredProducts = new String[uniqueNames.size()];
		
		for ( int i =  0; i < uniqueNames.size(); i++){
			DesiredProducts[i] = uniqueNames.get(i);
		}
		products = pExtractor.Extractproducts(DesiredProducts);
		System.out.println(products[0].profit);
		System.out.println(products.length);
			for (int i = 0; i < products.length; i++) {
				System.out.println("i   " + i);
				for (int x = 0; x < numOfProduct.get(i); x++) {
					Names.add(products[i].Name);
					Times.add(products[i].time);
				}
			}
		
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
		// first fit algorithm
		// create containers
		dayTime = new int[5][2];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 2; y++) {
				dayTime[x][y] = 0;
				dayTime[x][0] = getMinutes((String) ttTableModel.getValueAt(x,
						2))
						- getMinutes((String) ttTableModel.getValueAt(x, 1));
			}
		}
		// plop into containers
		boolean fits = true;
		boolean added = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 1000; y++) {
				sortedProducts[x][y] = " ";
			}
		}
		for (int b = 0; b < Names.size(); b++) {
			if (fits == true) {
				added = false;
				fits = false;
				for (int c = 0; c < 5; c++) {
					if (Times.get(b) <= dayTime[c][0] - dayTime[c][1]
							&& added == false) {
						for (int d = 0; d < 1000; d++) {
							if (sortedProducts[c][d].equals(" ")
									&& added == false) {
								sortedProducts[c][d] = Names.get(b) + "|"
										+ Times.get(b);
								dayTime[c][1] = dayTime[c][1] + Times.get(b);

								added = true;
								fits = true;
							}
						}
					}
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < 5; i++) {
			g.setColor(Color.WHITE);
			g.drawRect(1, i * thickness - 1, totalLength - 2, thickness - 2);
			g.setColor(Color.RED);

			time = getMinutes(ttTableModel.getValueAt(i, 2).toString());
			g.fillRect((int) (time * division), i * thickness, totalLength
					- (int) (time * division), thickness + 1);

			time = getMinutes(ttTableModel.getValueAt(i, 1).toString());
			g.fillRect(0, i * thickness, (int) (time * division), thickness + 1);

			g.setColor(Color.green);

			g.fillRect((int) (time * division), i * thickness,
					(int) (dayTime[i][1] * division), thickness + 1);
			g.setColor(Color.black);
			g.drawRect(0, i * thickness, totalLength, thickness);
		}

	}

	public void update(DefaultTableModel ttDefaultTableModel,
			DefaultTableModel productsTableModel) {

		this.ttTableModel = ttDefaultTableModel;
		this.productsTableModel = productsTableModel;
		createTimetable();
		saveImage();
		repaint();
	}

	public void printTimetable(DefaultTableModel ttDefaultTableModel,
			DefaultTableModel productsTableModel) {
		this.ttTableModel = ttDefaultTableModel;
		this.productsTableModel = productsTableModel;
		createTimetable();
		saveImage();
	}

	private void saveImage() {
		MakeKey();
		MakeTimetable();
	}

	private void MakeTimetable() {
		int width = 1800, height = 600;
		try {
			BufferedImage timeSheet = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D image = timeSheet.createGraphics();

			Font keyFont = new Font("TimesRoman", Font.BOLD, 30);
			Font timeFont = new Font("TimesRoman", Font.ITALIC, 20);
			Font prodNumbers = new Font("TimesRoman", Font.BOLD, 15);
			image.setFont(keyFont);
			String keyString = "";
			FontMetrics fontMetrics = image.getFontMetrics();
			int stringWidth = fontMetrics.stringWidth(keyString);
			int stringHeight = fontMetrics.getAscent();
			image.setPaint(Color.white);
			image.fillRect(0, 0, width, height);
			image.setPaint(Color.black);
			image.drawString("Key:", 100, 100 - stringHeight);
			int fromtop = 100;
			int fromleft = 300;
			int daywidth = 100;
			int daylength = 1440;

			for (int i = 0; i < key.size(); i++) {

				image.drawString(i + "         " + key.get(i), 100, 100
						+ stringHeight * i);
			}

			image.setFont(timeFont);
			for (int i = 0; i <= 24; i++) {
				image.setPaint(Color.black);
				image.drawLine(fromleft + i * 60, fromtop, fromleft + i * 60,
						fromtop - 20);
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
				image.fillRect(fromleft + time, fromtop + i * daywidth,
						daylength - time, daywidth);
				time = getMinutes(ttTableModel.getValueAt(i, 1).toString());
				image.fillRect(fromleft, fromtop + i * daywidth, time, daywidth);

				image.setPaint(Color.black);
				totaltime = time;
				for (int x = 0; x < 1000; x++) {
					if (sortedProducts[i][x] == " ") {
						break;
					} else {
						time = Integer
								.parseInt(sortedProducts[i][x]
										.substring(sortedProducts[i][x]
												.indexOf("|") + 1));
						totaltime = totaltime + time;
						image.drawLine(fromleft + totaltime, fromtop + i
								* daywidth, fromleft + totaltime, fromtop
								+ (i + 1) * daywidth);
						image.drawString(getkey(sortedProducts[i][x].substring(
								0, sortedProducts[i][x].indexOf("|"))),
								fromleft + totaltime - (int) (time / 2),
								fromtop + i * daywidth + (int) (daywidth * 0.5));
					}
				}
				image.setPaint(Color.black);
				image.drawLine(fromleft, fromtop + i * daywidth, fromleft
						+ daylength, fromtop + i * daywidth);

			}
			image.drawLine(fromleft, fromtop + 5 * daywidth, fromleft
					+ daylength, fromtop + 5 * daywidth);
			ImageIO.write(timeSheet, "PNG", new File("Images/Timetable.jpg"));

		} catch (IOException ie) {
			ie.printStackTrace();
		}

		System.out.println("we DID IT");
	}

	private String getkey(String t) {
		for (int i = 0; i < key.size(); i++) {
			if (t.equals(key.get(i))) {
				t = Integer.toString(i);
				break;
			}
		}
		return t;
	}

	private void MakeKey() {
		key = new ArrayList<String>();
		key = Names;

		// add elements to al, including duplicates
		Set<String> hs = new HashSet<>();
		hs.addAll(key);
		key.clear();
		key.addAll(hs);

	}

	private int getMinutes(String time) {

		hours = time.substring(0, 2);
		mins = time.substring(3);
		// System.out.println(mins);
		int totMins = Integer.parseInt(hours) * 60 + Integer.parseInt(mins);

		return totMins;
	}
}
