package renderers;

import gui.Tab2;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import processing.FileAccess;

@SuppressWarnings("serial")
public class TTGui extends JPanel {

	private DefaultTableModel ttTableModel, productsTableModel;

	private int time;
	private String hours;
	private int start, end, kilnStart;
	private String mins;
	private String nameSwap1, nameSwap2;
	private int thickness = 30;
	private int totalLength, kilnSize = 1000, kilnFill = 0;
	private ArrayList<String> Names;
	private ArrayList<Integer> Times, kilnSizes;
	private int[][] dayTime;
	private String[][] sortedProducts;
	private FileAccess Products;
	private int timeSwap1, timeSwap2, ksSwap1, ksSwap2;
	private int z;
	private boolean toSwap = true;
	private double division;

	public TTGui(DefaultTableModel ttDefaultTableModel, Tab2 tab) {
		// TODO Auto-generated constructor stub
		totalLength = tab.getGuiSize().width;
		thickness = tab.getGuiSize().height / 5;
		System.out.println(totalLength);
		division = (double) totalLength / 1440;
		System.out.println(division);
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

		Products = new FileAccess(Paths.get("TextFiles/Products.txt"));
		Times = new ArrayList<Integer>();
		Names = new ArrayList<String>();
		kilnSizes = new ArrayList<Integer>();
		ArrayList<String> Temp = new ArrayList<String>();
		Temp = Products.sReadFileData();
		for (int i = 0; i < Temp.size(); i++) {
			// System.out.println(productsTableModel.getValueAt(i, 1));
			if ((productsTableModel.getValueAt(i, 1)).toString() != "0") {

				int numOfProduct = Integer.parseInt((String) productsTableModel
						.getValueAt(i, 1));
				int begin = Temp.get(i).indexOf("]") + 1;
				int kilnStart = Temp.get(i).indexOf("|");
				// System.out.println(Integer.parseInt((String)
				// productsTableModel.getValueAt(i, 1)));
				for (int x = 0; x < numOfProduct; x++) {
					Names.add((String) productsTableModel.getValueAt(i, 0));
					Times.add(Integer.parseInt(Temp.get(i).substring(begin,
							kilnStart)));
					kilnSizes.add(Integer.parseInt(Temp.get(i).substring(
							kilnStart + 1)));
				}
			}

		}
		System.out.println("");
		for (int y = 0; y < Times.size() - 1; y++) {
			toSwap = true;
			timeSwap1 = Times.get(y);
			timeSwap2 = Times.get(y + 1);
			nameSwap1 = Names.get(y);
			nameSwap2 = Names.get(y + 1);

			// System.out.println(y);
			z = y;
			if (timeSwap2 > timeSwap1) {
				while (toSwap == true) {
					Times.set(z, timeSwap2);
					Times.set(z + 1, timeSwap1);
					Names.set(z, nameSwap2);
					Names.set(z + 1, nameSwap1);
					kilnSizes.set(z, ksSwap2);
					kilnSizes.set(z + 1, ksSwap1);
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
						ksSwap1 = kilnSizes.get(z);
						ksSwap2 = kilnSizes.get(z + 1);

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
		// boolean allAdded = false;
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
						// System.out.println("got here" + dayTime[c][1]);
						for (int d = 0; d < 1000; d++) {
							if (sortedProducts[c][d].equals(" ")
									&& added == false) {
								sortedProducts[c][d] = Names.get(b);
								dayTime[c][1] = dayTime[c][1] + Times.get(b);
								kilnFill += kilnSizes.get(b);

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
		// repaint();
		this.ttTableModel = ttDefaultTableModel;
		this.productsTableModel = productsTableModel;
		createTimetable();
		repaint();
	}

	private int getMinutes(String time) {

		hours = time.substring(0, 2);
		mins = time.substring(3);
		// System.out.println(mins);
		int totMins = Integer.parseInt(hours) * 60 + Integer.parseInt(mins);

		return totMins;
	}
}
