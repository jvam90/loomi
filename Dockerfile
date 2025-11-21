# Multi-stage Dockerfile for building and running the Loomi Spring Boot app
# Stage 1: build the application with Maven
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /workspace

# Copy pom and download dependencies first to take advantage of Docker layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn -B -ntp dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -B -ntp -DskipTests package

# Stage 2: runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the packaged jar from the builder stage
COPY --from=builder /workspace/target/loomi-0.0.1-SNAPSHOT.jar ./app.jar

# Expose default Spring Boot port
EXPOSE 8080

# JVM options can be overridden at runtime with the JAVA_OPTS env var
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
