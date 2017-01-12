package commerceHub;

/**
 * This class defines about the result when a restocking operation is performed.
 * This class is a child class of the Abstract class TransactionResult
 * @author Anandaraju CS
 *
 */
public class RestockingResult extends TransactionResult{
	
	public RestockingResult(boolean status, String productId, int stockLevel, String location)
	{
		this.productId = productId;
		this.stockQuantity = stockLevel;
		this.successStatus = status;
		this.location = location;
	}
	
	public String outputMessage()
	{
		if(this.successStatus == true)
		{
			message = "The product " + productId + " is restocked successfully in the " + location + " location.\n";
			message += "Current count is : " + stockQuantity;
		}else
		{
			message = "The product " + productId + " is not available in the warehouse.\n";
			message += "So use the 'ADD' command to include the product in the product list.";
		}
		return message;
	}
}
