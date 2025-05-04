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
            System.out.println("4. Update an album's title (e.g., album ID 2, new title \"1989 (Taylor's Version)\")");
            System.out.println("5. Search songs by artist (exact match)");
            System.out.println("6. Add new artist");
            System.out.println("7. Add new album");
            System.out.println("8. Add new song");
            System.out.println("9. Delete song by ID");
            System.out.println("10. Delete album by ID");
            System.out.println("11. Delete artist by ID");
            System.out.println("12. Search songs by title keyword");
            System.out.println("13. List albums by year");
            System.out.println("14. List albums by year range");
            System.out.println("15. Count songs per album");
            System.out.println("16. Count albums per artist");
            System.out.println("17. List songs sorted by duration");
            System.out.println("18. List albums sorted by release year");
            System.out.println("19. Demonstrate transaction");
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
                case 4:
                    // Update an album's title
                    System.out.println("Hint: enter an existing album ID, for example: 2");
                    System.out.print("Enter album ID to update: ");
                    int albumId;
                    try {
                        albumId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid album ID.");
                        break;
                    }
                    System.out.println("Hint: enter the new album title, for example: 1989 (Taylor's Version)");
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    DatabaseManager.updateAlbumTitle(albumId, newTitle);
                    break;
                case 5:
                    // List songs by artist
                    System.out.println("Hint: enter the full artist name exactly, for example: The Beatles or Taylor Swift");
                    System.out.print("Enter artist name: ");
                    String artistName = scanner.nextLine();
                    DatabaseManager.listSongsByArtist(artistName);
                    break;
                case 6:
                    // Add new artist
                    System.out.println("Hint: enter name, genre, biography");
                    System.out.print("Name: ");
                    String aName = scanner.nextLine();
                    System.out.print("Genre: ");
                    String aGenre = scanner.nextLine();
                    System.out.print("Biography: ");
                    String aBio = scanner.nextLine();
                    DatabaseManager.addArtist(aName, aGenre, aBio);
                    break;
                case 7:
                    // Add new album
                    System.out.println("Hint: enter title, year, artist ID");
                    System.out.print("Title: ");
                    String albTitle = scanner.nextLine();
                    System.out.print("Year: ");
                    int albYear = Integer.parseInt(scanner.nextLine());
                    System.out.print("Artist ID: ");
                    int albArtistId = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.addAlbum(albTitle, albYear, albArtistId);
                    break;
                case 8:
                    // Add new song
                    System.out.println("Hint: enter title, duration (sec), track number, album ID");
                    System.out.print("Title: ");
                    String sTitle = scanner.nextLine();
                    System.out.print("Duration: ");
                    int sDuration = Integer.parseInt(scanner.nextLine());
                    System.out.print("Track #: ");
                    int sTrack = Integer.parseInt(scanner.nextLine());
                    System.out.print("Album ID: ");
                    int sAlbumId = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.addSong(sTitle, sDuration, sTrack, sAlbumId);
                    break;
                case 9:
                    // Delete song by ID
                    System.out.print("Enter song ID to delete: ");
                    int delSongId = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.deleteSongById(delSongId);
                    break;
                case 10:
                    // Delete album by ID
                    System.out.print("Enter album ID to delete: ");
                    int delAlbumId = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.deleteAlbumById(delAlbumId);
                    break;
                case 11:
                    // Delete artist by ID
                    System.out.print("Enter artist ID to delete: ");
                    int delArtistId = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.deleteArtistById(delArtistId);
                    break;
                case 12:
                    // Search songs by title keyword
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    DatabaseManager.searchSongsByTitle(keyword);
                    break;
                case 13:
                    // List albums by year
                    System.out.print("Enter year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.listAlbumsByYear(year);
                    break;
                case 14:
                    // List albums by year range
                    System.out.print("Enter start year: ");
                    int startYear = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter end year: ");
                    int endYear = Integer.parseInt(scanner.nextLine());
                    DatabaseManager.listAlbumsByYearRange(startYear, endYear);
                    break;
                case 15:
                    // Count songs per album
                    DatabaseManager.countSongsPerAlbum();
                    break;
                case 16:
                    // Count albums per artist
                    DatabaseManager.countAlbumsPerArtist();
                    break;
                case 17:
                    // List songs sorted by duration
                    System.out.print("Enter 1 for descending, 0 for ascending: ");
                    boolean descSong = scanner.nextLine().equals("1");
                    DatabaseManager.listSongsSortedByDuration(descSong);
                    break;
                case 18:
                    // List albums sorted by release year
                    System.out.print("Enter 1 for descending, 0 for ascending: ");
                    boolean descAlb = scanner.nextLine().equals("1");
                    DatabaseManager.listAlbumsSortedByYear(descAlb);
                    break;
                case 19:
                    // Demonstrate transaction
                    DatabaseManager.demonstrateTransaction();
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