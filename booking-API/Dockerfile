FROM openjdk:11
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} booking-api.jar
EXPOSE 8080
COPY . .
ENTRYPOINT ["java","-jar","/booking-api.jar"]