package tableModels;

import java.util.ArrayList;

/**
 * The Class timetableTableModel.
 *
 * @author Edward
 */
public class TimetableTableModel {
	
	/** The data. */
	public Object data[][];
	
	/** The column names. */
	private String columnNames[] = { "timetable", "start", ":", "finish" };
	
	/** The data2. */
	ArrayList<Object> data2 = new ArrayList<Object>();

	/**
	 * Instantiates a new timetable table model.
	 */
	public TimetableTableModel() {
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
		return 6;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object[][] getdata() {
		data = new Object[][] { { "Monday", "09:00", "17:00" }, { "Tuesday", "09:00", "17:00" },
				{ "Wednesday", "09:00", "17:00" }, { "Thursday", "09:00", "17:00" },
				{ "Friday", "09:00", "17:00" }, };
		return data;
	}

	/**
	 * Gets the column name.
	 *
	 * @param col the col
	 * @return the column name
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Gets the value at.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the value at
	 */
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/**
	 * Sets the value at.
	 *
	 * @param row the row
	 * @param col the col
	 * @param value the value
	 */
	public void setValueAt(int row, int col, Object value) {
		data[row][col] = value;
	}

	/**
	 * Checks if is cell editable.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if is cell editable
	 */
	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 2) {
			return true;
		} else {
			return false;
		}
	}

}