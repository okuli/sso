# Spring Boot SSO Demo

This repo shows how one can integrate using Google & Okta SSO.

## Tech Stack, Framework & Tools Used

* Maven
* Thymeleaf
* Java 11
* Spring Boot
* Okta
* Google SSO  
* Azure AD/ Microsoft SSO

# About The Project

This repo exposes certain endpoints which have been secured using OIDC tokens 
from Okta

# Running The Project

## IDE Approach
* Ensure you've an instance of PostgreSQL running.
* Create a PostgreSQL database with the name `users_db`  
* Open the project using your IDE of choice. e.g IntelliJIDEA
* Run the application. This will wire up the application on port **9080**


## Docker Approach
* From project root folder, run the command `mvn clean install`
* Create a PostgreSQL database with the name `users_db`
* From project root folder, run the command `docker-compose up`
* The application will wire up on port **9080**

## Okta Test Credentials

* **Username**: sidritleka@test.com
* **Password**: Password1*

## Microsoft 365 Test Credentials

* **Username**: sidrit@digispacesystems.onmicrosoft.com
* **Password**: JcV!A0dDqjZg


### Google sign, you can test using your preferred gmail account


**Setting Up Azure AD** :- `https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory`

