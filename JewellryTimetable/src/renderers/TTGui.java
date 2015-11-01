package renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import processing.FileAccess;

@SuppressWarnings("serial")
public class TTGui extends JPanel {

	private DefaultTableModel ttTableModel, productsTableModel;

	private int time;
	private String hours;
	private String mins;
	private String nameSwap1,nameSwap2;
	private int thickness = 20;
	private int totalLength = 400;
	private ArrayList<String> Names;
	private ArrayList<Integer> Times;
	private FileAccess Products;
	private int timeSwap1, timeSwap2;
	private int z;
	private boolean toSwap = true;

	private double division = 0.27778;

	public TTGui(DefaultTableModel ttDefaultTableModel) {
		// TODO Auto-generated constructor stub
		this.ttTableModel = ttDefaultTableModel;

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
			nameSwap2 = Names.get(y+1);
			z = y;
			if (timeSwap2 > timeSwap1) {
				
				while (toSwap == true) {
					Times.set(z, timeSwap2);
					Times.set(z + 1, timeSwap1);
					Names.set(z, nameSwap2);
					Names.set(z+1, nameSwap1);
					
					if (z == 0){
						toSwap = false;
					}else if(Times.get(z) > Times.get(z-1) == false){
						toSwap = false;
					}else{
						z--;
					}
				}

			}
			
			for (int a = 0; a < Names.size(); a++) {
				System.out.print("\t" + Times.get(a));
				
			}
			System.out.println(" ");

		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < 5; i++) {
			g.setColor(Color.WHITE);
			g.drawRect(1, i * thickness - 1, totalLength - 2, thickness - 2);

			hours = (ttTableModel.getValueAt(i, 1)).toString().substring(0, 2);
			mins = (ttTableModel.getValueAt(i, 1)).toString().substring(3);
			time = Integer.parseInt(hours) * 60 + Integer.parseInt(mins);

			g.setColor(Color.RED);
			g.fillRect(0, i * thickness, (int) (time * division), thickness + 1);

			hours = (ttTableModel.getValueAt(i, 2)).toString().substring(0, 2);
			mins = (ttTableModel.getValueAt(i, 2)).toString().substring(3);
			time = Integer.parseInt(hours) * 60 + Integer.parseInt(mins);
			System.out.println(totalLength - (int) (time * division));

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

}
