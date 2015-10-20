package tableModels;

import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.table.DefaultTableCellRenderer;

public class timetableTableModel {
	public Object data[][];
	
	private String columnNames[] = { "timetable", "start", ":", "finish" };
	ArrayList<Object> data2 = new ArrayList<Object>();

	public timetableTableModel() {
		getdata();
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return 6;
	}

	public Object[][] getdata() {
		data = new Object[][] { { "Monday", "09:00", "17:00" }, { "Tuesday", "09:00", "17:00" },
				{ "Wednesday", "09:00", "17:00" }, { "Thursday", "09:00", "17:00" },
				{ "Friday", "09:00", "17:00" }, };
		return data;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public void setValueAt(int row, int col, Object value) {
		data[row][col] = value;
	}

	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 2) {
			return true;
		} else {
			return false;
		}
	}

}