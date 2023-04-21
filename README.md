# Todos Application

This *application* is used to **manage** your *day-to-day* or even *long-term* **todos**!

<hr/>

## Requirements

The **stable** releases of the following *technologies* are used:

| Technology  | Version |
| ----------- | ------- |
| JDK         | 17+     |
| Spring Boot | 3.0+    |
| Lombok      | 1.18+   |
| MySQL       | 8.0+    |
| Maven       | 3.8+    |
| Thymeleaf   | 3.1+    |
| Bootstrap   | 4.6+    |
| jQuery      | 3.6+    |
| Docker      | 20+     |

P.S. For *production* purposes, only **Docker** is *sufficient*.

<hr/>

## Development

The *database* can be **monitored** as follows:

### Usage

**Run** the following *command* for Docker **Container**:

>docker run --detach --env MYSQL_ROOT_PASSWORD=dev-root --env MYSQL_USER=dev-user --env MYSQL_PASSWORD=dev-pass --env MYSQL_DATABASE=dev-todos --name dev-db --publish 3306:3306 mysql:8-oracle

**Use** the following *properties* for **Spring** in the "*application.properties*" file:

```
spring.datasource.url=jdbc:mysql://localhost:3306/dev-todos
spring.datasource.username=dev-user
spring.datasource.password=dev-pass
```

**Run** the "*TodosApplication*" class with this *command*:

>mvn spring-boot:run

Go to this *URL* to **use** the *application*:

>http://localhost:8080/todos

### Monitoring

**Connect** to the MySQL **DB** at the following *URL*:

>jdbc:mysql://localhost:3306/dev-todos

... with these **credentials**:

>dev-user:dev-pass

<hr/>

## Production

The *application* can be **deployed** as follows:


### Usage

**Run** the following *command* for Docker **Compose**:

>docker-compose up

Go to this *URL* to **use** the *application*:

>http://localhost:6969/todos

**Stop** the following *command* for Docker **Compose**:

>docker-compose down

### Monitoring

**Connect** to the MySQL **DB** at the following *URL*:

>jdbc:mysql://localhost:4242/todos-db

... with these **credentials**:

>root:p4$$w0rd

<hr/>

**Thank** you for *using* it!

<hr/>