package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tableModels.StockTableModel;

public class DeleteStock {
	private Product[] products;
	private ExtractProduct pExtractor = new ExtractProduct();
	FileAccess stocksFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
	ArrayList<String> stocks = new ArrayList<String>();
	ArrayList<String> usingProducts = new ArrayList<String>();
	private String toDelete;
	private boolean used;

	public DeleteStock() {

	}

	public void delete(DefaultTableModel table) {
		stocks = stocksFile.sReadFileData();
		toDelete = JOptionPane.showInputDialog("Enter name of stock to delete");
		products = pExtractor.ExtractAll();

		for (Product product : products) {
			for (String stock : product.stocks) {
				if (stock.equalsIgnoreCase(toDelete)) {
					JOptionPane.showMessageDialog(null, "Cannot delete stock: still in use in");
					return;
				}
			}
		}

		if (!used) {
			for (int i = 0; i < stocks.size(); i++) {
				if (stocks.get(i).equalsIgnoreCase(toDelete)) {
					table.removeRow(i/2);
					stocksFile.sRemoveLine(i);
					stocksFile.sRemoveLine(i);
					break;
				}
			}
		} else {
			
			JOptionPane.showMessageDialog(null, "Cannot delete stock: does not exist");
		}
	}

}
