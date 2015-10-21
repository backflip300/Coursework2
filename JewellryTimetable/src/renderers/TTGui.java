package renderers;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TTGui extends JPanel {

	private DefaultTableModel ttTableModel;
	private int time;
	private String hours;
	private String mins;
	private int thickness = 20;
	private int totalLength = 400;
	private double division = 0.27778;

	public TTGui(DefaultTableModel ttDefaultTableModel) {
		// TODO Auto-generated constructor stub
		this.ttTableModel = ttDefaultTableModel;

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

			g.fillRect((int) (time * division), i * thickness, totalLength
					- (int) (time * division), thickness + 1);

			g.setColor(Color.black);
			g.drawRect(0, i * thickness, totalLength, thickness);
		}

	}

	public void update(DefaultTableModel ttDefaultTableModel) {
		// repaint();
		this.ttTableModel = ttDefaultTableModel;
		repaint();
	}

}
