#Use official jdk slim version from docker hub
FROM openjdk:17

#set the work directory inside the container
WORKDIR /app

#Copy the compiled jar into the docker container folder app
COPY ./target/user-service-1.0.0.jar /app

#Expose the app to external on port 8080
EXPOSE 8080

#Command to run the application
CMD ["java", "-jar", "user-service-1.0.0.jar"]