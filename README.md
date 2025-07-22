# Enanpharma - Ecommerce Project

This is a full-stack ecommerce application built with a Spring Boot backend and a Vue.js frontend.

## Project Overview

The project is divided into two main parts:

*   **Backend:** A Java-based RESTful API using the Spring Boot framework. It handles business logic, data persistence, and security.
*   **Frontend:** A modern, single-page application (SPA) built with Vue.js and Vite. It provides the user interface for interacting with the ecommerce platform.

## Features

*   User authentication and authorization with JWT.
*   Product and category management.
*   Shopping cart and order processing.
*   RESTful API for seamless frontend-backend communication.

## Technologies Used

### Backend

*   **Java 21**
*   **Spring Boot 3.5.0:**
    *   Spring Web
    *   Spring Data JPA
    *   Spring Security
*   **PostgreSQL:** Database
*   **Maven:** Dependency management
*   **Lombok:** For reducing boilerplate code.
*   **JJWT:** For JWT token generation and validation.

### Frontend

*   **Vue.js 3:** A progressive JavaScript framework.
*   **Vite:** A fast build tool for modern web development.
*   **Vue Router:** For client-side routing.
*   **Axios:** For making HTTP requests to the backend.
*   **Bootstrap:** For UI components and styling.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Java 21 or later
*   Node.js and npm
*   PostgreSQL

### Installation

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/enanpharma.git
    cd enanpharma
    ```

2.  **Set up the database:**

    *   Make sure you have PostgreSQL installed and running.
    *   Create a new database named `enanpharma`.
    *   Run the `create_db_script.sql` script to create the necessary tables and roles.

3.  **Configure the backend:**

    *   Open the `backend/src/main/resources/application.properties` file.
    *   Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to match your PostgreSQL configuration.
    *   Update the `app.jwtSecret` with a strong, unique secret key.

4.  **Run the backend:**

    *   Navigate to the `backend` directory.
    *   Run the following command to start the Spring Boot application:

        ```bash
        ./mvnw spring-boot:run
        ```

5.  **Run the frontend:**

    *   Navigate to the `frontend` directory.
    *   Install the dependencies:

        ```bash
        npm install
        ```

    *   Start the development server:

        ```bash
        npm run dev
        ```

The frontend will be available at `http://localhost:5173`, and the backend will be running on `http://localhost:8080`.

## API Endpoints

The backend exposes a set of RESTful endpoints for managing users, products, categories, and orders. You can explore the available endpoints by examining the controller classes in the `backend/src/main/java/com/ecommerce/enanpharma/controller` directory.

## Database Schema

The database schema is defined in the `create_db_script.sql` file. It includes tables for users, roles, products, categories, and orders.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
