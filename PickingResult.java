package commerceHub;

/**
 * This class defines about the result when a picking operation is performed.
 * This class is a child class of the Abstract class TransactionResult
 * @author Anandaraju CS
 *
 */
public class PickingResult extends TransactionResult{
	
	public PickingResult(boolean status, String productId, int stockLevel, String location)
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
			message = "The product " + productId + " is picked successfully from the " + location + " location.\n";
			message += "Remaining count is : " + stockQuantity;
		}else
		{
			if(productId.length() > 0)
			{
				message = "The requested number of stocks for the product " + productId + " is not available.\n";
				message += "Please choose quantity atmost " + stockQuantity + ". Or Restock the product !!!";
			}else
			{
				message = "The requested product is not available !!! ";
			}
		}
		return message;
	}
}
