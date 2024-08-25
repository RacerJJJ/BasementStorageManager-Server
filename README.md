# Storage Manager back-end
This is the back-end of the storage manager. It provides an overview of the products you currently have at home, allowing you to check your stock while buying groceries. Additionally, the program reminds you when something is expiring soon, based on the expiry date you entered. The front-end will communicate with the back-end through HTTP requests, a feature that has already been implemented.  

The system works with the EAN number of the product. Users will be able to scan the EAN barcode of the product with their phone, and the information will then be sent to the server. In the future, you will be able to attach PDF receipts from supported supermarket chains, eliminating the need to scan every product. However, you will still need to manually enter the expiry date of each product.

## API endpoints
- **/api/sendean** - This endpoint loads the specified product into the cache table (to prevent spamming the OpenFoodFacts API every time you e.g. load the current stock) and adds it to the current stock.
- **/api/removeean** - This endpoint removes the specified product from the current stock.
- **/api/currentstock** - This endpoint returns the current stock, including all necessary information for each product. Currently, only sorting is available, but filtering and search functions are planned.
- **/api/productstock** - This endpoint returns all entries of a specified product (identified by their EAN) that are currently in stock.


### This software is not finished yet and can't be used yet. 
