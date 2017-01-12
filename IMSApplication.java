package commerceHub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Starting point of the application.
 * The class creates the instance of the application and also the CLI (Command Line Interface)
 * @author Anandaraju CS
 *
 */
public class IMSApplication {

	/**
	 *  User's login data , currently hard coded. In real time can be accessed from DataBase or any authentication server and stored.
	 */
	public static Map<String,String> userData = new HashMap<String,String>();
	/**
	 * 	Currently logged in users.
	 */
	public Map<String, UserTransaction> activeUsers = new HashMap<String, UserTransaction>();
	public CommerceHubIMS chIMS;
	/**
	 * The Executors are initialized with fixed number of threads.
	 * This helps in handling multiple requests from multiple users from terminals
	 * This can be modified based on the application size and frequency of usage.
	 */
	public static final int USER_THREAD_POOL_SIZE = 10;
	
	/**
	 * The time interval to update the back-end file with the new product list changes.
	 * Default value set to 1 minute.
	 * This should be modified based on the application size, number of users, 
	 * so we can increase or decrease the frequency to update optimal load.
	 */
	public static final int TIME_INTERVAL_TO_UPDATE = 1;
	public ThreadPoolExecutor transactionsExecutor;

	public static void main(String[] args) {
		System.out.println("Welcome to Commerce Hub's Inventry Management System");
		loadUserData();
		IMSApplication IMSAppInstance = new IMSApplication();

		//initialize the thread pool executor for processing the user transactions
		IMSAppInstance.transactionsExecutor=(ThreadPoolExecutor) Executors.newFixedThreadPool(USER_THREAD_POOL_SIZE);
		IMSAppInstance.chIMS = new CommerceHubIMS();
		try {
			IMSAppInstance.chIMS.dataInitialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Thread to update the backend file periodically at an specified interval
		ProductListUpdater productUpdater = new ProductListUpdater(IMSAppInstance.chIMS, TIME_INTERVAL_TO_UPDATE);
		Thread productUpdateThread = new Thread(productUpdater);
		productUpdateThread.start();
		System.out.println("Starting CLI .... ");
		IMSAppInstance.startCLI();
	}
	
	public void startCLI()
	{
		boolean exit=false;
		Scanner userInput = new Scanner(System.in);
		// For now lets consider only two users are registered for accessing the application. (user1 and user2)
		// lets simulate that, two terminals being opened, one for each user.
		// and users are entering their operations to perform on the IMS

		while(!exit)
		{
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			System.out.println("Enter your operation : ");
			System.out.println("1. User login - syntax - <<1 userId password>>");
			System.out.println("2. User logout - syntax - <<2 userId>>");
			System.out.println("3. Add a new product - syntax - <<3 userId productId amount_of_stock location>>");
			System.out.println("4. Pick a product - syntax - <<4 userId productId amount_of_stock>>");
			System.out.println("5. Restock a product - syntax - <<5 userId productId amount_of_stock>>");
			System.out.println("6. Get the location of the product - syntax - <<6 userId productId>>");
			System.out.println("7. Display the current product list - syntax - <<7 userId>>");
			System.out.println("--------------------------------------------------------------");
			System.out.println();

			String[] inputs = userInput.nextLine().split(" ");
			if(inputs.length == 0)
			{
				System.out.println("Enter valid operations as shown below: ");
				continue;
			}
			if(inputs[0].equals("exit") || inputs[0].equals("logout"))
			{
				break;
			}else
			{
				try{
					switch(Integer.valueOf(inputs[0]))
					{
					case 1:
						//User login
						if(userData.containsKey(inputs[1]) && userData.get(inputs[1]).equals(inputs[2]))
						{	
							UserTransaction userThread = new UserTransaction(inputs[1]);
							userThread.setInstance(chIMS);
							userThread.setUserStatus(true);
							activeUsers.put(inputs[1], userThread);
							System.out.println("User " + inputs[1] + " successfully logged IN !!!");
						}else
						{
							System.out.println("Incorrect UserName or password!!!");
						}
						break;
					case 2:
						//User logout
						if(activeUsers.containsKey(inputs[1]))
						{	
							activeUsers.remove(inputs[1]);
							System.out.println("User " + inputs[1] + " successfully logged OUT !!!");
						}
						else
						{
							System.out.println("User : " + inputs[1] + " not logged in ");
						}
						break;
					case 3:
						//add product
						activeUsers.get(inputs[1]).setTransaction(new Transaction(1,inputs[2],Integer.valueOf(inputs[3]),inputs[4]));
						transactionsExecutor.submit(activeUsers.get(inputs[1]));
						break;
					case 4:
						//pick product
						activeUsers.get(inputs[1]).setTransaction(new Transaction(2,inputs[2],Integer.valueOf(inputs[3])));
						transactionsExecutor.submit(activeUsers.get(inputs[1]));
						break;
					case 5:
						//Restock product
						activeUsers.get(inputs[1]).setTransaction(new Transaction(3,inputs[2],Integer.valueOf(inputs[3])));
						transactionsExecutor.submit(activeUsers.get(inputs[1]));
						break;
					case 6:
						//get product location
						activeUsers.get(inputs[1]).setTransaction(new Transaction(4,inputs[2]));
						transactionsExecutor.submit(activeUsers.get(inputs[1]));
						break;
					case 7:
						//Display the product list
						activeUsers.get(inputs[1]).setTransaction(new Transaction(5));
						transactionsExecutor.submit(activeUsers.get(inputs[1]));
						break;
					default:
						System.out.println("Enter valid inputs according to the syntax shown !!! ");
						break; 
					}
				}catch(Exception e)
				{
					System.out.println("Invalid inputs entered or user not logged in  !! try again !");
					continue;
				}
			}
		}
		userInput.close();
	}
	// Mock user data
	public static void loadUserData()
	{
		userData.put("user1","user1@123");
		userData.put("user2","user2@123");
	}
	/**
	 * Authenticate the user
	 * @param user
	 * @param password
	 */
	public void userLogin(String user, String password)
	{
		if(userData.containsKey(user) && userData.get(user).equals(password))
		{	
			System.out.println("User " + user + " successfully logged in !!!");
			UserTransaction userThread = new UserTransaction(user);
			userThread.setInstance(chIMS);
			userThread.setUserStatus(true);
			activeUsers.put(user, userThread);
		}else
		{
			System.out.println("Incorrect UserName or password!!!");
		}
	}
	// method used during testing. currently not used
	public void createTransactions() 
	{
		activeUsers.get("user1").setTransaction(new Transaction(1,"1003",456,"section3"));
		System.out.println("logging out user1");
		activeUsers.get("user1").setTransaction(new Transaction(4));
		System.out.println("user 1 logged out.");
		System.out.println("logging out user2");
		activeUsers.get("user2").setTransaction(new Transaction(4));
		System.out.println("user 2 logged out.");
	}
	// method used during testing. currently not used
	public void testTrans()
	{
		activeUsers.get("user1").setTransaction(new Transaction(1,"1003",456,"section3"));
		transactionsExecutor.submit(activeUsers.get("user1"));
		chIMS.displayProductDetails();
		activeUsers.get("user2").setTransaction(new Transaction(1,"1004",567,"section4"));
		transactionsExecutor.submit(activeUsers.get("user2"));
		chIMS.displayProductDetails();
		activeUsers.get("user1").setTransaction(new Transaction(4));
		chIMS.displayProductDetails();
		activeUsers.get("user2").setTransaction(new Transaction(4));
		chIMS.displayProductDetails();
	}
}
