
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
- Password should be hashed before storing.
- A JWT or similar token is returned upon successful registration.

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
  "description": "Buy milk, eggs, and bread"
}
```

Response:

```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread"
}
```

#### 🖊️ Update To-Do

`PUT /todos/{id}`

Request:

```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese"
}
```

Responses:
- `200 OK` if authorized
- `403 Forbidden` if the user is not the owner

#### ❌ Delete To-Do

`DELETE /todos/{id}`

- `204 No Content` if deleted
- `403 Forbidden` if the user is not authorized

#### 📄 List To-Dos

`GET /todos?page=1&size=10`

Response:

```json
{
  "data": [
    {
      "id": 1,
      "title": "Buy groceries",
      "description": "Buy milk, eggs, bread"
    },
    {
      "id": 2,
      "title": "Pay bills",
      "description": "Pay electricity and water bills"
    }
  ],
  "page": 1,
  "limit": 10,
  "total": 2
}
```
