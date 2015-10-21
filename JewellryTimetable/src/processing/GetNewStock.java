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

public class GetNewStock {

	private JFrame frame;
	private JPanel panel;
	private JTable stocksTable;
	private NewStockTableModel nsTableModel;
	private DefaultTableModel dTableModel, productsTableModel;
	private JScrollPane scrollPane;
	private JButton addProduct;
	private JTextField tName, tTime;
	private JLabel lName, lTime;
	private String newStock;
	private String Name;
	private int time;
	private FileAccess Productsfile = new FileAccess(Paths.get("TextFiles/Products.txt"));

	private boolean valid;
	private Validater Validater;
	private ArrayList<String> StocksNeeded = new ArrayList<String>();

	public GetNewStock(DefaultTableModel productsTableModel) {
		Validater = new Validater();
		//productsTableModel = this.productsTableModel;
		this.productsTableModel = productsTableModel;
		// TODO Auto-generated constructor stub
	}

	public String addStock() {

		// create gui
		// make frame and panel
		createFrame();
		panel = new JPanel();
		panel.setLayout(new MigLayout());

		// table & button
		createTable();
		createButton();
		// text area for name and time
		createTextAreas();
		// put all together
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

	private void createTextAreas() {
		tName = new JTextField(15);
		tTime = new JTextField(15);
		lName = new JLabel("Name");
		lTime = new JLabel("Time to create in minutes");

	}

	private void createButton() {
		addProduct = new JButton("Add Product");

		addProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				valid = true;

				// name validated
				if (Validater.vSimpleString(tName.getText()) == true) {
					Name = tName.getText();
				} else {
					valid = false;
				}
				// time validated
				if (Validater.vOnlyContainsNumbers(tTime.getText()) == true) {
					time = Integer.parseInt(tTime.getText());
					if (time <= 0) {
						valid = false;
					}
				} else {
					valid = false;
				}
				// stocks needed validated
				StocksNeeded.clear();
				for (int i = 0; i < nsTableModel.getRowCount(); i++) {

					if (stocksTable.getValueAt(i, 1).toString().equals("0") == false
							&& Validater.vOnlyContainsNumbers(stocksTable
									.getValueAt(i, 1).toString()) == true) {
						StocksNeeded.add(stocksTable.getValueAt(i, 0)
								.toString());
						StocksNeeded.add(stocksTable.getValueAt(i, 1)
								.toString());
						System.out.println(StocksNeeded.get(0));
					}

				}
				if (StocksNeeded.size() == 0) {
					valid = false;
				}
				
				// sort into format
				System.out.println(valid);
				if (valid == true) {
					newStock = Name + "[";

					for (int i = 0; i < StocksNeeded.size(); i++) {
						newStock += "/" + StocksNeeded.get(i);

					}
					newStock += "]" + time;
					//System.out.println(newStock);
					// add prodcut to products table
					Tab2.addRow(newStock.substring(0, newStock.indexOf("[")));
					
					Productsfile.sWriteFileData(newStock);
				} else {
					JOptionPane.showMessageDialog(null, "sumtin wrong");
				}

				// validate inputs of table, name, time to prepare
				
				// add product to products.txt

			}
		});
	}

	@SuppressWarnings("serial")
	private void createTable() {
		nsTableModel = new NewStockTableModel();
		dTableModel = new DefaultTableModel(nsTableModel.getdata(),
				new Object[] { "stocks", "# required to make" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		stocksTable = new JTable(dTableModel);
		stocksTable.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(stocksTable);
		scrollPane.setPreferredSize(new Dimension(300, 400));

	}

	private void createFrame() {
		frame = new JFrame("New Product");
		frame.getContentPane().setLayout(new MigLayout());
		frame.setTitle("Inventorize");
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}
