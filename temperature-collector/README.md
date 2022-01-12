# Temperature API

This project is an assignment project for Qardio.

#### Goal
Create an API to be used by a client that is connected to a temperature sensor.

![See Post scenario](https://i.ibb.co/j8rYW01/Novo-Projeto.png)

![See Get scenario](https://i.ibb.co/X5LbmHf/Novo-Projeto-2.png)

#### Technologies
- It was developed in Java

- It is a Rest API.

- Spring MVC

- Spring Boot

- Docker

- Docker compose

- Postgres

- Swagger

#### Build and run
- First run `mvn clean install` in project folder
- Build docker images in project folder, run `docker-compose -f docker-compose.yaml build`
- Now, start the containers using docker-compose `docker-compose -f docker-compose.yaml up -d`

- The api is running on port `8080`
- Endpoints are located in http://localhost:8080/swagger-ui/
- Postgres - GUI is running on port `5432`



