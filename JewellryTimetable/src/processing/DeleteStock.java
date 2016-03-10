package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import tableModels.StockTableModel;

public class DeleteStock {
	private Product[] products;
	private ExtractProduct pExtractor = new ExtractProduct();
	FileAccess stocksFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
	ArrayList<String> stocks = new ArrayList<String>();

	public DeleteStock() {
		
		
	}

	public int delete(StockTableModel table) {
		int deletestate = 0;
		products = pExtractor.ExtractAll();
		for ( int i = 0; i < products.length; i++){
			for(int x = 0; x < products[i].stocks.length;x++){
				if ()
					
				
			}
		}
		return deletestate;
	}

}
