package winkkari.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDatabaseDAO extends AbstractDatabaseDAO<BookTip> {
    private static final String TABLE_NAME = "TIPS_BOOKS";

    public BookDatabaseDAO() {
        super();
    }

    public BookDatabaseDAO(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    protected PreparedStatement getCreateTableQuery(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "CREATE TABLE " + TABLE_NAME +
                        "(ID SERIAL PRIMARY KEY, " +
                        "TITLE VARCHAR(512), " +
                        "AUTHOR VARCHAR(512), " +
                        "ISBN VARCHAR(13), " +
                        "CHECKED BOOLEAN);");
    }

    @Override
    protected BookTip constructFromResultSet(ResultSet rs) throws SQLException {
        return new BookTip(rs.getString("id"),
                           rs.getString("title"),
                           rs.getString("author"),
                           rs.getString("isbn"),
                           rs.getBoolean("checked"));
    }

    @Override
    protected PreparedStatement getAddQuery(Connection conn, BookTip tip) throws SQLException {
        var statement = conn.prepareStatement(
                "INSERT INTO " + TABLE_NAME +
                        "(TITLE, AUTHOR, ISBN, CHECKED) " +
                        "VALUES(?,?,?,0);");

        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getAuthor());
        statement.setString(3, tip.getIsbn());
        return statement;
    }

    @Override
    protected PreparedStatement getGetQuery(Connection connection, String id) throws SQLException {
        var statement = connection.prepareStatement(
                "SELECT " +
                        "ID as id, " +
                        "TITLE as title, " +
                        "AUTHOR as author, " +
                        "ISBN as isbn, " +
                        "CHECKED as checked " +
                        "FROM " + TABLE_NAME +
                        " WHERE ID = ?;");

        statement.setInt(1, Integer.parseInt(id));
        return statement;
    }

    @Override
    protected PreparedStatement getGetAllQuery(Connection conn) throws SQLException {
        var statement = conn.prepareStatement(
                "SELECT " +
                        "ID as id, " +
                        "TITLE as title, " +
                        "AUTHOR as author, " +
                        "ISBN as isbn, " +
                        "CHECKED as checked " +
                        "FROM " + TABLE_NAME);

        return statement;
    }

    @Override
    protected PreparedStatement getDeleteQuery(Connection conn, String id) throws SQLException {
        var statement = conn.prepareStatement(
                "DELETE " +
                        "FROM " + TABLE_NAME +
                        " WHERE ID = ?;");

        statement.setInt(1, Integer.parseInt(id));
        return statement;
    }

    @Override
    protected PreparedStatement getCheckQuery(
            Connection conn,
            String id,
            boolean check
    ) throws SQLException {
        var statement = conn.prepareStatement(
                "UPDATE " + TABLE_NAME +
                        " SET CHECKED=?" +
                        " WHERE ID = ?;");

        statement.setBoolean(1, !check);
        statement.setInt(2, Integer.parseInt(id));
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateQuery(Connection conn, BookTip tip) throws SQLException {
        var statement = conn.prepareStatement(
                "UPDATE " + TABLE_NAME +
                        " SET TITLE = ?," +
                        " AUTHOR = ?," +
                        " ISBN = ?," +
                        " CHECKED = ?" +
                        " WHERE ID = ?");

        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getAuthor());
        statement.setString(3, tip.getIsbn());
        statement.setBoolean(4, tip.getCheck());
        statement.setInt(5, Integer.parseInt(tip.getId()));
        return statement;
    }
}