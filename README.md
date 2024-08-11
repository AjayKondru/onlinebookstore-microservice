# Online Bookstore

This project is an online bookstore web application built using **Spring Boot** as the backend and **React** as the frontend.
The application allows users to browse books, manage their cart, place orders, and view their order history.
It also includes user authentication, profile management



## Features

- **User Authentication**: Signup, login, and logout functionality with JWT-based authentication.
- **User Profile Management**: Users can view and update their profile information.
- **Book Management**: Browse books available in the store.
- **Cart Management**: Add, remove, and clear items in the cart.
- **Order Management**: Place orders from the cart and view order history.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL
- **Build Tools**: Maven
- **Database**: H2

## Getting Started

### Prerequisites

- **Java** (version 11 or later)
- **Maven** (for managing Java dependencies)
### Backend Setup

1. **Clone the repository**:

    ```bash
    git clone [[https://github.com/your-username](https://github.com/AjayKondru)/online-bookstore.git](https://github.com/AjayKondru/onlinebookstore-microservice.git)
      

2. **Update the database configuration**:

    Used In Memory data base of H2 supported by Spring
    ```

3. **Build and run the backend**:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

    The backend should be running at `http://localhost:8080`.

   Attached postman collection for API details
   Step 1 : Create User
   Step 2 : Login with user created. API responds with a JWT token.
   Step 3 : All subsequent API requests should have a mandatory Authorization header with Bearer token generated in Step 2 
