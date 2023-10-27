# URLShortener
An URL shortener is a website that reduces the length of your URL, in order to make the address something that's easier to remember and track.


# Installation 
This is a SpringBoot application developed with JDK11 and uses an in-memory h2 database  

The application provides a Swagger UI in order to perform API calls. the swagger UI is reacheable through localhost:8080/swagger-ui.html.  

After building the application, you can execute the jar using the following command: java -jar urlshortener-api-0.0.1-SNAPSHOT.jar  

Once the application is up and running, you can browse to [the swagger api ]( http://localhost:8080/swagger-ui.html )   

In the swagger API, there are 2 endpoints, one for shortening the URLS and the other for retrieving the original URL. 

The in-memory h2 database can be accessed through localhost:8080/h2-console. Username and password are set the same: sa  

