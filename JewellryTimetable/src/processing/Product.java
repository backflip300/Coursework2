package processing;


/**
 * The Class Product.
 */
public class Product {
	
	/** The Name. */
	public String Name;
	
	/** The stocks. */
	public String[] stocks;
	
	/** The quantity. */
	public int[] quantity;
	
	/** The time. */
	public int time;
	
	/**
	 * Instantiates a new product.
	 *
	 * @param Name the name
	 * @param stocks the stocks
	 * @param quantity the quantity
	 * @param time the time
	 */
	public Product(String Name, String[] stocks, int[] quantity, int time) {
		this.Name = Name;
		this.stocks = new String[stocks.length];
		this.quantity = new int[quantity.length];
		this.stocks = stocks;
		this.quantity = quantity;
		this.time = time;

	}
	
}
