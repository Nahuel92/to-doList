[![Build Status](https://travis-ci.org/Nahuel92/to-doList.svg?branch=master)](https://travis-ci.org/Nahuel92/to-doList) [![codecov.io Code Coverage](https://img.shields.io/codecov/c/github/Nahuel92/to-doList.svg?maxAge=2592000)](https://codecov.io/github/Nahuel92/to-doList?branch=master) [![GitHub issues](https://img.shields.io/github/issues/Naereen/StrapDown.js.svg)](https://GitHub.com/Nahuel92/to-doList/issues/) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) [![Maintainability](https://api.codeclimate.com/v1/badges/09067e6fa8e8f1160d02/maintainability)](https://codeclimate.com/github/Nahuel92/to-doList/maintainability) [![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Nahuel92/to-doList/graphs/commit-activity) [![Open Source Love png1](https://badges.frapsoft.com/os/v1/open-source.png?v=103)](https://github.com/ellerbrock/open-source-badges/) [![Known Vulnerabilities](https://snyk.io/test/github/Nahuel92/to-doList.git/badge.svg)](https://snyk.io/test/github/Nahuel92/to-doList) 

# To-do List Project (WIP)

This is a hobby project which consists of an API REST Spring Boot based project.

I made this project firstly for training myself on new Java features like lambdas as well as for NoSQL database systems and Docker too.

In this case, I chose Cassandra DB for storing data.

------

## Table of Contents

- [To-do List Project (WIP)](#to-do-list-project-wip)
  - [Project Status](#project-status)
  - [Running the application](#running-the-application)
  - [Requisites (only for Docker deployment)](#requisites-only-for-docker-deployment)
  - [Requisites (only for local deployment)](#requisites-only-for-local-deployment)
  - [Requisites](#requisites)
  - [API Operations](#api-operations)
  - [Examples](#examples)
    - [Save item](#save-item)
      - [Success response](#success-response)
      - [Error response](#error-response)
    - [Save an items collection](#save-an-items-collection)
      - [Success response](#success-response-1)
      - [Error response](#error-response-1)
      - [Error response](#error-response-2)
    - [Delete item](#delete-item)
      - [Success response](#success-response-2)
      - [Error response](#error-response-3)
      - [Error response](#error-response-4)
    - [Delete all items](#delete-all-items)
      - [Success response](#success-response-3)
    - [Retrieve all items](#retrieve-all-items)
      - [Success response](#success-response-4)
    - [Update item](#update-item)
      - [Success response](#success-response-5)
      - [Error response](#error-response-5)
      - [Error response](#error-response-6)
    - [Swagger](#swagger)
  - [Technologies](#technologies)
  - [Tools](#tools)
  - [License](#license)

-----

## Project Status

- [x] App Dockerized.
- [x] Aspect for logging public controller methods.
- [ ] Authorization.
- [x] Caching with Redis (some operations only).
- [x] CRUD operations for a to-do list.
- [x] Exception handling with controller advice.
- [ ] Jenkins basic pipeline.
- [x] Kafka integration for massive creation operations.
- [x] Request data validation.
- [x] Spring Actuator (default configuration).
- [x] Spring HATEOAS.
- [x] Spring WebFlux.
- [x] Support for Cassandra DB.
- [x] Swagger for API documentation.
- [x] Travis basic pipeline.
- [x] Unit tests.

-----

## Running the application

This app is dockerized. This mean that the only things you have to do is clone this repo and run the following command on the project root to create the Docker image of the project):

```bash
mvn clean package dockerfile:build
```

 The mvnw wrapper is available if you don't want to install Maven:

```bash
./mvnw clean package dockerfile:build
```

After that, you can have all up and running by executing the following command:

```bash
docker-compose up
```

You can run this app locally too, but you have to manually configure all the dependencies described on the [Requisites (only for local deployment)](#requisites-only-for-local-deployment) section.

----

## Requisites (only for Docker deployment)

The only requisite is have installed Docker and Docker-compose. Maven is an optional requisite because you can use the provided Maven wrapper instead.

----

## Requisites (only for local deployment)

You must have Redis, Cassandra DB and a Kafka instance properly configured.
Please, configure the project's properties according to your configuration modifying the`application.yaml`file.

The following are the properties you can configure for this application:

```yaml
spring:
  data:
    cassandra:
      contact-points: localhost
      keyspace-name: todoList
      schema-action: CREATE_IF_NOT_EXISTS

  kafka:
    consumer:
      group-id: group-id
    template:
      default-topic: todoListUsers

  redis:
    host: localhost

  cache:
    redis:
      time-to-live: 600000

```

-----

## API Operations

The following is a list of supported operations:
- Save a to-do item:`POST /v1/todo-list/items`
- Save a collection of to-do item:`POST /v1/todo-list/batch/items`
- Delete a to-do item:`DELETE /v1/todo-list/items/{id}`
- Delete all to-do items:`DELETE /v1/todo-list/items`
- Retrieve a to-do item:`GET /v1/todo-list/items/{id}`
- Retrieve all to-do items:`GET /v1/todo-list/items`
- Update a to-do item:`PATCH /v1/todo-list/items`

----

## Examples

### Save item

Used to persist a to-do item on database.

**URL:** `/v1/todo-list/items`

**Method:** `POST`

**Auth required:** No.

**Data constraints:**

```json
{
	"description": "[Valid text, not null or empty]"
}
```

**Data example:**

```json
{
	"description": "To do item example"
}
```

#### Success response

**Code:** `201 CREATED`

**Content:** 

```json
{
  "id": "[Valid UUID value]",
	"description": "[Valid text]",
  "status": "Created",
  "createdDateTime": "[yyyy mm dd HH:MM:SS]"
}
```

**Data example:**

```json
{
	"id": "a056fb54-317e-4982-bd83-ccb0b8b97d74",
  "description": "To do item example",
  "status": "Created",
  "createdDateTime": "2019 11 04 16:50:00"
}
```

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If 'description' is null or empty.

**Content:**

```json
{
    "errorMessages": [
        "Description can not be null or empty"
    ]
}
```

---

### Save an items collection

Used to persist a to-do items collection on database.

**URL:** `/v1/todo-list/batch/items`

**Method:** `POST`

**Auth required:** No.

**Data constraints:**

```json
[
  {
    "description": "[Valid text, not null or empty]"
  },
  {
    "description": "[Valid text, not null or empty]"
  }
]
```

**Data example:**

```json
[
  {
		"description": "To do item example"
	},
  {
		"description": "To do item example 2"
	}
]
```

#### Success response

**Code:** `201 CREATED`

**Content:**

```json
[
  {
    "id": "[Valid UUID value]",
		"description": "[Valid text]",
  	"status": "Created",
	  "createdDateTime": "[yyyy mm dd HH:MM:SS]"
	},
  {
    "id": "[Valid UUID value]",
		"description": "[Valid text]",
  	"status": "Created",
	  "createdDateTime": "[yyyy mm dd HH:MM:SS]"
	}
]
```

**Data example:**

```json
[
  {
    "id": "a056fb54-317e-4982-bd83-ccb0b8b97d74",
    "description": "To do item example",
    "status": "Created",
    "createdDateTime": "2019 11 04 16:50:00"
  },
  {
    "id": "a056fb54-317e-4982-bd83-ccb0b8b97d74",
    "description": "To do item example 2",
    "status": "Created",
    "createdDateTime": "2019 11 04 16:50:01"
  }
]
```

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If request body is missing.

**Content:** 

```json
{
    "errorMessages": [
        "Empty request"
    ]
}
```

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If 'id' or 'description' are null or empty.

**Content:** 

```json
{
    "errorMessages": {
        "0": [
            "Description can not be null or empty",
            "Id can not be null"
        ]
    }
}
```

---

### Delete item

Used to delete a specific to-do item on database.

**URL:** `/v1/todo-list/items/{id}`

**Method:** `DELETE`

**Auth required:** No.

**Data constraints:**

`{id} must be a valid UUID value.`

**Data example:**

```
/todoList/items/a056fb54-317e-4982-bd83-ccb0b8b97d74
```

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** Sending a non UUID id param.

**Content:**

```json
{
    "errorMessages": [
        "Argument type mismatch. Please check data types and try again."
    ]
}
```

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** Deleting an inexistent item.

**Content:**

```json
{
    "errorMessages": [
        "Entity not found."
    ]
}
```

---

### Delete all items

Used to delete all to-do items saved on database.

**URL:** `/v1/todo-list/items`

**Method:** `DELETE`

**Auth required:** No.

**Data constraints:** No.

**Data example:** No.

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

---

### Retrieve all items

Used to retrieve all to-do items on database.

**URL:** `/v1/todo-list/items`

**Method:** `GET`

**Auth required:** No.

**Data constraints:** No.

#### Success response

**Code:** `200 OK`

**Content:** A collection of items.

```json
[
  {
    "id": "a056fb54-317e-4982-bd83-ccb0b8b97d74",
    "description": "To do item example",
    "createdDatetime": "2019 26 25 11:07:03",
    "status": "Created"
  },
  {
    "id": "a056fb54-317e-4982-bd83-ccb0b8b97d73",
    "description": "To do item example 2",
    "createdDatetime": "2019 57 25 11:07:49",
    "status": "In Progress"
  }
]
```

---

### Update item

Used to update a to-do item saved on database.

**URL:** `/v1/todo-list/items`

**Method:** `PATCH`

**Auth required:** No.

**Data constraints:**

```json
{
	"id": "[Valid UUID value]",
	"description": "[Valid text, not null or empty]",
  "status": "[Created, In Progress, Done]"
}
```

**Data example:**

```json
{
	"id": "a056fb54-317e-4982-bd83-ccb0b8b97d73",
	"description": "New description",
  "status": "Done"
}
```

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If 'id', 'description' or 'status' are null, empty or invalid.

**Content:**

```json
{
  "errorMessages": [
    "Description can not be null or empty",
    "Id can not be null",
    "Status can not be null"
  ]
}
```

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** Updating an inexistent item.

**Content:**

```json
{
    "errorMessages": [
        "Entity not found."
    ]
}
```

-----

## Swagger

[Here](http://localhost:8080/swagger-ui.html) is the Swagger-generated API documentation. The project has to be running to access the page.

-----

## Technologies

This project uses the following technologies:
- [Apache Cassandra DB](http://cassandra.apache.org/).
- [Apache Kafka](https://kafka.apache.org/).
- [Docker](https://www.docker.com/).
- [Java](https://openjdk.java.net/).
- [Maven](https://maven.apache.org/).
- [Redis](https://redis.io/).
- [Spring Boot](https://spring.io/projects/spring-boot).
- [Swagger](https://swagger.io/).

-----

## Tools

This project was made using the following tools:

- [Code Climate](https://codeclimate.com).
- [Codecov](https://codecov.io).
- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4).
- [gh-md-toc](https://github.com/ekalinin/github-markdown-toc)
- [Gitignore.io](https://www.gitignore.io).
- [IntelliJ IDEA](https://www.jetbrains.com/idea).
- [Open Source Badges](https://github.com/ellerbrock/open-source-badges).
- [Postman](https://www.getpostman.com/).
- [Snyk io](https://snyk.io).
- [Travis CI](https://travis-ci.org).
- [Typora](https://typora.io).

-----

## License

[GNU GPL 3.0](./LICENSE)
