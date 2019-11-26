package winkkari.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DatabaseDAO implements TipDAO {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseDAO.class);
    private static final String TABLE_NAME = "TIPS";

    private Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tips", "", "");
    }

    public DatabaseDAO() {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("CREATE TABLE " + TABLE_NAME + "(ID SERIAL PRIMARY KEY, TITLE VARCHAR(512), AUTHOR VARCHAR(512));")
        ) {
            statement.execute();
        } catch (SQLException ignored) {
            LOG.warn("Creating table failed. This is normal if table already exists.");
        }
    }

    @Override
    public void add(Tip tip) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(TITLE, AUTHOR) VALUES(?,?);")
        ) {
            statement.setString(1, tip.getTitle());
            statement.setString(2, tip.getAuthor());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error adding tip: ", e);
        }
    }

    @Override
    public Optional<Tip> get(String id) {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("SELECT ID as id, TITLE as title, AUTHOR as author FROM " + TABLE_NAME + " WHERE ID = ?;")
        ) {
            statement.setInt(1, Integer.valueOf(id));

            final ResultSet rs = statement.executeQuery();
            final Tip tip = new Tip(rs.getString("id"), rs.getString("title"), rs.getString("author"));
            return Optional.of(tip);
        } catch (SQLException e) {
            LOG.trace("Could not get tip by ID");
            return Optional.empty();
        }
    }

    @Override
    public Collection<Tip> getAll() {
        try (final var conn = getConnection();
             final var statement = conn.prepareStatement("SELECT ID as id, TITLE as title, AUTHOR as author FROM " + TABLE_NAME)
        ) {
            final ResultSet rs = statement.executeQuery();
            final List<Tip> foundTips = new ArrayList<>();
            while (rs.next()) {
                foundTips.add(new Tip(rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author")));
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
            statement.setInt(1, Integer.valueOf(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error deleting database entry: ", e);
        }
    }

}