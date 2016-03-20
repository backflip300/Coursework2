package processing;

import java.awt.Color;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.ScrollTextArea;


/**
 * The Class Restock.
 */
public class Restock {

	/** The date format specifies which format to display time in. */
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/** The Order history allows access to the OrderHistory text file. */
	private FileAccess OrderHistory;

	/** The Stocks allows access to the Stocks text file. */
	private FileAccess Stocks;

	/** The calendar turns system time into a readable date. */
	Calendar calendar = Calendar.getInstance();

	/**
	 * Instantiates a new restock.
	 */
	public Restock() {
		OrderHistory = new FileAccess(Paths.get("TextFiles/OrderHistory.txt"));
		Stocks = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
	}

	/**
	 * Restock.
	 *
	 * @param stockTable
	 *            the stock table
	 * @param console
	 *            the console
	 */
	public void restock(JTable stockTable, ScrollTextArea console) {
		// stop cell editing
		stockTable.editCellAt(0, 2);
		stockTable.getCellEditor().stopCellEditing();

		// set null values to 0
		for (int x = 0; x < stockTable.getRowCount(); x++) {
			if ((stockTable.getValueAt(x, 2)).equals("")) {
				stockTable.setValueAt("0", x, 2);
			}
		}
		ArrayList<String> history = new ArrayList<String>();
		ArrayList<Integer> cStocks = new ArrayList<Integer>();
		ArrayList<Integer> rStocks = new ArrayList<Integer>();
		String confirm = "to restock \n";
		String tempcStocks, temprStocks;
		cStocks.clear();
		rStocks.clear();

		try {
			// Get current stock and amount to restock.
			for (int i = 0; i < stockTable.getRowCount(); i++) {

				tempcStocks = stockTable.getValueAt(i, 1).toString();
				temprStocks = stockTable.getValueAt(i, 2).toString();
				cStocks.add(Integer.parseInt(tempcStocks));
				rStocks.add(Integer.parseInt(temprStocks));

				if (rStocks.get(i) != 0) {
					// Add any restocks to history and confirm text.
					confirm += "\n" + stockTable.getValueAt(i, 0) + " x " + rStocks.get(i) + "\t total: "
							+ (cStocks.get(i) + rStocks.get(i));
					history.add(stockTable.getValueAt(i, 0) + " x " + rStocks.get(i) + "\t total: "
							+ (cStocks.get(i) + rStocks.get(i)));
				}
			}

			// Check something to restock.
			if (history.size() == 0) {
				JOptionPane.showMessageDialog(null, "Could not restock: nothing to restock");
			} else {
				// Get user to confirm restocks.
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, confirm, "Comfirmation", dialogButton);

				if (dialogResult == JOptionPane.YES_OPTION) {
					for (int ii = 0; ii < stockTable.getRowCount(); ii++) {
						// Add restock values to current values, change on table
						// and
						// in text file.
						stockTable.setValueAt((Object) (cStocks.get(ii) + rStocks.get(ii)), ii, 1);
						stockTable.setValueAt(0, ii, 2);
						Stocks.sEditline(String.valueOf(cStocks.get(ii) + rStocks.get(ii)), (2 * ii) + 1);
					}
					// Add order history to text file and to the console.
					OrderHistory.sWriteFileData("\n" + dateFormat.format(calendar.getTime()));
					console.appendToOutput("\n" + dateFormat.format(calendar.getTime()) + "\n", Color.black, false);
					for (int x = 0; x < history.size(); x++) {
						OrderHistory.sWriteFileData(history.get(x));
						console.appendToOutput(history.get(x) + "\n", Color.BLACK, false);
					}

				}
			}
			history.clear();
			cStocks.clear();
			rStocks.clear();
		} catch (NumberFormatException e) {
			console.appendToOutput("error: incorrect input (try using just positive numbers) \n", Color.red, true);
		}
	}
}
