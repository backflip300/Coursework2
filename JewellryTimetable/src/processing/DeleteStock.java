package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * The Class DeleteStock.
 */
public class DeleteStock {

	/** The products contains all products that exist */
	private Product[] products;

	/** The p extractor extracts products from text file. */
	private ExtractProduct pExtractor = new ExtractProduct();

	/**
	 * The stocks file allows access to stocks text file for reading/writing.
	 */
	FileAccess stocksFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));

	/** The stocks holds the contents of each line of the stocks text file. */
	ArrayList<String> stocks = new ArrayList<String>();

	/**
	 * The using products holds a list of all products current using the stock
	 * attempting to be deleted.
	 */
	ArrayList<String> usingProducts = new ArrayList<String>();

	/** The to delete is the name of the stock to delete. */
	private String toDelete;

	/**
	 * The used defines whether the stock is used in creation of any product or
	 * not.
	 */
	private boolean used;

	/**
	 * Instantiates a new delete stock.
	 */
	public DeleteStock() {

	}

	/**
	 * Delete attempts to delete product
	 *
	 * @param stocksTable
	 *            the stocks table.
	 */
	public void delete(DefaultTableModel stocksTable) {

		stocks = stocksFile.sReadFileData();
		// input name of stock to delete
		toDelete = JOptionPane.showInputDialog("Enter name of stock to delete");
		products = pExtractor.ExtractAll();
		// checks if stock is used in creation of any products.
		for (Product product : products) {
			for (String stock : product.stocks) {
				if (stock.equalsIgnoreCase(toDelete)) {
					JOptionPane.showMessageDialog(null, "Cannot delete stock: still in use in");
					return;
				}
			}
		}
		// if not used attempt to find stock
		if (!used) {
			for (int i = 0; i < stocks.size(); i++) {

				if (stocks.get(i).equals(toDelete)) {
					// delete stock.
					stocksTable.removeRow(i / 2);
					stocksFile.sRemoveLine(i);
					stocksFile.sRemoveLine(i);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "Cannot delete stock: does not exist");
		}
	}

}
