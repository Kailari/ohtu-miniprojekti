package winkkari.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Database implements TipDAO {
    Connection conn;

    public Database() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:h2:~/winkkari", "", "");
        conn.prepareStatement("CREATE TABLE TEST(ID VARCHAR(24) PRIMARY KEY, TITLE VARCHAR(512), AUTHOR VARCHAR(512));");
    }

    @Override
    public void add(Tip tip) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO TEST(TITLE, AUTHOR) VALUES(?,?);");
            statement.setString(0, tip.getTitle());
            statement.setString(1, tip.getAuthor());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @Override
    public Optional<Tip> get(String id) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM TEST WHERE ID = ?;");
            statement.setString(0, id);
            ResultSet rs = statement.executeQuery();
            Tip tip = new Tip(rs.getString(0), rs.getString(1), rs.getString(2));
            return Optional.of(tip);
        } catch (SQLException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    @Override
    public Collection<Tip> getAll() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM TEST;");
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}