# Storage Manager back-end
This is the back-end of the storage manager. It provides an overview of the products you currently have at home, allowing you to check your stock while buying groceries. Additionally, the program reminds you when something is expiring soon, based on the expiry date you entered. The front-end will communicate with the back-end through HTTP requests, a feature that has already been implemented.  

The system works with the EAN number of the product. Users will be able to scan the EAN barcode of the product with their phone, and the information will then be sent to the server. In the future, you will be able to attach PDF receipts from supported supermarket chains, eliminating the need to scan every product. However, you will still need to manually enter the expiry date of each product.

## API endpoints
- **/api/sendean** - This endpoint loads the specified product into the cache table (to prevent spamming the OpenFoodFacts API every time you e.g. load the current stock) and adds it to the current stock.
- **/api/removeean** - This endpoint removes the specified product from the current stock.
- **/api/currentstock** - This endpoint returns the current stock, including all necessary information for each product. Currently, only sorting is available, but filtering and search functions are planned.
- **/api/productstock** - This endpoint returns all entries of a specified product (identified by their EAN) that are currently in stock.


## Usage
**This is still in development and not production ready yet.**  
To use it you should have a current Java Runtime Environment (JRE) installed. Download the jar from the release tab or build it yourself with the source code. To start it run it in the terminal with ```java -jar BasementStorageManager-0.1.0-alpha.jar``` and than it runs on 127.0.0.1 on port 8080 (this will be in a config in the future, currently it's hard coded in the main class). To get an overview of the endpoints take a look at the previous paragraph.
