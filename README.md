# To-do List Project

This is a hobby project which consists of an API REST Spring Boot project based.
I made this project firstly for training myself on new Java features like lambdas as well as for NoSQL database systems and Docker too.
In this case, I chose Cassandra DB for storing data.

## Features Status

- [x] CRUD operations for a to-do list project.
- [x] Cassandra integration for persistence.
- [x] Aspect for logging public controller methods.
- [x] Controller advice.
- [ ] Unit tests.

## API Operations

The following is a list of supported operations:
- Save a to-do item:`POST /todoList/items.`
- Save a collection of to-do item:`POST /todoList/items/bulk.`
- Delete a to-do item:`DELETE /todoList/items/{id.}`
- Delete all to-do items:`DELETE /todoList/items.`
- Retrieve all to-do items:`GET /todoList/items.`
- Retrieve all to-do items:`GET /todoList/items.`
- Update a to-do item:`PATCH /todoList/items.`

## Examples

— To do.

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