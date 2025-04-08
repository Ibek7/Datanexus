import java.sql.*;
import java.util.Scanner;

public class MusicDBClient {
    public static void main(String[] args) {
        // Create tables and populate data (only needed the first time you run)
        DatabaseManager.createTables();
        DatabaseManager.populateData();

        // Simple console-based menu-driven client
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMusic Database Client");
            System.out.println("1. List all Artists");
            System.out.println("2. List all Albums");
            System.out.println("3. List all Songs");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch(choice) {
                case 1:
                    listArtists();
                    break;
                case 2:
                    listAlbums();
                    break;
                case 3:
                    listSongs();
                    break;
                case 0:
                    System.out.println("Exiting client.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    // Retrieve and display all artists from the database
    private static void listArtists() {
        String query = "SELECT artist_id, name, genre FROM Artist";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nArtists:");
            while (rs.next()) {
                int id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String genre = rs.getString("genre");
                System.out.println(id + ": " + name + " (" + genre + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
    }
    
    // Retrieve and display all albums from the database
    private static void listAlbums() {
        String query = "SELECT album_id, title, release_year FROM Album";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nAlbums:");
            while (rs.next()) {
                int id = rs.getInt("album_id");
                String title = rs.getString("title");
                int year = rs.getInt("release_year");
                System.out.println(id + ": " + title + " (" + year + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving albums: " + e.getMessage());
        }
    }
    
    // Retrieve and display all songs from the database
    private static void listSongs() {
        String query = "SELECT song_id, title, duration FROM Song";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nSongs:");
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                System.out.println(id + ": " + title + " (" + duration + " seconds)");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving songs: " + e.getMessage());
        }
    }
}