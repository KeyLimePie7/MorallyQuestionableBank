# Hello
This is a simple, full stack mock banking web application. 
It uses DerbyDB as a database, and JDBC to connect to the DB.
It uses Spring MVC as a framework, and the application is properly split into the "Models" component, "Views" component (I used JSP templates with the JSP Standard Tag Library), and the main "Controller" component.
The entire web application can then be hosted on any web server. I used tomcat to host the web application on localhost.

It even has the feature to generate CSV and PDF files for the bank statements of our "customers"!

### Stakeholders
Let's imagine there are 2 users:
1. A new account/new customer executive
2. A cashier/teller

And we must create separate accounts with different access permissions for them.

### Scope
1. For the employee that needs to create new customer accounts

    They must be able to:
   1. Create, Update, and Delete Customers
   2. Create and Delete Cash Accounts (Each customer can have multiple cash accounts)
   3. View Customer and Account Status

2. For the cashier/teller

    They must be able to:
   1. Manage deposits, withdrawals and transfers between cash accounts
   2. Get Customer and Account details
   3. Get Customer-Account Transactions (Generate a Bank Statement)

### The Application

1. For the new-customer executive
   1. The login screen
   
      To allow the employee to login.
      The username can be alphanumeric with maximum n characters.
      The password can also be alphanumeric with maximum n characters.

      The input validation was done using javascript on the JSP frontend to improve user experience since feedback is instant. 

      ![LoginPage](./Images/LoginPage.JPG)

   2. The "Create Customer" screen

      To allow the new-customer-account executive to create a new customer

      ![CreateCustomer](./Images/CreateCustomer.JPG)

      There is also input validation done here
   3. The "Update Customer" screen

      To allow account executive to update existing customer information
   
      ![UpdateCustomer](./Images/UpdateCustomer.JPG)

   4. The "Search Customer" screen

      Before the account executive can update anything, they should be able to query for existing customers
      
      ![SearchCustomer](./Images/CustomerSearch.JPG)

      There is also input validation done here.
   5. The "Delete Customer" screen

      To allow the account executive to delete any existing customer

      ![DeleteCustomer](./Images/DeleteCustomer.JPG)

   6. The "Delete Account" screen

      To allow the account executive to delete existing accounts.

      ![DeleteAccount](./Images/DeleteAccount.JPG)

   7. The "View Customer Status" screen

      ![CustomerStatus](./Images/CustomerStatus.JPG)

   8. The "View Account Status" screen

      ![AccountStatus](./Images/AccountStatus.JPG)

   
2. For the cashier/teller
   1. The "Deposit" screen

      ![DepositMoney](./Images/DepositMoney.JPG)
      
   2. The "Withdraw" screen

      ![WithdrawMoney](./Images/WithdrawMoney.JPG)

   3. The "Transfer" screen

      ![TransferMoney](./Images/TransferMoney.JPG)
   
   4. The "Get Statement" screen

      ![ViewStatement](./Images/ViewStatement.JPG)

      ![ViewStatement2](./Images/ViewStatement2.JPG)
