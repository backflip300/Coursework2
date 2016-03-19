package dryruns;

import processing.ExtractProduct;
import processing.Product;


// TODO: Auto-generated Javadoc
/**
 * The Class ExtractProductTest.
 */
public class ExtractProductTest {
	
	/** The products. */
	static Product[] products = new Product[2];
	
	/** The te. */
	static String[] te = new String[2];
	
	/** The test. */
	static ExtractProduct test = new ExtractProduct();
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		te[0] = "Blue";
		te[1] = "RedBlue";
		products = test.Extractproducts(te);
		
		System.out.println(products[0].time);
		
	}
}
