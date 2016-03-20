package tableModels;

import java.nio.file.Paths;
import java.util.ArrayList;

import processing.FileAccess;

/**
 * The Class NewStockTableModel.
 */
public class NewStockTableModel {

	/** The data to go in the table. */
	public Object data[][];

	/** The column names. */
	private String columnNames[] = { "Name", "Current in Stock", "# to restock" };

	/** The stocks contains the contents of the stocks text file. */
	ArrayList<Object> stocks = new ArrayList<Object>();

	/**
	 * Instantiates a new new stock table model.
	 */
	public NewStockTableModel() {
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

		return stocks.size() / 2;

	}

	/**
	 * Gets the data for the table from the text file.
	 *
	 * @return the data
	 */
	public Object[][] getdata() {
		FileAccess access = new FileAccess(Paths.get("textFiles/Stocks.txt"));
		// get text from text file
		stocks = access.oReadFileData();
		data = new Object[getRowCount()][getColumnCount()];
		for (int i = 0; i < getRowCount(); i++) {
			for (int ii = 0; ii < 2; ii++) {
				if (ii == 1) {
					data[i][ii] = 0;

				} else {
					data[i][ii] = stocks.get(i * 2);

				}

			}
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
	 * @return the value at the specified cell
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
	 * Checks if a cell editable.
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
