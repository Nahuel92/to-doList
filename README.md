# To-do List Project (WIP)

This is a hobby project which consists of an API REST Spring Boot based project.
I made this project firstly for training myself on new Java features like lambdas as well as for NoSQL database systems and Docker too.
In this case, I chose Cassandra DB for storing data.

## Features Status

- [x] CRUD operations for a to-do list project.
- [x] Support for Cassandra.
- [x] Aspect for logging public controller methods.
- [x] Controller advice for handling controller exceptions.
- [ ] Request collection validation.
- [ ] Unit tests.
- [ ] Authorization.
- [ ] Support for Redis.
- [ ] Support for MongoDB.
- [ ] Support for MariaDB.

## API Operations

The following is a list of supported operations:
- Save a to-do item:`POST /todoList/items`
- Save a collection of to-do item:`POST /todoList/items/bulk`
- Delete a to-do item:`DELETE /todoList/items/{id}`
- Delete all to-do items:`DELETE /todoList/items`
- Retrieve all to-do items:`GET /todoList/items`
- Retrieve all to-do items:`GET /todoList/items`
- Update a to-do item:`PATCH /todoList/items`

## Examples

### Save item

Used to persist a to-do item on database.

**URL:**  `/todoList/items`

**Method:**  `POST`

**Auth required:** NO

**Data constraints:**

```json
{
	"id": "[Valid number value, different for each item (WIP to avoid sending this parameter)]",
	"description": "[Valid text, not null or empty]"
}
```

**Data example (WIP)**:

```json
{
	"id": 1,
	"description": "To do item example"
}
```



#### Success response

**Code:** `201 created.`

**Content:** Empty as the following example.

```json

```

#### Error response

**Code:** `400 bad request.`

**Condition:** if 'id' or 'description' are null or empty.

**Content:**

```json
[
    "Id can not be null",
    "Description can not be null or empty"
]
```

(WIP)

## Technologies

This project uses the following technologies:
- Java.
- Spring Boot.
- Maven.
- Cassandra DB.
- Docker.

## Tools

This project was made using the following tools:

- [IntelliJ IDEA](https://www.jetbrains.com/idea).
- [Gitignore.io](https://www.gitignore.io).
- [Typora](https://typora.io).
- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4).
- [Postman](https://www.getpostman.com/).

## License

[GNU GPL 3.0](./LICENSE)