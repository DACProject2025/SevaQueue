# SevaQueue Frontend

This is the React frontend for the SevaQueue application. It interacts with multiple backend services to provide a seamless user experience.

## Prerequisites

Before running the frontend, ensure the following backend services are running:

1.  **SevaQueueAuthService** (Spring Boot)
    *   Port: `8080` (Default)
    *   Function: Handles Registration and Login.
2.  **SevaQueueBackend** (Spring Boot)
    *   Port: `8081`
    *   Function: Provides main business data (e.g., Offices).
3.  **LoggerService** (.NET)
    *   Port: `5090`
    *   Function: Receives application logs.

## Getting Started

1.  **Install Dependencies**
    ```bash
    npm install
    ```

2.  **Run Development Server**
    ```bash
    npm run dev
    ```
    The application will be available at `http://localhost:5173`.

## Features

*   **Authentication**: User registration and login using JWT.
*   **Dashboard**: View active offices and their status.
*   **Logging**: Automatic logging of user actions to the .NET backend.
*   **UI/UX**: Premium glassmorphism design with responsive layout.

## Project Structure

*   `src/components`: UI components (Login, Register, Dashboard).
*   `src/context`: React Context for state management (AuthContext).
*   `src/services`: API service configurations.
