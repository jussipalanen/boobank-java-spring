# Build
FROM maven:3.8.5-openjdk-17 as build
COPY src /home/app/src
COPY pom.xml /home/app
# This runs the package command without the tests
# RUN mvn -f /home/app/pom.xml clean package -DskipTests
RUN mvn -f /home/app/pom.xml clean package

# Stage
FROM openjdk:17
COPY --from=build /home/app/target/release-0.0.1-SNAPSHOT.jar /usr/local/lib/release.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/release.jar"]