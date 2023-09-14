# TaskAPI for Housetrack Application

A Java Spring Boot API for managing task information from multiple frontend components of a shared chore tracking application.

## Description

A Java API using the Spring Boot framework, Maven and JPA connected to a PostgreSQL database, in order to service JSON transactions to frontend components of a multi-user chore tracking application. 
Frontend components will initially consist of a React.js web application, and an Andriod application written in Kotlin. 


## Getting Started

### Dependencies

* Apache Maven 3.5 or above
* Java 8 or above


### Installing & Executing

* After downloading the code from this repo, navigate to the root directory of the project and run ````mvn package```` in the terminal.
* This will use localhost:8080/api/v1/task as an endpoint, and can be accessed there on a web browser.

### Testing Functionality

* If your IDE supports automatic HTTP request creation, GET, POST, DELETE and PUT requests can be created and ran against the API.
* If not, then [Postman](https://www.postman.com/) can be used to perform the same functions.
* The current release supports POST requests in the following form: <br>

````
Content-Type: application/json

{

  "taskName": "your task here",
  "taskDesc": "description of your task",
  "TaskId": 42069,
  "dueTime": "2024-06-01T19:00:00",
  "completedTime": "2023-04-29T13:00:00",
  "userId" : 1
  

}
````
<br>
GET requests are made via: <br><strong>http://localhost:8080/api/v1/task</strong> <br>
This functionality will be expanded in future releases for more specified GET requests <br><br>
DELETE requests are made via: <br>
<strong>http://localhost:8080/api/v1/task/1</strong> <br><br>
PUT requests are made via: <br>
<strong>http://localhost:8080/api/v1/task/1?taskName=YOUR TASK NAME HERE&taskDesc= YOUR TASK DESC HERE</strong>
<br><br>


## Known Issues and Future Updates
* PUT requests interfacing with date and time fields are currently non-functional - to update these fields using PUT causes errors. Time fields to be refactored.
* More granular GET methods to feature in later updates
* Login component to be added, assigning each user a custom userId which is passed as part of the JSON.
* Spring Security features to be implemented.
* Application to be containerised using Docker and deployed to remote server for persistence.


## Authors
Contributor - [JWBrunkow](https://www.linkedin.com/in/jwbrunkow)

## Version History
* 1
    * Initial Release

## Acknowledgments
* Inspired by [Amigoscode's](https://www.youtube.com/@amigoscode) Tutorials
