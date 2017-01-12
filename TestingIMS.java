package commerceHub;

import java.io.IOException;

public class TestingIMS {
public static void main(String[] args) throws IOException {
	CommerceHubIMS cims = new CommerceHubIMS();
	cims.dataInitialize();
	cims.displayProductDetails();
	cims.addProduct("1003", 456, "section3");
	cims.displayProductDetails();
	System.out.println(cims.getProductLocation("1002"));
	System.out.println(cims.pickProduct("1001", 23));
	cims.displayProductDetails();
	cims.restockProduct("1001", 3000);
	cims.displayProductDetails();
}
}
