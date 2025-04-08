import java.sql.*;

public class DatabaseManager {
    // Connection string for SQLite
    private static final String DB_URL = "jdbc:sqlite:music.db";

    public static Connection getConnection() throws SQLException {
        // Explicitly load the SQLite JDBC driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() {
        String createArtistTable = "CREATE TABLE IF NOT EXISTS Artist ("
                + "artist_id INTEGER PRIMARY KEY,"
                + "name TEXT NOT NULL,"
                + "genre TEXT,"
                + "biography TEXT"
                + ");";

        String createAlbumTable = "CREATE TABLE IF NOT EXISTS Album ("
                + "album_id INTEGER PRIMARY KEY,"
                + "title TEXT NOT NULL,"
                + "release_year INTEGER,"
                + "artist_id INTEGER,"
                + "FOREIGN KEY (artist_id) REFERENCES Artist(artist_id)"
                + ");";

        String createSongTable = "CREATE TABLE IF NOT EXISTS Song ("
                + "song_id INTEGER PRIMARY KEY,"
                + "title TEXT NOT NULL,"
                + "duration INTEGER,"
                + "track_number INTEGER,"
                + "album_id INTEGER,"
                + "FOREIGN KEY (album_id) REFERENCES Album(album_id)"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createArtistTable);
            stmt.execute(createAlbumTable);
            stmt.execute(createSongTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void populateData() {
        // Use INSERT OR IGNORE to prevent duplicate key errors on repeated runs
        String insertArtist1 = "INSERT OR IGNORE INTO Artist (artist_id, name, genre, biography) VALUES (1, 'The Beatles', 'Rock', 'Legendary band from Liverpool.')";
        String insertArtist2 = "INSERT OR IGNORE INTO Artist (artist_id, name, genre, biography) VALUES (2, 'Taylor Swift', 'Pop', 'Popular singer-songwriter.')";

        String insertAlbum1 = "INSERT OR IGNORE INTO Album (album_id, title, release_year, artist_id) VALUES (1, 'Abbey Road', 1969, 1)";
        String insertAlbum2 = "INSERT OR IGNORE INTO Album (album_id, title, release_year, artist_id) VALUES (2, '1989', 2014, 2)";

        String insertSong1 = "INSERT OR IGNORE INTO Song (song_id, title, duration, track_number, album_id) VALUES (1, 'Come Together', 259, 1, 1)";
        String insertSong2 = "INSERT OR IGNORE INTO Song (song_id, title, duration, track_number, album_id) VALUES (2, 'Something', 182, 2, 1)";
        String insertSong3 = "INSERT OR IGNORE INTO Song (song_id, title, duration, track_number, album_id) VALUES (3, 'Blank Space', 231, 1, 2)";
        String insertSong4 = "INSERT OR IGNORE INTO Song (song_id, title, duration, track_number, album_id) VALUES (4, 'Style', 231, 2, 2)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            stmt.executeUpdate(insertArtist1);
            stmt.executeUpdate(insertArtist2);
            stmt.executeUpdate(insertAlbum1);
            stmt.executeUpdate(insertAlbum2);
            stmt.executeUpdate(insertSong1);
            stmt.executeUpdate(insertSong2);
            stmt.executeUpdate(insertSong3);
            stmt.executeUpdate(insertSong4);
            conn.commit();
            System.out.println("Sample data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }
}