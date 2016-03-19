package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtractProduct.
 */
public class ExtractProduct {

	/** The file. */
	private FileAccess file = new FileAccess(Paths.get("TextFiles/Products.txt"));
	
	/** The current line. */
	private String currentLine;
	
	/** The num of stocks. */
	private int openBracket, closeBracket, numOfStocks;
	
	/** The slashes. */
	ArrayList<Integer> slashes = new ArrayList<Integer>();

	/**
	 * Extractproducts.
	 *
	 * @param desiredProducts the desired products
	 * @return the product[]
	 */
	public Product[] Extractproducts(String[] desiredProducts) {
		ArrayList<String> fileData = new ArrayList<String>();
		Product[] products = new Product[desiredProducts.length];
		String Name;
		String[] stocks;
		int[] quantity;
		int time;
		fileData = file.sReadFileData();
		for (int i = 0; i < fileData.size(); i++) {
			currentLine = fileData.get(i);
			for (int x = 0; x < desiredProducts.length; x++) {
				if (extractName().equals(desiredProducts[x])) {
					getpoints();
					Name = extractName();
					stocks = extractStocks();
					quantity = extractQuantity();
					time = extractTime();
					products[x] = new Product(Name, stocks, quantity, time);
					break;
				}
			}
		}
		return products;
	}

	/**
	 * Extract all.
	 *
	 * @return the product[]
	 */
	public Product[] ExtractAll() {
		ArrayList<String> fileData = new ArrayList<String>();

		String Name;
		String[] stocks;
		int[] quantity;
		int time;

		fileData = file.sReadFileData();
		Product[] products = new Product[fileData.size()];
		for (int i = 0; i < fileData.size(); i++) {
			currentLine = fileData.get(i);
			getpoints();
			Name = extractName();
			stocks = extractStocks();
			quantity = extractQuantity();
			time = extractTime();
			products[i] = new Product(Name, stocks, quantity, time);
		}
		return products;

	}

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	private void getpoints() {
		slashes.clear();
		openBracket = currentLine.indexOf("[");
		closeBracket = currentLine.indexOf("]");
		numOfStocks = 0;
		for (int i = 0; i < currentLine.length(); i++) {
			if (currentLine.charAt(i) == '/') {
				numOfStocks++;
				slashes.add(i);
			}
		}
		numOfStocks = numOfStocks / 2;
	}

	/**
	 * Extract name.
	 *
	 * @return the string
	 */
	public String extractName() {
		String Name = currentLine.substring(0,currentLine.indexOf("[") );

		return Name;
	}

	/**
	 * Extract stocks.
	 *
	 * @return the string[]
	 */
	public String[] extractStocks() {
		String[] Stocks;
		Stocks = new String[numOfStocks];
		for (int i = 0; i < numOfStocks; i++) {
			Stocks[i] = currentLine.substring(slashes.get(2 * i) + 1, slashes.get(2 * i + 1));
		}

		return Stocks;

	}

	/**
	 * Extract quantity.
	 *
	 * @return the int[]
	 */
	public int[] extractQuantity() {
		int[] Quantity = new int[numOfStocks];
		for (int i = 0; i < numOfStocks - 1; i++) {
			Quantity[i] = Integer.parseInt(currentLine.substring(slashes.get((2 * i) + 1) + 1, slashes.get(2 * i + 2)));
		}
		Quantity[Quantity.length - 1] = Integer
				.parseInt(currentLine.substring(slashes.get(slashes.size() - 1) + 1, closeBracket));
		return Quantity;
	}

	/**
	 * Extract time.
	 *
	 * @return the int
	 */
	public int extractTime() {
		int time;
		time = Integer.parseInt(currentLine.substring(closeBracket + 1));
		return time;
	}
}