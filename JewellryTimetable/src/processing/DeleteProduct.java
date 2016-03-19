package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * The Class DeleteProduct function is to delete a specified product if it
 * exists.
 */
public class DeleteProduct {

	/** The products file allows access to the products text file. */
	private FileAccess productsFile = new FileAccess(Paths.get("TextFiles/Products.txt"));

	/** The products Arraylist to store the text in the products file. */
	private ArrayList<String> products = new ArrayList<String>();

	/** The to delete is the name of the product to delete. */
	String toDelete;

	/**
	 * The line to delete is the line on which the specified product appears on
	 * the text document.
	 */
	int lineToDelete = -1;

	/**
	 * Instantiates a new delete product.
	 */
	public DeleteProduct() {

	}

	/**
	 * Deletes products.
	 *
	 * @param productsTableModel
	 *            the products table model.
	 * @return true, if successfully deleted, else false.
	 */
	public boolean delete(DefaultTableModel productsTableModel) {
		boolean deleted = false;
		// Input name of product to delete.
		toDelete = JOptionPane.showInputDialog("Enter Name of product to delete (Case Sensitive)");
		products = productsFile.sReadFileData();
		// find product location.
		for (int i = 0; i < products.size(); i++) {
			if (toDelete.equals(products.get(i).substring(0, products.get(i).indexOf("[")))) {
				lineToDelete = i;
				break;
			}
		}
		// If product exists, then delete it from text file.
		if (lineToDelete != -1) {

			productsTableModel.removeRow(lineToDelete);
			productsFile.sRemoveLine(lineToDelete);
			deleted = true;
		}
		return deleted;
	}
}
