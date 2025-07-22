# Enanpharma - E-commerce Project

This is a full-stack e-commerce application designed to provide a robust platform for online shopping. It features a secure backend API built with Spring Boot and a dynamic, responsive frontend developed with Vue.js.

## Project Overview

The project is structured into two main components:

*   **Backend:** A powerful RESTful API developed using Java and the Spring Boot framework. It is responsible for handling all business logic, data persistence, security (authentication and authorization), and serving data to the frontend.
*   **Frontend:** A modern Single-Page Application (SPA) built with Vue.js and Vite. It provides an intuitive user interface for browsing products, managing shopping carts, placing orders, and user authentication.

## Features

*   **User Management:** Secure user registration, login, and profile management.
*   **Authentication & Authorization:** JWT (JSON Web Token) based security for protecting API endpoints and managing user roles (e.g., admin, customer).
*   **Product Catalog:** Comprehensive product listing with details, search, and filtering capabilities.
*   **Category Management:** Organize products into various categories for easy navigation.
*   **Shopping Cart:** Functionality to add, update, and remove items from a persistent shopping cart.
*   **Order Processing:** Streamlined order creation, tracking, and history for users.
*   **RESTful API:** A well-documented and structured API for seamless communication between the frontend and backend.
*   **Database Migration:** Automated database schema management using Flyway.

## Technologies Used

### Backend (Spring Boot)

*   **Language:** Java 21
*   **Framework:** Spring Boot 3.5.0
    *   **Spring Web:** For building RESTful APIs.
    *   **Spring Data JPA:** For simplified data access and persistence with Hibernate.
    *   **Spring Security:** For robust authentication and authorization mechanisms.
*   **Database:** PostgreSQL (Relational Database)
*   **Build Tool:** Maven (Dependency Management and Project Build)
*   **ORM:** Hibernate (Object-Relational Mapping)
*   **Security:** JJWT (Java JWT) for JSON Web Token implementation.
*   **Utilities:** Lombok (To reduce boilerplate code like getters/setters).
*   **Database Migration:** Flyway (For managing database schema versions).

### Frontend (Vue.js)

*   **Framework:** Vue.js 3 (Progressive JavaScript Framework)
*   **Build Tool:** Vite (Next-generation frontend tooling for fast development)
*   **Routing:** Vue Router (Official router for Vue.js)
*   **HTTP Client:** Axios (Promise-based HTTP client for the browser and Node.js)
*   **UI Library:** Bootstrap 5 (Popular CSS framework for responsive and mobile-first design)

### Infrastructure

*   **Containerization:** Docker & Docker Compose (For easy setup and deployment of both backend and frontend services).
*   **Web Server (Frontend):** Nginx (Used in Docker for serving the Vue.js application).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed on your system:

*   **Java Development Kit (JDK) 21 or later**
*   **Node.js (LTS version recommended) and npm**
*   **PostgreSQL (or use the Dockerized version provided)**
*   **Docker and Docker Compose**

### Installation and Setup

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/enanpharma.git
    cd enanpharma
    ```

2.  **Database Setup (Choose one option):**

    *   **Option A: Using Docker Compose (Recommended)**
        The `docker-compose.yml` file includes a PostgreSQL service. This is the easiest way to get the database running.

        ```bash
        docker-compose up -d postgres
        ```
        This will start a PostgreSQL container. The backend will automatically connect to it and run Flyway migrations.

    *   **Option B: Manual PostgreSQL Setup**
        If you prefer to use a local PostgreSQL installation:
        *   Ensure PostgreSQL is running.
        *   Create a new database named `enanpharma`.
        *   The backend will automatically run the Flyway migrations (`V1__init.sql`, etc.) on startup to create tables and initial data.

3.  **Backend Configuration:**

    *   Navigate to the `backend` directory: `cd backend`
    *   Open `src/main/resources/application.properties`.
    *   **Database Connection:** If using manual PostgreSQL setup, update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` to match your local PostgreSQL configuration. If using Docker Compose, the default settings should work.
    *   **JWT Secret:** Update the `app.jwtSecret` property with a strong, unique secret key. This is crucial for security.

4.  **Run the Backend:**

    *   From the `backend` directory, you can run the Spring Boot application using Maven Wrapper:

        ```bash
        ./mvnw spring-boot:run
        ```
        The backend will start on `http://localhost:8080` (default).

5.  **Frontend Installation and Run:**

    *   Navigate to the `frontend` directory: `cd ../frontend`
    *   Install frontend dependencies:

        ```bash
        npm install
        ```
    *   Start the development server:

        ```bash
        npm run dev
        ```
        The frontend will be available at `http://localhost:5173` (default).

### Running with Docker Compose (Full Stack)

For a complete, containerized setup, you can use Docker Compose to run both the backend and frontend services:

1.  Ensure you are in the root directory of the project (`enanpharma`).
2.  Build and start all services:

    ```bash
    docker-compose up --build
    ```
    This command will:
    *   Build Docker images for both the backend and frontend.
    *   Start the PostgreSQL database container.
    *   Start the backend service (Spring Boot application).
    *   Start the frontend service (Nginx serving the Vue.js app).

    The frontend will be accessible via Nginx, typically at `http://localhost:80` (or `http://localhost` if port 80 is free).

## API Endpoints

The backend exposes a comprehensive set of RESTful API endpoints. You can explore the available endpoints by examining the controller classes:

*   `backend/src/main/java/com/ecommerce/enanpharma/controller/AuthController.java` (User authentication)
*   `backend/src/main/java/com/ecommerce/enanpharma/controller/UserController.java` (User management)
*   `backend/src/main/java/com/ecommerce/enanpharma/controller/CategoryController.java` (Product categories)
*   `backend/src/main/java/com/ecommerce/enanpharma/controller/ProductController.java` (Product management)
*   `backend/src/main/java/com/ecommerce/enanpharma/controller/OrderController.java` (Order processing)
*   `backend/src/main/java/com/ecommerce/enanpharma/controller/PharmaController.java` (Specific pharmacy-related endpoints, if any)

## Database Schema

The database schema is managed by Flyway migrations, located in `backend/src/main/resources/db/migration/`. The initial schema is defined in `V1__init.sql`, with subsequent migrations handling schema evolution.

## Contributing

Contributions are welcome! If you'd like to contribute, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and ensure tests pass.
4.  Commit your changes with a clear and concise message.
5.  Push your branch to your fork.
6.  Open a pull request to the `main` branch of this repository.

Please make sure to update tests as appropriate and adhere to the existing code style.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details. If `LICENSE.md` does not exist, the project is implicitly licensed under MIT, and you are free to create the `LICENSE.md` file with the standard MIT license text.