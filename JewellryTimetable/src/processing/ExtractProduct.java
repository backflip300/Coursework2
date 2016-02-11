package processing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExtractProduct {

	FileAccess file = new FileAccess(Paths.get("TextFiles/Products.txt"));
	String currentLine;
	int openBracket, closeBracket, comma, numOfStocks;
	ArrayList<Integer> slashes = new ArrayList<Integer>();

	public Product[] Extractproducts(String[] desiredProducts) {
		ArrayList<String> fileData = new ArrayList<String>();
		Product[] products = new Product[desiredProducts.length];
		String Name;
		String[] stocks;
		int[] quantity;
		int time;
		int profit;
		fileData = file.sReadFileData();
		for (int i = 0; i < fileData.size(); i++) {
			currentLine = fileData.get(i);
			for (int x = 0; x < desiredProducts.length; x++) {
				if (extractName().equals(desiredProducts[x])) {
					getpoints();
					Name = extractName();
					stocks = extractStocks();
					quantity = extractQuantity();
					time = extractTime();
					profit = extractProfit();
					products[x] = new Product(Name, stocks, quantity, time,
							profit);
					break;
				}
			}
		}
		return products;

	}

	private void getpoints() {
		slashes.clear();
		openBracket = currentLine.indexOf("[");
		closeBracket = currentLine.indexOf("]");
		comma = currentLine.indexOf(",");
		numOfStocks = 0;
		for (int i = 0; i < currentLine.length(); i++) {
			if (currentLine.charAt(i) == '/') {
				numOfStocks++;
				slashes.add(i);
				//System.out.println("gothere");
				System.out.println("size: " + slashes.size());
			}
		}
		numOfStocks = numOfStocks / 2;
	}

	public String extractName() {
		String Name = currentLine.substring(0, currentLine.indexOf("["));

		return Name;
	}

	public String[] extractStocks() {
		String[] Stocks;
		Stocks = new String[numOfStocks];
		System.out.println("stock num: " + numOfStocks);
		for (int i = 0; i < numOfStocks; i++) {
			Stocks[i] = currentLine.substring(slashes.get(2 * i) + 1,
					slashes.get(2 * i + 1));
		}

		return Stocks;

	}

	public int[] extractQuantity() {
		int[] Quantity = new int[numOfStocks];;
		
		for (int i = 0; i < numOfStocks - 1; i++) 
		{
			System.out.println("\t" + i);
			System.out.println(currentLine.substring(
					slashes.get(2 * i + 1) + 1, slashes.get(2 * i + 2)));
			Quantity[i] = Integer.parseInt(currentLine.substring(
					slashes.get((2 * i) + 1) + 1, slashes.get(2 * i + 2)));
		}
		Quantity[Quantity.length -1] = Integer.parseInt(currentLine.substring(
			slashes.get(slashes.size()-1) +1, closeBracket));
		return Quantity;
	}

	public int extractTime() {
		int time;
		time = Integer.parseInt(currentLine.substring(closeBracket + 1, comma));
		return time;
	}

	public int extractProfit() {
		int profit;
		
		profit = Integer.parseInt(currentLine.substring(comma + 1));
		
		return profit;
	}
}
