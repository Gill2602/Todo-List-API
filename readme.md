
# 📝 Todo List API

This project was developed following an exercise from the Roadmap SH platform. The goal is to build a RESTful API to manage a to-do list with full user authentication functionality.

- https://roadmap.sh/projects/todo-list-api

## 🎯 Project Goals

This API project covers the following key concepts:

- User authentication
- Schema design and database usage
- RESTful API design
- CRUD operations
- Error handling
- API security

## ⚙️ Tech Stack

- **Language:** Java 21
- **Framework/API:** Spring Boot
- **Database:** MariaDB

## 📌 Core Features

### 👤 User Authentication

#### 📥 Registration

`POST /register`

Request:

```json
{
  "name": "John Doe",
  "email": "john@doe.com",
  "password": "password"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
}
```

- Email must be unique.
- Password is hashed before storing.
- A JWT token is returned upon successful registration.

#### 🔐 Login

`POST /login`

Request:

```json
{
  "email": "john@doe.com",
  "password": "password"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
}
```

---

### ✅ CRUD for To-Do Items

> All following operations require the token in the request header:
```
Authorization: Bearer <token>
```

#### ➕ Create To-Do

`POST /todos`

Request:

```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread",
  "expirationDate": "2025-07-27"
}
```

Response:

```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread",
  "progress": "TO_DO",
  "expirationDate": "2025-07-27"
}
```

#### 🖊️ Update To-Do

`PUT /todos/{id}`

Request:

```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese",
  "progress": "IN_PROGRESS",
  "expirationDate": "2025-07-27"
}
```

Responses:
- `200 OK` if authorized
- `403 Forbidden` if the user is not the owner

#### ❌ Delete To-Do

`DELETE /todos/{id}`

- `204 No Content` if deleted
- `403 Forbidden` if the user is not authorized

#### 📄 Read All To-Dos

`GET /todos?page=0&size=10`

Response:

```json
{
  "content": [
    {
      "id": 7,
      "title": "Buy groceries",
      "description": "Buy milk, eggs, and bread",
      "progress": "CANCELLED",
      "expirationDate": null
    },
    {
      "id": 8,
      "title": "Buy groceries",
      "description": "Buy milk, eggs, and bread",
      "progress": "CANCELLED",
      "expirationDate": null
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  }
}
```
