# Daily Expenses Sharing Application

This is a backend service built using **Java**, **Spring Boot**, and **Maven** to manage and share daily expenses. The application allows users to add expenses, split them by exact amounts, percentages, or equally, and generate downloadable balance sheets.

## Features

- Add Users
- Add new expenses
- Split expenses by exact amounts, percentages, or equally
- Manage user details and validate inputs
- Generate downloadable balance sheets

## Technologies Used

- **Backend:** Java, Spring Boot, Maven
- **Frontend:** Thymeleaf, HTML, CSS, Bootstrap
- **Database:** H2
- **Tools:** Postman, Git

## Dependencies

The following dependencies are required for the project. Add these to your `pom.xml` file if they are not already present:

### 1. Spring Boot Starter Web
For building web and RESTful applications using Spring MVC. Uses Tomcat as the default embedded container.
### 2. Spring Boot Starter Data JPA
For Spring Data JPA with Hibernate.
### 3. H2 Connector
To connect your application to a H2 database.
### 4. Spring Boot Starter Test
For testing the application.
### 5. Spring Boot DevTools
For enabling live reload during development.

## Setup and Installation Instructions
### Prerequisites
- **Java 17**
- **Spring Boot 3.3.4**
- **Maven**
- **H2**
- **IDE( IntelliJ IDEA )**

## Steps to Run the Application
### 1.Clone the Repository:
-git clone https://github.com/your-username/Daily-ExpenseSharing-Application.git
-cd daily-expenses-sharing-app

### 2. Set Up H2 Database:
-Create a database for the application:
CREATE DATABASE and Update the src/main/resources/application.properties file with your MySQL credentials:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=<your-username> (sa)
spring.datasource.password=<your-password> (password)
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

### 3. Build the Project:
mvn clean install

### 4. Run the Application:
mvn spring-boot:run

### 5. Access the Application:
Once the application is running, open a browser and go to:
http://localhost:8080

## API Endpoints

### 1. Home Page API:
http://localhost:8080/
### 2. Add Users Detail and Expenses:
-GET http://localhost:8080/addExpense 
-POST http://localhost:8080/saveExpense
### 3. Edit Users Detail and Expenses:
-GET http://localhost:8080/editExpense 
-POST http://localhost:8080/updateExpense
### 4. Delete Users Detail and Expenses:
http://localhost:8080/deleteExpense 
### 5. Split Expenses:
#### - EQUAL SPLIT 
http://localhost:8080/equalSplit
#### - EXACT SPLIT 
http://localhost:8080/exactSplit
#### - PERCENTAGE SPLIT 
http://localhost:8080/percentageSplit
### 6. Get Balance Sheet 
http://localhost:8080/balanceSheet
### 7. Download Balance Sheet 
http://localhost:8080/downloadBalanceSheet
