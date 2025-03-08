# Etapa 1: Construção da aplicação
FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /api

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

#Etapa 2: Execução da aplicação
FROM eclipse-temurin:23-jdk

WORKDIR /api

COPY --from=builder /api/target/*.jar api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api.jar"]