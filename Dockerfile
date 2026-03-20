FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

# CHANGED: Using eclipse-temurin instead of the deprecated openjdk image
# OPTIMIZED: Using 'jre' (Java Runtime Environment) instead of 'jdk' to make the final image smaller
FROM eclipse-temurin:21-jre AS runner

WORKDIR /app

COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]