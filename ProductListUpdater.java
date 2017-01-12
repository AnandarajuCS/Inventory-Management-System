package commerceHub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 *  This class is used to update the back-end file asynchronously at regular frequency.
 *  So when the application exits or when shutdown unexpectedly, we can have the backup of the product list
 */
public class ProductListUpdater implements Runnable{

	CommerceHubIMS IMSInstance;
	// time interval to update the file - in minutes.
	int frequencyToUpdate = 0;

	public ProductListUpdater(CommerceHubIMS IMSInstance, int freq)
	{
		this.IMSInstance = IMSInstance;
		this.frequencyToUpdate = freq;
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(frequencyToUpdate*1000);
				writeListToFile();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeListToFile()
	{
		File productData = new File("commerceHubProductData.txt");
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(productData, false);
			Map<String,Product> productMap = IMSInstance.productMap;
			for(String proId : productMap.keySet())
			{
				Product product = productMap.get(proId);
				String entry = proId+":"+product.getStockLevel()+":"+product.getLocation()+"\n";
				fileWriter.write(entry);
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
