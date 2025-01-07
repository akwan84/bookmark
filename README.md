# Bookmark Manager Application
A bookmark manager built using:

- [Spring Boot (Java)](https://spring.io/projects/spring-boot) for the backend REST API
- [PostgreSQL](https://www.postgresql.org/) database for persistence
- [React](https://react.dev/) for the frontend

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