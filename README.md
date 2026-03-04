
# Oral Health Surveillance System (OHSS)

The Oral Health Surveillance System (OHSS) is a web-based tool designed for teams of doctors to visit schools, collect oral health data, and (in future versions) perform data analysis. Currently, the application enables efficient data retrieval and management from multiple schools by multiple examiners. Data analysis features are planned for future version.


## Purpose


OHSS is designed for two main roles:

- **Doctors:** Visit schools and collect oral health data using the system.
- **Admin:** Manage doctor accounts and, in the future, perform data analysis on the collected data.

Currently, only data collection and doctor management are available. Data analysis features for admins are planned for future development.



## Tech Stack

- **Backend:** Java, Spring Boot, Maven
- **Frontend:** Angular, TypeScript, HTML, CSS
- **Database:** MySQL (configure in `application.properties`)


## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- npm or yarn
- Maven

### Backend Setup
1. Go to the backend directory:
   ```bash
   cd ohss-backend
   ```
2. Set your database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```
   Or use environment variables.
3. Build and run the backend:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend runs at `http://localhost:8080` by default.

### Frontend Setup
1. Go to the frontend directory:
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
   The frontend runs at `http://localhost:4200` by default.


## Project Structure

- `ohss-backend/` — Spring Boot backend (Java)
- `ohss-frontend/` — Angular frontend (TypeScript)


