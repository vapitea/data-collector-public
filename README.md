# data-collector

This project contains a Spring boot 2 and an Angular 11 application. The project can be built with Java 11 and Maven 3.6+.  
The Angular app is built and packaged using the [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin)

## Building

```bash
mvn clean package
```

## Running

```
mvn spring-boot:run
```
OR

```bash
java -jar target/data-collector-0.0.1-SNAPSHOT.jar
```

## Debug

H2 database console available at http://localhost:8080/h2/  
Angular app is running at http://localhost:8080  
Usable credentials:  
-Admin "Lewis Hamilton":"12345"  
-Operator "Valtteri Bottas":"password"  
-User "Max Verstappen":"qwerty"  

##Docs

[specifikacio.md](./documents/specifikacio/specifikacio.md)
