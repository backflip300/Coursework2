package processing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

	
public class ExtractProduct {


 FileAccess file = new FileAccess(Paths.get("Images/Products.txt"));
 String currentLine;
 
 
 public Product[] Extractproducts(String[] desiredProducts){
		ArrayList<String> fileData = new ArrayList<String>();
		Product[] products = new Product[desiredProducts.length];
		String Name;
		String[] stocks;
		int[] quantity;
		int time;
		int profit;
		fileData = file.sReadFileData();
		for (int i = 0;i < fileData.size();i++){
			currentLine = fileData.get(i);
			for (int x = 0; x < desiredProducts.length; x++ ){
				if(extractName().equals(desiredProducts[i])){
					Name = extractName();
					stocks = extractStocks();
					quantity = extractQuantity();
					time = extractTime();
					profit = extractProfit();
					products[i] = new Product(Name, stocks, quantity, time, profit);
					break;
				}
			}
		}
		return products;
		
	}
 
 
 public String extractName(){
	String Name = currentLine.substring(0,currentLine.indexOf("["));
	
	return Name;
 }
 
 public String[] extractStocks(){
	 String[] Stocks;
	 int numOfStocks = 0;
	 for(int i = 0; i < currentLine.length(); i++){
		 if(currentLine.charAt(i) == '\\'){
			 
		 }
	 }
 }
 
 public int[] extractQuantity(){
	 
 }
 
 public int extractTime(){
	 
 }
 
 public int extractProfit(){
	 
 }
}
