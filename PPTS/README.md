# Placement Preparation Tracker System (PPTS)

## About the Project
The **Placement Preparation Tracker System (PPTS)** is a structured, console-based Java application designed to help students track, organize, and improve their coding preparation for campus placements. 

Often, students practice across various platforms (like LeetCode, HackerRank, CodeChef, and Codeforces) and lose track of their overall progress and weak areas. This system provides a unified personal record to track solved problems, monitor competitive programming rating progression (e.g., climbing from the 1100–1200 band toward Expert), and automatically calculates a "Placement Readiness Score" based on practice volume, rating, and topic diversity.

## Key Features
- **User Authentication**: Secure individual student accounts (Registration & Login).
- **Track Solved Problems**: Log problems with details including Name, Topic, Platform, Difficulty, and Language (e.g., C++, Java, Python).
- **View & Search**: Quickly find past problems filtered by Topic or Platform.
- **Update & Delete**: Modify or remove inaccurate problem records.
- **Stats & Rating Progression**: Visual text-based summary of topics covered and competitive rating progression analysis.
- **Readiness Score**: An auto-calculated metric out of 100 assessing placement readiness.

## Resources & Technologies Used
- **Core Logic**: Java 11
- **Database**: MySQL Server
- **Integration**: JDBC (Java Database Connectivity) via `mysql-connector-java` (Version 8.0.33)
- **Build Tool**: Apache Maven (Used for dependency management and packaging the application into a single executable JAR file)
- **Target IDE**: Developed and tested for seamless use in Visual Studio Code (VS Code).

## Setup Instructions

### 1. Database Setup
1. Ensure you have MySQL Server installed and running locally.
2. Open your MySQL client and execute the provided `schema.sql` script to create the database (`ppts_db`) and necessary tables (`users`, `problems`):
   ```sql
   source schema.sql;
   ```
3. *(Optional)* Update the database credentials (`USER` and `PASSWORD`) inside the `src/main/java/com/ppts/DatabaseConnection.java` file if they differ from your local MySQL configuration.

### 2. Running the Application
**Option A: Using Maven (Terminal)**
```bash
# Compile and package the application into a fat jar
mvn clean compile assembly:single

# Run the executable jar
java -jar target/ppts-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**Option B: Using VS Code**
1. Open the project folder in VS Code.
2. Ensure you have the "Extension Pack for Java" installed.
3. Open `src/main/java/com/ppts/Main.java`.
4. Click the "Run" button provided by the Java extension above the `main` method.

## Future Enhancements
The architecture separates Data Access Objects (DAOs) and Models from the main UI, making it highly scalable:
- **GUI Application**: Easily adaptable to JavaFX or Swing.
- **Web Application**: Can be migrated to Spring Boot and Spring Data JPA.
- **AI Recommendation Engine**: Can be extended to automatically recommend new coding problems based on identified weak topics.
