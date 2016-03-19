package tableModels;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import processing.FileAccess;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductTableModel.
 *
 * @author Edward
 */
public class ProductTableModel {

	/** The data to fill the table with. */
	public Object data[][];

	/** The column names. */
	private String columnNames[] = { "Product", "# to produce" };

	/** The products contains the contents of the products text file. */
	ArrayList<Object> products = new ArrayList<Object>();

	/**
	 * Instantiates a new product table model.
	 */
	public ProductTableModel() {
		getdata();
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	public int getRowCount() {

		return products.size();

	}

	/**
	 * Gets the data for the table.
	 *
	 * @return the data
	 */
	public Object[][] getdata() {
		FileAccess access = new FileAccess(Paths.get("TextFiles/Products.txt"));
		products = access.oReadFileData();
		data = new Object[getRowCount()][getColumnCount()];
		for (int i = 0; i <= getRowCount() - 1; i++) {
			String sTemp = products.get(i).toString().substring(0, products.get(i).toString().indexOf("["));
			data[i][0] = sTemp;
			data[i][1] = "0";
		}
		return data;
	}

	/**
	 * Gets the column name.
	 *
	 * @param col
	 *            the col
	 * @return the column name
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Gets the value at a certain cell.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @return the value at the cell
	 */
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/**
	 * Sets the value at a certain cell.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @param value
	 *            the value to set
	 */
	public void setValueAt(int row, int col, Object value) {
		data[row][col] = value;
	}

	/**
	 * Checks if cell editable.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @return true, if cell is editable
	 */
	public boolean isCellEditable(int row, int col) {
		if (col < 1) {
			return false;
		} else {
			return true;
		}
	}

}