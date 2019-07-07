# banking-application
Accounts and Transactions Management Project

## Project Description:
A SpringBoot REST API Accounts and Transactions management application with in memory h2 database.

## Commands to execute the application:
Prerequisities:-  mvn 3.5.4 and java8  
  mvn clean package  
  mvn spring-boot:run  

## API Catalogue 
This application exposes the following APIs:
  - GET /v1/accounts/{accountNumber}  
      Retrieves all account details of the given accountNumber   
  ---
  - GET /v1/accounts/{accountNumber}/transactions   
      Lists all transactions against the account number either as a from or to account.  
  ---
  - POST /v1/accounts/{accountNumber}/transactions  
      POST a transaction from one account to another account  
  ---
## Key info:
  - data.sql contains table structure initialised on h2 DB and some predefined values.  
  - src/main/test/BankingApplicationTests.java contains end to end integration tests.  
  - Before running the junit, comment the insert queries on data.sql.
  - application.properties within src/main/resources contain H2 DB connection details.
  - More focus on integration test than to individual junits. As per requirement doc, havent spent much efforts on junits. Can provide more detailed junits if required.

## Examples
### Example endpoint and request to post transaction from one account to another
POST http://localhost:8080/v1/accounts/123456/transactions

#### Request
```json
{
    "toAccountNumber": 123789,
    "amountToBeTransferred": 500,
    "transactionType": "WIRE",
    "transactionReference": "Testing"
}
```
### Example endpoint and response to retrieve account details
GET http://localhost:8080/v1/accounts/123456

#### Response
```json
{
    "account": {
        "id": 1,
        "accountNumber": 123456,
        "accountSortCode": 110101,
        "accountName": "MR Test User 1",
        "accountDisplayName": "TU1",
        "accountType": "CURRENT",
        "accountSociety": "GB",
        "accountBalance": 900
    }
}
```
### Example endpoint and response to list all transactions
GET http://localhost:8080/v1/accounts/123789/transactions 

#### Response
```json
{
    "listTransactions": [
        {
            "trans_id": 1,
            "transFromAccNumber": 123456,
            "transToAccNumber": 123789,
            "transAmount": 500,
            "transStatus": "S",
            "transType": "WIRE",
            "transReference": "Testing"
        }
    ]
}
```
