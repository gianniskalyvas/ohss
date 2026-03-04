# Oral Health Surveillance System (OHSS)

Oral Health Surveillance System (OHSS) is a full-stack web application designed to streamline the process of scheduling, managing, and administering oral health examinations and sessions. This project builds on Java (Spring Boot) for the backend and Angular for the frontend.

## Features

- User authentication and authorization
- Examiner and examination management
- Session scheduling and tracking
- RESTful API integration
- Secure backend with Spring Security


## Tech Stack

- **Backend:** Java, Spring Boot, Maven
- **Frontend:** Angular, TypeScript, HTML, CSS
- **Database:** MySQL (set connection URL in `application.properties`).

## Getting Started

### Prerequisites
- Java 17+
- Node.js 16+
- npm or yarn
- Maven


### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd ohss-backend
   ```
2. Add your database credentials to `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```
   Or set them as environment variables.
3. Build and run the backend:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The backend will start on `http://localhost:8080` by default.

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd ohss-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   npm start
   ```
4. The frontend will be available at `http://localhost:4200`.

## Project Structure

- `ohss-backend/` - Spring Boot backend (Java)
- `ohss-frontend/` - Angular frontend (TypeScript)

## Why This Project?

This project showcases:
- Full-stack development skills
- Clean code and modular architecture
- REST API design and integration
- Real-world authentication and authorization
- Automated testing (unit and integration)
- Modern UI/UX practices

## How to Use
- Clone the repository
- Follow the setup instructions above
- Explore the codebase and features

## License

This project is for demonstration and job application purposes. Please contact the author for further use or collaboration.

---

**Author:** [Your Name]

**Contact:** [your.email@example.com]

**LinkedIn:** [Your LinkedIn Profile]
