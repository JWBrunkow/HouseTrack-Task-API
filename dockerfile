FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /usr/src/app

COPY pom.xml .
COPY src/ src/

RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /usr/src/app

COPY --from=builder /usr/src/app/target/taskAPI-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","taskAPI-0.0.1-SNAPSHOT.jar"]
