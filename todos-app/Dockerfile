
# Cache the Dependencies

FROM maven:3.9.1-eclipse-temurin-17-alpine AS builder

WORKDIR /todos-app
COPY pom.xml .

RUN mvn dependency:go-offline

# Package the Application

COPY src/ ./src/
RUN mvn package -DskipTests

# Create a Slim Image

FROM eclipse-temurin:17-jre-alpine AS runner

LABEL version="1.0.0"
LABEL author="Ph4nToM"
LABEL maintainer="subhatav@gmail.com"

ENV SPRING_PROFILES_ACTIVE=production
COPY --from=builder /todos-app/target/todos-app-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
