package tableModels;

import java.nio.file.Paths;
import java.util.ArrayList;

import processing.FileAccess;

/**
 * The Class StockTableModel.
 *
 * @author Edward
 */
public class StockTableModel {

	/** The data for the table. */
	public Object data[][];

	/** The column names. */
	private String columnNames[] = { "Name", "Current in Stock /cm or /g", "# to restock" };

	/** The stocks contains the contents of the stocks text file. */
	ArrayList<Object> stocks = new ArrayList<Object>();

	/**
	 * Instantiates a new stock table model.
	 */
	public StockTableModel() {
		getdata();
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	public int getColumnCount() {
		return 3;
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
	 * Gets the data for the table.
	 *
	 * @return the data
	 */
	public Object[][] getdata() {
		FileAccess access = new FileAccess(Paths.get("textFiles/Stocks.txt"));
		stocks = access.oReadFileData();
		data = new Object[getRowCount()][getColumnCount()];
		int a = 0;
		for (int i = 0; i < getRowCount(); i++) {
			for (int ii = 0; ii < 3; ii++) {
				if (2 == ii) {
					data[i][ii] = 0;
				} else {
					data[i][ii] = stocks.get(a);
					a++;
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
	 * @return the value of the cell
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
	 * Checks if cell is editable.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @return true, if cell is editable
	 */
	public boolean isCellEditable(int row, int col) {
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

}