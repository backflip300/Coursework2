package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import processing.NewProduct;
import processing.Validater;
import renderers.TTGui;
import renderers.TTMRenderer;
import tableModels.Tab2TableModel;
import tableModels.timetableTableModel;

public class Tab2 {
	private String ErrorMessage;
	private JPanel tab2;
	TabbedPanel t;
	private JTable table, ttable;
	private Tab2TableModel tModel;
	private timetableTableModel ttModel;
	private static DefaultTableModel dtablemodel, dtablemodel2;
	Dimension GuiSize = new Dimension(600, 200);
	private NewProduct newProduct;
	private TTMRenderer cellRenderer;
	private TTGui ttGui, ttGui2;
	private Graphics g;
	Validater validater = new Validater();

	public Tab2() {

	}

	@SuppressWarnings("serial")
	public JPanel create(TabbedPanel t) {
		this.t = t;
		cellRenderer = new TTMRenderer();
		// cellRenderer.setBackground(Color.BLUE);

		// layout
		// tab2.setEnabled(false);
		tab2 = new JPanel();
		tab2.setLayout(new MigLayout());
		tab2.setEnabled(false);
		//

		// create products table
		tModel = new Tab2TableModel();
		dtablemodel = new DefaultTableModel(tModel.data, new Object[] { "product", "# to produce" }) {
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
		dtablemodel2 = new DefaultTableModel(ttModel.data, new Object[] { "timetable", "start", "finish" }) {
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
		ttGui = new TTGui(dtablemodel2, this);
		ttGui.setPreferredSize(GuiSize);
		ttGui.setBackground(Color.white);
		ttGui.paint(g);
		ttGui2 = new TTGui(dtablemodel2, this);
		ttGui2.setPreferredSize(GuiSize);
		ttGui2.setBackground(Color.white);
		ttGui2.paint(g);

		// create buttons
		JButton OpenTimetable= new JButton("Open Timetable");
		JButton NewProduct = new JButton("New Product");
		JButton createTimetable = new JButton("Create new Timetable");

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
		tab2.add(scrollPane, "cell 0 0 1 5");
		tab2.add(ttscrollPane, "cell 1 0 ");
		tab2.add(ttGui, "cell 1 1");		
		tab2.add(NewProduct, "cell 1 2, grow");
		tab2.add(createTimetable, "cell 1 3,grow");
		tab2.add(OpenTimetable, "cell 1 4, grow");

		
		OpenTimetable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				File f = new File("Images/Timetable.png");
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		createTimetable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int confirm = JOptionPane.showConfirmDialog(null,
						"are you sure you want to overide old timetable? (CANNOT BE UNDONE)", "create new timetable",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION)
					updateTimeTable(true);

			}
		});
		// timetablemodel listener
		ttable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				updateTimeTable(false);
			}
		});
		// products table listener
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				updateTimeTable(false);
			}
		});
		tab2.setEnabled(false);
		return tab2;
	}

	private void updateTimeTable(boolean image) {
		
		if (validInputs() == true) {
			ttGui.update(dtablemodel2, dtablemodel, image, t);
		} else {
			JOptionPane.showMessageDialog(null, ErrorMessage);
		}
	}

	private boolean validInputs() {
		boolean valid = true;
		for (int i = 0; i < dtablemodel2.getRowCount(); i++) {
			System.out.println(ttable.getValueAt(i, 1));
			if (!validater.vtime((String) ttable.getValueAt(i, 1))
					|| !validater.vtime((String) ttable.getValueAt(i, 2))) {
				ErrorMessage = "Invalid times entered (use format \"00:00\")";

				valid = false;
			}
		}
		for (int i = 0; i < dtablemodel.getRowCount(); i++) {
			if (!validater.vOnlyContainsNumbers((String) dtablemodel.getValueAt(i, 1))
					|| !validater.vIntRange(Integer.parseInt((String) dtablemodel.getValueAt(i, 1)), 0, 10000000)) {
				valid = false;
				ErrorMessage = "Invalid product number entered ";
			}
		}

		return valid;
	}

	public static void addRow(String Product) {
		dtablemodel.addRow(new Object[] { Product, "0" });
	}

	public Dimension getGuiSize() {
		return GuiSize;
	}
}
