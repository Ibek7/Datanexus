# Final Project: Music Database Application

This project is a simple Music Database Application implemented in Java. It uses SQLite as its database backend and provides a console-based client for interacting with the database.

## Project Structure

```
FinalProject/
├── README.md
└── SourceSRC/
    ├── DatabaseManager.java
    ├── MusicDBClient.java
    └── sqlite-jdbc-3.45.1.0.jar
```

- **DatabaseManager.java**: Contains methods to connect to the SQLite database, create tables, and populate the database with sample data.
- **MusicDBClient.java**: Provides a console-based interface for users to list artists, albums, and songs.
- **sqlite-jdbc-3.45.1.0.jar**: SQLite JDBC driver required to connect to the SQLite database.

## Prerequisites

- **Java Development Kit (JDK 11 or later)**
- **SQLite JDBC Driver** (included in the project under `src`)

## Compilation Instructions

1. Open a terminal and navigate to the `src` directory:

   ```bash
   cd /path/to/FinalProject/SourceSRC
   ```

2. Compile the Java files using the following command:

   - On macOS/Linux:
     ```bash
     javac -cp ".:sqlite-jdbc-3.49.1.0.jar" *.java
     ```
   - On Windows (use semicolon `;` as the classpath separator):
     ```bash
     javac -cp ".;sqlite-jdbc-3.49.1.0.jar" *.java
     ```

## Running the Application

To run the application, use the following command:

- On macOS/Linux:
  ```bash
   java -cp ".:sqlite-jdbc-3.49.1.0.jar" MusicDBClient                    
  ```
- On Windows:
  ```bash
  java -cp ".;sqlite-jdbc-3.49.1.0.jar" MusicDBClient
  ```

The application will initialize the database (creating tables and inserting sample data) and then present a menu with options to list artists, albums, and songs.

## Additional Notes

- **Database Initialization:** The database is initialized every time the application runs. The `DatabaseManager` class creates tables and populates them with sample data.
- **Error Handling:** Basic error handling is implemented. Any database errors will be printed to the console.
- **Package Structure:** Both classes belong to the `com.example.musicdb` package to ensure consistency and adhere to company standards.

## Contact

For any questions or issues, please contact Bekam at [bekamdawit551@gmail.com].
