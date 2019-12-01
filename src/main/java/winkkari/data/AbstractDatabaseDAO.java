package winkkari.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDatabaseDAO<TTip extends Tip> implements TipDAO<TTip> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDatabaseDAO.class);
    private final ConnectionProvider connectionProvider;

    protected Connection getConnection() throws SQLException {
        return connectionProvider.get();
    }

    private static Connection defaultConnectionProvider() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tips", "", "");
    }

    protected AbstractDatabaseDAO() {
        this(AbstractDatabaseDAO::defaultConnectionProvider);
    }

    protected AbstractDatabaseDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;

        try (final var conn = getConnection();
             final var statement = getCreateTableQuery(conn)
        ) {
            statement.execute();
        } catch (SQLException ignored) {
            LOG.warn("Creating table failed. This is normal if table already exists.");
        }
    }

    protected abstract PreparedStatement getCreateTableQuery(Connection connection) throws SQLException;

    protected abstract PreparedStatement getGetQuery(
            Connection connection,
            String id
    ) throws SQLException;

    protected abstract PreparedStatement getAddQuery(
            Connection conn,
            TTip tip
    ) throws SQLException;

    protected abstract PreparedStatement getGetAllQuery(Connection conn) throws SQLException;

    protected abstract PreparedStatement getDeleteQuery(
            Connection conn,
            String id
    ) throws SQLException;

    protected abstract PreparedStatement getCheckQuery(
            Connection conn,
            String id,
            boolean check
    ) throws SQLException;

    protected abstract PreparedStatement getUpdateQuery(
            Connection conn,
            TTip tip
    ) throws SQLException;

    protected abstract TTip constructFromResultSet(ResultSet rs) throws SQLException;

    @Override
    public void add(TTip tip) {
        try (final var conn = getConnection();
             final var statement = getAddQuery(conn, tip)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error adding tip: ", e);
        }
    }

    @Override
    public Optional<TTip> get(String id) {
        try (final var conn = getConnection();
             final var statement = getGetQuery(conn, id)
        ) {
            final ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                LOG.trace("Unknown id \"{}\"", id);
                return Optional.empty();
            }

            final TTip tip = constructFromResultSet(rs);
            return Optional.of(tip);
        } catch (SQLException e) {
            LOG.trace("Could not get tip by ID");
            return Optional.empty();
        }
    }


    @Override
    public Collection<TTip> getAll() {
        try (final var conn = getConnection();
             final var statement = getGetAllQuery(conn)
        ) {
            final ResultSet rs = statement.executeQuery();
            final List<TTip> foundTips = new ArrayList<>();
            while (rs.next()) {
                foundTips.add(constructFromResultSet(rs));
            }
            return foundTips;
        } catch (SQLException e) {
            LOG.error("Error getting all database rows: ", e);
            return List.of();
        }
    }

    @Override
    public void delete(String id) {
        try (final var conn = getConnection();
             final var statement = getDeleteQuery(conn, id)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error deleting database row: ", e);
        }
    }

    @Override
    public void check(String id, boolean check) {
        try (final var conn = getConnection();
             final var statement = getCheckQuery(conn, id, check)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error updating database row: ", e);
        }
    }

    @Override
    public void update(TTip tip) {
        try (final var conn = getConnection();
             final var statement = getUpdateQuery(conn, tip)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error updating database row: ", e);
        }
    }

    public interface ConnectionProvider {
        Connection get() throws SQLException;
    }
}
