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
import processing.DeleteProduct;
import processing.NewProduct;
import processing.Validator;
import renderers.TimetableGui;
import renderers.TimetableModelRenderer;
import tableModels.ProductTableModel;
import tableModels.TimetableTableModel;


/**
 * The Class Tab2.
 */
public class Tab2 {

	/**
	 * The Error message holds the appropriate error message for any current
	 * incorrect inputs
	 */
	private String ErrorMessage;

	/** The JPanel which contains everything required on the tab */
	private JPanel tab2;

	/** The TabbedPanel which contains the 3 tabs */
	TabbedPanel tabbedPanel;

	/** The create timetable overwrites old timetable with new image */
	private JButton createTimetable;

	/**
	 * The product table displays each product and allows you to enter how many
	 * to produce
	 */
	private JTable productTable;

	/**
	 * The timetable table displays and allows input of start and end of each
	 * work day(mon-fri).
	 */
	private JTable timetableTable;

	/** The dtablemodel2. */
	private static DefaultTableModel dtablemodel, dtablemodel2;

	/** The gui size. */
	Dimension GuiSize = new Dimension(600, 200);

	/** The delete product function deletes specified product. */
	private DeleteProduct deleteProduct = new DeleteProduct();

	/**
	 * The timetable gui presents a preview of what the timetable will look
	 * like.
	 */
	private TimetableGui timetableGui;

	/** The graphics used to render timetable */
	private Graphics graphics;

	/** The validator checks inputs are in the correct format */
	Validator validator = new Validator();

	/**
	 * Instantiates a new tab2.
	 */
	public Tab2() {

	}

	/**
	 * The Create function creates all buttons and tables required for the tab
	 *
	 * @param tabbedPanel
	 *            the tabbed panel which hold this tab
	 * 
	 * @return tab2
	 */
	@SuppressWarnings("serial")
	public JPanel create(TabbedPanel tabbedPanel) {
		ProductTableModel productTableModel;
		TimetableTableModel TimetableTableModel;
		TimetableModelRenderer cellRenderer;
		this.tabbedPanel = tabbedPanel;
		cellRenderer = new TimetableModelRenderer();
		tab2 = new JPanel();
		tab2.setLayout(new MigLayout());
		// Create products table.
		productTableModel = new ProductTableModel();
		dtablemodel = new DefaultTableModel(productTableModel.data, new Object[] { "product", "# to produce" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		productTable = new JTable(dtablemodel);
		JScrollPane scrollPane = new JScrollPane(productTable);
		scrollPane.setPreferredSize(new Dimension(200, 450));
		scrollPane.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
		productTable.getTableHeader().setReorderingAllowed(false);

		// Create timetable table.
		TimetableTableModel = new TimetableTableModel();
		dtablemodel2 = new DefaultTableModel(TimetableTableModel.data,
				new Object[] { "timetable", "start", "finish" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= 1;
			}

		};
		timetableTable = new JTable(dtablemodel2);
		JScrollPane ttscrollPane = new JScrollPane(timetableTable);
		timetableTable.getTableHeader().setReorderingAllowed(false);
		timetableTable.setDefaultRenderer(Object.class, cellRenderer);

		// Create timetable preview renderer.
		timetableGui = new TimetableGui(dtablemodel2, this);
		timetableGui.setPreferredSize(GuiSize);
		timetableGui.setBackground(Color.white);
		timetableGui.paint(graphics);

		// Create buttons
		JButton OpenTimetable = new JButton("Open Timetable");
		JButton NewProduct = new JButton("New Product");
		createTimetable = new JButton("Create new Timetable");
		JButton DeleteProduct = new JButton("Delete Product");

		// Add created objects to tab2 in correct locations.
		ttscrollPane.setPreferredSize(new Dimension(600, 103));
		tab2.add(scrollPane, "cell 0 0 1 5");
		tab2.add(ttscrollPane, "cell 1 0 ");
		tab2.add(timetableGui, "cell 1 1");
		tab2.add(NewProduct, "cell 1 2, split 2 ,Grow");
		tab2.add(DeleteProduct, "cell 1 2, Grow");
		tab2.add(createTimetable, "cell 1 3,grow");
		tab2.add(OpenTimetable, "cell 1 4, grow");

		// Add action listeners to buttons.
		NewProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				NewProduct newProduct = new NewProduct(dtablemodel);
				newProduct.addStock();

			}
		});

		OpenTimetable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				File f = new File("Images/Timetable.png");
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		createTimetable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"are you sure you want to overide old timetable? (CANNOT BE UNDONE)", "create new timetable",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION)
					updateTimetable(true);

			}
		});

		DeleteProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean deleted = false;
				deleted = deleteProduct.delete(dtablemodel);
				if (deleted == false) {
					JOptionPane.showMessageDialog(null, "Product not found");
				}
			}
		});
		// Add listeners to tables to check if anything changes, allowing
		// real-time checking of invalid entries.
		timetableTable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				updateTimetable(false);
			}
		});

		productTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				updateTimetable(false);
			}
		});
		tab2.setEnabled(false);
		return tab2;
	}

	/**
	 * Update timetable checks if any inputs where invalid, and if not updates
	 * the preview.
	 * 
	 * @param image
	 *            specifies whether or not to overwrite the current image of the
	 *            timetable.
	 * 
	 */
	private void updateTimetable(boolean image) {

		if (validInputs() == true) {
			timetableGui.update(dtablemodel2, dtablemodel, image, tabbedPanel, createTimetable);
		} else {
			createTimetable.setText(ErrorMessage);
			createTimetable.setEnabled(false);
		}
	}

	/**
	 * Valid inputs checks whether inputs to the products table and timetable
	 * table are in the correct format and valid
	 *
	 * @return true, if all inputs valid, else false.
	 */
	private boolean validInputs() {
		boolean valid = true;
		for (int i = 0; i < dtablemodel2.getRowCount(); i++) {
			if (!validator.vtime((String) timetableTable.getValueAt(i, 1))
					|| !validator.vtime((String) timetableTable.getValueAt(i, 2))) {
				ErrorMessage = "Invalid times entered (use format \"00:00\")";

				valid = false;
			}
		}
		for (int i = 0; i < dtablemodel.getRowCount(); i++) {
			if (!validator.vOnlyContainsNumbers((String) dtablemodel.getValueAt(i, 1))
					|| !validator.vIntRange(Integer.parseInt((String) dtablemodel.getValueAt(i, 1)), 0, 10000000)) {
				valid = false;
				ErrorMessage = "Invalid product number entered ";
			}
		}

		return valid;
	}

	/**
	 * Adds a row to the products table
	 *
	 * @param Product
	 *            Name of product to add.
	 */
	public static void addRow(String Product) {
		dtablemodel.addRow(new Object[] { Product, "0" });
	}

	/**
	 * Gets the gui size.
	 *
	 * @return the gui size
	 */
	public Dimension getGuiSize() {
		return GuiSize;
	}
}
