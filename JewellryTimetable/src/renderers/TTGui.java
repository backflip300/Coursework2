package renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import processing.FileAccess;

@SuppressWarnings("serial")
public class TTGui extends JPanel {

	private DefaultTableModel ttTableModel, productsTableModel;

	private int time;
	private String hours;
	private int start, end;
	private String mins;
	private String nameSwap1, nameSwap2;
	private int thickness = 20;
	private int totalLength = 400;
	private ArrayList<String> Names;
	private ArrayList<Integer> Times;
	private int[][] dayTime;
	private int[] timeUsed;
	private String[][] sortedProducts;
	private FileAccess Products;
	private int timeSwap1, timeSwap2;
	private int z;
	private boolean toSwap = true;

	private double division = 0.27778;

	public TTGui(DefaultTableModel ttDefaultTableModel) {
		// TODO Auto-generated constructor stub
		this.ttTableModel = ttDefaultTableModel;
		sortedProducts = new String[5][1000];
		timeUsed = new int[5];

	}

	public void createTimetable() {

		Products = new FileAccess(Paths.get("TextFiles/Products.txt"));
		Times = new ArrayList<Integer>();
		Names = new ArrayList<String>();
		ArrayList<String> Temp = new ArrayList<String>();
		Temp = Products.sReadFileData();
		for (int i = 0; i < Temp.size(); i++) {
			// System.out.println(productsTableModel.getValueAt(i, 1));
			if ((productsTableModel.getValueAt(i, 1)).toString() != "0") {

				int numOfProduct = Integer.parseInt((String) productsTableModel.getValueAt(i, 1));
				int begin = Temp.get(i).indexOf("]") + 1;
				System.out.println(Integer.parseInt((String) productsTableModel.getValueAt(i, 1)));
				for (int x = 0; x < numOfProduct; x++) {
					Names.add((String) productsTableModel.getValueAt(i, 0));
					Times.add(Integer.parseInt(Temp.get(i).substring(begin)));
				}
			}

		}

		for (int a = 0; a < Names.size(); a++) {
			System.out.println(Names.get(a) + "\t" + Times.get(a));
		}

		for (int y = 0; y < Times.size() - 1; y++) {
			toSwap = true;

			timeSwap1 = Times.get(y);
			timeSwap2 = Times.get(y + 1);
			nameSwap1 = Names.get(y);
			nameSwap2 = Names.get(y + 1);
			System.out.println(y);
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
			for (int a = 0; a < Names.size(); a++) {
				System.out.print("\t" + Times.get(a));

			}
			System.out.println(" ");

		}

		// first fit algorithm
		// create containers
		dayTime = new int[5][2];
		for (int x = 0; x < 5; x++) {
			timeUsed[x] = 0;

			for (int y = 0; y < 2; y++) {
				dayTime[x][y] = 0;
			}
		}

		for (int a = 0; a < 5; a++) {

			dayTime[a][0] = getMinutes((String) ttTableModel.getValueAt(a, 2))
					- getMinutes((String) ttTableModel.getValueAt(a, 1));
			System.out.println(dayTime[a][0]);
		}
		// plop into containers
		boolean fits = true;
		boolean allAdded = false;
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
					if (Times.get(b) <= dayTime[c][0] - dayTime[c][1] && added == false) {
						// System.out.println("got here" + dayTime[c][1]);
						dayTime[c][1] = dayTime[c][1] + Times.get(c);
						for (int d = 0; d < sortedProducts.length; d++) {
							if (sortedProducts[c][d].equals(" ") && added == false) {

								sortedProducts[c][d] = Names.get(b);
								timeUsed[c] = timeUsed[c] + Times.get(b);
								added = true;
								fits = true;
							}
						}
					}
				}
			}
		}
		for (int x = 0; x < 5; x++) {
			System.out.println(timeUsed[x] + "\t " + dayTime[x][0]);
			for (int y = 0; y < 100; y++) {
				if (sortedProducts[x][y].equals(" ") == false) {
					System.out.print(sortedProducts[x][y] + "\t");
				}
			}
			System.out.println("");
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < 5; i++) {
			g.setColor(Color.WHITE);
			g.drawRect(1, i * thickness - 1, totalLength - 2, thickness - 2);
			time = getMinutes(ttTableModel.getValueAt(i, 1).toString());
			g.setColor(Color.RED);
			g.fillRect(0, i * thickness, (int) (time * division), thickness + 1);
			time = getMinutes(ttTableModel.getValueAt(i, 2).toString());
			// System.out.println(totalLength - (int) (time * division));

			g.fillRect((int) (time * division), i * thickness, totalLength - (int) (time * division), thickness + 1);

			g.setColor(Color.black);
			g.drawRect(0, i * thickness, totalLength, thickness);
		}

	}

	public void update(DefaultTableModel ttDefaultTableModel, DefaultTableModel productsTableModel) {
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
