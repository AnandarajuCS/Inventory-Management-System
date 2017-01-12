package commerceHub;

/**
 * Product class contains the details of the product that is used by the Company.
 * @author Anandaraju CS
 *
 */
public class Product {
	public String productId;
	public int stockLevel;
	public String location;
	// In future this member variable list can be extended to include, Category(Electronics, Household, etc.), Image of the product, etc.
	
	public Product(String productId, int level, String location)
	{
		this.productId=productId;
		this.location=location;
		this.stockLevel=level;
	}

	public void setStockLevel(int newQuantity){
		this.stockLevel = newQuantity;
	}
	
	public String getProductId() {
		return productId;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public String getLocation() {
		return location;
	}
	
	
}
