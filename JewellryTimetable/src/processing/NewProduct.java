package processing;

import gui.Tab2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import tableModels.NewStockTableModel;

/**
 * The Class NewProduct.
 */
public class NewProduct {

	/** The frame. */
	private JFrame frame;

	/**
	 * The stocks table displays current stocks and allows input of how many are
	 * required to produce the product.
	 */
	private JTable stocksTable;

	/** The new stock table model. */
	private NewStockTableModel newStockTableModel;

	/** The default table model. */
	private DefaultTableModel defaultTableModel;

	/** The scroll pane which contains the stocks table. */
	private JScrollPane scrollPane;

	/** The add product button when all inputs have been added. */
	private JButton addProduct;

	/** The tTime allows input of how long the product takes to create */
	private JTextField tTime;

	/** The tName allows input of the name of the product. */
	private JTextField tName;
	/** The l time defines . */
	private JLabel lTime;

	/** The lName labels the respective input text field. */
	private JLabel lName;
	/** The new stock labels the respective input text field. */
	private String newStock;

	/** The name of the product. */
	private String name;

	/**
	 * Instantiates a new new product.
	 *
	 * @param productsTableModel
	 *            the products table model
	 */
	public NewProduct(DefaultTableModel productsTableModel) {

		// productsTableModel = this.productsTableModel;
	}

	/**
	 * Attempts to create new stock.
	 *
	 * @return the string
	 */
	public String addStock() {

		// Create gui.
		// Make frame and panel.
		createFrame();
		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new MigLayout());

		// Create table & button.
		createTable();
		createButton();
		// Create text areas for name and time.
		createTextAreas();
		// Add everything to the jpanel.
		panel.add(scrollPane, "west");
		panel.add(addProduct, "south");
		panel.add(lName, "wrap");
		panel.add(tName, "wrap");
		panel.add(lTime, "wrap");
		panel.add(tTime, "wrap");
		frame.add(panel);

		frame.setVisible(true);
		// table data from Stocks
		// button when data entered(grey out)?

		return newStock;

	}

	/**
	 * Creates the text areas.
	 */
	private void createTextAreas() {
		tName = new JTextField(15);
		tTime = new JTextField(15);
		lName = new JLabel("Name");
		lTime = new JLabel("Time to create in minutes");

	}

	/**
	 * Creates the button.
	 */
	private void createButton() {

		addProduct = new JButton("Add Product");

		// Add action Listener to button.
		addProduct.addActionListener(new ActionListener() {
			// validates inputs and then creates new product if
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String errorMessage = "";
				Validator Validator = new Validator();
				ArrayList<String> StocksNeeded = new ArrayList<String>();
				boolean valid = true;
				int time = 0;
				// Validate name.
				if (Validator.vSimpleString(tName.getText()) == true) {
					name = tName.getText();
					// Validate name is not already being used.
					if (Validator.productExists(name)) {
						valid = false;

						errorMessage = "Could not create product: product already exists with that name.";
					}
				} else {
					valid = false;
					errorMessage = "Could not create product: invalid name (can only contain letters and numbers).";
				}

				// Validate time.
				if (Validator.vOnlyContainsNumbers(tTime.getText()) == true) {
					time = Integer.parseInt(tTime.getText());
					if (time <= 0) {
						valid = false;
						errorMessage = "Could not create product: invalid time (positive numbers only).";
					}
				} else {
					valid = false;
					errorMessage = "Could not create product: invalid time (positive numbers only).";
				}
				// Validate stocks needed.
				StocksNeeded.clear();
				for (int i = 0; i < newStockTableModel.getRowCount(); i++) {
					if (stocksTable.getValueAt(i, 1).toString().equals("0") == false
							&& Validator.vOnlyContainsNumbers(stocksTable.getValueAt(i, 1).toString()) == true) {
						StocksNeeded.add(stocksTable.getValueAt(i, 0).toString());
						StocksNeeded.add(stocksTable.getValueAt(i, 1).toString());
					} else if (Validator.vOnlyContainsNumbers(stocksTable.getValueAt(i, 1).toString()) == false) {
						valid = false;
						errorMessage = "Could not create product: invalid number to produce input(s).";

					}

				}
				if (StocksNeeded.size() == 0) {
					errorMessage = "Could not create product: no stock used.";
					valid = false;
				}

				// Turn inputs into correctly formatted string.
				System.out.println(valid);
				if (valid == true) {
					newStock = name + "[";

					for (int i = 0; i < StocksNeeded.size(); i++) {
						newStock += "/" + StocksNeeded.get(i);

					}
					newStock += "]" + time;
					// Add product to products table.
					Tab2.addRow(newStock.substring(0, newStock.indexOf("[")));
					// Add product to text file
					FileAccess Productsfile = new FileAccess(Paths.get("TextFiles/Products.txt"));
					Productsfile.sWriteFileData(newStock);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, errorMessage);
				}
			}
		});
	}

	/**
	 * Creates the table.
	 */
	@SuppressWarnings("serial")
	private void createTable() {
		newStockTableModel = new NewStockTableModel();
		defaultTableModel = new DefaultTableModel(newStockTableModel.getdata(),
				new Object[] { "stocks", "# required to make" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		stocksTable = new JTable(defaultTableModel);
		stocksTable.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(stocksTable);
		scrollPane.setPreferredSize(new Dimension(300, 400));

	}

	/**
	 * Creates the frame.
	 */
	private void createFrame() {
		frame = new JFrame("New Product");
		frame.getContentPane().setLayout(new MigLayout());
		frame.setTitle("Inventorize");
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}