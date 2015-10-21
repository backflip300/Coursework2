package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import processing.GetNewStock;
import renderers.TTGui;
import renderers.TTMRenderer;
import tableModels.Tab2TableModel;
import tableModels.timetableTableModel;

public class Tab2 {

	private JPanel tab2;
	private JTable table, ttable;
	private Tab2TableModel tModel;
	private timetableTableModel ttModel;
	private static DefaultTableModel dtablemodel, dtablemodel2;

	private GetNewStock getNewStock;
	private TTMRenderer cellRenderer;
	private TTGui ttGui;
	private Graphics g;

	public Tab2() {
	}

	@SuppressWarnings("serial")
	public JPanel create() {
		cellRenderer = new TTMRenderer();
		// cellRenderer.setBackground(Color.BLUE);

		// layout
		tab2 = new JPanel();
		tab2.setLayout(new MigLayout());

		//

		// create products table
		tModel = new Tab2TableModel();
		dtablemodel = new DefaultTableModel(tModel.data, new Object[] {
				"product", "# to produce" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		table = new JTable(dtablemodel);
		JScrollPane scrollPane = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);

		// create timetable table
		ttModel = new timetableTableModel();
		dtablemodel2 = new DefaultTableModel(ttModel.data, new Object[] {
				"timetable", "start", "finish" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= 1;
			}

		};
		ttable = new JTable(dtablemodel2);
		JScrollPane ttscrollPane = new JScrollPane(ttable);
		ttable.getTableHeader().setReorderingAllowed(false);
		ttable.setDefaultRenderer(Object.class, cellRenderer);

		// create timetable renderer
		ttGui = new TTGui(dtablemodel2);
		ttGui.setPreferredSize(new Dimension(500, 400));
		ttGui.setBackground(Color.white);
		ttGui.paint(g);

		// create buttons
		JButton NewProduct = new JButton("New Product");
		// NewProduct.setEnabled(false);
		NewProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getNewStock = new GetNewStock(dtablemodel);
				getNewStock.addStock();
			}
		});
		// set sizes

		scrollPane.setPreferredSize(new Dimension(200, 400));
		ttscrollPane.setPreferredSize(new Dimension(300, 400));
		tab2.add(scrollPane);
		tab2.add(ttscrollPane);
		tab2.add(ttGui, "wrap");
		tab2.add(NewProduct);

		ttable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				ttGui.update(dtablemodel2);
			}
		});

		// adding
		return tab2;

	}

	public static void addRow(String Product) {
		dtablemodel.addRow(new Object[] { Product, "0" });
	}

}
