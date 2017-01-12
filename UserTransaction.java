package commerceHub;
/**
 * The Object of this class is considered as the task that is submitted to the ThreadPoolExecutor used to process the user transaction.
 * @author Anandaraju CS
 *
 */
public class UserTransaction implements Runnable{

	CommerceHubIMS IMSInstance;
	boolean userLogged  = false;
	boolean transactionRecieved = false;
	Transaction currentTrans;
	String threadName;
	public UserTransaction(String threadName)
	{
		this.threadName=threadName;
	}
	public void setInstance(CommerceHubIMS IMSInstance)
	{
		this.IMSInstance = IMSInstance;
	}

	public void setUserStatus(boolean loggedIn)
	{
		this.userLogged = loggedIn;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transactionRecieved=true;
		this.currentTrans = transaction;
	}

	@Override
	public void run() {
		if(transactionRecieved)
		{
			switch(currentTrans.transactionNumber)
			{
			case 1:
				IMSInstance.addProduct(currentTrans.productId,currentTrans.stockLevel, currentTrans.location);
				break;
			case 2:
				PickingResult pickResult = IMSInstance.pickProduct(currentTrans.productId,currentTrans.stockLevel);
				System.out.println(pickResult.outputMessage());
				break;
			case 3:
				RestockingResult restockResult = IMSInstance.restockProduct(currentTrans.productId,currentTrans.stockLevel);
				System.out.println(restockResult.outputMessage());
				break;
			case 4:
				String location = IMSInstance.getProductLocation(currentTrans.productId);
				// the output is written in the system console.
				System.out.println("The product " + currentTrans.productId + " is located at " +location);
				break;
			case 5:
				IMSInstance.displayProductDetails();
				break;
			default:
				System.out.println("Unknown Operation : Enter valid operations");
				break;
			}
		}
		transactionRecieved=false;
	}

}
