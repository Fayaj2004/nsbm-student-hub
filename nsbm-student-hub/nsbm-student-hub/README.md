# NSBM Student Hub (Spring Boot)

## Requirements Covered
- Student entity: id (auto PK), name, email (unique), batch, gpa
- CRUD REST APIs
- Pagination + sorting for "get all"
- Role-based login (roles stored in DB)
- Password encryption (BCrypt)
- GitHub ready project

## Tech
- Spring Boot 3 (Java 17)
- Spring Data JPA
- Spring Security (HTTP Basic)
- MySQL (XAMPP)

## Database (XAMPP)
1. Start **Apache** + **MySQL** in XAMPP.
2. Ensure MySQL is running on `localhost:3306`.
3. Update `src/main/resources/application.properties` if your MySQL password is not empty.

Default DB used:
`nsbm_student_hub` (auto-created if missing)

## Run
### IntelliJ
- Open as Maven project
- Run `NsbmStudentHubApplication`

## Default Users (auto-seeded)
- **ADMIN**
  - username: `admin`
  - password: `admin123`
- **USER**
  - username: `user`
  - password: `user123`

## API Endpoints

### Auth
- `POST /api/auth/register` (public) -> creates a USER with encrypted password
- `GET /api/auth/me` (requires Basic Auth) -> shows logged-in principal

### Students
All endpoints are under `/api/students`

**ADMIN only**
- `POST /api/students`
- `PUT /api/students/{id}`
- `DELETE /api/students/{id}`

**USER or ADMIN**
- `GET /api/students`
- `GET /api/students/{id}`

### Pagination + Sorting example
`GET /api/students?page=0&size=5&sortBy=name&direction=asc`

## Quick cURL examples

### List students (as USER)
```bash
curl -u user:user123 "http://localhost:8080/api/students?page=0&size=5&sortBy=id&direction=asc"
```

### Create student (as ADMIN)
```bash
curl -u admin:admin123 -H "Content-Type: application/json" \
  -d '{ "name":"Kamal", "email":"kamal@example.com", "batch":"22.1", "gpa":3.4 }' \
  http://localhost:8080/api/students
```
