package tableModels;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import processing.FileAccess;

public class NewStockTableModel {
	private Path Stocks = Paths.get("textFiles/Stocks.txt");
	public Object data[][];
	private String columnNames[] = { "Name", "Current in Stock", "# to restock" };
	ArrayList<Object> data2 = new ArrayList<Object>();

	public NewStockTableModel() {
		// TODO Auto-generated constructor stub
		getdata();
	}

	public int getColumnCount() {
		return 2;

	}

	public int getRowCount() {

		return data2.size() / 2;

	}

	public Object[][] getdata() {
		FileAccess access = new FileAccess(Stocks);
		data2 = access.oReadFileData();
		data = new Object[getRowCount()][getColumnCount()];
		for (int i = 0; i < getRowCount(); i++) {
			for (int ii = 0; ii < 2; ii++) {
				if (ii == 1) {
					data[i][ii] = 0;

				} else {
					data[i][ii] = data2.get(i * 2);

				}

			}
		}
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
		if (col < 1) {
			return false;
		} else {
			return true;
		}
	}

}
