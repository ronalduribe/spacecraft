# Use the official Maven image to create a build artifact.
# This is a multi-stage build. 
FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/spacecraft-0.0.1-SNAPSHOT.jar /usr/local/lib/spacecraft.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/spacecraft.jar"]
