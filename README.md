# counter-service
In the interest of time the application is designed with simple get and post logic. It demonstrates the functionality requested in the problem statement. 
It is implemented with following features: 
* It exposes two endpoints as /top/{int} and /search using Spring Boot Rest APIs. 
* It is built using Java 8.
* Aspect is used to log method start and end loggers for Rest Controller Methods to demonstrate performance logging . 
* It has Unit Testing, Integration Testing to cover all the Success and Error Test scenarios.
* I have taken the assumption that the Static text will not be changed at the runtime.
* Repository pattern has been implemented for extensibility purpose. In Future, If we have to get the Text from the downstream System or datasource. We can can implement relevant repository accordingly 



## Software & Libraries

* Spring Boot,Spring Security, Java 8, lombok, Git, Maven, Eclipse(Intellij can be used as well). 

    
    
## Plugins

* maven compiler plugin to generate implementation code for mapstruct interface.  

## API Documentation
* Open API has been used for API documentation. Which can be found after Application startup at 
  * Json: http://localhost:8180/v3/api-docs/
  * Yaml: http://localhost:8180/api-docs.yaml
  * UI  : http://localhost:8180/swagger-ui.html 

## Building and Deploying Application

* Checkout the project from this location : https://github.com/KhushvinderSingh10/counter-service
* Application can be built by using an IDE or by using maven command : mvn clean install.
* Run the application (using the ProjectApplication class) as a spring boot application from the IDE.
* The application on startup will read the paragraph paragraph.text from classpath and prepare handy object for later use   
* Once the application is up it can be tested using the postman collection scripts.

## Testing the application -
* Get Top Words 
    * URL - http://localhost:8180/counter-api/top/5
    * Method - GET
    * Request Body : blank
    * Response - 
        CSV file
 
* Search Words 
    * Endpoint - http://localhost:8180/counter-api/search
    * Method - POST
    * Request Body -  
        {
    "searchText": [
        "Duis",
        "Sed",
        "Donec",
        "Augue",
        "Pellentesque",
        "123"
    ]
}
    * Success Response - 
			Status - 200 OK 
        	{
    "counts": [
        {
            "123": 0
        },
        {
            "Sed": 16
        },
        {
            "Donec": 8
        },
        {
            "Augue": 7
        },
        {
            "Pellentesque": 6
        },
        {
            "Duis": 11
        }
    ]
}
         
         * Error response 
          {
				"errorCode": "SOME_ERROR_CODE",
				"errorMessage": "SOME_ERROR_MESSAGE",
				"errors": null
		  }
		  
		  

## Postman Collection.

(Post collection is in the resources folder (CounterAPI.postman_collection.json). If any problems are faced in using it then import the collection using the JSON attached with the email.) 
