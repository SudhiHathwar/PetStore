# Basic Spring Boot Rest assured framework for automating backend services


**This test suite uses the factory method design pattern to automate web services.**

## Test cases proposed for Pet store service

I have taken consideration the below PET service for automation and I below are the use case I would propose for automation

```
/ GET - get pet by Pet ID
/ POSt - create a new Pet
/ PUT - update existing pet data
/ Delete - delete existing pet data
```
* GET pet endpoint with multiple sets of id's
* Get pet endpoint validation cases for 400 and 404 
* Create pet with valid data sets and verify whether GET returns the same set of data as created
* Create pet with invalid data sets and assert validations
* Update existing pet data which was created above and verify with GET call if the update was successful
* Delete newly created pet data and make a GET call and check if the data still exists


## Approach

I have used testng, rest assured library along with spring boot to automate the web services. 
Coming to the structure I have used factory method design pattern to have more clean and readable code.

#### Why Rest Assured?
It removes the need for writing a lot of boilerplate code required to set up an HTTP connection, send a request and 
receive and parse a response. It supports a Given/When/Then test notation, which instantly makes your tests human 
readable.

####Why Sprint boot?

It provides a flexible way to handle configurations and dependency injection


## Getting Started

1. Follow the steps in the **[readme](https://github.com/swagger-api/swagger-petstore) to setup local environment and 
run the services

## Running Tests Locally

Go into the project directory in the terminal and execute ```mvn clean test``` command to run the tests

## Report generation

I have used allure reporting as it gives more flexibility to log additional info to the reports along with network 
transaction details

To generate allure report. Please execute ```mvn allure:service```, this will open the report in default browser setup 
in the system
