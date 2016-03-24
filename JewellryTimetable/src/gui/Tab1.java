package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import processing.DeleteStock;
import processing.FileAccess;
import processing.NewStock;
import processing.Restock;
import tableModels.StockTableModel;

/**
 * The Class Tab1.
 */
public class Tab1 {

	/** The JPanel which contains everything required on the tab. */
	private JPanel tab1;

	/**
	 * The JTable which contains the name and quantity of each stock, also
	 * ability to enter restock amount.
	 */
	private JTable stockTable;

	/** The TableModel for the stock table. */
	private StockTableModel tModel;

	/** The console which displays restock history and any errors. */
	private ScrollTextArea console;

	/** The panel which contains the console. */
	private JPanel cPanel;

	/** The new stock function creates a new stock. */
	private NewStock newStock = new NewStock();

	/**
	 * The restock function inputs restock outputs new stock amounts and writes
	 * to order history.
	 */
	private Restock restock = new Restock();

	/** The delete stock deletes desired stock. */
	private DeleteStock deleteStock = new DeleteStock();

	/** The stock table model. */
	private DefaultTableModel stockTableModel;

	/** The Stocks allows access to Stocks text file for reading/writing */
	private FileAccess Stocks;

	/**
	 * The Order history allows access to OrderHistory text file for
	 * reading/writing.
	 */
	private FileAccess OrderHistory;

	/** The date format specifies what format the time should be displayed in */
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/** The calendar converts time to a date. */
	Calendar cal = Calendar.getInstance();

	/**
	 * Instantiates a new tab1.
	 */
	public Tab1() {

		Stocks = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
		OrderHistory = new FileAccess(Paths.get("TextFiles/OrderHistory.txt"));

	}

	/**
	 * The Create function creates all buttons and tables required for the first
	 * tab.
	 *
	 * @return Tab1
	 */
	@SuppressWarnings("serial")
	public JPanel create() {
		tab1 = new JPanel();
		tab1.setLayout(new MigLayout());

		// Creating buttons for gui display.
		JButton restockButton = new JButton("Restock");
		JButton newStockButton = new JButton("New Stock");
		JButton deleteStockButton = new JButton("Delete Stock");

		// Creating scroll panel for console.
		cPanel = new JPanel();
		cPanel.setPreferredSize(new Dimension(300, 400));
		console = new ScrollTextArea(300, 400);
		console.setPreferredSize(new Dimension(320, 475));
		cPanel.add(console);

		getOrderHistory();

		// Make the Table for stocks.
		tModel = new StockTableModel();

		stockTableModel = new DefaultTableModel(tModel.data,
				new Object[] { "Name", "Current in Stock in /g or /cm", "# to restock" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		stockTable = new JTable(stockTableModel);
		stockTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(stockTable);
		// add created objects to tab1 in correct locations
		tab1.add(scrollPane, "cell 0 0, split 2");
		tab1.add(cPanel, "cell 0 0");
		tab1.add(restockButton, "cell 0 1, split 3,grow");
		tab1.add(newStockButton, "cell 0 1 , grow");
		tab1.add(deleteStockButton, "cell 0 1,grow");

		// add action listeners to buttons.
		newStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newStock.newStock(stockTableModel);

			}
		});
		deleteStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteStock.delete(stockTableModel);
			}
		});
		restockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restock.restock(stockTable, console);
			}
		});
		return tab1;
	}

	/**
	 * Updates current stock amounts from the text file
	 */
	public void update() {
		ArrayList<String> stockFile = new ArrayList<String>();
		stockFile = Stocks.sReadFileData();
		for (int i = 0; i < stockFile.size() / 2; i++) {
			stockTable.setValueAt(stockFile.get(2 * i + 1), i, 1);
		}
	}

	/**
	 * Gets the order history from text file and outputs to console.
	 */
	private void getOrderHistory() {
		ArrayList<String> tOrderHistory = new ArrayList<String>();
		tOrderHistory = OrderHistory.sReadFileData();
		for (int i = 0; i < tOrderHistory.size(); i++) {
			console.appendToOutput(tOrderHistory.get(i) + "\n", Color.BLACK, false);
		}
	}
}
