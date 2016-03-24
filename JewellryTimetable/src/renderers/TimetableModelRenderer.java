package renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import processing.Validator;


/**
 * The Class TTMRenderer.
 */
@SuppressWarnings("serial")
public class TimetableModelRenderer extends DefaultTableCellRenderer {

	/** The validater. */
	Validator validator = new Validator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(
	 * javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			c.setBackground(table.getBackground());
		} else if (validator.vtime((String) table.getValueAt(row, column)) == true) {
			c.setBackground(new java.awt.Color(163, 193, 65));

		} else {
			c.setBackground(new java.awt.Color(244, 43, 43));
		}
		return c;
	}
}
