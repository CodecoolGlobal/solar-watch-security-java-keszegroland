# Welcome to the SolarWatch Project Repository!

SolarWatch is a full-stack application that uses Spring Boot for the backend to display the sunrise and sunset times for the selected city. This project showcases best practices in full-stack development, including containerization, CI/CD workflows, database integration, user authentication, and frontend development with React.

## Table of Contents
- [Overview](#overview)
- [Getting Started](#getting-Started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Features](#features)
  - [Dockerization](#dockerization)
  - [User Management](#user-management)
  - [Frontend Integration](#frontend-integration)
  - [Integration Testing](#integration-testing)
  - [Continuous Integration & Deployment](#continuous-integration--deployment)
- [Usage](#usage)
- [Technologies](#technologies)

## Overview
SolarWatch began as a simple Spring Boot Web API project and has evolved into a full-featured application with the following enhancements:

  - MVC Architecture: Utilizes the Spring MVC framework for a complete UI and API integration.
  - Dockerization: Containerized using Docker for seamless deployment and environment management.
  - Database Integration: Uses PostgreSQL to minimize dependency on external APIs.
  - User Management: Incorporates Spring Security for user authentication and authorization.
  - Automated Testing: Includes integration tests to ensure application reliability.
  - CI/CD: Employs GitHub Actions for automated testing and deployment workflows.

## Getting Started
  ### Prerequisites
  Ensure the following are installed:
  - Java 17+
  - Maven 4+
  - Docker
  - PostgreSQL
  - Node.js and npm (for the frontend)

  ### Installation
  To set up the project locally:
1. Clone the repository from GitHub:
   ```bash
    git clone git@github.com:CodecoolGlobal/solar-watch-security-java-keszegroland.git

    # navigate into the project directory
    cd solar-watch-security-java-keszegroland/solar-watch
   ```

2. Backend Setup:
   - Navigate to the backend directory:
     ```bash
      cd backend
     ```

   - Build the project:
     ```bash
      mvn clean install
     ```

   - Set environment variables:
     - Option 1: Using PowerShell
       ```bash
        $env:API_KEY="YOUR_API_KEY"
        $env:DATABASE_URL="jdbc:postgresql://localhost:5432/solar_watch_db"
        $env:DATABASE_USERNAME="YOUR_DATABASE_USERNAME"
        $env:DATABASE_PASSWORD="YOUR_DATABASE_PASSWORD"
       ```

     - Option 2: Using Command Prompt
       ```bash
        set API_KEY=YOUR_API_KEY
        set DATABASE_URL=jdbc:postgresql://localhost:5432/solar_watch_db
        set DATABASE_USERNAME=YOUR_DATABASE_USERNAME
        set DATABASE_PASSWORD=YOUR_DATABASE_PASSWORD
       ```

   - Run the application:
     ```bash
      mvn spring-boot:run
     ```

3. Frontend Setup:
   - Navigate to the frontend directory:
     ```bash
      cd frontend
     ```

   - Install dependencies:
     ```bash
      npm install
     ```

   - Start the development server:
       ```bash
        npm run dev
       ```

## Features
  ### Dockerization
  - The application is fully containerized using Docker.
  - Docker Compose manages multi-container setups, including the PostgreSQL database.
  - To run the application with Docker Compose:
    - Set environment variables in `docker-compose.yaml`.
        ```bash
        # db env variables
         POSTGRES_DB=YOUR_DB_NAME
         POSTGRES_USER=YOUR_DB_USERNAME
         POSTGRES_PASSWORD=YOUR_DB_PASSWORD

        ## backend env variables
         SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/YOUR_DB_NAME
         SPRING_DATASOURCE_USERNAME=YOUR_DB_USERNAME
         SPRING_DATASOURCE_PASSWORD=YOUR_DB_PASSWORD
         API_KEY=YOUR_API_KEY
        ```
    - Build and start the containers:
        ```bash
         docker compose up --build
        ```
  
  ### User Management
  - Handles user authentication and authorization via Spring Security.
  - Features include:
    - User Registration: Allows new users to sign up.
      - Visual: ![RegisterImage](.//ImagesReadme/RegisterImage.png)
    - Authentication: Users log in to receive a JWT token.
      - Visual: ![SignInImage](.//ImagesReadme/SignInImage.png)
    - Authorization: Secures endpoints to authenticated users.

  ### Frontend Integration
  - Built with React-Vite to interact with the SolarWatch API.
  - Features include:
    - City Search: Users can query sunrise and sunset times.
      - Visual: ![ReportImage](.//ImagesReadme/ReportImage.png)
    - Admin Page: Restricted to admin users for managing saved requests.
      - Visuals: ![AdminImage](.//ImagesReadme/AdminImage.png) ![UpdateImage](.//ImagesReadme/UpdateImage.png)
  
  ### Integration Testing
  - Comprehensive integration tests ensure application functionality and robustness.
  - Tests cover API calls and database interactions.
  
  ### Continuous Integration & Deployment
  - GitHub Actions automates testing and builds on each repository push.
  - The CI pipeline automatically runs tests and builds the project on each push to the repository.

## Usage
Using SolarWatch:
  - Register an Account: Sign up to create a new account.
  - Log In: Use your credentials to log in.
  - Search for a City: On the main page, search for a city to retrieve sunrise and sunset times.
  - Admin Access: If you have an admin role, access the [http://localhost:5173/admin](http://localhost:5173/admin) page to manage saved requests.


## Technologies
Technologies used in this application:
  - Backend: Spring Boot, Spring MVC, Spring Data JPA, Spring Security, Hibernate, PostgreSQL
  - Frontend: React-Vite
  - Containerization: Docker
  - CI/CD: GitHub Actions