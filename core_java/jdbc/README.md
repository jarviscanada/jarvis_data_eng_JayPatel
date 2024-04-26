# Stock Quote Application in Java

## Introduction
The Java Stock Quote Application is a tool designed to enable users to search for, save, and manage stock information from a backend API into a PostgreSQL database. Users can utilize this saved data to execute purchase and sell orders, similar to a live stock exchange. The application is built using Java and its SQL libraries, with Maven used for building and packaging. It is dockerized and hosted on Docker Hub for easy deployment.

## Implementation
The Java Stock Quote Application is developed using Java 11. It features a `Quote` class to encapsulate stock information and a `Position` class to manage purchase positions. Stock information is fetched from the Alpha Vantage API to ensure accuracy, with `HttpRequest` used to retrieve data in JSON format. This JSON data is parsed and inserted into the database using the `QuoteDAO`. Multiple DAO files handle application logic, while service files preprocess data before interaction with DAOs. Controllers are responsible for saving, finding, and deleting stock and position data. Maven manages dependencies and packages the project, while Docker ensures portability.

## ER Diagram
![image](https://github.com/jarviscanada/jarvis_data_eng_JayPatel/assets/49347932/e37713f7-1cc5-415f-b60a-8153b578631c)


## Design Patterns
The application follows a service layer architecture consisting of Entity, DAO, and service layers, with a controller layer serving as the user interface.

- **Entity Layer**: Contains classes for `Quote` and `Position` with relevant getters, setters, and information.
  
- **DAO Layer**: Provides methods to access/manipulate data in the database, including CRUD operations for quotes and positions.
  
- **Service Layer**: Facilitates communication between user controllers and DAO layer, implementing methods for purchasing/selling stocks and saving stock information.
  
- **Controller Layer**: Allows user input and output, featuring a terminal interface for interacting with stock quotes and positions.

## Testing
Testing for the project includes unit testing with JUnit and integration testing with Mockito.

- **Unit Testing**: Covers every method in the position and quote DAO layers. Tests involve creating, deleting, and updating database values and verifying the expected changes.
  
- **Integration Testing**: Utilizes Mockito to mock stub and DAO objects, mocking database, method, and API calls. Mock injections are applied to service layer objects.
  
- **Manual Testing**: Involves using the controller class to input commands and inspect database changes against expected values. 

