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

    /**
     * Updates the title of an existing album.
     * @param albumId ID of the album to update.
     * @param newTitle New title to set.
     */
    public static void updateAlbumTitle(int albumId, String newTitle) {
        String updateSql = "UPDATE Album SET title = ? WHERE album_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, albumId);
            int affected = pstmt.executeUpdate();
            System.out.println("Updated " + affected + " album(s).");
        } catch (SQLException e) {
            System.out.println("Error updating album title: " + e.getMessage());
        }
    }

    /**
     * Lists all songs by a given artist, printing song title and album title.
     * @param artistName Name of the artist to query.
     */
    public static void listSongsByArtist(String artistName) {
        String query = 
            "SELECT s.title AS song_title, a.title AS album_title " +
            "FROM Song s " +
            "JOIN Album a ON s.album_id = a.album_id " +
            "JOIN Artist ar ON a.artist_id = ar.artist_id " +
            "WHERE ar.name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, artistName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String song = rs.getString("song_title");
                String album = rs.getString("album_title");
                System.out.println(song + " - " + album);
            }
        } catch (SQLException e) {
            System.out.println("Error listing songs by artist: " + e.getMessage());
        }
    }
    /**
     * Inserts a new artist.
     */
    public static void addArtist(String name, String genre, String biography) {
        String sql = "INSERT INTO Artist (name, genre, biography) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, genre);
            pstmt.setString(3, biography);
            pstmt.executeUpdate();
            System.out.println("Artist added.");
        } catch (SQLException e) {
            System.out.println("Error adding artist: " + e.getMessage());
        }
    }

    /**
     * Inserts a new album.
     */
    public static void addAlbum(String title, int releaseYear, int artistId) {
        String sql = "INSERT INTO Album (title, release_year, artist_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, releaseYear);
            pstmt.setInt(3, artistId);
            pstmt.executeUpdate();
            System.out.println("Album added.");
        } catch (SQLException e) {
            System.out.println("Error adding album: " + e.getMessage());
        }
    }

    /**
     * Inserts a new song.
     */
    public static void addSong(String title, int duration, int trackNumber, int albumId) {
        String sql = "INSERT INTO Song (title, duration, track_number, album_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, duration);
            pstmt.setInt(3, trackNumber);
            pstmt.setInt(4, albumId);
            pstmt.executeUpdate();
            System.out.println("Song added.");
        } catch (SQLException e) {
            System.out.println("Error adding song: " + e.getMessage());
        }
    }

    /**
     * Deletes a song by ID.
     */
    public static void deleteSongById(int songId) {
        String sql = "DELETE FROM Song WHERE song_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, songId);
            int affected = pstmt.executeUpdate();
            System.out.println("Deleted " + affected + " song(s).");
        } catch (SQLException e) {
            System.out.println("Error deleting song: " + e.getMessage());
        }
    }

    /**
     * Deletes an album by ID.
     */
    public static void deleteAlbumById(int albumId) {
        String sql = "DELETE FROM Album WHERE album_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, albumId);
            int affected = pstmt.executeUpdate();
            System.out.println("Deleted " + affected + " album(s).");
        } catch (SQLException e) {
            System.out.println("Error deleting album: " + e.getMessage());
        }
    }

    /**
     * Deletes an artist by ID.
     */
    public static void deleteArtistById(int artistId) {
        String sql = "DELETE FROM Artist WHERE artist_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artistId);
            int affected = pstmt.executeUpdate();
            System.out.println("Deleted " + affected + " artist(s).");
        } catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }

    /**
     * Searches songs by title keyword.
     */
    public static void searchSongsByTitle(String keyword) {
        String sql = "SELECT song_id, title FROM Song WHERE title LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("song_id") + ": " + rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching songs: " + e.getMessage());
        }
    }

    /**
     * Lists albums released in a given year.
     */
    public static void listAlbumsByYear(int year) {
        String sql = "SELECT album_id, title, release_year FROM Album WHERE release_year = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("album_id") + ": " + rs.getString("title")
                    + " (" + rs.getInt("release_year") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error listing albums by year: " + e.getMessage());
        }
    }

    /**
     * Lists albums released between two years.
     */
    public static void listAlbumsByYearRange(int startYear, int endYear) {
        String sql = "SELECT album_id, title, release_year FROM Album WHERE release_year BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, startYear);
            pstmt.setInt(2, endYear);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("album_id") + ": " + rs.getString("title")
                    + " (" + rs.getInt("release_year") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error listing albums by range: " + e.getMessage());
        }
    }

    /**
     * Counts songs per album.
     */
    public static void countSongsPerAlbum() {
        String sql = "SELECT a.title AS album_title, COUNT(s.song_id) AS song_count "
                   + "FROM Album a LEFT JOIN Song s ON a.album_id = s.album_id "
                   + "GROUP BY a.album_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("album_title") + ": " + rs.getInt("song_count"));
            }
        } catch (SQLException e) {
            System.out.println("Error counting songs per album: " + e.getMessage());
        }
    }

    /**
     * Counts albums per artist.
     */
    public static void countAlbumsPerArtist() {
        String sql = "SELECT ar.name AS artist_name, COUNT(a.album_id) AS album_count "
                   + "FROM Artist ar LEFT JOIN Album a ON ar.artist_id = a.artist_id "
                   + "GROUP BY ar.artist_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("artist_name") + ": " + rs.getInt("album_count"));
            }
        } catch (SQLException e) {
            System.out.println("Error counting albums per artist: " + e.getMessage());
        }
    }

    /**
     * Lists songs sorted by duration.
     */
    public static void listSongsSortedByDuration(boolean descending) {
        String sql = "SELECT title, duration FROM Song ORDER BY duration "
                   + (descending ? "DESC" : "ASC");
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("title") + ": " + rs.getInt("duration") + "s");
            }
        } catch (SQLException e) {
            System.out.println("Error listing songs sorted: " + e.getMessage());
        }
    }

    /**
     * Lists albums sorted by release year.
     */
    public static void listAlbumsSortedByYear(boolean descending) {
        String sql = "SELECT title, release_year FROM Album ORDER BY release_year "
                   + (descending ? "DESC" : "ASC");
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("title") + ": " + rs.getInt("release_year"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing albums sorted: " + e.getMessage());
        }
    }

    /**
     * Demonstrates a simple transaction that adds then rolls back.
     */
    public static void demonstrateTransaction() {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            // example: insert temporary artist then rollback
            String sql = "INSERT INTO Artist (name, genre, biography) VALUES ('Temp Artist', 'Test', 'Demo')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Temporary artist added but will rollback");
                conn.rollback();
                System.out.println("Rolled back transaction");
            }
        } catch (SQLException e) {
            System.out.println("Error in transaction demo: " + e.getMessage());
        }
    }
}