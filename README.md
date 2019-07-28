# To-do List Project (WIP)

This is a hobby project which consists of an API REST Spring Boot based project.

I made this project firstly for training myself on new Java features like lambdas as well as for NoSQL database systems and Docker too.

In this case, I chose Cassandra DB for storing data.

## Project Status

- [ ] Authorization.
- [x] Aspect for logging public controller methods.
- [x] Caching with Redis (some operations only).
- [x] CRUD operations for a to-do list project.
- [x] Request data validation.
- [x] Exception handling with controller advice.
- [ ] Kafka integration for massive creation operations.
- [x] Support for Cassandra.
- [ ] Swagger for API documentation.
- [ ] Unit tests.

## API Operations

The following is a list of supported operations:
- Save a to-do item:`POST /todo-list/item`
- Save a collection of to-do item:`POST /todo-list/items`
- Delete a to-do item:`DELETE /todo-list/item/{id}`
- Delete all to-do items:`DELETE /todo-list/items`
- Retrieve all to-do items:`GET /todo-list/items`
- Update a to-do item:`PATCH /todo-list/item`

## Examples

### Save item

Used to persist a to-do item on database.

**URL:** `/todo-list/item`

**Method:** `POST`

**Auth required:** No.

**Data constraints:**

```json
{
	"id": "[Valid number value, different for each item (WIP to avoid sending this parameter)]",
	"description": "[Valid text, not null or empty]"
}
```

**Data example:**

```json
{
	"id": 1,
	"description": "To do item example"
}
```

#### Success response

**Code:** `201 CREATED`

**Content:** No.

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If 'id' or 'description' are null or empty.

**Content:**

```json
{
    "errorMessages": [
        "Id can not be null",
        "Description can not be null or empty"
    ]
}
```

### Save an items collection

Used to persist a to-do items collection on database.

**URL:** `/todo-list/items`

**Method:** `POST`

**Auth required:** No.

**Data constraints:**

```json
[
  {
    "id": "[Valid number value, different for each item (WIP to avoid sending this parameter)]",
    "description": "[Valid text, not null or empty]"
  }
]
```

**Data example:**

```json
[
  {
    "id": 1,
		"description": "To do item example"
	},
  {
    "id": 2,
		"description": "To do item example 2"
	}
]
```

#### Success response

**Code:** `201 CREATED`

**Content:** No.

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

### Delete item

Used to delete a specific to-do item on database.

**URL:** `/todo-list/item/{id}`

**Method:** `DELETE`

**Auth required:** No.

**Data constraints:**

`{id} must be a valid number value.`

**Data example:**

```
/todoList/items/1
```

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** Sending a non number id param.

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

### Delete all items

Used to delete all to-do items saved on database.

**URL:** `/todo-list/items`

**Method:** `DELETE`

**Auth required:** No.

**Data constraints:** No.

**Data example:** No.

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

### Retrieve all items

Used to retrieve all to-do items on database.

**URL:** `/todo-list/items`

**Method:** `GET`

**Auth required:** No.

**Data constraints:** No.

#### Success response

**Code:** `200 OK`

**Content:** A collection of items.

```json
[
  {
    "id": 1,
    "description": "To do item example",
    "createdDatetime": "2019 26 25 11:07:03"
  },
  {
    "id": 2,
    "description": "To do item example 2",
    "createdDatetime": "2019 57 25 11:07:49"
  }
]
```

### Update item

Used to update a to-do item saved on database.

**URL:** `/todo-list/item`

**Method:** `PATCH`

**Auth required:** No.

**Data constraints:**

```json
{
	"id": "[Valid number value, unique for each item]",
	"description": "[Valid text, not null or empty]"
}
```

**Data example:**

```json
{
	"id": 1,
	"description": "New description for to-do item with id = 1"
}
```

#### Success response

**Code:** `204 NO CONTENT`

**Content:** No.

#### Error response

**Code:** `400 BAD REQUEST`

**Condition:** If 'id' or 'description' are null or empty.

**Content:**

```json
{
    "errorMessages": [
        "Description can not be null or empty",
        "Id can not be null"
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

## Technologies

This project uses the following technologies:
- Cassandra DB.
- Docker.
- Java.
- Maven.
- Redis.
- Spring Boot.

## Tools

This project was made using the following tools:

- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4).
- [IntelliJ IDEA](https://www.jetbrains.com/idea).
- [Gitignore.io](https://www.gitignore.io).
- [Postman](https://www.getpostman.com/).
- [Typora](https://typora.io).

## License

[GNU GPL 3.0](./LICENSE)