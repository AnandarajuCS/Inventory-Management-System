package commerceHub;

/**
 * Each request from the user's terminal is considered as a Transaction.
 * Each transaction has a defined number to know the required operation to perform.
 * @author Anandaraju CS
 *
 */
public class Transaction {
	String productId;
	String location;
	int stockLevel=0;
	int transactionNumber=-1;
	
	/*
	 * Transaction - 1 -> to add the product
	 * Transaction - 2 -> to pick the product
	 * Transaction - 3 -> to restock the product
	 * Transaction - 4 -> to get the location of the product
	 * Transaction - 5 -> to display the current product list
	 */
	
	// Transaction - 1 : Transaction to add the product
	public Transaction(int transaction, String productId, int level, String location)
	{
		this.transactionNumber= transaction;
		this.productId=productId;
		this.location=location;
		this.stockLevel=level;
	}
	// Transaction - 2/3 : transaction to Pick or Restock the particular product
	public Transaction(int transaction, String productId, int level)
	{
		this.transactionNumber= transaction;
		this.productId=productId;
		this.stockLevel=level;
	}
	// Transaction - 4 : transaction to get the location of the product
	public Transaction(int transaction, String productId)
	{
		this.transactionNumber = transaction;
		this.productId=productId;
	}
	
	// Transaction - 5 : transaction to display the current product list
	public Transaction(int transaction)
	{
		this.transactionNumber=transaction;
	}
}
