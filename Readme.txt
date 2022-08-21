
Pension Management System

## The application has the following services:

1. Authorization Service
2. Pensioner Detail Service
3. Pensioner Detail Service
4. Pension Management Portal (Front-end in Angular)


Details below:-
------------------------------------------------------
## 1. Authorization Service
This service is responsible to provide login access to the application and provide security to it with the help of stateless authentication using JWT Tokens.

### This service provides two controller END-POINTS:

1. Open your spring boot application and run the service.
2. Open your browser and head to this URL - http://localhost:8081/swagger-ui.html#/ this will redirect you to Swagger UI where you can test the service.
3. Select the authorization controller header
4. **Login functionality**
* Select **login** POST method and click try it out
* Then enter these **correct** username and password credentials as follows:

```
{
  "username": "admin1",
  "password": "adminpass@1234"
}
```

* Then hit execute and you will see a JWT Token generated. Copy this token to be used in the next step.
* For these **incorrect** credentials:  

 ```
{
  "username": "admin123",
  "password": "wrongpassword"
}
 ``` 
 
**Response**

```
{
    "message": "Incorrect Username or Password",
    "timestamp": "2022-07-25T17:33:36.4292109",
    "fieldErrors": [
        "Incorrect Username or Password"
    ]
}
```

5. **Validation functionality**
* Select **validation** POST method and click try it out
* Then enter previously generated **valid** Token that you had copied into the Authorization header.
* Then hit execute and you would see `true` in the response body.
* If the token in **invalid** the application throws an appropriate error response related to either `Token expired`, `Token malformed` or `Token signature incorrect`.

---------------------------------------------------------------

## 2. Pensioner Detail Service
  
  **Description**
      
      This microservice is responsible for Provides information about the registered pensioner detail i.e., 
      Pensioner name, PAN, bank name, bank account number, bank type – private or public
    
   **Steps and Action**
   
      => This Microservice is to fetch the pensioner detail by the Aadhaar number.
      => Flat file(CSV file with pre-defined data) should be created as part of the Microservice. 
      => This file has to contain data for 20 Pensioners. This has to be read and loaded into List for 
         ALL the operations of the microservice.
      
   **Endpoint**
   
      url- http://localhost:8083/api/pensioner-detail/pensionerDetailByAadhaar/123456789012 
      This endpoint accept the user request and provides the Pensioner details. Access this using the POSTMAN client
      
      Input - Aadhaar Number => 123456789012
      
**Valid Response**
      
```
{
  "name": "Anand",
  "dateOfBirth": "1956-09-12",
  "pan": "BHMER12436",
  "salary": 27000,
  "allowance": 10000,
  "pensionType": "self",
  "bank": {
    "bankName": "ICICI",
    "accountNumber": 12345678,
    "bankType": "private"
  }
}
```
**Invalid Response**
       
```
{
    "message": "Aadhaar Number Not Found",
    "timestamp": "2022-07-25T17:34:27.9908205",
    "fieldErrors": [
        "Aadhaar Number Not Found"
    ]
}
```

----------------------------------------------------------------        
     
## 3. Process Pension Service
* It takes in the pensioner detail like the name, aadhaar number, pan detail, self or family or both type of pension
* Verifies if the pensioner detail is accurate by getting the data from PensionerDetail Microservice or not. 
* If not, validation message `“Invalid pensioner detail provided, please provide valid detail.”`
* If valid, then pension calculation is done and the pension detail is returned to the Web application to be displayed on the UI.

### This service provides two controller end-points:

1. Open your spring boot application and run the service.
2. Open your browser and head to this URL - http://localhost:8082/swagger-ui.html#/ this will redirect you to Swagger UI where you can test the service.
3. This endpoint only accept authenticated request so make sure that there is is valid token present in "Authentication" header. Use AUTH-SERVICE to generate tokens

4. **Get Pension Details functionality**
Select **/pensionerInput** POST method and click try it out
**Valid Input**

```
{
  "aadhaarNumber": "123456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Anand",
  "pan": "BHMER12436",
  "pensionType": "self"
}
```

**Response for valid input**

```
{
  "name": "Anand",
  "dateOfBirth": "12/09/1956",
  "pan": "BHMER12436",
  "pensionType": "self",
  "pensionAmount": 31600
}
```

**Invalid Input**

```
{
  "aadhaarNumber": "123456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Anand",
  "pan": "BHMER12436",
  "pensionType": "family"
}
```

**Response for invalid input**

```
{
    "message": "Details entered are incorrect",
    "timestamp": "2022-07-25T17:35:56.8632553",
    "fieldErrors": [
        "Details entered are incorrect"
    ]
}
```

**Invalid Input - wrong Aadhaar number**

```
{
  "aadhaarNumber": "223456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Anand",
  "pan": "BHMER12436",
  "pensionType": "family"
}
```

**Response for invalid input**

```
{
    "message": "Aadhaar Number Not Found",
    "timestamp": "2022-07-25T17:36:27.388088",
    "fieldErrors": [
        "Aadhaar Number Not Found"
    ]
}
```

--------------------------------------------------------------------

## 4. Pension Management Portal

This is the front end made using Angular, open VS code and enter `ng serve` in the terminal to run the application at `http:\\localhost:4200`
---------------------------------------------------------------------

Submitted by- Kumar Rounak 
              (97rounak@gmail.com)