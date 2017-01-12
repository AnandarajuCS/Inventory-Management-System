ReadMe.txt

!!! IMPORTANT TO READ !!!

BEFORE YOU RUN :
1. Create a package named "commerceHub" and place all the java files under the package.
2. START class - "IMSApplication"

Welcome to CommerceHub Inventory Management System.

1 - The application is a simple standalone application, that manages the inventory stored in the warehouse.
2 - The starting point of the application is "IMSApplication" class, which contains the main method.
3 - The application instance is created with the start of the IMSApplication class.
4 - CLI (Command Line Interface) interface is provided for the user's login and inventory access.
5 - For simplicity, we have considered to store the product in File system, in the file name "commerceHubProductData.txt",
which in real time DataBase can be used.
6 - Currently we are using same terminal (System console) as input for both the mock users (user1 and user2). 
So each command from the user's actual terminal (in real time), is considered as different command to our application here.

Eg: Let us consider two users user1 and user2 are logged in. We consider the below commands as originating from different terminals

user1 terminal#  3 user1 <productId> <amount_of_stock> <location>
user2 terminal#  3 user2 <productId> <amount_of_stock> <location>

where, <3> - is the operation to be performed. Eg. Pick a product, Restock a product, Add a product, Get the location, 
Display the product details, User login, User logout.


EXAMPLE CLI commands:

User 'user1' login >> 1 user1 user1@123

User 'user1' pick product >> 4 user1 1001 235

User 'user2' login >> 1 user2 user2@123 

User 'user2' restock product >> 5 user2 1001 300

User 'user1' display products list >> 7 user1

Try it out !!! :)