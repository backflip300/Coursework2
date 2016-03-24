package processing;

import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * The Class NewStock.
 */
public class NewStock {

	/** The validater. */
	private Validator validator = new Validator();

	/**
	 * Instantiates a new new stock.
	 */
	public NewStock() {
	}

	/**
	 * New stock attempts to create a new stock.
	 *
	 * @param stockTableModel
	 *            the stock table model
	 */
	public void newStock(DefaultTableModel stockTableModel) {
		String Stock;
		String number;
		// Input name.
		Stock = JOptionPane.showInputDialog("name of Stock");
		System.out.println(Stock);
		// Validate name.
		if (validator.vSimpleString(Stock)) {
			// Input current amount in stock.

			if (validator.stockexists(Stock) == false) {

				number = JOptionPane.showInputDialog("current # in stock");
				// If valid input add stock to table and text file.
				if (validator.vOnlyContainsNumbers(number) && Integer.parseInt(number) >= 0) {

					FileAccess access = new FileAccess(Paths.get("TextFiles/Stocks.txt"));

					stockTableModel.addRow(new Object[] { Stock, number, 0 });
					access.sWriteFileData(Stock);
					access.sWriteFileData(number);
				} else {
					JOptionPane.showMessageDialog(null, "Couldn't create stock: Invalid quantity.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Couldn't create stock: Stock already exists.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Couldn't create stock: Invalid name ( must only contain letters and numbers) .");
		}

	}
}
