package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.clappedArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import processing.FileAccess;
import tableModels.Tab1TableModel;
import net.miginfocom.swing.MigLayout;

public class Tab1 {
	private JPanel tab1;
	private JTable table;
	private Tab1TableModel tModel;
	private ScrollTextArea console;
	private JPanel cPanel;
	private DefaultTableModel dtablemodel;
	private FileAccess Stocks, OrderHistory;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();

	public Tab1() {

		Stocks = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
		OrderHistory = new FileAccess(Paths.get("TextFiles/OrderHistory.txt"));

	}

	@SuppressWarnings("serial")
	public JPanel create() {
		// Frame1.setSize(new Dimension);
		tab1 = new JPanel();
		tab1.setLayout(new MigLayout());
		// buttons
		
		JButton restock = new JButton("Restock");
		JButton newStock = new JButton("New Stock");
		// scroll area
		cPanel = new JPanel();
		cPanel.setPreferredSize(new Dimension(500, 400));
		console = new ScrollTextArea(500, 400);
		console.setPreferredSize(new Dimension(520, 475));
		cPanel.add(console);
		// get order history
		getOrderHistory();

		// table

		tModel = new Tab1TableModel();
		
		dtablemodel = new DefaultTableModel(tModel.data, new Object[] { "Name",
				"Current in Stock in /g or /cm", "# to restock" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};
	

		// add above to tab1
		tab1.add(cPanel, "east");
		tab1.add(restock, "South");
		tab1.add(newStock, "South");

		table = new JTable(dtablemodel);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		tab1.add(scrollPane, BorderLayout.CENTER);

		// button actions

		newStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> stemp = new ArrayList<String>();
				FileAccess access = new FileAccess(Paths.get("TextFiles/Stocks.txt"));
				stemp.add(JOptionPane.showInputDialog("name of Stock"));
				stemp.add(JOptionPane.showInputDialog("current # in stock"));
				dtablemodel.addRow(new Object[] { stemp.get(0), stemp.get(1), 0 });
				access.sWriteFileData(stemp.get(0));
				access.sWriteFileData(stemp.get(1));
			}
		});

		restock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.editCellAt(0, 2);
				table.getCellEditor().stopCellEditing();
				for (int x = 0; x < dtablemodel.getRowCount(); x++) {
					if ((dtablemodel.getValueAt(x, 2)).equals("")) {
						dtablemodel.setValueAt("0", x, 2);
					}
				}
				
				ArrayList<String> history = new ArrayList<String>();
				ArrayList<Integer> cStocks = new ArrayList<Integer>();
				ArrayList<Integer> rStocks = new ArrayList<Integer>();
				String confirm = "to restock \n";
				String tempcStocks, temprStocks;
				cStocks.clear();
				rStocks.clear();
				for (int i = 0; i < dtablemodel.getRowCount(); i++) {

					tempcStocks = dtablemodel.getValueAt(i, 1).toString();
					temprStocks = dtablemodel.getValueAt(i, 2).toString();
					// System.out.println("\n \n \n" + i);

					// System.out.println( "\t" + dtablemodel.getValueAt(i, 2));

					cStocks.add(Integer.parseInt(tempcStocks));

					rStocks.add(Integer.parseInt(temprStocks));

					if (rStocks.get(i) != 0) {

						confirm += "\n" + dtablemodel.getValueAt(i, 0) + " x "
								+ rStocks.get(i) + "\t total: "
								+ (cStocks.get(i) + rStocks.get(i));
						history.add(dtablemodel.getValueAt(i, 0) + " x "
								+ rStocks.get(i) + "\t total: "
								+ (cStocks.get(i) + rStocks.get(i)));
					}
				}

				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, confirm,
						"Comfirmation", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					for (int ii = 0; ii < dtablemodel.getRowCount(); ii++) {
						dtablemodel.setValueAt(
								(Object) (cStocks.get(ii) + rStocks.get(ii)),
								ii, 1);
						dtablemodel.setValueAt(0, ii, 2);
						Stocks.sEditline(
								String.valueOf(cStocks.get(ii)
										+ rStocks.get(ii)), (2 * ii) + 1);

					}
					OrderHistory.sWriteFileData("\n"
							+ dateFormat.format(cal.getTime()));
					console.appendToOutput(
							"\n" + dateFormat.format(cal.getTime()) + "\n",
							Color.black, false);
					for (int x = 0; x < history.size(); x++) {
						OrderHistory.sWriteFileData(history.get(x));
						console.appendToOutput(history.get(x) + "\n",
								Color.BLACK, false);
					}

				} else {

				}
				history.clear();
				cStocks.clear();
				rStocks.clear();
			}
		});
		return tab1;
	}

	private void getOrderHistory() {
		ArrayList<String> tOrderHistory = new ArrayList<String>();
		tOrderHistory = OrderHistory.sReadFileData();
		for (int i = 0; i < tOrderHistory.size(); i++) {
			console.appendToOutput(tOrderHistory.get(i) + "\n", Color.BLACK,
					false);
		}
	}
}
