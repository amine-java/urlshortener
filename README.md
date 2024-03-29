# URLShortener
An URL shortener is a website that reduces the length of your URL, in order to make the address something that's easier to remember and track.


# Application  
This is a SpringBoot application developed with JDK11 and uses an in-memory h2 database. There's a front end with Angular that was added recently.  

The application provides a Swagger UI in order to perform API calls. the swagger UI is reacheable through localhost:8080/swagger-ui.html.  

<i><b>The back-end should be launched on port 8080, and the front-end should be launched on port 4200</b></i>

## Build and install
You can build the app by executing the ```mvn clean install```, and then execute the api jar using the following command: ```java -jar urlshortener-api-0.0.1.jar```  

Once the application is up and running, you can browse to [the swagger api ]( http://localhost:8080/swagger-ui.html )   

In the swagger API, there are 2 endpoints, one for shortening the URLS and the other for retrieving the original URL. 

The in-memory h2 database can be accessed through localhost:8080/h2-console. Username and password are set the same: sa  and the URL is: jdbc:h2:mem:testdb

To launch the front end, execute ```npm install``` and then launch ```ng serve```

## Docker  

You can launch the app using Docker. 

### For the back-end: 
Build <br/>
``` docker build -t url-shortener-backend-app . ```
<br/> Run <br/>
``` docker run -p 8080:8080 url-shortener-backend-app ```

### For the front-end: 
Build <br/>
``` docker build -t url-shortener-app . ```
<br/> Run <br/>
``` docker run -p 4200:80 url-shortener-app ```
