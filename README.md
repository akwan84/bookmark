# Bookmark Manager Application
A URL manager built using:

- [Spring Boot (Java)](https://spring.io/projects/spring-boot) for the backend REST API
- [PostgreSQL](https://www.postgresql.org/) database for persistence
- [React](https://react.dev/) for the frontend
- [Docker](https://www.docker.com/) to create a consistent environment for the frontend and backend

## Inspiration
This inspiration for this project was two-fold. During my internship, I had the chance to work on a simple microservice for URL shortening. The straightforward nature of that project gave me a clear understanding of how to structure and approach similar challenges. Building on this foundation, I saw an opportunity to extend the concept beyond a basic URL shortener, and add additional, meaningful features reflected in this project.

The other key piece of inspiration was the very beneficial learning experience. Having already completed a full-stack fitness tracker application using MongoDB and Express.js backend, I sought to challenge myself by exploring different technologies. Having gained experience with lightweight frameworks and NoSQL databases, I wanted to delve into a more robust framework and relational database, making Spring Boot and PostgreSQL a natural choice.

## Installation and Setup
### Required Installations
- <a href="https://www.docker.com/products/docker-desktop/" target="_blank">Docker Desktop</a>
- <a href="https://www.atlassian.com/git/tutorials/install-git" target="_blank">Git</a>

### Cloning the Repository
To download the code, click the Code button at the top of Github page and copy the HTTP link. Open your terminal or command prompt and type git clone <link> using the retrieved link to download the application code.

### Environment Variables and Database Configuration
In the `frontend` directory, rename `.env.example` to `.env`. This will only contain 1 environment variable, `REACT_APP_API_URL`. This can be customized for a deployed API, but if running locally, the default `http://localhost:8080` will suffice.

For database configuration, open `docker-compose.yml` and set the environment variables in lines 11-14 if you desire. But if running locally, the defaults will suffice. 

### Running the Application
Using the terminal, navigate to the project folder `bookmark`. Then to start the backend, begin by using the Maven wrapper (or Maven if installed locally) to package the application into an executable `.jar` file. For Windows:
```
mvnw.cmd clean package
```
and for Mac and Linux:
```
./mvnw clean package
```
Then to execute the backend:
```
docker-compose up -d
```
Which will download the PostgreSQL Docker image, dockerize the Spring Boot application, and run the application.

Similarly for the frontend, navigate to the `frontend` folder in the terminal and use:
```
docker-compose up -d
```

To check if everything is running, open Docker Desktop, and make sure that in the Containers tab, all running containers are green. With everything working, the application should be running locally, so open a web browser and go to `http://localhost:3000`.

### Stopping the Application
To stop the backend, from the root project folder, use:
```
docker-compose down
```
And similarly, to stop the frontend, navigate to the `frontend` folder in the terminal, and use the same command as above.

## Features
### Login and Registration
When launching the app, you will be greeted with the login page where you can choose to register a new user, or log in an existing user. An authentication token will be generated and stored in a cookie, allowing a user to stay logged in for a set amount of time before being logged out automatically.

### Logout
When logged in, a user has the ability to log themselves out of the application. This will redirect them back to the login page, and delete the cookie storing the authentication token, preventing any protected API routes from being called.

### Viewing Created Bookmarks
Once logged in, the home page will display the created bookmarked URLs, which can either be permanent, temporary, or one-time use. 

Each URL consists of a:
- <strong>Shortend URL</strong>: Ending with `url/<code>` where `<code>` is a randomly generated 6 digit alphanumeric string. 
- <strong>Full URL</strong>: Where the shortened URL will be redirected to
- <strong>Number of Visits</strong>: For permanent and temporary URLs, the number of times the shortend URL was visited

Temporary URLs will also include an <strong>expiration date</strong>, which is the time when the URL will no longer be valid. Expired temporary URLs will be displayed in red, while valid ones will be displayed in blue.

Similarly, one-time URLs will indicate whether they are active or inactive with active URLs displayed in blue, and inactive URLs displayed in blue.

### Creating URLs
To create new URLs, the user will provide a URL to redirect to, the type of URL to create (permanent, temporary, one-time), and the expiration time or status (active or inactive) for temporary and one-time URLs respectively. Once created, the newly created URL will be given a 6 character alphanumeric code, and the associated information will be displayed in the proper section of the home page.

### Deleting and Updating URLs
Each created URL bookmark can be updated or deleted. When updating, the URL to redirect to, and the expiration time or activity status (where applicable) can be updated, while the code mapped to the URL can not be changed. 

Deleting a URL will remove it from the home page, and invalidate the shortened URL.

## Future Plans
### Spring Security
Having learned the fundamentals of Spring Boot, learning about Spring Security would be a logical next step to provide more security measures to my application. At the moment, I have implemented a simple authentication which relies on the database queries (i.e. requires state), which works, but has several downsides. Having worked with JWT in the past, learning about how it works in tandem with Spring Security will help me to properly implement authentication in my backend. 

### Redis Caching for Updating Visitation Count
When the shortened URL is visited, a GET endpoint is called, which updates the visitation count in the PostgreSQL database each time. At larger scales, having to do this for every visitation can become slow. A better technique would be to use a tool like Redis to cache the number of visitations, and make periodic bulk updates to the PostgreSQL database instead. 

### Database Migrations
Database migrations are particularly important when working with relational databases to ensure schema consistency, manage versioning, and facilitate smooth transitions during updates. At the moment, Hibernate is handling schema updates (see `application.yml` under `ddl-auto`), but this technique can lead to unintended schema changes in production. Using a tool like Flyway would be desirable to better control schema changes.