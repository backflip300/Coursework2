package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import processing.FileAccess;
import processing.Validater;
import tableModels.StockTableModel;
import net.miginfocom.swing.MigLayout;

public class Tab1 {
	private JPanel tab1;
	private JTable stockTable;
	private StockTableModel tModel;
	private ScrollTextArea console;
	private JPanel cPanel;
	private Validater validater = new Validater();
	private DefaultTableModel stockTableModel;
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
		JButton deleteStock = new JButton("Delete Stock");
		
		//sort out buttons
	
		// scroll area
		cPanel = new JPanel();
		cPanel.setPreferredSize(new Dimension(300, 400));
		console = new ScrollTextArea(300, 400);
		console.setPreferredSize(new Dimension(320, 475));
		cPanel.add(console);
		// get order history
		getOrderHistory();

		// table

		tModel = new StockTableModel();
		
		stockTableModel = new DefaultTableModel(tModel.data, new Object[] { "Name",
				"Current in Stock in /g or /cm", "# to restock" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		stockTable = new JTable(stockTableModel);
		stockTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(stockTable);
		// add above to tab1
		tab1.add(scrollPane, "cell 0 0, split 2");
		tab1.add(cPanel, "cell 0 0");
		tab1.add(restock, "cell 0 1, split 3,grow");
		tab1.add(newStock, "cell 0 1 , grow");
		tab1.add(deleteStock, "cell 0 1,grow");
		
		

		// button actions

		newStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String Stock;
				String number;
				int entered = 0;
				while (entered == 0) {
					Stock = JOptionPane.showInputDialog("name of Stock");
					System.out.println(Stock);
					if (validater.vSimpleString(Stock) == true) {
						number = JOptionPane
								.showInputDialog("current # in stock");
						if (validater.vOnlyContainsNumbers(number) == true
								&& Integer.parseInt(number) >= 0) {

							ArrayList<String> stemp = new ArrayList<String>();
							stemp.add(Stock);
							stemp.add(number);
							FileAccess access = new FileAccess(Paths
									.get("TextFiles/Stocks.txt"));
					
							stockTableModel.addRow(new Object[] { stemp.get(0), stemp.get(1), 0 });
							access.sWriteFileData(stemp.get(0));
							access.sWriteFileData(stemp.get(1));
							entered = 1;
						}else if(number == ""){
							entered = -1;
							break;
						}
						

					} else if (Stock.equals("")) {
						entered = -1;
						break;
					}

				}

				
			}
		});
		deleteStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
				
			}
		});
		restock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				stockTable.editCellAt(0, 2);
				stockTable.getCellEditor().stopCellEditing();
				for (int x = 0; x < stockTableModel.getRowCount(); x++) {
					if ((stockTableModel.getValueAt(x, 2)).equals("")) {
						stockTableModel.setValueAt("0", x, 2);
					}
				}

				ArrayList<String> history = new ArrayList<String>();
				ArrayList<Integer> cStocks = new ArrayList<Integer>();
				ArrayList<Integer> rStocks = new ArrayList<Integer>();
				String confirm = "to restock \n";
				String tempcStocks, temprStocks;
				cStocks.clear();
				rStocks.clear();
				
				
				try{
				for (int i = 0; i < stockTableModel.getRowCount(); i++) {

					tempcStocks = stockTableModel.getValueAt(i, 1).toString();
					temprStocks = stockTableModel.getValueAt(i, 2).toString();
					// System.out.println("\n \n \n" + i);

					// System.out.println( "\t" + dtablemodel.getValueAt(i, 2));

					cStocks.add(Integer.parseInt(tempcStocks));

					rStocks.add(Integer.parseInt(temprStocks));

					if (rStocks.get(i) != 0) {

						confirm += "\n" + stockTableModel.getValueAt(i, 0) + " x "
								+ rStocks.get(i) + "\t total: "
								+ (cStocks.get(i) + rStocks.get(i));
						history.add(stockTableModel.getValueAt(i, 0) + " x "
								+ rStocks.get(i) + "\t total: "
								+ (cStocks.get(i) + rStocks.get(i)));
					}
				}

				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, confirm,
						"Comfirmation", dialogButton);
				
				
				if (dialogResult == JOptionPane.YES_OPTION) {
					for (int ii = 0; ii < stockTableModel.getRowCount(); ii++) {
						stockTableModel.setValueAt(
								(Object) (cStocks.get(ii) + rStocks.get(ii)),
								ii, 1);
						stockTableModel.setValueAt(0, ii, 2);
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
			}catch(NumberFormatException e){
				console.appendToOutput("error: incorrect input (try using just positive numbers)", Color.red, true);
			}
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
