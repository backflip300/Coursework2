package processing;

import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
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
		// TODO Auto-generated constructor stub
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
		int entered = 0;
		while (entered == 0) {
			// Input name.
			Stock = JOptionPane.showInputDialog("name of Stock");
			System.out.println(Stock);
			// Validate name.
			if (validator.vSimpleString(Stock) == true) {
				// Input current amount in stock.
				number = JOptionPane.showInputDialog("current # in stock");
				// If valid input add stock to table and text file.
				if (validator.vOnlyContainsNumbers(number) == true && Integer.parseInt(number) >= 0) {

					FileAccess access = new FileAccess(Paths.get("TextFiles/Stocks.txt"));

					stockTableModel.addRow(new Object[] { Stock, number, 0 });
					access.sWriteFileData(Stock);
					access.sWriteFileData(number);
					entered = 1;
				} else if (number == "") {
					entered = -1;
					break;
				}

			} else if (Stock.equals("")) {
				entered = -1;
				break;
			}

		}
	}

}
