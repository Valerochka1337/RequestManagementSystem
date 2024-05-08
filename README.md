# Request Management System

### Technologies used
- Java 11
- Spring boot 2/security
- Hibernate
- Flyway (migrations)
- Mapstruct (mappers)
- PostgreSQL

### Setup
- Start 'docker-compose.yml' with command ```docker-compose up -f <path-to-docker-compose-file>```
- Compile and run the app (it will automatically apply migrations)

### Usage
- Import 'Basic requests.postman_collection.json' in Postman

### Implementation Choices
- There are permissions for each role. As the task said, they are already in the DB, I just created a migration file.
  But for myself, I prefer creating a special ApplicationListener, which creates roles and permissions if they don't
  exist. In my mind, it's better because I use permission names in the code, so DB changes can affect my application
  user logic (that's no good).
- When an administrator tries to find a user by a part of their name, a bad request will be returned if there are many
  users with that part of the name (e.g., partialUsername='a' -> bad request if there are users with names like Valera,
  Anton... as they both have 'a' in their names).
- Place indexes on username and user_id in the users_roles many-to-many relationship. Since we search for users by a
  part of their username, it's a complex question whether we benefit from using indexing. This needs to be tested in the
  future.
- No test, cause it says nothing about them. But I would have created Unit-tests for Mappers, Integration-tests for
  testing business logic.
- Requests have 2 separate dates: CreationDate and SentDate. This was made, because I suppose Operators want to sort
  requests by sent date. It seems logical, because User could have made the request way before sending it. And operators
  have no benefits for knowing when it was created rather than when it was sent

### What can be done in the future
- Unit and Integration testing (JUnit/Mockito, Testcontainers)
- More precise exception handling
- User registration
- JWT auth
- DB optimizations (like replication/partitioning/sharding)
- #TODO