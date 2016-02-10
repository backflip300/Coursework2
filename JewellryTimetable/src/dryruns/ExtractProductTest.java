package dryruns;

import javax.xml.transform.Templates;

import processing.ExtractProduct;
import processing.Product;

public class ExtractProductTest {
	static Product[] products = new Product[2];
	static String[] te = new String[2];
	static ExtractProduct test = new ExtractProduct();
	public static void main(String args[]){
		te[1] = "RedBlue";
		te[0] = "Blue";
		products = test.Extractproducts(te);
		
		System.out.println(products[0].quantity[0]);
		
	}
}
