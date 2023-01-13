FROM maven:3-eclipse-temurin-17-alpine AS builder
COPY . /usr/local/src
WORKDIR /usr/local/src
RUN mvn clean install

FROM eclipse-temurin:17
WORKDIR /usr/local/src
COPY --from=builder /usr/local/src/target/topkino-0.0.1-SNAPSHOT.jar topkino-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/local/src/topkino-0.0.1-SNAPSHOT.jar"]