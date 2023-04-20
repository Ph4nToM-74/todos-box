FROM maven:3.9.1-eclipse-temurin-17-alpine

LABEL author="Ph4nToM"
LABEL maintainer="subhatav@gmail.com"

WORKDIR /todos-app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run