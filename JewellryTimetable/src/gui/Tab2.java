package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import processing.NewProduct;
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
	Dimension GuiSize = new Dimension(600, 200);
	private NewProduct newProduct;
	private TTMRenderer cellRenderer;
	private TTGui ttGui, ttGui2;
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
		ttGui = new TTGui(dtablemodel2,this);
		ttGui.setPreferredSize(GuiSize);
		ttGui.setBackground(Color.white);
		ttGui.paint(g);
		ttGui2 = new TTGui(dtablemodel2,this);
		ttGui2.setPreferredSize(GuiSize);
		ttGui2.setBackground(Color.white);
		ttGui2.paint(g);

		// create buttons
		JButton NewProduct = new JButton("New Product");
		JButton createTimetable = new JButton("Create Timetable");
		
		// NewProduct.setEnabled(false);
		NewProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newProduct = new NewProduct(dtablemodel);
				newProduct.addStock();
			}
		});

		scrollPane.setPreferredSize(new Dimension(200, 450));
		scrollPane.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
		ttscrollPane.setPreferredSize(new Dimension(600, 103));
		tab2.add(scrollPane, "cell 0 0 1 4");
		tab2.add(ttscrollPane, "cell 1 0 ");
		tab2.add(ttGui,"cell 1 1");
		tab2.add(createTimetable, "cell 1 3,grow");
		tab2.add(NewProduct, "cell 1 2, grow");
		// timetablemodel listener
		ttable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				ttGui.update(dtablemodel2, dtablemodel);

			}
		});
		// products table listener
		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				ttGui.update(dtablemodel2, dtablemodel);
			}
		});

		// adding
		return tab2;

	}

	public static void addRow(String Product) {
		dtablemodel.addRow(new Object[] { Product, "0" });
	}

	public Dimension getGuiSize() {
		return GuiSize;
	}
}
