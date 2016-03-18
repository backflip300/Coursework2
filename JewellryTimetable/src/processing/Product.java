package processing;

import java.util.ArrayList;

public class Product {
	public String Name;
	public String[] stocks;
	public int[] quantity;
	public int time;
	public Product(String Name, String[] stocks, int[] quantity, int time) {
		this.Name = Name;
		this.stocks = new String[stocks.length];
		this.quantity = new int[quantity.length];
		this.stocks = stocks;
		this.quantity = quantity;
		this.time = time;

	}
	
}
