package tableModels;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import processing.FileAccess;

public class Tab2TableModel {
	private Path Products = Paths.get("TextFiles/Products.txt");
	public Object data[][];
	private String columnNames[] = { "Product", "# to produce" };
	ArrayList<Object> data2 = new ArrayList<Object>();

	public Tab2TableModel() {
		getdata();
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {

		return data2.size();

	}

	public Object[][] getdata() {
		FileAccess access = new FileAccess(Products);
		data2 = access.oReadFileData();
		data = new Object[getRowCount()][getColumnCount()];

		for (int i = 0; i <= getRowCount() - 1; i++) {

			String sTemp = data2.get(i).toString()
					.substring(0, data2.get(i).toString().indexOf("["));
			
			data[i][0] = sTemp;
			data[i][1] = "0";
			// System.out.println(data[i][ii]);

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