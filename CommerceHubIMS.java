package commerceHub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This the important class holding the product data in-memory and handling all the operations.
 * Operations include: Add, Pick, Restock and get the Location
 * @author Anandaraju CS
 *
 */
public class CommerceHubIMS implements InventoryManagementSystem{
	
	// Map to store the Product Objects.
	// ConcurrentHashMap will handle the concurrent access of Product objects during read and write access operations
	// by multiple users concurrently, which is thread safe.
	ConcurrentHashMap<String,Product> productMap = new ConcurrentHashMap<String,Product>();

	public CommerceHubIMS getInstance()
	{
		return this;
	}

	/**
	 * This method is invoked to initialize the inventory data on the IMS startup
	 * @return 
	 * @throws IOException 
	 */
	public boolean dataInitialize() throws IOException
	{
		System.out.println("\nProduct data loading...");

		// Fetches the data from the file and places in memory during startup
		// the file is expected to have the three values in each row

		// ProductId:stockLevel:Location

		File productData = new File("commerceHubProductData.txt");
		if(!productData.exists())
		{
			productData.createNewFile();
			loadMockData(productData);
		}
		Scanner dataScanner = new Scanner(productData);
		while(dataScanner.hasNextLine())
		{
			String dataLine = dataScanner.nextLine();
			System.out.println(dataLine);
			String[] data = dataLine.split(":");
			productMap.put(data[0], new Product(data[0],Integer.valueOf(data[1]),data[2]));
		}
		dataScanner.close();

		System.out.println("Product data successfully loaded !!!\n");
		return true;
	}

	public void loadMockData(File productData) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(productData, false);
			fileWriter.write("1001:800:section1\n");
			fileWriter.write("1002:200:section2");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void addProduct(String productId, int level, String location)
	{
		if(productMap.containsKey(productId))
		{
			// if the product already exists.
			restockProduct(productId, level);
		}else
		{
			productMap.put(productId, new Product(productId,level,location));
			System.out.println(productId+ " -- > product added");
		}
	}

	@Override
	public PickingResult pickProduct(String productId, int amountToPick) {
		PickingResult result = null;
		if(productMap.size() > 0 && productMap.containsKey(productId))
		{
			Product product = productMap.get(productId);
			int quantityAvailable = product.getStockLevel();

			if(quantityAvailable < amountToPick)
			{
				// failure result
				result = new PickingResult(false, productId, quantityAvailable, product.getLocation());
				return result;
			}else
			{
				product.setStockLevel(quantityAvailable-amountToPick);
				productMap.put(productId,product);
				// success result
				result = new PickingResult(true, productId, quantityAvailable-amountToPick, product.getLocation());
				return result;
			}
		}
		result = new PickingResult(false,"",-1,"");
		return result;
	}

	@Override
	public RestockingResult restockProduct(String productId, int amountToRestock) {
		RestockingResult result = null;
		if(productMap.size() > 0 && productMap.containsKey(productId))
		{
			Product product = productMap.get(productId);
			int quantityAvailable = product.getStockLevel();
			product.setStockLevel(quantityAvailable+amountToRestock);

			// return the updated amount of product
			result = new RestockingResult(true, productId, quantityAvailable+amountToRestock, product.getLocation());
		}else
		{
			result = new RestockingResult(false,"",-1,"");
		}
		return result;
	}

	/**
	 * Return the location of product
	 * @param productId
	 * @return
	 */
	public String getProductLocation(String productId)
	{
		if(productMap.size() > 0)
		{
			if(productMap.containsKey(productId))
			{
				return productMap.get(productId).getLocation();
			}
		}
		return null;
	}

	public void displayProductDetails()
	{
		System.out.println("--------------------------------------------------------------");
		System.out.println("PRODUCT DETAILS : product Id - stock level - location");
		System.out.println("--------------------------------------------------------------");
		for(String pro : productMap.keySet())
		{
			System.out.println(pro+" - "+productMap.get(pro).getStockLevel() + " - " + productMap.get(pro).getLocation());
		}
		System.out.println("--------------------------------------------------------------");
	}
}
