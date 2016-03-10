package processing;

import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DeleteProduct {
	private FileAccess productsFile = new FileAccess(
			Paths.get("TextFiles/Products.txt"));
	private ArrayList<String> products = new ArrayList<String>();
	String toDelete;
	int linetoDelete = -1;

	public DeleteProduct() {
		// TODO Auto-generated constructor stub
		
	}
	public boolean delete(DefaultTableModel productsTableModel){
		boolean deleted = false;
		toDelete = JOptionPane
				.showInputDialog("Enter Name of product to delete (Case Sensitive)");
		products = productsFile.sReadFileData();

		for (int i = 0; i < products.size(); i++) {
			if (toDelete.equals(products.get(i).substring(0,
					products.get(i).indexOf("[")))) {
				linetoDelete = i;
				break;
			}
		}
		if (linetoDelete != -1) {
			
			productsTableModel.removeRow(linetoDelete);
			productsFile.sRemoveLine(linetoDelete);
			deleted = true;
		}
		return deleted;
	}
}
