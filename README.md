# Sanad Spring Task

A Spring Boot application that manages currency exchange rates and provides RESTful API endpoints for currency operations.

## Prerequisites

- Java 17
- Maven 3.8+
- PostgreSQL 15+ (compatible with Flyway migrations)

## Running the Application

### Using Docker Compose

1. Ensure Docker and Docker Compose are installed
2. Run the following command from the project root:
   ```bash
   docker-compose up
   ```
3. The application will be available at `http://localhost:8080`

### Running Natively

1. Set up PostgreSQL database:
   - Version: 15+
   - Database name: `sanad_db`
   - User: `sanad_user`
   - Password: `sanad_password`

2. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. The application will be available at `http://localhost:8080`

## API Documentation

The application includes OpenAPI documentation that can be accessed at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

## Testing

Integration tests use H2 in-memory database for isolation, ensuring each test runs independently. The `@TestPropertySource` annotation is used to load test-specific configurations from `application.yml`.

## Future Enhancements

1. **Environment Variables**: Move sensitive API keys and configuration into environment variables for better security.

## Development Tools

This project was developed with the assistance of AI tools:
- ChatGPT
- Cascade (Windsurf's AI coding assistant)
2. **Database Configuration**: Consider moving database credentials to environment variables or a secure secrets management system.
3. **API Rate Limiting**: Implement rate limiting for API endpoints to prevent abuse.
4. **Caching Strategy**: Enhance caching mechanisms for better performance.
5. **Error Handling**: Improve error handling and response formatting.

## Project Structure

- `src/main/java/org/example/sanadspringtask/` - Main application code
- `src/test/java/org/example/sanadspringtask/` - Test classes
- `src/main/resources/application.properties` - Application configuration
- `src/main/resources/db/migration/` - Flyway database migrations

## Testing

The project includes both unit tests and integration tests. Run tests using:
```bash
mvn test
```

## Database Migrations

The project uses Flyway for database migrations. Migration scripts are located in:
`src/main/resources/db/migration/`

Make sure to run migrations before starting the application for the first time.

## Security Suggestion by me

- API endpoints are currently unsecured (for development purposes)
- Consider implementing authentication/authorization for production use
- Sensitive data should be encrypted and stored securely
