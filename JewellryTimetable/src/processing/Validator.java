package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The Class Validater.
 */
public class Validator {

	/** The is valid specifies wheter an input is valid. */
	private boolean isValid;

	/**
	 * Instantiates a new validater.
	 */
	public Validator() {
		isValid = false;
	}

	/**
	 * V simple string validates if input is a simple string containing only
	 * letters and numbers.
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if valid
	 */
	public boolean vSimpleString(String toValidate) {
		return toValidate.matches("[a-zA-Z0-9]+");
	}

	/**
	 * V int range determines if a integer is between 2 values.
	 *
	 * @param toValidate
	 *            the number to validate
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return true, if successful
	 */
	public boolean vIntRange(int toValidate, int min, int max) {
		isValid = false;
		if (toValidate <= max && toValidate >= min) {
			isValid = true;
		}
		return isValid;

	}

	/**
	 * Vtime determines if a string is in the correct format for the timetable.
	 * 
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if successful
	 */
	public boolean vtime(String toValidate) {
		isValid = false;
		int hours = 100;
		int mins = 1000;
		if (toValidate.length() == 5) {
			if (toValidate.substring(0, 2).matches("[0-9]+")) {
				hours = Integer.parseInt((toValidate.substring(0, 2)));

			}
			if (toValidate.substring(3).matches("[0-9]+")) {

				mins = Integer.parseInt((toValidate.substring(3)));
			}
			if (mins < 60 && mins >= 0 && hours < 24 && hours >= 0) {
				isValid = true;

			}
		}
		return isValid;
	}

	/**
	 * V only contains numbers determines if a string only contains numbers.
	 *
	 * @param toValidate
	 *            the string to validate
	 * @return true, if successful
	 */
	public boolean vOnlyContainsNumbers(String toValidate) {
		isValid = false;
		if (toValidate.matches("[0-9]+")) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Stockexists checks if stock name already exists
	 *
	 * @param stock
	 *            the stock name to check
	 * @return true, if stock already exists
	 */
	public boolean stockexists(String stock) {
		isValid = false;
		// Get existing stocks, check against new stock if any match
		ArrayList<String> stocks = new ArrayList<String>();
		FileAccess StockFile = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
		stocks = StockFile.sReadFileData();
		for (int i = 0; i < stocks.size() / 2; i++) {
			if (stock.equals(stocks.get(2 * i))) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}

	/**
	 * Product exists checks if a product already exists under then given name.
	 *
	 * @param name
	 *            the name of the product to check
	 * @return true, if product exists
	 */
	public boolean productExists(String name) {
		isValid = false;
		// Get existing products, see if name matches any of the products.
		ArrayList<String> products = new ArrayList<String>();
		FileAccess productsFile = new FileAccess(Paths.get("TextFiles/Products.txt"));
		products = productsFile.sReadFileData();
		for (int i = 0; i < products.size(); i++) {
			if (name.equals(products.get(i).substring(0, products.get(i).indexOf('['))))
				isValid = true;
		}
		return isValid;
	}

}
