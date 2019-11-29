package winkkari.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LinkDatabaseDAOTest {
    private LinkDatabaseDAO dao;
    private Connection connection;
    private LinkDatabaseDAO.ConnectionProvider connectionProvider;

    private class ConnectionProvider implements LinkDatabaseDAO.ConnectionProvider {
        @Override
        public Connection get() throws SQLException {
            return connection = spy(DriverManager.getConnection("jdbc:h2:test.db"));
        }
    }

    @BeforeAll
    static void beforeAll() {
        try {
            Files.delete(Paths.get("./test.db"));
        } catch (IOException ignored) {
        }
    }

    @BeforeEach
    void beforeEach() {
        connectionProvider = spy(new ConnectionProvider());
        dao = new LinkDatabaseDAO(connectionProvider);
        reset(connectionProvider);
    }

    @Test
    void addingTipWithValidInfoDoesOneQuery() throws SQLException {
        dao.add(new LinkTip("test", "test", "test", "test"));

        verify(connectionProvider, times(1)).get();
        verify(connection, times(1)).prepareStatement(anyString());
    }

    @Test
    void afterAddingTipGetAllContainsTheTip() {
        dao.add(new LinkTip("testTitle", "testUrl", "testComment"));

        var result = dao.getAll().stream().findFirst().get();
        assertEquals("testTitle", result.getTitle());
        assertEquals("testUrl", result.getUrl());
        assertEquals("testComment", result.getComment());
    }

    @Test
    void afterAddGetReturnsTheCorrectTip() {
        dao.add(new LinkTip("testTitle", "testUrl", "testComment"));
        var added = dao.getAll().stream().findFirst().get();

        var result = dao.get(added.getId()).get();
        assertEquals("testTitle", result.getTitle());
        assertEquals("testUrl", result.getUrl());
        assertEquals("testComment", result.getComment());
    }
}
