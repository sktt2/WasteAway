# README #


### Development ###

For backend development (Spring Boot)

* run `./mvnw spring-boot:run` at current working directory (where `mvnw` wrapper file is found)
* make necessary code changes in `src/master/src/main/java/csd/week6`
* note that app runs via port 8080, with hot reloading enabled

For frontend development (React)

* open a separate IDE
* cd into `frontend` directory
* run `npm start`
* make necessary code changes in `src` folder (entry point is the App.js, components are in `component` directory)
* note that app runs via port 3000, with hot reloading enabled


### Deployment ###

* To have a single build of the Spring Boot app and the React app using Maven, run `./mvnw clean install` (note that the necessary configurations have been set up in the pom.xml)
* check `target` folder, one should be able to see `week6-0.0.1-SNAPSHOT.jar` 
* run `java -jar target\week6-0.0.1-SNAPSHOT.jar`
* launch app at `http://localhost:8080`