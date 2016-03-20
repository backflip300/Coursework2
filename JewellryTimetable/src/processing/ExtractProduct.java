package processing;

import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * The Class ExtractProduct.
 */
public class ExtractProduct {

	/**
	 * The productsFile allows access to the products text file for
	 * reading/writing.
	 */
	private FileAccess productsFile = new FileAccess(Paths.get("TextFiles/Products.txt"));

	/** The current line which the product is being extracted from. */
	private String currentLine;

	/** The num of different stocks each product can produce. */
	private int numOfStocks;

	/**
	 * The close bracket holds the location in the current line of the closed
	 * bracket.
	 */
	private int closeBracket;
	/**
	 * The slashes holds the location in the current line of all the forwawrd
	 * slashes.
	 */
	ArrayList<Integer> slashes = new ArrayList<Integer>();

	/**
	 * Extractproducts get the products into the correct format from the text
	 * file.
	 *
	 * @param desiredProducts
	 *            the desired products
	 * @return the product[] contains all desired products
	 */
	public Product[] Extractproducts(String[] desiredProducts) {
		ArrayList<String> fileData = new ArrayList<String>();
		Product[] products = new Product[desiredProducts.length];
		String Name;
		String[] stocks;
		int[] quantity;
		int time;
		fileData = productsFile.sReadFileData();
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
	 * Extract all get's all products saved in the products text file.
	 *
	 * @return the product[]
	 */
	public Product[] ExtractAll() {
		ArrayList<String> fileData = new ArrayList<String>();

		String Name;
		String[] stocks;
		int[] quantity;
		int time;

		fileData = productsFile.sReadFileData();
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
	 * Gets the points in products string required to determine it
	 * name,stocks,quantity, and time.
	 *
	 * @return the points
	 */
	private void getpoints() {
		slashes.clear();
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
	 * Extract name of product.
	 *
	 * @return the name
	 */
	public String extractName() {
		String Name = currentLine.substring(0, currentLine.indexOf("["));

		return Name;
	}

	/**
	 * Extract stocks used by product.
	 *
	 * @return the string[] containing stocks needed to create the product
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
	 * Extract quantity of each stock used by product .
	 *
	 * @return the int[] containing the quantity of each stock needed to create
	 *         the product
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
	 * Extract time to create product.
	 *
	 * @return the int containing the time needed to create the product.
	 */
	public int extractTime() {
		int time;
		time = Integer.parseInt(currentLine.substring(closeBracket + 1));
		return time;
	}
}