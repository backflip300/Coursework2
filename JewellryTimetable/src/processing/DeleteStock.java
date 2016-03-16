package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import tableModels.StockTableModel;

public class DeleteStock {
	private Product[] products;
	private ExtractProduct pExtractor = new ExtractProduct();
	FileAccess stocksFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
	ArrayList<String> stocks = new ArrayList<String>();
	ArrayList<String> usingProducts = new ArrayList<String>();
	private String toDelete;
	private boolean used, exists;
	private S
	public DeleteStock() {

	}

	public void delete(StockTableModel table) {

		used = false;
		stocks = stocksFile.sReadFileData();
		toDelete = JOptionPane.showInputDialog("Enter name of stock to delete");
		products = pExtractor.ExtractAll();

		Search: for (Product product : products) {
			for (String stock : product.stocks) {
				if (stock.equalsIgnoreCase(toDelete)) {
					used = true;
					break Search;
				}
			}

		}

		if (used == false) {
			for (int i = 0;i < stocks.size();stocks++) {
				if (stocks.get(i).equalsIgnoreCase(toDelete)) {
					lineToDelete = i;
				}

			}
		}
	}

}
