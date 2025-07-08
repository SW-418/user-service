# user-service
A basic user service with JWT authentication.

A user can signup and then login to retrieve a JWT token.

The JWT token can then be used to access protected endpoints.

Spring Security is used to determine which endpoints are publicly available and which require authentication.

The JWT token is validated by the [JwtAuthenticationFilter](src/main/java/io/samwells/user_service/security/JwtAuthenticationFilter.java) which is hit prior to the Controller endpoints.

## Endpoints
- `POST /api/v1/signup`
- `POST /api/v1/login`
- `GET /api/v1/users/{id}` - Needs Authorization header with Bearer token

## Docker

```bash
docker compose up --build
```

## Environment variables
- `USER_SVC_JWT_SECRET` - Used to generate and verify JWTs
