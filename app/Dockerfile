# Etapa 1: Construção da aplicação
FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

#Etapa 2: Execução da aplicação
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]