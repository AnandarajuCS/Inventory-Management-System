package commerceHub;
/**
 * Abstract class defining the Result that the CommerceHubIMS class methods are returning.
 * @author Anandaraju CS
 *
 */
public abstract class TransactionResult {
	boolean successStatus = false;
	String productId = "";
	int stockQuantity = 0;
	String location = "";
	String message="";
	
	public String getProductId()
	{
		return this.productId;
	}
	
	public int getCurrentStockLevel()
	{
		return this.stockQuantity;
	}
	
	public boolean getStatus() 
	{	
		return this.successStatus;
	}

	public String getLocation() 
	{
		return this.location;
	}
}
