package winkkari.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookDatabaseDAO implements TipDAO<BookTip> {
    private static final Logger LOG = LoggerFactory.getLogger(BookDatabaseDAO.class);
    private static final String TABLE_NAME = "TIPS_BOOKS";

    private final ConnectionProvider connectionProvider;

    private Connection getConnection() throws SQLException {
        return connectionProvider.get();
    }

    private static Connection defaultConnectionProvider() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tips", "", "");
    }

    public BookDatabaseDAO() {
        this(BookDatabaseDAO::defaultConnectionProvider);
    }

    public BookDatabaseDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;

        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("CREATE TABLE " + TABLE_NAME +
                                                                 "(ID SERIAL PRIMARY KEY, " +
                                                                 "TITLE VARCHAR(512), " +
                                                                 "AUTHOR VARCHAR(512), " +
                                                                 "ISBN VARCHAR(13), " +
                                                                 "CHECKED INTEGER);")
        ) {
            statement.execute();
        } catch (SQLException ignored) {
            LOG.warn("Creating table failed. This is normal if table already exists.");
        }
    }

    @Override
    public void add(BookTip tip) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(TITLE, AUTHOR, ISBN) VALUES(?,?,?);")
        ) {
            statement.setString(1, tip.getTitle());
            statement.setString(2, tip.getAuthor());
            statement.setString(3, tip.getIsbn());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error adding tip: ", e);
        }
    }

    @Override
    public Optional<BookTip> get(String id) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("SELECT ID as id, TITLE as title, AUTHOR as author, ISBN as isbn, CHECKED as checked FROM " + TABLE_NAME + " WHERE ID = ?;")
        ) {
            statement.setInt(1, Integer.parseInt(id));

            final ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                LOG.trace("Unknown id \"{}\"", id);
                return Optional.empty();
            }

            final BookTip tip = new BookTip(rs.getString("id"),
                                            rs.getString("title"),
                                            rs.getString("author"),
                                            rs.getString("isbn"),
                                            rs.getBoolean("checked"));
            return Optional.of(tip);
        } catch (SQLException e) {
            LOG.trace("Could not get tip by ID");
            return Optional.empty();
        }
    }

    @Override
    public Collection<BookTip> getAll() {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("SELECT ID as id, TITLE as title, AUTHOR as author, ISBN as isbn, CHECKED as checked FROM " + TABLE_NAME)
        ) {
            final ResultSet rs = statement.executeQuery();
            final List<BookTip> foundTips = new ArrayList<>();
            while (rs.next()) {
                foundTips.add(new BookTip(rs.getString("id"),
                                          rs.getString("title"),
                                          rs.getString("author"),
                                          rs.getString("isbn"),
                                          rs.getBoolean("checked")));
            }
            return foundTips;
        } catch (SQLException e) {
            LOG.error("Error getting all database entries: ", e);
            return List.of();
        }
    }

    @Override
    public void delete(String id) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE ID = ?;")
        ) {
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error deleting database entry: ", e);
        }
    }

    @Override
    public void check(String id, boolean check) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("UPDATE " + TABLE_NAME + " SET CHECKED=? WHERE ID = ?;")
        ) {
            statement.setBoolean(1, !check);
            statement.setInt(2, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error updating database entry: ", e);
        }
    }

    public interface ConnectionProvider {
        Connection get() throws SQLException;
    }
}